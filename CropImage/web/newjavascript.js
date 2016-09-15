/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var postFile={
    init:function(){
        var t = this;
        t.regional = document.getElementById('label'); //处理区域
        t.getImage = document.getElementById('get_image');  //图片区域
        t.editPic = document.getElementById('edit_pic');    //裁剪区域
        t.editBox = document.getElementById('cover_box');   //遮罩 
        t.px = 0;//background image x //图片导入坐标x
        t.py = 0;//background image y //图片导入坐标y
        t.sx = 15;//crop area x //裁剪区域坐标x
        t.sy = 15;//crop area y //裁剪区域坐标y
        t.sHeight = 100;//crop area height  //裁剪宽度
        t.sWidth = 100;//crop area width //裁剪高度
        document.getElementById('post_file').addEventListener("change",t.handleFiles,false); //点击浏览添加事件
        //保存图片
        document.getElementById('sava_button').onclick = function(){
         t.editPic.height = t.sHeight;
         t.editPic.width = t.sWidth;
         
         var ctx = t.editPic.getContext('2d');
         var images = new Image();
         images.src = t.imgUrl;
         images.onload = function(){
          ctx.drawImage(images,t.sx,t.sy,t.sHeight,t.sWidth,0,0,t.sHeight,t.sWidth);
          document.getElementById('show_pic').getElementsByTagName('img')[0].src = t.editPic.toDataURL();
         };
        };
    },
    handleFiles:function(){
        var fileList = this.files[0];// //如果只有一个文件则只需要访问这个FileList对象中的第一个元素.
        console.log("fileList:"+this.files);
        var oFReader = new FileReader();//实例化一个FileReader对象oFReader
        console.log(oFReader);
        oFReader.readAsDataURL(fileList);//调用readAsDataURL（）方法将文件读取出来并处理成base64编码的格式
        //当图像文件加载后,转换成一个data:URL,传递到onload回调函数中
        oFReader.onload = function(oFREvent){
            postFile.paintImage(oFREvent.target.result);//加载完成后图片画在浏览器上
        };
    },
    paintImage:function(url){
        var t = this;
        var createCanvas = t.getImage.getContext('2d');//返回一个用于在画布上绘图的“2d”环境。当前只允许传“2d”
        var img = new Image();
        img.src = url;
        console.log(img);
        //将原图片按照原大小等比例重画出来
        img.onload = function(){
            //offsetWidth 是对象的可见宽度，包滚动条等边线，会随窗口的显示大小改变
            
            //图片尺寸小于处理界面尺寸，则图片区域等于图片大小
            if(img.width < t.regional.offsetWidth && img.height < t.regional.offsetHeight){
                t.imgWidth = img.width;
                t.imgHeight = img.height;
            }
            //否则按比例缩放
            else{
                var pWidth = img.width/(img.height/t.regional.offsetHeight);
                var pHeight = img.height/(img.width/t.regional.offsetWidth);
                t.imgWidth = img.width>img.height?t.regional.offsetWidth:pWidth;
                t.imgHeight = img.height > img.width?t.regional.offsetHeight:pHeight;
            }
            t.px = (t.regional.offsetWidth - t.imgWidth) / 2+'px';
            t.py = (t.regional.offsetHeight - t.imgHeight) / 2 + 'px';
            
            //定位图片位置
            t.getImage.height = t.imgHeight;
            t.getImage.width = t.imgWidth;
            t.getImage.style.left = t.px;
            t.getImage.style.top = t.py;
            //drawImage(image, sx, sy, sWidth, sHeight, dx, dy, dWidth, dHeight);
            //dx,dy     canvas坐标
            //sx，sy    img坐标
            createCanvas.drawImage(img,0,0,t.imgWidth,t.imgHeight);
            t.imgUrl = t.getImage.toDataURL();
            t.cutImage();
            t.drag();
            t.changClipSize();
        };
    },
    cutImage:function(){
        var t = this;
//        遮罩相关
        t.editBox.height = t.imgHeight;
        t.editBox.width = t.imgWidth;
        t.editBox.style.display = 'block';
        t.editBox.style.left = t.px;
        t.editBox.style.top = t.py;
        
        var cover = t.editBox.getContext('2d');
        cover.fillStyle = "rgba(0,0,0,0.5)";
        cover.fillRect(0,0,t.imgWidth,t.imgHeight);
        //清理出一块裁剪区域
        cover.clearRect(t.sx,t.sy,t.sHeight,t.sWidth);
        
        document.getElementById('show_edit').style.background = 'url('+t.imgUrl+')'+ (-t.sx)+'px '+ (-t.sy)+'px no-repeat';
//        document.getElementById('show_edit').style.background ='url('+t.imgUrl+')';
        document.getElementById('show_edit').style.height = t.sHeight+'px';
        document.getElementById('show_edit').style.width = t.sWidth + 'px';
    },
    drag:function(){
     var t = this;
     var draging = false;
     var startX = 0;
     var startY = 0;
     
     document.getElementById('cover_box').onmousemove = function(e){
       //裁剪区域相对于处理区域的坐标
       var pageX = e.pageX - (t.regional.offsetLeft + this.offsetLeft);
       var pageY = e.pageY - (t.regional.offsetTop + this.offsetTop);
       if(pageX > t.sx && pageX<t.sx + t.sWidth && pageY>t.sy && pageY<t.sy+t.sHeight){
           this.style.cursor = 'move';
           
           this.onmousedown = function(){
               draging = true;
               
               t.ex = t.sx;
               t.ey = t.sy;
               
               startX = e.pageX - (t.regional.offsetLeft + this.offsetLeft);
               startY = e.pageY - (t.regional.offsetTop + this.offsetTop);
           };
           
           window.onmouseup = function(){
               draging = false;
           };
           
           if(draging){
               //t.ex+(pageX - startX)拖动后相对于处理区域的坐标
               if(t.ex+(pageX - startX)<0){
                   t.sx = 0;
               //t.ex+(pageX - startX)+t.sWidth拖动后相对于处理区域的坐标加上裁剪宽度
               }else if(t.ex + (pageX - startX)+t.sWidth>t.imgWidth){
                   t.sx = t.imgWidth - t.sWidth;
               }else{
                   t.sx = t.ex+(pageX - startX);
               }
               
               if(t.ey + (pageY - startY)<0){
                    t.sy = 0;
               }
               else if(t.ey + (pageY - startY)+t.sHeight>t.imgHeight){
                    t.sy = t.imgHeight - t.sHeight;
               }else{
                    t.sy = t.ey+(pageY - startY);
               }
               t.cutImage();
           }else{
               this.style.cursor = 'auto';
           }
       }
     };
    }
};
postFile.init();
