<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<base href="<%=basePath%>">
<title>球_弹性</title>
<style>
    p{
        position: absolute;
        top: 0; left: 420px;
        padding: 5px;
        width: 100px;
        background: #eee;  
    }
</style>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>public/ball.js"></script>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/utils.js"></script>
</head>
<body>
<canvas id="canvas" width="600" height="600" style="background:#ccc">
your browser not support canvas
</canvas>
<script type="text/javascript">
	var canvas = $("#canvas")[0];
	var context = canvas.getContext('2d');
	var mouse = canvasUtils.captureMouse(canvas);
	var balls = [];//球的集合
	var ballNum = 2;//球的个数
	var spring = 0.03//弹性系数
	var gravity = 0.2;//重力
	var f = 0.9

	function init(){
		for(var i=0; i<ballNum; i++){
			var ball = new Ball(20);
			ball.x = 0;
			ball.y = 0;
			balls.push(ball);
		}
	}
	
	function moveBall(ball,mouse){
		var dx = mouse.x-ball.x;
		var dy = mouse.y-ball.y;
		
		ball.vx += dx*spring;
		ball.vy += dy*spring;
		ball.vx *= f;//阻力损失
		ball.vy *= f;

		ball.vy += gravity;//重力
		
		ball.x += ball.vx;
		ball.y += ball.vy;
	}
	$(function(){
		init();
		(function drawFrame(){
			window.requestAnimationFrame(drawFrame);
			context.clearRect(0,0,canvas.width,canvas.height);
			
			balls.forEach(function(ball,i,balls){
				//移动球
				moveBall(ball,mouse);
				//绘制绳子
				context.save();
				context.beginPath();
				context.moveTo(ball.x,ball.y);
				context.lineTo(mouse.x,mouse.y);
				context.stroke();
	            context.closePath();
				context.restore();
				ball.draw(context);
			});
		})();
	})
</script>
</body>
</html>