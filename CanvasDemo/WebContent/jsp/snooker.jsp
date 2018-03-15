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
<title>球_角度旋转</title>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>public/ball.js"></script>
<script type="text/javascript" src="<%=basePath %>public/line.js"></script>
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

function bounce(ballA,ballB){//动量守恒
	if(Math.abs(ballA.x-ballB.x)<=ballA.radius+ballB.radius){
		vxA = ((ballA.mass-ballB.mass)*ballA.vx+2*ballB.vx)/(ballA.mass+ballB.mass);
		vxB = ((ballB.mass-ballA.mass)*ballB.vx+2*ballA.vx)/(ballA.mass+ballB.mass);
		ballA.vx = vxA;
		ballB.vx = vxB;
	}
}
$(function(){

	var ball1 = new Ball(30,"red");
	ball1.x = 0;
	ball1.y = canvas.height/2;
	ball1.vx = 1;
	
	var ball2 = new Ball(10,"blue");
	ball2.x = canvas.width;
	ball2.y = canvas.height/2;
	ball2.vx = -3;
	ball1.mass = 1;//转换系数
	ball2.mass = 1;
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame);
		context.clearRect(0,0,canvas.width,canvas.height);
		
		ball1.x += ball1.vx;	ball2.x+=ball2.vx;
		bounce(ball1,ball2);
		ball1.draw(context);
		ball2.draw(context);
	})();
})
</script>

</body>
</html>