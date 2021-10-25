<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>答题成绩</title>

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css" href="css/style3.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap337.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
	<script src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
	<script
	src="<%=request.getContextPath()%>/js/bootstrap337.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<script type="text/javascript">
	var level='<%=Integer.parseInt(request.getParameter("level"))%>';
	var str=["","根据我们刚才的小测验，你适合学习启蒙班的课程","根据我们刚才的小测验，你适合学习基础班的课程","根据我们刚才的小测验，你适合学习进阶班的课程","根据我们刚才的小测验，你适合学习提高班的课程"];
	$(function(){
	if(level<=4){
	setTimeout("showInfo()", 1200);
	}
	
	$("#btn").click(function(){
		window.location.href="signSuccess2.jsp";
	});
	
	});
	function showInfo(){
		for(var i=1;i<=level;i++){
		setTimeout($('#star'+i).attr('src','images/star-sel@2x.png'), 1000);
		}
		$("#level").attr("src","images/level-"+(level-1)+"@2x.png");
		$("#p1").text(str[level]);
	}
</script>
</head>

<body>
<div class="bg">
	<h3>测试结果</h3>
	<div class="neirong">
		<div  class="starr starr1">
			<img id="star1" src="images/star-no@2x.png" >
		</div>
		<div  class="starr starr2">
			<img id="star2" src="images/star-no@2x.png" >
		</div>
		<div  class="starr starr3">
			<img id="star3" src="images/star-no@2x.png" >
		</div>
		<div  class="starr starr4">
			<img id="star4" src="images/star-no@2x.png" >
		</div>
	</div>
</div>
<div id="info" class="info">
	<p>小朋友</p>
	<p>你真棒!</p>
</div>
</div>
<div class="neirong2">
	<img id="level" src="images/level-0@2x.png" style="width: 100%;margin: 0 auto;">
	<br><br>
	<p id="p1" >根据我们刚才的小测验，你适合学习基础班的课程</p>
	<p>欢迎加入凡豆英语课堂</p>	
</div>
<div class="footer-btn">
	<button type="button" class="btn btn-default btn btn-primary btn-success btn-lg btn-block"  id="btn">去学习</button>
</div>
</body>
</html>