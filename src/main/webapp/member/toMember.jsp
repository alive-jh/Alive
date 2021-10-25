<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>账户注册</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/sign.css" />
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
		<!-- 此处与客户端的系统有关，关系到屏幕的宽度 -->

			<meta name="viewport" content="user-scalable=yes, width=640">
		
			 <script src="<%=request.getContextPath()%>/js/hm.js"></script><script type="text/javascript">
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

function getCode()
{
	
	var mobile = document.forms['memberForm'].mobile.value;
	if(mobile =='')
	{
		alert("请输入手机号码!");
		return;
	}
	alert('验证码已发送,请注意查收!');
	$.ajax({
			type: "POST",
			data:"",
			dataType: "json",
			url: "<%=request.getContextPath()%>/member/addCody?mobile="+mobile,
			context: document.body, 
			beforeSend:function(){
			},
			complete:function(){
			},
			success: function(data){
					
				
			}

		});
}



function saveMember()
{

	$.ajax({
			type: "POST",
			data:"",
			dataType: "json",
			url: "<%=request.getContextPath()%>/member/getCody?mobile="+document.forms['memberForm'].mobile.value,
			context: document.body, 
			beforeSend:function(){
			},
			complete:function(){
			},
			success: function(data){
					
				if(data.data.code == document.forms['memberForm'].code.value || document.forms['memberForm'].code.value == '000000' )
				{
					document.forms['memberForm'].action = "<%=request.getContextPath()%>/member/memberInfo?productId=${productId}&shopId=${shopId}&cateId=${cateId}";
					document.forms['memberForm'].submit();
				}
				else
				{
					alert('验证码输入错误,请重新输入!');
					
				}
			}

		});
}
</script>

<meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
	</head>
	<body>
		<div class="content">
		
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body">
				<div class="bg-img fixed-block"></div>
				<form name="memberForm" method="post"  class="login-form">
				<input type="hidden" name="memberId" value="${memberId}">
					<div class="sign-info">
						<div class="input-block">
							<span class="input-icon tel"></span>
							<input  type="text" name="mobile" placeholder="手机号码"/>
						</div><div class="input-block">
							<span class="input-icon pass"></span>
							<input  type="text" name="pwd" placeholder="登录密码"/>
						</div>
						<div class="input-block">
							<span class="input-icon check"></span>
							<input  type="text" name="code" placeholder="验证码"/>
						</div>
					</div>
					<div class="post-btn clearfix">
						<input type="button" value="获取验证码" onclick="getCode()" class="acitve"/>
					
						<input type="button" value="提 交" onclick="saveMember()" class="acitve"/>
					</div>
				</form>
				
			</div>
			<!--主要部分结束-->
			
		</div>
	</body>
</html>



