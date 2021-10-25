<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
		<title>凡豆未来书院</title>
		
		<link rel="stylesheet" href="css/common.css" />
		<link rel="stylesheet" href="plug/css/swiper.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/index.css" />
		
		<script src="lib/js/zepto.js"></script>
		<script src="js/common.js"></script>
		<script src="plug/js/swiper.js"></script>
		
		
	</head>
	<body>
		<!--search-->
		<div class="search-box fixed-bolck">
			<input type="search" placeholder="自出版/图书/杂志"/>
			<button class="book-type-btn"></button>
		</div>
		
		
		
		<!-- Swiper -->
	    <div class="swiper-container swiper-container-h1">
	    	<!--swiper-btn-->
			<div class="head-btn fixed-bolck">
				<ul class="clearfix">
					<li class="publish-btn active">
						<span><i></i>中文</span>
					</li>
					<li class="book-btn">
						<span><i></i>英语</span>
					</li>
					<li class="magazine-btn">
						<span><i></i>系列</span>
					</li>
				</ul>
			</div>
	        <div class="swiper-wrapper">
	        	<div class="swiper-slide">
	        		<!--slide-box-->
	        		<div class="swiper-container swiper-container-scroll1 slide-box"><div class="swiper-wrapper"><div class="swiper-slide" style="height:auto;"><div style="width:100%;">
	        			<img class="benner-img" src="images/benner.jpg" />
		                <div class="swiper-container swiper-container-h1-1">
		                    <div class="swiper-wrapper middle-swiper">
		                        <div class="swiper-slide">
		                        <c:forEach items="${fengmianList1}" var="tempFengmian">
		                        	<a href="<%=request.getContextPath()%>/book/bookMobileView?cateId=${tempFengmian[0]}">
		                        		<img src="<%=request.getContextPath()%>/wechatImages/book/${tempFengmian[3]}" alt="${tempFengmian[1]}"/>
		                        		<h3 class="book-name">${tempFengmian[1]}</h3>
		                        		<p class="author-name">${tempFengmian[2]}</p>
		                        	</a>
		                        </c:forEach>
		                        	
		                        </div>
		                        <div class="swiper-slide">
	                        		 <c:forEach items="${fengmianList2}" var="tempFengmian">
		                        	<a href="<%=request.getContextPath()%>/book/bookMobileView?cateId=${tempFengmian[0]}">
		                        		<img src="<%=request.getContextPath()%>/wechatImages/book/${tempFengmian[3]}" alt="${tempFengmian[1]}"/>
		                        		<h3 class="book-name">${tempFengmian[1]}</h3>
		                        		<p class="author-name">${tempFengmian[2]}</p>
		                        	</a>
		                        </c:forEach>
		                        </div>
		                        <div class="swiper-slide">
	                        		 <c:forEach items="${fengmianList3}" var="tempFengmian">
		                        	<a href="<%=request.getContextPath()%>/book/bookMobileView?cateId=${tempFengmian[0]}">
		                        		<img src="<%=request.getContextPath()%>/wechatImages/book/${tempFengmian[3]}" alt="${tempFengmian[1]}"/>
		                        		<h3 class="book-name">${tempFengmian[1]}</h3>
		                        		<p class="author-name">${tempFengmian[2]}</p>
		                        	</a>
		                        </c:forEach>
		                        </div>
		                        <div class="swiper-slide">
	                        		 <c:forEach items="${fengmianList4}" var="tempFengmian">
		                        	<a href="<%=request.getContextPath()%>/book/bookMobileView?cateId=${tempFengmian[0]}">
		                        		<img src="<%=request.getContextPath()%>/wechatImages/book/${tempFengmian[3]}" alt="${tempFengmian[1]}"/>
		                        		<h3 class="book-name">${tempFengmian[1]}</h3>
		                        		<p class="author-name">${tempFengmian[2]}</p>
		                        	</a>
		                        </c:forEach>
		                        </div>
		                        <div class="swiper-slide">
	                        		 <c:forEach items="${fengmianList5}" var="tempFengmian">
		                        	<a href="<%=request.getContextPath()%>/book/bookMobileView?cateId=${tempFengmian[0]}">
		                        		<img src="<%=request.getContextPath()%>/wechatImages/book/${tempFengmian[3]}" alt="${tempFengmian[1]}"/>
		                        		<h3 class="book-name">${tempFengmian[1]}</h3>
		                        		<p class="author-name">${tempFengmian[2]}</p>
		                        	</a>
		                        </c:forEach>
		                        </div>
		                    </div>
		                    
		                    <div class="swiper-pagination swiper-pagination-h1-1"></div>
		                </div>
		                <!--month-box-->
						<!--
		                <div class="month-box">
		                	<ul class="book-list clearfix">
		                		<li class="sale">
		                			<a href="javascript:void(0);">
		                				<h2>本月畅销</h2>
		                        		<img src="images/book_01.jpg" alt="图1"/>
		                        		<h3 class="book-name">喜欢就买单</h3>
		                        		<p class="author-name">闻珺里</p>
		                        		<button>TOP 30</button>
		                        	</a>
		                		</li>
		                		<li class="good">
		                			<a href="javascript:void(0);">
		                				<h2>本月好评</h2>
		                        		<img src="images/book_01.jpg" alt="图1"/>
		                        		<h3 class="book-name">喜欢就买单</h3>
		                        		<p class="author-name">闻珺里</p>
		                        		<button>TOP 30</button>
		                        	</a>
		                		</li>
		                		<li class="new">
		                			<a href="javascript:void(0);">
		                				<h2>本月新作</h2>
		                        		<img src="images/book_01.jpg" alt="图1"/>
		                        		<h3 class="book-name">喜欢就买单</h3>
		                        		<p class="author-name">闻珺里</p>
		                        		<button>TOP 30</button>
		                        	</a>
		                		</li>
		                	</ul>
		                </div>
						-->
		                <!--month-box-end-->
		                
		                <!--sale-box-->
						<c:forEach items="${bookInfoList}" var="bookInfo">
		                 <div class="other-box sale-box">
		                	<a href="javascript:void(0);"><a href="<%=request.getContextPath()%>/book/toBookMobileByLabel?labelId=${bookInfo.id}&title=${bookInfo.title}">
		                		<h2 class="box-title more-link">${bookInfo.title}</h2>
		                	</a>
		                	<ul class="book-list clearfix">
								<c:forEach items="${bookInfo.mallProductList}" var="tempBook">
									<li>
										<a href="<%=request.getContextPath()%>/book/bookMobileView?cateId=${tempBook[0]}">
											<img src="<%=request.getContextPath()%>/wechatImages/book/${tempBook[3]}" alt="${tempBook[1]}"/>
											<h3 class="book-name">${tempBook[1]}</h3>
											<p class="author-name">${tempBook[2]}</p>
										</a>
									</li>
									
								</c:forEach>
		                	</ul>
		                </div>
						</c:forEach>
		                <!--sale-box-end-->
		                
		                <!--other-box-->
						<!--
		                <div class="other-box">
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
		                	</ul>
		                	<button class="book-cunt">共<span>15</span>部作品</button>
		                </div>
		                -->
		                <!--like-box-->
		                <!--
						<div class="other-box like-box">
		                	<h2 class="box-title">猜你喜欢</h2>
		                	<ul class="book-list clearfix">
		                		<li>
		                			<a href="javascript:void(0);" class="clearfix">
		                        		<img src="images/book_01.jpg" alt="图1"/>
		                        		<div class="info-box">
		                        			<h3 class="book-name">喜欢就买单</h3>
		                        			<h4 class="book-subname">副标题副标题副标题副标题副标题副标题</h4>
			                        		<p class="author-name">闻珺里</p>
			                        		<div class="book-star clearfix">
			                        			<ul class="clearfix">
			                        				<li class="active"></li>
			                        				<li class="active"></li>
			                        				<li class="active"></li>
			                        				<li class="active"></li>
			                        				<li></li>
			                        			</ul>
			                        			<b>7.6</b>
			                        			<span>(201评价)</span>
			                        		</div>
			                        		<div class="book-short">内容简介内容简介内容简介内容简介</div>
		                        		</div>
		                        	</a>
		                		</li>
		                		<li>
		                			<a href="javascript:void(0);" class="clearfix">
		                        		<img src="images/book_01.jpg" alt="图1"/>
		                        		<div class="info-box">
		                        			<h3 class="book-name">喜欢就买单</h3>
		                        			<h4 class="book-subname">副标题副标题副标题副标题副标题副标题</h4>
			                        		<p class="author-name">闻珺里</p>
			                        		<div class="book-star clearfix">
			                        			<ul class="clearfix">
			                        				<li class="active"></li>
			                        				<li class="active"></li>
			                        				<li class="active"></li>
			                        				<li class="active"></li>
			                        				<li></li>
			                        			</ul>
			                        			<b>7.6</b>
			                        			<span>(201评价)</span>
			                        		</div>
			                        		<div class="book-short">内容简介内容简介内容简介内容简介</div>
		                        		</div>
		                        	</a>
		                		</li>
		                		
		                	</ul>
		                </div>

						-->
		                <!--like-box-end-->
		                <!--other-type-->
		                <div class="other-type">
		                	<ul class="clearfix">
			                	<li><a href="javascript:void(0)">类型小说</a></li>
			                	<li><a href="javascript:void(0)">严型小说</a></li>
			                	<li><a href="javascript:void(0)">世界名著</a></li>
			                	<li><a href="javascript:void(0)">散文随笔</a></li>
			                	<li><a href="javascript:void(0)">人文社科</a></li>
			                	<li><a href="javascript:void(0)">科技与科普</a></li>
			                	<li><a href="javascript:void(0)">IT</a></li>
			                	<li><a href="javascript:void(0)">商业理财</a></li>
			                	<li><a href="javascript:void(0)">. . .</a></li>
		                	</ul>
		                </div>
	                	<!--other-type-end-->
	        		</div>
	        	</div></div><div class="swiper-scrollbar swiper-scrollbar1"></div></div>
	            </div>
	            <div class="swiper-slide">
	            	<!--slide-box-->
	        		<div class="swiper-container swiper-container-scroll2 slide-box"><div class="swiper-wrapper"><div class="swiper-slide" style="height:auto;"><div style="width:100%;">
	        		<img class="benner-img" src="images/benner.jpg" />
	        		<div class="swiper-container swiper-container-h1-2">
	        			
		                    <div class="swiper-wrapper middle-swiper" >
		                        <div class="swiper-slide">	
		                        	内容1
		                        </div>
		                        <div class="swiper-slide">
		                        	内容2
		                        </div>
		                        <div class="swiper-slide">
		                        	内容3
		                        </div>
		                        <div class="swiper-slide">
		                        	内容4
		                        </div>
		                        <div class="swiper-slide">
		                        	内容5
		                        </div>
		                    </div>
		                    
	                    <div class="swiper-pagination swiper-pagination-h1-2"></div>
	                </div>
	        		<!--month-box-->
	                <div class="month-box">
	                	<ul class="book-list clearfix">
	                		<li class="sale">
	                			<a href="javascript:void(0);">
	                				<h2>本月畅销</h2>
	                        		<img src="images/book_01.jpg" alt="图1"/>
	                        		<h3 class="book-name">喜欢就买单</h3>
	                        		<p class="author-name">闻珺里</p>
	                        		<button>TOP 30</button>
	                        	</a>
	                		</li>
	                		<li class="good">
	                			<a href="javascript:void(0);">
	                				<h2>本月好评</h2>
	                        		<img src="images/book_01.jpg" alt="图1"/>
	                        		<h3 class="book-name">喜欢就买单</h3>
	                        		<p class="author-name">闻珺里</p>
	                        		<button>TOP 30</button>
	                        	</a>
	                		</li>
	                		<li class="new">
	                			<a href="javascript:void(0);">
	                				<h2>本月新作</h2>
	                        		<img src="images/book_01.jpg" alt="图1"/>
	                        		<h3 class="book-name">喜欢就买单</h3>
	                        		<p class="author-name">闻珺里</p>
	                        		<button>TOP 30</button>
	                        	</a>
	                		</li>
	                	</ul>
	                </div>
	        		<!--month-box-end-->
	        		</div></div></div><div class="swiper-scrollbar swiper-scrollbar2"></div></div>
	        		
	        		
	        		
	        	</div>
	            <div class="swiper-slide">
	            	<!--slide-box-->
	        		<div class="swiper-container swiper-container-scroll3 slide-box"><div class="swiper-wrapper"><div class="swiper-slide" style="height:auto;"><div style="width:100%;">
	        		
	        		<img class="benner-img" src="images/benner.jpg" />
	        		<div class="swiper-container swiper-container-h1-3">
		                    <div class="swiper-wrapper middle-swiper">
		                        <div class="swiper-slide">	
		                        	内容1
		                        </div>
		                        <div class="swiper-slide">
		                        	内容2
		                        </div>
		                        <div class="swiper-slide">
		                        	内容3
		                        </div>
		                        <div class="swiper-slide">
		                        	内容4
		                        </div>
		                        <div class="swiper-slide">
		                        	内容5
		                        </div>
		                    </div>
		                    
		                    <div class="swiper-pagination swiper-pagination-h1-3"></div>
		                </div>
	        		</div></div></div><div class="swiper-scrollbar swiper-scrollbar3"></div></div>
	            </div>
	        </div>
	        <!-- Add Pagination -->
	        <div class="swiper-pagination swiper-pagination-h1"></div>
	    </div>

        <!--menu-group-->
        <div class="menu-group off">
            <ul class="clearfix">
                <li class="switch-btn">
                    <a href="#">

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
		var swiperH = new Swiper('.swiper-container-h1', {
	        pagination: '.swiper-pagination-h1',
	        paginationClickable: true,
	        spaceBetween: 0,
	        onSlideChangeEnd:function(){
	        	var curr=$('.swiper-pagination-h1 span.swiper-pagination-bullet-active').index();
	        	$('.head-btn ul li').eq(curr).addClass('active').siblings().removeClass('active');
	        }
	    });
	    var swiperV = new Swiper('.swiper-container-h1-1', {
	        pagination: '.swiper-pagination-h1-1',
	        paginationClickable: true,
	        //direction: 'vertical',
	        spaceBetween: 0
	    });
	    var swiperV = new Swiper('.swiper-container-h1-2', {
	        pagination: '.swiper-pagination-h1-2',
	        paginationClickable: true,
	        //direction: 'vertical',
	        spaceBetween: 0
	    });
	    var swiperV = new Swiper('.swiper-container-h1-3', {
	        pagination: '.swiper-pagination-h1-3',
	        paginationClickable: true,
	        //direction: 'vertical',
	        spaceBetween: 0
	    });
	    $('.swiper-container-scroll1').height($('body').height()-$('.head-btn').height()*2+10);
	    var swiper = new Swiper('.swiper-container-scroll1', {
	        scrollbar: '.swiper-scrollbar1',
	        direction: 'vertical',
	        slidesPerView: 'auto',
	        mousewheelControl: true,
	        freeMode: true
    	});
    	
	    $('.swiper-container-scroll2').height($('body').height()-$('.head-btn').height()*2+10);
	    var swiper = new Swiper('.swiper-container-scroll2', {
	        scrollbar: '.swiper-scrollbar2',
	        direction: 'vertical',
	        slidesPerView: 'auto',
	        mousewheelControl: true,
	        freeMode: true
    	});
    	
	    $('.swiper-container-scroll3').height($('body').height()-$('.head-btn').height()*2+10);
	    var swiper = new Swiper('.swiper-container-scroll3', {
	        scrollbar: '.swiper-scrollbar3',
	        direction: 'vertical',
	        slidesPerView: 'auto',
	        mousewheelControl: true,
	        freeMode: true
    	});
	</script>
</html>
