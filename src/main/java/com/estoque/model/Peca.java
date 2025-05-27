package com.estoque.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a mechanical part in the inventory system.
 * Stores information about parts including quantity, location, and usage tracking.
 */
@Entity
@Table(name = "pecas")
public class Peca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(length = 500)
    private String descricao;
    
    @Column(nullable = false)
    private Integer quantidadeEstoque;
    
    @Column(nullable = false)
    private Integer quantidadeMinima;
    
    @Column(length = 50)
    private String localizacao;
    
    @Column
    private Double precoUnitario;
    
    @Column(length = 50)
    private String fornecedor;
    
    @Column
    private LocalDateTime dataCadastro;
    
    @Column
    private LocalDateTime dataUltimaMovimentacao;
    
    // Constructors
    public Peca() {
        this.dataCadastro = LocalDateTime.now();
        this.dataUltimaMovimentacao = LocalDateTime.now();
    }
    
    public Peca(String nome, String descricao, Integer quantidadeEstoque, 
                Integer quantidadeMinima, String localizacao, Double precoUnitario, String fornecedor) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeEstoque = quantidadeEstoque;
        this.quantidadeMinima = quantidadeMinima;
        this.localizacao = localizacao;
        this.precoUnitario = precoUnitario;
        this.fornecedor = fornecedor;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
        this.dataUltimaMovimentacao = LocalDateTime.now();
    }
    
    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }
    
    public void setQuantidadeMinima(Integer quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }
    
    public String getLocalizacao() {
        return localizacao;
    }
    
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
    
    public Double getPrecoUnitario() {
        return precoUnitario;
    }
    
    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
    
    public String getFornecedor() {
        return fornecedor;
    }
    
    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
    
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    public LocalDateTime getDataUltimaMovimentacao() {
        return dataUltimaMovimentacao;
    }
    
    public void setDataUltimaMovimentacao(LocalDateTime dataUltimaMovimentacao) {
        this.dataUltimaMovimentacao = dataUltimaMovimentacao;
    }
    
    /**
     * Checks if the part is below minimum stock level.
     * @return true if current stock is below minimum threshold
     */
    public boolean isEstoqueBaixo() {
        return quantidadeEstoque <= quantidadeMinima;
    }
    
    /**
     * Reduces stock quantity by the specified amount.
     * @param quantidade amount to reduce from stock
     * @return true if operation was successful, false if insufficient stock
     */
    public boolean darBaixa(Integer quantidade) {
        if (quantidade <= 0 || quantidade > quantidadeEstoque) {
            return false;
        }
        this.quantidadeEstoque -= quantidade;
        this.dataUltimaMovimentacao = LocalDateTime.now();
        return true;
    }
    
    /**
     * Adds stock quantity by the specified amount.
     * @param quantidade amount to add to stock
     */
    public void adicionarEstoque(Integer quantidade) {
        if (quantidade > 0) {
            this.quantidadeEstoque += quantidade;
            this.dataUltimaMovimentacao = LocalDateTime.now();
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s (Estoque: %d)", nome, quantidadeEstoque);
    }
} 