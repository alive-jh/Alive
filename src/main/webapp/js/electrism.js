$(function(){
	var $orderList=$('.goods-list ul li');
	var $checkBtn=$('.check-btn input[name="goods-check"]',$orderList);
	var $checkAll=$('.check-all input[name="goods-check"].all');
	var $plusBtn=$('.goods-num .plus-btn',$orderList);
	var $minusBtn=$('.goods-num .minus-btn',$orderList);
	var $numText=$('.goods-num .num-text',$orderList);
	var $deleteBtn=$('.goods-info h3 a',$orderList);
	var $sPrice=$('.goods-info .goods-price .price-num',$orderList);
	var listNum=$checkBtn.length;
	var priceSArr=new Array(listNum);//每个商品的总价
	var priceArr=new Array(listNum);//每个商品的总价
	var numArr=new Array(listNum);//每个商品的数量
	var checkArr=new Array(listNum);//每个商品是否选中
	var priceAll=0;
	
	init();
	
	//初始化
	function init(){
		markNum($plusBtn);
		markNum($minusBtn);
		markNum($checkBtn);
		//markNum($numText);
		
		//产品数量
		$numText.each(function(i,item){
			if($(this).html()=='1'){
				$(this).parent().find('.minus-btn').addClass('disable');
			}else{
				$(this).parent().find('.minus-btn').removeClass('disable');
			}
			numArr[i]=parseInt($(this).html());
		})
		
		//产品单价
		$sPrice.each(function(i,item){
			priceSArr[i]=parseFloat($(this).html());
		})
		
		//复选框
		$checkBtn.each(function(i,item){
			if($(this).prop('checked')==false){
				checkArr[i]=0;
			}else{
				checkArr[i]=1;
			}
		})
		//总价格
		countPrice();
	}
	
	//计算价格
	function countPrice(){
		priceAll=0;
		$checkBtn.each(function(i,item){
			
			priceAll+=checkArr[i]*numArr[i]*priceSArr[i];
		})
		$('.all-price-num').html(priceAll.toFixed(2));
	}
	
	//编号
	function markNum(obj){
		obj.each(function(i,item){
			$(this).attr('index',i);
		})
	}
	
	
	//删除按钮效果
	$deleteBtn.click(function(){
		var i=$(this).parent().parent().parent().find('.plus-btn').attr('index');
		
		$(this).parent().parent().parent().remove();
		checkArr[i]=0;
		countPrice();
	})
	
	//全选按钮
	$checkAll.click(function(){
		var $this=$(this);
		$checkBtn.each(function(i,item){
			var _checked=$this.prop('checked')?1:0;
			$(item).prop('checked',$this.prop('checked'));
			checkArr[i]=_checked;
		})
		countPrice();
	})
	
	//复选框点击效果
	$checkBtn.click(function(){
		var _checkAll=true;
		if($(this).prop('checked')==false){
			checkArr[$(this).attr('index')]=0;
		}else{
			checkArr[$(this).attr('index')]=1;
		}
		for(var i=0;i<checkArr.length;i++){
			if(checkArr[i]==0){
				_checkAll=false;
			}
		}
		$checkAll.prop('checked',_checkAll);
		countPrice();
	})
	
	//数字选择器效果
	$plusBtn.click(function(){
		var i=$(this).attr('index');
		var $numText=$(this).parent().find('.num-text');
		var _num=parseInt($numText.html());
		$numText.html(++_num);
		if(_num>1){
			$(this).parent().find('.minus-btn').removeClass('disable');
		}
		numArr[i]=_num;
		countPrice();
	})
	$minusBtn.click(function(){
		var i=$(this).attr('index');
		var $numText=$(this).parent().find('.num-text');
		var _num=parseInt($numText.html());
		if(_num>1){
			$numText.html(--_num);
		}
		if(_num<2){
			$(this).addClass('disable');
		}
		
		numArr[i]=_num;
		countPrice();
	})

})
