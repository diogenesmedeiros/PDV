package br.com.catolica.pdv.model;

import br.com.catolica.pdv.enums.StatusVenda;

public abstract class Venda {
    protected Cliente cliente;
    protected Funcionario funcionario;
    protected StatusVenda statusVenda;
    protected Carrinho carrinho;
    protected double total;

    public Venda(Cliente cliente, Funcionario funcionario, Carrinho carrinho) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.carrinho = carrinho;
        this.statusVenda = StatusVenda.PENDENTE;
        this.total = 0.0;
    }

    public abstract void realizarVenda();
}
