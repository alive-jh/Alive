<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>个人中心</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/me.css" />
		<script src="js/zepto.js"></script>
		
		<!-- 此处与客户端的系统有关，关系到屏幕的宽度 -->

			<meta name="viewport" content="user-scalable=yes, width=640">
		
			 <script src="js/hm.js"></script><script type="text/javascript">
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
		    </script><meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
<script>
		wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数
    appId: '${appId}', // 必填，公众号的唯一标识
    timestamp: ${timestamp},
	nonceStr: '${nonceStr}',
	signature: '${signature}',
    jsApiList: [  'checkJsApi',
            'chooseImage',
            'uploadImage',
            'downloadImage',
            'previewImage',
            'getNetworkType'
			] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});





  
wx.ready(function () {

       var images = {
            localId: [],
            serverId: [],
            downloadId: []
        };
        document.querySelector('#scanQRCode1').onclick = function () {
            wx.chooseImage({
                success: function (res) {
                    images.localId = res.localIds;
                    alert(images.localId);
                    //jQuery(function(){
                      // $.each( res.localIds, function(i, n){
                        //    document.getElementById("testImg").src=n; 
                       // });
                    //});
                }
            });
        };
  
  
  
       document.querySelector('#scanQRCode2').onclick = function () {
            if (images.localId.length == 0) {
                alert('请先使用选择图片按钮');
                return;
            }
            images.serverId = [];
            jQuery(function(){
                $.each(images.localId, function(i,n) {
                    wx.uploadImage({
                        localId: n,
                        success: function (res) {
                            images.serverId.push(res.serverId);
                        },
                        fail: function (res) {
                            alert(JSON.stringify(res));
                        }
                    });
                });
            });
        };




});


function sign()
{
	 var url ="<%=request.getContextPath()%>/member/memberSign/?memberId=${member.id}"
	 window.location.href = url;
}
</script>
	</head>
	<body>
		<div class="content">
			<!--头部开始-->
			<!--<div class="header">
				<div class="head-left">
					<a href="javascript:;"></a>
				</div>
				<h3 class="head-title">
					购物车
				</h3>
				<div class="head-right">
					<a href="javascript:;"></a>
				</div>
			</div>-->
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body">
				<div class="top-info">
					<div class="user-info">
						<img id="testImg" src="${member.headImgUrl}" />
						<p><span>${member.nickName}</span></p>
					</div>
					<div class="score-info">
						<ul class="clearfix">
							<li>
								<a href="<%=request.getContextPath()%>/member/integralManager?memberId=${member.id}">
									<p>豆豆币</p>
									<span>${integralCount}</span>
								</a>
							</li>
							<c:if test="${member.type==1}">
								<li>
									<a href="<%=request.getContextPath()%>/electrism/electrismPaymentOrder?memberId=${member.id}">
										<p>余额</p>
										<span>${sumPayment}</span>
									</a>
								</li>
							</c:if>
							<c:if test="${member.type==0}">
								<li>
										<a href="#"><p>余额</p>
										<span>0</span></a>
								</li>
							</c:if>
							<li>
								<a href="<%=request.getContextPath()%>/coupons/couponsMobileManager?memberId=${member.id}">
									<p>优惠券</p>
									<span>${couponsCount}</span>
								</a>
							</li>
						</ul>
					</div>
				</div>
				
				<div class="center-info">
					<ul>
					
						<li>
							<a href="<%=request.getContextPath()%>/member/memberOrderManager?memberId=${member.id}">
								<i></i>
								<span>商城订单</span>
								<b class="arrow"></b>
							</a>
						</li>
						
						<li>
							<a href="<%=request.getContextPath()%>/book/memberShopOrder?memberId=${member.id}&shopId=9">
								<i></i>
								<span>我的书单</span>
								<b class="arrow"></b>
							</a>
						</li>
						
						<!-- 
						<li>
							<a href="<%=request.getContextPath()%>/electrism/electrismOrder?memberId=${member.id}">
								<i></i>
								<span>预约订单</span>
								<b class="arrow"></b>
							</a>
						</li>
						 -->
						<li>
							<a href="<%=request.getContextPath()%>/member/addressManager?memberId=${member.id}&searchType=modufy">
								<i></i>
								<span>常用地址</span>
								<b class="arrow"></b>
							</a>
						</li>
						<li>
							<a href="javascript:;">
								<i></i>
								<span>投诉建议</span>
								<b class="arrow"></b>
							</a>
						</li>
						<li>
							<a href="javascript:;">
								<i></i>
								<span>联系我们</span>
								<b class="arrow"></b>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<!--主要部分结束-->
			<!--底部菜单开始
			<div class="footer-nav">
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
			</div>
			<!--底部菜单结束-->
		</div>
	</body>
</html>
