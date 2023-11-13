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

import com.dio.academia.entity.AvaliacaoFisica;
import com.dio.academia.entity.form.AvaliacaoFisicaForm;
import com.dio.academia.entity.form.AvaliacaoFisicaUpdateForm;
import com.dio.academia.service.impl.AvaliacaoFisicaServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoFisicaController {
	
	@Autowired
	private AvaliacaoFisicaServiceImpl service;
	
	@GetMapping
	public List<AvaliacaoFisica> getAll(){
		return service.getAll();
	}
	
	@PostMapping
	public AvaliacaoFisica create(@Valid @RequestBody AvaliacaoFisicaForm form) {
		return service.create(form);
	}
	
	@GetMapping("/{id}")
	public AvaliacaoFisica ge(@PathVariable Long id) {
		return service.get(id);
	}
	
	@PutMapping("/{id}")
	public AvaliacaoFisica update(@PathVariable Long id, @Valid @RequestBody AvaliacaoFisicaUpdateForm form) {
		return service.update(id, form);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
