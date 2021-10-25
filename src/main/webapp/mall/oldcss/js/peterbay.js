//页面特效代码

$(function(){
	//购买信息弹出效果
	shopMesg();
	shopNum();
	shopSize();
	tab();
	function shopMesg(){
		var $buyBtn=$('.buy-response');
		var $mask=$('.mask');
		var $shopBox=$('#shop-box');
		var $closeBtn=$('#close-btn');
		$buyBtn.click(function(){
			$shopBox.css('display','block');
			$shopBox.find('.shop-message').animate({bottom:0+'px'},400);
			return false;
		})
		$mask.add($closeBtn).click(function(){
			$shopBox.find('.shop-message').animate({bottom:'-100%'},400,'ease-in-out',function(){
				$shopBox.css('display','none');
			});
		
		});
	}
	
	//尺寸选择
	function shopSize(){
		var $size=$('#shop-size li');
		
		$size.click(function(){
			$(this).addClass('active').siblings().removeClass('active');
		})
	}
	
	//购买数量选择
	function shopNum(){
		var $numPlus=$('#num-btn .num-plus');
		var $numMinus=$('#num-btn .num-minus');
		var $numText=$('#num-btn .num-text');
		$numText.blur(function(){
			if($(this).val()<1){
				$(this).val(1);
				$numMinus.addClass('disabled');
			}
			if(!(/^\d+$/g.test($(this).val()))){
				alert('请输入有效数字！');
				$(this).val(1);
			}
			if($(this).val()>1){
				$numMinus.removeClass('disabled');
			}
		})
		$numPlus.click(function(){
			var num=$numText.val();
			$numText.val(++num);
			if($numText.val()>1){
				$numMinus.removeClass('disabled');
			}
		})
		
		$numMinus.click(function(){
			if($numText.val()<=1){
				$numMinus.addClass('disabled');
				return false;
			}
			var num=$numText.val();
			$numText.val(--num);
		})
	}
	
	//tab切换效果
	function tab(){
		var $tabHLi=$('.tab-head ul li');
		var $tabBCont=$('.tab-body .tab-cont');
		
		$tabHLi.click(function(){
			var cTarg=$(this).attr('datatarget');
			$(this).addClass('active').siblings().removeClass('active');
			$('.tab-cont[name='+cTarg+']').show().siblings().hide();
		})
	}
})
