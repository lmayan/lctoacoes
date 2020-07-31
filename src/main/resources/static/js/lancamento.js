// Inicializa o MODAL de Inclusao de Lancamento
$('#novoLancamento').on('show.bs.modal', function(event){
	
});


// Inicializa o MODAL de confirmacao de Exclusao
$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event){
	
	var button = $(event.relatedTarget); //event.relatedTarget indica qual botao/componente disparou o EVENT
	
	var idLancamento = button.data('codigo'); // Recupera o atributo 'data-codigo' criado do th:attr na pagina 
	var nomeAcao = button.data('descricao'); // Recupera o atributo 'data-descricao' criado do th:attr na pagina 
	
	var modal = $(this); //Pega o componente(modal) que disparou o evento
	var form = modal.find('form');// Pega o <form> de dentro do modal
	var action = form.attr('data-url-base');// Pega a <action> de dentro do <form>
	
	if(!action.endsWith('/')){ // (SE não terminar com '/') adicione a '/' 
		action += '/';
	}
	
	form.attr('action', action + idLancamento +'/deletar');//Edita a action adicionando o codigoTitulo e CONTROLLER
	//Encontra o <span> dentro da class='modal-body' e edita a estrutura do componente
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o Lançamento com ID: <strong>'
			+idLancamento+' '+nomeAcao+'</strong>?')
	
	//alert para confirmar recebimento do atributo que sera usado para exclusao 
	//alert('Lancamento Excluido com Sucesso!!  ' + idLancamento);
	
});


// Inicializa as funcoes abaixo do JavaScript ao carregar pagina
$(function () {

	//Inicializa comp. 'tooltip' Bootstrap possuem a tag 'rel="tooltip"'
	$('[rel="tooltip"]').tooltip(); 
	
	//Inicializa as mascaras do 'maskMoney'
	$('.js-currency').maskMoney({thousands:'.', decimal:',', affixesStay: false, allowZero:false});

	//Executa funcao qdo clicar sobre componente que possua class="js-atualizar-status" 
	$('.js-atualizar-status').on('click', function(event){ 

		event.preventDefault(); //Faz que o componente nao execute de forma PADRAO (Fazendo requisicao)
		
		var btnReceber = $(event.currentTarget); //Variavel para receber o componente disparado (botao clicado).
		var urlReceber = btnReceber.attr('href'); //Variavel para receber caminho da URL contido no objeto
		
		//console.log('clicou'); //Teste EVENT no console
		//console.log('Conteudo de urlReceber: ',urlReceber);
		
		//Chamando AJAX via JQuery
		var response = $.ajax({
			url: urlReceber, //Define o caminho da URL 
			type: 'PUT' //Define o metodo da requisicao (no caso PUT para editar o status)
		});
		
		
		// Depois de chamar AJAX se RESPOSTA tiver SUCESS, executa isso!!
		response.done(function(resp){
			// Recupera informacao do codigo que esta no botao receber
			var codigoTitulo = btnReceber.data('codigo');
			
			// Captura o componente que tem a marcacao 'data-status-linha' e faz alteracao no componente <span>
			// que esta dentro dele modificando o seu conteudo via html.
			// Recebe tambem o retorno do metodo "atualizarStatus()" para edicao da descricao do <spans>
			$('[data-status-linha=' + codigoTitulo + ']').html('<span class="label label-success">'+ resp +'</span>')
			
			// Esconde o botão de receber
			btnReceber.hide();
			
			//Teste Recebimento INFO
			//alert("SUCESSO -  ao Receber a cobrança.");
		});
		
		// Depois de chamar AJAX se RESPOSTA tiver FAIL, executa isso!!
		response.fail(function(e){
			console.log(e);
			alert("Erro ao Receber a cobrança.");
		});
		
		
	});
	
});

function validacion() {
	
	//*****	Converter campo quantidade
	m1 = $("#qtdade").maskMoney('unmasked')[0];
	//alert("M1 Antes: "+m1);
	$("#qtdade").val(parseInt(m1));
	//alert("M1 Depois: "+m1);

	
	
	//*****	CONDICOES VALIDACOES FRONT
	/* if () {
	  alert('[ERROR] '+);
	  return false;
	}*/	
} 

function calcularTotLcto(){
	
	//***** Efetuando calculo valor Total no FRONT
    var 	m1 = document.getElementById('qtdade').value.toString().replace(',','.'),
    	m2 = document.getElementById('vlrUnit').value.toString().replace(',','.'),
    	m3 = document.getElementById('vlrDesp').value.toString().replace(',','.');
    	
	//***** Se campo Quantidade >=(maior) que 1 execute
	//***** Se campo VlrUnit >=(maior) que 8 execute
	if(m1>=1){
		//alert('M1: '+m1+' M2: '+m2+' M3: '+m3+' M4: '+m4)
		if(m2.length>=8){
			alert("M2 Antes: "+m2)
			m2 = document.getElementById('vlrUnit').value.toString().replace('.','')
			alert("M2 Depois: "+m2)
			$("#vlrUnit").val(m2);
		}
		//alert('M1: '+m1+' M2: '+m2+' M3: '+m3)
		var m4 = (m1 * m2 - m3);
	}

	//***** Reescrevendo valor no campo TotLcto para exibir valor correto (maskMoney)
	$("#totLcto").val(m4);
	temp = document.getElementById('totLcto').value.toString().replace('.',',');
	//alert("TOT CORRIGIDO: "+temp)
	$("#totLcto").val(temp);
	//$("#totLcto").maskMoney('mask', temp);
}




