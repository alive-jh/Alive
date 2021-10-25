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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/video/style2.css">
    
    <script type="text/javascript">
    	var memberId="${memberId}";
    	var page=0;
    	var count=10;
    	var flag = true;
    	$(function(){
    		$('#showvotedescinfo').click(function(){
    			$('#votedescinfo').css('display','');
    		});
    		
    		$('#hidevotedescinfo').click(function(){
    			$('#votedescinfo').css('display','none');
    		});
    		
    		$("#s").textScroll();
    		
    		$('#serachbtn').click(function(){
    			var mingzi = $('#serach').val();
    			if(mingzi==""||mingzi==null){
    				alert("请先输入查询条件");
    				location.reload();
    				return;
    			}
    			$.ajax({
                type: "POST",
                url:"<%=request.getContextPath()%>/api/queryVideo",
                dataType: "json",
                data:{serachVal:mingzi},
                async: false,
                error: function(request) {
                    alert("错误！！");
                },
                success: function(result) {
                	if(result.searchVideo!=null){
                		var data1 = result.searchVideo;
                		var fragment = document.createDocumentFragment();
                		console.log(data1);
                		for(var i=0;i<data1.length;i++){
                    	var content1 = $('.content')[0];
        				var $content = $(content1.cloneNode(true));
	            		$content.find('.img2').attr('src',data1[i][4]);
	            		$content.find('.img').attr('id',data1[i][1]);
	            		$content.find('.text').attr('id',data1[i][1]);
	            		$content.find('#xingming').html('姓名:'+data1[i][24]);
	            		$content.find('#bianhao').html('参赛编号:'+data1[i][1]);
	            		$content.find('#score').html('作品得分:'+data1[i][6]);
	            		$content.find('.piaoshu').attr('id','vt'+(i+1));
	            		$content.find('.piaoshu').html('当前票数'+data1[i][14]);
	            		$content.find('#toupiaotemp').html(data1[i][14]);
	            		$content.find('#toupiaotemp').attr('class','vt'+(i+1));
	            		$content.find('.voteclick').attr('id',data1[i][1]);
	            		$content.find('.voteclick').attr('click','vote(this,vt'+(i+1)+')');
	            		$(fragment).append($content);
	            		}
	            		$('.videos').empty();
	            		$('.videos').append(fragment);
	            		flag=false;
	            		$('.footer1').css('display','none');
                    }else{
                    	alert("没有该参赛信息");
                    }
                }
            });
    			
    		});
    		
    		var t1 = window.setInterval(updataStatisticalInfo,5000);
    		
    	});
    	function play(that){
    		var vid=that.id;
           	window.location.href = "<%=request.getContextPath()%>/api/getAllVideoCompetitionByVerifSuccess2?vid="+vid;
    	}
    	function vote(that,vt){
    		var vid = that.id;
    		var nowDate = new Date();
    		var endDate = new Date(2017, 10-1, 18,20,00); 
    		if(nowDate<endDate){
    			location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=toupiao:"+vid+"#wechat_redirect"; 
    		}else{
    			alert("投票已经截止");
    		}
            
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
        	for(var i=0;i<data.length;i++){
            		var content1 = $('.content')[0];
        			var $content = $(content1.cloneNode(true));
            		count++;
            		$('.videos').append($content);
            		$content.find('.img2').attr('src',data[i][4]);
            		$content.find('.img').attr('id',data[i][1]);
            		$content.find('.text').attr('id',data[i][1]);
            		$content.find('#xingming').html('姓名:'+data[i][17]);
            		$content.find('#bianhao').html('参赛编号:'+data[i][1]);
            		$content.find('#score').html('作品得分'+data[i][5]);
            		$content.find('.piaoshu').attr('id','vt'+count);
            		$content.find('.piaoshu').html('当前票数'+data[i][13]);
            		$content.find('#toupiaotemp').html(data[i][13]);
            		$content.find('#toupiaotemp').attr('class','vt'+count);
            		$content.find('.voteclick').attr('id',data[i][1]);
            		$content.find('.voteclick').attr('click','vote(this,vt'+count+')');
            		}
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
	        		page++;
	    		$.ajax({
	                type: "POST",
	                url:"<%=request.getContextPath()%>/api/getAllVideoCompetitionByVerifSuccessWithAjax?page="+page,
	                async: false,
	                error: function(request) {
	                    alert("错误！！");
	                },
	                success: function(result) {
	                	if(result.data.length>0){
	                    	console.log(result.data);
	                    	addVideo(result.data);
	                    }else{
	                    	$('#btnn').html('已无更多');
	                    }
	                }
	            });
            }
        }
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
	
	
    </script>

