package com.dio.academia.service;

import java.util.List;

import com.dio.academia.entity.Aluno;

public interface AlunoService {

	// Criar um novo aluno
	Aluno create(AlunoForm aluno);
	
	// retorna uma aluno com base no id passado
	Aluno get(Long id);
	
	// retorna todos os alunos
	List<Aluno> getAll();
	
	// atualizar as informaçãoes do aluno
	Aluno update(Long id, AlunoUpadateForm formUpadte);
	
	// deletar um aluno pelo seu id
	void delete(Long id);
}
