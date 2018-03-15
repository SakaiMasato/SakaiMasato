function Shape(x,y,text){
	this.x = x;
	this.y = y;
	this.text = text;
	this.size = 150;
	
	this.placement = [];//该数组用来存放绘制文字的元素
}

Shape.prototype.getValue = function(ctx){
	//将文字绘制在canvas中
	ctx.textAlign = "center";
	ctx.font = this.size + 'px serif';
	ctx.fillText(this.text,this.x,this.y);
	
	//拿到整个画布的数据
	var canvasData = ctx.getImageData(0,0,W,H);//画布的二维数组数据以数组形式放在canvasData中
	var buffer32 = new Uint32Array(canvasData.data.buffer);
	
	for(var j=0; j < H; j += gridY){
	 	for(var i=0 ; i < W; i += gridX){
	 		if(buffer32[j * W + i]){
	 			var particle = new Particle(i, j, type);//行遍历buffer32里面的数据
	 			this.placement.push(particle);
	 		}
	 	}
	 }
	ctx.clearRect(0,0,W,H);
} 

