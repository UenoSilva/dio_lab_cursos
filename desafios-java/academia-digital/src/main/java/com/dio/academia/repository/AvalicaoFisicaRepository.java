package com.dio.academia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dio.academia.entity.AvaliacaoFisica;

@Repository
public interface AvalicaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Long> {

}
