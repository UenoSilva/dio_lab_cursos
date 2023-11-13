package com.dio.academia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.academia.entity.Aluno;
import com.dio.academia.entity.Matricula;
import com.dio.academia.entity.form.MatriculaForm;
import com.dio.academia.repository.AlunoRepository;
import com.dio.academia.repository.MatriculaRepository;
import com.dio.academia.service.MatriculaService;

@Service
public class MatriculaServiceImpl implements MatriculaService{
	
	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Autowired
	private AlunoRepository alunoRepository;

	@Override
	public Matricula create(MatriculaForm form) {
		Matricula matricula = new Matricula();
		Aluno aluno = alunoRepository.findById(form.getAlunoId()).get();
		
		matricula.setAluno(aluno);
		
		return matriculaRepository.save(matricula);
	}

	@Override
	public Matricula get(Long id) {
		return matriculaRepository.findById(id).get();
	}

	@Override
	public List<Matricula> getAll() {
		return matriculaRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		matriculaRepository.deleteById(id);
	}

}