</head>
<body>
<div class="container-fluidcontainer-fluid">
	<div id="s" style="width:100%; position:relative; white-space:nowrap; overflow:hidden; height:20px;background-color: #2e3238">
    <div id="noticeList" style="position:absolute; top:10; height:10px;color:white;"><span>感谢山东省临沂市兰陵县教育局，</span><span>山西省临汾市中国人寿、太平洋保险，</span><span>安徽省安庆市怀宁县独秀小学、振宁学校，以及广大的家长和小朋友们对本次活动的大力支持与配合！</span></div>
	</div>
    <div class="header_img">
        <img src='<%=request.getContextPath() %>/activity/img/header.jpg' style='width:100%'/>
    </div>

    <div class="activity_info">
        <div class="title">
            <h5>英语新势力&蛋蛋机器人英语秀【全国争霸赛】</h5>
        </div>

        <div class="vote_info">
            <ul class='inline'>
                <li>参与学生<BR><span id='wxallstunum'>${stuNum}</span></li>
                <li class='left-border'>累计投票<BR><span id='wxallvotenum'>${voteNum }</span></li>
                <li class='left-border'>访问次数<BR><span id='wxallviewnum'>${acessNum }</span></li>
            </ul>
        </div>

        <div class='blockcell'>
            <i class='glyphicon glyphicon-time'></i> 投票开始时间：2017-10-12
        </div>
        <div class='blockcell'>
            <i class='glyphicon glyphicon-time'></i> 投票截止时间：2017-10-18
        </div>
        <div class='blockcell'>
            <i class='glyphicon glyphicon-warning-sign'></i> 投票规则：每个微信给每个选手每天只能投1票
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
    <input id="memberId" type="text" value="${memberId }" style="display: none"> 
    <div style="width: 100%;text-align: right;padding: 10px 10px 10px 10px">
    	<input type="text" id="serach" placeholder="请输入姓名或参赛编号" style="text-align: center;width: 80%"><button id="serachbtn" style="width: 20%">搜索</button>
    </div>
    <div class="videos">
	<c:forEach items="${data}" var="item" varStatus="status"> 
    <div class="content">
        <div class="content2">
            <div class="num">
                <div>
                    <span>#</span>
                </div>
            </div>
            <div class="img" id="${item[1]}" onclick="play(this)">
                <img class="img1" src="<%=request.getContextPath() %>/activity/img/001.png">
                <img class="img2" src="${item[4]}"">
            </div>
            <div class="text" id="${item[1]}" onclick="play(this)" >
                <div><span id="xingming">姓名:${item[24]}</span></div>
                <div><span id="bianhao">参赛编号:${item[1]}</span></div>
                <div><span id="score">作品得分:${item[6]}</span></div>
                <div><span id="vt${status.count }" class="piaoshu">当前票数:${item[14]}</span></div>
                <span class="vt${status.count }" id="toupiaotemp" style="display:none">${item[14]}</span>
            </div>
            <div class="vote">
                <img id="${item[1]}" class="voteclick" src="<%=request.getContextPath() %>/activity/img/vote.png" onclick="vote(this,'vt${status.count }')">
            </div>
        </div>
    </div>
	</c:forEach>
	</div>
	<br>
	<div class="footer1" style="width: 100%;text-align: center;height: 20px"><img src="<%=request.getContextPath() %>/activity/img/loding.gif" width="20px" height="20px">&nbsp;&nbsp;&nbsp;<span id="btnn" style="font-size: 13px;line-height: 20px">加载更多</span></div>
	<br>
</div>

</body>
</html>