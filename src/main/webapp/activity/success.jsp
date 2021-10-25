<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>投票结果</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/wechat/wechat-share.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
body{
    background-image: url("<%=request.getContextPath()%>/activity/img/end.jpg");
    background-size: 100%;
    background-repeat: no-repeat;
}
	</style>
  </head>
  <script type="text/javascript">
  	var config={
  			link:"http://wechat.fandoutech.com.cn/wechat/h5/bookShop/applyShopCard",
  			title:"测试",
  			imgUrl:"http://wechat.fandoutech.com.cn/wechat/wechatImages/image/20161221/20161221161442_60.png",
  			desc:'描述',
  			debug:true
  	}
  	$.wechatShare(config);
  </script>
  <body>
    
  </body>
</html>
