package br.senai.sp.cpf138.Lanchonete.controller;

import java.io.IOException;

import javax.management.RuntimeErrorException;

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
import br.senai.sp.cpf138.Lanchonete.util.FirebaseUtil;

@Controller
public class LanchoneteController {

	
	@Autowired
	private TIpoLanchoRepository repTipo;
	@Autowired
	private LanchoneteRepository repLan;
	@Autowired
	private FirebaseUtil firebaseUtil;
	
	@RequestMapping("formLanchonete")
	public String formLanchonete(Model model) {
		
		model.addAttribute("tipos", repTipo.findAll());
		
		return "Lanchonete/lanchonete";
	}
	@RequestMapping(value = "salvarLanchonete", method = RequestMethod.POST)
	public String salvarLanchonete(Lanchonete lancho, @RequestParam("fileFotos") MultipartFile[] fileFotos){
		//string para url das fotos
		String fotos = "";
				
		//percorrer cada arquivo que foi submetido no formulario
		for(MultipartFile arquivo : fileFotos) {
		//verificar se o arquivo está vazio
			if(arquivo.getOriginalFilename().isEmpty()) {
				//vai para o próximo arquivo 
				continue;
			}
			//faz o upload para a nuvem e obtem a url gerada
			try {
				fotos += firebaseUtil.uploadFile(arquivo)+";";
			} catch (IOException e) {
				e.printStackTrace();
				 throw new RuntimeException(e);
			}
		}
		//atribui a String fotos ao objeto lanchonete
		lancho.setFotos(fotos);
		
		repLan.save(lancho);
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
	@RequestMapping("excluirFotoLanchonete")
	public String excluirFoto(Long idLanchonete, int numFoto, Model model) {
		//primeira coisa busca o restaurante no banco de dados
		Lanchonete lancho = repLan.findById(idLanchonete).get();
		//pegar a String da foto a ser excluida
		String fotoUrl = lancho.verFotos()[numFoto];
		//excluir do firebase
		firebaseUtil.deletar(fotoUrl);
		//"arranca" a foto da String fotos
		lancho.setFotos(lancho.getFotos().replace(fotoUrl+";", ""));
		//salva no banco de dados o objeto lancho
		repLan.save(lancho);
		//adiciona o lancho na Model
		model.addAttribute("lancho", lancho);
		//encaminhar para o form
	
		return "forward:formLanchonete";
	}
	
}
