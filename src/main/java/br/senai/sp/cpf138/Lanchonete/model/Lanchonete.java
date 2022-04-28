package br.senai.sp.cpf138.Lanchonete.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Lanchonete {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	private String endereco;	
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String cep;
	private String estado;
	private String bairro;
	private String cidade;
	
	@Column(columnDefinition = "TEXT")
	private String fotos;
	private String complemento;
	private String numero;
	
	@ManyToOne
	private TipoLanchonete tipo;
	private boolean estacionamento;
	private String formaPagamento;
	private String redeSocial;
	private String telefone;
	private boolean delivery;
	
	@OneToMany(mappedBy = "lanchonete")
	private List<Avaliacao> avaliacoes;
	
	public String[] verFotos() {
		return this.fotos.split(";");
	}
 	
}
