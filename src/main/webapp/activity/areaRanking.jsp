<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>排行榜</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/activity/css/video/vote_ranking.css">
  </head>
  
  <body>
     <div class="container-fluidcontainer-fluid">
        <div class="header1">
            <div class="vote_info">
                <ul class='inline'>
                    <li>参与学生<BR><span id='wxallstunum'>${studentNum }</span></li>
                    <li class='left-border'>累计投票<BR><span id='wxallvotenum'>${voteNum }</span></li>
                    <li class='left-border'>访问次数<BR><span id='wxallviewnum'>${accessNUm }</span></li>
                </ul>
            </div>
            <div class="header_text">
                <span>活动截止时间：2017-10-18</span>
            </div>
        </div>
        <div class="ranking_data">
            <h4>${area }地区视频排行榜</h4>
            <div class="ranking_title1">
                <div class="d1"><span>名次</span></div>
                <div class="d2"><span>选手</span></div>
                <div class="d3"><span>总票数</span></div>
            </div>
            <c:forEach items="${data}" var="item" varStatus="status">
            <div class="ranking_title">
                <div class="d1"><div class="num"><span>${status.count }</span></div></div>
                <div class="d2"><a href="../api/getAllVideoCompetitionByVerifSuccess2?vid=${item[0] }"><span class="stuInfo">${item[0] }.${item[16] }${item[15] }</span></a></div>
                <div class="d3"><span>${item[12] }</span></div>
            </div>
            </c:forEach>
            <div class="ranking_title2">
            </div>
        </div>
    </div>
  </body>
</html>
