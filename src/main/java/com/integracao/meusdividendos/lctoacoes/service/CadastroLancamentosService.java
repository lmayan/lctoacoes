package com.integracao.meusdividendos.lctoacoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.integracao.meusdividendos.lctoacoes.model.Lancamento;
import com.integracao.meusdividendos.lctoacoes.repository.Lancamentos;

@Service
public class CadastroLancamentosService {

	@Autowired
	private Lancamentos lctos;
	
	// SALVAR
	public void salvar(Lancamento lancamento) {
		try {
			lctos.save(lancamento);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido.");
		}
	}
	
	public void deletar(Long id) {
		lctos.deleteById(id);
	}
	
	public List<Lancamento> buscarTodos(){
		List<Lancamento> lst = lctos.findAll();
		return lst;
	}
	
}
