package com.dio.academia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dio.academia.entity.Aluno;
import java.time.LocalDate;


@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{

	// Retorna uma lista de alunos com a data de nascimento correspondente
	List<Aluno> findByDataNascimento(LocalDate dataNascimento);
}
