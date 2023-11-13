package com.dio.academia.entity.form;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoFisicaUpdateForm {
	
	@NotNull(message = "preecha o campo corretamente.")
	@Positive(message = "'${validatedValue}' precisa ser positivo.")
	private double peso;

	@NotNull(message = "preecha o campo corretamente.")
	@Positive(message = "'${validatedValue}' precisa ser positivo.")
	@DecimalMin(value = "150", message = "'${validatedValue}' precisa ser no minimo {value}")
	private double altura;
}
