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
    var jsonData = $.parseJSON('${jsonData}').data;


	var epalId = "";
    //更新总记录
    
    var channelList;
    
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
	        var device=jsonData[i];
	        
	     	newcontent = newcontent + '<tr>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + device.id + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + device.deviceNo + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + device.epalId + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + device.sn + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >******</td>';
	       	newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + device.nickName + '</td>';
	        newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
	        newcontent = newcontent + '<button class="btn btn-xs btn-info"  onclick="editDeviceInfo('+i+')" ><i class="icon-edit bigger-120"></i></button>'
	        newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  value="编辑频道" onclick="showChannelList('+i+')" data-toggle="modal" data-target="#channelListinfo">频道列表</button>'
	        newcontent = newcontent + '&nbsp;&nbsp;<button onclick="deleteData(' + i + ')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>'
		    newcontent = newcontent + '</tr>';	
	      }
	      
	      dataListTab.append(newcontent);
	      
	}
	

    //初始化
    $(document).ready(function () {
        //更新界面
        updateUI();

    });


//删除数据
function deleteDeviceChannel(id) {
    if (confirm('你确定要删除数据吗？')) {
        //通过Ajax删除服务器上的数据，并更新界面
        var dataPar = 'id='+id;
        $.ajax({
            type: "POST",
            data: dataPar,
            dataType: "json",
            url: "<%=request.getContextPath() %>/api/channel/deleteDeviceChannel",
            context: document.body,
            beforeSend: function () {
                },
                complete: function () {
                },
                success: function (data) {
                    updateChannelListUI();

                }
        });
    }
}

    //添加 设备信息
    function editData(index) {
        if (index.length == '0') {
            $("#modifyData").children("#index").val("");
            $("#modifyData").children("#id").val("");
        } else {
            
        }
    }

    
function showChannelList(i){
	$("#channelListinfo").modal('show');
	var channelListTab = $('#channelListTab'); 
	epalId = jsonData[i].epalId;
	$.ajax({
		type: "POST",
		data:"userId="+epalId,
		dataType: "json",
		url: "<%=request.getContextPath() %>/api/channel/getChannelList", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
		},
		complete:function(){
			
		},
		success: function(data){
			channelData=data.data;
			var newcontent='';
			$("#channelListTab tr:not(:has(th))").remove();
			for(var i=0;i<channelData.length;i++){
					newcontent = newcontent + '<tr>';	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+channelData[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+channelData[i].channelId+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+channelData[i].name+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+channelData[i].image+'" /></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="deleteDeviceChannel('+channelData[i].id+')" data-toggle="modal" data-target="#myModifyCourse">删除</button></td>';
					newcontent = newcontent + '</tr>';
			}
			channelListTab.append(newcontent);
		}
	});	
}
    
    
    //新增设备
function updateData() {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/registDevice' ,
          type: 'POST',
          data: formData,
		  dataType: "json",
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

function addChannel(){
	$("#addChannel").modal('show');

}

function searchChannel(){
	var channelNameStr = $(" input[ name='channelNameStr' ] ").val();
	$.ajax({
			type: "POST",
			data:"channelStr="+channelNameStr,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/searchChannel", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				channelList = data.data.items;
				var newcontent='';
				
				$("#channelListTab2 tr:not(:has(th))").remove();
				for(var i=0;i<channelList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="channelInfo" value="'+ channelList[i].id +'"></td>'
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+channelList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+channelList[i].channelId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+channelList[i].name+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+channelList[i].image+'" /></td>';
						newcontent = newcontent + '</tr>';
				}
				$("#channelListTab2").append(newcontent);
			}
	});	
}
function saveChannel(){
	 $("#addChannel").modal("hide");
	 var index = $(" input[ name='index' ] ").val();

     var channels = $("input[name='channelInfo']:checked").val([]);
     var channelIdList = "";
     for(var i=0;i<channels.length;i++){
 		channelIdList = channelIdList + channels[i].value + ",";
 	 }
 	 var formData = new FormData();
 	 formData.append("channelIds",channelIdList);
 	 formData.append("epalId",epalId);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveDeviceChannel' ,
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
			  updateChannelListUI();
          },
          error: function (returndata) {
          
          }
     });
}

function updateChannelListUI(){
	var channelListTab = $('#channelListTab'); 
	$.ajax({
		type: "POST",
		data:"userId="+epalId,
		dataType: "json",
		url: "<%=request.getContextPath() %>/api/channel/getChannelList", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
		},
		complete:function(){
			
		},
		success: function(data){
			channelData=data.data;
			var newcontent='';
			$("#channelListTab tr:not(:has(th))").remove();
			for(var i=0;i<channelData.length;i++){
					newcontent = newcontent + '<tr>';	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+channelData[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+channelData[i].channelId+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+channelData[i].name+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+channelData[i].image+'" /></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="deleteDeviceChannel('+channelData[i].id+')" data-toggle="modal" data-target="#myModifyCourse">删除</button></td>';
					newcontent = newcontent + '</tr>';
			}
			channelListTab.append(newcontent);
		}
	});	

}

function uploadDeviceInfo(){
	$("#uploadDeviceInfo").modal("show");
	
}

</script>

