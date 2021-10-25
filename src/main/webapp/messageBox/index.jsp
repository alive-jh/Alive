<%@ page language="java" import="java.util.*,com.wechat.entity.VideoAcitivity" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>教师信箱</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="<%=request.getContextPath() %>/js/jquery-2.1.1.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap337.min.js"></script>
    <link href="<%=request.getContextPath() %>/messageBox/css/index.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/messageBox/minirefresh/minirefresh.css" rel="stylesheet">
</head>
	<script type="text/javascript">
		function delMessage(messageId){
			$.ajax({
                type: "POST",
                url:"<%=request.getContextPath()%>/api/delMessage",
                dataType: "json",
                data:{messageId:messageId},
                async: false,
                error: function(request) {
                    alert("错误！！");
                },
                success: function(result) {
                	if(result.code==200){
                		alert("删除成功");
                		location.reload();
                	}
                	
                }
            });
		}
	</script>
<body>

	<div id="minirefresh" class="minirefresh-wrap">
	    <div class="minirefresh-scroll">
			<div class="container-fluidcontainer-fluid">
  		<div class="content">
  			<c:if test="${data==null }">
  				<div style="width: 100%;text-align: center;"><h5 style="margin: 0 auto">暂无学生邀请</h5></div>
  			</c:if>
  			<c:forEach items="${data }" var="comment">
  			
  				<c:if test="${comment[4]==1 }">
  					<div class="message" style="border: 1px solid #cac8c9">
		  				<div class="widget-title" style="background-color: #cac8c9;border: 1px solid #cac8c9">
		                            <h4><i class="glyphicon glyphicon-edit"></i>  邀请信息 (已评价) </h4>
		                             <span class="tools">
		                                <a class="icon-chevron-down" href="javascript:;"></a>
		                                <a class="icon-remove" href="javascript:;"></a>
		                             </span>
		                </div>
		                <div class="messageContent">
		                	<a href="<%=request.getContextPath()%>/api/gotoComment?vid=${comment[1] }&teacherid=${comment[2] }&mid=${comment[0] }">${comment[3] }</a>
		                	<p><span onclick="delMessage(${comment[0] })">删除</span> | ${fn:substring(comment[5], 0, 16)}</p>
		                </div>
  					</div>
  				</c:if>
  			
  				<c:if test="${comment[4]==0 }">
  					<div class="message">
		  				<div class="widget-title">
		                            <h4><i class="glyphicon glyphicon-edit"></i>  邀请信息 </h4>
		                             <span class="tools">
		                                <a class="icon-chevron-down" href="javascript:;"></a>
		                                <a class="icon-remove" href="javascript:;"></a>
		                             </span>
		                </div>
		                <div class="messageContent">
		                	<a href="<%=request.getContextPath()%>/api/gotoComment?vid=${comment[1] }&teacherid=${comment[2] }&mid=${comment[0] }">${comment[3] }</a>
		                	<p><span onclick="delMessage(${comment[0] })">删除</span> | ${fn:substring(comment[5], 0, 16)}</p>
		                </div>
  					</div>
  				</c:if>
  			</c:forEach>
  		</div>
	</div>
	    </div>
	</div>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/messageBox/minirefresh/minirefresh.js"></script>
	<script type="text/javascript">
	
		// 引入任何一个主题后，都会有一个 MiniRefresh 全局变量
		var miniRefresh = new MiniRefresh({
		    container: '#minirefresh',
		    down: {
		        callback: function() {
		            // 下拉事件
				
		            miniRefresh.endDownLoading();
		            window.location.reload();
		        }
		    },
		    up: {
	
		        callback: function() {
		            // 上拉事件
		            // 注意，由于默认情况是开启满屏自动加载的，所以请求失败时，请务必endUpLoading(true)，防止无限请求
		            miniRefresh.endUpLoading(true);
		        }
		    }
		});
	</script>
</body>
</html>