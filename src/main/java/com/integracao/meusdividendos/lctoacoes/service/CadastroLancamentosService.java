package com.integracao.meusdividendos.lctoacoes.service;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

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
	
	// EDITAR
	public Optional<Lancamento> editar(Long codigo) {
		return lctos.findById(codigo);
	}
	
	// DELETAR
	public void deletar(Long id) {
		lctos.deleteById(id);
	}
	
	public List<Lancamento> buscarTodos(){
		List<Lancamento> lst = lctos.findAll();
		return lst;
	}
	
	// CRIAR CSV
	public void criar_CSV(List<Lancamento> lstCriarCsv) {
		
		try {
			// Cria Arquivo CSV que sera preenchido.
			PrintWriter pw = new PrintWriter(new File("C:\\Users\\Lucas Mayan\\Documents\\ExportCSV\\export_lctoacoes.csv"));
			
			// Adiciona as modificacoes/objetos ao Arquivo criado.
			StringBuilder sb = new StringBuilder();
			
			// Nomeando as colunas do Objeto(Lancamento).
			sb.append("Codigo");
			sb.append(",");
			sb.append("Data");
			sb.append(",");
			sb.append("Valor");
			sb.append(",");
			sb.append("Quantidade");
			sb.append(",");
			sb.append("Tipo");
			sb.append(",");
			sb.append("Despesas");
			sb.append("\r\n");
			
			// Criando formatador de Data
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			
			// Criar condicao para cada lcto da lista de lancamentos, pegue os dados e insira no arquivo CSV
			for (Lancamento lcto : lstCriarCsv) {
				sb.append(lcto.getNomeAcao());
				sb.append(",");
				sb.append(formatador.format(lcto.getDataCompraVenda()));
				sb.append(",");
				sb.append("\"");
				sb.append(lcto.getVlrUnit().toString().replace(".", ","));
				sb.append("\"");
				sb.append(",");
				sb.append(lcto.getQtdade());
				sb.append(",");
				sb.append(lcto.getTipo_vc());
				sb.append(",");
				sb.append("\"");
				sb.append(lcto.getVlrDesp().toString().replace(".", ","));
				if(lcto.getVlrDesp() == null) {
					lcto.setVlrDesp(new BigDecimal(0));
				}
				sb.append("\"");
				sb.append("\r\n");
			}
			// Teste Arquivo CSV
			System.out.println("Lancamento em CSV: "+sb.toString());
			
			// Escreve o StringBuilder no aqruivo.
			pw.write(sb.toString());
			
			// Fecha a operacao de Escrita do PrintWriter
			pw.close();
			
			System.out.println("***** CRIADO ARQUIVO (CSV) *****");

			
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println("***** Erro ao gerar arquivo CSV *****");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
