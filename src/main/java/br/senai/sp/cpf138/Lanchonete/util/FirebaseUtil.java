package br.senai.sp.cpf138.Lanchonete.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FirebaseUtil {

	//variavel para guardar as credenciais do firebase
	private Credentials credenciais;
	//variável para acessar o storage
	private Storage storage;
	//constante para o nome do bucket
	private final String BUCKET_NAME = "guide-lanchonete.appspot.com";
	//constante para o prefixo da URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/guide-lanchonete.appspot.com/o/";
	//constante para o suffix da URL
	private final String SUFFIX = "?alt=media";
	//constante para a url
	private final String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;
	
	public FirebaseUtil() {
		//busca as credenciais (arquivos json)
		Resource resource = new ClassPathResource("chavefirebase.json");
		//ler o arquivo para obter as credenciais
		try {
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			//acessa o serviço de storage
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	//retorna a extensão do arquivo através do seu nome
	private String getExtensao(String nomeArquivo) {
		//retorna o trecho da String que vai do ultimo ponto até o fim
		
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
		
	}
	
	public String uploadFile(MultipartFile arquivo) throws IOException {
		
		//método que gera uma String aleátoria para o nome o arquivo
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		
		//criar um blob
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);
		
		//criar um blobinfo a partir do blobid
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		
		//manda o blobinfo para o storage passando os bytes do arquivo
		storage.create(blobInfo, arquivo.getBytes());
		
		//retorna a url para acessar o arquivo
		return String.format(DOWNLOAD_URL, nomeArquivo);
	}
	
	//método para excluir a foto do firebase
	public void deletar(String nomeArquivo) {
		//retira o prefixo e o sufixo do nome do arquivo
		nomeArquivo = nomeArquivo.replace(PREFIX, "").replace(SUFFIX, "");
		//pega um blob através do nome do arquivo
		Blob blob = storage.get(BlobId.of(BUCKET_NAME, nomeArquivo));
		//deleta o arquivo
		storage.delete(blob.getBlobId());
	}
	
}
