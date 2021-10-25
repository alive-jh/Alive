<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>订单列表</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/fillOrder.css" />	
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
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
		        
		        
		        

	function dopay(id) {

      
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

							document.forms['orderForm'].action ="updateOrderPaymentStatus?orderId="+id+"&status="+status+"&memberId=${member.id}";
							document.forms['orderForm'].submit();
                        } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                           // alert("您已手动取消该订单支付。");
                        } else {
                            alert("订单支付失败,请重试!");
                        }
                    });
                }
            });
        }


		        function updateOrderStatus(id,status)
		        {
		        	if(confirm('你确定取消该订单吗?'))
					{
						alert('订单金额将于3个工作日内原路返回!');
						document.forms['orderForm'].action ="updateOrderStatus?orderId="+id+"&status="+status+"&memberId=${member.id}";
						document.forms['orderForm'].submit();
						
					}
		        }
		        function updateElectrismOrder(id,status)
		        {
		        	
						
					document.forms['orderForm'].action ="updateElectrismOrder?orderId="+id+"&status="+status+"&memberId=${member.id}";
					document.forms['orderForm'].submit();
						
					
		        }
		        function toComment(id,electrismId)
		        {

					document.forms['orderForm'].action ="toComment?orderId="+id+"&electrismId="+electrismId+"&memberId=${member.id}";
					document.forms['orderForm'].submit();
						
					
		        }
		         function toAddPayment(id)
		        {

					document.forms['orderForm'].action ="toAddPayment?orderId="+id+"&memberId=${member.id}";
					document.forms['orderForm'].submit();
						
					
		        }
				 function updateOrderPayment(id)
		        {

					document.forms['orderForm'].action ="updateOrderPayment?orderId="+id+"&memberId=${member.id}";
					document.forms['orderForm'].submit();	
					
		        }
		        
		        
				function toMobile(mobile)
				{
					window.location.href = mobile;
				}
		    </script>
		    
		    <meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
	</head>
	<body>
		<div class="content no-margin">
			<!--头部开始-->
			
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body">
				
				<div class="goods-list order-list">
					<ul>
					<form name="orderForm" method="post">
						<c:forEach items="${resultPage.items}" var="temp"> 
						<li class="clearfix">
						
							<h3 class="order-num clearfix"><span class="left">
							
							订单编号:<font color="red">${temp.orderNumber}</font>
							</span>
							
							<span class="right">
							
							<c:if test="${temp.status ==0}">待处理</c:if>
							<c:if test="${temp.status ==1}">进行中</c:if>
							<c:if test="${temp.status ==2}">已取消</c:if>
							<c:if test="${temp.status ==3}">待付款</c:if>
							<c:if test="${temp.status ==4}">进行中</c:if>
							<c:if test="${temp.status ==5}">已完成</c:if>
							<c:if test="${temp.status ==6}">已评论</c:if>
							</span></h3>

						
							<div class="order-info">
								<div class="goods-img">
								<a href="<%=request.getContextPath() %>/electrism/toElectrismOrder?electrismId=${temp.electrismId}&memberId=${temp.memberId}&itemType=${temp.serviceItem}">

									
									<c:if test="${temp.serviceItem == 1}"><img  src="<%=request.getContextPath()%>/images/e01.jpg" /></c:if>
									<c:if test="${temp.serviceItem == 2}"><img  src="<%=request.getContextPath()%>/images/e02.jpg" /></c:if>
									<c:if test="${temp.serviceItem == 3}"><img  src="<%=request.getContextPath()%>/images/e03.jpg" /></c:if>
									<c:if test="${temp.serviceItem == 4}"><img  src="<%=request.getContextPath()%>/images/e04.jpg" /></c:if>
								</a>
								</div>
								<div class="goods-info" style="align:left">
									<a href="<%=request.getContextPath() %>/electrism/toElectrismOrder?electrismId=${temp.electrismId}&memberId=${temp.memberId}&itemType=${temp.serviceItem}&orderDate=${temp.orderDate}">

									<c:if test="${member.type ==0}">
									社区电工：<i>${temp.electrismName}</i>
									</c:if>
									<c:if test="${member.type ==1}">
									客户名称：<i>${temp.contacts}</i>
									</c:if>
									<h3>服务项目：
									<c:if test="${temp.serviceItem == 1}">上门服务</c:if>
									<c:if test="${temp.serviceItem == 2}">电器维修</c:if>
									<c:if test="${temp.serviceItem == 3}">线路维护</c:if>
									<c:if test="${temp.serviceItem == 4}">其他服务</c:if>
								
								
										
									</h3>
									<h3>
										<span>预约时间：<strong class="price-num">${temp.orderDate}</strong></span>
									</h3>
									
									<c:if test="${member.type ==1}">
									服务地址：<i>${temp.address}</i>
									</c:if>
									<div class="goods-price">
										<span>￥<strong class="price-num"><fmt:formatNumber type="number" value="${temp.payment}" pattern="0.00" maxFractionDigits="2"/></strong></span>
									</div>
									</a> 
								</div>
								<div class="goods-num-text">
									
								</div>
								
							</div>
							


							
							<div class="control-btn clearfix">
							<c:if test="${member.type ==0}">
								<c:if test="${temp.status ==0}">
									<input type="button" value="取消订单" onclick="updateOrderStatus(${temp.id},'2')" class="confirm"/>
									
								</c:if>
								
								<c:if test="${temp.status ==1}">
								<input type="button" value="完成" class="evaluate" onclick="updateElectrismOrder(${temp.id},5)" />&nbsp;
									<input type="button" value="客服电话" onclick="toMobile('tel:075583200455')" class="confirm"/>
									
								</c:if>
								
								<c:if test="${temp.status ==3}">
								<input type="button" value="附加费:<fmt:formatNumber type="number" value="${temp.addPayment}" pattern="0.00" maxFractionDigits="2"/>"  class="confirm"/>
								&nbsp;
									<input type="button" value="付款" onclick="dopay(${temp.id})" class="confirm"/>
									
								</c:if>
								<c:if test="${temp.status ==4}">
									<input type="button" value="完成" class="evaluate" onclick="updateElectrismOrder(${temp.id},5)" />
								</c:if>
								<c:if test="${temp.status ==5}">
									<input type="button" value="投诉建议" onclick="toMobile('tel:075583200455')" class="confirm"/>&nbsp;&nbsp;
									<input type="button" value="评价" class="evaluate" onclick="toComment(${temp.id},${temp.electrismId})" />
								</c:if>
							
							</c:if>	
							
							<c:if test="${member.type ==1}">
								<c:if test="${temp.status ==0}"><input type="button" value="联系客服" onclick="toMobile('tel:13714569394')" class="confirm"/>
									&nbsp;	<input type="button" value="接单" onclick="updateElectrismOrder(${temp.id},'1')" class="confirm"/>
									&nbsp;	<input type="button" value="拒绝" onclick="updateElectrismOrder(${temp.id},'2')" class="confirm"/>
								</c:if>
								<c:if test="${temp.status ==1}">
									<input type="button" value="附加费" onclick="toAddPayment(${temp.id})" class="confirm"/>
									
								</c:if>
								<c:if test="${temp.status ==3}">
								
									<input type="button" value="取消附加费" onclick="updateOrderPayment(${temp.id})" class="confirm"/>
									
								</c:if>
							</c:if>	
								
								
							</div>
						</li>

						</c:forEach>
						</form>
					</ul>
				</div>
				
				
			</div>
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
		<br/><br/><br/>

		<!--<jsp:include page="/sitemesh/mobileFooter.jsp" flush="true"/>-->
	</body>
</html>
