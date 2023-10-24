package com.dio.desafio;

import com.dio.desafio.domain.Bootcamp;
import com.dio.desafio.domain.Curso;
import com.dio.desafio.domain.Dev;
import com.dio.desafio.domain.Mentoria;
import java.time.LocalDate;

/**
 *
 * @author Ueno
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Curso c1 = new Curso();
        c1.setTitulo("Java");
        c1.setDescricao("Curso Java");
        c1.setCargaHoraria(8);

        Mentoria mentoria = new Mentoria();
        mentoria.setTitulo("mentoria de java");
        mentoria.setDescricao("mentoria java");
        mentoria.setData(LocalDate.now());

        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setNome("Bootcamp Java Developer");
        bootcamp.setDescricao("Descrição Bootcamp Java Developer");
        bootcamp.getConteudos().add(c1);
        bootcamp.getConteudos().add(mentoria);

        Dev ueno = new Dev();
        ueno.setNome("Ueno");
        ueno.inscreverBootcamp(bootcamp);

        System.out.println("Conteúdos inscritos" + ueno.getConteudosInscritos());

        ueno.progredir();
        ueno.progredir();

        System.out.println("Conteúdos inscritos" + ueno.getConteudosInscritos());
        System.out.println("Conteúdos inscritos" + ueno.getConteudosFinalizados());
        System.out.println("Xp adiquirido " + ueno.calcularXp());
        
    }

}
