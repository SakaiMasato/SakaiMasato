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
<script type="text/javascript">
	var canvas = $("#canvas")[0];
	var context = canvas.getContext('2d');
	var h = canvas.height;
	var w = canvas.width;
	
	$(function(){
		var ball = new Ball(30);
		var vr = 0.05;
		(function drawFrame(){
			window.requestAnimationFrame(drawFrame);
			context.clearRect(0,0,w,h);
			
			ball.x = w/2+100*Math.cos(vr);//半径100的圆周运动
			ball.y = h/2+100*Math.sin(vr);
			vr+=0.05;
			
			ball.draw(context);
		})();
	})
</script>

</body>
</html>