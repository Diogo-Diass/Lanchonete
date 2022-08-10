

function fazPost(url, body){
	
	console.log("body: ", body)
	
	let request = new XMLHttpRequest()
	request.open("POST", url, true)
	request.setRequestHeader("content-type", "application/json")
	request.send(JSON.stringify(body))
	
	return request.responseText
}


function cadastrarUsuario(){
	

	let url = "http://localhost:8080/api/usuario"
	let nome = document.getElementById("nome").value
	let email = document.getElementById("email").value
	let senha = document.getElementById("senha").value
	
	console.log(nome)
	
	let body = {
		"nome": nome,
		"email": email,
		"senha": senha
	}
	
	fazPost(url, body)
	
	 nome = document.getElementById("nome").reset()
	 email = document.getElementById("email").reset()
	 senha = document.getElementById("senha").reset()
	
		
	
	}
	
	
