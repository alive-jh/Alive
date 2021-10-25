<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>

<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/validator/local/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/jquery.base64.min.js"></script>
	
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/channel/base.js"></script>
	
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">

<style>
.gw_num{border: 1px solid #dbdbdb;width: 110px;line-height: 26px;overflow: hidden;}
.gw_num em{display: block;height: 26px;width: 26px;float: left;color: #7A7979;border-right: 1px solid #dbdbdb;text-align: center;cursor: pointer;}
.gw_num .num{display: block;float: left;text-align: center;width: 52px;font-style: normal;font-size: 14px;line-height: 24px;border: 0;}
.gw_num em.add{float: right;border-right: 0;border-left: 1px solid #dbdbdb;}
</style>
<script>

var newcontent = '';
var jsonData;

//更新总记录
function updateTotalCount(count){
	//更新总记录
	var total=$("font#fPageTotal").text();
	total=eval(total)+count;
	$("font#fPageTotal").text(total);
}


//更新界面的方法
function updateUI(){
	newcontent='';
	var dataListTab = $('#dataListTab');
	$("#dataListTab tr:not(:has(th))").remove();
	for(var i=0;i<jsonData.length;i++){

			newcontent = newcontent + '<tr>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].epalId+'</td>';
			if(jsonData[i].recordcount == null){
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >0</td>';
			}else{
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].recordcount+'</td>';
			}
			
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].createDate+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="showFriends('+i+')" data-toggle="modal" data-target="#myModal-data">查看好友</button>';
			
			newcontent = newcontent + '</td>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}

//初始化
$(document).ready( function () {
	
	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	updateUI();
});


//查看好友列表
function showFriends(index) {
     //隐藏modal
	 $("#myModal-data").modal("show");
	var epalId = jsonData[index].epalId;
	$.ajax({
			type: "GET",
			data:"epalId="+epalId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/searchDeviceRelations", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
				$("#soundListTab tr:not(:has(th))").remove();
		   		$("#soundListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			},
			complete:function(){
				
			},
			success: function(data){
				friendsList = data.data;
				var newcontent='';
				
				$("#friendsListTab tr:not(:has(th))").remove();
				for(var i=0;i<friendsList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+friendsList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+friendsList[i].friendId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+friendsList[i].friendName+'</td>';
						if(friendsList[i].role == "guardian"){
							newcontent = newcontent + '<td nowrap="nowrap" align="left" >监护人</td>';
						}else{
							newcontent = newcontent + '<td nowrap="nowrap" align="left" >好友</td>';	
						}
						
						if(friendsList[i].isbind == 1){
							newcontent = newcontent + '<td nowrap="nowrap" align="left" style="background:#008000;">当前绑定账号</td>';
						}else{
							newcontent = newcontent + '<td nowrap="nowrap" align="left" >其他</td>';	
						}
						
						newcontent = newcontent + '</tr>';
				}
				$("#friendsListTab").append(newcontent);
			}
	});	
}

</script>


<div class="main-content">
	<div id="myJson" style="display: none;">${jsonData}</div>
	<div class="breadcrumbs" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active"><a href="">统计管理</a></li>
			<li class="active"><a href="">60天课程管理</a></li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/statistical/sixtyDayCourseManager"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>机器人ID</td>
						<td><input type="text" name="searchStr"
							value="${queryDto.searchStr}"></td>
						<td>
							<div class="form-group col-lg-6" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right">创建日期</label>
								<div class="col-sm-9">
									<div class="input-medium" style="">
										<div class="input-group">
											<input class="input-medium date-picker" type="text"
												name="startDate" value="${queryDto.startDate}"
												data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
												class="input-group-addon"> <i class="icon-calendar"></i>
											</span> <span class="input-group-addon"
												style="border:none;background-color:transparent;"> <i>--</i>
											</span> <input class="input-medium date-picker" type="text"
												name="endDate" value="${queryDto.endDate}"
												data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
												class="input-group-addon"> <i class="icon-calendar"></i>
										</div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>

				<div class="btn-group col-md-12">
					<button type="submit" class="btn btn-purple ml10 mb10">
						查询 <i class="icon-search icon-on-right bigger-110"></i>
					</button>
					</div>

			</form>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="row">
					<div class="col-xs-12">
						<div class="table-responsive" style="text-align:right;">
							<table id="dataListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th width="30">序号</th>
										<th width="100">机器人账号</th>
										<th width="100">总计</th>
										<th width="100">添加时间</th>
										<th width="100">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./sixtyDayCourseManager"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style=width:auto;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">好友列表</h4>
			</div>
			<div class="modal-body">
				
				<table id="friendsListTab" name="soundsListTab"
							class="table table-striped table-bordered table-hover"
							style="word-break:break-all">
							<thead>
								<tr>
									<th width="5%">ID</th>
									<th width="5%">好友账号</th>
									<th width="5%">好友名称</th>
									<th width="15%">角色</th>
									<th width="10%">是否绑定</th>
								</tr>
							</thead>

				</table>
			
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

