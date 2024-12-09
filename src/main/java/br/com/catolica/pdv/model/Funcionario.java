package br.com.catolica.pdv.model;

import br.com.catolica.pdv.contract.IRelatorio;
import br.com.catolica.pdv.enums.NivelFuncionario;

public class Funcionario extends Pessoa implements IRelatorio {
    private NivelFuncionario nivelFuncionario;
    private String cargo;
    private double salario;
    private int horasTrabalhadas;
    private int vendasRealizadas;
    private boolean ativo;
    private String turno;

    public Funcionario(String nome,
                       String cpf,
                       NivelFuncionario nivelFuncionario,
                       String cargo, double salario, String turno
    ) {
        super(nome, cpf);
        try {
            if (nome == null || nome.isEmpty()) throw new IllegalArgumentException("O nome do funcionário não pode ser vazio.");
            if (cpf == null || cpf.isEmpty()) throw new IllegalArgumentException("O CPF do funcionário não pode ser vazio.");
            if (cargo == null || cargo.isEmpty()) throw new IllegalArgumentException("O cargo não pode ser vazio.");
            if (salario <= 0) throw new IllegalArgumentException("O salário deve ser maior que zero.");
            if (turno == null || turno.isEmpty()) throw new IllegalArgumentException("O turno não pode ser vazio.");

            this.nivelFuncionario = nivelFuncionario;
            this.cargo = cargo;
            this.salario = salario;
            this.horasTrabalhadas = 0;
            this.vendasRealizadas = 0;
            this.ativo = true;
            this.turno = turno;
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao criar funcionário: " + e.getMessage());
        }
    }

    public void registrarHoras(int horas) {
        try {
            if (horas <= 0) {
                throw new IllegalArgumentException("A quantidade de horas deve ser maior que zero.");
            }
            this.horasTrabalhadas += horas;
            System.out.println("Horas registradas com sucesso: " + horas);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao registrar horas: " + e.getMessage());
        }
    }

    public void registrarVenda() {
        try {
            if (!ativo) {
                throw new IllegalStateException("Funcionário inativo não pode registrar vendas.");
            }
            this.vendasRealizadas++;
            System.out.println("Venda registrada com sucesso.");
        } catch (IllegalStateException e) {
            System.err.println("Erro ao registrar venda: " + e.getMessage());
        }
    }

    public double calcularSalarioFinal() {
        try {
            if (!ativo) {
                throw new IllegalStateException("Funcionário inativo não pode calcular o salário.");
            }
            if (salario <= 0) {
                throw new IllegalStateException("O salário base do funcionário está incorreto.");
            }

            double valorHoraExtra = salario / 220;
            double comissaoPorVenda = 5.0;
            double salarioFinal = salario + (horasTrabalhadas * valorHoraExtra) + (vendasRealizadas * comissaoPorVenda);

            System.out.println("Salário final calculado: R$ " + salarioFinal);
            return salarioFinal;
        } catch (Exception e) {
            System.err.println("Erro ao calcular o salário final: " + e.getMessage());
            return 0;
        }
    }

    public void alterarStatus(boolean ativo) {
        try {
            if (this.ativo == ativo) {
                throw new IllegalStateException("O status do funcionário já está definido como " + (ativo ? "ativo" : "inativo") + ".");
            }
            this.ativo = ativo;
            String status = ativo ? "ativo" : "inativo";
            System.out.println("O funcionário agora está " + status + ".");
        } catch (IllegalStateException e) {
            System.err.println("Erro ao alterar o status do funcionário: " + e.getMessage());
        }
    }

    @Override
    public void imprimirRelatorio() {
        try {
            if (getNome() == null || getNome().isEmpty()) {
                throw new IllegalStateException("Nome do funcionário não está definido.");
            }
            System.out.println("\nRelatório do Funcionário:");
            System.out.println("Nome: " + this.getNome());
            System.out.println("CPF: " + this.getCpf());
            System.out.println("Cargo: " + this.cargo);
            System.out.println("Nível: " + this.nivelFuncionario);
            System.out.println("Salário Base: R$ " + this.salario);
            System.out.println("Horas Trabalhadas: " + this.horasTrabalhadas);
            System.out.println("Vendas Realizadas: " + this.vendasRealizadas);
            System.out.println("Status: " + (this.ativo ? "Ativo" : "Inativo"));
            System.out.println("Turno: " + this.turno);
        } catch (Exception e) {
            System.err.println("Erro ao imprimir o relatório do funcionário: " + e.getMessage());
        }
    }
}