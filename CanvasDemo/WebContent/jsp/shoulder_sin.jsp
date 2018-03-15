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
<title>平滑运动_手臂</title>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
</head>
<body>
<canvas id="canvas" width="800" height="400" style="background-color: #ccc "></canvas>
</body>
<script type="text/javascript">
	var canvas = $("#canvas")[0];
	var context = canvas.getContext('2d');
	var origX = canvas.width/2;
	var origY = canvas.height/2;
	var shoulderL = new Shoulder(0,10,-100,10);//左手
 	var shoulderR = new Shoulder(0,10,100,10);
 	var legL = new Shoulder(0,30,0,100);//左腿
	//画线
	function drawLine(ctx,x1,y1,x2,y2,lineWidth,color,lineCap){
		ctx.beginPath();
		ctx.lineWidth = (lineWidth=='undefined')?1:lineWidth;  //设置线宽
		ctx.strokeStyle = (color=='undefined')?'#000':color; //设置填充色
		ctx.lineCap = (lineCap=='undefined')?'butt':lineCap
		ctx.moveTo(x1,y1);
		ctx.lineTo(x2,y2);
		ctx.stroke();
	}
	//画圆
	function drawCircle(ctx,x,y,r,lineWidth,color){
		ctx.beginPath();
		ctx.lineWidth = (lineWidth=='undefined')?1:lineWidth;  //设置线宽
		ctx.fillStyle = (color=='undefined')?'#000':color; //设置填充色
		ctx.arc(x, y, r, 0, 2 * Math.PI, false);//路径开始
		ctx.fill();
	}

	//手臂
	function Shoulder(x1,y1,x2,y2){
		this.x=x1;
		this.y=y1;
		this.xEnd=x2;
		this.yEnd=y2;
		this.beta = 0;
		this.color="#ff5500";
	}
	
	Shoulder.prototype.drawShoulder = function(ctx,del){
		ctx.save();
       	ctx.translate(origX,origY);//将canvas坐标原点平移至画布中点
		ctx.lineWidth = 10;
       	ctx.lineCap = "round";
       	//透明度
       	ctx.globalAlpha = 0.8;
       	ctx.strokeStyle =this.color;
		this.beta += del*0.0012;
	    var l = Math.sin(this.beta);
	    ctx.beginPath();
	    //this.x,this.y是开始点
	    ctx.moveTo(this.x, this.y);
        
        //周期性改变y坐标
        this.yEnd = this.y + l*100;//100是振幅
        ctx.quadraticCurveTo((this.x+this.xEnd),this.y,this.xEnd,this.yEnd);//(this.x-100,this.y)控制点              (this.xEnd,this.yEnd)结束点
        
        ctx.stroke();
		ctx.restore();
		
	}
	
	Shoulder.prototype.drawLeg = function(ctx,del,param){
		ctx.save();
       	ctx.translate(origX,origY);//将canvas坐标原点平移至画布中点
		ctx.lineWidth = 10;
       	ctx.lineCap = "round";
       	//透明度
       	ctx.globalAlpha = 0.8;
       	ctx.strokeStyle =this.color;
		this.beta += del*0.0012;
	    var l = Math.sin(this.beta);
	    ctx.beginPath();
	    //this.x,this.y是开始点
	    ctx.moveTo(this.x, this.y);
        
        //周期性改变y坐标
        if(param){
        	this.xEnd = this.x+l*100;
        	ctx.quadraticCurveTo(this.x,(this.y+100),this.xEnd,this.yEnd);
        }else{
        	this.xEnd = this.x+l*100;
        	ctx.quadraticCurveTo(this.x,(this.y+100),-this.xEnd,this.yEnd);
        }
        
        ctx.stroke();
		ctx.restore();
		
	}
	
	$(function(){
 		
 		var oldTime = new Date().getTime(),
 	    del = null, newTime = null;
 		
 		(function drawFrame(){
 			window.requestAnimationFrame(drawFrame);
 			context.clearRect(0,0,canvas.width,canvas.height);
 			
 			newTime = new Date().getTime();
 	       	del = new Date().getTime() - oldTime;
 	       	oldTime = newTime;
 			//画头和躯干
 			context.save();
 			context.translate(origX,origY);//将canvas坐标原点平移至画布中点
 	 		drawCircle(context,0,0,10,1,'#000');
 	 		drawLine(context,0,10,0,30,1,'#000');
 	 		context.restore();
 	 		
 	 		//画四肢
 	 		shoulderL.drawShoulder(context,del);
 	 		shoulderR.drawShoulder(context,del);
 	 		legL.drawLeg(context,del,true);
 	 		legL.drawLeg(context,del,false);
 		})();
	});
</script>
</html>