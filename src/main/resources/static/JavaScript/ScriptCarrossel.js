const img = document.getElementById("img")
const imgs = document.querySelectorAll("#img img")

console.log(img)

let idx = 0;

function carrossel(){
	idx++;
	
	if(idx > imgs.length - 1){
		idx = 0;
	}
	
	img.style.transform = `translateX(${-idx * 100}%)`
}
setInterval(carrossel, 2300)