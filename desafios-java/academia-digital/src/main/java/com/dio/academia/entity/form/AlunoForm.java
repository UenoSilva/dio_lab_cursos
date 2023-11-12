package com.dio.academia.entity.form;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoForm {
	
	private String nome;

	private String cpf;

	private String bairro;

	private LocalDate dataNascimento;
}
