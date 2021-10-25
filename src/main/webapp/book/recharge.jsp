<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html class="white-bg">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
		<title>豆豆币充值</title>
		
		<link rel="stylesheet" href="plug/css/jq-plug.css" />
		<link rel="stylesheet" href="lib/css/font-awesome.min.css" />
        <link rel="stylesheet" href="css/common.css" />
		<link rel="stylesheet" href="plug/css/swiper.css" />
        <link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/index.css" />
		<link rel="stylesheet" href="css/myMoney.css" />

		<script src="lib/js/jquery-2.1.3.min.js"></script>
		<script src="lib/js/iscroll.js"></script>
		<script src="plug/js/jq-plug.js"></script>
		<script src="js/common.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
		
	</head>


	<script>

var orderNumber ='';
     function dopay(payment) {

		
        
            $.ajax({
                type : 'POST',
                url : '<%=request.getContextPath()%>/payment/gopay',
                dataType : 'json',
                data : {
                    "commodityName" : '会员充值',//商品名称
                    "totalPrice" : payment, //支付的总金额
                    "memberOpenId" : '${member.openId}', //支付的总金额
                    "memberId" : '${member.id}' //
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
                           
                           window.location.href = "<%=request.getContextPath()%>/book/saveMemberPayment?memberId=${memberId}&orderNumber=201605316589&payment="+payment;
                        } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                           // alert("您已手动取消该订单支付。");
                        } else {
                            alert("订单支付失败,请重试!");
                        }
                    });
                }
            });
        }



	function test()
	{
		alert('aaa');
	}

	</script>
	<body class="white-bg">
		<!--search-->
		<!--<div class="search-box fixed-bolck">
			<input type="search" placeholder="自出版/图书/杂志"/>
			<button class="book-type-btn"></button>
		</div>-->
     
		
        <!--page-center-->
        <div class="page-center">
            <ul class="recharge-list">
                <li onclick="dopay('6')">
                    <span>600</span>
                    <i>￥6.00</i>
                </li>
                <li onclick="dopay('12')">
                    <span>1200</span>
                    <i>￥12.00</i>
                </li>
                <li onclick="dopay('18')">
                    <span>1800</span>
                    <i>￥18.00</i>
                </li>
                <li onclick="dopay('30')">
                    <span>3000</span>
                    <i>￥30.00</i>
                </li>
                <li onclick="dopay('50')">
                    <span>5000</span>
                    <i>￥50.00</i>
                </li>
                <li onclick="dopay('88')">
                    <span>8800</span>
                    <i>￥88.00</i>
                </li>
                <li onclick="dopay('200')">
                    <span>20000</span>
                    <i>￥200.00</i>
                </li>
				<li onclick="dopay('200')">
                    <span>黄金会员</span>
                    <i>￥688.00</i>
                </li>
				<li onclick="dopay('200')">
                    <span>白金会员</span>
                    <i>￥888.00</i>
                </li>
            </ul>
            <div class="rechage-explain">
                <h3>说明</h3>
                <ul>
                    <li>在凡豆微信端充值的豆币可用于在凡豆微信端或Android App购买音频内容，冲抵邮费。
                    </li>
                    <li>在凡豆微信端充值的豆币只能在凡豆微信网站或Android App内使用。

                    </li>
                    <li>充值的豆豆币没有使用期限，会一直保存在你的账户内，可随时使用。

                    </li>
                    <li> 充值的豆豆币不能退回。

                    </li>
                    <li>白金会员、黄金会员仅用于凡豆书院借书，购买后享受相应的借书服务。
                    </li>
                </ul>
            </div>
        </div>
        <!--page-center-end-->

		
		
		

        <!--menu-group-->
              <div class="menu-group off">
            <ul class="clearfix">
                <li class="switch-btn">
                    <a href="#">

                    </a>
                </li>
                <li class="shop-btn">
                     <a href="<%=request.getContextPath()%>/book/bookMobileManager?memberId=${memberId}">

                    </a>
                </li>
                <li class="me-btn">
                    <a href="<%=request.getContextPath()%>/book/memberBookInfo?memberId=${memberId}">


                    </a>
                </li>
                <li class="local-btn">
                    <a href="<%=request.getContextPath()%>/book/bookVehicleManager?memberId=${memberId}">

                    </a>
                </li>
            </ul>
        </div>
        <!--menu-group-end-->
	</body>


</html>
