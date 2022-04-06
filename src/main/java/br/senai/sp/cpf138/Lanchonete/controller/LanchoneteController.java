package br.senai.sp.cpf138.Lanchonete.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.senai.sp.cpf138.Lanchonete.model.Lanchonete;
import br.senai.sp.cpf138.Lanchonete.repository.LanchoneteRepository;
import br.senai.sp.cpf138.Lanchonete.repository.TIpoLanchoRepository;

@Controller
public class LanchoneteController {
	@Autowired
	private TIpoLanchoRepository repTipo;
	private LanchoneteRepository repLan;
	
	@RequestMapping("formLanchonete")
	public String formLanchonete(Model model) {
		
		model.addAttribute("tipos", repTipo.findAll());
		
		return "Lanchonete/lanchonete";
	}
	@RequestMapping(value = "salvarLanchonete", method = RequestMethod.POST)
	public String salvarLanchonete(Model model, Lanchonete lancho){
		repLan.save(lancho);
		return "redirect:lanchonete";
	}
}
