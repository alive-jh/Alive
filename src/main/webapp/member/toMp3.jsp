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
		<title>音频推送</title>
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




function toMp3(mp3Name,url,mp3Id)
{


	
	$.ajax({
			type: "POST",
			data:"",
			dataType: "json",
			url: "<%=request.getContextPath()%>/app/sendMp3?mp3Id="+mp3Id+"&mp3Name="+mp3Name+"&url="+url,
			context: document.body, 
			beforeSend:function(){
			},
			complete:function(){
			},
			success: function(data){
					alert("推送成功！");
				
			}

		});
}



function toMp3ByUrl(mp3Name,mp3Id)
{

	
	$.ajax({
			type: "POST",
			data:"",
			dataType: "json",
			url: "<%=request.getContextPath()%>/app/sendMp3?mp3Name="+mp3Name+"&url="+document.forms['memberForm'].url.value+"&mp3Id"+mp3Id,
			context: document.body, 
			beforeSend:function(){
			},
			complete:function(){
			},
			success: function(data){
					alert("推送成功！");
				
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
							
							<input  type="text" name="url" placeholder="音频地址"/>
						</div>
						
						
					</div>
						<div class="post-btn clearfix">
						
						<input type="button" value="推送网络音频" onclick="toMp3ByUrl('','')" class="acitve"/>
					</div>
					<div class="post-btn clearfix">
						
						<input type="button" value="推送 小星星音频" onclick="toMp3('TwinkleTwinkle Little Star','http://image.fandoutech.com.cn/322c09755d9e4e73a69b562d9708500a.mp3','')" class="acitve"/>
						<input type="button" value="推送 小星星课堂" onclick="toMp3('Twinkle Twinkle Little Star','http://image.fandoutech.com.cn/322c09755d9e4e73a69b562d9708500a.mp3','296')" class="acitve"/>
					</div>
				</form>
				
			</div>
			<!--主要部分结束-->
			
		</div>
	</body>
</html>



