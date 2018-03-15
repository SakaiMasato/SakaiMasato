var btn = $("#btn")[0];
var control = $("#control")[0];

btn.addEventListener('click',function(e){
	control.classList.toggle('slide');
},false);