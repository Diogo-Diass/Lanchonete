package br.senai.sp.cpf138.Lanchonete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.senai.sp.cpf138.Lanchonete.util.HashUtil;
import lombok.Data;

@Data
//para mapear como uma entidade jpa
@Entity
public class Administrador {
	
//chave primaria auto increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	@Column(unique = true)
	@Email
	private String email;
	
	@NotEmpty
	private String senha;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	//método para "setar" a senha aplicando o hash
	public void setSenha(String senha) {
		//aplica o hash e "seta" a senha no objeto
		this.senha = HashUtil.hash256(senha);
		
	}
	//método para "setar" a senha sem aplicar o hash
	public void setSenhaComHash(String hash) {
		//"seta" o hash na senha
		this.senha = hash;
	}
}


