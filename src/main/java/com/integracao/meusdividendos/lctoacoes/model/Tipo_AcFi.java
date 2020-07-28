package com.integracao.meusdividendos.lctoacoes.model;

public enum Tipo_AcFi {

	ACAO("Ação"), FII("FII");
	
	private String descricao;

	private Tipo_AcFi(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
