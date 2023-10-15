package com.dio.banco;

/**
 *
 * @author Ueno
 */
public interface ContaInterface {
    
    void sacar(double valor);
    
    void depositar(double valor);
    
    void transferir(double valor, ContaInterface conta);
    
    void imprimirExtrato();
}
