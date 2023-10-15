package com.dio.banco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Ueno
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Banco banco = new Banco();
        banco.setBanco("Banco DIO");
        
        Cliente cliente = adicionarCliente("ueno");
        HashMap<String, List<Conta>> c = new HashMap<>();
        List<Conta> contas = Arrays.asList(new ContaCorrente(cliente), new ContaPoupanca(cliente));
       
        // Adiconar o cliente e as suas contas ao banco
        c.put(cliente.getNome(), contas);
        banco.setContas(c);
        
        // Realizar deposito na conta corrente do usuário 'ueno'
        banco.getContas().get("ueno").get(0).depositar(100);
        
        // Realizar deposito na conta corrente do usuário 'ueno'
        banco.getContas().get("ueno").get(1).depositar(150);
        
        // Realizar a impressão do extrato da conta poupança do usuário 'ueno'
        banco.getContas().get("ueno").get(1).imprimirExtrato();
        
        System.out.println(banco.toString());
        
    }
    
    public static Cliente adicionarCliente(String nomeCliente){
        Cliente cliente = new Cliente();
        cliente.setNome(nomeCliente);
        
        return cliente;
    }
    
}
