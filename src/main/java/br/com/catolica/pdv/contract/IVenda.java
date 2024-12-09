package br.com.catolica.pdv.contract;

public interface IVenda {
    void receberPagamento(double valor);
    void emitirNotaFiscal();
}