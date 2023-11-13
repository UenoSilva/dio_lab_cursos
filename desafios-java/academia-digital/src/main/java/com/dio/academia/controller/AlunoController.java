package com.dio.academia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dio.academia.entity.Aluno;
import com.dio.academia.entity.AvaliacaoFisica;
import com.dio.academia.entity.form.AlunoForm;
import com.dio.academia.entity.form.AlunoUpdateForm;
import com.dio.academia.service.impl.AlunoServiceImpl;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

	@Autowired
	private AlunoServiceImpl service;

	@GetMapping
	public List<Aluno> getAll() {
		return service.getAll();
	}

	@PostMapping
	public Aluno create(@RequestBody AlunoForm form) {
		return service.create(form);
	}

	@GetMapping("/{id}")
	public Aluno get(@PathVariable Long id) {
		return service.get(id);
	}
	
	@GetMapping("/avaliacoes/{id}")
	public List<AvaliacaoFisica> getAllAvalicaoFisicaId(@PathVariable Long id){
		return service.getAllAvalicaoFisicaId(id);
	}

	@PutMapping("/{id}")
	public Aluno update(@PathVariable Long id, @RequestBody AlunoUpdateForm aluno) {
		return service.update(id, aluno);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

}
