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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">


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
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].classRoomId+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].classRoomName+'</td>';

			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].teacherName+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img src="'+jsonData[i].cover+'" width="50" height="50" /></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].summary+'</td>';
			//newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].status+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="labelManager('+i+')">标签管理</button>';
			//newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showClassRoomDetail('+i+')" data-toggle="modal" data-target="#answer-data">详情</button>';
			newcontent = newcontent + '&nbsp;&nbsp;</td>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}


function showClassRoomDetail(index){
	$("#classScriptList").modal("show");
	var teacherId = jsonData[index].teacherId;
     var formData = new FormData();
     formData.append("teacherId",teacherId);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/getClassScriptListBy' ,
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
          	$("#classRoomScriptListTab tr:not(:has(th))").remove();
          	var data = returndata.data.items;
          	var newcontent = "";
          	for(var i=0;i<data.length;i++){
     			newcontent = newcontent + '<tr>';	
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].id+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].classGradesName+'</td>';
	
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img src="'+data[i].cover+'" width="50" height="50" /></td></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].summary+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].createTime+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].auditingStatus+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].status+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data">编辑</button>';
				newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showClassGrades('+i+')" data-toggle="modal" data-target="#answer-data">班级管理</button>';
				newcontent = newcontent + '&nbsp;&nbsp;</td>';
				newcontent = newcontent + '</tr>';
          	}
          	$("#classGradesListTab").append(newcontent);
          },
          error: function (returndata) {
          
          }
     });

}


//初始化
$(document).ready( function () {
	
	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	updateUI();

});



