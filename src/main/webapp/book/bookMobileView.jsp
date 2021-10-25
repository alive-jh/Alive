<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
		<title>图书详情</title>
		
		<link rel="stylesheet" href="css/common.css" />
        <link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/index.css" />
		<link rel="stylesheet" href="css/bookDetail.css" />
		
		<script src="lib/js/zepto.js"></script>
		<script src="js/common.js"></script>
		<script src="js/jquery.wordLimit.js"></script>
		<script src="js/jquery.fixedBlock.js"></script>
	</head>

		<script>
	function addBook(id)
	{
	
		var tempMemberId ="${memberId}";
		if(tempMemberId == '')
		{

						document.forms['bookForm'].action = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&state=toBookView@"+id+"#wechat_redirect";
						document.forms['bookForm'].submit();
		}
		else
		{
		
				$.ajax({
				type: "POST",
				data:"",
				dataType: "json",
				url: '<%=request.getContextPath()%>/book/saveBookVehicle?memberId=${memberId}&cateId='+id,
				context: document.body, 
				beforeSend:function(){
				},
				complete:function(){
				},
				success: function(data){
						
					
				}

				});
					alert("已添加到借书车!");

				}
		

	}
	</script>
	<body>
		
		<form name="bookForm" method="post" >
			</form>
		<div class="page-content">
			<!--hort-info-->
			<div class="short-info">
				<div class="short-info-box">
					<div class="short-info-text">
						<h1>${category[1]}</h1>
						<h3>${category[5]}</h3>
						<div class="book-star clearfix">
	            			<ul class="clearfix">
	            				<li class="active"></li>
	            				<li class="active"></li>
	            				<li class="active"></li>
	            				<li class="active"></li>
	            				<li class="harf"></li>
	            			</ul>
	            			<b>7.6</b>
	            			<span>(201人评价)</span>
	            		</div>
	            		<p>${category[4]}</p>
	            		<p>${category[12]}/${category[13]}</p>
	            		<p>${category[6]}页</p>
					</div>
					<div class="short-info-img">
						<!--<img src="<%=request.getContextPath() %>/wechatImages/book/${category[7]}" />-->
						<img src="<%=request.getContextPath() %>/wechatImages/book/${category[7]}" />
						<span class="publish">${category[12]}</span>
					</div>
				</div>
				
			</div>
			<div class="price-buy clearfix">
				<span class="price">￥${category[2]}</span>
				<button class="give-btn"><i></i><span>购买</span></button>
				<button class="cart-btn" onclick="addBook('${category[0]}')"><i></i><span>添加到书架</span></button>
				<!--
				<button class="give-btn"><i></i><span>赠送</span></button>
				-->
			</div>
			<!--brief-info-->
			<div class="brief-info info-box up">
				<h2 class="info-title">简介</h2>
				<div class="brief-info-content">
					${category[8]}
				</div>
			</div>
			<!--index-info-->
			<div class="index-info info-box up">
				<h2 class="info-title">目录</h2>
				<div class="index-info-content">
					<ul class="first-lever">
						${category[14]}
					</ul>
				</div>
			</div>
			<!--author-info-->
			<!--
			<div class="author-info info-box">
				<h2 class="info-title">作者</h2>
				<div class="author-info-content">
					<div class="author-img-name clearfix">
						<img src="images/author.jpg">
						<div class="author-name">
							<h3>未名湖听书</h3>
							<p>已发表<span>4</span>篇作品</p>
						</div>
					</div>
					<div class="author-text">
						作者说明作者说明作者说明作者说明作者说明作者说明作者说明作者说明作者说明作者说明作者说明作者说明
					</div>
				</div>
			</div>
			-->
			<!--comment-info-->
			<div class="comment-info info-box">
				<h2 class="info-title">读者评论</h2>
				<ul class="comment-info-content">

				<c:forEach items="${commentList}" var="comment">
					<li>
						<a href="javascript:void(0);">
							<div class="book-star clearfix">
                    			<ul class="clearfix">
                    				<li class="active"></li>
                    				<li class="active"></li>
                    				<li class="active"></li>
                    				<li class="active"></li>
                    				<li></li>
                    			</ul>
                    		</div>
                    		<p class="comment-text">${comment[1]}</p>
                    		<div class="time-num">
                    			<span class="comment-name">${comment[0]}</span>
                    			<span class="comment-time">${comment[3]}</span>
								<!--赞和评论
                    			<i class="comment-follow">1000</i>
                    			<i class="comment-agree">2</i>
								-->
                    		</div>
						</a>
					</li>
					</c:forEach>
				</ul>
				<button class="all-comment-btn">
					<span>全部评论</span>
				</button>
			</div>
			<!--tag-info-->
			<div class="tag-info info-box">
				<h2 class="info-title">标签</h2>
				<ul class="tag-info-content">
				<c:forEach items="${labelList}" var="tempLabel">
					<li>
						<a href="javascript:void(0);">${tempLabel}</a>
					</li>
				</c:forEach>	
				</ul>
			</div>
			<!--other-box-->
	        <div class="other-box other-like-box">
	        	<h2 class="box-title box-title-icon clearfix">每日推荐</h2>
	        	<ul class="book-list clearfix">
	        		<li>
	        			<a href="javascript:void(0);">
	                		<img src="images/book_01.jpg" alt="图1"/>
	                		<h3 class="book-name">喜欢就买单</h3>
	                		<p class="author-name">闻珺里</p>
	                	</a>
	        		</li>
	        		<li>
	        			<a href="javascript:void(0);">
	                		<img src="images/book_01.jpg" alt="图1"/>
	                		<h3 class="book-name">喜欢就买单</h3>
	                		<p class="author-name">闻珺里</p>
	                	</a>
	        		</li>
	        		<li>
	        			<a href="javascript:void(0);">
	                		<img src="images/book_01.jpg" alt="图1"/>
	                		<h3 class="book-name">喜欢就买单</h3>
	                		<p class="author-name">闻珺里</p>
	                	</a>
	        		</li>
	        		<li>
	        			<a href="javascript:void(0);">
	                		<img src="images/book_01.jpg" alt="图1"/>
	                		<h3 class="book-name">喜欢就买单</h3>
	                		<p class="author-name">闻珺里</p>
	                	</a>
	        		</li>
	        		<li>
	        			<a href="javascript:void(0);">
	                		<img src="images/book_01.jpg" alt="图1"/>
	                		<h3 class="book-name">喜欢就买单</h3>
	                		<p class="author-name">闻珺里</p>
	                	</a>
	        		</li>
	        		<li>
	        			<a href="javascript:void(0);">
	                		<img src="images/book_01.jpg" alt="图1"/>
	                		<h3 class="book-name">喜欢就买单</h3>
	                		<p class="author-name">闻珺里</p>
	                	</a>
	        		</li>
	        	</ul>
	        </div>
			
		</div>
		        <!--menu-group-->
        <div class="menu-group off">
            <ul class="clearfix">
                <li class="switch-btn">
                    <a href="javascript:void(0);">
                    </a>
                </li>
                <li class="shop-btn">
                    
					<a href="<%=request.getContextPath()%>/book/bookMobileManager?memberId=${memberId}">
                    </a>
                </li>
                <li class="me-btn">
                   <a href="<%=request.getContextPath()%>/book/memberBookInfo?memberId=${memberId}">

                    </a>
                </li>
                <li class="local-btn">
                    <a href="<%=request.getContextPath()%>/book/bookVehicleManager?memberId=${memberId}">

                    </a>
                </li>
            </ul>
        </div>
        <!--menu-group-end-->
	</body>
	<script>
		$(function(){
			//简介展现效果
			var _str=$('.brief-info-content').html();
			$('.brief-info-content').wordLimit(140);
			$('.brief-info-content').click(function(){
				$(this).parent().toggleClass("up");
				if($(this).parent().hasClass("up")){
					$(this).wordLimit(140);
				}else{
					$('.brief-info-content').html(_str);
					$(this).wordLimit();
				}
			});
			
			//目录展现效果
			$('.index-info-content').css('height','7.2rem');
			$('.index-info-content').click(function(){
				$(this).parent().toggleClass("up");
				if($(this).parent().hasClass("up")){
					$(this).css('height','9rem');
				}else{
					$(this).css('height','auto');
				}
			});
			
			//阅读按钮固定效果
			$('#read-btn').fixedBlock({
				top:0,
				fixedCallback:function(){
					$(this).addClass("fixed");
				},
				staticCallback:function(){
					$(this).removeClass("fixed");
				}
			});
		})
	</script>
</html>


