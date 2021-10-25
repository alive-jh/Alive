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
var shipmentList;
var currentAgentId;

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
			var count = i + 1;
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+count+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].account+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].name+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].email+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].mobile+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showShipment('+i+')" data-toggle="modal" data-target="#answer-data">出货单管理</button>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}


//更新界面的方法
function updateShipmentUI(){
	newcontent='';
	$("#shipmentListTab tr:not(:has(th))").remove();
	for(var i=0;i<shipmentList.length;i++){
			newcontent = newcontent + '<tr>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentList[i].id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentList[i].name+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentList[i].status+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="showDetail('+i+')">查看详细</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteAnswer('+i+')" data-toggle="modal" data-target="#answer-data">删除</button></td>';
			newcontent = newcontent + '</tr>';
	}
	$("#shipmentListTab").append(newcontent);
	
}

var shipmentDetailList;
//更新出货单详细页面
function updateShipmentDetailUI(){
	newcontent='';
	$("#shipmentDetailListTab tr:not(:has(th))").remove();
	for(var i=0;i<shipmentDetailList.length;i++){
			newcontent = newcontent + '<tr>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentDetailList[i].deviceNo+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentDetailList[i].epalId+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentDetailList[i].studentName+'</td>';
			if(shipmentDetailList[i].classGradesName == '没有成为加入班级'){
				newcontent = newcontent + '<td nowrap="nowrap" align="left" style="color:red;">'+shipmentDetailList[i].classGradesName+'</td>';
			}else{
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentDetailList[i].classGradesName+'</td>';
			}
			
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentDetailList[i].createTime+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentDetailList[i].activeTime+'</td>';
			if(shipmentDetailList[i].friendId=='没有绑定'){
				newcontent = newcontent + '<td nowrap="nowrap" align="left" style="color:red;">'+shipmentDetailList[i].friendId+'</td>';
			}else{
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentDetailList[i].friendId+'</td>';
			}
			if(shipmentDetailList[i].countTime=='0'){
				newcontent = newcontent + '<td nowrap="nowrap" align="left" style="color:red;">'+parseInt(shipmentDetailList[i].countTime)*2+'</td>';
			}else{
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentDetailList[i].countTime+'</td>';
			}
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+shipmentDetailList[i].bindTime+'</td>';
			newcontent = newcontent + '</tr>';
	}
	$("#shipmentDetailListTab").append(newcontent);
	
}

var shipmentId;
function showDetail(index){
	shipmentId = shipmentList[index].id;
	$("#shipment-detail").modal("show");
	$.ajax({
		type: "POST",
		data:"shipmentId="+shipmentId,
		dataType: "json",
		url: "<%=request.getContextPath() %>/api/getShipmentDetailList", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
		   $("#shipmentDetailListTab tr:not(:has(th))").remove();
		   $("#shipmentDetailListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		},
		complete:function(){
			
		},
		success: function(data){
			shipmentDetailList = data.data.items;
			updateShipmentDetailUI();
		}
	});	

}
//初始化
$(document).ready( function () {
	
	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	updateUI();

});



function showShipment(i){
	$("#shipment-modal").modal('show');
	agentId = jsonData[i].id;
	currentAgentId = agentId;
	$.ajax({
		type: "POST",
		data:"agentId="+jsonData[i].id,
		dataType: "json",
		url: "<%=request.getContextPath() %>/api/getBillOfSalesByAgentId", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
		   $("#shipmentListTab tr:not(:has(th))").remove();
		   $("#shipmentListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		},
		complete:function(){
			
		},
		success: function(data){
			shipmentList = data.data.items;
			updateShipmentUI();
		}
	});	
	
	
	
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

//保存出货单信息

function saveShipment() {
     //隐藏modal
	var shipmentName = $("#shipmentName").val();
	$.ajax({
			type: "POST",
			data:"agentId="+currentAgentId+"&name="+ shipmentName,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/createBillOfSales", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				$("#addShipment-modal").modal("hide");
			}
		});	
}


function addShipment() {
	$("#addShipment-modal").modal("show");
}

function updateShipmentStatus(index){
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


function addShipmentRecord(){
	$("#addShipmentRecord").modal("show");
	$("#shipmentRecord").val("");

}
function saveShipmentRecord(){
	$("#addShipmentRecord").modal("hide");
	var shipmentRecord = $("#shipmentRecord").val();
	var formData = new FormData();
	formData.append("deviceNo",shipmentRecord);
	formData.append("agentId",currentAgentId);
	formData.append("billOfSalesId",shipmentId);
	$.ajax({
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
		url: "<%=request.getContextPath() %>/api/agentShipment", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
		},
		complete:function(){
			
		},
		success: function(data){

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
			<li class="active">出货单管理</li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/agent/shipmentManager"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>代理商名字</td>
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
					添加</button>
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
										<th width="100">序号</th>
										<th width="100">账号</th>
										<th width="120">名字</th>
										<th width="120">邮箱</th>
										<th width="120">电话号码</th>
										<th width="120">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./shipmentManager"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 查看出货单弹窗 -->
<div class="modal fade" id="shipment-modal" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:auto;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">出货单列表</h4>
				<button class="btn btn-purple ml10 mb10" onclick="addShipment()" data-toggle="modal" value="添加">
				添加</button>
			</div>
			<div class="modal-body">
				
				<table id="shipmentListTab"
							class="table table-striped table-bordered table-hover"
							style="text-align:left;">
							<thead>
								<tr>
									<th width="100">ID</th>
									<th width="100">名称</th>
									<th width="100">状态</th>
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

<!-- 添加出货单弹窗 -->
<div class="modal fade" id="addShipment-modal" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:40%;height: 800px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加出货单</h4>
			</div>
			<div class="modal-body">
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">名称</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="shipmentName" id="shipmentName"
									type="text" data-rule="出货单名称:required;"
									style="max-width:650px;width:100%;" />
							</div>
						</div>
					</div>
				</div>
			
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" onclick="saveShipment()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>


<!-- 查看出货单详情弹窗 -->
<div class="modal fade" id="shipment-detail" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:auto;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">出货单详情</h4>
				<button class="btn btn-purple ml10 mb10" onclick="addShipmentRecord()" data-toggle="modal" value="添加">
				添加</button>
			</div>
			<div class="modal-body">
				
				<table id="shipmentDetailListTab"
							class="table table-striped table-bordered table-hover"
							style="text-align:left;">
							<thead>
								<tr>
									<th width="100">设备号</th>
									<th width="100">机器人账号</th>
									<th width="100">学生名字</th>
									<th width="100">所加班级</th>
									<th width="100">出货时间</th>
									<th width="120">最后开机时间</th>
									<th width="120">联系人</th>
									<th width="120">最近一周开机时间(单位分钟)</th>
									<th width="120">最新绑定时间</th>
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


<!-- 添加出货单记录弹窗 -->
<div class="modal fade" id="addShipmentRecord" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:40%;height: 800px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加出货单记录</h4>
			</div>
			<div class="modal-body">
				<div id="divInput" class="form-group" style="min-height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">出货记录</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<textarea rows="30" cols="30" name="shipmentRecord" id="shipmentRecord"></textarea>
							</div>
						</div>
					</div>
				</div>
			
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" onclick="saveShipmentRecord()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>



