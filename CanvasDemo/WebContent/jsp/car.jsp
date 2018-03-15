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
// 	var ball1 = new Ball(0,0,20);//在坐标系中的相对坐标
// 	var ball2 = new Ball(0,0,20);
// 	var carBody = new Body(-50,-50,-30,60); 	
	var car = new Car(0,0);
	
	//画圆
	function drawCircle(ctx,x,y,r,lineWidth,color){
		ctx.beginPath();
		ctx.lineWidth = (lineWidth=='undefined')?1:lineWidth;  //设置线宽
		ctx.fillStyle = (color=='undefined')?'#000':color; //设置填充色
		ctx.arc(x, y, r, 0, 2 * Math.PI, false);//路径开始
		ctx.fill();
	}
	
	//画矩形
	function drawRect(ctx,x,y,width,height,lineWidth,color){
		ctx.beginPath();
		ctx.lineWidth = (lineWidth=='undefined')?1:lineWidth;  //设置线宽
		ctx.fillStyle = (color=='undefined')?'#f00':color; //设置填充色
		ctx.fillRect(x,y,width,height);
		ctx.fill();
	}
	
	function Ball(x,y,radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	Ball.prototype.draw = function(ctx,x,y){
		ctx.save();
		ctx.translate(x,y);
		drawCircle(ctx,this.x,this.y,this.radius,1,'#0000ff');
		ctx.restore();
	}
	
	Ball.prototype.tanslate = function(x,y){
		this.x += x;
		this.y += y;
	}
	
	function Body(x,y,width,height){//车身
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	Body.prototype.draw = function(ctx,x,y){
		ctx.save();
		ctx.translate(x,y);
		drawRect(ctx,this.x,this.y,this.width,this.height,1,"#f00");
		ctx.restore();
	}
	
	function Car(x,y){
		this.x = x;
		this.y = y;
		this.body = new Body();
		this.body.width = 100;
		this.body.height = 60;
		this.body.x = this.x-this.body.width/2;
		this.body.y = this.y-this.body.height/2;
		var radius = 20;//车轮半径
		this.ball1 = new Ball(this.body.x+radius,this.body.y+this.body.height+radius,radius);
		this.ball2 = new Ball(this.body.x+this.body.width-radius,this.body.y+this.body.height+radius,radius);
	}
	
	Car.prototype.draw = function(ctx){
		ctx.save();
		ctx.translate(origX,origY);//绝对坐标
		this.ball1.draw(ctx,this.x,this.y);//以小车为参照物的相对坐标
		this.ball2.draw(ctx,this.x,this.y);
		this.body.draw(ctx,this.x,this.y);
		ctx.restore();
	}
	
	//匀速运动
	$(function(){
		var speed = 0;
		var a = 0.01;//加速度
		var angle = 0;
		var range = 50;
		(function drawBall(){
			window.requestAnimationFrame(drawBall,canvas);
			context.clearRect(0,0,canvas.width,canvas.height);
			speed += a;//不加加速度便是匀速
			car.x += speed;
			
			if(car.x > canvas.width/2+car.body.width/2){//球滚出画布
				car.x = -canvas.width/2-car.body.width/2;//从画布的最左边出来
				
			}
			//垂直， 由于angle角度没发生变化，所以纵坐标保持不变
            car.y = 0+Math.sin(angle)*range;//range是振幅
//             angle+=0.05
			
			car.draw(context);
		})();
	});
</script>
</body>
</html>