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
<title>重力加速度</title>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
</head>
<body>
<canvas id="canvas" width="800" height="400" style="background-color: #ccc "></canvas>
<script type="text/javascript">
	var canvas = $("#canvas").get(0);
	var context = canvas.getContext('2d');
	var origX = canvas.width/2;
	var origY = canvas.height/2;
	var ball = new Ball(0,-190,20);
	
	//画圆
	function drawCircle(ctx,x,y,r,lineWidth,color){
		ctx.beginPath();
		ctx.lineWidth = (lineWidth=='undefined')?1:lineWidth;  //设置线宽
		ctx.fillStyle = (color=='undefined')?'#000':color; //设置填充色
		ctx.arc(x, y, r, 0, 2 * Math.PI, false);//路径开始
		ctx.fill();
	}
	
	function Ball(x,y,radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	Ball.prototype.draw = function(ctx){
		ctx.save();
		ctx.translate(origX,origY);
		drawCircle(ctx,this.x,this.y,this.radius,1,"#f00");
		ctx.restore();
	}
	
	$(function(){
		var gravity = 0.2;//重力加速度
		var bounce = -0.8;//定义反弹系数
		var speed = 0;//初速度
		
		function checkBounce(){//撞击速度，撞击系数
			if(ball.y>canvas.height/2-ball.radius){
				ball.y = canvas.height/2-ball.radius;
				speed *= bounce;
			}
		}
		
		(function drawBall(){
			window.requestAnimationFrame(drawBall);
			context.clearRect(0,0,canvas.width,canvas.height);
			speed += gravity;
			ball.y += speed;
			checkBounce(ball);//碰撞检测
			ball.draw(context);
			
		})();
		
	})
</script>

</body>
</html>