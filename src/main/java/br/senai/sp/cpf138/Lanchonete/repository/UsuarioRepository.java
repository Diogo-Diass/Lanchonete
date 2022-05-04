package br.senai.sp.cpf138.Lanchonete.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import br.senai.sp.cpf138.Lanchonete.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
	
	public Usuario findByEmailAndSenha(String email, String senha);

}
