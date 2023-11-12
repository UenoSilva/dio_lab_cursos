package com.dio.academia.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.academia.entity.Aluno;
import com.dio.academia.entity.AvaliacaoFisica;
import com.dio.academia.entity.form.AvaliacaoFisicaForm;
import com.dio.academia.entity.form.AvaliacaoFisicaUpdateForm;
import com.dio.academia.repository.AlunoRepository;
import com.dio.academia.repository.AvalicaoFisicaRepository;
import com.dio.academia.service.AvaliacaoFisicaService;

@Service
public class AvaliacaoFisicaServiceImpl implements AvaliacaoFisicaService {

	@Autowired
	private AvalicaoFisicaRepository repository;
	
	@Autowired
	private AlunoRepository alunoRepository;

	@Override
	public AvaliacaoFisica create(AvaliacaoFisicaForm form) {
		AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();
		
		Aluno aluno = alunoRepository.findById(form.getAlunoId()).get();

		avaliacaoFisica.setAluno(aluno);
		avaliacaoFisica.setAltura(form.getAltura());
		avaliacaoFisica.setPeso(form.getPeso());
 
		return repository.save(avaliacaoFisica);
	}

	@Override
	public AvaliacaoFisica get(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public List<AvaliacaoFisica> getAll() {
		return repository.findAll();
	}

	@Override
	public AvaliacaoFisica update(Long id, AvaliacaoFisicaUpdateForm formUpadate) {
		AvaliacaoFisica avalicao = repository.findById(id).get();
		avalicao.setAltura(formUpadate.getAltura());
		avalicao.setPeso(formUpadate.getPeso());

		return repository.save(avalicao);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
