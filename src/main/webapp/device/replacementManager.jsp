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
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + jsonData[i].lastEpalId + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + jsonData[i].currentEpalId + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + jsonData[i].remark + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + jsonData[i].createTime + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="Synchronous('+i+')" data-toggle="modal" data-target="#Synchronous-data" value="同步数据">同步数据</button></td>';
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


	function Synchronous(index){
		var oldEpalId = jsonData[index].lastEpalId;
		var newEpalId = jsonData[index].currentEpalId;
		$("#oldEpalId").val(oldEpalId);
		$("#newEpalId").val(newEpalId);
	}
    
function editData(index)
{	
	if(index.length == '0'){
		$("#index").val("");
		$("#id").val("");
		$("#lastEpalId").val("");
		$("#currentEpalId").val("");
		$("#remark").val("");

	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		$("#lastEpalId").val(myData.lastEpalId);
		$("#currentEpalId").val(myData.currentEpalId);
		$("#remark").val(myData.remark);
	}
}


//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/saveReplacement' ,
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
function checkEpalId(){
	var oldEpalId = $("#lastEpalId").val();
	var formData = new FormData();
	formData.append("epalId",oldEpalId);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/checkDeviceExist' ,
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
          	  var status = returndata.data.status;
          	  if(status==1){
          	  	var newEpalId = getNewEpalId();
          	  	$("#currentEpalId").val(newEpalId);
          	  	
          	  }else{
          	  	alert('请输入正确账号！');
          	  }
          },
          error: function (returndata) {
          
          }
     });

}
function getNewEpalId(){
	var oldEpalId = $("#lastEpalId").val();
	var newEpalId;
	var sear = "z"
	if(oldEpalId.indexOf(sear)!=-1){
		var arr = oldEpalId.split('z');

		var temp = parseInt(arr[1]) + 1;
		
		newEpalId = arr[0] + "z" + temp;
	}else{
		newEpalId = oldEpalId + "z1"; 
	}
	
	return newEpalId;
	

}
function saveSynchronousData(){
	 $("#Synchronous-data").modal("hide");
     var formData = new FormData($("#SynchronousData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/saveSynchronousData' ,
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
          	  alert("同步成功！");
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
			<li class="active">换机管理</li>
		</ul>
		<!-- .breadcrumb -->

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/device/getDevicesInfo"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>设备唯一标示号</td>
						<td><input type="text" name="searchStr" value="${queryDto.searchStr}">
						</td>
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
					<button class="btn btn-purple ml10 mb10" onclick="editData('')" data-toggle="modal" data-target="#myModal-data" value="添加">
					添加换机</button>
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
										<th width="15%">旧IM号</th>
										<th width="15%">新IM号</th>
										<th width="25%">备注</th>
										<th width="20%">更换时间</th>
										<th width="20%">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./replacementManager"
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
					<h4 class="modal-title">添加换机</h4>
				</div>
				<div class="modal-body">
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">旧IM号码</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="lastEpalId" id="lastEpalId"
										type="text" data-rule="IM号:required;"
										style="max-width:650px;width:100%;" onblur="checkEpalId()"/>
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">新IM号</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="currentEpalId" id="currentEpalId"
										type="text" data-rule="IM号:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">备注</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="remark" id="remark"
										type="text" data-rule="备注:required;"
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

<form class="data" method="post" name="SynchronousData" id="SynchronousData"
	enctype="multipart/form-data" action="">
	<div class="modal fade" id="Synchronous-data" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width:900px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">同步数据</h4>
				</div>
				<div class="modal-body">
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">旧机器人</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="oldEpalId" id="oldEpalId"
										type="text" data-rule="IM号:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
	
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">新机器人</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="newEpalId" id="newEpalId"
										type="text" data-rule="IM号:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">同步类型</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<label><input name="online" type="checkbox" value="online" />在线课堂学习记录</label> 
								<label><input name="deviceBookStack" type="checkbox" value="deviceBookStack" />机器人书库</label> 
							</div>
						</div>
					</div>
				</div>
	
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="saveSynchronousData()">保存</button>
	
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>