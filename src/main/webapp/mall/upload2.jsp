<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<base href="<%=basePath%>">
		<link href="<%=basePath%>uploadify2/uploadify.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	</head>
<body>
<div id="fileQueue"></div>
	<input type="file" name="uploadify" id="uploadify" />
    <p>
        <a href="javascript: jQuery('#uploadify').uploadifyUpload()">开始上传</a>&nbsp;
		<a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消所有上传</a>
	</p>
</body>
<script type="text/javascript">
	//官方网址：http://www.uploadify.com/
	$(document).ready(function(){
		//$("#uploadify").uploadifySettings('scriptData',	{'name':'liudong','age':22});
		$("#uploadify").uploadify({
			'uploader'	:	"<%=basePath%>js/uploadify/uploadify.swf",
			'script'    :	"<%=basePath%>/UploadPhotoServlet",
			'cancelImg' :	"<%=basePath%>js/uploadify/cancel.png",
			'folder'	:	"uploads",//上传文件存放的路径,请保持与uploadFile.jsp中PATH的值相同
			'queueId'	:	"fileQueue",
			'queueSizeLimit'	:	10,//限制上传文件的数量
			'fileExt'	:	"*.rar,*.zip,*.png,*.txt",
			//'fileDesc'	:	"RAR *.rar",//限制文件类型
			'auto'		:	false,
			'multi'		:	true,//是否允许多文件上传
			'simUploadLimit':	2,//同时运行上传的进程数量
			'buttonText':	"files",
			//'scriptData':	{'name':'liudong','age':22},//这个参数用于传递用户自己的参数，此时'method' 必须设置为GET, 后台可以用request.getParameter('name')获取名字的值
			'method'	:	"GET"
		});
		
		//$("#uploadify").uploadifySettings();
	});
</script>
</html>