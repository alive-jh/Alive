<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>购物车</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/shoppingCart.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		<script src="<%=request.getContextPath()%>/js/electrism.js"></script>
		
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



				function toSaveOrder()
				{

					
					document.forms['searchForm'].ids.value = "";
					document.forms['searchForm'].counts.value = "";
					document.forms['searchForm'].specifications.value = "";
					document.forms['searchForm'].prices.value = "";
					document.forms['searchForm'].productIds.value = "";
						$("input[name='goods-check']:checkbox").each(function() {
						if(this.checked == true)
						{
							if($(this).val()!= '')
							{
								
								document.forms['searchForm'].ids.value = document.forms['searchForm'].ids.value + $(this).val()+','
								
								document.forms['searchForm'].counts.value = document.forms['searchForm'].counts.value + document.getElementById('sCount'+$(this).val()).innerHTML+',';
								
								document.forms['searchForm'].specifications.value = document.forms['searchForm'].specifications.value + document.getElementById('tempName'+$(this).val()).innerHTML+',';

								document.forms['searchForm'].prices.value = document.forms['searchForm'].prices.value + document.getElementById('sPrice'+$(this).val()).innerHTML+',';

								document.forms['searchForm'].productIds.value = document.forms['searchForm'].productIds.value + document.getElementById('tempProductId'+$(this).val()).innerHTML+',';
							}
							
							
						
						}
						})
							
					
					if(document.forms['searchForm'].ids.value == "")
					{
						alert('请至少选择一个产品!');
						return;
					}
					
					
					document.forms['searchForm'].action = "<%=request.getContextPath()%>/member/toSaveOrder?orderType=1";
					document.forms['searchForm'].submit();
				}


				
		    </script><meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
	</head>
	<body>
		<div class="content no-margin"  >
			<!--头部开始-->
		
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body">
				<label class="check-all"><input type="checkbox" name="goods-check" value=""class="all"/>全选</label>
				<form class="form-inline" role="form" method="post" name="searchForm" action="<%=request.getContextPath()%>/member/toSaveOrder" >
				<div class="goods-list">
					<ul>
					<c:forEach items="${tempList}" var="temp">
					
						<li class="clearfix">
							<div class="goods-img check-btn">
								<input type="checkbox" id="checkId${temp.id}" value="${temp.id}" name="goods-check" />
								<img src="<%=request.getContextPath() %>/wechatImages/mall/${temp.productImg}" />
							</div>
							<div class="goods-info"> <span id="tempProductId${temp.id}" style="display:none">${temp.productId}</span>
								<h3>
								   
									<span class="goods-name">${temp.productName}</span><br/>
									规格:<span id="tempName${temp.id}" class="goods-name">${temp.specifications}</span>
									<a href="<%=request.getContextPath() %>/member/deleteShoppingCart?shoppingCartId=${temp.id}&memberId=${temp.memberId}"></a> 
								</h3>
								<div class="goods-price">
									<span >￥<strong class="price-num" id="sPrice${temp.id}"><fmt:formatNumber type="number" value="${temp.price}" pattern="0.00" maxFractionDigits="2"/>  </strong></span>
									<div class="goods-num clearfix">
										<a href="javascript:;" class="minus-btn disable">-</a>
										<span class="num-text" id="sCount${temp.id}">${temp.count}</span>
										<a href="javascript:;" class="plus-btn">+</a>
									</div>
								</div>
							</div>
						</li>
						</c:forEach>
					</ul>
				</div>
				
				<input type="hidden" name="ids">
				<input type="hidden" name="productIds" >
				<input type="hidden" name="counts">
				<input type="hidden" name="prices">
				<input type="hidden" name="specifications">
				<input type="hidden" name="memberId" value="${memberId}">
				<div class="all-price">
					<h3>合计:<span>￥</span><strong class="all-price-num">26.80</strong>
						<a href="javascript:toSaveOrder()">立刻结算</a>
					</h3>
				</div>
				</form>
			</div>
			<!--主要部分结束-->
			<!--底部菜单开始
			<jsp:include page="/sitemesh/mobileFooter.jsp" flush="true"/>
			<!--底部菜单结束-->
		</div>
	</body>
</html>

