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
<title>球_移出</title>
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
<script type="text/javascript" src="<%=basePath %>public/utils.js"></script>
</head>
<body>
<canvas id="canvas" width="600" height="600" style="background:#ccc">
your browser not support canvas
</canvas>
<p id="log"></p>
</body>
<script type="text/javascript">
var canvas = $("#canvas")[0];
var log = $("#log")[0];
var context = canvas.getContext('2d');
var W = canvas.width,
	H = canvas.height;

var origX = canvas.width/2;
var origY = canvas.height/2;

var balls = [];

//初始化是个球球
function init(){
	for(var i=0;i<50;i++){
		var ball = new Ball(Math.random()*50+1,Math.random()*(0xffffff));
		ball.name = "ball_"+i;
		ball.x = Math.random()*W;
		ball.y = Math.random()*H;
		ball.vx = Math.random()*5-1;
		ball.vy = Math.random()*5-1;
		balls.push(ball);
	}
}

$(function(){
	init();
	var angle = 0.5;
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame);
		context.clearRect(0,0,W,H);
		
		balls.forEach(function(ball,i,balls){
			if(ball.x-ball.radius>W || ball.y-ball.radius>H || ball.x+ball.radius<0 || ball.y+ball.radius<0){
// 				balls.splice(i,1);
				ball.x = W/2;
				ball.y = H;
				ball.vx = Math.random()*5-1;
				ball.vy = Math.random()*5-1;
				if(balls.length>0){
					log.innerHTML += ball.name+"移出<br />"
				}else{
					log.innerHTML = "全部移除";
				}
			}
			ball.x+=ball.vx;
			ball.y+=ball.vy;
// 			angle+=0.05;
// 			ball.radius += Math.sin(angle);
			ball.draw(context);
		});
	})();
});
</script>
</html>