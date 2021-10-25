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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">

<script>
    //html存储全局变量
    var newcontent = '';

    //json字符串转json数组
    var jsonData;
    
    //更新总记录
    function updateTotalCount(count) {
        //更新总记录
        var total = $("font#fPageTotal").text();
        total = eval(total) + count;
        $("font#fPageTotal").text(total);
    }

    //更新界面的方法
    function updateUI() {
         newcontent = '';
         var dataListTab = $('#dataListTab');
         $("#dataListTab tr:not(:has(th))").remove();
	     for(var i=0;i<jsonData.length;i++){
	     	newcontent = newcontent + '<tr>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + jsonData[i].id + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + jsonData[i].name + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="addDeviceNo('+i+')" ><i class="icon-edit bigger-120"></i>添加流水号</button>&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showDeviceList('+i+')">查看机器使用情况</button></td>';
	        newcontent = newcontent + '</tr>';
			


		
	      }
	      dataListTab.append(newcontent);
	      
	}

    //初始化
    $(document).ready(function () {
		jsonData=$.parseJSON($("#myJson").text()).data;
		
        //更新界面
        updateUI();

    });


function showDeviceList(index){
	var categoryId = jsonData[index].id;
	$("#deviceList").modal("show");
	var formData = new FormData();
	formData.append("categoryId",categoryId);
	$.ajax({
         url: '<%=request.getContextPath() %>/api/getDeviceListByCategory' ,
         type: 'POST',
         data:formData,
         dataType: "json",  
         async: false,
         contentType: false,
         processData: false,
         beforeSend:function(){
	   //这里是开始执行方法
	  },
         success: function (returndata) {
         	var deviceList = returndata.data;
			$("#classDeviceListTab tr:not(:has(th))").remove();
			var newcontent = "";
			for(var i=0;i<deviceList.length;i++){
				newcontent = newcontent + '<tr>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+deviceList[i].deviceNo+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+deviceList[i].epalId+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+deviceList[i].onTime+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+deviceList[i].bindMobile+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+deviceList[i].classGrades+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="showStudentClassRecord('+deviceList[i].epalId+')" ><i class="icon-edit bigger-120"></i>查看上课情况</button>';
				newcontent = newcontent + '</td>';
				newcontent = newcontent + '</tr>';	
			}
			$("#classDeviceListTab").append(newcontent);
		  
         },
         error: function (returndata) {
         
         }
    });
	
}
    
function editData(index)
{	
	if(index.length == '0'){
		$("#index").val("");
		$("#id").val("");
		$("#categoryName").val("");


	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		$("#categoryName").val(myData.name);
	}
}


//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/saveDeviceCategory' ,
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
				jsonData.push(returndata.data); 
				updateTotalCount(1);
			  //更新界面
			  updateUI();
          },
          error: function (returndata) {
          
          }
     });
}

function addDeviceNo(index){
	var deviceCategoryId = jsonData[index].id;
	$("#addDeviceNo").modal("show");
	$("#categoryId").val(deviceCategoryId);

}
function saveDeviceNo(){
	var categoryId = $("#categoryId").val();
	var deviceNoList = $("#deviceNoList").val();
	var formData = new FormData();
	formData.append("categoryId",categoryId);
	formData.append("deviceNoList",deviceNoList);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/saveDeviceNoToCategory' ,
          type: 'POST',
          data: formData,
          
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		   var dataListTab = $('#dataListTab'); 
		  },
          success: function (returndata) {
			$("#addDeviceNo").modal("hide");
			updateUI();
          },
          error: function (returndata) {
          
          }
     });

}
</script>

<div class="main-content">
	<div id="myJson" style="display: none;">${jsonData}</div>
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed');
			} catch (e) {
			}
		</script>

		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active">设备管理</li>
			<li class="active">机器分类管理</li>
		</ul>
		<!-- .breadcrumb -->

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/device/categoryManager"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>分类名称</td>
						<td><input type="text" name="searchStr" value="${queryDto.searchStr}">
						</td>
					</tr>


				</table>

				<div class="btn-group col-md-12">
					<button class="btn btn-purple ml10 mb10" onclick="editData('')" data-toggle="modal" data-target="#myModal-data" value="添加">
					添加分组</button>
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
										<th width="5%">序号</th>
										<th width="15%">分组名称</th>
										<th width="20%">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./categoryManager"
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
					<h4 class="modal-title">添加分组</h4>
				</div>
				<div class="modal-body">
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">分组名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="categoryName" id="categoryName"
										type="text" data-rule="IM号:required;"
										style="max-width:650px;width:100%;"/>
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

<!-- 添加机器人流水号 -->
<div class="modal fade" id="addDeviceNo" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加流水号</h4>
			</div>
			<div class="modal-body">
				<input type="hidden" name="categoryId" id="categoryId" value=""> 
				<div id="divInput" class="form-group" style="height:300px;">
					<label class="col-sm-2 control-label no-padding-right text-right">流水号</label>
					<div class="col-sm-8">
						<textarea id="deviceNoList" style="max-width:100%;width:100%;height:300px;"></textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					onclick="saveDeviceNo()">保存</button>

				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="deviceList" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">机器人列表</h4>
			</div>
			<div class="modal-body">
				<table id="classDeviceListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
					<thead>
						<tr>
							<th width="100">流水号码</th>
							<th width="100">机器人EpalID</th>
							<th width="100">最后开机时间</th>
							<th width="100">当前绑定账号</th>
							<th width="100">当前加入班级</th>
							<th width="100">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
