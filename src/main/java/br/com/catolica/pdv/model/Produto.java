package br.com.catolica.pdv.model;

import br.com.catolica.pdv.enums.TipoProduto;

public abstract class Produto {
    protected String nome;
    protected double preco;
    protected TipoProduto tipoProduto;
    protected int codigo;
    protected String descricao;

    public Produto(String nome, double preco, TipoProduto tipoProduto, int codigo, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.tipoProduto = tipoProduto;
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public abstract double calcularPrecoFinal();

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }
}
