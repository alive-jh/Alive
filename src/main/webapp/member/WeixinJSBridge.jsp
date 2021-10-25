<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="author" content="qinuoli">
<title>微信支付-调用微信浏览器自带函数发起支付</title>
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
</head>
<style type="text/css">
body {
    padding: 0px;
    margin: 0px auto;
}

body {
    background-color: #EEE;
}

.i-assets-container {
    border: 1px solid #E1E1E1;
    vertical-align: top;
    display: block;
    background-color: #FFF;
    margin: 5px 10px;
    color: #A1A1A1 !important;
}

#container.ui-container {
    color: #666;
}

.i-assets-content {
    margin: 10px 10px 10px 20px;
}

.fn-clear:after {
    visibility: hidden;
    display: block;
    font-size: 0px;
    content: " ";
    clear: both;
    height: 0px;
}

.i-assets-header h3 {
    font-size: 14px;
}

.fn-left {
    display: inline;
    float: left;
}

h3 {
    margin: 0px;
    padding: 0px;
    font: 12px/1.5 tahoma, arial, "Hiragino Sans GB", "Microsoft Yahei",
        "宋体";
}

.i-assets-balance-amount {
    line-height: 24px;
    margin-right: 20px;
}

.amount {
    font-size: 24px;
    font-weight: 400;
    color: #666;
    margin-left: 25px;
}

.amount .fen {
    font-size: 18px;
}

#wx_bottom {
    display: flex;
}

#wx_bottom {
    overflow: hidden;
    margin: 15px 0px;
}

#wx_bottom {
    display: box;
    display: -ms-box;
    display: -webkit-box;
    display: flex;
    display: -ms-flexbox;
    display: -webkit-flex;
}

#wx_bottom  .a {
    width: 100%;
    height: 40px;
    line-height: 40px;
    margin: 0px 10px;
    border: 1px solid #DDD;
    text-align: center;
    border-radius: 3px;
    color: #666;
    background: #fff;
    text-decoration: none;
}

.WX_search {
    background-color: #EFEFEF;
    height: 40px;
    line-height: 40px;
    position: relative;
    border-bottom: 1px solid #DDD;
    text-align: center;
}

.pay_buttom {
    margin: 15px 0px;
    width: 100%;
    display: box;
    display: -ms-box;
    display: -webkit-box;
    display: flex;
    display: -ms-flexbox;
    display: -webkit-flex;
    width: 100%;
}

.pay_buttom a {
    height: 40px;
    line-height: 40px;
    margin: 0px 10px;
    border: 1px solid #DDD;
    text-align: center;
    border-radius: 3px;
    color: #666;
    background: #fff;
    text-decoration: none;
    width: 100%;
    display: block;
    flex: 1;
    -ms-flex: 1;
    -webkit-flex: 1;
    box-flex: 1;
    -ms-box-flex: 1;
    -webkit-box-flex: 1;
}
</style>
<body>

    <div class="WX_search">
        <p>订单支付信息确认</p>
    </div>
    <form action="" method="post">
        <div class="i-assets-container ui-bookblock-item">
            <div class="i-assets-content">
                <div class="i-assets-header fn-clear">
                    <h3 class="fn-left">入款账户</h3>
                </div>
                <div class="i-assets-body fn-clear">
                    <div class="i-assets-balance-amount fn-left">
                        <strong class="amount"><span
                            style="font-size: 15px;">上海***科技有限公司</span></strong>
                    </div>

                </div>
            </div>
            <div class="i-assets-content">
                <div class="i-assets-header fn-clear">
                    <h3 class="fn-left">商品名称</h3>
                </div>
                <div class="i-assets-body fn-clear">
                    <div class="i-assets-balance-amount fn-left">
                        <strong class="amount"><span
                            id="commodityName" style="font-size: 15px;">红色系 莱肯超跑 * 1</span></strong>
                    </div>

                </div>
            </div>
            <div class="i-assets-content">
                <div class="i-assets-header fn-clear">
                    <h3 class="fn-left">支付总金额</h3>
                </div>
                <div class="i-assets-body fn-clear">
                    <div class="i-assets-balance-amount fn-left">
                        <strong class="amount"><span id="total">0.01</span></strong>元
                    </div>

                </div>
            </div>
        </div>

        <div class="i-assets-container ui-bookblock-item">
            <div class="i-assets-content">
                <div class="i-assets-header fn-clear">
                    <h3 class="fn-left">您需要支付金额</h3>
                </div>
                <div class="i-assets-body fn-clear">
                    <div class="i-assets-balance-amount fn-left"
                        style="color: #F37800;">
                        <strong class="amount" style="color: #F37800;"
                            id="totalPrice">0.01</strong>元
                    </div>
                </div>
            </div>

        </div>

        <div class='pay_buttom'>
            <a href="#" style="background: #06C; color: #fff;" onclick="dopay();">确认支付</a>
        </div>
    </form>
    <script type="text/javascript">  
        var basePath = "<%=contextPath%>";  
        function dopay() {
            $.ajax({
                type : 'POST',
                url : '<%=contextPath%>/member/gopay',
                dataType : 'json',
                data : {
                    "commodityName" : 1,//商品名称
                    "totalPrice" : 1 //支付的总金额
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
                    WeixinJSBridge.invoke('getBrandWCPayRequest',{
                        "appId" : data[0].appId, //公众号名称，由商户传入  
                        "timeStamp" : data[0].timeStamp, //时间戳，自 1970 年以来的秒数  
                        "nonceStr" : data[0].nonceStr, //随机串  
                        "package" : data[0].packageValue, //商品包信息
                        "signType" : data[0].signType, //微信签名方式:  
                        "paySign" : data[0].paySign //微信签名  
                    },function(res) {
                        /* 对于支付结果，res对象的err_msg值主要有3种，含义如下：(当然，err_msg的值不只这3种)
                        1、get_brand_wcpay_request:ok   支付成功后，微信服务器返回的值
                        2、get_brand_wcpay_request:cancel   用户手动关闭支付控件，取消支付，微信服务器返回的值
                        3、get_brand_wcpay_request:fail   支付失败，微信服务器返回的值

                        -可以根据返回的值，来判断支付的结果。
                        -注意：res对象的err_msg属性名称，是有下划线的，与chooseWXPay支付里面的errMsg是不一样的。而且，值也是不同的。
                        */
                        
                        if (res.err_msg == 'get_brand_wcpay_request:ok') {
                            alert("支付成功！");
                            window.location.href = data[0].sendUrl;
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
</body>
</html>