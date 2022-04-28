package br.senai.sp.cpf138.Lanchonete.model;

import lombok.Data;

@Data
public class Erro {

	private int statusCode;
	private String mensagem;
	private String exception;
}
