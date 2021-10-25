<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
    	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>${bookshop.name}会员卡（免费申请）</title>
		<link rel="shortcut icon" href="images/yz_fc.ico">
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/bootstrap.min.css">
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.telNum.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<!-- 此处与客户端的系统有关，关系到屏幕的宽度 -->

		
	
		<!-- 屏幕的宽度设置 -->
		<style>
			body{
				width: 100%;

			}
			.header{
				margin-top: 20px;
				text-align: center;
			}
			.content{
				margin-top: 35px;

			}
			.footer{
				text-align: center;
				margin-top: 50px;
				width: 100%

			}
			

		</style>
	</head>
	<body>
		<script>
			var shopId = '${bookshop.id}';
		
		</script>
		<!--内容部分开始-->
		<div class="container ">
			<input id="memberId" name="memberId" value="${memberId}" style="display:none">
			<input id="memberMobile" name="memberMobile" value="${memberMobile}" style="display:none">
			<div class="header">
				<h3>${bookshop.name}会员卡（免费申请）</h3>
			</div>
			<div class="content">
				<div class="">
					<img src="${bookshop.memberCardImage}" width="100%">	
				</div>
				<div>
					<h4>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：${bookshop.contacts }</h4>
					<h4>联系方式：${bookshop.telephone }</h4>
				</div>
			</div>

			<!--选购按钮开始-->
			<div class="footer">
				请输入手机号码：<input id="telephone" name="telephone" type="text">
				<br><br><br>
				<button class="btn btn-warning" value="" onclick="applay();">立即申请</button>
			</div>
			<div>

			</div>
		</div>
		<script type="text/javascript">
			function applay(){
				var telephone = $('#telephone').val();
				if(telephone==null||telephone==""||telephone==undefined){
					alert("请输入手机号码");
					return ;
				}
				if(!(/^1[34578]\d{9}$/.test($("#telephone").val()))){ 
					alert("手机号码格式不正确，请重新输入");  
					return ; 
				} 
				
				window.location="<%=request.getContextPath()%>/h5/bookShop/wechatLogin?shopId="+shopId+"&telephone="+telephone;
				<%-- alert("test");
				var memberId = $("#memberId").val();
				var memberMobile = $("#memberMobile").val();
				if(memberMobile==""){
					alert("请输入手机号码!");
				}else{
					$.ajax({
		                type: "POST",
		                url:"<%=request.getContextPath()%>/h5/bookShop/insertShopCard",
		                dataType: "json",
		                data:{memberid:memberId,shopid:shopId},
		                async: false,
		                error: function(request) {
		                    alert("错误！！");
		                },
		                success: function(result) {
		                	alert("申请成功");
		                }
		            });
				} --%>
			}
		</script>
	</body>
</html>
