package com.dio.desafio.domain;

/**
 *
 * @author Ueno
 */
public class Curso extends Conteudo {

    private int cargaHoraria;

    public Curso() {
        super();
    }

    @Override
    public double calcularXp() {
        return XP_PADRAO * cargaHoraria;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public String toString() {
        return "Curso{" 
                + "titulo=" + getTitulo() + "\n"
                + "descricao=" + getDescricao() + "\n"
                + "carga horaria=" + cargaHoraria 
                + '}';
    }

}
