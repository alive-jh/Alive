<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html><!DOCTYPE html>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
		<title>借书车</title>
		
		<link rel="stylesheet" href="plug/css/jq-plug.css" />
		<link rel="stylesheet" href="lib/css/font-awesome.min.css" />
        <link rel="stylesheet" href="css/common.css" />
		<link rel="stylesheet" href="plug/css/swiper.css" />
		<link rel="stylesheet" href="css/index.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/bookList.css" />
		<link rel="stylesheet" href="css/shoppingCart.css" />
		<link rel="stylesheet" href="css/style.css" />
		<script src="lib/js/jquery-2.1.3.min.js"></script>
		<script src="lib/js/iscroll.js"></script>
		<script src="plug/js/jq-plug.js"></script>
		<script src="js/common.js"></script>

		
	</head>

	<script>
	function deleteShoppingCart()
	{
		
		
		
		if(document.forms['bookForm'].ids.value != '')
		{
			
			document.forms['bookForm'].ids.value = document.forms['bookForm'].ids.value.substring(0,document.forms['bookForm'].ids.value.length-1);

			document.forms['bookForm'].action ="<%=request.getContextPath()%>/book/deleteShoppingCart?memberId=${memberId}&ids="+document.forms['bookForm'].ids.value;

			document.forms['bookForm'].submit();
		}
		
		
	

	}

	function saveOrder()
	{

		document.forms['bookForm'].ids.value = '';
		var tempIds = '';
		$("input[name='goods-check']:checkbox").each(function() {
			if(this.checked == true)
			{
				
				if($(this).val()!= '')
				{
					
					document.forms['bookForm'].ids.value = document.forms['bookForm'].ids.value + "'"+ $(this).val()+"',";
					
					tempIds =tempIds+ $(this).val()+",";
				}
				
				
			
			}
		})
		if(document.forms['bookForm'].ids.value != '')
		{
			
			document.forms['bookForm'].ids.value = document.forms['bookForm'].ids.value.substring(0,document.forms['bookForm'].ids.value.length-1);
			tempIds = tempIds.substring(0,tempIds.length-1);
			var bookCodes='';
			var count = 0;
			var tempName = '';
			$.ajax({
				type: "POST",
				data:"",
				dataType: "json",
				url: '<%=request.getContextPath()%>/book/searchBookCount?ids='+document.forms['bookForm'].ids.value,
				context: document.body, 
				beforeSend:function(){
				},
				complete:function(){
				},
				success: function(data){
				
				
					for(var i=0;i<data.infoList.length;i++)
					{
						
						
						if(data.infoList[i].bookCode == undefined)
						{
							
							tempName = data.infoList[i].bookName;
							count++;
							
						}
						bookCodes = bookCodes + data.infoList[i].bookCode+",";
					}
					
					if(count ==0)
					{
						
						bookCodes = bookCodes.substring(0,bookCodes.length-1);
						document.forms['bookForm'].action ="<%=request.getContextPath()%>/book/toSaveBookOrder?memberId=${memberId}&ids="+tempIds+"&bookCodes="+bookCodes+"&cateIds="+document.forms['bookForm'].ids.value;
						document.forms['bookForm'].submit();
					}
					else
					{
						alert('书籍:<'+tempName+'>库存不足,请选择其他书籍!');
						return;
					}
				
				}
			});



		}
		else
		{
			alert('请至少选择一条记录!');
		}

		
		

	}
	</script>
	<body>
		<!--search-->
		<!--<div class="search-box fixed-bolck">
			<input type="search" placeholder="自出版/图书/杂志"/>
			<button class="book-type-btn"></button>
		</div>-->
        <!--
		<div class="search-box" style="position: fixed;">
            <button class="head-left-btn" id="cancelBtn"></button>
            <h1>购物车</h1>
            <button class="head-right-btn" id="orderEdit">编辑</button>
		</div>
		-->
		
		
		<!--book-list-->
		<form name="bookForm" method="post">
			<input type="hidden" name="ids">
		</form>
        <div class="other-box like-box" id="bookList" style="padding-top:46px;">
            <div class="swiper-object">
                <ul class="book-list clearfix">

					
					<c:forEach items="${bookList}" var="tempBook">
                    <li class="list-info" >
                        <a href="javascript:void(0);" class="clearfix">
                           
                             
                            <img src="${tempBook.cover}"/>
                            <div class="info-box">
                                <h3 class="book-name">${tempBook.bName}</h3>
                                
                                <p class="author-name">${tempBook.author}</p>
                                <!--<div class="book-star clearfix">
                                    <ul class="clearfix">
                                        <li class="active"></li>
                                        <li class="active"></li>
                                        <li class="active"></li>
                                        <li class="active"></li>
                                        <li></li>
                                    </ul>
                                    <b>7.6</b>
                                    <span>(201评价)</span>
                                </div>-->
                                <div class="book-short"></div>
                                

                            </div>
                        </a>
                    </li>
					
					</c:forEach>

                </ul>

            </div>

        </div>
        <!--book-list-end-->

        <!--submit-group-->
       
        <!--submit-group-end-->

		
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
                    <a href="<%=request.getContextPath()%>/book/memberBookOrderManager?memberId=${memberId}">


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


</html>
