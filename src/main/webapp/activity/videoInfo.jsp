<%@ page language="java" import="java.util.*,com.wechat.entity.VideoAcitivity" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>英语新势力&蛋蛋机器人英语秀【襄汾站】</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/activity/css/videoInfo.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/dialog/dialog.css">
</head>
<body>
	<div class="container-fluidcontainer-fluid">
		<div class='topbar'>
			<a href='<%=request.getContextPath() %>/api/getAllVideoCompetitionByVerifSuccess?memberId=${memberId }' target='_top'> <i class=" icon-angle-left"></i> < 首页
			</a>
		</div>
		<div class="title">
			<h4>参赛选手：${video[24]}&nbsp;&nbsp;&nbsp;&nbsp;${gradeName }</h4>
			<HR align=center width=100% color=#987cb9 SIZE=1>
		</div>
		<div class="video">
			<video controls="controls" poster="${video[3]}" x5-video-player-type='h5' playsinline='true' webkit-playsinline='true'>
				<source src="${video[8]}">
			</video>
		</div>
		<div class="content">
			<div class="text1">
				<h5>英语小霸王&机器人英语秀【襄汾站】</h5>
			</div>
			<div class="text1">
				<h5 style="float: left;">${video[24]}&nbsp;&nbsp;参赛编号:${video[1]}</h5>
			</div>
			<div class="text2">
				<a style="float: right;" href="https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIzNDAzMzkzMQ==#wechat_redirect">30天轻松登台show英语？how？</a>
			</div>
		</div>
		<div class="button">
			<div class="voteNum">
				<%-- <span id="vote">已获票数：${video[14]}</span>&nbsp;&nbsp;&nbsp;<span id="score">总得分：${score }</span> --%>
				<span id="vote">已获票数：${video[14]}</span> <br> <br>
			</div>
			<div class="vote">
				<button id="${video[1]}" class="btn btn-warning" onclick="vote(this)" style="vertical-align: text-top;">
					<i class='glyphicon glyphicon-thumbs-up'></i> 投TA一票
				</button>
				<a style="vertical-align: text-top;" class="btn btn-warning" href='<%=request.getContextPath() %>/api/getAllVideoCompetitionByVerifSuccess?memberId=${memberId }' target='_top'> <i class=" icon-angle-left"></i> 看看其他人
				</a>
			</div>
		</div>
		<input id="memberId" type="hidden" value="${memberId }">
		<footer class="footer">
			<div class="footer_text">Copyright © 2018 东莞市凡豆信息科技有限公司</div>
		</footer>
	</div>
</body>
<script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>
<script src="<%=request.getContextPath()%>/dialog/script/dialog.js"></script>
<script type="text/javascript">
	var memberId = $('#memberId').val();

	$(document).ready(function() {
		$(window).resize(function() {
			var windowWidth = $(window).innerWidth();
			var windowHeight = $(window).innerHeight();
			$('.c-h5').css({
				width : windowWidth,
				height : windowHeight
			})
		})
	})

	function vote(that) {

		var vid = that.id;
		var message = '';
		var type = 'ok';
		var code = 0;

		$.ajax({
					type : "POST",
					url : '/wechat/v2/video/vote?_time' + new Date().getTime(),
					data : {
						memberId : memberId,
						vId : vid
					},
					async : false,
					beforeSend : function() {
						$.showLoading();
					},
					complete : function() {
						$.hideLoading();
					},
					error : function(request) {
						alert("错误！！");
						return;
					},
					success : function(success) {
						console.log(success);
						if (success.code == 200) {
							console.log(success);
							message = success.data.count > 0
									? "投票成功，您今天还可以为" + success.data.count
											+ "位小朋友投票"
									: "投票成功，谢谢你为小朋友们投票";
							tuijian();
						} else {
							type = 'warning';
							message = success.msg;
							code = success.code;
						}
					}
				});

		var d = $.dialog({
			delay : 2500,
			//弹框类型
			type : type,
			//描述文字
			message : message,
			//尺寸
			width : 200,
			//对话框遮罩层透明度
			maskOpacity : 0.2,
			animate : true
		});

		setTimeout(function() {
			if (code == 20504) {
				location.href = "/wechat/api/voteRecord?memberId=" + memberId;
			}
		}, 2500);

	}

	$(function() {
		if ("${video[0]}" == null || "${video[0]}" == ""
				|| "${video[0]}" == undefined) {
			alert("视频信息不存在");
		}
	});

	function tuijian() {
		setTimeout(
				function() {

					$.modal({
								title : "恭喜您，抽中《凡豆好声音》评委资格，成为评委！",
								text : "快为您喜欢的学员转身投票吧！ 为学员投一票，您的朋友(每天第一票的学员)也能自动加一票！注意：每日仅3次加票机会！”",
								buttons : [
										{
											text : "去点评",
											onClick : function() {
												location.href = "/wechat/api/randomVideos?memberId="
														+ memberId
											}
										}, {
											text : "下次吧",
											onClick : function() {
												console.log(2)
											}
										}, ]
							});
				}, 2500);
	}
</script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="/wechat/js/wechat/wechat-share.js"></script>
<script type="text/javascript">
	var config = {
		link : "http://wechat.fandoutech.com.cn/wechat/api/videoLogin?vid=${video[1]}",
		title : "英语新势力&蛋蛋机器人英语秀",
		imgUrl : "http://test.fandoutech.com.cn/wechat/activity/img/header.jpg",
		desc : '我参加了由凡豆教育举办的<<英语新势力&蛋蛋机器人英语秀>>大赛,快来帮我投上你宝贵的一票吧',
		debug : false
	}

	$.wechatShare(config);
</script>

</html>