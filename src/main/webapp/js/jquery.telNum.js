/*
 *电话号码格式插件
 * 使电话号码的中间4位数用*号代替
 *
 */

(function($){
	$.fn.telNum=function(){
		listener.call(this);
	}
	function listener(){
		var $this=this;
		var _num=$this.text();
		var _num01="",_num02="****",_num03="";
		_num01=_num.substring(0,3);
		_num03=_num.substring(8,11);
		_num=_num01+_num02+_num03;
		$this.text(_num);
	}
})(Zepto)
