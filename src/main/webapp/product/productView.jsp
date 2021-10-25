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

	document.forms['productForm'].action = "<%=request.getContextPath() %>/member/addProduct?productId=${product[0]}&memberId=${memberId}&memberType=${memberType}";
	document.forms['productForm'].submit();
}
</script>
<body style="padding:0;margin:0;">
	编号:${product[1]}<br/>
	名称:${product[3]}<br/>
	型号:${product[4]}<br/>
	功率:${product[5]}<br/>
	功率因数:${product[6]}<br/>
	色温:${product[7]}<br/>
	显指:${product[8]}<br/>
	调光:${product[9]}<br/>
	光通量:${product[10]}<br/>
	生产日期:${product[2]}<br/>
	产品描述:${product[11]}<br/>
	产品特点:${product[12]}<br/>

<form name="productForm" method="post">
<input type="hidden" name ="memberId"  value="${memberId}">
<input type="hidden" name ="memberType"  value="${memberType}">


<c:if test="${memberId != null}">
	<c:if test="${memberType != null}">
		<c:if test="${memberType ==0}">
			<c:if test="${product[13] ==0}"><input type="button" onclick="updateProduct()"value="点击绑定"></c:if>
		</c:if>
		<c:if test="${memberType ==1}">
			<c:if test="${product[14] ==0}"><input type="button" onclick="updateProduct()"value="点击绑定"></c:if>
		</c:if>
	</c:if>
</c:if>
</form>	
</body>
</html>