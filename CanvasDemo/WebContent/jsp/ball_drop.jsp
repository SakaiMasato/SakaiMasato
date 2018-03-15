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
<title>球_坠落</title>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>public/ball.js"></script>
<script type="text/javascript" src="<%=basePath %>public/utils.js"></script>
</head>
<body>
<canvas id="canvas" width="600" height="600" style="background:#ccc">
<script>
var canvas = $("#canvas")[0],
	context = canvas.getContext('2d'),
    origX = canvas.width/2,
    origY = canvas.height/2

var ball = new Ball(20);
	
$(function(){
	var gravity = 0.2,
		bounce = -0.8;
	ball.vx = ball.vy = Math.random()*10-5;//这样可以造出反方向的速度
	
	ball.x = origX;
	ball.y = origY;
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame);
		context.clearRect(0,0,canvas.width,canvas.height);
		
		ball.vy += gravity;//重力
		ball.x += ball.vx;
		ball.y += ball.vy;
		
		//碰撞检测
		if(ball.x + ball.radius > canvas.width){
            ball.x = canvas.width - ball.radius;
            ball.vx *= bounce;
        }else if(ball.x - ball.radius < 0){
            ball.x = ball.radius;
            ball.vx *= bounce;
        }
        
        if(ball.y + ball.radius > canvas.height){
            ball.y = canvas.height - ball.radius;
            ball.vy *= bounce;
        }else if(ball.y - ball.radius < 0){
            ball.y = ball.radius;
            ball.vy *= bounce;
        }
		
		ball.draw(context);
		
	})();
})
</script>
</body>
</html>