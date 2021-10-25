/*
 
 * 图片列表功能：1、列表中的图片宽度和高度相等 2、点击图片可放大预览
 * */

(function($){
	$.fn.imgList=function(width,height){
		this.width=width||0;
		this.height=height||0;
		init.call(this);
		listener.call(this);
	}
	function init(){
		//设置图片列表的宽高
		var $this=this;
		var _width=0;
		var _height=0;
		if($this.width){
			_width=$this.width;
		}else{
			_width=$(this).eq(0).css('width')
		}
		if($this.height){
			_height=$this.height;
		}else{
			_height=$(this).eq(0).css('height')
		}
		$this.each(function(){
			$(this).css({'width':_width,'height':_height})
		})
		
		//准备弹出的图片列表
	}
	
	function listener(){
		
	}
	
})(Zepto)

