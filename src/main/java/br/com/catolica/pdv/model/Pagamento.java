package br.com.catolica.pdv.model;

import br.com.catolica.pdv.enums.TipoPagamento;

public abstract class Pagamento {
    protected double valor;
    protected TipoPagamento tipoPagamento;

    public Pagamento(double valor, TipoPagamento tipoPagamento) {
        this.valor = valor;
        this.tipoPagamento = tipoPagamento;
    }

    public abstract void processarPagamento();
}
