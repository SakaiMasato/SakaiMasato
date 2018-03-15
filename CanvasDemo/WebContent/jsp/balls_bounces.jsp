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
<script>
var canvas = $("#canvas")[0];
var context = canvas.getContext('2d');
var w = canvas.width;
var h = canvas.height;
var bounce = -1;
var balls = [];
var ballNum = 5;
var spring = 0.03;

function init(){
	for(var i=0;i<ballNum;i++){
		var ball = new Ball(Math.random()*30,Math.random()*(0xffffff))
		ball.x = Math.random()*w;
		ball.y = Math.random()*h;
		ball.vx = Math.random()*5-1;
		ball.vy = Math.random()*5-1;
		balls.push(ball);
	}
}
//碰撞检测
function balls_bounce(ball,i,balls){
	for(var j=i+1;j<ballNum;j++){
		if(canvasUtils.intersects_ball(ball,balls[j])){
// 			ball.vx*=bounce;
// 			ball.vy*=bounce;
// 			balls[j].vx*=bounce;
// 			balls[j].vy*=bounce;
			
//             var min_distance = ball.radius + balls[j].radius;
// 			var dx = ball.x-balls[j].x;
// 			var dy = ball.y-balls[j].y;
// 			var angle = Math.atan(dy, dx);
//             var targetX = ball.x + Math.cos(angle)*min_distance;
//             var targetY = ball.y + Math.cos(angle)*min_distance;
//             var ax = (targetX - ball.x)*spring*0.5;
//             var ay = (targetY - ball.y)*spring*0.5;
         
            ball.vx -= 0.2;
            balls[j].vx += 0.1;
            ball.vy -= 0.2;
            balls[j].vy += 0.1; 
		}
	}
}
//边界检测
function balls_border(ball){
	if(ball.x-ball.radius<0){
		ball.x = ball.radius;
		ball.vx*=bounce;
	}
	else if(ball.x+ball.radius>w){
		ball.x = w-ball.radius;
		ball.vx*=bounce;
	}
	if(ball.y-ball.radius<0){
		ball.y = ball.radius;
		ball.vy *= bounce;
	}
	if(ball.y+ball.radius>h){
		ball.y = h-ball.radius;
		ball.vy *= bounce;
	}
}

function draw(ball){
	ball.x+=ball.vx;
	ball.y+=ball.vy;
	ball.draw(context);
}

$(function(){
	init();
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame);
		context.clearRect(0,0,w,h);
		
		balls.forEach(balls_bounce);
		balls.forEach(balls_border);
		balls.forEach(draw);
	})();
})
</script>
</body>
</html>