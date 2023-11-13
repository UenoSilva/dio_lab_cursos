package com.dio.academia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dio.academia.entity.Matricula;
import com.dio.academia.entity.form.MatriculaForm;
import com.dio.academia.service.impl.MatriculaServiceImpl;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
	
	@Autowired
	private MatriculaServiceImpl service;
	
	@GetMapping
	public List<Matricula> getAll(){
		return service.getAll();
	}
	
	@PostMapping
	public Matricula create(@RequestBody MatriculaForm form) {
		return service.create(form);
	}
	
	@GetMapping("/{id}")
	public Matricula get(@PathVariable Long id) {
		return service.get(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
