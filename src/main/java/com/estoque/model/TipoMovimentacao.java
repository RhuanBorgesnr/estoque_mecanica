package com.estoque.model;

/**
 * Enumeration defining the types of stock movements.
 * Used to categorize inventory transactions.
 */
public enum TipoMovimentacao {
    ENTRADA("Entrada"),
    SAIDA("Saída"),
    AJUSTE("Ajuste"),
    PERDA("Perda"),
    DEVOLUCAO("Devolução");
    
    private final String descricao;
    
    TipoMovimentacao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
} 