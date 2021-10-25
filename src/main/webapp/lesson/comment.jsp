<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
<script>
//初始化
$(document).ready( function () {
	var result=$("#myInitData").text();
	jsonData=$.parseJSON(result);
	updateUI();
	
});
function updateUI(){
	$("#head").empty();
	var head = '<h1>'+jsonData.title+'</h1>';
	$("#head").append(head);
}
</script>
<style>
 ul {
  padding-left: 0;
  overflow: hidden;
 }
 .light{
  float: left;
  list-style: none;
  width: 60px;
  height: 60px;
  background: url(img/star.png) no-repeat;
background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;
 }
 .heavy{
   float: left;
  list-style: none;
  width: 60px;
  height: 60px;
  background: url(img/star2-2.png) no-repeat;
	background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;
 
 }
 ul li a {
  display: block;
  width: 100%;
  padding-top: 27px;
  overflow: hidden;
 }
 ul li.light {

 }
 .sound-item{
 	font-size:40px;
 	margin-top:20px;
 	text-decoration:underline;
 }
 .content{
 	background: url(img/background.jpg) no-repeat;
 	filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
   -moz-background-size:100% 100%;
   background-size:100% 100%;
 }
 .head{
 	margin-left:0px;font-size:42px;text-align:center;
 }
 .comment_score{
 	font-size:50px;height:60px;
 }
 </style>
<div id="myInitData" style="display: none;" >${jsonData }</div>
 <div class="content">
	<div class="head" id=head>
		<h1>6月总结</h1>
	</div>
	<div class="comment_score">
		<div style="display:inline;line-height:60px">
			<span style="float: left;">能力评估：</span>
			<ul style="display:inline;height:60px">
			  <li class="light"><a href="javascript:;"></a></li>
			  <li class="light"><a href="javascript:;"></a></li>
			  <li class="light"><a href="javascript:;"></a></li>
			  <li class="light"><a href="javascript:;"></a></li>
			  <li class="heavy"><a href="javascript:;"></a></li>
			 </ul>
		</div>
		<div style="display:inline;float: right;margin-left:20px">
			<span>综合评分：</span> <span style="font-style:italic;font-size:55px">89分</span>
		</div>
	</div>
	<div class="sound_comment" style="font-size:50px;margin-top:40px;background:#EAEAEA;">
		<span>音频评论：</span><a href="${soundUrl}"><img src="img/play.png" width="140px" height="140px" border="" style="vertical-align:middle;"></a>
	</div>
	
	<div class="text_comment" style="font-size:50px;margin-top:40px;background:#EAEAEA;">
		<div>
			<span>老师评价：</span>
		</div>
		<div style="font-size:40px;margin-top:10px;color:#8E8E8E;font-style:italic;">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这个学生非常好，继续努力！加油！加油！加油！加油！加油！加油！加油！加油！加油！加油！加油！加油！
		</div>
	</div>
	
	
	<div class="sound_list" style="font-size:50px;margin-top:40px;background:#EAEAEA;">
		<div>
			<span>优质学生音频：</span>
		</div>
		<div class="sound-item">
			<span>丑小鸭 part 1：</span><a href="http://ocghbl1t3.bkt.clouddn.com/00107922cc30d4018187c44d6594ea97.mp3"><img src="img/play.png" width="140px" height="140px" border="" style="vertical-align:middle;"></a>
		</div>
		
		<div class="sound-item">
			<span>丑小鸭 part 2：</span><a href="http://ocghbl1t3.bkt.clouddn.com/00107922cc30d4018187c44d6594ea97.mp3"><img src="img/play.png" width="140px" height="140px" border="" style="vertical-align:middle;"></a>
		</div>
	</div>

</div>