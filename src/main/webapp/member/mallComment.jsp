<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>发表评论</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/fillComment.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<!-- 此处与客户端的系统有关，关系到屏幕的宽度 -->

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
		        
		        
		        
		      $(function(){
			var $deletBtn=$('.delete');
			var $starUl=$('.attr-star ul');
			var $star=$('.attr-star ul li');
			
			lightStar($starUl);
			
			$deletBtn.on('click',function(){
				$(this).parent().remove();
			})
			
			$star.on('click',function(){
				var _index=$(this).index()+1;
				$(this).parent().attr('data-star',_index);
				lightStar($(this).parent());
			})
			
			//点亮星星
			function lightStar(obj){
				obj.each(function(){
					var _index=$(this).attr('data-star');
					for(var i=0;i<5;i++){
						if(i<_index){
							$(this).find('li').eq(i).addClass('acitve');
						}else{
							$(this).find('li').eq(i).removeClass('acitve');
						}
						
					}
				})
				
			}
		})
		
		
	wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数
    appId: '${appId}', // 必填，公众号的唯一标识
    timestamp: ${timestamp},
	nonceStr: '${nonceStr}',
	signature: '${signature}',
    jsApiList: [  'checkJsApi',
            'chooseImage',
            'uploadImage',
            'downloadImage',
            'previewImage',
            'getNetworkType'
			] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});


function addImg(str){
				var newImg=$('.temp-img').clone(true).removeClass('temp-img');
				newImg.find('img').attr('src',str);
				$('.temp-img').before(newImg);
			}
  


wx.ready(function () {

       var images = {
            localId: [],
            serverId: [],
            downloadId: []
        };
       
       
     //选择图片
	document.querySelector('#scanQRCode1').onclick = function () {
        
       
            wx.chooseImage({
                success: function (res) {
               
                    images.localId = res.localIds;
                    jQuery(function(){
                       $.each( res.localIds, function(i, n){
                      		
                            //document.getElementById("testImg").src=n;   
                            addImg(n);
                        });
                    });
                    uploadImage();
                    
                }
            });
        };
  
  
  //上传图片
 		
     function uploadImage()
     {
      if (images.localId.length == 0) 
      {
      	//alert('请先使用 chooseImage 接口选择图片');
      	return;
	  }
	    var i = 0, length = images.localId.length;
	    images.serverId = [];
	    function upload() {
	      wx.uploadImage({
	        localId: images.localId[i],
	        success: function (res) {
	          i++;
	          //alert('已上传：' + i + '/' + length);
	          
	          images.serverId.push(res.serverId);
	         document.forms['commentForm'].img.value = document.forms['commentForm'].img.value + res.serverId+",";
	          if (i < length) {
	            upload();
	          }
	        },
	        fail: function (res) {
	          alert(JSON.stringify(res));
	        }
	      });
	    }
	    upload();
	    
     }
           
     


  
  document.querySelector('#scanQRCode2').onclick = function () {
  
 		var $starUl=$('.attr-star ul');
  		var $star=$('.attr-star ul li');
  		var starArr=[];
		$starUl.each(function(i,item){
			starArr.push($(this).attr('data-star'));
			
			
		});
		document.forms['commentForm'].starValue.value = starArr;
   		document.forms['commentForm'].action = "saveComment";
   		document.forms['commentForm'].submit();

    
    
  };
  
 


});
		
		
		
		    </script>
		    
		    <meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
	</head>
	<body>
		<div class="content">
			<!--头部开始-->
			
			<!--头部结束-->
			<!--主要部分开始-->
			<form name="commentForm" method="post" >
			<input type="hidden" name="img">
			<input type="hidden" name="starValue">
			<input type="hidden" name="orderId" value = "${mallOrderProduct.id}">
			<input type="hidden" name="memberId" value = "${memberId}">
			<input type="hidden" name="productId" value = "${mallOrderProduct.productId}">
			<div class="content-body no-padding">
		
				<div class="goods-com">
					<div class="goods-pic clearfix">
						<img src="<%=request.getContextPath()%>/wechatImages/mall/${mallOrderProduct.productImg}" />
						<h3>${mallOrderProduct.productName} </h3>
						<p>￥${mallOrderProduct.price}</p>
					</div>
					
				</div>
				<div class="shop-com">
					
					<div class="attr-star clearfix">
						<label>商品评价</label>
						<ul data-star='5' class="clearfix">
							<li></li>
							<li></li>
							<li></li>
							<li></li>
							<li></li>
						</ul>
					</div>
					
					
				</div>
				<div class="goods-com">
					<div class="goods-pic clearfix">
						
						<textarea id="content" name="content" placeholder="请写下对宝贝的感受吧，对他人帮助很大哦"></textarea>
					</div>
					<div class="goods-pic-upload">
						<ul class="clearfix">		
							<li class="temp-img">
								<img src="images/goodsImg/02.jpg" />
								<i class="delete">×</i>
							</li>			
							<li class="btn-bg">
								<input type="button" id="scanQRCode1" />
							</li>
						</ul>
					</div>
					
				</div>
				
			</div>
			<!--主要部分结束-->
			
			
			<!--提交按钮开始-->
			<div class="control-bar">
				<input type="button" id="scanQRCode2" value="发布评论" />&nbsp;
				
			</div>
			</form>
			<!--提交按钮结束-->
			
			<!--底部菜单开始-->
			
			<!--底部菜单结束-->
		</div>
	</body>
	<script>
		$(function(){
			var $imgUl=$('.img-list');
			var $starUl=$('.attr-star ul');
			var $star=$('.attr-star ul li');
			
			lightStar($starUl);
			
			//获取星星数量
			function starNum(){
				var starArr=[];
				$starUl.each(function(i,item){
					starArr.push($(this).attr('data-star'));
				});
				document.forms['commentForm'].starValue.value = starArr;
				//alert('星级评价的数量:'+starArr);
			}
			starNum();
			
			$imgUl.on('click','li .delete',function(){
			
			document.forms['commentForm'].img.value = document.forms['commentForm'].img.value.replace($(this).parent().find("img").attr("src"),"");
			alert(document.forms['commentForm'].img.value);
			$(this).parent().remove();
			})
			
			$('.add-img').on('click',function(){
				$(this).before($('.temp-img').clone(true).removeClass('temp-img'));
			})
			
			$star.on('click',function(){
				var _index=$(this).index()+1;
				$(this).parent().attr('data-star',_index);
				lightStar($(this).parent());
				starNum();
			})
			
			
			//点亮星星
			function lightStar(obj){
				obj.each(function(){
					var _index=$(this).attr('data-star');
					for(var i=0;i<5;i++){
						if(i<_index){
							$(this).find('li').eq(i).addClass('acitve');
						}else{
							$(this).find('li').eq(i).removeClass('acitve');
						}
						
					}
				})
				
			}
		})
	</script>
</html>

</html>
