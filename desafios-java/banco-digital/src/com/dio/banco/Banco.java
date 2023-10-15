/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dio.banco;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Ueno
 */
public class Banco {

    private String banco;
    private Map<String, List<Conta>> contas;

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Map<String, List<Conta>> getContas() {
        return contas;
    }

    public void setContas(Map<String, List<Conta>> contas) {
        this.contas = contas;
    }

    @Override
    public String toString() {
        return banco + "\ncontas=" + contas;
    }

    

}
