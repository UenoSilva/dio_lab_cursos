/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dio.banco;

/**
 *
 * @author Ueno
 */
public class ContaPoupanca extends Conta{

    public ContaPoupanca(Cliente cliente){
        super(cliente);
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("===Extrato Conta Poupança===");
        super.imprimirInfosComuns();
    }

    @Override
    public String toString() {
        return "ContaPoupanca{" + super.toString() + '}';
    }
    
    
    
}
