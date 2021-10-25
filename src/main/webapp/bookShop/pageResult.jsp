<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>申请结果</title>

<style type="text/css">
	.title{
		width: 100%;
		text-align: center;
		margin-top: 75%;
	}
</style>
</head>
<body>
	
	<div class="title">
		<div><img alt="" src="http://image.fandoutech.com.cn/<%=request.getAttribute("pic") %>" width="50px" height="50px"></div>
		<h5><%=request.getAttribute("message") %></h5>
	</div>
	
</body>
</html>