//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/updateQuestionById' ,
          type: 'POST',
          data: formData,
          
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		   var dataListTab = $('#dataListTab'); 
		   $("#dataListTab tr:not(:has(th))").remove();
		   dataListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		  },
          success: function (returndata) {
              var index=$("#index").val();
			  if(index == ''){
					//更新数组数据
					//添加到最后
					jsonData.push(returndata.data); 
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
var currentClassRoomId;
function labelManager(index){
	$("#labelManager").modal("show");
	var classRoomId = jsonData[index].classRoomId;
	currentClassRoomId = classRoomId;
	$.ajax({
          url: '<%=request.getContextPath() %>/ajax/classRoom/getLabelListByClassRoomId' ,
          type: 'GET',
          data: 'classRoomId='+classRoomId,
          
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (returndata) {
          	$("#classRoomLabelListTab tr:not(:has(th))").remove();
          	var data = returndata.data;
          	var newcontent = "";
          	for(var i=0;i<data.length;i++){
     			newcontent = newcontent + '<tr>';	
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].lid+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].labelName+'</td>';
				if(data[i].isShow == 1){
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >显示</td>';
				}else{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >不显示</td>';
				}
				
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].status+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="deleteLabel('+data[i].id+')">删除</button>';
				newcontent = newcontent + '&nbsp;&nbsp;</td>';
				newcontent = newcontent + '</tr>';
          	}
          	$("#classRoomLabelListTab").append(newcontent);
          },
          error: function (returndata) {
          
          }
     });
}
function deleteLabel(id){
	$.ajax({
         url: '<%=request.getContextPath() %>/ajax/classRoom/deleteLabelFormClassRoom' ,
         type: 'GET',
         data: 'id='+ id,
         
         async: false,
         cache: false,
         contentType: false,
         processData: false,
         beforeSend:function(){
	   //这里是开始执行方法
	  },
         success: function (returndata) {
         
         },
         error: function (returndata) {
         
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
			<li class="active">加盟管理</li>
			<li class="active">课程管理</li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/agent/classRoomManager"
				style="float: left;width:100%">
				<table class="filterTable">
					<tr>
						<td>课程名字</td>
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
										<th width="100">课程ID</th>
										<th width="100">课程名字</th>
										<th width="100">老师名字</th>
										<th width="120">简图</th>
										<th width="120">简介</th>
										<th width="120">操作</th>
										
									<!-- <th width="120">班级状态</th><th width="120">操作</th> -->	
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./classRoomManager"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="addTeacher" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加老师</h4>
			</div>
			<div class="modal-body">
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">凡豆账号</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="account" id="account"
									type="text" data-rule="凡豆账号:required;"
									style="max-width:650px;width:80%;" />
									<button id="searchTeacher" onclick="searchTeacher()" class="btn btn-info" style="max-width:650px;width:20%;" >搜索</button>
							</div>
						</div>
					</div>
				</div>
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">会员ID</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="memberId" id="memberId"
									type="text" data-rule="凡豆会员ID:required;"
									style="max-width:650px;width:100%;" />
							</div>
						</div>
					</div>
				</div>
				
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">老师名字</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="teacherName" id="teacherName"
									type="text" data-rule=老师名字":required;"
									style="max-width:650px;width:100%;" />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					onclick="saveTeacher()">保存</button>

				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>



<div class="modal fade" id="classScriptList" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:90%;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">课程资源列表</h4>
			</div>
			<div class="modal-body">
			<table id="classRoomScriptListTab"
				class="table table-striped table-bordered table-hover"
				style="text-align:left;">
				<thead>
					<tr>
						<th width="100">类型</th>
						<th width="100">音频</th>
						<th width="100">排序</th>
						<th width="100">创建时间</th>
					</tr>
				</thead>
			
			</table>
				
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					onclick="updateData()">保存</button>

				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<!-- 标签管理弹窗 -->
<div class="modal fade" id="labelManager" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:90%;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">标签列表</h4>
				<button class="btn btn-purple ml10 mb10" onclick="addLabel()">新增 
				</button>
			</div>
			<div class="modal-body">
			<table id="classRoomLabelListTab"
				class="table table-striped table-bordered table-hover"
				style="text-align:left;">
				<thead>
					<tr>
						<th width="100">id</th>
						<th width="100">标签名称</th>
						<th width="100">是否显示</th>
						<th width="100">状态</th>
						<th width="100">操作</th>
					</tr>
				</thead>
			
			</table>
				
			</div>
		</div>
	</div>
</div>
<script>
function addLabel(){
	$("#addLabel").modal("show");
	$("#labelNameList tr:not(:has(th))").remove();
	$("#labelName").val(''); 
}
var currentLabelList;
function searchLabel(){
	var labelName = $("#labelName").val(); 
	$.ajax({
         url: '<%=request.getContextPath() %>/ajax/classRoom/searchLabelName' ,
         type: 'GET',
         data: 'labelName='+labelName,
         
         async: false,
         cache: false,
         contentType: false,
         processData: false,
         beforeSend:function(){
	   //这里是开始执行方法
	  },
         success: function (returndata) {
         	$("#labelNameList tr:not(:has(th))").remove();
         	var data = returndata.data;
         	currentLabelList = data;
         	var newcontent = "";
         	for(var i=0;i<data.length;i++){
    			newcontent = newcontent + '<tr>';	
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].lid+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].labelName+'</td>';
				if(data[i].isShow == 1){
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >显示</td>';
				}else{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >不显示</td>';
				}
				
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data[i].status+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="addLabelToClassRoom('+i+')" >添加</button>';
				
				newcontent = newcontent + '&nbsp;&nbsp;</td>';
				newcontent = newcontent + '</tr>';
         	}
         	$("#labelNameList").append(newcontent);
         },
         error: function (returndata) {
         
         }
    });
}

function addLabelToClassRoom(index){
	var labelId = 0
	var labelName = "";
	var formData = new FormData();

	if(index == -1){
		labelName = $("#labelName").val();
		labelId= 0
		formData.append("labelName",labelName);
		formData.append("classRoomId",currentClassRoomId);
		
	}else{
		labelName = currentLabelList[index].labelName;
		labelId = currentLabelList[index].lid;
		formData.append("labelName",labelName);
		formData.append("labelId",labelId);
		formData.append("classRoomId",currentClassRoomId);
	}
	var data={"labelName":labelName,"labelId":labelId,"classRoomId":currentClassRoomId}
	$("#addLabel").modal("hide");
	$.ajax({
         url: '<%=request.getContextPath() %>/ajax/classRoom/saveClassRoomLabel' ,
         type: 'GET',
		 data: 'labelName='+labelName+'&labelId='+labelId+'&classRoomId='+currentClassRoomId,  

         async: false,
         cache: false,
         contentType: false,
         processData: false,
         beforeSend:function(){
	   //这里是开始执行方法
	  },
         success: function (returndata) {
     }
    });
}
</script>

<!-- 标签添加弹窗 -->
<div class="modal fade" id="addLabel" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:40%;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">新增标签</h4>

			</div>
			<div class="modal-body">
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">标签名称</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="labelName" id="labelName"
									type="text" data-rule=标签名称":required;"
									style="max-width:650px;width:100%;" onkeydown="searchLabel()"/>
							</div>
						</div>
					</div>

				</div>	
				<button type="button" class="btn btn-primary"
					onclick="addLabelToClassRoom(-1)">新增保存</button>
				<table id="labelNameList"
					class="table table-striped table-bordered table-hover"
					style="text-align:left;">
					<thead>
						<tr>
							<th width="100">id</th>
							<th width="100">标签名称</th>
							<th width="100">是否显示</th>
							<th width="100">状态</th>
							<th width="100">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
