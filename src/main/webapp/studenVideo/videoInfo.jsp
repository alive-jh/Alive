<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/studenVideo/css/videoInfo.css">
</head>
<script type="text/javascript">
	function index(){
		window.history.go(-1);
	}
</script>
<body>
<div class="container-fluidcontainer-fluid">
    <div class="title-bar">
        <div class="back" onclick="index()">
            <i class="glyphicon glyphicon-menu-left"></i>
        </div>
        <div class="title-text">
            <span>视频详情</span>
        </div>
    </div>

    <div class="student-title">
        <h4>姓名:${data.getStr('name') }&nbsp;&nbsp;&nbsp;&nbsp;${data.getStr('class_grades_name') }</h4>
    </div>
    <HR align=center width=90% color=#987cb9 SIZE=1 style="margin: 0 auto">
    <div class="video-info">
        <video controls="controls" width="100%" height="300px" style="background-color: black" poster="${data.getStr('pic_url') }">
            <source src="${data.getStr('v_url') }">
        </video>
    </div>
    <HR align=center width=90% color=#987cb9 SIZE=1 style="margin: 0 auto">

    <div class="student-info">
        <div class="video-title"><span style="font-size: 15px">${data.getStr('v_title') }</span></div>
        <div class="learn-time"><span style="font-size: 15px">${fn:substring(data.getStr('create_time'), 0, 16)} &nbsp;</span></div>
        <div class="page-ad1">
            <a style="float: right;" href="https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzIzNDAzMzkzMQ==#wechat_redirect">30天轻松登台show英语？how？</a>
        </div>
    </div>

    <div class="buttons1">
        <a class="btn btn-warning" href='<%=request.getContextPath()%>/h5/userVideo/getShowStudents' target='_top'>看看其他人</a>
    </div>

    <div class="dibu">
        <div class="title-text">
            Copyright © 2017 东莞市凡豆信息科技有限公司
        </div>
    </div>
</div>


</body>
</html>