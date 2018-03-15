//将utils定义为window对象下的一个属性，属性值为对象
window.canvasUtils = {};

//定义捕获坐标的方法(web)
window.canvasUtils.captureMouse = function(element){
    //定义一个名为mouse的对象
    var mouse = {x:0,y:0};
    
    //为元素绑定mousemove事件
    element.addEventListener('mousemove',function(event){
        var x,y;
        
        //获取鼠标位于当前屏幕的位置， 并作兼容处理
        if(event.pageX||event.pageY){
            x = event.pageX;
            y = event.pageY;
        }else{
            x = event.clientX + document.body.scrollLeft +document.documentElement.scrollLeft;
            y = event.clientY + document.body.scrollTop +document.documentElement.scrollTop;
        }
        //将当前的坐标值减去元素的偏移位置，即为鼠标位于当前canvas的位置
        x -= element.offsetLeft;
        y -= element.offsetTop;

        mouse.x = x;
        mouse.y = y;
    },false);
     //返回值为mouse对象
     return mouse;
}
//定义捕获坐标的方法(移动端)
window.canvasUtils.captureTouch = function (element) {
    var touch = {
                  x: null,
                  y: null,
                  isPressed: false,
                  event: null
                 };
    var body_scrollLeft = document.body.scrollLeft,
        element_scrollLeft = document.documentElement.scrollLeft,
        body_scrollTop = document.body.scrollTop,
        element_scrollTop = document.documentElement.scrollTop,
        offsetLeft = element.offsetLeft,
        offsetTop = element.offsetTop;
        
   // 绑定touchstart事件
    element.addEventListener('touchstart', function (event) {
      touch.isPressed = true;
      touch.event = event;
    }, false);
    
   // 绑定touchend事件
    element.addEventListener('touchend', function (event) {
      touch.isPressed = false;
      touch.x = null;
      touch.y = null;
      touch.event = event;
    }, false);
    
   //绑定touchmove事件
    element.addEventListener('touchmove', function (event) {
      var x, y,
          touch_event = event.touches[0]; //第一次touch

      if (touch_event.pageX || touch_event.pageY) {
        x = touch_event.pageX;
        y = touch_event.pageY;
      } else {
        x = touch_event.clientX + body_scrollLeft + element_scrollLeft;
        y = touch_event.clientY + body_scrollTop + element_scrollTop;
      }
      //剪去偏移量
      x -= offsetLeft;
      y -= offsetTop;

      touch.x = x;
      touch.y = y;
      touch.event = event;
    }, false);
    //返回touch对象
    return touch;
  };
  
  //碰撞检测(盒子)
  window.canvasUtils.intersects_box = function(rectA, rectB){
	    return !(rectA.x + rectA.width <= rectB.x ||
	             rectB.x + rectB.width <= rectA.x ||
	             rectA.y + rectA.height <= rectB.y ||
	             rectB.y + rectB.height <= rectA.y);
  }
  
  window.canvasUtils.intersects_ball = function(ballA,ballB){
	  var distance = Math.pow((ballA.x-ballB.x),2)+Math.pow((ballA.y-ballB.y),2);
	  if(distance<=Math.pow((ballA.radius+ballB.radius),2)){
		  return true;
	  }else{
		  return false;
	  }
  }
  
  window.canvasUtils.containsPoint = function(rect, x, y){
	    return !(x<rect.x || x>rect.x + rect.width ||
	             y<rect.y || y>rect.y + rect.height);
  }
  