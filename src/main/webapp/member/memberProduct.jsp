<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>中国好电工</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<script>

function updateProduct()
{
	documemt.forms['productForm'].action = "<%=request.getContextPath() %>/member/addProduct";
	documemt.forms['productForm'].submit();
}
</script>
<body style="padding:0;margin:0;">
	 这里是二维码产品列表!<ber/>
	 <table>
	 <c:forEach items="${resultPage.items}" var="product">
	 <tr>
	 <td>${product[1]}</td>
	  <td>${product[3]}</td>
	  <td>${product[4]}</td>
	  <td>${product[2]}</td>
	 <td><a href="#">查看详情</a></td>
	 </tr>	
	 
	 </c:forEach>
	</table>

</body>
</html>