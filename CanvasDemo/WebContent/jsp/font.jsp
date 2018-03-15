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
<title>文字变换</title>
<link rel="stylesheet" href="<%=basePath %>public/font/css/font.css">
<script type="text/javascript" src="<%=basePath %>public/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>public/canvasUtils.js"></script>
</head>
<body>
<canvas id="canvas"></canvas>
   
   <div id="control">
     
      <div>
        <h1>文字粒子</h1>
        <p style="margin: 0 0 20px 10px;">
          <span id="ball">圆形</span>
          <span id="rect">方块</span>
        </p>
        <p>文字</p>
        <input type="text" id="message" value="hahaha" onchange="change()">
        <p>重力</p>
        <input type="range" id="gra" value="0" min="-1" max="1" step="0.1" onchange="changeV()">
        <p>周期</p>
        <input type="range" id="dur" value="0.4" min="0.1" max="0.99" step="0.01" onchange="changeV()">
        <p>速度</p>
        <input type="range" id="speed" value="0.1" min="0" max="5" ste="0.01" onchange="changeV()">
        <p>半径</p>
        <input type="range" id="radius" value="2" min="1.8" max="20" step="0.1" onchange="changeV()">
        <p>分辨率</p>
        <input type="range" id="res" value="10" min="3" max="20" step="1" onchange="change()">
      </div>
    
       <div id="btn">
         <span></span>
         <span></span>
         <span></span>
       </div>

   </div>
   <p id="tips">*** 点击菜单按钮，滑出侧边栏，进行参数控制 ***</p>
	<script type="text/javascript" src="<%=basePath %>public/utils.js"></script>
	<script type="text/javascript" src="<%=basePath %>public/font/js/shape.js"></script>
	<script type="text/javascript" src="<%=basePath %>public/font/js/particle.js"></script>
   	<script type="text/javascript" src="<%=basePath %>public/font/js/init.js"></script>
	<script type="text/javascript" src="<%=basePath %>public/font/js/sidebar.js"></script>
</body>
</html>