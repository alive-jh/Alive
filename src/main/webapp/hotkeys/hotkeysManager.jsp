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
var answerData;
var questionId;
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
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].groupName+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].groupCode+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].status+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].updateDate+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].insertDate+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data">分组编辑</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showAnswer('+i+')" data-toggle="modal" data-target="#answer-data">热词管理</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteQuestion('+i+')" data-toggle="modal" data-target="#answer-data">删除分组</button></td>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}


//更新界面的方法
function updateAnswerUI(){
	newcontent='';
	var answerInfoListTab = $('#answerInfoListTab');
	$("#answerInfoListTab tr:not(:has(th))").remove();
	for(var i=0;i<answerData.length;i++){
			newcontent = newcontent + '<tr>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+answerData[i].id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+answerData[i].answer+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="saveAnswer('+i+')" data-toggle="modal" data-target="#myModal-data">保存</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteAnswer('+i+')" data-toggle="modal" data-target="#answer-data">删除</button></td>';
			newcontent = newcontent + '</tr>';
	}
	answerInfoListTab.append(newcontent);
	
}

//初始化
$(document).ready( function () {
	
	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	updateUI();

});



function showAnswer(i){
	$("#myAnswerInfo").modal('show');
	var answerInfoListTab = $('#answerInfoListTab'); 
	questionId = jsonData[i].id;
	$.ajax({
		type: "POST",
		data:"questionId="+jsonData[i].id,
		dataType: "json",
		url: "<%=request.getContextPath() %>/api/getAnswerByQuestionId", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
		   $("#answerInfoListTab tr:not(:has(th))").remove();
		   answerInfoListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		},
		complete:function(){
			
		},
		success: function(data){
			answerData=data.data;
			var courseContent='';
			$("#coursesInfoListTab tr:not(:has(th))").remove();
			for(var i=0;i<answerData.length;i++){
					courseContent = courseContent + '<tr>';	
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+answerData[i].id+'</td>';
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+answerData[i].answer+'</td>';
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+answerData[i].answer+'</td>';
					courseContent = courseContent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="saveAnswer('+i+')" data-toggle="modal" data-target="#myModifyCourse">保存</button>';
					courseContent = courseContent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteAnswer('+i+')" data-toggle="modal" data-target="#myModifyCourse">删除</button></td>';
					courseContent = courseContent + '</tr>';
			}
			answerInfoListTab.append(courseContent);
		}
	});	
	
	
	
}

//预添加或者修改
function editData(index)
{	
	if(index == ''){
		$("#index").val("");
		$("#id").val("");
		$("#question").val("");

	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		$("#question").val(myData.question);

	}
}


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

//修改数据

function saveAnswer(index) {
     //隐藏modal
    
	var newAnswer = "";
    if(index == ''||typeof(variableName) == "undefined"){
		id = '';
		newAnswer=$("input[name='newAnswer']").val();
		newScore=$("input[name='newScore']").val();
	}else{
		id = answerData[index].id;
		newAnswer=answerData[index];
		newScore=answerData[index];
	}
	$.ajax({
			type: "POST",
			data:"id="+id+"&answer="+ newAnswer +"&questionId=" + questionId + "&score=" + newScore,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/saveHotKeys", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				answerData.push(data.data);
				  updateAnswerUI();
			}
		});	
}


function addAnswer() {
	newcontent='';
	var answerInfoListTab = $('#answerInfoListTab');
	$("#answerInfoListTab tr:not(:has(th))").remove();
	var newcontent = "";
	for(var i=0;i<answerData.length;i++){
			newcontent = newcontent + '<tr>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+answerData[i].id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+answerData[i].answer+'</td>';


			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="saveAnswer('+i+')" data-toggle="modal" data-target="#myModal-data">保存</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteAnswer('+i+')" data-toggle="modal" data-target="#answer-data">删除</button></td>';
			newcontent = newcontent + '</tr>';
	}
	
	newcontent = newcontent + '<tr>';	
	newcontent = newcontent + '<td nowrap="nowrap" align="left" >请输入内容(点击保存)</td>';
	newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input value="" name="newAnswer"></td>';
	newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input value="" name="newScore"></td>';
	newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="saveAnswer()" data-toggle="modal" data-target="#myModifyCourse">保存</button>';
	newcontent = newcontent + '</tr>';
	answerInfoListTab.append(newcontent);
}

function deleteAnswer(index){
	$.ajax({
			type: "POST",
			data:"id="+answerData[index].id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/deleteAnswerById", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
	              answerData.remove(index);
				  updateAnswerUI();
			}
		});	
}

function deleteQuestion(index){
	$.ajax({
			type: "POST",
			data:"id="+jsonData[index].id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/deleteQuestionById", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				jsonData.remove(index);
				updateUI();
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
			<li class="active">热词管理</li>
			<li class="active">分组管理</li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/question/quesionManage"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>分组名称</td>
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
					<button class="btn btn-purple ml10 mb10" onclick="editData()" data-toggle="modal" data-target="#myModal-data" value="添加">
					添加分组</button>
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
										<th width="100">分组ID</th>
										<th width="100">分组名称</th>
										<th width="100">分组CODE</th>
										<th width="100">状态</th>
										<th width="100">修改时间</th>
										<th width="100">插入时间</th>
										<th width="120">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./quesionManage"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form class="data" method="post" name="modifyData" id="modifyData"
	enctype="multipart/form-data" action="">
	<input type="hidden" name="index" id="index" value=""> 
	<input type="hidden" name="id" id="id" value=""> 
	<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:900px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">分组编辑</h4>
				</div>
				<div class="modal-body">
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">分组名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="question" id="question"
										type="text" data-rule="问题描述:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>


				</div>
				<div class="modal-body">
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">分组CODE</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="question" id="question"
										type="text" data-rule="问题描述:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>


				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="updateData()">保存</button>

					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>



<form width=200 height=300  method="post" name="answerInfo" id="answerInfo"
	enctype="multipart/form-data" action="">
	<input type="hidden" name="index" id="index" value=""> <input
		type="hidden" name="id" id="id" value=""> <input type="hidden"
		name="logo" id="logo" value="">
	<div class="modal fade" id="myAnswerInfo" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:auto;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">热词列表</h4>
					<button class="btn btn-purple ml10 mb10" onclick="addAnswer()" data-toggle="modal" value="添加">
					添加热词</button>
				</div>
				<div class="modal-body">
					
					<table id="answerInfoListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th width="100">热词ID</th>
										<th width="100">热词名称</th>
										<th width="100">热度值</th>
										<th width="120">操作</th>
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
</form>

