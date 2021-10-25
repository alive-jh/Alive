<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>优秀视频展示</title>
		<!-- 此处与客户端的系统有关，关系到屏幕的宽度 -->

		<meta name="viewport" content="user-scalable=yes, width=640">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
		<!-- 屏幕的宽度设置 -->
		<style>
			body{
				width: 100%;

			}
			.header{
				margin-top: 20px;
				text-align: center;
				width: 100%;
			}
			.content{
				width:100%;
				margin-top: 80px;

			}
			.videoList{
				width: 100%;
			}
			.footer{
				text-align: center;
				margin-top: 80px;
				width: 100%

			}
			.footer button{

				width: 80%;
				height: 80px;
				font-size: 2.5em;
				background-color: #FF6600;
				color: #ffffff;
			}
			.list-group{
				width:100%;
				margin-top: 30px;
			}
			.list-group-item{
				width: 100%;
				text-align: center;

			}
			.list-group_item video{
				width: 80%;
			}

		</style>
	</head>
	<body>
		<!--内容部分开始-->
		<div class="container ">
			<div class="header">
				<select>
					<option>兰陵</option>
					<option>烟台</option>
				</select>
				<select>
					<option>兰陵</option>
					<option>烟台</option>
				</select>
			</div>
			<div class="content">
				<div class="videoList">
					<div class="list-group">
					    <div class="list-group-item">
					    	<div>
								兰陵：初级班（刘XX）
					    	</div>
					    	<video src="http://video.fandoutech.com.cn/10001.mp4" controls="controls" width="100%" height="320px" preload="auto" poster="http://video.fandoutech.com.cn/2.jpg"></video>
					    </div>
					    <div class="list-group-item">
					    	<video src="http://video.fandoutech.com.cn/10002.mp4" controls="controls" width="100%" height="320px" preload="auto" poster="http://video.fandoutech.com.cn/2.jpg"></video>
						</div>
					    <div class="list-group-item">
					    	<video src="http://video.fandoutech.com.cn/10003.mp4" controls="controls" width="100%" height="320px" preload="auto" poster="http://video.fandoutech.com.cn/2.jpg"></video>
						</div>
					    <div class="list-group-item">
					    	<video src="http://video.fandoutech.com.cn/10004.mp4" controls="controls" width="100%" height="320px" preload="auto" poster="http://video.fandoutech.com.cn/2.jpg"></video>
					    </div>
					    <div class="list-group-item">
					    	<video src="http://video.fandoutech.com.cn/10005.mp4" controls="controls" width="100%" height="320px" preload="auto" poster="http://video.fandoutech.com.cn/2.jpg"></video>
					    </div>
					</div>	
				</div>
				
			</div>

			<!--选购按钮开始-->
			<div>

			</div>
		</div>
		<!--底部开始-->
		
		<!--底部结束-->
	</body>
	<!--页内脚本开始-->
	<script>

        
	</script>
</html>
