<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
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
   <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
   <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
   <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <script type="text/javascript">
      var nameData=[];
      $(function(){
         $('.headImg').attr('src',textToImg("${student[14]}"));
         
         for(var i=0;i<nameData.length;i++){
            $('#img'+(i+1)).attr('src',textToImg(nameData[i]));
         }     
         
         for(var i=0;i<3;i++){
         	$('#x'+(i+1)).attr('src','<%=request.getContextPath()%>/statistical/img/'+(i+1)+'@2x.png');
         }
         
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
         }else if(uname.length>=5){
            name = 'X';
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
         .span1{
            width:20px;
            float:left;
            line-height:75px;
            font-size: 20px;
            color: #ffae61;
            text-align: center;
         }
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
            width: 100%;
            height: 75px;
            margin: 0 auto;
         }

         #userImage{
            width: 90px;
            height: 75px;
            position: relative;
            float:left;
            text-align: center;
            line-height: 100px;
         }
         #userImage #x1 {
            position: absolute;
            top:5px;
            left:62px;
         }
         #userImage #x2 {
            position: absolute;
            top:5px;
            left:62px;
         }
         #userImage #x3 {
            position: absolute;
            top:5px;
            left:62px;
         }
         .xcss{
         	position: absolute;
         }
         #t1{
         	position: absolute;
         	left: 5px;
         }
         #y{
            margin-top: 5px;
         }
         .name1{
            width:50px;
            height:75px;
            float:left;
            margin-left:10px;
            line-height: 75px;
         }

      </style>
      <body> 
         <div class="container">

            <c:if test="${ranking<=20}">
    		<div id="title" class="hander" style="text-align: center;" >
    			<img src="" class="img-circle headImg" alt="User Image">
    			<img src="<%=request.getContextPath()%>/statistical/img/gx@2x.png" id="x" width="95" height="95" >
    			<h3 style="color: orange;">${student[7] }分/排名${ranking }</h3>
    		</div>
    		</c:if>
    		<c:if test="${student[7] <=60 && ranking>=20}">
    		<div id="title" class="hander" style="text-align: center;" >
    			<img src="" class="img-circle headImg" alt="User Image">
    			<img src="<%=request.getContextPath()%>/statistical/img/gx@2x.png" id="x" width="95" height="95" >
    			<h3 style="color: orange;font-size: 20px;margin-top: 25px;">未上榜,需加油哦!</h3>
    		</div>
    		</c:if>
            <HR align=center width=100% color=#987cb9 SIZE=1>
            <c:choose>
    		<c:when test="${!empty dataList}">
    			<c:forEach items="${dataList}" var="item" varStatus="status">
    			<script type="text/javascript">nameData.push("${item[2] }");</script>
            <div class="row">
              <div class="span1">
                <span >${status.count }</span>
             </div>
             <c:if test="${status.count<=3 }">
               <div id="userImage" style="text-align: left;" >
               <img id="t1" src="<%=request.getContextPath()%>/statistical/img/gx@2x.png" width="80" height="80" >
               <img id="img${status.count }" src="" class="img-circle headImg" alt="User Image" width="50" height="50" style="margin-left: 20px;margin-top: 10px">
               <img src="<%=request.getContextPath()%>/statistical/img/touming.png" class="xcss" id="x${status.count }" width="20" height="20" style="margin-left:-12px">
               
             </div>
			 </c:if>
			 <c:if test="${status.count>3 }">
               <div id="userImage" >
               <img id="img${status.count }" src="" class="img-circle headImg" alt="User Image">
               <img src="<%=request.getContextPath()%>/statistical/img/touming.png" class="xcss" id="x${status.count }" width="20" height="20" >
             </div>
			 </c:if>
             <div class="name1">
             	<c:if test="${fn:length(item[2])<=5}"><span style="font-size: 15px;">${item[2] }</span></c:if>
             	<c:if test="${fn:length(item[2])>=5}"><span style="font-size: 15px;">XXX</span></c:if>          
             </div>
            <span style="float: right;line-height: 75px;font-size: 20px;color: green;">${item[7] }分</span>     
            </div>
            <HR align=center width=100% color=#987cb9 SIZE=1>
            </c:forEach>
    		</c:when>
            <c:otherwise>           	
            	<div style="width: 100%;text-align: center;"><h3>暂无榜单</h3></div>
            </c:otherwise>
             </c:choose>   
            </div>

		

         <canvas id="headImg" style="display:none"></canvas>

      </body>
      </html>


