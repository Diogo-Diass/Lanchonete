package br.senai.sp.cpf138.Lanchonete.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.senai.sp.cpf138.Lanchonete.model.TipoLanchonete;

public interface TIpoLanchoRepository extends PagingAndSortingRepository<TipoLanchonete, Long>{
	
	@Query("SELECT p from TipoLanchonete p WHERE p.palavraChave LIKE %:p%")
	public List<TipoLanchonete> procurarPalavraChave(@Param("p") String parametro);
			
	
}
