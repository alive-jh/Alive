<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>cepingManager.html</title>

<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

</head>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
var onum=0;
$(function() { 
	$("#addOption").click(function(){
		$("#option").append("选项文本:<input type='text' id='options["+onum+"].text' name='options["+onum+"].text'><br><br> 选项配图：<input type='text' id='options["+onum+"].picUrl' name='options["+onum+"].picUrl'> <br><br> 选项录音：<input type='text' id='options["+onum+"].soundUrl'name='options["+onum+"].soundUrl'> <br><br> 是否正确：<input type='text' id='options["+onum+"].isCorrect'name='options["+onum+"].isCorrect'><hr>"); 
		onum++;
	});
	
	$("#file1").change(function(){
		var formData = new FormData(); 
        formData.append('file',$("#file1")[0].files[0]);
        $.ajax({
                url:"<%=request.getContextPath()%>/file/file",
                async: false,
                type:"post",
                data:formData,
                processData:false,
                contentType:false,
                success:function(result){
                    $("#url").val(result.key);
                    alert("上传成功");
                },
                error:function(e){
                    alert("上传文件失败");
                }
            });
	});
	
	
	
});
	

</script>
<body>
	<h1>微信测评题目上传</h1>
	<div
		style="width:500px;height:800px; margin: 0 auto;background-color: yellow; float: left">
		<form name="form" id="form1"
			action="<%=request.getContextPath()%>/eval/saveQuestion"
			method="post">
			题目等级：<input type="text" id="sort" name="sort"><br>
			<br> 题目文本：<input type="text" id="text" name="text"> <br>
			<br> 题目配图：<input type="text" id="picUrl" name="picUrl">
			<br> 题目录音：<input type="text" id="soundUrl" name="soundUrl">
			<br> <br>
			<button type="button" id="addOption">添加题目选项</button>
			<div id="option"></div>
			<button type="submit" id="btn1" style="float: right;">提交题目</button>
		</form>
	</div>

	<div>
		文件地址:<input type="text" id="url" name="url" size="60"> <br></br>
		文件上传:<input type="file" id="file1" name="file1">
	</div>

</body>
</html>
