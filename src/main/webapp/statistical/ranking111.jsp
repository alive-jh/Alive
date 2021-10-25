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

	<title>成绩排行榜</title>

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
		var nameData=[];
		$(function(){
			$('.headImg').attr('src',textToImg("${student[14]}"));
			
			for(var i=0;i<nameData.length;i++){
			$('#img12').attr('src',textToImg("铭强"));
			}	
			
			$('.content').css('height',$(window).height());
				
		});

		function textToImg(uname) {
			var name='';
			var fontSize = 18;
			var w=33;
			var h=33;
			if(uname.length==3){
				name = uname.charAt(1)+uname.charAt(2);
			}else if(uname.length==2){
				name = uname.charAt(1);
			}else{
				name = uname.charAt(0);	
			}
            //var name = uname.charAt(0)+uname.charAt(1);
            var fontWeight = 300;
            var color=['#61ACFF','#FF7561','#FFAE61','#9C5BFF'];
            var id = Math.floor(Math.random()*3);
            var canvas = document.getElementById('headImg');
            var img1 = document.getElementById('headImg');
            canvas.width = 65;
            canvas.height = 65;
            var context = canvas.getContext('2d');
            context.fillStyle = color[id];
            context.fillRect(0, 0, canvas.width, canvas.height);
            context.fillStyle = '#FFFFFF';
            context.font = fontWeight + ' ' + fontSize + 'px sans-serif';
            context.textAlign = 'center';
            context.textBaseline="middle";
            context.fillText(name, w, h);
            return canvas.toDataURL("image/png");
        };
    </script>
    <style type="text/css">
    	.hander {
    		position: relative;
    		width:200px;
    		height:100px;
    		margin:0 auto;
            margin-top: 20px;
    	}
    	.hander #x {
    		position: absolute;
    		top:-10px;
    		left:53px;
    	}
        .row{
            width: 95%;
            margin: 0 auto;
        }
        
        .content{
        	width: 100%;
        	height: 800px;
        	background: yellow;
        	
        }

    </style>
    <body> 
    	<div class="container-fluid">
    		<c:if test="${ranking <=10}">
    		<div id="title" class="hander" style="text-align: center;" >
    			<img src="" class="img-circle headImg" alt="User Image">
    			<img src="<%=request.getContextPath()%>/statistical/img/gx@2x.png" id="x" width="95" height="95" >
    			<h3 style="color: orange;">${student[7] }分/排名${ranking }</h3>
    		</div>
    		</c:if>
    		<c:if test="${ranking >10}">
    		<div id="title" class="hander" style="text-align: center;" >
    			<img src="" class="img-circle headImg" alt="User Image">
    			<img src="<%=request.getContextPath()%>/statistical/img/gx@2x.png" id="x" width="95" height="95" >
    			<h3 style="color: orange;">${student[7] }分</h3>
    		</div>
    		</c:if>
            <HR align=center width=100% color=#987cb9 SIZE=1>
			<div class="content">
				
			</div>
			
    			

   </div>
   <canvas id="headImg" style="display:none"></canvas>

</body>
</html>

		
		
