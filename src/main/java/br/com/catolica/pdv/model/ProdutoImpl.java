package br.com.catolica.pdv.model;

import br.com.catolica.pdv.contract.IProduto;
import br.com.catolica.pdv.contract.IRelatorio;
import br.com.catolica.pdv.enums.TipoProduto;

import java.util.HashMap;
import java.util.Map;

public class ProdutoImpl extends Produto implements IRelatorio, IProduto {
    private int quantidadeEstoque;
    private double desconto;
    private String fornecedor;
    private String dataValidade;
    private boolean disponivel;
    private Map<Integer, Produto> produtos;

    public ProdutoImpl(String nome, double preco, TipoProduto tipoProduto, int codigo, String descricao, int quantidadeEstoque, String fornecedor, String dataValidade) {
        super(nome, preco, tipoProduto, codigo, descricao);
        try {
            if (preco <= 0) throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
            if (quantidadeEstoque < 0) throw new IllegalArgumentException("A quantidade de estoque não pode ser negativa.");
            if (fornecedor == null || fornecedor.isEmpty()) throw new IllegalArgumentException("O fornecedor não pode ser vazio.");

            this.quantidadeEstoque = quantidadeEstoque;
            this.desconto = 0.0;
            this.fornecedor = fornecedor;
            this.dataValidade = dataValidade;
            this.disponivel = quantidadeEstoque > 0;
            this.produtos = new HashMap<>();
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao criar o produto: " + e.getMessage());
        }
    }

    @Override
    public double calcularPrecoFinal() {
        double precoFinal = preco - desconto;
        if (precoFinal < 0) precoFinal = 0;
        return precoFinal;
    }

    @Override
    public void adicionarEstoque(int quantidade) {
        try {
            if (quantidade <= 0) throw new IllegalArgumentException("A quantidade adicionada deve ser maior que zero.");
            this.quantidadeEstoque += quantidade;
            this.disponivel = true;
            System.out.println("Estoque atualizado. Quantidade atual: " + quantidadeEstoque);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao adicionar estoque: " + e.getMessage());
        }
    }

    @Override
    public void removerEstoque(int quantidade) {
        try {
            if (quantidade <= 0) throw new IllegalArgumentException("A quantidade removida deve ser maior que zero.");
            if (quantidade > quantidadeEstoque) throw new IllegalStateException("Quantidade insuficiente no estoque.");

            this.quantidadeEstoque -= quantidade;
            this.disponivel = quantidadeEstoque > 0;
            System.out.println("Estoque atualizado. Quantidade atual: " + quantidadeEstoque);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Erro ao remover estoque: " + e.getMessage());
        }
    }

    public boolean verificarDisponibilidade() {
        return this.disponivel;
    }

    @Override
    public void imprimirRelatorio() {
        try {
            System.out.println("\n===== RELATÓRIO DO PRODUTO =====");
            System.out.println("Nome: " + this.getNome());
            System.out.println("Código: " + this.getCodigo());
            System.out.println("Descrição: " + this.getDescricao());
            System.out.println("Tipo: " + this.getTipoProduto());
            System.out.println("Fornecedor: " + this.fornecedor);
            System.out.println("Preço Original: R$ " + this.preco);
            System.out.println("Desconto: R$ " + this.desconto);
            System.out.println("Preço Final: R$ " + calcularPrecoFinal());
            System.out.println("Quantidade em Estoque: " + this.quantidadeEstoque);
            System.out.println("Data de Validade: " + this.dataValidade);
            System.out.println("Disponibilidade: " + (this.disponivel ? "Disponível" : "Indisponível"));
            System.out.println("==============================\n");
        } catch (Exception e) {
            System.err.println("Erro ao imprimir o relatório do produto: " + e.getMessage());
        }
    }
}