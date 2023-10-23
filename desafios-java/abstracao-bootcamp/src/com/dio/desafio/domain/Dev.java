package com.dio.desafio.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Ueno
 */
public class Dev {

    private String nome;
    private Set<Conteudo> conteudosInscritos = new HashSet<>();
    private Set<Conteudo> conteudosFinalizados = new HashSet<>();

    public void inscreverBootcamp(Bootcamp bootcamp) {
        this.conteudosInscritos.addAll(bootcamp.getConteudos());
        bootcamp.getDevs().add(this);
    }

    public void progredir() {
        Optional<Conteudo> conteudo = conteudosInscritos.stream().findFirst();
        if (conteudo.isPresent()) {
            conteudosFinalizados.add(conteudo.get());
            conteudosInscritos.remove(conteudo.get());
        } else {
            System.err.println("Voce nao esta inscrito em nenhum conteudo");
        }
    }

    public double calcularXp() {
        Iterator<Conteudo> iterator = conteudosInscritos.iterator();
        double soma = 0;
        while (iterator.hasNext()) {
            double next = iterator.next().calcularXp();
            soma += next;
        }
        return soma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Conteudo> getConteudosInscritos() {
        return conteudosInscritos;
    }

    public void setConteudosInscritos(Set<Conteudo> conteudosInscritos) {
        this.conteudosInscritos = conteudosInscritos;
    }

    public Set<Conteudo> getConteudosFinalizados() {
        return conteudosFinalizados;
    }

    public void setConteudosFinalizados(Set<Conteudo> conteudosFinalizados) {
        this.conteudosFinalizados = conteudosFinalizados;
    }

}
