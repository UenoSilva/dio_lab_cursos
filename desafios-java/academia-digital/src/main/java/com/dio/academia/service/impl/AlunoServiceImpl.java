package com.dio.academia.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.academia.entity.Aluno;
import com.dio.academia.entity.form.AlunoForm;
import com.dio.academia.entity.form.AlunoUpdateForm;
import com.dio.academia.repository.AlunoRepository;
import com.dio.academia.service.AlunoService;

@Service
public class AlunoServiceImpl implements AlunoService {

	@Autowired
	private AlunoRepository repository;

	@Override
	public Aluno create(AlunoForm form) {
		Aluno aluno = new Aluno();
		aluno.setNome(form.getNome());
		aluno.setBairro(form.getBairro());
		aluno.setCpf(form.getCpf());
		aluno.setDataNascimento(form.getDataNascimento());
		
		return repository.save(aluno);
	}

	@Override
	public Aluno get(Long id) {
		return repository.findById(id).orElseThrow(NoSuchElementException::new);
	}

	@Override
	public List<Aluno> getAll() {
		return repository.findAll();
	}

	@Override
	public Aluno update(Long id, AlunoUpdateForm formUpadte) {
		
		Aluno aluno = repository.findById(id).get();
		aluno.setNome(formUpadte.getNome());
		aluno.setBairro(formUpadte.getBairro());
		aluno.setDataNascimento(formUpadte.getDataNascimento());
		
		return repository.save(aluno);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
