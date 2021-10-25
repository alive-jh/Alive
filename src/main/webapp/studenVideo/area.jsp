<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>地区</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/studenVideo/css/videoInfo.css">
<style type="text/css">
	.content{
		    width: 100%;
		    text-align: center;
		    height: 500px;
		    padding: 65px 0px 0px 5px;
	}
	.study_time{
		    width: 43%;
		    height: 50px;
		    margin: 3%;
		    float: left;
		    line-height: 51px;
		    border-radius: 11px;
		    border: 1px solid black;
	}	
</style>
</head>
<script type="text/javascript">
	function index(){
		window.history.go(-1);
	}
	
	function areaVideo(_this){
		var agentId = _this.id;
		location.href="<%=request.getContextPath()%>/h5/userVideo/getShowStudentsByArea?agentId="+agentId;
	}
	
	
</script>
<body>
<div class="container-fluidcontainer-fluid">
    <div class="title-bar">
        <div class="back" onclick="index()">
            <i class="glyphicon glyphicon-menu-left"></i>
        </div>
        <div class="title-text">
            <span>地区</span>
        </div>
    </div>

    <div class="content">
       <div class="study_time" id="10000" onclick="areaVideo(this)">
       	<span>兰陵</span>
       </div>

















	   <div class="study_time" id="10001" onclick="areaVideo(this)">
       	<span>安徽</span>
       </div>
       
       <div class="study_time" id="10002" onclick="areaVideo(this)">
       	<span>南充</span>
       </div>
       
       <div class="study_time" id="10003" onclick="areaVideo(this)">
       	<span>河南</span>
       </div>
       
       <div class="study_time" id="10004" onclick="areaVideo(this)">
       	<span>临汾</span>
       </div>
       
       <div class="study_time" id="10005" onclick="areaVideo(this)">
       	<span>遵义</span>
       </div>
       
       <div class="study_time" id="10006" onclick="areaVideo(this)">
       	<span>怀宁</span>
       </div>
       <div class="study_time" id="10007" onclick="areaVideo(this)">
       	<span>烟台</span>
       </div>
       <div class="study_time" id="10008" onclick="areaVideo(this)">
       	<span>天津</span>
       </div>
       <div class="study_time" id="100010" onclick="areaVideo(this)">
       	<span>贵州</span>
       </div>
       
    
	</div>
	
	<div class="dibu">
        <div class="title-text">
            Copyright © 2017 东莞市凡豆信息科技有限公司
        </div>
    </div>


</body>
</html>