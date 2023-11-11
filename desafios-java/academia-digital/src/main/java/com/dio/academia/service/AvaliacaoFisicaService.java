package com.dio.academia.service;

import java.util.List;

import com.dio.academia.entity.AvaliacaoFisica;

public interface AvaliacaoFisicaService {

	// criar uma avalição física e retorna a avalição física criada
	AvaliacaoFisica create(AvaliacaoFisicaForm avaliacaoFisica);
	
	// buscar uma avalição física pelo seu id
	AvaliacaoFisica get(Long id);
	
	// retorna todas as avalições físicas
	List<AvaliacaoFisica> getAll();
	
	// Alterar informações da avaliação fíca, utilizando o id para buscar a 
	// avalição física e novas informações de avaliação físca
	AvaliacaoFisica update(Long id, AvaliacaoFisicaUpdateForm formUpadate);
	
	// deleta uma avaliação físca pelo seu id
	void delete(Long id);
}
