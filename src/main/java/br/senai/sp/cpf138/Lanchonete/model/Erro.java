package br.senai.sp.cpf138.Lanchonete.model;

import lombok.Data;

@Data
public class Erro {

	private int statusCode;
	private String mensagem;
	private String exception;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
	
}
