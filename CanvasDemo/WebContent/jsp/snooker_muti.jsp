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
<title>球_碰撞</title>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>public/ball.js"></script>
<script type="text/javascript" src="<%=basePath %>public/line.js"></script>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/utils.js"></script>
</head>
<body>
<canvas id="canvas" width="600" height="600" style="background:#aaf">
your browser not support canvas
</canvas>
<script>
var canvas = $("#canvas")[0];
var context = canvas.getContext('2d');
var bounce = -1;
var balls = [];
var distance = 80;//如果球之间的距离低于distance则绘制白线
var ballNum = 50;

function init(){
	for(var i=0;i<ballNum;i++){//50个球
		var radius = Math.random()*5 + 5;//随机半径
        var ball = new Ball(radius, "#fff");
        ball.mass = radius;//重量
        ball.x = Math.random()*canvas.width;//位置
        ball.y = Math.random()*canvas.height;
        ball.vx = Math.random()*3 - 1;//速度
        ball.vy = Math.random()*3 - 1;
        balls.push(ball);
	}
}
//碰撞检测
function bounces(ball1,ball2){
	var dx = ball2.x - ball1.x,
    dy = ball2.y - ball1.y,
    dist = Math.sqrt(dx*dx + dy*dy);

if(dist < ball1.radius + ball2.radius){
    var angle = Math.atan2(dy, dx),
        sin = Math.sin(angle),
        cos = Math.cos(angle);
    
    //rotate ball1 position
    var pos0 = {
        x: 0,
        y: 0 
    }
    
    //rotate ball2 position
    var pos1 = rotate(dx, dy, sin, cos, true);
    
    //rotate ball1 velocity
    var vel0 = rotate(ball1.vx, ball1.vy, sin, cos, true);
    
    //rotate ball2 velcoity
    var vel1 = rotate(ball2.vx, ball2.vy, sin, cos, true);
    
    //collision reaction
    
    var vxTotal = vel0.x - vel1.x;
    
    vel0.x = ((ball1.mass - ball2.mass)*vel0.x + 2*ball2.mass*vel1.x)/(ball1.mass + ball2.mass);
    vel1.x = vxTotal + vel0.x;
    
    //update position
    pos0.x += vel0.x;
    pos1.x += vel1.x;
    
    //rotate everything back
    var pos0F = rotate(pos0.x, pos0.y, sin, cos, false);
    var pos1F = rotate(pos1.x, pos1.y, sin, cos, false);
    
    //adjust position to actual screen position
    ball2.x = ball1.x + pos1F.x;
    ball2.y = ball1.y + pos1F.y;
    ball1.x = ball1.x + pos0F.x;
    ball1.y = ball1.y + pos0F.y;
    
    //rotate velocity back
    var vel0F = rotate(vel0.x, vel0.y, sin, cos, false);
    var vel1F = rotate(vel1.x, vel1.y, sin, cos, false);
    
    ball1.vx = vel0F.x;
    ball1.vy = vel0F.y;
    ball2.vx = vel1F.x;
    ball2.vy = vel1F.y;
	}
}

function rotate(x,y,sin,cos,reverse){//将速度合成到球心连接线上
	return {
        x: (reverse)?(x*cos + y*sin):(x*cos - y*sin),
        y: (reverse)?(y*cos - x*sin):(y*cos + x*sin)
    }
}
//边界检测
function checkWalls(ball){
    if(ball.x + ball.radius > canvas.width){
        ball.x = canvas.width - ball.radius;
        ball.vx *= bounce;
    }else if(ball.x - ball.radius < 0){
        ball.x = ball.radius;
        ball.vx *= bounce;
    }
    
    if(ball.y + ball.radius > canvas.height){
        ball.y = canvas.height - ball.radius;
        ball.vy *= bounce;
    }else if(ball.y - ball.radius < 0){
        ball.y = ball.radius;
        ball.vy *= bounce;
    }
}

function checkBounce(ball,i){
	for(var j=i=1;j<ballNum;j++){
		bounces(ball,balls[j]);//检测碰撞
	}
}

function draw(ball){
	ball.x+=ball.vx;
	ball.y+=ball.vy;
	ball.draw(context);
}

function drawLine(ball){
	for(var j=i=1;j<ballNum;j++){
		var dx = ball.x-balls[j].x;
		var dy = ball.y-balls[j].y;
		var dis = Math.sqrt(dx*dx+dy*dy);
		if(dis<distance){
			context.save();
            context.strokeStyle = "rgba(225,225,225,0.5)";
            context.beginPath();
            context.moveTo(ball.x, ball.y);
            context.lineTo(balls[j].x, balls[j].y);
            context.closePath()
            context.stroke()
            context.restore();
		}
	}
}
$(function(){
	init();
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame);
		context.clearRect(0,0,canvas.width,canvas.height);
		
        balls.forEach(checkBounce);//碰撞检测
        balls.forEach(checkWalls);//边界检测
        balls.forEach(drawLine);//绘制连接线
        balls.forEach(draw);
	})();
})
</script>
</body>
</html>