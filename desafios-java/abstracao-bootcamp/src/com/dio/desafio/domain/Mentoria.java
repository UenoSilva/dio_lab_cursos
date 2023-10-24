package com.dio.desafio.domain;

import java.time.LocalDate;

/**
 *
 * @author Ueno
 */
public class Mentoria extends Conteudo {

    private LocalDate data;

    public Mentoria() {
        super();
    }

    @Override
    public double calcularXp() {
        return XP_PADRAO + 20d;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Mentoria{" 
                + "titulo=" + getTitulo() + "\n"
                + "descricao=" + getDescricao() + "\n"
                + "data=" + data + 
                '}';
    }

}
