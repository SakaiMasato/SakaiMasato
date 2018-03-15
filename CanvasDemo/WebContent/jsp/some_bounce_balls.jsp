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
<title>球_角度旋转_鼠标</title>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>public/ball.js"></script>
<script type="text/javascript" src="<%=basePath %>public/line.js"></script>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/utils.js"></script>

</head>
<body>
<canvas id="canvas" width="400" height="500" style="background:#ccc">
your browser not support canvas
</canvas>
<script>
var canvas = $("#canvas")[0];
var context = canvas.getContext('2d');
var lines=[];//线
var ball=new Ball(20,"purple");
var lineNum = 7;//线数量
var bounce = -0.6;//反弹系数
var gravity = 0.2;//重力系数


function init(){
	ball.x = 200;
	ball.y = 50;
	for(var i=0; i<lineNum; i++){
        var line = new Line(-50, 0, 50, 0);
        lines.push(line);
    }
    
    lines[0].x = 200;
    lines[0].y = 100;
    lines[0].rotation = 30 * Math.PI/180;
    
    lines[1].x = 200;
    lines[1].y = 200;
    lines[1].rotation = 45 * Math.PI/180;
    
    lines[2].x = 320;
    lines[2].y = 150;
    lines[2].rotation = -20 * Math.PI/180;
    
    lines[3].x = 250;
    lines[3].y = 330;
    lines[3].rotation = 10 * Math.PI/180;
    
    lines[4].x = 330;
    lines[4].y = 250;
    lines[4].rotation = -30 * Math.PI/180;
    
    lines[5].x = 150;
    lines[5].y = 430;
    lines[5].rotation = 30 * Math.PI/180;
    
    lines[6].x = 350;
    lines[6].y = 430;
    lines[6].rotation = -30 * Math.PI/180;	
}

function lineDraw(line){
	checkBounce(line);
	line.draw(context);
}

function checkBounce(line){
	var bounds = line.getBounds();
	if(ball.x+ball.radius>bounds.x && ball.x-ball.radius<bounds.x+bounds.width){
		var cos = Math.cos(line.rotation);
		var sin = Math.sin(line.rotation);
		
		var x1 = ball.x-line.x
		var y1 = ball.y-line.y;
		
		var y2 = y1*cos-x1*sin;
		var vy1 = ball.vy*cos-ball.vx*sin;

		if(y2>-ball.radius && y2<vy1){
			var x2 = x1*cos+y1*sin;
			
// 			y2 = y1*cos-x1*sin;
			//旋转速度（处理反弹）
			var vx1 = ball.vx*cos+ball.vy*sin;//注意与线段恢复的公式相反

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
	}
	ball.draw(context);
}
$(function(){
	init();
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame);
		context.clearRect(0,0,canvas.width,canvas.height);
		
		ball.x+=ball.vx;
		ball.y+=ball.vy;
		ball.vy+=gravity;
		
		//边界检测
		if(ball.x + ball.radius > canvas.width){
            ball.x = canvas.width - ball.radius;
            ball.vx *= bounce;
        }else if(ball.x < ball.radius){
            ball.x = ball.radius;
            ball.vx *= bounce;
        }
        
        if(ball.y + ball.radius > canvas.height){
            ball.y = canvas.height - ball.radius;
            ball.vy *= bounce;
        }else if(ball.y < ball.radius){
            ball.y = ball.radius;
            ball.vy *= bounce;
        }
        
		lines.forEach(lineDraw);
	})();
})
</script>
</body>
</html>