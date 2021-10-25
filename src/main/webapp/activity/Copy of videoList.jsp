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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/bootstrap3/css/bootstrap.min.css">
    <script src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/activity/css/bootstrap3/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/videolist.css">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <script type="text/javascript">
    	var videoData;
        $(function(){
        
        	var $content = $('.content');
        	var content = document.getElementsByName("content");
        	
        	

            $("video").click(function(){
                this.play();
                var img = this.previousSibling.previousSibling;
                img.style.display="none";
                this.onended = function() {img.style.display="";};
            });
            
            getAllVideo();
            
            if(videoData.length!=0){
            	for(var i=0;i<videoData.length;i++){
            	if(i!=0)
            		$('.container-fluid').append($content.clone(true));
            		$content.find('video').attr('src',videoData[i][6]);
            		$content.find('#name').html('郑铭强');
            		$content.find('#num').html('参赛编号:'+videoData[i][1]);
            		$content.find('#vote').html(videoData[i][13]);
            		$content.find('button').attr('id',videoData[i][1]);
            	}
            }
            
            



        });



        function addVote(that){
        	var num = that.id;
        	var memberId = "<%=request.getParameter("memberId")%>";
        	var date = new Date();
        	var dateNum = (date.getMonth()+1)+""+date.getDate();
            $.ajax({
                type: "POST",
                url:"<%=request.getContextPath()%>/api/addVote?vid="+num+"&memberId="+memberId+"&date="+dateNum,
                async: false,
                error: function(request) {
                    alert("错误！！");
                },
                success: function(result) {
                    if(result.status==1){
                    	alert("投票成功");
                    	alert(that.parentNode);
                    }if(result.status==0){
                    	alert("你今天已经投过票啦");
                    }
                }
            });
        }
        
        function getAllVideo(){
	            $.ajax({
	                type: "POST",
	                url:"<%=request.getContextPath()%>/api/getAllVideoCompetitionByVerifSuccess",
	                async: false,
	                error: function(request) {
	                    alert("错误！！");
	                },
	                success: function(result) {
	                    if(result.data!=null){
	                        console.log(result);
	                        videoData = result.data;
	                    }
	                }
	            });

        }


    </script>
</head>

<body>
<div class="container-fluid" >
    <div style="width: 100%;text-align: center;"><h3>投票视频列表</h3></div>

    <div id="index">

    </div>


    <div class="content">
    <div class="videoContent">
        <div class="video">
            <img src="<%=request.getContextPath() %>/activity/images/1.png"/>
            <video src=""></video>
        </div>

        <div class="videoText">
            <div>
                <span id="name" >郑铭强</span>
            </div>
            <div>
                <span id="num">参赛编号:201</span>
            </div>
        </div>

        <div class="videoText2">
            <div>
                <span id="vote">1</span>
            </div>
            <div>
                <button onclick="addVote(this)">投票</button>
            </div>
        </div>

    </div>

    </div>

    
    


    </div>











</body>
</html>
