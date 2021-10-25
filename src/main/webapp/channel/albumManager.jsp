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
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumItem" value="'+ i +'"></td>'
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].album_id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].name+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].intro+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+jsonData[i].image+'" /></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].channel_id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].status+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].sort_id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data">编辑专辑</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showSounds('+jsonData[i].id+')">查看音频</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteAlbum('+i+')" data-toggle="modal" data-target="#answer-data">删除专辑</button></td>';
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


var currentIndex;
//预添加或者修改
function editData(index)
{	
	if(index.length == '0'){
		$("#index").val("");
		$("#id").val("");
		$("#albumId").val("");
		$("#name").val("");
		$("#intro").val("");
		$("#image").val("");
		$("#channelId").val("");
		$("#status").val("");
		$("#sortId").val("");

	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		currentAlbumId = myData.id;
		currentIndex = index;
		$("#albumId").val(myData.album_id);
		$("#name").val(myData.name);
		$("#intro").val(myData.intro);
		$("#image").val(myData.image);
		$("#channelId").val(myData.channel_id);
		$("#status").val(myData.status);
		$("#sortId").val(myData.sort_id);
		showTag(myData.id);
	}
}


function showTag(albumId){
	var alubmId = albumId;
	$.ajax({
          url: '<%=request.getContextPath() %>/api/channel/getAlbumTagFromAlbumId' ,
          type: 'GET',
          data: "albumId="+albumId,
          
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (returndata) {
          $("#albumTagList").empty();
          var dataList = returndata.data.items;
          var newContetn = ""
		  for(var i=0;i<dataList.length;i++){
   			 newContetn += '<span class="tag-modal tag tag-info"><span>'+dataList[i].tagName+'</span><button type="button" class="close" onclick="deleteTag('+dataList[i].id+')">×</button></span>'
		  }
		  $("#albumTagList").append(newContetn);
          },
          error: function (returndata) {
          
          }
     });
							
								
								
							

}

//删除标签
function deleteTag(id){
	var id = id;
	$.ajax({
          url: '<%=request.getContextPath() %>/api/channel/deleteTag' ,
          type: 'GET',
          data: "id="+id,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		   
		  },
          success: function (returndata) {
          	  editData(currentIndex);
          },
          error: function (returndata) {
          
          }
     });
}

//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveAlbum' ,
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
					var data = returndata.data;
					data["channel_id"] = data["channelId"]
					data["album_id"] = data["albumId"]
					data["sort_id"] = data["sortId"]
					jsonData[index]= data;
			  }
			  //更新界面
			  updateUI();
          },
          error: function (returndata) {
          
          }
     });
}

//修改数据

function saveRecommendAlbums() {
     //隐藏modal
	var tag = $("select[ name='tag' ] ").val();
	var recommendAlbums = $("input[name='albumItem2']:checked").val([]);
	var albums = "";
	for(var i=0;i<recommendAlbums.length;i++){
		albums = albums +  recommendAlbums[i].value + ",";
	}
	var formData = new FormData();
	formData.append("tag",tag);
	formData.append("albums",albums);
	$.ajax({
		 url: "<%=request.getContextPath() %>/api/channel/saveRecommendAlbums", 
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
          		alert("添加成功！");
              	$("#recommendAlbums").modal("hide");
          },
          error: function (returndata) {
          
          }
		});	
}

function recommendAlbums(){
	
	var recommendAlbums = $("input[name='albumItem']:checked").val([]);
	if(recommendAlbums.length == 0){
		alert("请选择专辑！");
		$("#recommendAlbums").modal("hide");
	}else{
		$("#recommendAlbums").modal("show");
		newcontent='';
		$("#recommendAlbumList tr:not(:has(th))").remove();
		for(var i=0;i<recommendAlbums.length;i++){
				newcontent = newcontent + '<tr>';	
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumItem2" value="'+ jsonData[recommendAlbums[i].value].album_id +'" checked></td>'
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[recommendAlbums[i].value].id+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[recommendAlbums[i].value].album_id+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[recommendAlbums[i].value].name+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+jsonData[recommendAlbums[i].value].image+'" /></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="saveAnswer('+i+')" data-toggle="modal" data-target="#myModal-data">保存</button>';
				newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteAnswer('+i+')" data-toggle="modal" data-target="#answer-data">删除</button></td>';
				newcontent = newcontent + '</tr>';
		}
		$('#recommendAlbumList').append(newcontent);
	}
}

var currentAlbumId;

function saveTag(){
	$("#mymodal-data2").modal("hide");
	var albumId = currentAlbumId;
	var tagCheckedIds = $("input[name='tagChecked']:checked").val([]);
	var tagCheckedId = "";
	for(var i=0;i<tagCheckedIds.length;i++){
		tagCheckedId = tagCheckedId + tagCheckedIds[i].value + ",";
	}
	var formData = new FormData();
	formData.append("albumId",albumId);
	formData.append("tagCheckedId",tagCheckedId);
	$.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveTagToSource' ,
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
			  editData(currentIndex);
          },
          error: function (returndata) {
          
          }
     });

}

