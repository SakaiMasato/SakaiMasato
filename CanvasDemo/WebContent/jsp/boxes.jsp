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
<script type="text/javascript" src="<%=basePath %>public/box.js"></script>
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
var gravity = 0.1;
var coordinates = new Array(0,20,40,60,80,120,160,200,240,260,280,300,320,340,360,380);
var boxes = [];
var activeBox = createBox();

function createBox(){
	var color = Math.random()*(0xffffff);
	var box = new Box(20,20,color);
	box.x = coordinates[Math.floor(Math.random()*coordinates.length)];
	box.y = -20;
	boxes.push(box);
	return box;
}

function drawBox(box){
	if(activeBox !== box && canvasUtils.intersects_box(activeBox,box)){
		activeBox.y = box.y-activeBox.height;
		activeBox = createBox();
	}
	box.draw(context);
}

window.addEventListener('keydown', function(event){
    switch (event.keyCode){
        case 37:
            activeBox.x -= 20;
            break;
        case 39:
            activeBox.x += 20;
            break;
        case 40:
            gravity += 0.1;
            break;
    }      
}, false);
window.addEventListener("keyup", function(event){
       gravity = 0.1;
}, false);

$(function(){
	(function drawFrame(){
		window.requestAnimationFrame(drawFrame);
		context.clearRect(0,0,canvas.width,canvas.height);
		
		activeBox.vy+=gravity;
		activeBox.y+=activeBox.vy;
		
		if(activeBox.y + activeBox.height > canvas.height){
            activeBox.y = canvas.height - activeBox.height;
            activeBox = createBox();
        }
        if(activeBox.x < 0 ){
            activeBox.x = 0;
        }
        if( activeBox.x + activeBox.width > canvas.width){
            activeBox.x = canvas.width -activeBox.width;
        }
        
        boxes.forEach(drawBox);
	})();
})
</script>
</body>
</html>