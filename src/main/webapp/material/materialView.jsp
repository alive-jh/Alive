<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${weChatMaterial.title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/website/mobile/001/css/base.css" />
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/website/mobile/001/css/common.css" />
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/website/mobile/001/css/style.css" />
<script type="text/javascript"
	src="<%=basePath%>/website/mobile/001/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/website/mobile/001/js/index.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/website/mobile/001/js/hhSwipe.js"></script>
<script type="text/javascript"
	src="<%=basePath%>website/mobile/001/js/ai.js"></script>
</head>
<script type="text/javascript">
	$(function() {
		if (ai.ovb.ios() || ai.ovb.android()) {
			$("#page").css({
				"position" : "relative",
				"margin" : "0 auto",
				"min-width" : "320px",
				"height" : "auto"
			});
			$(".header").css({
				"overflow" : "hidden",
				"width" : "auto",
				"height" : "auto",
				"background" : "#000"
			});
			$("#header").hide();
		}
		if (!ai.ovb.ios() && !ai.ovb.android()) {
			$("#page").css({
				"position" : "relative",
				"margin" : "0 auto",
				"min-width" : "320px",
				"height" : "auto",
				"max-width" : "320px"
			});
			$(".header").css({
				"overflow" : "hidden",
				"width" : "auto",
				"height" : "auto",
				"background" : "#000",
				"max-width" : "320px"
			});
		}
	});
</script>

<body>
	<div id="page" class="bk-cont-01 pb5">
		<!-- /header -->
		<div data-role="header" id="header" class="header">
			<a href="javascript:history.go(-1);" class="fl ico-back">后退</a>
			<h1 class="fl h-title">${weChatMaterial.title}</h1>
			<a href="javascript:window.history.forward();" class="fr ico-more">前进</a>
		</div>
		<!-- /header -->
		<div class="cont-box">
			<h2 class="title">${weChatMaterial.title}</h2>
			<h6 class="date">发布日期：${weChatMaterial.createDate}</h6>
			<div>
				<article>
					<!--scroll-->
					<div class="scroll relative">
						<div class="scroll_box" id="scroll_img">
							<ul class="scroll_wrap">
								<img  src="<%=request.getContextPath() %>/npulsewechatImages/${weChatMaterial.logo}" width="100%" /> 										</li>
							</ul>
						</div>

						<span class="scroll_position_bg opacity6"></span>
						<ul class="scroll_position" id='scroll_position'>
						</ul>
					</div>
					<!--scroll-->
				</article>
			</div>
			<p>${weChatMaterial.summary}</p>

			<p>${weChatMaterial.content}</p>
		</div>
		<!-- /content -->
	</div>
</body>
</html>
