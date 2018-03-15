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
<title>分加速度</title>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
</head>
<body>
<canvas id="canvas" width="800" height="400" style="background-color: #ccc "></canvas>
<script>
	var canvas = $("#canvas").get(0);
	var context = canvas.getContext('2d');
	var origX = canvas.width/2;
	var origY = canvas.height/2;
	var circle = new Circle(0,0,20); 
	
	//画圆
	function drawCircle(ctx,x,y,r,lineWidth,color){
		ctx.beginPath();
		ctx.lineWidth = (lineWidth=='undefined')?1:lineWidth;  //设置线宽
		ctx.fillStyle = (color=='undefined')?'#000':color; //设置填充色
		ctx.arc(x, y, r, 0, 2 * Math.PI, false);//路径开始
		ctx.fill();
	}
	
	function Circle(x,y,radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	Circle.prototype.draw = function(ctx){
		ctx.save();
		ctx.translate(this.x,this.y);
		drawCircle(ctx,this.x,this.y,this.radius);
		ctx.restore();
	}
	
	$(function(){
		var speed = 0;
		var a = 0.5;
		(function drawCircle(){
			window.requestAnimationFrame(drawCircle);
			context.clearRect(0,0,canvas.width,canvas.height);
			
			var aX = a * Math.cos(60 * Math.PI/180);
			var aY = a * Math.sin(60 * Math.PI/180);
			
			if(circle.y > canvas.height/2+circle.radius){
				circle.y = -circle.radius;
			}
			circle.x += aX;
			circle.y += aY;
			circle.draw(context);
		})();
	})
	
</script>
</body>
</html>