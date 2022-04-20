package br.senai.sp.cpf138.Lanchonete.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cpf138.Lanchonete.model.TipoLanchonete;
import br.senai.sp.cpf138.Lanchonete.repository.TIpoLanchoRepository;

@RequestMapping("/api/tipo")
@RestController
public class TipoLanchoneteRestController {
	
	@Autowired
	private TIpoLanchoRepository tipReposirory;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<TipoLanchonete> getTipoLanchonetes(){
		
		return tipReposirory.findAll();
	}
}
