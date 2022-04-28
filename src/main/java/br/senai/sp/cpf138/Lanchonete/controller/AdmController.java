package br.senai.sp.cpf138.Lanchonete.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cpf138.Lanchonete.annotation.Publico;
import br.senai.sp.cpf138.Lanchonete.model.Administrador;
import br.senai.sp.cpf138.Lanchonete.repository.AdminRepository;
import br.senai.sp.cpf138.Lanchonete.util.HashUtil;

@Controller
public class AdmController {
	
	//repository para persistencia do adm
	@Autowired
	private AdminRepository admRep;

	//request mapping para o form
	@RequestMapping("formAdm")
	public String formAdm() {
		
		return "Adm/formAdm"; 
	}
	//request pra salvar o adm
	@RequestMapping(value = "salvarAdm", method = RequestMethod.POST )
	public String SalvarAdmin(@Valid Administrador adm, BindingResult result, RedirectAttributes attr) {
		//verifica se houve erro na validação do objeto
		
		if(result.hasErrors()){
			//envia msg de erro via requisição
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			return "redirect:formAdm";
		}
		//verifica se esta sendo feita uma alteração ao invés de uma inserção
		boolean alteracao = adm.getId() != null ? true : false;
		
		
		//verifica se a senha esta vazia
		if(adm.getSenha().equals(HashUtil.hash256(""))) {
			//se nao for alteração, defino a primeira parte do email como senha
			if(!alteracao) {
				//EXTRAI A PARTE DO EMAIL ANTES DO @
				//substring pega um trecho da String começando pela posição 0, no caso a primeira letra, o index of faz com que o trecho(subString) pegue so ate o @, definido pelo index of
				String parte = adm.getEmail().substring(0, adm.getEmail().indexOf("@"));
				//define a senha do admin
				adm.setSenha(parte);
			}else {
				//busca a senha atual
				String hash = admRep.findById(adm.getId()).get().getSenha();
				//"seta"  a senha com hash
				adm.setSenhaComHash(hash);
			}
		}
		
		
		try {
			//salva o adm
			admRep.save(adm);
			//exibe uma mensagem de sucesso ao adm ao ele ter conseguido se cadastrar
			attr.addFlashAttribute("mensagemSucesso", "Administrador salvo com sucesso!!! OBS: Caso a senha não tenha sido informada,ela será a primeira parte do seu email ----- ID: " + adm.getId());	
			
		} catch (Exception e) {
			//exibe uma mensagem de erro ao adm ao ele não ter conseguido se cadastrar
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o administrador " + e.getMessage());
		}
		return "redirect:listarAdmin/1";
	}
	//request mapping para listar, informando a pagina desejada
	@RequestMapping("listarAdmin/{page}")
		
									// pathVariable associa o page da url e o int é a associação ao page da url
	public String listar(Model model,@PathVariable("page") int page) {
		
		//cria um pageable com 6 elementos por páginas ordenando oa objetos pelo nome de forma ascendente
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		
		//cria a página atual através do repository
		Page<Administrador> pagina = admRep.findAll(pageable);
		
		//descobrir o total de pginas
		int totalPages = pagina.getTotalPages();
		
		//cria uma lista de inteiros para representar as paginas
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		//preencher a lista com as páginas
		for(int i = 0; i < totalPages; i++) {
			pageNumbers.add(i+1);
			
		} 
		//adiciona as variáveis na Model
		model.addAttribute("admins", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		//retorna para o html das listas
		return "Adm/lista";
	}
	
	@RequestMapping("excluirAdm")
	public String excluirAdm(Long id) {
		
		admRep.deleteById(id);
		
		return "redirect:listarAdmin/1";
	}
	
	@RequestMapping("alterarAdm")
	public String alterarAdm(Model model, Long id) {
		
		Administrador adm = admRep.findById(id).get();
		model.addAttribute("adm", adm);
		
		return "forward:formAdm";
	}
	@Publico
	@RequestMapping("login")
	public String login(Administrador admLogin, RedirectAttributes attr, HttpSession session) {
		//buscar o adm no banco de dados através do email e da senha
		Administrador admin = admRep.findByEmailAndSenha(admLogin.getEmail(), admLogin.getSenha());
		//verifica se existe o admin
		if(admin == null) {
			attr.addFlashAttribute("mensagemErro", "Login e/ou senha inválido(s)");
			return "redirect:/";
		}else {
			//se não for nulo, salva na sessao e acessa o sisyrma
			session.setAttribute("usuarioLogado", admin);
			return "redirect:inicio";
		}
	
	}
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		//elimina o usuário da session
		session.invalidate();
		//retorna para a barra inicial
		return "redirect:/";
		
	}
	
}
