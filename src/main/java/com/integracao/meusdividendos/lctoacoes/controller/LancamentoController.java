package com.integracao.meusdividendos.lctoacoes.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.integracao.meusdividendos.lctoacoes.model.Lancamento;
import com.integracao.meusdividendos.lctoacoes.model.Tipo_AcFi;
import com.integracao.meusdividendos.lctoacoes.service.CadastroLancamentosService;

@Controller
@RequestMapping("/lancamentos") // Define a raiz do controller evitando repeticao de codigo 
public class LancamentoController {
	
	@Autowired
	private CadastroLancamentosService lctoServ;
	
	@RequestMapping() //Define o acesso a pagina Raiz, ou seja ja abre pesquisando um titulo 
	public ModelAndView pesquisa() {
		//@ModelAttribute("filtro") = informa ao SPRING o nome do objeto relacionado na VIEW
		List<Lancamento> lst= lctoServ.buscarTodos();
		ModelAndView mv = new ModelAndView("PesquisaLancamentos"); //PesquisaLancamentos
		mv.addObject("listaLancamentos", lst);
		return mv;
	}
	
	
	@RequestMapping("/novo") //Define o acesso a pagina Raiz, ou seja ja abre pesquisando um titulo 
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("CadastroLancamento"); //PesquisaLancamentos
		mv.addObject(new Lancamento());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Lancamento lancamento, Errors errors) { 
		ModelAndView mv = new ModelAndView("CadastroLancamento"); //Criando objeto que retorna dados
		
		if(errors.hasErrors()) {
			return mv;
		}
		
		try {
			lctoServ.salvar(lancamento);
			mv.addObject("mensagem", "Lançamento Salvo com Sucesso!");
			mv.addObject(new Lancamento()); //Seta um novo objeto depois de SALVAR sem usar o Redirect.
			return mv;
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataCompraVenda", null, e.getMessage()); //objeto-errorCode-String(message)
			return mv;
		}
	}
	
	@ModelAttribute("tipos_acfi")
	public List<Tipo_AcFi> tipos_AcFi(){
		return Arrays.asList(Tipo_AcFi.values());
	}

}
