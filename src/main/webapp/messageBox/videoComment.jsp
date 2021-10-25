<%@ page language="java" import="java.util.*,com.wechat.entity.VideoAcitivity" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>教师信箱</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="<%=request.getContextPath() %>/js/jquery-2.1.1.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap337.min.js"></script>
	<link href="<%=request.getContextPath() %>/messageBox/css/videoComment.css" rel="stylesheet">
</head>
<script type="text/javascript">
	var ajaxPack= {	method:function(url,data,method,success){
	    $.ajax({
	        type: method,
	        url: url,
	        data: data,
	        timeout: 30000,
	        async: false,
	        error: function (data) {
	            console.log(data);
	            alert("请求失败");
	        },
	        success: function (data) {
	            console.log(data);
	            success?success(data):function(){
	            };
	        }
		});    
	}
	}
	function saveComment(){
		var status=0;
		var fayin = $("input[name='fayin']:checked").val(); 
		var biaoyan = $("input[name='biaoyan']:checked").val(); 
		var liuchang = $("input[name='liuchang']:checked").val(); 
		var textPL = $("#pinglun").val();
		var teacherid = $("#teacherid").val();
		var messageid = $("#messageid").val();
		
		var vid="${video[0]}";
		
		if(fayin==null||fayin==""||fayin==undefined){
			$("#pingjia").html("请勾选发音项");
			$(".dialog").fadeIn();
    		setTimeout(function(){$(".dialog").fadeOut()},1000);
			return;
		}
		if(biaoyan==null||biaoyan==""||biaoyan==undefined){
			$("#pingjia").html("请勾选表演项");
			$(".dialog").fadeIn();
    		setTimeout(function(){$(".dialog").fadeOut()},1000);
			return;
		}
		if(liuchang==null||liuchang==""||liuchang==undefined){
			$("#pingjia").html("请勾选流畅项");
			$(".dialog").fadeIn();
    		setTimeout(function(){$(".dialog").fadeOut()},1000);
			return;
		}
		var pinglun = '发音:'+fayin+'分;表演:'+biaoyan+'分;流畅度'+liuchang+'分';
		if(textPL!=null&&textPL!=""&&textPL!=undefined){
			pinglun+="<p>"+textPL+"</p>";
		}
		ajaxPack.method("../api/saveTeacherComment",{ vid: vid, pinglun: pinglun,teacherid:teacherid,messageid:messageid},"post",function (data) {
        	if(data.code==200){
        		status=1;
        	}
        });
		
		if(status==1){
			$("#btnn").attr("disabled", true);
			$("#pingjia").html("评价成功");
			$(".dialog").fadeIn();
    		setTimeout(function(){$(".dialog").fadeOut()},1000);
    		status=0;
    		setTimeout(function(){window.location.href="<%=request.getContextPath() %>/api/showComment?vid="+vid+"&teacherid="+teacherid;},2000);
		}
		
		
		
	}
</script>
<body>
	<div class="container-fluidcontainer-fluid">
  		<div class="content">
  			<div class="title">
  				<span>${gradeName }</span>
  				<span>${video[24] }</span>
  			</div>
  			<div class="videoPanle" >
		        <video id="media" controls="controls" webkit-playsinline="true" playsinline="true" x5-video-orientation="h5" style="z-index: 1">
		            <source id="video1" src="${video[2] }">
		        </video>
    		</div>
    		<HR align=center width=90% color=#987cb9 SIZE=1>
    		<div class="comment">
    			<div>
	    			<p>
	    			发音：
	    			<input name="fayin" type="radio" value="1" />1分
	    			<input name="fayin" type="radio" value="2" />2分
	    			<input name="fayin" type="radio" value="3" />3分
	    			<input name="fayin" type="radio" value="4" />4分
	    			<input name="fayin" type="radio" value="5" />5分
	    			</p>
	    			<p>
	    			表演：
	    			<input  name="biaoyan" type="radio" value="1">1分
	    			<input  name="biaoyan" type="radio" value="2">2分
	    			<input  name="biaoyan" type="radio" value="3">3分
	    			<input  name="biaoyan" type="radio" value="4">4分
	    			<input  name="biaoyan" type="radio" value="5">5分
	    			</p>
	    			<p>
	    			流畅度：
	    			<input  name="liuchang" type="radio" value="1">1分
	    			<input  name="liuchang" type="radio" value="2">2分
	    			<input  name="liuchang" type="radio" value="3">3分
	    			<input  name="liuchang" type="radio" value="4">4分
	    			<input  name="liuchang" type="radio" value="5">5分
	    			</p>
    			</div>
    			<div class="textComment">
    				<textarea rows="3" cols="20" id="pinglun" style="width: 100%;height: 110px;"></textarea>
    			</div>
    		</div>
  		</div>
  		<input id="teacherid" type="hidden" value="${teacherId }">
  		<input id="messageid" type="hidden" value="${messageId }">
  		<div class="dialog">
  			<div class="dialog-content"><span id="pingjia">评价成功</span></div>
  		</div>
  		<div class="bottom">
  			<button id="btnn" class="btn btn-warning" onclick="saveComment()">提交评论</button>
  			<a href="<%=request.getContextPath() %>/api/showComment?vid=${video[0] }&teacherid=${teacherId }" class="btn btn-warning">查看所有评价</a>
  		</div>
  		<div style="height: 10px;"></div>
	</div>
</body>
</html>