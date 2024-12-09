package br.com.catolica.pdv.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    private String endereco;
    private String telefone;
    private String email;
    private List<String> historicoCompras;
    private double saldo;
    private boolean statusAtivo;

    public Cliente(String nome, String cpf, String endereco, String telefone, String email) {
        super(nome, cpf);
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.historicoCompras = new ArrayList<>();
        this.saldo = 0.0;
        this.statusAtivo = true;
    }

    public void atualizarEndereco(String novoEndereco) {
        try {
            if (novoEndereco == null || novoEndereco.isEmpty()) {
                throw new IllegalArgumentException("O endereço não pode ser vazio ou nulo.");
            }
            this.endereco = novoEndereco;
            System.out.println("Endereço atualizado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar endereço: " + e.getMessage());
        }
    }

    public void adicionarSaldo(double valor) {
        try {
            if (valor <= 0) {
                throw new IllegalArgumentException("O valor para adicionar saldo deve ser positivo.");
            }
            this.saldo += valor;
            System.out.println("Saldo de R$ " + valor + " adicionado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao adicionar saldo: " + e.getMessage());
        }
    }

    public void registrarCompra(String descricao) {
        try {
            if (!statusAtivo) {
                throw new IllegalStateException("Cliente inativo não pode registrar compras.");
            }
            if (descricao == null || descricao.isEmpty()) {
                throw new IllegalArgumentException("A descrição da compra não pode ser vazia.");
            }
            historicoCompras.add(descricao);
            System.out.println("Compra registrada com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao registrar compra: " + e.getMessage());
        }
    }

    public void realizarCompra(double valor) {
        try {
            if (!statusAtivo) {
                throw new IllegalStateException("Cliente inativo não pode realizar compras.");
            }
            if (valor <= 0) {
                throw new IllegalArgumentException("O valor da compra deve ser positivo.");
            }
            if (valor > saldo) {
                throw new IllegalArgumentException("Saldo insuficiente para realizar a compra.");
            }
            saldo -= valor;
            registrarCompra("Compra no valor de R$ " + valor);
            System.out.println("Compra de R$ " + valor + " realizada com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao realizar compra: " + e.getMessage());
        }
    }

    public void alterarStatus(boolean ativo) {
        try {
            if (statusAtivo == ativo) {
                throw new IllegalStateException("O status do cliente já está definido como " + (ativo ? "ativo" : "inativo") + ".");
            }
            statusAtivo = ativo;
            System.out.println("Status do cliente alterado para " + (ativo ? "Ativo" : "Inativo") + " com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao alterar status do cliente: " + e.getMessage());
        }
    }
}