function searchTag(){
	var tagName = $(" input[ name='tagName' ] ").val();
	$.ajax({
			type: "POST",
			data:"tagName="+tagName,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/searchTag", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
				$("#albumTagTab tr:not(:has(th))").remove();
		   		$("#albumTagTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			},
			complete:function(){
				
			},
			success: function(data){
				tagList = data.data.items;
				var newcontent='';
				
				$("#albumTagTab tr:not(:has(th))").remove();
				for(var i=0;i<tagList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="tagChecked" value="'+ tagList[i].id +'"></td>'
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].tagName+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].groupName+'</td>';
						newcontent = newcontent + '</tr>';
				}
				$("#albumTagTab").append(newcontent);
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
			<li class="active">音频管理</li>
			<li class="active">专辑管理</li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/channel/albumManage"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>专辑名称</td>
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
					<button class="btn btn-purple ml10 mb10" onclick="editData('')" data-toggle="modal" data-target="#myModal-data" value="添加">
					添加专辑</button>
					<!-- <button class="btn btn-purple ml10 mb10" onclick="recommendAlbums()" data-toggle="modal" data-target="#recommendAlbums">
					添加到推荐</button> -->
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
										<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
										<th width="50">专辑ID</th>
										<th width="50">XMLY专辑ID</th>
										<th width="100">专辑名称</th>
										<th width="100">专辑简介</th>
										<th width="100">专辑海报</th>
										<th width="80">频道ID</th>
										<th width="50">状态</th>
										<th width="50">排序ID</th>
										<th width="200">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./albumManage"
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
					<h4 class="modal-title">添加/修改专辑</h4>
				</div>
				<div class="modal-body">
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">专辑ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="albumId" id="albumId"
										type="text" data-rule="专辑ID:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">所属频道ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="channelId" id="channelId"
										type="text" data-rule="所属频道ID:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">专辑名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="name" id="name"
										type="text" data-rule="专辑名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">专辑图片</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="image" id="image"
										type="text" data-rule="专辑图片:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">简介</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="intro" id="intro"
										type="text" data-rule="专辑名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">状态</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="status" id="status"
										type="text" data-rule="专辑名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">排序ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="sortId" id="sortId"
										type="text" data-rule="专辑名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					<div class="form-group" style="height:50px;">
						<label class="col-sm-2 control-label no-padding-right text-right">标签列表</label>
						<div class="col-sm-10">
							<div class="input-medium">
								<div class="input-group">
								<button class="btn btn-primary"  data-toggle="modal" data-target="#mymodal-data2" onclik="addTag()">添加标签</button>
								<input type="hidden" name="item" id="uItems" />
								</div>
							</div>
						</div>
					</div>
					
					<div class="form-group" style="height:50px;">
						<div class="tags tagSelected" style="border: none;width:auto;padding-left:0;width:650px;" id="albumTagList">
							<span class="tag-modal tag tag-info" style="display: none;">
								<span>模板</span>
								<button type="button" class="close">×</button>
							</span>
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


<div class="modal fade" id="soundList" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:auto;height: 1200px;">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h4 class="modal-title">音频列表</h4>
			<button class="btn btn-purple ml10 mb10" onclick="addAnswer()" data-toggle="modal" value="添加">
			添加音频</button>
		</div>
		<div class="modal-body">
			
			<table id="answerInfoListTab"
						class="table table-striped table-bordered table-hover"
						style="text-align:left;">
						<thead>
							<tr>
								<th width="50">序列</th>
								<th width="50">音频ID</th>
								<th width="100">音频名称</th>
								<th width="100">音频海报</th>
								<th width="100">播放地址</th>
								<th width="50">状态</th>
								<th width="50">排序ID</th>
								<th width="150">操作</th>
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



<div class="modal fade" id="recommendAlbums" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1000px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">推荐分类选择</h4>
			</div>
			<div class="modal-body">
				<div style="position:relative;margin:10px;text-align:center">
					<select id="tag" name="tag">
						<option value="">请选择分类</option>
						<option value="故事">故事</option>
						<option value="儿歌">儿歌</option>
						<option value="国学">国学</option>
						<option value="英语">英语</option>
						<option value="音乐">音乐</option>
					</select>	
				</div>
				<div style="position:relative;margin:10px">			
					<table id="recommendAlbumList"
									class="table table-striped table-bordered table-hover"
									style="text-align:left;">
									<thead>
										<tr>
											<th width="20"><input type="checkbox" name="" value="" onclick="" checked></th>
											<th width="50">序列</th>
											<th width="50">专辑ID</th>
											<th width="100">专辑名称</th>
											<th width="100">专辑海报</th>
											<th width="100">操作</th>
										</tr>
									</thead>
	
						</table>
					</div>		
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" onclick="saveRecommendAlbums()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<%@include file="Dialog_showSounds.jsp"%>

<div class="modal  fade" id="mymodal-data2" tabindex="-3" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:800px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">选择标签</h4>
			</div>
			<div class="modal-body">
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">标签名称：</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="tagName" id="tagName"
									type="text" 
									style="max-width:650px;width:80%;" />&nbsp;&nbsp;&nbsp;&nbsp;
								<button onclick="searchTag()" style="max-width:650px;width:15%;">搜索标签</button>
							</div>
						</div>
					</div>
				</div>
				<div class="table-responsive" style="max-height: 550px;overflow: auto;">
					<table  class="table table-striped table-bordered table-hover" id="albumTagTab">
						<thead>
							<tr>
								<th style="width:30px;">序号</th>
								<th style="width:30px;">标签名称</th>
								<th style="width:30px;">标签分组</th>						
							</tr>
						</thead>
						<tbody>	
						</tbody>
					</table>
				</div><!-- /.table-responsive -->
			</div><!-- /.modal-body -->
			<div class="modal-footer">
				<button type="button"  onclick="saveTag()" class="btn btn-primary">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

