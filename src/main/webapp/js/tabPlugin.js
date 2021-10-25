/*
 *插件名：tab插件
 * 插件描述：实现选项卡效果
 *使用条件：整个选项卡结构分为：头部（按钮部分 .tab-head）和内容（内有各内容页面 .tab-body .tab-cont）
 * 			头部是一个无序列表，有datatarget属性；每个内容页面有name属性，与头部无序列表的datatarget属性值一致
 *
 * 
 * */

(function($){
	$.fn.tabPlugin=function(pro,callback){
		this.pro=pro?pro:null;
		this.callback=callback;
		init.call(this);
		listener.call(this);
	}
	
	function init(){
		var $this=this;
		var $tabBCont=$this.find('.tab-body .tab-cont');
		var $tabHLi=$this.find('.tab-head ul li');
		if($tabBCont.not('.active').size()==$tabBCont.size()){
			$tabBCont.eq(0).show().siblings().hide();
			$tabHLi.eq(0).addClass('active');
		}
	}
	
	//tab切换效果
	function listener(){
		var $this=this;
		var $tabHLi=$this.find('.tab-head ul li');
		var $tabBCont=$this.find('.tab-body .tab-cont');
		
		$tabHLi.click(function(){
			var cTarg=$(this).attr('datatarget');
			$(this).addClass('active').siblings().removeClass('active');
			$('.tab-cont[name='+cTarg+']').show().siblings().hide();
			$this.callback&&$this.callback($(this));
		})
	}
})(Zepto)

