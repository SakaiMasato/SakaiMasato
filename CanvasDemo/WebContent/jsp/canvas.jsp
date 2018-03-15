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
<title>Canvas</title>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
</head>
<body>
<canvas id="canvas" width='500' height="500" style="background:#000"></canvas>
</body>
<script type="text/javascript">
$(function(){
	var canvas=$("#canvas")[0];
	//点击
	mouse = canvasUtils.captureMouse(canvas);
	//触摸
// 	mouse = canvasUtils.captureTouch(canvas);
// 	在画布上点击，控制台输出相对坐标
	canvas.addEventListener('mousedown',function(){
		console.log("x: "+mouse.x+" y: "+mouse.y);
	});
});
</script>
</html>