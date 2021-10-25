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
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].soundId+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].name+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].playUrl+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+jsonData[i].image+'" /></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].albumId+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].albumName+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].tagCN+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteRecommend('+i+')">删除</button></td>';
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

	}
}


//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveRecommend' ,
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
              var index=$("#index").val();
			  if(index == ''){
					//更新数组数据
					//添加到最后
					jsonData.push(resultData); 
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


function deleteRecommend(index){
	$.ajax({
			type: "POST",
			data:"id="+jsonData[index].id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/deleteRecommend", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				jsonData.remove(index);
				updateTotalCount(-1);
				updateUI();
			}
		});	

}


function AutomaticFilling(){
	channelId = $("input[ name='channelId' ] ").val();
	$.ajax({
			type: "POST",
			data:"channelId="+channelId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/getChanelInfoFromWeb", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				myData = data.data;
				$("#channelId").val(myData.channelId);
				$("#name").val(myData.name);
				$("#intro").val(myData.intro);
				$("#image").val(myData.image);
				$("#fans").val(myData.fans);
				$("#status").val(myData.status);
				$("#level").val(myData.level);
				$("#myModal-data").modal("show");
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
			<li class="active"><a href="">音频管理</a></li>
			<li class="active"><a href="">推荐管理</a></li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/channel/recommendManage"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>音频名称</td>
						<td><input type="text" name="searchStr"
							value="${queryDto.searchStr}">
						</td>
						<td>分类</td>
						<td>
							<select id="tag" name="tag">
								<option value="">请选择分类</option>
								<option value="故事">故事</option>
								<option value="儿歌">儿歌</option>
								<option value="国学">国学</option>
								<option value="英语">英语</option>
								<option value="音乐">音乐</option>
							</select>
						</td>
						
					</tr>

				</table>

				<div class="btn-group col-md-12">
					<button type="submit" class="btn btn-purple ml10 mb10">
						查询 <i class="icon-search icon-on-right bigger-110"></i>
					</button>
					<button class="btn btn-purple ml10 mb10" onclick="editData('')" data-toggle="modal" data-target="#myModal-data" value="添加">
					添加推荐</button>
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
										<th width="50">序列</th>
										<th width="50">音频ID</th>
										<th width="100">音频名称</th>
										<th width="100">播放地址</th>
										<th width="100">音频海报</th>
										<th width="80">专辑ID</th>
										<th width="50">专辑名称</th>
										<th width="50">分类</th>
										<th width="150">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./recommendManage"
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
					<h4 class="modal-title">添加推荐(音频ID和专辑ID只需要填一个)</h4>
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
						<label class="col-sm-2 control-label no-padding-right text-right">分类</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<select id="category" name="category">
									<option>请选择分类</option>
									<option>故事</option>
									<option>儿歌</option>
									<option>国学</option>
									<option>英语</option>
									<option>音乐</option>
								</select>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">专辑ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="albumId" id="albumId"
										type="text"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="soundId" id="soundId"
										type="text"
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

