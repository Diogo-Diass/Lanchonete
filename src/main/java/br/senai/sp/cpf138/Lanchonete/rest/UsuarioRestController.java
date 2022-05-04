package br.senai.sp.cpf138.Lanchonete.rest;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Payload;

import br.senai.sp.cpf138.Lanchonete.annotation.Privado;
import br.senai.sp.cpf138.Lanchonete.annotation.Publico;
import br.senai.sp.cpf138.Lanchonete.model.Erro;
import br.senai.sp.cpf138.Lanchonete.model.TokenJWT;
import br.senai.sp.cpf138.Lanchonete.model.Usuario;
import br.senai.sp.cpf138.Lanchonete.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
	
	//constantes para gerar o token
	public static final String EMISSOR = "Senai";
	
	//funciona como uma espécie de senha
	public static final String SECRET = "Lancho@Guide";

	@Autowired
	private UsuarioRepository repository;
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario){
		
		try {	
			
		//salvar o usúario no banco de dados
		repository.save(usuario);
		//retorna código 201  com a URL para acesso no Location e o usuario inserido no corpo da resposta
		
		return ResponseEntity.created(URI.create("/"+usuario.getId())).body(usuario);
		
		} catch (DataIntegrityViolationException e) {
		e.printStackTrace();
		
		Erro erro = new Erro();
		
		//status do erro, sendo ele o erro 500
		erro.setStatusCode(500);
		
		//mensagem do que causou o erro
		erro.setMensagem("Erro de constraint: Registro duplicado");
		
		//erro da exception
		erro.setException(e.getClass().getName());	
		
		return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
						
			Erro erro = new Erro();
			
			erro.setStatusCode(500);
			
			erro.setMensagem("Erro de constraint:"+ erro.getMensagem());
			
			erro.setException(e.getClass().getName());	
			
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable("id") Long id){
		//valida o id
		if(id != usuario.getId()) {
			throw new RuntimeException("ID Inválido");
		}
		//salvar o usuario
		repository.save(usuario);
		//criar um cabecalho http
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create("/api/usuario/"));
		return new ResponseEntity<Void>(header, HttpStatus.OK);
	}
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> findByUsuario(@PathVariable("id") Long idUsuario){
		
		Optional<Usuario> usuario = repository.findById(idUsuario);
		
		//se tem um usuario presente
		if(usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
			
		}else {
		return ResponseEntity.notFound().build();
		}
	}
	@Privado
	@RequestMapping(value =  "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirUsuario(@PathVariable("id") Long idUsuario){
		repository.deleteById(idUsuario);
		return ResponseEntity.noContent().build();
	}
	
	@Publico
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenJWT> logar(@RequestBody Usuario usuario){
		
		//busca o usuário no banco de dados
		usuario = repository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
		
		//verifica se existe um usuário 
		if(usuario != null) {
			//valores adicionais para o token
			Map<String, Object> payload = new HashMap<String, Object>();
			payload.put("id_usuario", usuario.getId());
			payload.put("nome_usuario", usuario.getNome());
			
			//definir a data de expiração
			//CalendarGetInstance te da a data e hora atual
			Calendar expiracao = Calendar.getInstance();
			expiracao.add(Calendar.HOUR, 1);
			
			Algorithm algoritmo = Algorithm.HMAC256(SECRET);
			
			//gerar o token
			TokenJWT tokenJwt = new TokenJWT();
			
			//create: cria o token.........  payload: passa as informações do usuario, as do id e nome por exemplo...... Expires: pega a data de expiração pra saber quando expirar o login do usuario
			//e assinatura do algoritmo com "hash"
			tokenJwt.setToken(JWT.create().withPayload(payload).withIssuer(EMISSOR).withExpiresAt(expiracao.getTime()).sign(algoritmo));
			
			return ResponseEntity.ok(tokenJwt);
			
		}else {
			return new ResponseEntity<TokenJWT>(HttpStatus.UNAUTHORIZED);
		}
	}
}
