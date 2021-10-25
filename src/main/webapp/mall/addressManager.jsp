<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
		<title>管理收货地址</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/base.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/commen.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/wapcss/addrManager.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.1.min.js"></script>
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

			function toUserAddress()
			{
					document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/getAddress?memberId=${memberId}&searchType=${searchType}";
					document.forms['mallForm'].submit();
			}
			function checkUserAddress(id)
			{
				
					document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/getAddress?memberId=${memberId}&productId=${productId}&userAddressId="+id+"&searchType=${searchType}";
					document.forms['mallForm'].submit();
			}

			function delUserAddress()
			{
			
				var ids = '';
				$("input[name='checkId']:checkbox").each(function() {
					 
				if(this.checked == true)
				{
					ids = ids + $(this).val()+',';
				}
				})
				if(ids =='')
				{
					alert('请至少选择一条记录!');
					return;
				}
				else
				{
					ids = ids.substring(0,ids.length -1);
				}
				

				document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/deleteAddress?userAddressId="+ids+"&memberId=${memberId}&searchType=${searchType}";
				document.forms['mallForm'].submit();
			}
			function editUserAddress(id)
			{
			
				document.forms['mallForm'].action = "<%=request.getContextPath()%>/member/getAddress?searchType=${searchType}&type=edit&userAddressId="+id;
				document.forms['mallForm'].submit();
			}

	

		function checkAll()
		{
			
			 var checkAllId = document.getElementById("checkAllId");
			 if(checkAllId.checked == true)
			 {
				$("input[name='checkId']:checkbox").each(function() {
					 
				this.checked = true;  })
										
			}
			else
			{
				$("input[name='checkId']:checkbox").each(function() { 
					
				this.checked = false;  }) 
			}
		}


		    </script><meta name="viewport" content="width=device-width, user-scalable=no, target-densitydpi=medium-dpi">
			
	
		<!-- 屏幕的宽度设置 -->
		
	</head>
	<body>
	<form name="mallForm" method="post" >
		<div class="content">
			<!--头部开始-->
		
			<!--头部结束-->
			<!--主要部分开始-->
			<div class="content-body" id="checkAllBox">
				<div class="addr-list">
					<ul>
					<c:forEach items="${tempList}" var="temp"> 
					<c:if test="${temp.status ==0}"><li class="clearfix default"></c:if>
					<c:if test="${temp.status ==1}"><li class="clearfix"></c:if>
					
						
							<div class="addr-left">
								<input type="checkbox" id= "checkId" value="${temp.id}" name="checkId"/>
							</div>
							<div class="addr-center" onclick="checkUserAddress('${temp.id}')">
								<h3><span class="addr-name">${temp.userName}</span><span class="addr-tel">${temp.mobile}</span></h3>
								<p>${temp.area}${temp.address}</p>
							</div>
							<div class="addr-right">
								 <a href="javascript:editUserAddress('${temp.id}')" class="edit-icon"></a>
						
							</div>
						</li>
						
					</c:forEach>   
					</ul>
				</div>
				
				<div class="control-bar fixed-block">
					<label class="check-all left"><input type="checkbox" id= "checkAllId" onclick="checkAll()" name="check-single" class="all"/>全选</label>
					<span><a href="javascript:toUserAddress()" class="add-icon"></a></span>
					<span class="right" id="delete-btn"><a href="javascript:delUserAddress()" class="delete-icon"></a></span>
				</div>
				
			</div>
			<!--主要部分结束-->
			<!--底部菜单开始-->
			<!--<div class="footer-nav">
				<ul class="clearfix fixed-block">
					<li>
						<a href="index.html">
							<span></span>
							<p>首页</p>
						</a>
					</li>
					<li>
						<a href="order.html">
							<span></span>
							<p>订单</p>
						</a>
					</li>
					<li7>
						<a href="me.html">
							<span></span>
							<p>我的</p>
						</a>
					</li>
					<li>
						<a href="more.html">
							<span></span>
							<p>更多</p>
						</a>
					</li>
				</ul>
			</div>-->
			<!--底部菜单结束-->
		</div>
		</form>
	</body>
	
</html>
