<%@ page language="java" import="java.util.*,com.wechat.entity.VideoAcitivity" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${video[23]}的学习视频</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/video_share.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/messageBox/css/comment.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/evaluate-1.css">
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    
    

</head>
<script type="text/javascript">
var openid;
var vid;
var memberid;
var ajaxPack= {
		method:function(url,data,method,success){
	        $.ajax({
	            type: method,
	            url: url,
	            data: data,
	            timeout: 30000,
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
var videoPanle;
$(function(){
	videoPanle = document.getElementById("videoPanel123").innerHTML;
	var isExits = $('#isExits').val();
	if(isExits==""||isExits==null||isExits=="null"||isExits==undefined){
    	alert("视频信息不存在");
    	return;
    } 
	
	openid = $('#openid').val();
	vid = $('#vid').val();
	memberid = $('#memberid').val();
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: "${appid}", // 必填，公众号的唯一标识
	    timestamp: "${timestamp}", // 必填，生成签名的时间戳
	    nonceStr: "${noncestr}",  // 必填，生成签名的随机串
	    signature: "${signature}",// 必填，签名，见附录1
	    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	wx.ready(function(){
		wx.onMenuShareAppMessage({
		    title: '${video[23]}的学习视频', // 分享标题
		    desc: '仅仅几个月，英语突飞猛进', // 分享描述
		    /* link: 'https://wechat.fandoutech.com.cn/wechat/api/wechatShare?vid='+"${video[0]}", */ // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		    link: 'https://wechat.fandoutech.com.cn/wechat/member/wechatShareForward?vid='+'${video[0]}',
		    imgUrl: '${video[3]}', // 分享图标
		    type: '', // 分享类型,music、video或link，不填默认为link
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
	});	  
	

	
	
			
});
function wechatpay(){
	var oid1 = $('#openid').val();
	var payPrice = $("#price12").val();
	$.ajax({
        type : 'POST',
        url : '<%=request.getContextPath()%>/payment/gopay',
        dataType : 'json',
        data : {
            "commodityName" : '视频打赏',//商品名称
            "totalPrice" : payPrice, //支付的总金额
            "memberOpenId" : oid1,  //openid
            "memberId" : $("#memberid").val() //
        },
        cache : false,
        error : function() {
            alert("系统错误，请稍后重试！");
            return false;
        },
        success : function(data) {
            if (parseInt(data[0].agent) < 5) {
                alert("您的微信版本低于5.0无法使用微信支付。");
                return;
            }
			orderNumber = data[0].timeStamp;
			
            WeixinJSBridge.invoke('getBrandWCPayRequest',{
                "appId" : data[0].appId, //公众号名称，由商户传入  
                "timeStamp" : data[0].timeStamp, //时间戳，自 1970 年以来的秒数  
                "nonceStr" : data[0].nonceStr, //随机串  
                "package" : data[0].packageValue, //商品包信息
                "signType" : data[0].signType, //微信签名方式:  
                "paySign" : data[0].paySign //微信签名  
            },
				function(res) {
                /* 对于支付结果，res对象的err_msg值主要有3种，含义如下：(当然，err_msg的值不只这3种)
                1、get_brand_wcpay_request:ok   支付成功后，微信服务器返回的值
                2、get_brand_wcpay_request:cancel   用户手动关闭支付控件，取消支付，微信服务器返回的值
                3、get_brand_wcpay_request:fail   支付失败，微信服务器返回的值

                -可以根据返回的值，来判断支付的结果。
                -注意：res对象的err_msg属性名称，是有下划线的，与chooseWXPay支付里面的errMsg是不一样的。而且，值也是不同的。
                */
                
                if (res.err_msg == 'get_brand_wcpay_request:ok') {
                    //alert("支付成功！");
                    $('#myModal').modal('hide');
                    $('.video').append(videoPanle);
                    ajaxPack.method("../api/savePayRecord",{ vid: vid, memberid: memberid,price:payPrice},"post",function (data) {
                    	if(data.code==200)
                    	alert("感谢您的鼓励");
                    });
                } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                   // alert("您已手动取消该订单支付。");
                } else {
   					alert(res.err_msg);
                    alert("订单支付失败,请重试!");
                    alert(JSON.stringify(res));
                }
            });
        }
    });
				
}
function get_price(price) {
	$('#media').remove();
	$('#myModal').modal('show');
	$('#price12').focus();
	$('#price12').val(price);
}
function gopay(){
	var prices = $('#price12').val();
	if(prices!=null||prices!=''||prices!=undefined){
		wechatpay();
	}else{
		alert("请输入打赏金额");
		return;
	}
}
function addlike(that){
	var likeNum = $(that).find('a').html();
	ajaxPack.method("../api/addlike",{ openid: openid, vid: vid },"post",function (data) {
	    if(data.code==200){
	    	likeNum++;
	    	$(that).find('a').html(likeNum);
	    	alert('点赞成功');
	    	return;
	    }if(data.code==201){
	    	alert('你已经点过赞啦，明天再来');
	    	return;
	    }else{
	    	alert('未授权登录');
	    	return;
	    }
	});
	
}
function hideModel(){
	$('#myModal').modal('hide')
	$('.video').append(videoPanle);
}
</script>
<body><script src="<%=request.getContextPath() %>/activity/demos/googlegg.js"></script>
<!--
    此评论textarea文本框部分使用的https://github.com/alexdunphy/flexText此插件
-->
<div class="container-fluidcontainer-fluid" >
    <div class="title">
        <h4>${video[24]}的学习视频&nbsp;&nbsp;&nbsp;&nbsp;${gradeName }</h4>
        <span>${fn:substring(video[10], 0, 16)} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${learnTime }</span>
    </div>
    <HR align=center width=90% color=#987cb9 SIZE=1>
    <div id="videoPanel123" class="video" style="z-index: 1">
        <video id="media" controls="controls" width="95%" webkit-playsinline="true" playsinline="true" x5-video-orientation="h5" height="80px" poster="${video[3]}" style="z-index: 1">
            <source id="video1" src="${video[2]}">
        </video>
    </div>
</div>
<input id="vid" type="hidden" value="<%=request.getParameter("vid") %>">
<input id="isExits" type="hidden" value="${video[0]}">
<input id="openid" type="hidden" value="<%=request.getParameter("openid") %>">
<input id="memberid" type="hidden" value="<%=request.getParameter("memberid") %>">
<HR align=center width=90% color=#987cb9 SIZE=1>
<div class="commentAll">
    <!--评论区域 begin-->
    <!--评论区域 end-->
    <!--回复区域 begin-->
    <div class="comment-show">
    <c:forEach items="${vclist}" var="item" varStatus="status">  
	        <div class="comment-show-con clearfix">
	            <div class="comment-show-con-img pull-left"><img src="http://oqcfa7n69.bkt.clouddn.com/logo.png" alt=""></div>
	            <div class="comment-show-con-list pull-left clearfix">
	            	<div style="float: left;margin-top: 10px;">
	            	<a href="#" class="comment-size-name">${item.commenter }: </a>
	            	</div>
	                <div class="pl-text clearfix" style="float: left">
	                    
	                    <span class="my-pl-con">&nbsp;${item.content }</span>
	                </div>
	                <div class="date-dz">
	                    <span class="date-dz-left pull-right comment-time">${fn:substring(item.creatTime, 0, 16)}</span>
	                    <div class="date-dz-right pull-right comment-pl-block">
	                    ${pageScope.openid }
	                        <c:if test="${item.openid eq openid}">
	                        	<a id="${item.id }" href="javascript:;" class="removeBlock">删除</a>	
	                        	<span class="pull-left date-dz-line">|</span>
	                        </c:if>
	<!--                         <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a>-->
	                    </div>
	                </div>
	                <div class="hf-list-con"></div>
	            </div>
	        </div>
      </c:forEach>
      <div class="comment-show-con clearfix">
            <div class="comment-show-con-img pull-left"><img src="<%=request.getContextPath() %>/activity/images/header-img-comment_03.png" alt=""></div>
            <div class="comment-show-con-list pull-left clearfix">
                <div class="pl-text clearfix">
                    <a href="#" class="comment-size-name">凡豆博士 : </a>
                    <span class="my-pl-con">&nbsp;小朋友你太厉害了</span>
                </div>
                <div class="date-dz">
                    <span class="date-dz-left pull-right comment-time">${fn:substring(video[10], 0, 16)}</span>
                    <div class="date-dz-right pull-right comment-pl-block">
<!--                         <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a>-->
                    </div>
                </div>
                <div class="hf-list-con"></div>
            </div>
        </div>
    </div>
    <!--回复区域 end-->
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="top:30%;z-index: 9999999999">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel" style="text-align: center;">
					给TA的鼓励
				</h4>
			</div>
			<div class="modal-body" style="text-align: center;font-size: 25px">
				¥ &nbsp;<input id="price12" type="text" style="width:90px;font-size: 25px;text-align: center;"/>
			</div>
            <div class="modal-footer" style="width: 100%;text-align: center">
                <button type="button" class="btn btn-default" onclick="hideModel()">关闭</button>
                <button type="button" class="btn btn-primary" onclick="gopay()">鼓励一下</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript" src="<%=request.getContextPath() %>/activity/js/jquery.flexText.js"></script>
<!--textarea高度自适应-->
<script type="text/javascript">
    $(function () {
        $('.content').flexText();
    });
</script>
<!--textarea限制字数-->
<script type="text/javascript">
    function keyUP(t){
        var len = $(t).val().length;
        if(len > 139){
            $(t).val($(t).val().substring(0,140));
        }
    }
</script>
<!--点击评论创建评论条-->
<script type="text/javascript">
    $('.commentAll').on('click','.plBtn',function(){
    	var that = $(this);
        var myDate = new Date();
        //获取当前年
        var year=myDate.getFullYear();
        //获取当前月
        var month=myDate.getMonth()+1;
        //获取当前日
        var date=myDate.getDate();
        var h=myDate.getHours();       //获取当前小时数(0-23)
        var m=myDate.getMinutes();     //获取当前分钟数(0-59)
        if(m<10) m = '0' + m;
        var s=myDate.getSeconds();
        if(s<10) s = '0' + s;
        var now=year+'-'+month+"-"+date+" "+h+':'+m+":"+s;
        //获取输入内容
        var oSize = $(this).siblings('.flex-text-wrap').find('.comment-input').val();    
        var sjs = Math.floor(Math.random()*99999);
        var oid = $('#openid').val();
        if(oid==""||oid==null||oid=="null"||oid==undefined){
        	alert("未授权登录");
        	return;
        }        
        var pinglun = $('#pinglun').val();
        if(pinglun!=null&&pinglun!=""&&pinglun!=undefined){
        ajaxPack.method("../api/addVideoComment",{ vid: vid, content: pinglun,commenter: '游客'+sjs, openid: openid,memberid: memberid },"post",function (data) {
        	if(data.code==200){
        		//动态创建评论模块
                oHtml = '<div class="comment-show-con clearfix"><div class="comment-show-con-img pull-left"><img src="http://oqcfa7n69.bkt.clouddn.com/logo.png" alt=""></div> <div class="comment-show-con-list pull-left clearfix"><div style="float: left;margin-top: 10px;"><a href="#" class="comment-size-name">游客'+sjs+' : </a></div><div class="pl-text clearfix" style="float: left"><span class="my-pl-con">&nbsp;'+ pinglun +'</span> </div> <div class="date-dz"> <span class="date-dz-left pull-right comment-time">'+now+'</span> <div class="date-dz-right pull-right comment-pl-block"><a id="'+data.vcID+'" href="javascript:;" class="removeBlock">删除</a> <span class="pull-left date-dz-line">|</span> </div> </div><div class="hf-list-con"></div></div> </div>';
                that.parents('.reviewArea ').siblings('.comment-show').prepend(oHtml);
                that.siblings('.flex-text-wrap').find('.comment-input').prop('value','').siblings('pre').find('span').text('');     
        	}
        });
        }else{
        	alert("评论内容不能为空");
        }
        
        
        
        
    });
</script>
<!--点击回复动态创建回复块-->
<script type="text/javascript">
    $('.comment-show').on('click','.pl-hf',function(){
        //获取回复人的名字
        var fhName = $(this).parents('.date-dz-right').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
        //回复@
        var fhN = '回复@'+fhName;
        //var oInput = $(this).parents('.date-dz-right').parents('.date-dz').siblings('.hf-con');
        var fhHtml = '<div class="hf-con pull-left"> <textarea class="content comment-input hf-input" placeholder="" onkeyup="keyUP(this)"></textarea> <a href="javascript:;" class="hf-pl">评论</a></div>';
        //显示回复
        if($(this).is('.hf-con-block')){
            $(this).parents('.date-dz-right').parents('.date-dz').append(fhHtml);
            $(this).removeClass('hf-con-block');
            $('.content').flexText();
            $(this).parents('.date-dz-right').siblings('.hf-con').find('.pre').css('padding','6px 15px');
            //console.log($(this).parents('.date-dz-right').siblings('.hf-con').find('.pre'))
            //input框自动聚焦
            /* $(this).parents('.date-dz-right').siblings('.hf-con').find('.hf-input').val('').focus().val(fhN); */
        }else {
            $(this).addClass('hf-con-block');
            $(this).parents('.date-dz-right').siblings('.hf-con').remove();
        }
    });
</script>
<!--评论回复块创建-->
<script type="text/javascript">
    $('.comment-show').on('click','.hf-pl',function(){
        var oThis = $(this);
        var myDate = new Date();
        //获取当前年
        var year=myDate.getFullYear();
        //获取当前月
        var month=myDate.getMonth()+1;
        //获取当前日
        var date=myDate.getDate();
        var h=myDate.getHours();       //获取当前小时数(0-23)
        var m=myDate.getMinutes();     //获取当前分钟数(0-59)
        if(m<10) m = '0' + m;
        var s=myDate.getSeconds();
        if(s<10) s = '0' + s;
        var now=year+'-'+month+"-"+date+" "+h+':'+m+":"+s;
        //获取输入内容
        var oHfVal = $(this).siblings('.flex-text-wrap').find('.hf-input').val();
        console.log(oHfVal)
        var oHfName = $(this).parents('.hf-con').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
        var oAllVal = '回复@'+oHfName;
        if(oHfVal.replace(/^ +| +$/g,'') == '' || oHfVal == oAllVal){

        }else {
            $.getJSON("json/pl.json",function(data){
                var oAt = '';
                var oHf = '';
                $.each(data,function(n,v){
                    delete v.hfContent;
                    delete v.atName;
                    var arr;
                    var ohfNameArr;
                    if(oHfVal.indexOf("@") == -1){
                        data['atName'] = '';
                        data['hfContent'] = oHfVal;
                    }else {
                        arr = oHfVal.split(':');
                        ohfNameArr = arr[0].split('@');
                        data['hfContent'] = arr[1];
                        data['atName'] = ohfNameArr[1];
                    }

                    if(data.atName == ''){
                        oAt = data.hfContent;
                    }else {
                        
                    }
                    oHf = data.hfName;
                });

                var oHtml = '<div class="all-pl-con"><div class="pl-text hfpl-text clearfix"><a href="#" class="comment-size-name">我的名字 : </a><span class="my-pl-con">'+oAt+'</span></div><div class="date-dz"> <span class="date-dz-left pull-left comment-time">'+now+'</span> <div class="date-dz-right pull-right comment-pl-block"> <a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 </a> </div> </div></div>';
                oThis.parents('.hf-con').parents('.comment-show-con-list').find('.hf-list-con').css('display','block').prepend(oHtml) && oThis.parents('.hf-con').siblings('.date-dz-right').find('.pl-hf').addClass('hf-con-block') && oThis.parents('.hf-con').remove();
            });
        }
    });
</script>
<!--删除评论块-->
<script type="text/javascript">
    $('.commentAll').on('click','.removeBlock',function(){
      var vid = $(this).attr('id');
      $.post("../api/delVideoComment", { vid: vid });
      var oT = $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con');
      if(oT.siblings('.all-pl-con').length >= 1){
          oT.remove();
      }else {
          $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con').parents('.hf-list-con').css('display','none')
          oT.remove();
      }
      $(this).parents('.date-dz-right').parents('.date-dz').parents('.comment-show-con-list').parents('.comment-show-con').remove();   

    })
</script>


</body>
</html>
