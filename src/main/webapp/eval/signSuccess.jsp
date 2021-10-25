<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>微信测评</title>

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="css/bootstrap337.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap337.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function(){
		$("#btn1").click(function(){
			window.location.href="<%=request.getContextPath() %>/eval/evaluation.html";
		});
		
		$("#btn2").click(function(){
			window.location.href="<%=request.getContextPath() %>/eval/signSuccess2.jsp";
		});
	});

</script>
<style type="text/css">
body{
			background-image: url("<%=request.getContextPath() %>/activity/images/bg1.png");
			background-repeat: no-repeat;
			background-size:100%;
		}
</style>
  </head>  
  <body>
   <div class="container" >
		<div style="width: 90%;height: 300px;background-color: white;margin: 0 auto;margin-top: 80%;border-radius: 15px;">
			<br>
			<div style="width: 100%;height: 50px;text-align: center;line-height: 65px;border-radius: 15px;">
				<span id="title" style="font-size: 18px;color: #6E6048">您了解您孩子的英语水平吗？</span>
			</div>
			<div style="width: 100%;height: 50px;text-align: center;line-height: 65px;border-radius: 15px;">
				<span id="title" style="font-size: 15px;color: #6E6048">我们准备了一个简单的英语听力能力测评</span>
			</div>
			<div style="width: 100%;height: 50px;text-align: center;line-height: 65px;border-radius: 15px;">
				<span id="title" style="font-size: 15px;color: #6E6048">大概需要5分钟,是否参与？</span>
			</div>
			
			<div class="footer" style="text-align: center;margin-top:40px;margin-bottom: 10px">
			<img id="btn1" src="<%=request.getContextPath() %>/activity/images/112681.png" style="width: 120px;height: 50px">
			<img id="btn2" src="<%=request.getContextPath() %>/activity/images/112682.png" style="width: 120px;height: 50px">
			</div>
		</div>
  </body>
</html>
