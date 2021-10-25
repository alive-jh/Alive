<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'videoActivity.jsp' starting page</title>
    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="this is my page">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
	<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
   	<script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
   	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		
		$(function(){
			showvideoActivity();
			var vid = "<%=request.getParameter("vid")%>";
			$("select").change(function(){
				action();
			
			});
			
			
			$("#btn1").click(function(){
				var activityId = $("select").val();
				var vid = <%=request.getParameter("vid")%>;
				$.ajax({
					type: "POST",
					url:"<%=request.getContextPath()%>/api/saveVideoCompetition?vid="+vid+"&activityId"+activityId,
					data:{vid:vid,activityId:activityId},
					async: false,
					error: function(request) {
						alert("错误！！");
					},
					success: function(result) {
						if(result.success==1){
							alert("参赛成功");
						}else{
							alert("参赛失败");
						}
					}
				});
			});
		});
		
		function showvideoActivity(){
			$.ajax({
				type: "POST",
				url:"<%=request.getContextPath()%>/api/getVideoAcitivity",
				async: false,
				cache:false,
				error: function(request) {
					alert("错误！！");
				},
				success: function(result) {
					if(result!=null){
						var data = result.data;
						console.log(data);
						for(var i=0;i<data.length;i++){
						$("select").append("<option value="+data[i].id+">"+data[i].activityName+"</option>");
						}
					}
				}
			});
		
		}
		
		function action(){
			var vid = "<%=request.getParameter("vid")%>";
			var sid = $("select").val();
			$.ajax({
				type: "POST",
				url:"<%=request.getContextPath()%>/api/verificationVideo",
				data:{vid:vid,activityId:sid},
				async: false,
				cache:false,
				error: function(request) {
					alert("错误！！");
				},
				success: function(result) {
					console.log(result);
					if(result.success==1){
						alert("抱歉，这场比赛已经参加");
						$("#btn1").css("display","none");
						
					}else{
						$("#btn1").css("display","");
					}
				}
			});
		}
		
	
	</script>
  </head>
  
  <body>
    <div style="width: 100%; text-align: center;margin-top: 20%">
    	<select></select>
    	
    	<button id="btn1" style="display:">确认参赛</button>
    </div>
  </body>
</html>
