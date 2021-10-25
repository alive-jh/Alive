/**
 * 主要功能：实现元素滚动到窗口顶部时，固定在顶部 
 * 
 * 调用方法：obj.fixedBlock({height:null,top:0,fixedCallback:null,staticCallback:null}); 
 * //obj为要定位的元素，height为元素固定时滚动条的高度（默认为元素距离窗口顶部的高度）
 * //top为元素固定后距离窗口顶部的高度（默认为0）
 *
 *
 */
(function($){
	$.fn.fixedBlock=function(_config){
		var config={
			height:null,
			top:0,
			fixedCallback:null,
			staticCallback:null
		}
		$.extend(true,config,_config);
		console.log(config)
		this.height=config.height;
		this.top=config.top||0;
		this.fixedCallback=config.fixedCallback;
		this.staticCallback=config.staticCallback;
		listener.call(this);
	}
	
	function listener(){
		var $this=this;
		var _height=0;
		if(typeof $this.height=='number'){
			_height=$this.height;
		}else{
			_height=$this.offset().top;
		}
		$(document).scroll(function(){
			if(getScrollTop()>_height){
				$this.css({'position':'fixed','top':$this.top,'zIndex':999});
				$this.fixedCallback&&$this.fixedCallback.call($this);
			}else{
				$this.css({'position':'static','top':0,'left':0});
				$this.staticCallback&&$this.staticCallback.call($this);
			}  
		});
	}
	
	function getScrollTop() {  
        var scrollPos;  
        if (window.pageYOffset) {  
        scrollPos = window.pageYOffset; }  
        else if (document.compatMode && document.compatMode != 'BackCompat')  
        { scrollPos = document.documentElement.scrollTop; }  
        else if (document.body) { scrollPos = document.body.scrollTop; }   
        return scrollPos;   
	}  

	
})(Zepto)


