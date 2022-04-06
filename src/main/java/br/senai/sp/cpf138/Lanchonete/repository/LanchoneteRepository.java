package br.senai.sp.cpf138.Lanchonete.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cpf138.Lanchonete.model.Lanchonete;
import br.senai.sp.cpf138.Lanchonete.model.TipoLanchonete;

public interface LanchoneteRepository extends PagingAndSortingRepository<Lanchonete, Long> {

	public List<TipoLanchonete> findAllByOrderByNomeAsc();
}
