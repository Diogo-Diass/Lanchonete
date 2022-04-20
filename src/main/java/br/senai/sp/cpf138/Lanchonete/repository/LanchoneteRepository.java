package br.senai.sp.cpf138.Lanchonete.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cpf138.Lanchonete.model.Lanchonete;
import br.senai.sp.cpf138.Lanchonete.model.TipoLanchonete;

public interface LanchoneteRepository extends PagingAndSortingRepository<Lanchonete, Long> {

	public List<TipoLanchonete> findAllByOrderByNomeAsc();
	
	public List<Lanchonete> findByTipoId(Long idTipo);
	
	public List<Lanchonete> findByEstacionamento(boolean estacionamento);
	
	public List<Lanchonete> findByEstado(String estado);
}
