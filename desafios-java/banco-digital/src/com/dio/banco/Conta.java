package com.dio.banco;

/**
 *
 * @author Ueno
 */
public abstract class Conta implements ContaInterface {

    private static final int AGENCIA_PADRAO = 1;
    private static int SEQUENCIA = 1;

    private int agencia;
    private int numero;
    private double saldo;
    private Cliente cliente;

    public Conta(Cliente cliente) {
        this.agencia = AGENCIA_PADRAO;
        this.numero = SEQUENCIA++;
        this.cliente = cliente;
    }

    @Override
    public void sacar(double valor) {
        saldo -= valor;
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
    }

    @Override
    public void transferir(double valor, ContaInterface conta) {
        sacar(valor);
        conta.depositar(valor);
    }

    public int getAgencia() {
        return agencia;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void imprimirInfosComuns() {
        System.out.printf("%s%s%n%s%d%n%s%d%n",
                "Titular: ", cliente.getNome(),
                "Agencia: ", agencia,
                "Numero: ", numero,
                "Saldo: ", saldo);
    }

    @Override
    public String toString() {
        return "agencia=" + agencia + ", numero=" + numero + ", saldo=" + saldo ;
    }

}
