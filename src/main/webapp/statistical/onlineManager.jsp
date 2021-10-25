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
var answerData;
var currentChannelId;
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
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].className+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].teacherName+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+jsonData[i].cover+'" /></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].summary+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].createTime+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data">查看课程</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showStudentList('+jsonData[i].id+')"">已学</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="updateChannelInfo('+i+')">完成</button></td>';
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


//预添加或者修改
function editData(index)
{	
	if(index.length == '0'){
		$("#index").val("");
		$("#id").val("");
		$("#channelId").val("");
		$("#name").val("");
		$("#intro").val("");
		$("#image").val("");
		$("#fans").val("");
		$("#status").val("");
		$("#level").val("");
	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		$("#channelId").val(myData.channelId);
		$("#name").val(myData.name);
		$("#intro").val(myData.intro);
		$("#image").val(myData.image);
		$("#fans").val(myData.fans);
		$("#status").val(myData.status);
		$("#level").val(myData.level);
		$("#nextUpdateTime").val(myData.nextUpdateTime);
		$("#lastUpdateTime").val(myData.lastUpdateTime);
		$("#updateCycle").val(myData.updateCycle);
		alert(myData.updateCycle);

	}
}





//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveChannel' ,
          type: 'POST',
          data: formData,
          
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (returndata) {
        	  var data = returndata.data;
              var index=$("#index").val();
			  if(index == ''){
					//更新数组数据
					//添加到最后
					jsonData.push(data); 
					updateTotalCount(1);
				}else{
					//更新数组数据
					jsonData[index]=returndata.data;
			  }
			  //更新界面
			  updateUI();
          },
          error: function (returndata) {
          
          }
     });
}


function showStudentList(classId){
	$("#studentList").modal("show");
	$.ajax({
			type: "POST",
			data:"classId="+classId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/statistical/studentList", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				studentList = data.data;
				newcontent='';
				$("#studentListTab tr:not(:has(th))").remove();
				for(var i=0;i<studentList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+studentList[i].id+'</td>';
						newcontent = newcontent + '</tr>';
				}
				$("#studentListTab").append(newcontent);
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
			<li class="active"><a href="">在线课堂管理</a></li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/channel/channelManage"
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
			<div style="text-align:center;"><h4>最近七天统计</h4></div>
			<div class="" style="text-align:left;">
				<table id="totalTable" class="table" tyle="text-align:left;">
					<thead>
						<tr>
							<th width="30">日期</th>
							<th width="30">推送课堂数</th>
							<th width="100">计划上课人数</th>
							<th width="100">实际上课人数</th>
							<th width="100">到课率</th>
							<th width="100">完整记录</th>
							<th width="100">完整率</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td >2017-04-25</td>
							<td>10000</td>
							<td>8562</td>
							<td>600</td>
							<td>98%</td>
							<td>8966</td>
							<td>80%</td>
						</tr>
						<tr>
							<td >2017-04-24</td>
							<td>10000</td>
							<td>8562</td>
							<td>600</td>
							<td>98%</td>
							<td>8966</td>
							<td>80%</td>
						</tr>
						<tr>
							<td>2017-04-23</td>
							<td>1000</td>
							<td>5986</td>
							<td>8966</td>
							<td>80%</td>
							<td>8966</td>
							<td>80%</td>
						</tr>
					</tbody>
				</table>
			</div>
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="row">
					<div class="col-xs-12">
						<div class="table-responsive" style="text-align:right;">
							<div style="text-align:center;"><h4>课堂列表</h4></div>
							<table id="dataListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th width="30">课堂ID</th>
										<th width="100">课堂名称</th>
										<th width="100">老师</th>
										<th width="70">海报</th>
										<th width="200">简介</th>
										<th width="100">添加时间</th>
										<th width="100">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./onlineManager"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="studentList" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style=width:auto;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">学生列表</h4>
			</div>
			<div class="modal-body">
				
				<table id="studentListTab" name="studentListTab"
							class="table table-striped table-bordered table-hover"
							style="word-break:break-all">
							<thead>
								<tr>
									<th width="5%">机器人账号</th>
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

