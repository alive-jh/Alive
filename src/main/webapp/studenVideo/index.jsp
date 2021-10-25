<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>凡豆-学员风采</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/studenVideo/css/index.css">
    
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/studenVideo/css/component.css" />
	<script src="<%=request.getContextPath() %>/studenVideo/js/modernizr.custom.js"></script>
	<script src="<%=request.getContextPath() %>/studenVideo/js/classie.js"></script>
	<script type="text/javascript" src="http://hammerjs.github.io/dist/hammer.min.js "></script>
</head>
<script type="text/javascript">

$(function(){
	
	var hammer1 = new Hammer(document.body);
	hammer1.on('swiperight', function(ev) {
		classie.toggle( body, 'cbp-spmenu-push-toleft' );
		classie.toggle( menuRight, 'cbp-spmenu-open' );
		disableOther( 'showRightPush' );
	});
	var hammer2 = new Hammer(document.getElementById("cbp-spmenu-s2"));
	hammer2.get('swipe').set({ direction: Hammer.DIRECTION_VERTICAL });
	hammer2.on('swipeup', function(ev) {});
	

	
});
var page = 1;
function play(that){
	/* var vid = that.id; */
	var _this = $(that);
	var sid = _this.find('#sid').val();
	location.href="<%=request.getContextPath()%>/h5/userVideo/getStudentVideoByStudentId?studentId="+sid;
}

function nextPage(){
	
	var type = $("#type").val();
	var agentId=$("#agentId").val();
	var period = $('#period').val();
	
	page++;
	$.ajax({
        type: "POST",
        url:"<%=request.getContextPath()%>/h5/userVideo/getShowStudentsByAJAX",
        async: true,
        data:{page:page,type:type,agentId:agentId,period:period},
        beforeSend: function(){
        },
        complete: function(){
            // Handle the complete event
        },
        error: function(request) {
        },
        success: function(result) {
        	console.log(result);
        	if(result.isLastPage){
        		$('#btnn').html('已无更多');
        		$('#btnn').removeAttr('onclick');
        	}
        	if(result.code==200&&result.data.length!=0){
        		for(var i=0;i<result.data.length;i++){
            		var content1 = $('.study-video-image')[0];
        			var $content = $(content1.cloneNode(true));
            		$('.data-list').append($content);
            		$content.find('#sid').val(result.data[i].student_id);
            		$content.find('img').attr('src',result.data[i].pic_url);
            		$content.find('#videoText').html(result.data[i].name+":"+result.data[i].class_grades_name);
            		}
        	}
        		
        	
        }
 });
}
</script>
<body class="cbp-spmenu-push">
	<nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right" id="cbp-spmenu-s2">
			<h3>分类</h3>
			<a href="<%=request.getContextPath()%>/h5/userVideo/getShowStudents">首页</a>
			<a href="<%=request.getContextPath()%>/studenVideo/studyTimeType.jsp">学龄分类</a>
			<a href="<%=request.getContextPath()%>/studenVideo/area.jsp">地区分类</a>
	</nav>
<div class="container-fluidcontainer-fluid">
    <div class="title-bar">
    <c:if test="${type eq 'all' }"><h4>凡豆-学员风采</h4></c:if>
    <c:if test="${type eq 'learTime' }"><h4>${title }</h4></c:if>
    <c:if test="${type eq 'area' }"><h4>${title }</h4></c:if>
    
    <img id="showRightPush" alt="菜单" src="http://oqcfa7n69.bkt.clouddn.com/44598f898e0d4c947f95ce34542fbc53.png" width="25px" height="20px" >
 	</div>
	
	    <div class="fandou-ad">
        <div class="img-ad">
            <img src="<%=request.getContextPath() %>/studenVideo/img/20171130145621.jpg" width="100%" height="150px">
        </div>
    </div>
    <HR align=center width=100% color=#987cb9 SIZE=1>

    <div class="data-list">
        <div class="data-title">
           <p>凡豆明星学员</p>
        </div>
       
       <input id = "type" type="hidden" value="${type }">
       <input id = "agentId" type="hidden" value="${agentId }">
       <input id = "period" type="hidden" value="${period }">
       <c:if test="${msg!=null }"><h5 style="margin: 0 auto;margin-top: 50px">${msg }</h5></c:if>
       <c:if test="${dataWithAll!=null }">
	        <c:forEach items="${dataWithAll}" var="item" varStatus="status"> 
	        
	        <div id="${item.getStr('student_id') }" class="study-video-image" onclick="play(this)">
	        	<input type="hidden" value="${item.getStr('student_id') }" id="sid">
	            <div class="video-image">
	                <img src="${item.getStr('pic_url') }">
	            </div>
	            <div class="video-text">
	                <span id="videoText">${item.getStr('name') } ：${item.getStr('class_grades_name') }</span>
	            </div>
	        </div>
	        
	        </c:forEach>
       </c:if>
       
       <c:if test="${dataWithArea!=null }">
	        <c:forEach items="${dataWithArea}" var="item" varStatus="status"> 
	        
	        <div id="${item.getStr('student_id') }" class="study-video-image" onclick="play(this)">
	        	<input type="hidden" value="${item.getStr('student_id') }" id="sid">
	            <div class="video-image">
	                <img src="${item.getStr('pic_url') }">
	            </div>
	            <div class="video-text">
	                <span id="videoText">${item.getStr('name') } ：${item.getStr('class_grades_name') }</span>
	            </div>
	        </div>
	        
	        </c:forEach>
       </c:if>
       
       <c:if test="${dataWithLearnTime!=null }">
	        <c:forEach items="${dataWithLearnTime}" var="item" varStatus="status"> 
	        
	        <div id="${item.getStr('student_id') }" class="study-video-image" onclick="play(this)">
	        	<input type="hidden" value="${item.getStr('student_id') }" id="sid">
	            <div class="video-image">
	                <img src="${item.getStr('pic_url') }">
	            </div>
	            <div class="video-text">
	                <span id="videoText">${item.getStr('name') } ：${item.getStr('class_grades_name') }</span>
	            </div>
	        </div>
	        
	        </c:forEach>
       </c:if>
    </div>

</div>
	<div class="footer1" style="width: 100%;text-align: center;height: 20px">&nbsp;&nbsp;&nbsp;<span id="btnn" style="font-size: 13px;line-height: 20px;clear: both;" onclick="nextPage()">下一页</span></div>	
		
		
		
		<script>
			var menuRight = document.getElementById( 'cbp-spmenu-s2' ),
				showRightPush = document.getElementById( 'showRightPush' ),
				body = document.body;

			showRightPush.onclick = function() {
				classie.toggle( body, 'cbp-spmenu-push-toleft' );
				classie.toggle( menuRight, 'cbp-spmenu-open' );
				disableOther( 'showRightPush' );
			};

			function disableOther( button ) {
				if( button !== 'showRightPush' ) {
					classie.toggle( showRightPush, 'disabled' );
				}
			}
		</script>

</body>
</html>