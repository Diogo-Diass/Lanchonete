package br.senai.sp.cpf138.Lanchonete.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.cloud.storage.BucketInfo.PublicAccessPrevention;

import br.senai.sp.cpf138.Lanchonete.annotation.Privado;
import br.senai.sp.cpf138.Lanchonete.annotation.Publico;
import br.senai.sp.cpf138.Lanchonete.rest.UsuarioRestController;

@Component
public class AppInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//variável para descobrir pra onde estão tentando ir
		String uri = request.getRequestURI();
		
		System.out.println(uri);
		//verifica se o handler é um HandlerMethod, o que indica que foi encontrado um método em algum controller para a requisição
		if(handler instanceof HandlerMethod) {
			//liberar o acesso á pagina inicial
			if(uri.equals("/")) {
				return true;
			}
			if(uri.endsWith("/error")) {
				return true;
			}
			//fazer o casting para HandlerMethod
			HandlerMethod metodoChamado = (HandlerMethod) handler;
			
			if(uri.startsWith("/api")) {								
				//variavel para o token
				String token = null;
				
				//se for um metodo privado
				if(metodoChamado.getMethodAnnotation(Privado.class) != null) {
					try {
					token = request.getHeader("Authorization");
					
					//algoritmo para descriptografar
					Algorithm algoritmo = Algorithm.HMAC256(UsuarioRestController.SECRET);
					
					//objeto para verificar o token										
					JWTVerifier verifier =  JWT.require(algoritmo).withIssuer(UsuarioRestController.EMISSOR).build();
					//linha que vai validar o token
					DecodedJWT jwt = verifier.verify(token); 
					//extrair os dados do payload
					Map<String, Claim> payloadMap = jwt.getClaims();
					
					System.out.println(payloadMap.get("nome_usuario"));
					
					return true;
					
		}catch (Exception e) {
			//verifica se o token está vindo nulo
			if(token == null) {
				//se o token não estiver autorizado, manda uma msg de erro
				response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
			}else {
				response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
			}
			return false;
		} 
					
	}
				
				return true;
			}else {
							
			//se o método for publico, libera
			if(metodoChamado.getMethodAnnotation(Publico.class) != null) {
				return true;
			}
			
			//verifica se existe um usuario logado
			if(request.getSession().getAttribute("usuarioLogado") != null) {
				return true;
			}
			else {
				//redireciona para a pagina inicial
				response.sendRedirect("/");
				return false;
			}
			
		}
	}
		return true;
	}

}
