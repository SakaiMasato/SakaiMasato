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
<title>ball_front_ball_back</title>
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

$(function(){
	var canvas = $("#canvas")[0];
	var context = canvas.getContext('2d');
	var ball1 = new Ball(20,"#ff0000");
	var ball2 = new Ball(10,"orange");
	var mouse = canvasUtils.captureMouse(canvas);
	var easing = 0.05;
	
	ball1.x = ball2.x = 0;
	ball1.y = ball2.y = 0; 
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame);
		context.clearRect(0,0,canvas.width,canvas.height);
		
		vx1 = (mouse.x-ball1.x)*easing;
		vy1 = (mouse.y-ball1.y)*easing;
		
		vx2 = (ball1.x-ball2.x)*easing;
		vy2 = (ball1.y-ball2.y)*easing;
		
		ball1.x+=vx1;
		ball1.y+=vy1;
		
		ball2.x+=vx2;
		ball2.y+=vy2;
		
		ball1.draw(context);
		ball2.draw(context);
	})();
});
</script>
</body>
</html>