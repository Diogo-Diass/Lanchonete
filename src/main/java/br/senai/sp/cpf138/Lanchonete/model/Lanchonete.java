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



	public String getEndereco() {
		return endereco;
	}



	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public String getCep() {
		return cep;
	}



	public void setCep(String cep) {
		this.cep = cep;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public String getBairro() {
		return bairro;
	}



	public void setBairro(String bairro) {
		this.bairro = bairro;
	}



	public String getCidade() {
		return cidade;
	}



	public void setCidade(String cidade) {
		this.cidade = cidade;
	}



	public String getFotos() {
		return fotos;
	}



	public void setFotos(String fotos) {
		this.fotos = fotos;
	}



	public String getComplemento() {
		return complemento;
	}



	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public TipoLanchonete getTipo() {
		return tipo;
	}



	public void setTipo(TipoLanchonete tipo) {
		this.tipo = tipo;
	}



	public boolean isEstacionamento() {
		return estacionamento;
	}



	public void setEstacionamento(boolean estacionamento) {
		this.estacionamento = estacionamento;
	}



	public String getFormaPagamento() {
		return formaPagamento;
	}



	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}



	public String getRedeSocial() {
		return redeSocial;
	}



	public void setRedeSocial(String redeSocial) {
		this.redeSocial = redeSocial;
	}



	public String getTelefone() {
		return telefone;
	}



	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}



	public boolean isDelivery() {
		return delivery;
	}



	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}



	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}



	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}



	public String[] verFotos() {
		return this.fotos.split(";");
	}
 	
}
