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
    <title>${activityName }</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/video/style.css">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/activity/css/jquery.alertable.css">
    <script src='<%=request.getContextPath() %>/activity/js/velocity.min.js'></script>
	<script src='<%=request.getContextPath() %>/activity/js/velocity.ui.min.js'></script>
	<script src="<%=request.getContextPath() %>/activity/js/jquery.alertable.js"></script>
</head>
<script type="text/javascript">
	var vid="${vid}";
	var flag=false;
	function join(that){
		$.alertable.alert('比赛已经结束');
		return;
		
		if($('#ck1').is(':checked')){
			var activityId=that.getAttribute('name');
			check(activityId);
			if(flag){
			$.ajax({
					type: "POST",
					url:"<%=request.getContextPath()%>/api/saveVideoCompetition",
					data:{vid:vid,activityId:activityId},
					async: false,
					error: function(request) {
						$.alertable.alert('错误');
					},
					success: function(result) {
						if(result.success==1){
							$.alertable.alert('参赛成功,请点击左上角返回');
						}else{
							$.alertable.alert('参赛失败，请点击左上角返回');
						}
						flag=false;
					}
				});
				}
			}else{
				$.alertable.alert('请先同意参赛规则');
			}
		}
	
	function check(activityId){
		$.ajax({
				type: "POST",
				url:"<%=request.getContextPath()%>/api/verificationVideo",
				data:{vid:vid,activityId:activityId},
				async: false,
				cache:false,
				error: function(request) {
					$.alertable.alert('错误');
				},
				success: function(result) {
					console.log(result);
					if(result.success==1){
						$.alertable.alert('您已经参加过啦，请点击左上角返回');
						flag = false;
					}
					if(result.success==0){
						flag = true;
					}
					if(result.success==-1){
						$.alertable.alert('错误');
						flag = false;
					}
				}
			});
			
			return;
	}
		
</script>
<body>
    <div class="container-fluidcontainer-fluid">
		
        <div class="content">
        	<img src="<%=request.getContextPath() %>/activity/img/activity1.png" width="100%">
        </div>
        
        <footer class="footer">
        	<img name="${activityId }" src="<%=request.getContextPath() %>/activity/img/btn1.png" style="width: 150px" onclick="join(this)">
        	<div>
        	<span>
        	<i><input type="checkbox" id="ck1" name="tongyi" style="margin-top: 10px;" checked="checked"></i>
        	<input type='button' value='已同意参赛规则' style="text-decoration:underline;color:white;padding:0 0 2px 0; madgin:0px;" class='btn btn-link' data-toggle='modal' data-target='#myModal'/>
        	</span></div>
        	
        	
        </footer>
        
        
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" >
	        <div class="modal-content" style="margin-top:10px;padding: 15px 15px 15px 15px">
	        	<div style="width:100%;text-align: center;">
	        		<h4>欢迎你参与本次英语小霸王线上争霸赛</h4>
	        	</div>
	           <p>本次比赛采用网络的形式进行，你上传的视频将作为评奖的唯一依据，请选择你最得意的作品上传。</p>
	           <p>1、评分规则:</p>
	           <p>评奖包含两个维度，总分120分 专业分：总分100分，多名专家打分的平均分，语音语调60分，感染力和表现力40分</p>
	           <p>人气分：总分20分，根据网上投票数排名给分 公平竞争，我承诺不刷票，一旦主办方查到有刷票嫌疑，可以将人气分扣减 </p>
	           <p>2、比赛时间：</p>
	           <p>10月1日——10月7日，选择视频参赛 10月9日——10月15日，分享自己的链接，邀请朋友投票 </p>
	           <p>3、视频要求：</p>
	           <p>开始讲英语之前，先介绍自己和蛋蛋：“我叫XXX，今年X岁，我和蛋蛋机器人一起学英语2个月，下面我为大家表演XXX”</p>
	           <p>录制的内容：角色扮演、独立唱儿歌、独立讲故事（三选一） 有机器人入境 时长：2-3分钟</p>
	           <div style="width:70px; margin: 0 auto;">
	           		<input type='button' value='我知道了' style="color:padding:0 0 2px 0; madgin:0px;" class='btn btn-link' data-toggle='modal' data-target='#myModal'/>
	           </div>
	           	
	         </div>
	    </div>
    </div>
</body>
</html>