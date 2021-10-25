//订单结算部分

$(function(){
	
	orderEd();
	priceCheck();
	orderEdit();
	
	
	//订单编辑按钮效果
	function orderEd(){
		var $oEdit=$('.shop-name .order-edit');
		var $oFinish=$('.shop-name .order-finish');
		
		$oEdit.click(function(){
			var $numText=$(this).parent().parent().find('.goods-price p');
			var $numBox=$(this).parent().parent().find('.goods-price .goods-num');
			var $numInput= $numBox.find('input');
		
			$('.control-btn.oedit').css('display','block');
			$('.control-btn.ocount').css('display','none');
			$(".m-checkbox[name='orderDelete']").css('display','inline-block');
			$(".m-checkbox[name='orderCheck']").css('display','none');
			
			$(this).hide();
			$(this).parent().find('.order-finish').show();
			
			$numText.hide();
			$numInput.each(function(i,item){
				$(this).val($numText.eq(i).html().substring(1,$numText.html().length));
			})
			$numBox.show();
			
			orderEdit()
		})
		
		$oFinish.click(function(){
			var $numText=$(this).parent().parent().find('.goods-price p');
			var $numBox=$(this).parent().parent().find('.goods-price .goods-num');
			var $numInput= $numBox.find('input');
			var $numInput= $numBox.find('input');
			
			
			$('.control-btn.oedit').css('display','none');
			$('.control-btn.ocount').css('display','block');
			$(".m-checkbox[name='orderDelete']").css('display','none');
			$(".m-checkbox[name='orderCheck']").css('display','inline-block');
			
			$(this).hide();
			$(this).parent().find('.order-edit').show();
			
			$numText.show();//alert($numInput.length)
			$numText.each(function(i,item){
				$(this).html('×'+$numInput.eq(i).val());
			})
			$numBox.hide();
			priceCheck();
		})
	}
	
	//价格计算
	function priceCheck(){
		var $checkAll=$('div div[name='+"orderCheck"+'].all');
		var $checkSingl=$('li div[name='+"orderCheck"+']').not('.all');
		var $checkLi=$('div div[name='+"orderCheck]");
		var _price=0;
		var _number=0;
		
		priceChange();
		function priceChange(){
			_price=0;
			_number=0;
			$checkLi.each(function(){
				if($(this).find('input').prop('checked')){
					$(this).addClass('active');
					if(!$(this).hasClass('all')){
						_number+=parseInt($(this).parent().find('.goods-price p').html().substring(1));
						_price+=parseInt($(this).parent().find('.goods-price h3').html().substring(1))*parseInt($(this).parent().find('.goods-price p').html().substring(1));
					}
				}else{
					$(this).removeClass('active');
				}
			})
			$('.control-btn.ocount h3 span').html(_price);
			$('.control-btn.ocount input').attr('value','结算('+_number+')');
			if(_number==0){
				$('.control-btn.ocount .countBtn').attr('disabled','disabled').addClass('disabled');
			}else{
				$('.control-btn.ocount .countBtn').removeAttr('disabled').removeClass('disabled');
			}
			
		}
		
		$checkLi.click(function(){
			priceChange();
		})
	}
	
	//订单编辑
	function orderEdit(){
		var $checkAll=$('div div[name='+"orderDelete"+'].all');
		var $checkSingl=$('li div[name='+"orderDelete"+']').not('.all');
		var $checkLi=$('div div[name='+"orderDelete]");
		var $deletBtn=$('.control-btn.oedit .deleteBtn');
		var _check=false;
		
		orderChange();
		function orderChange(){
			_check=false;
			$checkLi.each(function(){
				if($(this).find('input').prop('checked')){
					$(this).addClass('active');
					_check=true;
					
				}else{
					$(this).removeClass('active');
				}
			})
			if(!_check){
				$('.control-btn.oedit').addClass('disabled');
				$deletBtn.addClass('disabled').attr('disabled','disabled');
			}else{
				$('.control-btn.oedit').removeClass('disabled');
				$deletBtn.removeClass('disabled').removeAttr('disabled');
			}
			
		}
		
		$checkLi.click(function(){
			orderChange();
		})
		
		$deletBtn.click(function(){
			$checkLi.each(function(){
				if($(this).find('input').prop('checked')){
					if(!$(this).hasClass('all')){
						$(this).parent().parent().remove();
					}
				}
			})
			if($('.tab-cont.active .order-mesg').length==0){
				var empty="<div class='empty'>"+
							"<h3>购物车快饿瘪了T.T</h3>"+
							"<p>快给我挑点宝贝</p>"+
							"<input type='button' value='去逛逛'/>"+
							"</div>"
				$('div.tab-cont[name="shoping-cart"]').html(empty);
				
				$('.footer').css('margin','0');
				$('.empty input').click(function(){
					window.location='index.html';
				})
			}
		})
	}
	
	
})
