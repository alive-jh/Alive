<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>活动报名</title>
	
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="this is my page">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap337.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/style.css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/qiandao_style.css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/eval/css/zdialog.css"/>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/eval/css/reset.css">
	<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
	<script src="<%=request.getContextPath() %>/eval/js/zdialog.js"></script>
	

	<script>
		
		function confirmL(){
			window.location.href="evaluation.html";
   		}

   		function Immediate(){
	  		window.location.href="signSuccess2.jsp";
   		}
		
		
		$(function(){
		
			var memberId = $("#memberId").val();
		
			$.ajax({
				type: "POST",
				url:"../eval/getSignInfo?memberId="+memberId,
				async: false,
	            error: function(request) {
	                  	alert("错误！！");
	            },
	            success: function(data) {
	            	if(data.sign!=0){
	            		$.DialogByZ.Confirm({Title: "", Content: "系统检测到您之前已经报名,是否开始测评",FunL:confirmL,FunR:Immediate});
	            	}
	            }
	        });
			
		
			$("#btn1").click(function(){
			if($("#uName").val()==""||$("#uName").val()==null||$("#uName").val()==undefined){
				alert("请填写宝贝姓名");
				return false;
			}
			if($("#uGrade").val()==""||$("#uGrade").val()==null||$("#uGrade").val()==undefined){
				alert("请填写宝贝年级");
				return false;
			}
			if($("#uAge").val()==""||$("#uAge").val()==null||$("#uAge").val()==undefined){
				alert("请填写宝贝年龄");
				return false;
			}
			if($("#mobile").val()==""||$("#mobile").val()==null||$("#mobile").val()==undefined){
				alert("请填写手机号码");
				return false;
			}else{
				if(!(/^1[34578]\d{9}$/.test($("#mobile").val()))){ 
					alert("手机号码有误，请重填");  
					return false; 
				} 
			}
			
			$.ajax({
				type: "POST",
				url:"../eval/saveUserSign",
				data:$('#form1').serialize(),
				async: false,
                error: function(request) {
                      	alert("错误！！");
                },
                success: function(data) {
                	alert("报名成功");
                	window.location.href="signSuccess.jsp";
                }
            });

			
		
		}); 
		
		
	});
	</script>

	<style type="text/css">
		body{
			background-image: url("<%=request.getContextPath() %>/activity/images/bg1.png");
			background-repeat: no-repeat;
			background-size:100%;
		}

		#form1 span{
			font-weight: 500px;
			font-stretch: 黑体;
			color: #A5977C;
			line-height: 35px;
		}
		input {
			text-align: center;
		}
		.spandiv{
			height: 35px;
		}
	</style>
</head>

<body>
	<div class="container" >
		<div style="width: 90%;height: 350px;background-color: white;margin: 0 auto;margin-top: 80%;border-radius: 15px;">
			<div style="width: 100%;height: 50px;text-align: center;line-height: 65px;border-radius: 15px;">
				<span id="title" style="font-size: 25px;color: #6E6048">完善信息</span>
				<br>
			</div>
			<div id="from" style="width: 85%;margin: 0 auto;">	
				<form id="form1">
					<div class="spandiv"><span>宝贝姓名：</span></div>
					<input class="form-control" name="uName" id="uName" placeholder="请输入宝贝姓名">
					<div class="spandiv"><span>宝贝年级：</span></div>
					<input class="form-control" name="uGrade" id="uGrade" placeholder="请输入宝贝年级,如:一年级">
					<div class="spandiv"><span>宝贝年龄：</span></div>
					<input class="form-control" name="uAge" id="uAge" placeholder="请输入宝贝年龄，如:9">
					<div class="spandiv"><span>家长手机号码：</span></div>
					<input class="form-control" name="mobile" id="mobile" placeholder="请输入手机号码">
					<input id="memberId" name="memberId" value="<%=request.getParameter("memberId")%>" style="display: none;">     
				</form>
			</div>
			
			<div class="footer" style="text-align: center;margin-top:40px;margin-bottom: 10px">
			<img id="btn1" src="<%=request.getContextPath() %>/activity/images/116625.png" style="width: 120px;height: 50px">
			</div>
		</div>
		<br>
	</div>
	
</body>
</html>
