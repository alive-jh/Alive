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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/studenVideo/css/videoList.css">
    
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
</script>
<body class="cbp-spmenu-push">
	<nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right" id="cbp-spmenu-s2">
			<h3>分类</h3>
			<a href="<%=request.getContextPath()%>/studenVideo/studyTimeType.jsp">学龄分类</a>
			<a href="<%=request.getContextPath()%>/studenVideo/area.jsp">地区分类</a>
			<a href="#">Zucchini garlic</a>
			<a href="#">Catsear azuki bean</a>
			<a href="#">Dandelion bunya</a>
			<a href="#">Rutabaga</a>
	</nav>
<div class="container-fluidcontainer-fluid">
    <div class="title-bar">
    <h4>视频列表</h4>
    <img id="showRightPush" alt="菜单" src="http://oqcfa7n69.bkt.clouddn.com/44598f898e0d4c947f95ce34542fbc53.png" width="25px" height="20px" >
 	</div>
	
	    
    <HR align=center width=100% color=#987cb9 SIZE=1>

    

</div>	
		
		
		
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