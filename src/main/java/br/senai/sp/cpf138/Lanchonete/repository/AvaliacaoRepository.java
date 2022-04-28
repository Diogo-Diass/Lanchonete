package br.senai.sp.cpf138.Lanchonete.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cpf138.Lanchonete.model.Avaliacao;
import br.senai.sp.cpf138.Lanchonete.model.Lanchonete;

public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long>{

	public List<Avaliacao> findByLanchoneteId(Long idLanchonete);
}
