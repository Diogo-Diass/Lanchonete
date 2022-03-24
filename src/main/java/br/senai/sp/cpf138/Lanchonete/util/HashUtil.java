package br.senai.sp.cpf138.Lanchonete.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {
	
	//transformar a Senha em hash(criptografando ela)
	public static String hash256(String palavra) {
		//tempero para o hash
		String salt = "s3nh4d1f1c1l";
				//acrescento o tempero
				palavra = palavra + salt;
				//cria o hash e armazena a String
				String sha256 = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();
		return sha256;
	}
}
