package com.dio.academia.service;

import java.util.List;

import com.dio.academia.entity.Matricula;
import com.dio.academia.entity.form.MatriculaForm;

public interface MatriculaService {

	// Criar uma nova matricula
	Matricula create(MatriculaForm form);
	
	// retorna uma matricula com base no id passado
	Matricula get(Long id);
	
	// retorna todas as matriculas
	List<Matricula> getAll();
	
	// deleta uma matricula pelo id
	void delete(Long id);
}
