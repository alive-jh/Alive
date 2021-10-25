<%@ page language="java" import="java.util.*,com.wechat.entity.VideoAcitivity" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>情景英语第九届口语大赛总决赛</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/scenarioEnglish/videoInfo.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/video625js/js/video.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/js/video625js/css/video-js.css">
</head>
<script type="text/javascript">
	function vote(that){
		var _this = $(that);
		var vid = that.id;
		var lnum =parseInt((_this.find('span').html())) ;
		$.ajax({
            type: "POST",
            url:"<%=request.getContextPath()%>/api/scenarioEnglishLike",
            dataType: "json",
            data:{vid:vid},
            async: false,
            error: function(request) {
                alert("错误！！");
            },
            success: function(result) {
            	if(result.code==200){
            		lnum++;
            		_this.find('span').html(""+lnum);
            	}
            }
        });
	}
	
	$(function(){
		if("${data[3]}"==null||"${data[3]}"==""||"${data[3]}"==undefined){
			alert("视频信息不存在");
		}
	});
	

</script>
<body>
<div class="container-fluidcontainer-fluid" >
    <div class='topbar'>
        <a href='<%=request.getContextPath() %>/api/scenarioEnglishAll' target='_top'><i class=" icon-angle-left"></i>< 首页</a>
    </div>
    <div class="title">
        <h4>姓名：${data[1]}</h4>
        <HR align=center width=100% color=#987cb9 SIZE=1>
    </div>
    <div class="video">
        <video controls="controls" width="95%" height="80px" poster="${data[3]}">
            <source src="${data[2]}" type="video/mp4">
        </video>
    </div>
    <div class="content">
        <div class="text1">
            <h5>情景英语第九届口语大赛总决赛</h5>
        </div>
        <div class="text2">
            <a style="float: right;" href="https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIzNDAzMzkzMQ==#wechat_redirect">30天轻松登台show英语？how？</a>
        </div>
    </div>
    <div class="button" style="margin-top: 20px">
        <div class="vote">
            <button id="${data[0]}" class="btn btn-warning" onclick="vote(this)" style="width: 80px"><i class='glyphicon glyphicon-thumbs-up'></i>&nbsp;&nbsp;&nbsp;<span>${data[6] }</span> </button>
            <a class="btn btn-warning" href='<%=request.getContextPath() %>/api/scenarioEnglishAll' target='_top'><i class=" icon-angle-left"></i>看看其他人</a>
        </div>
    </div>
	<input id="memberId" type="text" value="${memberId }" style="display: none">
	<footer class="footer" style="margin-top: 8%">
		<div class="footer_text">
		Copyright © 2017 东莞市凡豆信息科技有限公司
		</div>
	</footer>
</div>
</body>
</html>