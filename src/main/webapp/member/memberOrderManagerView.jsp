<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>订单详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="viewport" content="user-scalable=yes, width=640">
		<script type="text/javascript">
		        if(/Android (\d+\.\d+)/.test(navigator.userAgent)){
		            var version = parseFloat(RegExp.$1);
		            if(version>2.3){
		                var phoneScale = parseInt(window.screen.width)/640;
		                document.write('<meta name="viewport" content="width=640, minimum-scale = '+ phoneScale +', maximum-scale = '+ phoneScale +', target-densitydpi=device-dpi">');
		            }else{
		                document.write('<meta name="viewport" content="width=640, target-densitydpi=device-dpi">');
		            }
		        }else{
		            document.write('<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">');
		        }


				function saveOrder()
				{
					document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/memberPayment";
					document.forms['mallForm'].submit();
				}
		    </script><meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/fillOrder.css" />

		<script src="<%=request.getContextPath()%>js/zepto.js"></script>
		
	</head>
	<script type="text/javascript">

	function toUserAddress()
	{
		document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/addressManager?memberId=${mallOrder.memberId}";
		document.forms['mallForm'].submit();
	}

var mch_id ='';
        function dopay() {
            $.ajax({
                type : 'POST',
                url : '<%=request.getContextPath()%>/payment/gopay',
                dataType : 'json',
                data : {
                    "commodityName" : '${mallOrder.productList[0].productName}',//商品名称
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
					mch_id = data[0].mch_id;
					
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
                            window.location.href = "<%=request.getContextPath()%>/member/saveOrder?mchId="+mch_id;
                        } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                            alert("您已手动取消该订单支付。");
                        } else {
                            alert("订单支付失败。");
                        }
                    });
                }
            });
        }
        
	</script>
	<body>
		<div class="content">
			<!--头部开始-->
			
			<!--头部结束-->
			<!--主要部分开始-->
			<form name="mallForm" method="post" action="">
			<input type="hidden" name="productId" value="${productId}">  
			<div class="content-body">
				<div class="goods-list">
					<h3 class="sub-title">商品清单</h3>
					<ul>
						<c:forEach items="${mallOrder.productList}" var="tempProduct">

						<li class="clearfix">

							<div class="goods-img">
								<img src="<%=request.getContextPath()%>/wechatImages/mall/${tempProduct.productImg}" />
							</div>
							<div class="goods-info">
								<h3>
									<span class="goods-name">${tempProduct.productName}</span><br/>
									规格:<span class="goods-type1">${tempProduct.specifications}</span>
									
								</h3>
								<div class="goods-price">
									<span>￥<strong class="price-num">${tempProduct.price}</strong></span>
								</div>
							</div>
							<div class="goods-num-text">
								<span>×<i>${tempProduct.count}</i></span>
							</div>
						</li>


					
					</c:forEach>
					</ul>
				</div>
				
				<div class="consignee-info info-block">
					<h3 class="sub-title">收货人信息</h3>
					<div class="info-text">
						<span>收货人：${userAddress.userName}</span>&nbsp;&nbsp;&nbsp;<span>${userAddress.mobile}</span><br/>
						<span>${userAddress.area}&nbsp;${userAddress.address}&nbsp;</span>
						
						
					</div>
				</div>
				<!--
				<div class="delivery-info info-block">
					<h3 class="sub-title">配送信息</h3>
					<div class="info-text">
						<span>配送时间</span>
						<span>XXX</span>
						<span>XXX</span>
						<a class="arrow" href="javascript:;"></a>
					</div>
				</div>
				--><!--
				<div class="payment-info info-block">
					<h3 class="sub-title">支付信息</h3>
					<div class="info-text">
						<span>优惠券</span>
						
						<a class="arrow" href="javascript:;"></a>
					</div>
					<div class="info-text">
						<span>使用积分抵扣</span>
						
						<a class="arrow" href="javascript:;"></a>
					</div>
				</div>
				
				<div class="debit-info info-block">
					<h3 class="sub-title">发票信息</h3>
					<div class="info-text">
						<span>是否需要发票</span>
						<span>XXX</span>
						<span>XXX</span>
						<a class="arrow" href="javascript:;"></a>
					</div>
				</div>
				-->
				<div class="remark-info info-block">
					<h3 class="sub-title">备注</h3>
					<div class="info-text">
						${mallOrder.remarks}
						无
					</div>
				</div>
				
				<div class="price-list info-block">
					<h3 class="sub-title">价格清单</h3>
					<div class="info-text">
						<span>商品总价格</span>
						<div class="goods-price-text acitve">
							<span>￥<i><fmt:formatNumber type="number" value="${totalPrice}" pattern="0.00" maxFractionDigits="2"/></i></span>
						</div>
					</div>
					<div class="info-text">
						<span>运费</span>
						<div class="goods-price-text acitve">
							<span>￥<i><fmt:formatNumber type="number" value="${mallOrder.freight}" pattern="0.00" maxFractionDigits="2"/></i></span>
						</div>
					</div>
					<div class="info-text">
						<span>积分抵扣</span>
						<div class="goods-price-text acitve">
							<span>￥-<i><fmt:formatNumber type="number" value="${mallOrder.integral}" pattern="0.00" maxFractionDigits="2"/></i></span>
						</div>
					</div>
					<div class="info-text">
						<span>优惠券抵扣</span>
						<div class="goods-price-text acitve">
							<span>￥-<i><fmt:formatNumber type="number" value="${mallOrder.couponsMoney}" pattern="0.00" maxFractionDigits="2"/></i></span>
						</div>
					</div>
				</div>
				<div class="all-price fixed-block">
					<h3>实付:<span>￥</span><strong id="sTotalPrice" class="all-price-num"><fmt:formatNumber type="number" value="${mallOrder.totalPrice}" pattern="0.00" maxFractionDigits="2"/>  </strong>	
					</h3>
				</div>
			</div>
			</form>
			<!--主要部分结束-->
			<!--底部菜单开始-->
			<!--<div class="footer-nav fixed-block">
				<ul class="clearfix">
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
</html>
