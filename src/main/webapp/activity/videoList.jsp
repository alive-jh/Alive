<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>英语新势力&蛋蛋机器人英语秀【襄汾站】</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/video/style2.css?id=5659548">
    <script src="<%=request.getContextPath() %>/dialog/script/dialog.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/dialog/dialog.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
	<link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">
    <script type="text/javascript">
    	var pageNumber=1;
    	
    	var flag = true;
    	$(function(){
    		$('#showvotedescinfo').click(function(){
    			$('#votedescinfo').toggle();
    		});
    		
    		$('#hidevotedescinfo').click(function(){
    			$('#votedescinfo').css('display','none');
    		});
    		
    		$("#s").textScroll();
    		
    		videoList();
    		updataStatisticalInfo();
    		var t1 = window.setInterval(updataStatisticalInfo,5000);
    		
    	});
    	function play(that){
    		var vid=that.id;
           	window.location.href = "<%=request.getContextPath()%>/api/getAllVideoCompetitionByVerifSuccess2?vid="+vid;
    	}
    	function vote(that,vt){
    		
    		var vid = that.id;
    		var message = '';
    		var type = 'ok';
    		var code = 0;
    		$.ajax({
                type: "POST",
                url:'/wechat/v2/video/vote?_time'+new Date().getTime(),
                data:{memberId:memberId,vId:vid},
                async: false,
                beforeSend: function(){
                	$.showLoading();
                },
                complete: function(){
                	$.hideLoading();
                },
                error: function(request) {
                    alert("错误！！");
                    return;
                },
                success: function(success) {
                	console.log(success);
                	if(success.code==200){
        				console.log(success);
        				message = success.data.count>0?"投票成功,您今天还可以为"+success.data.count+"位小朋友投票":"投票成功，谢谢你为小朋友们投票";
        			}else{
        				type = 'warning';
        				message = success.msg;
        				code = success.code;
        			}
                }
            });
    		
    		var d=$.dialog(
    				{
    							delay:2500,
    							//弹框类型
    							type:type,
    							//描述文字
    							message:message,
    							//尺寸
    							width:200,
    							//对话框遮罩层透明度
    							maskOpacity:0.2,
    							animate:true
    				});
    		
    		
			setTimeout(function(){
				if(code==20504){
	    			location.href="/wechat/api/voteRecord";
	    		}
			},2500);
    		
    		
            
    	}
    	$.fn.textScroll=function(){

            var speed=60,flag=null,tt,that=$(this),child=that.children();

            var p_w=that.width(), w=child.width();

            child.css({left:p_w});

            var t=(w+p_w)/speed * 1000;

            function play(m){

                var tm= m==undefined ? t : m;

                child.animate({left:-w},tm,"linear",function(){

                    $(this).css("left",p_w);

                    play();

                });

            }

            child.on({

                mouseenter:function(){

                    var l=$(this).position().left;

                    $(this).stop();

                    tt=(-(-w-l)/speed)*1000;

                },

                mouseleave:function(){

                    play(tt);

                    tt=undefined;

                }

            });

            play();

        }
        function addVideo(data){
        	
        	var _videoList = '';
        	
        	$.each(data, function (n, v) {
	   			 _videoList += `<div class="content">
						                <div class="content2">
						                    <div class="num">
						                        <div>
						                            <span>#</span>
						                        </div>
						                    </div>
						                    <div class="img" id="`+v[0]+`" onclick="play(this)">
						                        <img class="img1" src="/wechat/activity/img/001.png">
						                        <img class="img2" src="`+v[3]+`">
						                    </div>
						                    <div class="text" id="`+v[0]+`" onclick="play(this)">
						                        <div><span id="xingming">姓名:`+v[5]+`</span></div>
						                        <div><span id="bianhao">参赛编号:`+v[0]+`</span></div>
						                        <div><span id="score">作品得分:`+v[1]+`</span></div>
						                        <div><span id="vt1" class="piaoshu">当前票数:`+v[2]+`</span></div>
						                        <span class="`+v[2]+`" id="toupiaotemp" style="display:none">`+v[2]+`</span>
						                    </div>
						                    <div class="vote">
						                        <img id="`+v[0]+`" class="voteclick" src="/wechat/activity/img/vote.png" onclick="vote(this,'vt1')">
						                    </div>
						                </div>
			           				 </div>`;
	        	});        	
        	
        	$('.videos').append(_videoList);
          }
          
        function getScrollTop() { 
		    var scrollTop = 0; 
		    if (document.documentElement && document.documentElement.scrollTop) { 
		    scrollTop = document.documentElement.scrollTop; 
		    } 
		    else if (document.body) { 
		    scrollTop = document.body.scrollTop; 
		    } 
		    return scrollTop; 
    	} 

    //获取当前可视范围的高度 
    	function getClientHeight() { 
		    var clientHeight = 0; 
		    if (document.body.clientHeight && document.documentElement.clientHeight) { 
		    clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight); 
		    } 
		    else { 
		    clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight); 
		    } 
		    return clientHeight; 
    	} 

    //获取文档完整的高度 
    	function getScrollHeight() { 
    		return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight); 
    	} 
        window.onscroll = function () { 
        	if (getScrollTop() + getClientHeight() == getScrollHeight()) { 
        		if(flag){
        			pageNumber++;
        			videoList();
            }
        }
}
        
    function videoList(){
    	$.ajax({
            type: "GET",
            url:'/wechat/api/getAllVideoCompetitionByVerifSuccessWithAjax?_time='+new Date().getTime(),
            data:{pageNumber:pageNumber,pageSize:10,activtId:8},
            async: false,
            beforeSend: function(){
            	$('#loading').show();
            },
            complete: function(){
            	$('#loading').hide();
            },
            error: function(request) {
                alert("错误！！");
                return;
            },
            success: function(result) {
            	console.log(result);
            	if(result.data.length>0){
                	addVideo(result.data);
                }else{
                	$('#nomore').show();
                }
            }
        });
    }
        
	function updataStatisticalInfo(){
		$.ajax({
                type: "POST",
                url:"<%=request.getContextPath()%>/api/updataStatisticalInfo",
                async: false,
                error: function(request) {
                },
                success: function(result) {
                	if(result!=null){
                        $('#wxallstunum').html(result.studentNum);
                		$('#wxallvotenum').html(result.voteNum);
                		$('#wxallviewnum').html(result.accessNUm);
                    }
                	
                }
         });
	}
	
	function search(){
		var mingzi = $('#serach').val();
		if(mingzi==""||mingzi==null){
			alert("请先输入查询条件");
			location.reload();
			return;
		}
		
		
		$.ajax({
            type: "GET",
            url:'/wechat/api/getAllVideoCompetitionByVerifSuccessWithAjax?_time='+new Date().getTime(),
            data:{pageNumber:1,pageSize:10,activtId:8,search:mingzi},
            async: true,
            error: function(request) {
                alert("错误！！");
                return;
            },
            success: function(result) {
            	console.log(result);
            	$('.videos').empty();
            	$('.footer1').hide();
            	if(result.data.length>0){
                	addVideo(result.data);
                }else{
                	alert('暂无参赛信息');
                }
            }
        });
		
	}
	
    </script>
