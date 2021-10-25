<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		

		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>商品评价</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commentList.css" />
		<script src="<%=request.getContextPath()%>/js/zepto.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.telNum.js"></script>
		<script src="<%=request.getContextPath()%>/js/tabPlugin.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

			<meta name="viewport" content="user-scalable=yes, width=640">

			 <script type="text/javascript">
		        if(/Android (\d+\.\d+)/.test(navigator.userAgent)){
		            var version = parseFloat(RegExp.$1);
		            if(version>2.3){
		                var phoneScale = parseInt(window.screen.width)/640;
		                document.write('<meta name="viewport" content="width=640, minimum-scale = '+ phoneScale +', maximum-scale = '+ phoneScale +', target-densitydpi=device-dpi">');
		            }else{
		                document.write('<meta name="viewport" content="width=640, target-densitydpi=device-dpi">');
		            }
		        }else{
		            document.write('<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">');
		        }
		        
		        
	
		
		
	wx.config({
    			debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数
    			appId: '${appId}', // 必填，公众号的唯一标识
    			timestamp: ${timestamp},
				nonceStr: '${nonceStr}',
				signature: '${signature}',
    			jsApiList: [  'checkJsApi',
            	'previewImage'
			] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	
	function showImg(img)
	{
		
		wx.previewImage({
    	current: img, // 当前显示图片的http链接
   		 urls: [img] // 需要预览的图片http链接列表
		})
	}



		
		
		
		    </script>
		    
		    <meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
	</head>
	<body>
		<div class="content no-margin">
			<!--头部开始-->
			
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body">
				<div class="comment-head comment-select tab-head">
					<!--
					<ul class="clearfix">
						<li datatarget="all-comment">
							<h3>全部</h3>
							<p>26</p>
						</li>
						<li datatarget="good-comment">
							<h3>好评</h3>
							<p>25</p>
						</li>
						<li datatarget="soso-comment">
							<h3>中评</h3>
							<p>0</p>
						</li>
						<li datatarget="bad-comment">
							<h3>差评</h3>
							<p>1</p>
						</li>
					</ul>
					-->
				</div>
				<div class="comment-head">
					商品评价（<span>${totalCount}</span>）
				</div>
				<div class="tab-body">
				
					<div class="comment-list tab-cont" name='all-comment'>
						<ul>
						<c:forEach items="${commentList}" var ="tempComment">
							<li>
								<h3 class="num-star clearfix">
									<span class="tel-num left">${tempComment.memberName}</span>
									<span class="stars right" data-star="${tempComment.star}">
									</span>
								</h3>
								<p class="comment-text">
									${tempComment.content}
								</p>
								<p class="comment-time">${tempComment.createDate}</p>
								<c:forEach items="${tempComment.imgList}" var = "tempImg">
									<img witch="50" height="50" src="${tempImg}" onclick="showImg('${tempImg}')"/>&nbsp;
								</c:forEach>		
							</li>
						</c:forEach>	
						</ul>
						<a class="load-more" href="javascript:void(0)">查看更多评论</a>
					</div>
					<!--
					<div class="tab-cont" name="good-comment">
						2222
					</div>
					<div class="tab-cont" name="soso-comment">
						3333
					</div>
					<div class="tab-cont" name="bad-comment">
						4444
					</div>
					-->
				</div>
				
			</div>
			<!--主要部分结束-->
			<!--底部菜单开始-->
			
			<!--底部菜单结束-->
		</div>
	</body>
	<script>
		$(function(){
			$('.tel-num').telNum();
			//根据data-star加载星星数量
			$('.stars').each(function(i,item){
				var _starNum=$(this).attr('data-star');
				var _starStr='';
				for(var i=0;i<5;i++){
					if(i<_starNum){
						_starStr+='<img src="<%=request.getContextPath()%>/images/star2.png"/>';
					}else{
						_starStr+='<img src="<%=request.getContextPath()%>/images/star2-2.png"/>';
					}
				}
				$(this).html(_starStr);
			})
			
			//tab效果
			$('.content-body').tabPlugin();
		})
	</script>
</html>


</html>
