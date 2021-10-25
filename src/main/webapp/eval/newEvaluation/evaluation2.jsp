<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<title>英语测评</title>
<link rel="stylesheet" href="/wechat/frozenui-1.3.0/css/frozen.css">
<link rel="stylesheet" href="css/style.css">
<style type="text/css">
.header-bule {
	background-color: white;
}

.progress {
	padding: 20px 0px;
}

.submit-btn {
	top: 5px;
	width: 93%;
	margin: 0 auto;
}

.progress-text {
	color: black;
	top: 12px;
	right: 16px;
	position: absolute;
	font-size: 10px;
}

.question{
	height: 50px;
    padding: 27px 0px 0px 26px;
}
.question-titile{
	vertical-align:middle;
}
.question-titile img{
	float: left;
	margin-right: 10px;
}
.audio-ico{
	float: left;
	margin-right: 15px;
	margin-left: 5px;
}
.question-text{
}


.answer {
	padding: 10px;
}

.answer-option {
	display: -webkit-inline-box;
    margin-bottom: 25px;
    margin-top: 10px;
}

.answer-option-index {
	line-height: 100px;
	margin-right: 10px;
	margin-left: 10px;
}
.answer-option-content img{
	width: 130px;
	height: 130px;
}
.answer-option-content img:HOVER {
	border: 1px solid blue;
}
</style>
</head>
<body>
	<header class="ui-header ui-header-positive ui-border-b header-bule">
		<div class="progress">
			<div class="ui-progress">
				<span style="width: 50%">1/10</span>
			</div>
		</div>
		<div class="progress-text">
			<span>1/10</span>
		</div>
	</header>
	<footer class="ui-footer ui-footer-btn ui-border-t">
		<button class="ui-btn-lg submit-btn	">确定</button>
	</footer>
	<section class="ui-container">
		<div class="question">
			<div class="question-title">
					<img class="audio-ico" alt="" src="../images/laba2x.png" width="20px" height="20px">
				<div class="question-text">
					<span>听：panda</span>
				</div>
			</div>
		</div>

		<div class="answer">
		
			<div class="answer-option">
				<div class="answer-option-index">
					<span>A.</span>
				</div>
				<div class="answer-option-content">
					<img alt="" src="http://oupqad0ge.bkt.clouddn.com/image22.jpeg" width="100px" height="100px">
				</div>
			</div>
			
			<div class="answer-option">
				<div class="answer-option-index">
					<span>B.</span>
				</div>
				<div class="answer-option-content">
					<img alt="" src="http://oupqad0ge.bkt.clouddn.com/image22.jpeg" width="100px" height="100px">
				</div>
			</div>
			<div class="answer-option">
				<div class="answer-option-index">
					<span>C.</span>
				</div>
				<div class="answer-option-content">
					<img alt="" src="http://oupqad0ge.bkt.clouddn.com/image22.jpeg" width="100px" height="100px">
				</div>
			</div>
			
			<div class="answer-option">
				<div class="answer-option-index">
					<span>D.</span>
				</div>
				<div class="answer-option-content">
					<img alt="" src="http://oupqad0ge.bkt.clouddn.com/image22.jpeg" width="100px" height="100px">
				</div>
			</div>
		</div>
	</section>
	<script src="/wechat/frozenui-1.3.0/lib/zepto.min.js"></script>
	<script src="/wechat/frozenui-1.3.0/js/frozen.js"></script>
	<script>
		var data;
		
		$(function(){
			getEvaluation()
		});
		
		function getEvaluation(){
			$.post("<%=request.getContextPath()%>/h5/evaluation/getEvaluation",
					function(success){ 
						if(success.code==200){
							data = success.data;
							showData();
						}
		    		},"json");
		}
		
		function showData(){
			
			for(var i=0;i<data.length;i++){
			}
		}
		
	</script>
</body>
</html>