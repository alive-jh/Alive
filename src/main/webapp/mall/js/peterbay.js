//页面特效代码

$(function(){
	//购买信息弹出效果
	shopMesg();
	shopNum();
	shopSize();
	tab();
	checkbox('addrCheck');
	checkbox('orderCheck');
	checkbox('orderDelete');
	
	
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
		var $numPlus=$('.num-btn .num-plus');
		var $numMinus=$('.num-btn .num-minus');
		var $numText=$('.num-btn .num-text');
		
		
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
			var $tnumText=$(this).parent().find('.num-text');
			var $tnumMinus=$(this).parent().find('.num-minus');
			var num=$tnumText.val();
			$tnumText.val(++num);
			if($tnumText.val()>1){
				$tnumMinus.removeClass('disabled');
			}
		})
		
		$numMinus.click(function(){
			var $tnumText=$(this).parent().find('.num-text');
			var $tnumMinus=$(this).parent().find('.num-minus');
			var num=$tnumText.val();
			
			if($tnumText.val()<=1){
				$tnumMinus.addClass('disabled');
				return false;
			}else{
				$tnumMinus.removeClass('disabled');
			}
			$tnumText.val(--num);
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
	
	//模拟复选框效果
	function checkbox(name){
		var $checkAll=$('div div[name='+name+'].all');
		var $checkSingl=$('li div[name='+name+']').not('.all');
		var $checkLi=$('div div[name='+name+']');
		
		checked();

		function checked(){
			var _check=true;
			$checkLi.each(function(){
				if($(this).find('input').prop('checked')){
					$(this).addClass('active');
				}else{
					$(this).removeClass('active');
					if(!$(this).hasClass('all')){
						_check=false;
					}
				}
			})
			
			return _check;
		}
		
		
		
		$checkLi.find('input').click(function(e){
			e.stopPropagation();//阻止父元素事件
		})
		
		$checkAll.click(function(){
			$(this).find('input').trigger('click');//触发子元素事件
			$checkSingl.find('input').prop('checked',$(this).find('input').prop('checked'));
			checked();
		})
		
		$checkSingl.click(function(){
			$(this).find('input').trigger('click');//触发子元素事件
			checked();
			var _check=checked();
			$checkAll.find('input').prop('checked',_check);
			checked();
		})
	}
	
})
