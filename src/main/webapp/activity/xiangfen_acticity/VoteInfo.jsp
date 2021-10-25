<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<!-- head 中 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">
<title>我的投票记录</title>
<style type="text/css">
.demos-header {
	padding: 20px 0;
}

.demos-title {
	text-align: center;
	font-size: 34px;
	color: #3cc51f;
	font-weight: 400;
	margin: 0 15%;
}
</style>
</head>
<body>
	<header class="demos-header">
	<h1 class="demos-title">我的投票记录</h1>
	</header>
	<div class="bd">
		<div class="page__bd">
			<div class="recoder_list">
			<div class="weui-cells__title">2018.05.13</div>
			<div class="weui-cells">
				<a class="weui-cell weui-cell_access" href="javascript:;">
					<div class="weui-cell__hd">
						<span style="width: 20px; margin-right: 5px; display: block">3.</span>
					</div>
					<div class="weui-cell__bd">
						<p>纪雄峰</p>
					</div>
					<div class="weui-cell__ft">参赛编号:8082</div>
				</a>
			</div>
		</div>
		</div>
	</div>
	
	<input type="hidden" id="memberId" value="${memberId }">
</body>
<!-- body 最后 -->
<script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>
<script type="text/javascript">

	var memberId = $('#memberId').val();
	
	$(function(){
		_list();
	})
	
	
	function _list(){
		$.ajax({
            type: "POST",
            url:'/wechat/v2/video/voteHistory?_time'+new Date().getTime(),
            data:{memberId:memberId},
            async: false,
            beforeSend: function(){
            	$.showLoading();
            },
            complete: function(){
            	$.hideLoading();
            },
            error: function(request) {
                alert("错误！！");
                return;
            },
            success: function(success) {
            	if(success.code==200){
    				init(success.data.list);
            	}
            }
        });
	}
	
	
	function init(list){
		
		var _recodeList = '';
		$(list).each(function (n, v) {
			
			_recodeList += `<div class="recoder_list">
			<div class="weui-cells__title">`+v.voteTime+`</div>`;
			
			$(v.voteList).each(function (n1, v1) {
				_recodeList+=`<div class="weui-cells">
								<a class="weui-cell weui-cell_access" href="/wechat/api/getAllVideoCompetitionByVerifSuccess2?vid=`+v1.vid+`&memberId=`+memberId+`">
									<div class="weui-cell__hd">
										<span style="width: 20px; margin-right: 5px; display: block">`+(parseInt(n1)+1)+`.</span>
									</div>
									<div class="weui-cell__bd">
										<p>`+v1.name+`</p>
									</div>
									<div class="weui-cell__ft">参赛编号:`+v1.vid+`</div>
								</a>
								</div></div>`;
			});
			
		});
		
		$('.page__bd').html(_recodeList);
	}
	
</script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="/wechat/js/wechat/wechat-share.js"></script>
<script type="text/javascript">
var config = {
		link : "http://wechat.fandoutech.com.cn/wechat/api/videoLoginIndex",
		title : "英语新势力&蛋蛋机器人英语秀",
		imgUrl : "http://test.fandoutech.com.cn/wechat/activity/img/header.jpg",
		desc : '很荣幸成为凡豆好声音导师，这是我最满意的学员，小伙伴们也支持TA，为TA投票吧',
		debug : true
	}
	
$.wechatShare(config);
</script>
</html>