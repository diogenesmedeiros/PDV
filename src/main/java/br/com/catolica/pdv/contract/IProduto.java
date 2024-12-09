package br.com.catolica.pdv.contract;

public interface IProduto {
    void adicionarEstoque(int quantidade);
    void removerEstoque(int quantidade);
}
