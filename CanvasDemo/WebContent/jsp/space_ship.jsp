<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>太空船</title>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
</head>
<body>
<canvas id="canvas" width="800" height="400" style="background-color: #ccc "></canvas>
<script>

<!-- 太空船 -->
function SpaceShip(x,y,rotation,isFlame){
	this.x = x;
	this.y = y;
	this.rotation = rotation;
	this.flame = isFlame;
}

SpaceShip.prototype.draw = function(ctx){
	ctx.save();
	ctx.translate(this.x,this.y);
	ctx.rotate(this.rotation);
	ctx.beginPath();
	ctx.strokeStyle='#ffffff';
	ctx.moveTo(0,0);
	ctx.lineTo(10,10);
	ctx.lineTo(0,20);
	ctx.lineTo(5,10);
	ctx.lineTo(0,0);
	ctx.stroke();
	if(this.flame){
		ctx.save();//因为火焰是以太空船为参照物绘制的,所以要保存绘制太空船时的画布状态
		ctx.beginPath();
		ctx.strokeStyle='#ff5555';
		ctx.globalAlpha=0.5
		ctx.lineWidth=5;
		ctx.moveTo(2.5,5);
		ctx.lineTo(-5,10);
		ctx.lineTo(2.5,15);
		ctx.stroke();
		ctx.restore();//恢复至绘制太空船时的画布状态
	}
	ctx.restore();//恢复至默认的画布状态	
}

$(function(){
	var canvas = $("#canvas")[0];
	var context = canvas.getContext('2d');
	var origX = canvas.width/2;
	var origY = canvas.height/2;
	var spaceShip = new SpaceShip(origX,origY,0,false);
	var	vR = 0,//控制旋转
		vX = 0,//控制X轴
		vY = 0,//控制Y轴
		a = 0;//加速度
	
	$(window).keydown(function(e){
		switch(e.keyCode){
			case 37: //left
				vR = -2;
				vX = 0;
				vY = 0;
				break;
			 case 39:  //right
				vR = 2;
				vX = 0;
				vY = 0;
				break;
			 case 38: //up
			 	spaceShip.flame = true;
			 	a = 1;//匀加速
			 	break;
             case 40: //back
				vR = 0;
				vX = 0;
				vY = 0;
				a = 0;//加速度
				break;
		}
	});
		
	$(window).keyup(function(e){
		a = 0;
		vR = 0;
		spaceShip.flame = false;
	});
	
	(function drawShip(){
		window.requestAnimationFrame(drawShip);
		context.clearRect(0,0,canvas.width,canvas.height);
		
		spaceShip.rotation += vR*Math.PI/180;
		spaceShip.x += a*Math.cos(spaceShip.rotation); 
		spaceShip.y += a*Math.sin(spaceShip.rotation);
		spaceShip.draw(context);
	})();
});

</script>


</body>
</html>