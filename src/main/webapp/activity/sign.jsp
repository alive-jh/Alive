<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>活动签到</title>
	
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="this is my page">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap337.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/style.css">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/qiandao_style.css">
	<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>

	<script>
		var singId = ${signId};
		var userSign = ${userSign};
		function signIn(){
			
			if(singId!=0){
				alert("您已经签到过啦");
				return false;
			}
		
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
				url:"<%=request.getContextPath()%>/activityAPI/saveSignInfo",
				data:$('#form1').serialize(),
				async: false,
				error: function(request) {
					alert("错误！！");
				},
				success: function(result) {
					if(result!=null){
						console.log(result);
						$('#number').html(result.signId);
						$(".maskbox").fadeIn();
						$(".qdbox").fadeIn();
						$(".maskbox").height($(document).height());
					}
				}
			});
		}
		
		function isWeiXin(){
    		var ua = window.navigator.userAgent.toLowerCase();
    		if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        		return true;
    		}else{
        		return false;
    		}
		}
		

		$(function(){
		
			if(userSign!=0){
				$("#title").css("font-size","16px");
				$("#title").html("欢迎您${userSignInfo.uName}的家长,您已经登记过信息");
				$("#from").css('display','none');
				$("#t2").css('display','');
				$("#title2").html("请点击签到获取抽奖号码");
				$("#memberId").val("${userSignInfo.memberId}");
				$("#uName").val("${userSignInfo.uName}");
				$("#uGrade").val("${userSignInfo.uGrade}");
				$("#uAge").val("${userSignInfo.uAge}");
				$("#mobile").val("${userSignInfo.mobile}");
			}
			
			if(singId!=0){	
				$("#show").css('display','');
				$("#t2").css('display','none');			
				$("#title2").html("");
				}
			
			$("#btn1").click(function(){
				$(".maskbox").fadeOut();
				$(".qdbox").fadeOut();
				if(isWeiXin()){
				var memberId1 = $("#memberId").val();
				window.location.href="<%=request.getContextPath()%>/activityAPI/getMemberInfo?memberId="+memberId1+"&id="+Math.floor(Math.random()*1000);
				}else{			
				location.reload();
				}
			});
			
			
		});
	</script>

	<style type="text/css">
		body{
			background-image: url("<%=request.getContextPath() %>/activity/images/bg1.png");
			background-repeat: no-repeat;
			background-size:100%;
		}

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
	<div class="container-fluid" >
		<div style="width: 90%;height: 350px;background-color: white;margin: 0 auto;margin-top: 80%;border-radius: 15px;">
			<div style="width: 100%;height: 50px;text-align: center;line-height: 65px;border-radius: 15px;">
				<span id="title" style="font-size: 25px;color: #6E6048">完善信息</span>
				<br>
			</div>
			<div id="t2" style="width: 100%;height: 50px;text-align: center;margin-top:30%;display: none">
			<span id="title2" style="font-size: 25px;color: #6E6048"></span>
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
					<p style="font-size: 10px;width: 100%;text-align: center; line-height: 25px;color: burlywood">注:抽奖时需验证手机号码</p>
					<input id="memberId" name="memberId" value="<%=request.getParameter("memberId")%>" style="display: none;">     
				</form>
			</div>
			<div id="show" style="width: 80%;margin: 0 auto;text-align: center;margin-top: 20%;display: none">	
				<h3>您的抽奖号码是:</h3>
				<span style="font-size: 50px;color:darkorange">${signId }</span>
			</div>
		</div>
		<div class="footer" style="text-align: center;margin-top:15px">
			<img src="<%=request.getContextPath() %>/activity/images/112.png" style="width: 120px;height: 50px" onclick="signIn()">
			<%-- <img id="btn2" src="<%=request.getContextPath() %>/activity/images/112.png" style="width: 120px;height: 50px;display: none" onclick="signIn2()"> --%>
		</div>
		<br>
	</div>
	<div class="maskbox"></div>
	<div class="qdbox" style="text-align: center;">
		<div class="text-center text-green font18"><strong id="qiandao">签到成功！</strong></div>
		<div class="text-center pt10"><span style="font-size: 20px">您的抽奖号码为</span></div>
		<div class="text-center ptb15"><span style="font-size: 50px" id="number"></span></div>
		<div class="text-center"><button class="btn btn-lottery" id="btn1">记住了</button></div>
	</div>
	
</body>

</html>