</head>
<body>
<div class="container-fluidcontainer-fluid">
	<div id="s" style="width:100%; position:relative; white-space:nowrap; overflow:hidden; height:20px;background-color: #2e3238">
    <div id="noticeList" style="position:absolute; top:10; height:10px;color:white;font-size: 14px">
    <span>感谢山西省临汾市襄汾县委县政府、教育局</span>
    <span>、襄汾一小、二小、三小，</span>
    <span>及广大家长和同学们对本次活动的大力支持与配合!</span>
    </div>
	</div>
    <div class="header_img">
        <img src='<%=request.getContextPath() %>/activity/img/header.jpg' style='width:100%'/>
    </div>

    <div class="activity_info">
        <div class="title">
            <h5>英语新势力&蛋蛋机器人英语秀【襄汾站】</h5>
        </div>

        <div class="vote_info1">
            <ul class='inline1'>
                <li>参与学生<BR><span id='wxallstunum'>${stuNum}</span></li>
                <li class="li-split">累计投票<BR><span id='wxallvotenum'>${voteNum }</span></li>
                <li>访问次数<BR><span id='wxallviewnum'>${acessNum }</span></li>
            </ul>
        </div>

        <div class='blockcell'>
            <i class='glyphicon glyphicon-time'></i> 投票开始时间：2018-05-14
        </div>
        <div class='blockcell'>
            <i class='glyphicon glyphicon-time'></i> 投票截止时间：2018-05-20
        </div>
        <div class='blockcell'>
            <i class='glyphicon glyphicon-warning-sign'></i> 投票规则：每人每天限投1次，每次可以投10票。
        </div>


        <div class='blockcell rightarrow' id='showvotedescinfo'>
            <i class='icon-bar-chart'></i> 活动介绍
            <i class='glyphicon glyphicon-collapse-down '></i>
        </div>
        <div class='votedescinfo' id="votedescinfo" style="display: none">
            <p style="margin:0px;padding:0px;clear:both;color:#3e3e3e;
            font-family:'helvetica neue', helvetica, 'hiragino sans gb', 'microsoft yahei', arial, sans-serif;font-size:16px;font-style:normal;
            font-weight:normal;letter-spacing:normal;text-indent:0px;text-transform:none;word-spacing:0px;background-color:#ffffff;">由亲友团在网上对作品投票评人气分</p><div>人气分总分20分，专业分由多名专家老师根据孩子的语音语调、感染力打分。</div><div>“专业分”+“人气分”的总和为选手的最终得分，根据最终得分评选优胜选手。</div><p><strong style="margin:0px;padding:0px;"><span style="margin:0px;padding:0px;color:#ff4c41;">本次比赛将评选全国十佳和当地优胜，有神秘大奖等着你！</span></strong></p><p><span style="margin:0px;padding:0px;color:#000000;">感谢山东省临沂市兰陵县教育局，山西省临汾市中国人寿、太平洋保险，安徽省安庆市怀宁县独秀小学、振宁学校，以及广大的家长和小朋友们对本次活动的大力支持与配合！</span></p><p><br /><i id="hidevotedescinfo" style="color: blue">收起</i></p>
        </div>

    </div>
    <div style="width: 100%;text-align: right;padding: 10px 10px 10px 10px">
    	<input type="text" id="serach" placeholder="请输入姓名或参赛编号" style="text-align: center;width: 80%"><button onclick="search()" class="weui-btn weui-btn_mini weui-btn_default" style="width: 20%">搜索</button>
    </div>
    <div class="videos">
		
	</div>	
	<div id="loading" class="weui-loadmore" style="display: none">
	  <i class="weui-loading"></i>
	  <span class="weui-loadmore__tips">正在加载</span>
	</div>
	<div id="nomore" class="weui-loadmore nomore" style="display: none">
	  <span class="weui-loadmore__tips">已无更多</span>
	</div>
<%-- 	<div class="footer1" style="width: 100%;text-align: center;height: 20px"><img id="lodingImg" src="<%=request.getContextPath() %>/activity/img/loding.gif" width="20px" height="20px">&nbsp;&nbsp;&nbsp;<span id="btnn" style="font-size: 13px;line-height: 20px">加载更多</span></div>
	 --%>
</div>
<!-- <div class="onekey_vote">
		<button class="btn btn-warning" onclick="onkeyVote()">一键投票</button>
</div> -->
</body>
<script src="https://cdn.bootcss.com/jquery-weui/1.2.0/js/jquery-weui.min.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="/wechat/js/wechat/wechat-share.js"></script>
<script type="text/javascript">
var config = {
		link : "http://wechat.fandoutech.com.cn/wechat/api/videoLoginIndex",
		title : "英语新势力&蛋蛋机器人英语秀",
		imgUrl : "http://test.fandoutech.com.cn/wechat/activity/img/header.jpg",
		desc : '我参加了由凡豆教育举办的<<英语新势力&蛋蛋机器人英语秀>>大赛,快来帮我投上你宝贵的一票吧',
		debug : false
	}
	
$.wechatShare(config);
</script>

</html>