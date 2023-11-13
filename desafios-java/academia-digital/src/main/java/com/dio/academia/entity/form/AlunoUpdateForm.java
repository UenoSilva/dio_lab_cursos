package com.dio.academia.entity.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoUpdateForm {
	
	@NotBlank(message = "preecha o campo corretamente.")
	@Size(min = 3, max = 50, message = "'${validatedValue}' precisa está entre {min} e {max} caracteres.")
	private String nome;

	@NotBlank(message = "preecha o campo corretamente.")
	@Size(min = 3, max = 50, message = "'${validatedValue}' precisa está entre {min} e {max} caracteres.")
	private String bairro;

	@NotNull(message = "preecha o campo corretamente.")
	@Past(message = "Data '${validatedValue}' é inválida.")
	private LocalDate dataNascimento;
}
