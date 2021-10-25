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
    
    <title>My JSP 'sign.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
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
 	$(function(){
 	
 		$("#btn1").click(function(){
 			var userName=$("#userName").val();
 			var userPhone=$("#userPhone").val();
 			var memberId=$("#memberId").val();
 			
 			$.ajax({
                type: "POST",
                url:"<%=request.getContextPath()%>/activityAPI/savaSignInfo",
                data:{userName:userName,
                	  userPhone:userPhone,
                	  memberId:memberId,
                	  action:1},
                async: false,
                error: function(request) {
                      	alert("错误！！");
                },
                success: function(result) {
                    alert('您的抽奖号码为'+result.singId);
                }
            });
 		});
 		
 		$("#btn2").click(function(){
 		
 			$.ajax({
                type: "POST",
                url:"<%=request.getContextPath()%>/activityAPI/savaSignInfo",
                data:{userName:"${memberInfo[1]}",
                	  userPhone:"${memberInfo[9]}",
                	  memberId:"${memberInfo[0]}",
                	  action:2},
                async: false,
                error: function(request) {
                      	alert("错误！！");
                },
                success: function(result) {
                    alert('您的抽奖号码为'+result.singId);
                }
            });
            
 		});
 	
 	});
 
 </script>

  <style type="text/css">
 body{
 background-image: url("images/123.jpg");
 background-repeat: no-repeat;
 background-size:100%;
 background-color: 
 }		
	
  span{
  font-size: 17px;
  top: 500px;
  }
  </style>
  </head>
  
  <body>
    <div class="container-fluid" style="margin-top: 15%">
    	<div class="row">
    	<div class="col-md-12" style="text-align: center;">
    	<h3>欢迎参加凡豆科技会展</h3>
    	</div>	
    	</div>
    	
    	<c:choose>
    	<c:when test="${memberInfo[9] eq '' || memberInfo[9]==null }">
    	<div class="row">
    	
    	</div>
    	<br>
    	<div class="row">
    	<div style="width: 70%;margin: 0 auto">
    	 <span>宝贝姓名/昵称：</span>
    	 <input class="form-control" name="userName" id="userName" placeholder="请输入用户姓名">
		 <br>
		<span>宝贝年级：</span>
		<input class="form-control" name="userPhone" id="userPhone" placeholder="请输入手机号码">
		<br>
		<span>宝贝年龄：</span>
		<input class="form-control" name="userPhone" id="userPhone" placeholder="请输入手机号码">
		<br>
		<span>家长手机号码：</span>
		<input class="form-control" name="userPhone" id="userPhone" placeholder="请输入手机号码">
		<br>
		<input id="memberId" name="memberId" value="${memberInfo[0] }" style="display: none;">     
    	</div>	
    	</div>
    	</c:when>
    	<c:otherwise>
    		<div class="row">
    		<div class="col-md-12" style="text-align: center;">
    		<h3>请点击签到获取抽奖号码</h3>
    		</div>	
    	</div>
    	</c:otherwise>
    	</c:choose>
    	
    	<br>
    	<c:choose>
    	<c:when test="${memberInfo[9] eq '' || memberInfo[9]==null }">
    		<div class="modal-footer" >
                <button type="button" class="btn btn-default btn btn-primary btn-lg btn-block"  id="btn1" >开始签到</button>
         	</div>
    	</c:when>
    	<c:otherwise>
    	<div class="modal-footer" >	
                <button type="button" class="btn btn-default btn btn-primary btn-lg btn-block"  id="btn2" >开始签到</button>
         	</div>
    	</c:otherwise>
    	</c:choose>
    <!-- 模态框 -->	
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    	<div class="modal-dialog">
        <div class="modal-content">
           <div class="modal-body">
           <h3>测试测试</h3>
           
		   </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    
    </div>
    
  </body>	
</html>