<div class="main-content">
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
			<li class="active">设备信息列表</li>
		</ul>
		<!-- .breadcrumb -->

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/device/getDeviceList"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>设备唯一标示号</td>
						<td><input type="text" name="searchStr" value="${queryDto.searchStr}">
						</td>
					</tr>


				</table>

				<div class="btn-group col-md-12">
					<button type="submit" class="btn btn-purple ml10 mb10">
						查询 <i class="icon-search icon-on-right bigger-110"></i>
					</button>
					<button class="btn btn-purple ml10 mb10" onclick="editDeviceInfo()" data-toggle="modal" data-target="#myModal-data" value="添加">
					添加设备</button>
					<button class="btn btn-purple ml10 mb10" onclick="uploadDeviceInfo()" data-toggle="modal" data-target="#myModal-data" value="添加">
					批量添加</button>
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
										<th width="3%">主键</th>
										<th width="5%">设备号</th>
										<th width="5%">EpalID</th>
										<th width="5%">SN</th>
										<th width="5%">Epal密码</th>
										<th width="5%">昵称</th>
										<th width="5%">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./getDeviceList"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<!-- 频道列表弹窗框--> 
<form width=200 height=300  method="post" name="channelInfo" id="channelInfo"
	enctype="multipart/form-data" action="">
	<input type="hidden" name="index" id="index" value=""> <input type="hidden" name="id" id="id" value="">
	<input type="hidden"name="epalId" id="epalId" value="">
	<div class="modal fade" id="channelListinfo" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:auto;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">频道列表</h4>
					<button class="btn btn-purple ml10 mb10" onclick="addChannel()" data-toggle="modal" value="添加">
					添加频道</button>
				</div>
				<div class="modal-body">
					<table id="channelListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th width="100">序号</th>
										<th width="100">频道ID</th>
										<th width="100">频道名称</th>
										<th width="100">图片</th>
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

<!-- 添加频道弹窗框--> 
<div class="modal fade" id="addChannel" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<input type="hidden" name="index" id="index" value=""> 
	<input type="hidden" name="id" id="id" value=""> 
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">频道添加</h4>
			</div>
			<div class="modal-body">
			
				<div id="divInput" class="form-group" style="height:35px;display: none;">
					<label class="col-sm-2 control-label no-padding-right text-right">序号</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="id" id="id"
									type="text"
									style="max-width:650px;width:100%;" readonly/>
							</div>
						</div>
					</div>
				</div>
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">频道名称：</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="channelNameStr" id="channelNameStr"
									type="text" 
									style="max-width:650px;width:80%;" />&nbsp;&nbsp;&nbsp;&nbsp;
								<button onclick="searchChannel()" style="max-width:650px;width:15%;">搜索频道</button>
							</div>
						</div>
					</div>
				</div>

					<div class="modal-body">
					<table id="channelListTab2"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
						<thead>
							<tr>
								<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">序号</th>
								<th width="100">频道ID</th>
								<th width="100">频道名称</th>
								<th width="100">图片</th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="saveChannel()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function editDeviceInfo(index){
		$("#editDeviceInfo").modal("show");
		$("#deviceNo").val(jsonData[index].deviceNo);
		$("#deviceEpalId").val(jsonData[index].epalId);
		$("#nickName").val(jsonData[index].nickName);
	}
	
</script>

<div class="modal fade" id="editDeviceInfo" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<input type="hidden" name="index" id="index" value=""> 
	<input type="hidden" name="id" id="id" value=""> 
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">设备信息管理</h4>
			</div>
			<div class="modal-body">
			
				
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">流水号</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="deviceNo" id="deviceNo"
									type="text" data-rule="IM号:required;"
									style="max-width:650px;width:100%;" onblur="checkEpalId()"/>
							</div>
						</div>
					</div>
				</div>

				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">IM账号</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="deviceEpalId" id="deviceEpalId"
									type="text" data-rule="IM号:required;"
									style="max-width:650px;width:100%;" />
							</div>
						</div>
					</div>
				</div>

				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">昵称</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="nickName" id="nickName"
									type="text" data-rule="IM号:required;"
									style="max-width:650px;width:100%;" />
							</div>
						</div>
					</div>
				</div>
								

				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">机器状态</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<select class="" style="display:inline-block;">
									<option>--状态选择--</option>
									<option>生产</option>
									<option>入库</option>
									<option>发货</option>
									<option>使用</option>
									<option>退回</option>
									<option>维修</option>
									<option>报废</option>
								</select>
							</div>
						</div>
					</div>
				</div>
							
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">收货人</label>
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
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">发货订单号</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="oderId" id="oderId"
									type="text" data-rule="备注:required;"
									style="max-width:650px;width:100%;" />
							</div>
						</div>
					</div>
				</div>
				
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">发货地址</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<select class="" style="display:inline-block;">
									<option>--省--</option>
									<option>安徽</option>
									<option>广东</option>
									<option>省3</option>
								</select>
								<select class="" style="display:inline-block;">
									<option>--市--</option>
									<option>市1</option>
									<option>市2</option>
									<option>市3</option>
								</select>
								<select class="" style="display:inline-block;">
									<option>--区--</option>
									<option>区1</option>
									<option>区2</option>
									<option>区3</option>
								</select>
								<input class="" style="width:40%;" type="text" placeholder="请输入详细地址" />
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





<!-- 上传文件弹窗 -->
<form action="uploadDeviceListFile" method="post" enctype ="multipart/form-data">
	<div class="modal fade" id="uploadDeviceInfo" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<input type="hidden" name="index" id="index" value=""> 
		<input type="hidden" name="id" id="id" value=""> 
		<div class="modal-dialog" style="width:900px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">导入设备列表</h4>
				</div>
				<div class="modal-body">
					<div id="divInput" class="form-group" style="height:35px;">
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input type="file" name="fileUpload" label=“上传文件"/>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">提交</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>