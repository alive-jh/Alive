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
		<title>WIFI二维码</title>
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




function saveQRcode()
{

	var url = "http://qr.liantu.com/api.php?text=FD_SET_WIFI:{AP="+document.forms['memberForm'].wifiName.value+",PWD="+document.forms['memberForm'].pwd.value+",mode=0}";
	window.location.href = url; 
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
							<input  type="text" name="wifiName" placeholder="WIFI名称"/>
						</div><div class="input-block">
							<span class="input-icon pass"></span>
							<input  type="text" name="pwd" placeholder="WIFI密码"/>
						</div>
						
					</div>
					<div class="post-btn clearfix">
						
						<input type="button" value="生成二维码" onclick="saveQRcode()" class="acitve"/>
					</div>
				</form>
				
			</div>
			<!--主要部分结束-->
			
		</div>
	</body>
</html>



