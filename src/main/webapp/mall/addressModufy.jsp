<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<meta name="viewport" content="width=device-width, user-scalable=no,initial-scale=1,maximum-scale=1 target-densitydpi=medium-dpi">
		<title>编辑地址</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/wapcss/addrSelect.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/addrSelectSin.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/swiper.css" />
		<script src="<%=request.getContextPath()%>/js/swiper.js"></script>
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		<script>

		$(document).ready( function () {

			 if(${userAddress.status} ==  0)
			 {
					document.getElementById('tempStatus').checked = true;
			 }
			
			});


	function test()
	{
		

		if(document.getElementById('tempStatus').checked ==true)
		{
			document.forms['mallForm'].status.value = 0;
		}
		else
		{
			document.forms['mallForm'].status.value = 1;
		}
		
	}
			

	function saveAddress()
	{
		document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/saveAddress?searchType=${searchType}";
		document.forms['mallForm'].submit();
	}

		</script>
	</head>
	<body>
		<div class="content">
			<!--头部开始
			<div class="header">
				<div class="head-left">
					<a href="javascript:;"></a>
				</div>
				<h3 class="head-title">
					地址管理
				</h3>
				<div class="head-right">
					
				</div>
			</div>
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body no-padding">
			<form name="mallForm" method="post" action="<%=request.getContextPath()%>/member/saveAddress">
				<input type="hidden" name="memberId" value="${mallOrder.memberId}">
				<input type="hidden" name="id" value="${userAddress.id}">
				<input type="hidden" name="status" value="${userAddress.status}">
				<div class="fill-addr">
					<ul>
						<li class="clearfix">
							<span>联系人</span>
							<input name="userName" value="${userAddress.userName}"  type="text" placeholder="请输入联系人" />
						</li>
						<li class="clearfix">
							<span>手机号码</span>
							<input name="mobile" value="${userAddress.mobile}" type="text" placeholder="请输入手机号码" />
						</li>
						<li class="clearfix" id='addr-sbox'>
							<span>选择地区</span>
							<input name="area" readonly value="${userAddress.area}" id="addrShow" type="text" placeholder="请选择地区" />
						</li>
						<li class="clearfix">
							<span>详细地址</span>
							
							<input name="address" value="${userAddress.address}" type="text" placeholder="请输入街道门牌信息" />
						</li>
						<li class="clearfix">
							<span>默认地址</span>
							<input type="checkbox" id="tempStatus" name="checkStatus" onclick="test()"/>
						</li>
					</ul>
				</div>
				</form>
			</div>
			<!--主要部分结束-->
			
			<!--时间选择器开始-->
			<div id="mask"></div>
		
			<div class="addr-swiper" id="addr-swiper">
				<div class="addr-head">
					<h3>请选择地址</h3>
					<input type="button" id="close-btn" value="完成"/>
				</div>
					<div class="addr-body">
						<div class="addr-cont">
							<p>上下滑动获取地址</p>
							<div style="overflow:hidden;position: relative;">
								<div class="select-box"></div>
								<div class="data-list data-list1 swiper-container">
									<ul class="data-box swiper-wrapper" id="data-box1">
										<li class="swiper-slide">请选择省</li>
									</ul>
								</div>
                                <div class="data-list data-list2 swiper-container">
                                    <ul class="data-box swiper-wrapper" id="data-box2">
                                        <li class="swiper-slide">请选择市</li>
                                    </ul>
                                </div>
                                <div class="data-list data-list3 swiper-container">
                                    <ul class="data-box swiper-wrapper" id="data-box3">
                                        <li class="swiper-slide">请选择区</li>
                                    </ul>
                                </div>
							</div>
						</div>
					<div style="clear: both;height:0;overflow: hidden;"></div>
				</div>
			</div>
			
			<!--底部菜单开始-->
			<div class="control-btn fixed-block">
					<c:if test="${userAddress.id != null}">
						<input type="button" value="修改地址" onclick="saveAddress()"/>
					 </c:if> 
					  <c:if test="${userAddress.id == null}">
					<input type="button" value="新增地址" onclick="saveAddress()"/>
					 </c:if> 
			</div>
			<!--底部菜单结束-->
		</div>
	</body>
		<script>
		
		
	$(function(){

		
		//按钮操作效果
		$('#addr-sbox').click(function(){
			$('#addr-swiper').animate({'bottom':'0'},400);
            $('#addr-swiper .data-list1').show();
			$('#mask').show();
			$('#addr-show').addClass('select');
		})

		//关闭按钮效果
		$('#close-btn').click(function(){
			$('#addr-swiper').animate({'bottom':'-100%'},40);
			$('#mask').hide();
			//alert('所选地址是:'+$('#addrShow').attr('value'));
		})
		$('#mask').click(function(){
			$('#addr-swiper').animate({'bottom':'-100%'},40);
			$('#mask').hide();
			//alert('所选地址是:'+$('#addrShow').attr('value'));
		})

		//调用地址插件并设置默认地址
        addressInit('data-box1', 'data-box2', 'data-box3','广东省','深圳市','罗湖区');
	})
	</script>
    <script src="<%=request.getContextPath()%>/js/js.address.js"></script>
</html>
