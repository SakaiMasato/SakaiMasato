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
<script>
var canvas = $("#canvas")[0];
var context = canvas.getContext('2d');

function drawLine(){
	var l = new Line(0, 0, 300, 0);
	l.x = 50;
    l.y = 300;
	l.rotation = 10*Math.PI/180;
	return l;
}

$(function(){		
	
	var line = drawLine();
	var ball = new Ball(20);
	ball.x = 100;
	ball.y = 100;
	var gravity = 0.2;
	var bounce = -0.6;
	var cos = Math.cos(10 * Math.PI/180);
	var sin = Math.sin(10 * Math.PI/180);
	
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame);
		context.clearRect(0,0,canvas.width,canvas.height);
		
		//初始为零，小球往下落
		ball.vy += gravity;
		ball.x += ball.vx;
		ball.y += ball.vy;
		//相对坐标
		var x1 = ball.x - line.x; 
		var y1 = ball.y - line.y;
		
		var y2 = y1*cos-x1*sin;
		if(y2 > -ball.radius){//处理碰撞
			//旋转坐标（先将线的坐标旋转至水平）
			var x2 = x1*cos+y1*sin;
// 			y2 = y1*cos-x1*sin;
			//旋转速度（处理反弹）
			var vx1 = ball.vx*cos+ball.vy*sin;//注意与线段恢复的公式相反
			var vy1 = ball.vy*cos-ball.vx*sin;
            y2 = -ball.radius;
            vy1 *= bounce;
	        //旋转坐标（再把线的坐标旋转回来）
	        x1 = x2 * cos - y2 * sin;
	        y1 = y2 * cos + x2 * sin;
	        
	        ball.vx = vx1 * cos - vy1 * sin;//注意与之前线段旋转的公式相反
	        ball.vy = vy1 * cos + vx1 * sin;
	        ball.x = line.x + x1;
	        ball.y = line.y + y1;
        }
        
        ball.draw(context);
        line.draw(context);
		
	})();
});
</script>
</body>
</html>