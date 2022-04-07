package br.senai.sp.cpf138.Lanchonete.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.senai.sp.cpf138.Lanchonete.model.Lanchonete;
import br.senai.sp.cpf138.Lanchonete.repository.LanchoneteRepository;
import br.senai.sp.cpf138.Lanchonete.repository.TIpoLanchoRepository;

@Controller
public class LanchoneteController {

	
	@Autowired
	private TIpoLanchoRepository repTipo;
	@Autowired
	private LanchoneteRepository repLan;
	
	@RequestMapping("formLanchonete")
	public String formLanchonete(Model model) {
		
		model.addAttribute("tipos", repTipo.findAll());
		
		return "Lanchonete/lanchonete";
	}
	@RequestMapping(value = "salvarLanchonete", method = RequestMethod.POST)
	public String salvarLanchonete(Lanchonete lancho, @RequestParam("fileFotos") MultipartFile[] fileFotos){
		
		System.out.println(fileFotos.length);
		//repLan.save(lancho);
		return "redirect:listarLanchonete";
	}
	@RequestMapping("listarLanchonete")
	public String listarLanchonete(Model model) {
		model.addAttribute("lanchonete",repLan.findAll());
		return "Lanchonete/listarLanchonete";
	}
	@RequestMapping("excluirLanchonete")
	public String excluirLanchonete(Long id) {
		
			repLan.deleteById(id);
		return "redirect:listarLanchonete";
	}
	@RequestMapping("alterarLanchonete")
	public String alterarLanchonete(Model model, Long id) {
		
		Lanchonete lancho = repLan.findById(id).get();
		model.addAttribute("lancho", lancho);
		
		return "forward:formLanchonete";
	}
}
