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
		<title>现场签到</title>
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




function saveMember()
{
	
	$.ajax({
			type: "POST",
			data:"",
			dataType: "json",
			url: "<%=request.getContextPath()%>/member/sendyaoqing?memberId=${memberId}",
			context: document.body, 
			beforeSend:function(){
			},
			complete:function(){
			},
			success: function(data){
					
			alert("签到成功,谢谢!");
			}

		});
}
</script>

<meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
	</head>
	

	<body style="padding:0;margin:0;">
	<form name="memberForm" method="post"  class="login-form">
	<img src="<%=request.getContextPath()%>/images/qiandao.jpg" onclick="saveMember()" width="100%" style="vertical-align: bottom;" />
	</form>
</body>


</html>



