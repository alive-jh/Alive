<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html style="font-size:16px;">
<head>
<meta charset="utf-8">
<title>${article.title }</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link type="text/css" rel="stylesheet" href="css/base.css" />
<link type="text/css" rel="stylesheet" href="css/common.css" />
<link type="text/css" rel="stylesheet" href="css/style.css" />
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/index.js"></script>
<script type="text/javascript" src="/js/hhSwipe.js"></script>
<script type="text/javascript" src="/js/ai.js"></script>
<script type="text/javascript" src="/js/WeixinApi.js"></script>

<style>
	.weArticle{
		width:100%;
		margin:0 auto;
		font-family:'微软雅黑','黑体';
	}
	.weArticle img{
		width:95%;
		min-height:20px;
		display:block;
		margin:10px 0;
	}
	.weArticle h3{
		font-size:1.5rem;
	}
	.weArticle h6.date{
		font-size:1rem;
		color:#8c8c8c;
	}
	.weArticle p{
		font-size: 1rem;
		color: rgb(51, 51, 51);
		line-height: 2em;
		margin:0;
		text-indent:0;
	}
	@media screen and (min-width: 1025px){
	.weArticle {
		width:50%;
		}
	}
</style>

</head>
<script type="text/javascript">
	var device=0;
	$(function() {
		if (ai.ovb.ios()) {
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
			device=1;
			$.post('<%=basePath%>website/readCount?content_id=${content.id}&account_id=${account_id}');
			$.post('<%=basePath%>website/readCountByRedirect?content_id=${content.id}&account_id=${account_id}&member_id=${member_id}');
		}
		
		if (ai.ovb.android()) {
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
			device=2;
			$.post('<%=basePath%>website/readCount?content_id=${content.id}&account_id=${account_id}');
			$.post('<%=basePath%>website/readCountByRedirect?content_id=${content.id}&account_id=${account_id}&member_id=${member_id}');
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

	// 需要分享的内容，请放到ready里
	WeixinApi
			.ready(function(Api) {

				// 微信分享的数据
				var wxData = {
					"appId" : "wx13fbc957c16f908f", // 服务号可以填写appId
					"imgUrl" : '<%=basePath%>npulsewechatImages/${account_id }/${content.img_urls[0]}',
					"link" : '<%=basePath%>website/content1?url=mobile/001&content_id=${content.id}&account_id=${account_id}&member_id=${member_id}',
					"desc" : '${content.long_title}',
					"title" : '${content.short_title}'
				};

				// 分享的回调
				var wxCallbacks = {
					// 分享操作开始之前
					ready : function() {
						// 你可以在这里对分享的数据进行重组
						//alert("准备分享");
					},
					// 分享被用户自动取消
					cancel : function(resp) {
						// 你可以在你的页面上给用户一个小Tip，为什么要取消呢？
						//alert("分享被取消，msg=" + resp.err_msg);
					},
					// 分享失败了
					fail : function(resp) {
						// 分享失败了，是不是可以告诉用户：不要紧，可能是网络问题，一会儿再试试？
						//alert("分享失败，msg=" + resp.err_msg);
					},
					// 分享成功
					confirm : function(resp) {
						// 分享成功了，我们是不是可以做一些分享统计呢？
						//alert("分享成功，msg=" + resp.err_msg);
						$.post('<%=basePath%>website/census?account_id=${account_id}&content_id=${content.id}&member_id=${member_id}&device='
										+ device);
					},
					// 整个分享过程结束
					all : function(resp, shareTo) {
						// 如果你做的是一个鼓励用户进行分享的产品，在这里是不是可以给用户一些反馈了？
						//alert("分享" + (shareTo ? "到" + shareTo : "") + "结束，msg="
						//		+ resp.err_msg);
					}
				};

				// 用户点开右上角popup菜单后，点击分享给好友，会执行下面这个代码
				Api.shareToFriend(wxData, wxCallbacks);

				// 点击分享到朋友圈，会执行下面这个代码
				Api.shareToTimeline(wxData, wxCallbacks);

				// 点击分享到腾讯微博，会执行下面这个代码
				//Api.shareToWeibo(wxData, wxCallbacks);

				// iOS上，可以直接调用这个API进行分享，一句话搞定
				//Api.generalShare(wxData, wxCallbacks);

				// 有可能用户是直接用微信“扫一扫”打开的，这个情况下，optionMenu、toolbar都是off状态
				// 为了方便用户测试，我先来trigger show一下
				// optionMenu
				//var elOptionMenu = document.getElementById('optionMenu');
				//elOptionMenu.click(); // 先隐藏
				//elOptionMenu.click(); // 再显示
				// toolbar
				//var elToolbar = document.getElementById('toolbar');
				//elToolbar.click(); // 先隐藏
				//elToolbar.click(); // 再显示
			});
</script>

<body>
	
		<!-- /header -->
		
		<!-- /header -->
		<div class="cont-box weArticle">
		<c:if test="${article.id!=61}">
			<h3 class="title">${article.title }</h3>
			<h6 class="date">发布日期：${article.releaseDate}</h6>
			<h6 class="date">来源：${article.source }</h6>
		</c:if>
			<div>
				<article>
					
				</article>
			</div>
			${article.content }
		</div>
		<!-- /content -->
	
</body>
</html>
