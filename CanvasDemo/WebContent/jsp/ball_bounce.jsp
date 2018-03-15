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
<title>bosxes</title>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>public/ball.js"></script>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/utils.js"></script>

</head>
<body>
<canvas id="canvas" width="400" height="600" style="background:#ccc">
your browser not support canvas
</canvas>
<textarea id="content" rows="10" cols="100"></textarea>
<script type="text/javascript">
var canvas = $("#canvas")[0];
var context = canvas.getContext('2d');
var mouse = canvasUtils.captureMouse(canvas);

var ball1 = new Ball(20,"blue");

var ball2 = new Ball(20,"red");
ball2.x = canvas.width/2;
ball2.y = canvas.height/2;

function drawBall(ball1,ball2){
	$("#content").html("");
	if(canvasUtils.intersects_ball(ball1,ball2)){
		$("#content").html("碰到了");
	}
	ball1.draw(context);
	ball2.draw(context);
}

$(function(){
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame);
		context.clearRect(0,0,canvas.width,canvas.height);
		ball1.x = mouse.x;
		ball1.y = mouse.y;
		
		drawBall(ball1,ball2);
	})();
});
</script>
</body>
</html>