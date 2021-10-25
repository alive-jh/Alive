<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1,maximum-scale=1 target-densitydpi=medium-dpi">
		<title>提交订单</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/dateEle.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/swiper.css" />
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		<script src="<%=request.getContextPath()%>/js/swiper.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	</head>



	<script>
	
	function dopay() {

        if("${userAddress.userName}" =='')
        {
        	alert('请选择收货地址!');
        	return;
        }
         if(document.forms['mallForm'].orderDate.value  =='')
        {
        	alert('请选择服务时间!');
        	return;
        }
            $.ajax({
                type : 'POST',
                url : '<%=request.getContextPath()%>/payment/gopay',
                dataType : 'json',
                data : {
                    "commodityName" : '上门服务',//商品名称
                    "totalPrice" : 1, //支付的总金额
                    "memberOpenId" : '${member.openId}' //支付的总金额
                },
                cache : false,
                error : function() {
                    alert("系统错误，请稍后重试！");
                    return false;
                },
                success : function(data) {
                    if (parseInt(data[0].agent) < 5) {
                        alert("您的微信版本低于5.0无法使用微信支付。");
                        return;
                    }
					orderNumber = data[0].timeStamp;
					
                    WeixinJSBridge.invoke('getBrandWCPayRequest',{
                        "appId" : data[0].appId, //公众号名称，由商户传入  
                        "timeStamp" : data[0].timeStamp, //时间戳，自 1970 年以来的秒数  
                        "nonceStr" : data[0].nonceStr, //随机串  
                        "package" : data[0].packageValue, //商品包信息
                        "signType" : data[0].signType, //微信签名方式:  
                        "paySign" : data[0].paySign //微信签名  
                    },
						function(res) {
                        /* 对于支付结果，res对象的err_msg值主要有3种，含义如下：(当然，err_msg的值不只这3种)
                        1、get_brand_wcpay_request:ok   支付成功后，微信服务器返回的值
                        2、get_brand_wcpay_request:cancel   用户手动关闭支付控件，取消支付，微信服务器返回的值
                        3、get_brand_wcpay_request:fail   支付失败，微信服务器返回的值

                        -可以根据返回的值，来判断支付的结果。
                        -注意：res对象的err_msg属性名称，是有下划线的，与chooseWXPay支付里面的errMsg是不一样的。而且，值也是不同的。
                        */
                        
                        if (res.err_msg == 'get_brand_wcpay_request:ok') {
                            //alert("支付成功！");
                           //alert( document.forms['mallForm'].memberId.value);
                            //window.location.href = "<%=request.getContextPath()%>/electrism/electrismSaveOrder?orderNumber="+orderNumber;
                            document.forms['mallForm'].action = "<%=request.getContextPath()%>/electrism/electrismSaveOrder?orderNumber="+orderNumber;
							document.forms['mallForm'].submit();
                        } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                           // alert("您已手动取消该订单支付。");
                        } else {
                            alert("订单支付失败,请重试!");
                        }
                    });
                }
            });
        }
        

	function toUserAddress()
	{
		
		document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/addressManager?memberId=${member.id}&searchType=electrism&searchType=${searchType}";
		document.forms['mallForm'].submit();
	}


	function saveOrder()
	{
		
		document.forms['mallForm'].action = "<%=request.getContextPath()%>/electrism/electrismSaveOrder";
		
		document.forms['mallForm'].submit();
	}
	</script>
	<body>
		<div class="content">
			<!--头部开始-->
			
			<!--头部结束-->
			<!--主要部分开始-->

			<form name="mallForm" method="post">
			<input type="hidden" name="electrismId" value ="${electrism.id}">
			<input type="hidden" name="payment" value ="30">
			<input type="hidden" name="address" value ="${userAddress.address}">
			<input type="hidden" name="contacts" value ="${userAddress.userName}">
			<input type="hidden" name="mobile" value ="${userAddress.mobile}">
			<input type="hidden" name="orderDate">
			<input type="hidden" name="memberId" value="${member.id}">
			<input type="hidden" name="orderTime">
			<input type="hidden" name="electrismName" value="${electrism.nickName}">
			<input type="hidden" name="serviceItem" value="${itemType}">

			
			<div class="content-body">
				<div class="date-info">
					<h3 class="ele-name">社区电工：<span>${electrism.nickName}</span></h3>
					<div class="clearfix table-box">
						<div class="goods-img">
							
							<c:if test="${itemType == 1}"><img  src="<%=request.getContextPath()%>/images/e01.jpg" /></c:if>
							<c:if test="${itemType == 2}"><img  src="<%=request.getContextPath()%>/images/e02.jpg" /></c:if>
							<c:if test="${itemType == 3}"><img  src="<%=request.getContextPath()%>/images/e03.jpg" /></c:if>
							<c:if test="${itemType == 4}"><img  src="<%=request.getContextPath()%>/images/e04.jpg" /></c:if>
						</div>
						<div class="goods-info">
							<h3>
								<span class="goods-name">
									<c:if test="${itemType == 1}">上门服务</c:if>
									<c:if test="${itemType == 2}">电器维修</c:if>
									<c:if test="${itemType == 3}">线路维修</c:if>
									<c:if test="${itemType == 4}">咨询服务</c:if>
								</span>
								
								<a href="javascript:;"></a> 
							</h3>
							<div class="goods-price">
								<span>￥<strong class="price-num">30.00</strong></span>
							</div>
						</div>
						<div class="goods-num-text">
							<!--<span>×<i>1</i></span>-->
						</div>
					</div>
				</div>
				
				<div class="time-addr">
					<h3 class="sub-title">请选择时间、地址</h3>
					<ul>
						<li>
							<a id="timer-sbox" class="clearfix" href="javascript:;">
								<i class="input-icon time"></i>
								<span>服务时间:</span>
								<p class="time-btn"><span class="time-show" id="time-show"><b></b><i>请输选择上门时间</i></span></p>
								<b class="arrow"></b>
							</a>
						</li>
						<li>
							<a  class="clearfix"href="javascript:toUserAddress();">
								<i class="input-icon user"></i>
								<span>顾客名称:</span>
								<input type="text" placeholder="请输入名称"  value="${userAddress.userName}" readonly="readonly" />
								<b class="arrow"></b>
							</a>
						</li>
						<li>
							<a  class="clearfix"href="javascript:toUserAddress();">
								<i class="input-icon tel"></i>
								<span>手机号码:</span>
								<input type="text" placeholder="请输入手机号码" value="${userAddress.mobile}" readonly="readonly" />
								<b class="arrow"></b>
							</a>
						</li>
						<li>
							<a  class="clearfix"href="javascript:toUserAddress();">
								<i class="input-icon addr"></i>
								<span>顾客地址:</span>
								<input type="text" placeholder="请选择地址" value="${userAddress.address}" readonly="readonly" />
								<b class="arrow"></b>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<!--主要部分结束-->
			
			<!--提交按钮开始-->
			<div class="control-btn fixed-block">
				<input type="button" value="确认" onclick="dopay()"/>
			</div>
			<!--提交按钮结束-->
			
			<!--时间选择器开始-->
			<div id="mask"></div>
		
			<div class="time-box" id="time-box">
				<div class="time-head">
					<h3>预约上门时间</h3>
					<input type="button" name="close-btn" id="close-btn" value="完成"/>
					
				</div>
					<div class="time-body">
						<div class="time-left">
							<p>上下滑动获取时间</p>
							<div style="overflow:hidden;position: relative;">
								<div class="select-box"></div>
								<div class="date-list swiper-container">
									<ul class="date-box swiper-wrapper" id="date-box">
										<li class="swiper-slide">今天</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="time-right">
							<p>预约时段：09:00-20:00</p>
							<div style="overflow:hidden;position: relative;">
								<div class="select-box" id="time-block"><span>上午</span></div>
								<div class="hour-list swiper-container">
									<ul class="hour-box swiper-wrapper" id="hour-box">
										<li class="swiper-slide">9:00</li>
									</ul>
								</div>
							</div>
						</div>
					<div style="clear: both;height:0;overflow: hidden;"></div>
				</div>
			</div>
			</form>
			<!--时间选择器结束-->
			
			<!--底部菜单开始-->
			<!--<div class="footer-nav">
				<ul class="clearfix fixed-block">
					<li>
						<a href="index.html">
							<span></span>
							<p>首页</p>
						</a>
					</li>
					<li>
						<a href="order.html">
							<span></span>
							<p>订单</p>
						</a>
					</li>
					<li>
						<a href="me.html">
							<span></span>
							<p>我的</p>
						</a>
					</li>
					<li>
						<a href="more.html">
							<span></span>
							<p>更多</p>
						</a>
					</li>
				</ul>
			</div>-->
			<!--底部菜单结束-->
		</div>
	</body>
	<script>
				
	$(function(){
		//日期部分
		var swiper1 = new Swiper('.date-list', {
			direction : 'vertical',
			slidesPerView : 9,
			centeredSlides : true,
			mousewheelControl : true,
			onInit: function(swiper1){
				swiper1.removeSlide(0);
				var s=new Array();
				var t="";
				var tdata="";
				for(i=0;i<7;i++){//设置日期
					if(i==0){t="今天";}
					else if(i==1){t="明天";}
					else{
						t=GetDateStr(i);
					}
					tdata=GetDateStr(i);
					s[i]='<li class="swiper-slide" data="'+tdata+'">'+t+'</li>';
				}
					swiper1.appendSlide(s);
					swiper1.update();
					swiper1.slideTo(0,0);
			},
			onSlideChangeEnd: function(swiper1){
				//设置时间段的可用性
				setHourData(swiper1.activeIndex);
				timeChanged()
				
			}
		});
		
		//时间部分
		var swiper2 = new Swiper('.hour-list', {
			direction : 'vertical',
			slidesPerView : 9,
			centeredSlides : true,
			mousewheelControl : true,
			onInit: function(swiper2){
				swiper2.removeSlide(0);
				var s=new Array();
				var t="";
				//创建时间段并返回当最终可用时间的索引(可传入4个参数，第一个为最终的小时，第二个为最晚的小时，第三个为用于存储的时间段数组，第4个为时间段的间隔分钟数--可选)
				var cN=createHours(9,20,s);
				swiper2.appendSlide(s);
				swiper2.update();
				//设置时间段的可用性
				setHourData(swiper1.activeIndex);
				swiper2.slideTo(cN,0);
			},
			onSlideChangeEnd: function(swiper2){
				setHourData(swiper1.activeIndex);
				//设置上午、下午、晚上
				if(parseInt($('#hour-box li.swiper-slide-active').html())<=12){
					$('#time-block span').html('上午');
				}else if(parseInt($('#hour-box li.swiper-slide-active').html())>12&&parseInt($('#hour-box li.swiper-slide-active').html())<=18){
					$('#time-block span').html('下午');
				}else if(parseInt($('#hour-box li.swiper-slide-active').html())>18){
					$('#time-block span').html('晚上');
				}
				timeChanged()
			}
		});
		
		//创建小时
		function createHours(start,end,s,k){
			//start最早的小时部分时间
			//end最晚的小时部分时间
			//s用于存储时间段的数组
			//k为每个时间段间隔时间，以分钟为单位
			var dateN=new Date();//当前时间
			var dateH=new Date();//当前时间无小时及以后的时间
			dateH.setHours(0,0,0,0);
			var diff=parseInt(dateN.getTime()-dateH.getTime());//当前小时及其以后的毫秒
			var currentN=0;//当前小时索引
			for(var i=start;i<(end+1);i++){
				//_k每个时间段分钟部分的零时变量
				//_kk每个时间段由于显示的分钟部分的零时变量
				//num每小时有多少段时间
				//dt每个时间段从小时开始的毫秒数
				var _k=0,_kk='00',num=0;
				var dt=i*1000*3600;
				if(typeof k!='undefined'&&k!=0){
					
					num=Math.floor(60/k);
					if(i==end){
						s[(i-start)*num]='<li class="swiper-slide" datatime="'+(i>=10?i:("0"+i)) + ':00">' + (i>=10?i:("0"+i)) + ':00</li>';
					}else{
						for(var j=0;j<num;j++){
							_k=k*j*1000*60;
							_kk=(k*j)>=10?(':'+(k*j)):(':0'+(k*j));
							dt=_k+dt;
							if(dt<diff){
								currentN=(i-start)*num+j+1;
							}
							s[((i-start)*num)+j]='<li class="swiper-slide" datatime="'+dt+'-'+(i>=10?i:('0'+i))+_kk+'">'+(i>=10?i:('0'+i))+_kk+'</li>';
						}
					}
				}else{
					dt=_k+dt;
					if(dt<diff){
						currentN=i-start+1;
					}
					s[i-start] ='<li class="swiper-slide" datatime="'+ dt + '-' + (i>=10?i:("0"+i)) + ':00">' + (i>=10?i:('0'+i))+':00</li>';
				}
			}
			return currentN;
		}
		
		//设置时间段的可用性
		function setHourData(d){
			//d为当前日期索引
			var $hourList=$('.hour-list .swiper-slide');
			$hourList.each(function(i,item){
				var dateNow=new Date();
				var daySelect=new Date();
				daySelect.setDate(daySelect.getDate()+d);
				daySelect.setHours(0,0,0,0);
				var _dataTime=parseInt($hourList.eq(i).attr('datatime'));
				if(parseInt(dateNow.getTime())>(_dataTime+parseInt(daySelect.getTime()))){
					$hourList.eq(i).addClass('disabled');
				}else{
					$hourList.eq(i).removeClass('disabled');
				}
			})
			//设置额外不可用时间段(可传入两个参数，都为数组，第一个表示日期，第二个表示时间点，具体用法参照函数声明部分)
			//disabledTime(['一','六'],['15:00','18:00']);
			disabledTime2(["${timeStr}"]);
			//disabledTime2(['2015-12-11 17:00','2015-12-12 17:00','2015-12-13 17:00','2015-12-08 17:00'])
		}
		
		//按完整时间设定时间不可用
		function disabledTime2(dateArr){
			//dateArr为不可用时间数组，数据类型为字符串--'2015-11-06 17:00'
			var $hourList=$('.hour-list .swiper-slide');
			var _hasDisable=false;
			var _disableIndex=0;
			var cDayS=$('#date-box li.swiper-slide-active').attr('data');
			var numDate=cDayS.replace(/[^\d]/g, " ").split(" ");
			numDate=[fomateNum(numDate[0]),fomateNum(numDate[1])];
			cNday='2016-'+numDate.join("-")
			//设置相应时间不可用
			for(var j=0;j<dateArr.length;j++){
				if(cNday==dateArr[j].split(" ")[0]){
					_hasDisable=true;
					//setFull($hourList,true)
					_disableIndex=j;
				}
			}
			setFull($hourList,_hasDisable)
			
			//设置约满
			function setFull(obj,f){
				
				if(f){
					
					obj.each(function(i,item){
						
						var cHour=$(this).attr('dataTime').split('-')[1];
						

						for(var j=0;j<dateArr.length;j++){
							
							if(cNday==dateArr[j].split(" ")[0]){
								
								
								var tempDD = dateArr[j].split(" ")[1].replace(":00","");
								tempDD =(new Number(tempDD)-1)+":00";
								if(cHour==tempDD)
								{
									$(this).addClass('disabled');
									document.forms['mallForm'].orderDate.value  ='';
								}
								//设置约满前一小时不能预约
								tempDD = dateArr[j].split(" ")[1].replace(":00","");
								tempDD =(new Number(tempDD)+1)+":00";
								if(cHour==tempDD)
								{
									$(this).addClass('disabled');
									document.forms['mallForm'].orderDate.value  ='';
								}
								//设置约满后一小时不能预约
								if(cHour==dateArr[j].split(" ")[1])
								{
									
									$(this).addClass('disabled');
									$(this).html('<font color="red">约满</font>');
									document.forms['mallForm'].orderDate.value  ='';
									break;
								}
								else
								{
									
									$hourList.eq(i).html(cHour);
								}
								
							}
						}

							
						
						
						

					})
				}
				else{
					
					obj.each(function(i,item){
						var cHour=$(this).attr('dataTime').split('-')[1];
						$hourList.eq(i).html(cHour);
					})
				}
			}
			
			//将小与10的数字转换成0d形式
			function fomateNum(d){
				return d>=9?d:'0'+d;
			}
			
		}
		
		
		
		//文本框时间变化
		function timeChanged(){
			if(isChanged){
				if($('#hour-box li.swiper-slide-active').hasClass('disabled')||$('#hour-box li.swiper-slide-active').hasClass('disabled')){
					$('#time-show i').html('请输选择上门时间');
					$('#time-show b').html('');
					$('#close-btn').prop('disabled','disabled');
					//alert('时间不可用，请选择其他时间')
				}else{
					$('#close-btn').prop('disabled','');
					$('#time-show b').html($('#date-box li.swiper-slide-active').attr('data'));
					$('#time-show i').html($('#hour-box li.swiper-slide-active').html()+'('+$('#time-block span').html()+')');
				}
			}
		}
		
		
		//获取时间---将日期换成星期格式	
		function GetDateStr(AddDayCount) {
			var dayArry=['周日','周一','周二','周三','周四','周五','周六']
			var dd = new Date();
			dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
			var y = dd.getFullYear();
			var m = dd.getMonth()+1;//获取当前月份的日期
			var d = dd.getDate();
			var w = dayArry[dd.getDay()];
			return m+"月"+d+"日 "+w;
		}	
		
		//按钮操作效果
		var isChanged=0;
		$('#timer-sbox').click(function(){
			isChanged=1;
			$('#time-box').animate({'bottom':'0'},400);
			$('#mask').show();
			$('#time-show').addClass('select');
			timeChanged()
			
		})
		
		
		$('#close-btn').click(function(){
			

			var dd = new Date();
			document.forms['mallForm'].orderDate.value = $('#date-box li.swiper-slide-active').attr('data');
			
			var month = document.forms['mallForm'].orderDate.value.substring(0,2);
			month = month.replace("月","");
			if(month <10)
			{
				month = '0'+month;
			}
			var day = document.forms['mallForm'].orderDate.value.substring(2,4);
		
			
			day = day.replace("日","");
			if(day <10)
			{
				day = '0'+day;
			}
			document.forms['mallForm'].orderDate.value = dd.getFullYear()+"-"+month+"-"+day;
			
			document.forms['mallForm'].orderTime.value = $('#hour-box li.swiper-slide-active').html();
			$('#time-box').animate({'bottom':'-100%'},40);
			$('#mask').hide();
		})
		$('#mask').click(function(){
			$('#time-box').animate({'bottom':'-100%'},40);
			$('#mask').hide();
		})
	})
	</script>
</html>

