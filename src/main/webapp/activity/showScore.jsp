<%@ page language="java" import="java.util.*,com.wechat.entity.VideoAcitivity" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>英语新势力&#38;蛋蛋机器人英语秀【襄汾站】</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/videoInfo.css">
</head>
<body>
<div class="container-fluidcontainer-fluid" >
    <div class="title">
        <h4>参赛选手：${video[7]}&nbsp;&nbsp;&nbsp;&nbsp;${classGrades.classGradesName }</h4>
        <HR align=center width=100% color=#987cb9 SIZE=1>
    </div>
    <div class="video">
        <video controls="controls" poster="${video[2]}">
            <source src="${video[1]}">
        </video>
    </div>
    <div class="content">
        <div class="text1">
            <h5>${video[6]}</h5>
        </div>
        <div class="text1">
            <h5 style="float: left;">${video[24]}&nbsp;&nbsp;参赛编号:${video[0]}</h5>
        </div>
    </div>
    <div class="button">
        <div class="voteNum">
            <%-- <span id="vote">已获票数：${video[14]}</span>&nbsp;&nbsp;&nbsp;<span id="score">总得分：${score }</span> --%>
            <span id="vote">已获票数：<span id="vote-num">${video[4]}</span></span>
            <br><br>
        </div>
    </div>
    
    <input id="videoId" type="hidden" value="${video_id }">
    
	<footer class="footer">
		<div class="footer_text">
		Copyright © 2018  东莞市凡豆信息科技有限公司
		</div>
	</footer>
</div>
</body>
<script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>
<script type="text/javascript">

		var videoId = $('#videoId').val();
		$(function(){
			if(videoId==null||videoId==""||videoId==undefined){
				alert("视频信息不存在");
			}
		});
		
</script>

</html>