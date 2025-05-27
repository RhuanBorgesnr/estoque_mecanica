package com.estoque.service;

import com.estoque.dao.MovimentacaoDAO;
import com.estoque.dao.PecaDAO;
import com.estoque.model.Movimentacao;
import com.estoque.model.Peca;
import com.estoque.model.TipoMovimentacao;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing inventory operations.
 * Contains business logic for stock management.
 */
public class EstoqueService {
    
    private final PecaDAO pecaDAO;
    private final MovimentacaoDAO movimentacaoDAO;
    
    public EstoqueService() {
        this.pecaDAO = new PecaDAO();
        this.movimentacaoDAO = new MovimentacaoDAO();
    }
    
    /**
     * Adds a new part to the inventory.
     * @param peca the part to add
     * @return the saved part
     */
    public Peca adicionarPeca(Peca peca) {
        Peca pecaSalva = pecaDAO.save(peca);
        
        // Register initial stock movement
        if (peca.getQuantidadeEstoque() > 0) {
            Movimentacao movimentacao = new Movimentacao(
                pecaSalva, 
                TipoMovimentacao.ENTRADA, 
                peca.getQuantidadeEstoque(),
                "Estoque inicial",
                "Sistema"
            );
            movimentacaoDAO.save(movimentacao);
        }
        
        return pecaSalva;
    }
    
    /**
     * Updates an existing part.
     * @param peca the part to update
     * @return the updated part
     */
    public Peca atualizarPeca(Peca peca) {
        return pecaDAO.save(peca);
    }
    
    /**
     * Removes stock quantity from a part (stock reduction).
     * @param pecaId the part ID
     * @param quantidade quantity to remove
     * @param observacao reason for the reduction
     * @param usuario user performing the operation
     * @return true if operation was successful
     */
    public boolean darBaixaEstoque(Long pecaId, Integer quantidade, String observacao, String usuario) {
        Peca peca = pecaDAO.findById(pecaId);
        if (peca == null) {
            throw new IllegalArgumentException("Peça não encontrada");
        }
        
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        
        if (peca.getQuantidadeEstoque() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente. Disponível: " + peca.getQuantidadeEstoque());
        }
        
        // Update stock
        peca.darBaixa(quantidade);
        pecaDAO.save(peca);
        
        // Register movement
        Movimentacao movimentacao = new Movimentacao(
            peca, 
            TipoMovimentacao.SAIDA, 
            quantidade,
            observacao,
            usuario
        );
        movimentacaoDAO.save(movimentacao);
        
        return true;
    }
    
    /**
     * Adds stock quantity to a part (stock increase).
     * @param pecaId the part ID
     * @param quantidade quantity to add
     * @param observacao reason for the addition
     * @param usuario user performing the operation
     * @return true if operation was successful
     */
    public boolean adicionarEstoque(Long pecaId, Integer quantidade, String observacao, String usuario) {
        Peca peca = pecaDAO.findById(pecaId);
        if (peca == null) {
            throw new IllegalArgumentException("Peça não encontrada");
        }
        
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        
        // Update stock
        peca.adicionarEstoque(quantidade);
        pecaDAO.save(peca);
        
        // Register movement
        Movimentacao movimentacao = new Movimentacao(
            peca, 
            TipoMovimentacao.ENTRADA, 
            quantidade,
            observacao,
            usuario
        );
        movimentacaoDAO.save(movimentacao);
        
        return true;
    }
    
    /**
     * Adjusts stock to a specific quantity.
     * @param pecaId the part ID
     * @param novaQuantidade new stock quantity
     * @param observacao reason for the adjustment
     * @param usuario user performing the operation
     * @return true if operation was successful
     */
    public boolean ajustarEstoque(Long pecaId, Integer novaQuantidade, String observacao, String usuario) {
        Peca peca = pecaDAO.findById(pecaId);
        if (peca == null) {
            throw new IllegalArgumentException("Peça não encontrada");
        }
        
        if (novaQuantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        
        Integer quantidadeAnterior = peca.getQuantidadeEstoque();
        Integer diferenca = novaQuantidade - quantidadeAnterior;
        
        if (diferenca != 0) {
            // Update stock
            peca.setQuantidadeEstoque(novaQuantidade);
            pecaDAO.save(peca);
            
            // Register movement
            TipoMovimentacao tipo = diferenca > 0 ? TipoMovimentacao.ENTRADA : TipoMovimentacao.SAIDA;
            Movimentacao movimentacao = new Movimentacao(
                peca, 
                TipoMovimentacao.AJUSTE, 
                Math.abs(diferenca),
                observacao + " (Anterior: " + quantidadeAnterior + ", Novo: " + novaQuantidade + ")",
                usuario
            );
            movimentacaoDAO.save(movimentacao);
        }
        
        return true;
    }
    
    /**
     * Retrieves all parts.
     * @return list of all parts
     */
    public List<Peca> listarPecas() {
        return pecaDAO.findAll();
    }
    
    /**
     * Searches parts by name.
     * @param nome name to search for
     * @return list of matching parts
     */
    public List<Peca> buscarPecasPorNome(String nome) {
        return pecaDAO.findByNome(nome);
    }
    
    /**
     * Retrieves parts with low stock.
     * @return list of parts with low stock
     */
    public List<Peca> listarEstoqueBaixo() {
        return pecaDAO.findEstoqueBaixo();
    }
    
    /**
     * Finds a part by ID.
     * @param id the part ID
     * @return the part or null if not found
     */
    public Peca buscarPecaPorId(Long id) {
        return pecaDAO.findById(id);
    }
    
    /**
     * Deletes a part.
     * @param id the part ID
     * @return true if deletion was successful
     */
    public boolean excluirPeca(Long id) {
        return pecaDAO.delete(id);
    }
    
    /**
     * Retrieves movement history for a part.
     * @param peca the part
     * @return list of movements for the part
     */
    public List<Movimentacao> obterHistoricoMovimentacao(Peca peca) {
        return movimentacaoDAO.findByPeca(peca);
    }
    
    /**
     * Retrieves movements within a date range.
     * @param dataInicio start date
     * @param dataFim end date
     * @return list of movements in the date range
     */
    public List<Movimentacao> obterMovimentacoesPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return movimentacaoDAO.findByPeriodo(dataInicio, dataFim);
    }
    
    /**
     * Retrieves recent movements.
     * @return list of recent movements
     */
    public List<Movimentacao> obterMovimentacaoRecente() {
        return movimentacaoDAO.findRecent();
    }
} 