/*
 *插件名：全选功能插件
 * 插件描述：实现全选和全不选功能
 *使用条件： input[type="checkbox"]的name必须为check-single
 * 			全选按钮必须有all样式
 *
 * 
 * */
(function($){
	$.fn.checkAll=function(arr,callback){
		this.arr=arr;
		this.callback=callback;
		this.checkedArr=[];
		init.call(this);//初始化函数，作用是初始化html结构
		listener.call(this);
		return(this);
	}
	function init(obj){
		var $this=obj?obj:this;
		var $chechSingleI=$this.find('input[name="check-single"]').not('.all');
		$chechSingleI.each(function(i,item){
			$this.checkedArr[i]=$(this).prop('checked')?1:0;
			$this.arr[i]=$this.checkedArr[i];
			this.index=i;
		})
	}
	
	function isCheckedAll(obj){
		for(var i=0;i<obj.length;i++){
			if(obj[i]==0){
				return false;
			}
		}
		return true;
	}
	
	function listener(){
		var $this=this;
		var $chechSingleI=$this.find('input[name="check-single"]').not('.all');
		var $checkAllI=$this.find('input[name="check-single"].all');
		
		//复选框按钮点击
		$chechSingleI.click(function(){
			init($this);
			if(isCheckedAll($this.checkedArr)){
				$checkAllI.prop('checked',true);
			}else{
				$checkAllI.prop('checked',false);
			}
			$this.callback&&$this.callback($this.checkedArr,$(this));
		})
		
		//全选按钮点击
		$checkAllI.click(function(){
			var _check=$(this).prop('checked');
			$chechSingleI.prop('checked',_check);
			init($this);
			$this.callback&&$this.callback($this.checkedArr,$chechSingleI);
		})
	}
	
})(Zepto);
