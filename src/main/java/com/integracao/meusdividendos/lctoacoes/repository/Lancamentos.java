package com.integracao.meusdividendos.lctoacoes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.integracao.meusdividendos.lctoacoes.model.Lancamento;

public interface Lancamentos extends JpaRepository<Lancamento, Long>{

	//Consulta ID
	public Optional<Lancamento> findById(Long id);
	
	//Lista de Lancamentos filtrando (nomeAcao)
	public List<Lancamento> findByNomeAcaoContaining(String nomeAcao);
	
}
