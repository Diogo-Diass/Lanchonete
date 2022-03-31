package br.senai.sp.cpf138.Lanchonete.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.senai.sp.cpf138.Lanchonete.model.TipoLanchonete;
import br.senai.sp.cpf138.Lanchonete.repository.TIpoLanchoRepository;

@Controller
public class TipoLanchoneteController {
	
	@Autowired
	private TIpoLanchoRepository repLancho;
	
	@RequestMapping("tipoLanchonete")
	public String formTipoLanchonete() {
		
		
		return "tipo/formTipo";
	}
	@RequestMapping( value = "salvarTipo", method = RequestMethod.POST)
	public String salvarTipo( TipoLanchonete tipo) {
		repLancho.save(tipo);
		
		return "redirect:listarTipos";
	}
	@RequestMapping("listarTipos")
	public String listarTipos(Model model) {
		model.addAttribute("tipos",repLancho.findAll());
		
		return "tipo/listarTipos";
	}
	@RequestMapping("alterarTipo")
	public String alterarTipo(Model model, Long id) {
		
		TipoLanchonete tipo = repLancho.findById(id).get();
		model.addAttribute("tipo", tipo);
		
		return "forward:tipoLanchonete";
	}
	@RequestMapping("excluirTipo")
	public String excluirTipo(Long id) {
		repLancho.deleteById(id);
		return "redirect:listarTipos";
	}
	@RequestMapping("buscarChave")
	public String buscarPalavra(Model model, String palavraChave) {
		model.addAttribute("tipos", repLancho.procurarPalavraChave(palavraChave));
		return "tipo/listarTipos";
	}
	
}
