package com.integracao.meusdividendos.lctoacoes.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.integracao.meusdividendos.lctoacoes.model.Lancamento;
import com.integracao.meusdividendos.lctoacoes.model.Tipo_AcFi;
import com.integracao.meusdividendos.lctoacoes.service.CadastroLancamentosService;

@Controller
@RequestMapping("/lancamentos") // Define a raiz do controller evitando repeticao de codigo
public class LancamentoController {

	@Autowired
	private CadastroLancamentosService lctoServ;
	
	private List<Lancamento> lstModal = new ArrayList<>();
	private int idAdd = 0;
	
	@RequestMapping() // Define o acesso a pagina Raiz, ou seja ja abre pesquisando um titulo
	public ModelAndView pesquisa() {
		// @ModelAttribute("filtro") = informa ao SPRING o nome do objeto relacionado na VIEW
		List<Lancamento> lst = lctoServ.buscarTodos();
		ModelAndView mv = new ModelAndView("PesquisaLancamentos"); // PesquisaLancamentos
		mv.addObject("listaLancamentos", lst);
		return mv;
	}
	
	@RequestMapping("/modal") // Define o acesso a pagina Raiz, ou seja ja abre pesquisando um titulo
	public ModelAndView pesquisaModal() {
		// @ModelAttribute("filtro") = informa ao SPRING o nome do objeto relacionado na VIEW
		//List<Lancamento> lstModal = new ArrayList<Lancamento>(); //Lista que recebera lancamentos MODAL
		ModelAndView mv = new ModelAndView("PesquisaLancamentos2"); // PesquisaLancamentos
		mv.addObject(new Lancamento());
		mv.addObject("listaLancamentosModal", lstModal);
		return mv;
	}

	@RequestMapping(value = "{id}/deletar")
	public String excluir(@PathVariable Long id, RedirectAttributes attributes) {
		lctoServ.deletar(id);
		attributes.addFlashAttribute("mensagem", "Lançamento Excluido com sucesso!");
		return "redirect:/lancamentos";
		// RedirectAttributes attributes = cria um objeto de redirecionamento mais LEVE
		// attributes.addFlashAttribute(); SEMELHANTE ao mv.addObject();
		// Com a diferenca que nao precisamos criar um objeto ModelAndView passamos info
		// por atributos
	}
	
	@RequestMapping(value = "{id}")
	public ModelAndView editar(@PathVariable Long id) {
		Optional<Lancamento> lancamento = lctoServ.editar(id);
		ModelAndView mv = new ModelAndView("CadastroLancamento");
		mv.addObject("lancamento", lancamento);
		return mv;
	}

	@RequestMapping("/novo") // Define o acesso a pagina Raiz, ou seja ja abre pesquisando um titulo
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("CadastroLancamento"); // PesquisaLancamentos
		mv.addObject(new Lancamento());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Lancamento lancamento, Errors errors) {
		ModelAndView mv = new ModelAndView("CadastroLancamento"); // Criando objeto que retorna dados
		if (errors.hasErrors()) {
			return mv;
		}

		try {
			lctoServ.salvar(lancamento);
			mv.addObject("mensagem", "Lançamento Salvo com Sucesso!");
			mv.addObject(new Lancamento()); // Seta um novo objeto depois de SALVAR sem usar o Redirect.
			return mv;
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataCompraVenda", null, e.getMessage()); // objeto-errorCode-String(message)
			return mv;
		}
		//System.out.println("--------------------------"+lancamento.toString());
	}
	
	@RequestMapping("/salvarModal")
	public ModelAndView salvarModal(@Validated Lancamento lancamento, Errors errors) {
		System.out.println(" MODAL------------- MODAL------------- MODAL: "+lancamento.toString());
		ModelAndView mv = new ModelAndView("PesquisaLancamentos2"); // Criando objeto que retorna dados
		if (errors.hasErrors()) {
			//System.out.println("-----------ERRO-----------");
			return mv;
		}

		try {
			//System.out.println("-----------SUCESSO-----------");
			idAdd = idAdd ++;
			lancamento.setId(idAdd);
			lstModal.add(lancamento); // Add (obj:lancamento) a LISTA DE LANCAMENTOS
			//System.out.println("LISTA LANCAMENTO: "+lstModal.toString());
			mv.addObject("listaLancamentosModal", lstModal); // Atualiza a lista com o novo lancamento
			mv.addObject("mensagem", "Lançamento Adicionado com Sucesso!");
			mv.addObject(new Lancamento()); // Seta um novo objeto depois de SALVAR sem usar o Redirect.
			return mv;
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataCompraVenda", null, e.getMessage()); // objeto-errorCode-String(message)
			return mv;
		}
	}
	
	@RequestMapping("/gerarCSV") // Define o acesso a pagina Raiz, ou seja ja abre pesquisando um titulo
	public ModelAndView criar_CSV() {
		ModelAndView mv = new ModelAndView("PesquisaLancamentos"); // PesquisaLancamentos
		//mv.addObject(new Lancamento());
		List<Lancamento> lstCriarCsv = lctoServ.buscarTodos();
		mv.addObject("listaLancamentos", lstCriarCsv);
		try {
			lctoServ.criar_CSV(lstCriarCsv);
			mv.addObject("mensagem", "Arquivo CSV gerado com Sucesso!");
			return mv;
		} catch (Exception e) {
			e.toString();
			return mv;
		}
	}

	@ModelAttribute("tipos_acfi")
	public List<Tipo_AcFi> tipos_AcFi() {
		return Arrays.asList(Tipo_AcFi.values());
	}

}
