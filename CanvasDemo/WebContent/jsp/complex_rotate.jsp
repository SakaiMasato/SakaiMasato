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
<title>bosxes_coordinate</title>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>public/ball.js"></script>
<script type="text/javascript" src="<%=basePath %>public/line.js"></script>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/utils.js"></script>

</head>
<body>
<canvas id="canvas" width="400" height="400" style="background:#ccc">
your browser not support canvas
</canvas>
<script type="text/javascript">
	var canvas = $("#canvas")[0];
	var context = canvas.getContext('2d');
	var h = canvas.height;
	var w = canvas.width;
	var centerX = w/2,
		centerY = h/2;
	var ball = new Ball(20);
	var vr = 0.05,
		sin = Math.sin(vr),
		cos = Math.cos(vr);
	
	function coordinate(){
		var l1 = new Line(0,h/2,w,h/2);
		l1.draw(context);
		var l2 = new Line(w/2,0,w/2,h);
		l2.draw(context);
	}
	
	function circle(angle,radius){
		context.save();
	    context.translate(centerX,centerY);
	    context.beginPath();
	    context.arc(0,0,50,0,angle,false);
	    context.arc(0,0,radius,0,Math.PI*2,false);
	    context.closePath();
	    context.stroke();
	    context.restore();
	}
	
	$(function(){
		ball.x = Math.random()*w;
        ball.y = Math.random()*h;
		(function drawFrame(){
			window.requestAnimationFrame(drawFrame);
			context.clearRect(0,0,w,h);
	        coordinate();//绘制坐标系

// 			ball.x = w/2+100*Math.cos(vr);//半径100的圆周运动
// 			ball.y = h/2+100*Math.sin(vr);
// 			vr+=0.05;
   			var x1 = ball.x - centerX;
            var y1 = ball.y - centerY;
            
            var x2 = x1*cos - y1*sin;
            var y2 = y1*cos + x1*sin;
            
            ball.x = centerX + x2;
            ball.y = centerY + y2;
            
            var radius = Math.sqrt(Math.pow((ball.x-centerX),2)+Math.pow((ball.y-centerY),2));
            var dx = ball.x-centerX;
            var dy = ball.y-centerY;
            var angle = Math.atan2(dy,dx);//x,y算角度，弧度制
            circle(angle,radius);
            
            context.save();
            context.beginPath();
            context.moveTo(centerX,centerY);
            context.lineTo(ball.x,ball.y);
            context.closePath();
            context.stroke();
            context.restore();
			
			ball.draw(context);
		})();
	})
</script>

</body>
</html>