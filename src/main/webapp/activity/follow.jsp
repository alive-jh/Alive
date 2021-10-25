<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>投票成功</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    
    <style type="text/css">
body{
    background-image: url("<%=request.getContextPath()%>/activity/img/bg_text.png");
    background-size: 100%;
    background-repeat: no-repeat;
}
.img{
    width: 100%;
    text-align: center;
    margin-top: 130px;
    height: 380px;
}
.img p{
    font-size: 15px;
    font-weight: 500;
    color: orange;
}
.text{
    width: 100%;
    margin-top: 15px;
    text-align: center;
}
.btn{
    width: 100%;
    margin-top: 10px;
    text-align: center;
}
.btn a{
    width: 50%;
}

</style>
</head>
<body>
    <div class="container-fluidcontainer-fluid">
        <div class="img">
           
        </div>

        <div class="btn">
            <a class="btn btn-warning" href="https://mp.weixin.qq.com/s/EDJt0rNXR_OgWabAbpOjtw">好的，我知道了</a>
        </div>
    </div>
</body>
</html>