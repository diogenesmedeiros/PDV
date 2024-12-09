package br.com.catolica.pdv.model;

import br.com.catolica.pdv.contract.ICalcular;
import br.com.catolica.pdv.contract.IRelatorio;
import br.com.catolica.pdv.contract.IVenda;
import br.com.catolica.pdv.enums.StatusVenda;
import br.com.catolica.pdv.enums.TipoPagamento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaImpl extends Venda implements IRelatorio, IVenda {
    private LocalDateTime dataVenda;
    private TipoPagamento metodoPagamento;
    private boolean notaFiscalEmitida;
    private double desconto;
    private double valorPago;
    private List<String> erros;

    public VendaImpl(Cliente cliente, Funcionario funcionario, Carrinho carrinho, TipoPagamento metodoPagamento) {
        super(cliente, funcionario, carrinho);
        try {
            if (metodoPagamento == null || metodoPagamento.equals("")) throw new IllegalArgumentException("O método de pagamento não pode ser vazio.");

            this.dataVenda = LocalDateTime.now();
            this.metodoPagamento = metodoPagamento;
            this.notaFiscalEmitida = false;
            this.desconto = 0.0;
            this.valorPago = 0.0;
            this.erros = new ArrayList<>();
        } catch (IllegalArgumentException e) {
            this.erros.add("Erro ao criar a venda: " + e.getMessage());
            System.err.println("Erro ao criar a venda: " + e.getMessage());
        }
    }

    @Override
    public void receberPagamento(double valor) {
        try {
            if (valor <= 0) throw new IllegalArgumentException("O valor pago deve ser maior que zero.");
            if (valor < this.total) throw new IllegalStateException("O valor pago é insuficiente para cobrir o total da venda.");

            this.valorPago = valor;
            this.statusVenda = StatusVenda.PAGA;
            System.out.println("Pagamento de R$ " + valor + " recebido com sucesso.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            this.erros.add("Erro ao receber pagamento: " + e.getMessage());
            System.err.println("Erro ao receber pagamento: " + e.getMessage());
        }
    }

    @Override
    public void realizarVenda() {
        try {
            if (carrinho == null || carrinho.getProdutos().isEmpty()) throw new IllegalStateException("O carrinho está vazio. Não é possível realizar a venda.");

            this.total = carrinho.calcularTotal() - this.desconto;
            this.statusVenda = StatusVenda.PAGA;
            this.dataVenda = LocalDateTime.now();

            PagamentoImpl pagamento = new PagamentoImpl(this.total, this.metodoPagamento, this.cliente.getNome());
            pagamento.processarPagamento();
            this.receberPagamento(pagamento.valor);

            System.out.println("Venda realizada com sucesso! Valor final: R$ " + this.total);
        } catch (IllegalStateException e) {
            this.erros.add("Erro ao realizar a venda: " + e.getMessage());
            System.err.println("Erro ao realizar a venda: " + e.getMessage());
        }
    }

    public void aplicarDesconto(double percentual) {
        try {
            if (percentual < 0 || percentual > 100) throw new IllegalArgumentException("O percentual de desconto deve estar entre 0 e 100.");
            this.desconto = (carrinho.calcularTotal() * percentual) / 100;
            System.out.println("Desconto de " + percentual + "% aplicado. Valor do desconto: R$ " + desconto);
        } catch (IllegalArgumentException e) {
            this.erros.add("Erro ao aplicar desconto: " + e.getMessage());
            System.err.println("Erro ao aplicar desconto: " + e.getMessage());
        }
    }

    @Override
    public void emitirNotaFiscal() {
        try {
            if (statusVenda != StatusVenda.REALIZADA && statusVenda != StatusVenda.PAGA)
                throw new IllegalStateException("A nota fiscal só pode ser emitida após a venda ser realizada ou paga.");

            this.notaFiscalEmitida = true;
            System.out.println("Nota fiscal emitida com sucesso.");

            System.out.println("Nota Fiscal - Detalhes da Venda:");
            System.out.println("Cliente: " + cliente.getNome());
            System.out.println("Funcionário: " + funcionario.getNome());
            System.out.println("Produtos:");

            for (Produto produto : carrinho.getProdutos()) {
                System.out.println("Produto: " + produto.getNome() + " | Preço: R$ " + produto.getPreco());
            }

            System.out.println("Total da Venda: R$ " + this.total);
        } catch (IllegalStateException e) {
            this.erros.add("Erro ao emitir nota fiscal: " + e.getMessage());
            System.err.println("Erro ao emitir nota fiscal: " + e.getMessage());
        }
    }

    @Override
    public void imprimirRelatorio() {
        try {
            System.out.println("\n===== RELATÓRIO DA VENDA =====");
            System.out.println("Data da Venda: " + this.dataVenda);
            System.out.println("Status da Venda: " + this.statusVenda);
            System.out.println("Total da Venda: R$ " + this.total);
            System.out.println("Desconto: R$ " + this.desconto);
            System.out.println("Pagamento recebido: R$ " + this.valorPago);
            System.out.println("Nota Fiscal Emitida: " + (this.notaFiscalEmitida ? "Sim" : "Não"));
            System.out.println("Método de Pagamento: " + this.metodoPagamento);
            System.out.println("==============================\n");
        } catch (Exception e) {
            this.erros.add("Erro ao imprimir o relatório: " + e.getMessage());
            System.err.println("Erro ao imprimir o relatório: " + e.getMessage());
        }
    }

    public double getValorPago() {
        return valorPago;
    }
}