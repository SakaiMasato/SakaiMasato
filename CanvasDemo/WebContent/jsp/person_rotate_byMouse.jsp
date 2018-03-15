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
<title>小人</title>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
</head>
<body>
<canvas id="canvas" width="500" height="500" style="background-color: #ccc "></canvas>
</body>
<script>
	var canvas = $("#canvas")[0];
	var context = canvas.getContext("2d")
	var origX = canvas.width/2;
	var origY = canvas.height/2;
	var mouse = canvasUtils.captureMouse(canvas);//web端
// 	var mouse = canvasUtils.captureTouch(canvas);//移动端
	var person = new Person();
	
	function Person(){
		this.x=0;
		this.y=0;
		this.rotation=0;
		this.color="#ff0000";
	}
	//画圆
	function drawCircle(ctx,x,y,r,lineWidth,color){
// 		ctx.save();
// 		ctx.translate(this.x,this.y);//将canvas坐标原点平移至x,y
// 		ctx.rotate(this.rotation);//设置canvas旋转角度
		ctx.beginPath();
		ctx.lineWidth = (lineWidth=='undefined')?1:lineWidth;  //设置线宽
		ctx.fillStyle = (color=='undefined')?'#000':color; //设置填充色
		ctx.arc(x, y, r, 0, 2 * Math.PI, false);//路径开始
		ctx.fill();
// 		ctx.restore();
	}
	
	//画线
	function drawLine(ctx,x1,y1,x2,y2,lineWidth,color){
		ctx.beginPath();
		ctx.lineWidth = (lineWidth=='undefined')?1:lineWidth;  //设置线宽
		ctx.strokeStyle = (color=='undefined')?'#000':color; //设置填充色
		ctx.moveTo(x1,y1);
		ctx.lineTo(x2,y2);
		ctx.stroke();
	}
	
	Person.prototype.draw = function(ctx){
		ctx.save();
 		ctx.translate(this.x,this.y);//将canvas坐标原点平移至x,y
 		ctx.rotate(this.rotation);//设置canvas旋转角度
 		drawCircle(ctx,0,0,10,1,'#ff0000');
 		drawLine(ctx,0,10,0,30,1,'#ff0000');
 		drawLine(ctx,0,10,-10,20,'#ff0000');
  		drawLine(ctx,0,10,10,20,'#ff0000');
  		drawLine(ctx,0,30,-10,40,'#ff0000');
  		drawLine(ctx,0,30,10,40,'#ff0000');
 		ctx.restore();
	}	
	$(function(){
		
		//指定圆心坐标
		person.x = origX;
		person.y = origY;
		
		//执行动画循环函数
		(function drawPerson(){
			window.requestAnimationFrame(drawPerson,canvas);
			context.clearRect(0,0,canvas.width,canvas.height);
			
			//旋转度数
			var dx = mouse.x-person.x;
			var dy = mouse.y-person.y;
			person.rotation = Math.atan2(dy, dx);
			person.draw(context);//绘制
		})();
	});
</script>
</html>