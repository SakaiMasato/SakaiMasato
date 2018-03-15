function Particle(x,y,type){
	this.radius = 1.1;
	this.radiusFur = utils.randomInt(radVal,radVal+10);//元素长宽的变化区间中的随机值(方形)
	this.x = x;
	this.y = y;
	this.vX = 0;//x方向的速度，默认为0
	this.vY = 0;//y方向上的速度，默认为0
	this.type = type;
	this.color = colors[Math.floor(Math.random()*colors.length)];//颜色数组中随机颜色
	
	this.dying = false;//判定改组成部分是否存活,默认存活
	this.base = [x, y];//用来重置元素的初始位置，例如：当元素因为重力原因离开初始位置是根据其重置
	this.gravity = graVal;//重力
	
	
	//当调整速度滑动控件对速度进行改变
	this.getSpeed = function(){
		return Math.sqrt(Math.pow(this.vX,2)+Math.pow(this.vY,2));//拿到速度
	}
	
	this.setSpeed = function(speed){
		var angle = this.getAngle();
		this.vX = speed * Math.cos(angle);
		this.vY = speed * Math.sin(angle);
	}
	
	//拿到角度
	this.getAngle = function(){
		return Math.atan2(this.vX,this.vY);
	}
	
	this.setAngle = function(angle){
		var speed = this.getSpeed();
		this.vX = speed*Math.cos(angle);
		this.vY = speed*Math.sin(angle);
	}
			
	this.draw = function(ctx){
		
		this.x += this.vX;
		this.vY += graVal;//重力加速度
		this.y += this.vY;
		
		if(this.y<0 || this.radius<1){//this.y<0因为重力的原因是this.radius<1是组成球的半径小到一个极限，使其存活
			this.dying = false;
			this.radiusFur = utils.randomInt(radVal,radVal+3);//元素长宽的变化区间中的随机值(方形)
			this.radius = 1.1;
			this.setSpeed(spdVal);
			this.setAngle(utils.degreesToRads(Math.random()*360));//每个元素的初始角度不同，所以在有初速度时运动方向不同
			this.x = this.base[0];//重置初始位置
			this.y = this.base[1];
		}
		
		if(this.radius < this.radiusFur && this.dying === false){
	      this.radius += durVal;
	    }else{
	      this.dying = true;
	    }
	    if(this.dying === true){
	      this.radius -= durVal;
	    }
		ctx.save();
		ctx.fillStyle = this.color;
		ctx.beginPath();
		switch(this.type){
			case 'ball':
				ctx.arc(this.x, this.y, this.radius, Math.PI * 2, false);
				break;
			case 'rect':
				ctx.fillRect(this.x, this.y,this.radiusFur,this.radiusFur);
				break;
		}
		ctx.closePath();
		ctx.fill();
		ctx.restore();
	}
	this.setSpeed(0.1+Math.random()*1);//速度之所以在角度之前是为了给文字变换一个效果
	this.setAngle(utils.degreesToRads(Math.random()*360));//每个元素的初始角度不同，所以在有初速度时运动方向不同
}