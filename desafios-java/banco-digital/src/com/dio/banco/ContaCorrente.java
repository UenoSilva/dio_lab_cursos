package com.dio.banco;

/**
 *
 * @author Ueno
 */
public class ContaCorrente extends Conta {

    public ContaCorrente(Cliente cliente) {
        super(cliente);
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("===Extrato conta corrente===");
        super.imprimirInfosComuns();
    }

    @Override
    public String toString() {
        return "ContaCorrente{" + super.toString() + '}';
    }

}
