package com.estoque.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing stock movements for tracking inventory changes.
 * Records all stock additions and reductions with timestamps and reasons.
 */
@Entity
@Table(name = "movimentacoes")
public class Movimentacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "peca_id", nullable = false)
    private Peca peca;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;
    
    @Column(nullable = false)
    private Integer quantidade;
    
    @Column(length = 500)
    private String observacao;
    
    @Column(nullable = false)
    private LocalDateTime dataMovimentacao;
    
    @Column(length = 100)
    private String usuario;
    
    // Constructors
    public Movimentacao() {
        this.dataMovimentacao = LocalDateTime.now();
    }
    
    public Movimentacao(Peca peca, TipoMovimentacao tipo, Integer quantidade, String observacao, String usuario) {
        this();
        this.peca = peca;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.usuario = usuario;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Peca getPeca() {
        return peca;
    }
    
    public void setPeca(Peca peca) {
        this.peca = peca;
    }
    
    public TipoMovimentacao getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }
    
    public Integer getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    
    public String getObservacao() {
        return observacao;
    }
    
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }
    
    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s: %d unidades (%s)", 
                           peca.getNome(), tipo, quantidade, dataMovimentacao.toLocalDate());
    }
} 