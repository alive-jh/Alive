<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ranking.jsp' starting page</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div style="width: 100px;margin: 0 auto">
    			<BR><BR>
	        	<a href="<%=request.getContextPath() %>/api/getAreaRanking?areaId=10000" class="btn btn-warning" style="width: 100%">兰陵</a>
	           	<BR><BR>
	        	<a href="<%=request.getContextPath() %>/api/getAreaRanking?areaId=10002" class="btn btn-warning" style="width: 100%">南充</a>
	           	<BR><BR>
	        	<a href="<%=request.getContextPath() %>/api/getAreaRanking?areaId=10004" class="btn btn-warning" style="width: 100%">临汾</a>
	           	<BR><BR>
	           	<a href="<%=request.getContextPath() %>/api/getAreaRanking?areaId=10006" class="btn btn-warning" style="width: 100%">怀宁</a>
	           	<BR><BR>
    </div>
  </body>
</html>
