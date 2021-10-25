<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>抽奖</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="凡豆,抽奖,活动">
	<meta http-equiv="description" content="凡豆会销抽奖">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/activity/js/luckDraw.js"></script>
    <style type="text/css">
        #bigDiv {
            width: 100%;
            margin: 0px auto; /*div网页居中*/
            background-color: #494949;
            color: #FFFFFF;
        }

        h1 {
            text-align: center; /*文本居中*/
            padding-top: 10px;
        }

        #first{
            width: 100%;
            height: 200px;
            font-size: 150px;
            line-height: 360px;
            text-align: center;
        }

        #first {
            background-color: #009BFF;
    
        }

        input {
            font-size: 30px;
            font-weight: 900;
        }

        #start {
            margin-left: 40%;
            margin-right: 5%;
        }
        a{color:blue;}
    </style>
  </head>
  
  <body>
	<div id="bigDiv">
        <h1>幸运抽奖活动</h1>
        <div id="first"></div>
        <div>
            <input type="button" value="开始" id="start">
        	<input type="button" value="停止" id="stop" disabled="disabled">
        </div>

    </div>
  </body>
</html>
