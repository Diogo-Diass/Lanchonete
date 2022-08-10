package br.senai.sp.cpf138.Lanchonete.model;

import lombok.Data;

@Data
public class TokenJWT {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
