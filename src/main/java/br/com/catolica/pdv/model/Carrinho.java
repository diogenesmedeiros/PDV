package br.com.catolica.pdv.model;

import br.com.catolica.pdv.contract.ICalcular;
import br.com.catolica.pdv.contract.IRelatorio;

import java.util.ArrayList;
import java.util.List;

public class Carrinho implements ICalcular, IRelatorio {
    private List<Produto> produtos;
    private double total;
    private int quantidadeProdutos;
    private boolean fechado;
    private double desconto;
    private String cliente;

    public Carrinho(String cliente) {
        this.produtos = new ArrayList<>();
        this.total = 0.0;
        this.quantidadeProdutos = 0;
        this.fechado = false;
        this.desconto = 0.0;
        this.cliente = cliente;
    }

    @Override
    public double calcularTotal() {
        double total = 0;
        for (Produto produto : produtos) {
            total += produto.calcularPrecoFinal();
        }
        return total;
    }

    public void adicionarProduto(Produto produto) {
        try {
            if (fechado) {
                throw new IllegalStateException("Não é possível adicionar produtos a um carrinho fechado.");
            }
            if (produto == null) {
                throw new IllegalArgumentException("Produto não pode ser nulo.");
            }
            produtos.add(produto);
            quantidadeProdutos++;
            total += produto.calcularPrecoFinal();
            System.out.println("Produto adicionado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    public void removerProduto(Produto produto) {
        try {
            if (fechado) {
                throw new IllegalStateException("Não é possível remover produtos de um carrinho fechado.");
            }
            if (!produtos.contains(produto)) {
                throw new IllegalArgumentException("Produto não encontrado no carrinho.");
            }
            produtos.remove(produto);
            quantidadeProdutos--;
            total -= produto.calcularPrecoFinal();
            System.out.println("Produto removido com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao remover produto: " + e.getMessage());
        }
    }

    @Override
    public void aplicarDesconto(double desconto) {
        try {
            if (desconto < 0 || desconto > total) {
                throw new IllegalArgumentException("O desconto deve ser um valor positivo e menor ou igual ao total.");
            }
            this.desconto = desconto;
            this.total -= desconto;
            System.out.println("Desconto de R$ " + desconto + " aplicado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao aplicar desconto: " + e.getMessage());
        }
    }

    public void finalizarCarrinho() {
        try {
            if (produtos.isEmpty()) {
                throw new IllegalStateException("Não é possível finalizar um carrinho vazio.");
            }
            if (fechado) {
                throw new IllegalStateException("O carrinho já foi finalizado.");
            }
            fechado = true;
            System.out.println("Carrinho finalizado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao finalizar carrinho: " + e.getMessage());
        }
    }

    @Override
    public void imprimirRelatorio() {
        try {
            System.out.println("========== Relatório do Carrinho ==========");
            System.out.println("Cliente: " + cliente);
            System.out.println("Produtos no carrinho:");
            for (Produto produto : produtos) {
                System.out.println(" - " + produto.getNome() + " | Preço: R$ " + produto.calcularPrecoFinal());
            }
            System.out.println("Quantidade de Produtos: " + quantidadeProdutos);
            System.out.println("Desconto Aplicado: R$ " + desconto);
            System.out.println("Total Final: R$ " + total);
            System.out.println("Status do Carrinho: " + (fechado ? "Fechado" : "Aberto"));
        } catch (Exception e) {
            System.err.println("Erro ao imprimir relatório do carrinho: " + e.getMessage());
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}