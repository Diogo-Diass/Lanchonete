package br.senai.sp.cpf138.Lanchonete.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cpf138.Lanchonete.annotation.Publico;
import br.senai.sp.cpf138.Lanchonete.model.Lanchonete;
import br.senai.sp.cpf138.Lanchonete.model.TipoLanchonete;
import br.senai.sp.cpf138.Lanchonete.repository.LanchoneteRepository;
import br.senai.sp.cpf138.Lanchonete.repository.TIpoLanchoRepository;


@RequestMapping("/api/lanchonete")
@RestController
public class LanchoneteRestController {
	
	@Autowired
	private LanchoneteRepository repository;
	
	
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Lanchonete> getLanchonetes(){
		//trás a lista de lanchonete
		return repository.findAll();
	}
	
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Lanchonete> findLanchonete(@PathVariable("id") Long idLanchonete){
		//busca a lanchonete
		Optional<Lanchonete> lanchonete = repository.findById(idLanchonete);
		//se e a lanchonete estiver pesente
		if(lanchonete.isPresent()) {
			return ResponseEntity.ok(lanchonete.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	@RequestMapping(value = "/tipo/{idTipo}", method = RequestMethod.GET )
	public List<Lanchonete> getTipoLanchonete(@PathVariable("idTipo") Long idTipo){
		
			return repository.findByTipoId(idTipo);
		}
	
	@RequestMapping(value = "boolean/{estacionamento}", method = RequestMethod.GET )
	public List<Lanchonete> getBooleanLanchonete(@PathVariable("estacionamento") boolean estacionamento){
		
		return repository.findByEstacionamento(estacionamento);
		
	}
	
	@RequestMapping(value = "estado/{estado}", method = RequestMethod.GET)
	public List<Lanchonete> getStringEstado(@PathVariable("estado") String estado){
		
		return repository.findByEstado(estado);
	}
}
	
	
	

