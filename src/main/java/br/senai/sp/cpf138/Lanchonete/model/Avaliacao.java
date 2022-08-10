package br.senai.sp.cpf138.Lanchonete.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;


@Entity
@Data
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty(access = Access.WRITE_ONLY)	
	@ManyToOne
	private Lanchonete lanchonete;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Calendar dataVisita;
	private String comentario;
	private double nota;
	
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Lanchonete getLanchonete() {
		return lanchonete;
	}



	public void setLanchonete(Lanchonete lanchonete) {
		this.lanchonete = lanchonete;
	}



	public Calendar getDataVisita() {
		return dataVisita;
	}



	public void setDataVisita(Calendar dataVisita) {
		this.dataVisita = dataVisita;
	}



	public String getComentario() {
		return comentario;
	}



	public void setComentario(String comentario) {
		this.comentario = comentario;
	}



	public double getNota() {
		return nota;
	}



	public void setNota(double nota) {
		this.nota = nota;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	@ManyToOne
	private Usuario usuario;
}
