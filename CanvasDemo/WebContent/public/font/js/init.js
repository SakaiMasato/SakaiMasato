var canvas = $("#canvas")[0];
	var context = canvas.getContext('2d');
	var W = canvas.width = window.innerWidth;
	var H = canvas.height = window.innerHeight;
	var type = 'ball';
	var gridX = 7,
		gridY = 7;//取样频率
	//改变组成文字的元素
	var ball = $("#ball")[0],
		rect = $("#rect")[0];
	//定义颜色
	var colors = [
		  '#f44336', '#e91e63', '#9c27b0', '#673ab7', '#3f51b5',
		  '#2196f3', '#03a9f4', '#00bcd4', '#009688', '#4CAF50',
		  '#8BC34A', '#CDDC39', '#FFEB3B', '#FFC107', '#FF9800',
		  '#FF5722'
		  ];
		  
	//获取到控制栏的各个元素
	var text = $("#message")[0],//文字
	 	gravity = $("#gra")[0],//重力  
		cycle = $("#dur")[0],//周期
	 	speed = $("#speed")[0],//速度
		radius = $("#radius")[0],//半径
		resolution = $("#res")[0];//分辨率
	
	var graVal = parseFloat(gravity.value),
     durVal = parseFloat(cycle.value),
     spdVal = parseFloat(speed.value),
     radVal = parseFloat(radius.value),
     resVal = parseFloat(resolution.value);
		
	var word = new Shape(W/2,H/2,text.value);
		word.getValue(context);

function changeType(particle){
	particle.addEventListener('click',function(){
		this.style.backgroundColor = "orange";
		((particle==ball)?rect:ball).style.backgroundColor = "rgba(0, 0, 0, 0)";
		type = ((particle==ball)?"ball":"rect");
		change();
	},false);
}
function change(){
	context.clearRect(0,0,W,H);//清空画布
	gridX = parseFloat(resolution.value);
	gridY = parseFloat(resolution.value);
	word.text = text.value;
	word.placement = [];//文字改变，则文字轨迹改变
	word.getValue(context);//获取新的文字轨迹
}

function changeV(){
	graVal = parseFloat(gravity.value);
    durVal = parseFloat(cycle.value);
    spdVal = parseFloat(speed.value);
    radVal = parseFloat(radius.value);
}
$(function(){
	changeType(ball);//绑定改变组成元素的事件
	changeType(rect);//绑定改变组成元素的事件
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame,canvas);
		context.clearRect(0,0,W,H);
		
		for(var i=0;i<word.placement.length;i++){
			word.placement[i].draw(context);
		}
	})();
});