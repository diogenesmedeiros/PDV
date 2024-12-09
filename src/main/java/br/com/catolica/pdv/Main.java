package br.com.catolica.pdv;

import br.com.catolica.pdv.enums.TipoProduto;
import br.com.catolica.pdv.enums.TipoPagamento;
import br.com.catolica.pdv.model.*;
import br.com.catolica.pdv.model.Produto;

public class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente(
                "João da Silva",
                "12345678900",
                "Rua tal",
                "40028922",
                "example@softknight.com"
        );
        Funcionario funcionario = new Funcionario(
                "Carlos Pereira",
                "12345678900",
                null,
                "Vendedor",
                1500.00,
                "Tarde"
        );
        Carrinho carrinho = new Carrinho(cliente.getNome());

        Produto produto1 = new ProdutoImpl(
                "Arroz",
                10.0,
                TipoProduto.ALIMENTO,
                1,
                "Arroz branco, pacote de 5kg",
                100,
                "Atacadao",
                "15/05/2025"
        );
        Produto produto2 = new ProdutoImpl(
                "Feijão",
                8.0,
                TipoProduto.ALIMENTO,
                1,
                "Feijão preto, pacote de 1kg",
                100,
                "Atacadao",
                "15/05/2025"
        );

        carrinho.adicionarProduto(produto1);
        carrinho.adicionarProduto(produto2);

        VendaImpl venda = new VendaImpl(cliente, funcionario, carrinho, TipoPagamento.PIX);
        venda.aplicarDesconto(15);
        venda.realizarVenda();
        cliente.adicionarSaldo(1500);
        cliente.realizarCompra(venda.getValorPago());

        System.out.println("Venda realizada com sucesso!");
        venda.emitirNotaFiscal();
        funcionario.registrarVenda();

        funcionario.registrarHoras(8);
        funcionario.imprimirRelatorio();
        funcionario.calcularSalarioFinal();

        venda.imprimirRelatorioVenda();
    }
}