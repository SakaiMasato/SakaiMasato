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
<title>匀速运动_球</title>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
</head>
<body>
<canvas id="canvas" width="800" height="400" style="background-color: #ccc "></canvas>
<script type="text/javascript">
	var canvas = $("#canvas")[0];
	var context = canvas.getContext('2d');
	var origX = canvas.width/2;
	var origY = canvas.height/2;
	var ball = new Ball(0,0,20);//在坐标系中的相对坐标
	
	function Ball(x,y,radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.scale = 1;//比例
	}
	
	//画圆
	function drawCircle(ctx,x,y,r,lineWidth,color){
		ctx.beginPath();
		ctx.lineWidth = (lineWidth=='undefined')?1:lineWidth;  //设置线宽
		ctx.fillStyle = (color=='undefined')?'#000':color; //设置填充色
		ctx.arc(x, y, r, 0, 2 * Math.PI, false);//路径开始
		ctx.fill();
	}
	
	Ball.prototype.draw = function(ctx){
		ctx.save();
		ctx.translate(origX,origY);
		drawCircle(ctx,this.x,this.y,this.radius,1,'#0000ff');
		ctx.restore();
	}
	
	//匀速运动
	$(function(){
		var speed = 0.05;
		var angle = 0;
		var range = 1;
		(function drawBall(){
			window.requestAnimationFrame(drawBall,canvas);
			context.clearRect(0,0,canvas.width,canvas.height);
			ball.x+=0.5;
			if(ball.x > canvas.width/2+ball.radius){//球滚出画布
				ball.x = -canvas.width/2-ball.radius;//从画布的最左边出来
			}
			//垂直， 由于angle角度没发生变化，所以纵坐标保持不变
            ball.y = 0+Math.sin(angle)*50;//range是振幅
 			angle += speed;
			
			//垂直， 由于angle角度没发生变化，所以纵坐标保持不变
			ball.scale = Math.sin(angle)*range;
			
            ball.radius = ball.radius+ball.scale;//range是振幅
//             angle+=0.05
			
			ball.draw(context);
		})();
	});
	
	//脉冲运动
	
</script>
</body>
</html>