package br.com.catolica.pdv.model;

import br.com.catolica.pdv.contract.IRelatorio;
import br.com.catolica.pdv.enums.TipoPagamento;

import java.time.LocalDateTime;

public class PagamentoImpl extends Pagamento implements IRelatorio {
    private String idTransacao;
    private LocalDateTime dataPagamento;
    private String nomeCliente;
    private boolean confirmado;
    private double troco;

    public PagamentoImpl(double valor, TipoPagamento tipoPagamento, String nomeCliente) {
        super(valor, tipoPagamento);
        try {
            if (valor <= 0) throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero.");
            if (nomeCliente == null || nomeCliente.isEmpty()) throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");

            this.idTransacao = gerarIdTransacao();
            this.dataPagamento = LocalDateTime.now();
            this.nomeCliente = nomeCliente;
            this.confirmado = false;
            this.troco = 0.0;
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao criar o pagamento: " + e.getMessage());
        }
    }

    private String gerarIdTransacao() {
        return "TRX-" + System.currentTimeMillis();
    }

    @Override
    public void processarPagamento() {
        try {
            if (valor <= 0) throw new IllegalStateException("O valor do pagamento não pode ser zero ou negativo.");
            if (confirmado) throw new IllegalStateException("O pagamento já foi confirmado.");

            this.confirmado = true;
            System.out.println("Pagamento de R$ " + valor + " processado através de " + tipoPagamento);
        } catch (IllegalStateException e) {
            System.err.println("Erro ao processar o pagamento: " + e.getMessage());
        }
    }

    public void cancelarPagamento() {
        try {
            if (confirmado) throw new IllegalStateException("O pagamento já foi confirmado e não pode ser cancelado.");
            System.out.println("Pagamento cancelado com sucesso.");
        } catch (IllegalStateException e) {
            System.err.println("Erro ao cancelar o pagamento: " + e.getMessage());
        }
    }

    public void calcularTroco(double valorPago) {
        try {
            if (valorPago < valor) throw new IllegalArgumentException("O valor pago é insuficiente.");
            this.troco = valorPago - valor;
            System.out.println("Troco calculado: R$ " + troco);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao calcular o troco: " + e.getMessage());
        }
    }

    @Override
    public void imprimirRelatorio() {
        try {
            if (!confirmado) throw new IllegalStateException("Não é possível imprimir um recibo de pagamento que ainda não foi confirmado.");
            System.out.println("\n===== RECIBO DE PAGAMENTO =====");
            System.out.println("ID da Transação: " + idTransacao);
            System.out.println("Data do Pagamento: " + dataPagamento);
            System.out.println("Nome do Cliente: " + nomeCliente);
            System.out.println("Valor: R$ " + valor);
            System.out.println("Tipo de Pagamento: " + tipoPagamento);
            System.out.println("Troco: R$ " + troco);
            System.out.println("Status: " + (confirmado ? "Confirmado" : "Não Confirmado"));
            System.out.println("==============================\n");
        } catch (IllegalStateException e) {
            System.err.println("Erro ao imprimir o recibo: " + e.getMessage());
        }
    }

    public boolean verificarStatusPagamento() {
        return this.confirmado;
    }
}