(function($){
	$.fn.progressBar=function(){
		listener.call(this);
	}
	
	function listener(){
		var $this=this;
		$this.each(function(){
			var _data=$(this).attr('data');
			$(this).find('i').animate({'width':_data},500);
		})
		
	}
})(Zepto)
