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
	
<style>
.line-limit-length {
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap; //文本不换行，这样超出一行的部分被截取，显示...
}
</style>
<script>

var newcontent = '';
var jsonData;
var soundList;
//更新总记录
function updateTotalCount(count){
	//更新总记录
	var total=$("font#fPageTotal").text();
	total=eval(total)+count;
	$("font#fPageTotal").text(total);
}


function switchSoundStatus(index){
	var soundId = jsonData[index].soundId;
	var status = jsonData[index].status;
	var nextStatus = 0;
	if(status == 1){
		nextStatus = 0;
	}else{
		nextStatus = 1;
	}
	$.ajax({
			type: "POST",
			data:"soundId="+soundId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/switchSoundStatus", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				jsonData[index].status = nextStatus;
				
				updateUI();
			}
		});	

}
//更新界面的方法
function updateUI(){
	newcontent='';
	var dataListTab = $('#dataListTab');
	$("#dataListTab tr:not(:has(th))").remove();
	for(var i=0;i<jsonData.length;i++){
			newcontent = newcontent + '<tr>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="soundInfo" value="'+ jsonData[i].id +'"></td>'
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].soundId+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+jsonData[i].image+'" /></td>'
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].name+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><div style="max-width: 110px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" onMouseOut="showSoundIntro('+i+')">'+jsonData[i].intro+'</div></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><audio id="music1'+i+'" src="'+jsonData[i].playUrl+'"  loop="loop">你的浏览器不支持audio标签。</audio>' +'<a id="audio_btn" onclick="audioBtnClick1('+i+')" ><img src="../images/pause.png" width="48" height="50" id="music_btn1'+i+'" border="0"></a></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].playTimes+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].albumName+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].channelId+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].albumId+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].updateTime+'</td>';
			if(jsonData[i].status == 1){
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="soundStatus" style="background:#00FF00" onclick="switchSoundStatus('+i+')" >屏蔽</button></td>';
			}else{
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="soundStatus" style="background:#FF0000" onclick="switchSoundStatus('+i+')">开放</button></td>';
			}
			
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].sortId+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].cateId+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].score+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].FDPlayCount+'</td>';
			
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data">编辑</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteQuestion('+i+')" data-toggle="modal" data-target="#answer-data">删除</button></td>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="addToCourses('+i+')" data-toggle="modal" data-target="#answer-data">添加到课程</button></td>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}

function showSoundIntro(index){
	var intro = jsonData[index].intro;
	alert(intro);
}
//初始化
$(document).ready( function () {
	
	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	soundList = jsonData;
	updateUI();

});


//预添加或者修改
function editData(index)
{	
	if(index.length == '0'){
		$("#index").val("");
		$("#id").val("");
		$("#soundId").val("");
		$("#name").val("");
		$("#image").val("");
		$("#intro").val("");
		$("#playTimes").val("");
		$("#playUrl").val("");
		$("#updateTime").val("");
		$("#albumName").val("");
		$("#channelId").val("");
		$("#albumId").val("");
		$("#status").val("");
		$("#sortId").val("");
		
		$("#cateId").val("");
		$("#score").val("");
		
		

	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		$("#soundId").val(myData.soundId);
		$("#name").val(myData.name);
		$("#image").val(myData.image);
		$("#intro").val(myData.intro);
		$("#playTimes").val(myData.playTimes);
		$("#playUrl").val(myData.playUrl);
		$("#updateTime").val(myData.updateTime);
		$("#albumName").val(myData.albumName);
		$("#channelId").val(myData.channelId);
		$("#albumId").val(myData.albumId);
		$("#status").val(myData.status);
		$("#sortId").val(myData.sortId);
		$("#cateId").val(myData.cateId);
		$("#score").val(myData.score);		
	}
}


//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/channel/updateSoundById' ,
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


function createCourse(){
	alert("创建课程");
	$.ajax({
			type: "POST",
			data:"id="+jsonData[index].id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/addToCourses", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				jsonData[index].status = 2;
				updateUI();
			}
		});	
}
function deleteSound(index){
	$.ajax({
			type: "POST",
			data:"id="+jsonData[index].id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/deleteSound", 
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

function switchSort(sortStr){
	$.ajax({
			type: "POST",
			data:"sortStr="+sortStr,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/soundManage", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				jsonData = data.data
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
			<li class="active">音频管理</li>
			<li class="active">音频管理</li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/channel/soundManage"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>音频名称</td>
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
					&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-purple ml10 mb10" onclick="editData()" data-toggle="modal" data-target="#myModal-data" value="添加">
					添加音频</button>
					&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-purple ml10 mb10" onclick="createCourse()" data-toggle="modal" data-target="#myModal-data" value="添加">
					创建课程</button>
					&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-purple ml10 mb10" onclick="addSoundToRecommend()" data-toggle="modal"value="添加">
					添加音频到推荐</button>
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
										<th width="50"><button onclick="switchSort('id')">序列↑↓</button></th>
										<th width="100">音频ID↑↓</th>
										<th width="100">海报</th>
										<th width="200">名称↑↓</th>
										<th width="200">简介</th>
										<th width="100">资源</th>
										<th width="100">播放次数↑↓</th>
										<th width="100">专辑名称</th>
										<th width="100">频道ID</th>
										<th width="100">专辑ID</th>
										<th width="100">更新时间</th>
										<th width="100">状态↑↓</th>
										<th width="50">排序↑↓</th>
										<th width="100">图书编码</th>
										<th width="50">评分</th>
										<th width="50">凡豆播放次数</th>
										<th width="200">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./soundManage"
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
					<h4 class="modal-title">添加/编辑音频</h4>
				</div>
				<div class="modal-body">
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="soundId" id="soundId"
										type="text" data-rule="音频ID:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="name" id="name"
										type="text" data-rule="音频名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频简介</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="intro" id="intro"
										type="text" data-rule="音频简介:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频海报</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="image" id="image"
										type="text" data-rule="音频简介:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频播放地址</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="playUrl" id="playUrl"
										type="text" data-rule="音频简介:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>	
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频次数</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="playTimes" id="playTimes"
										type="text" data-rule="音频简介:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频更新时间</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="updateTime" id="updateTime"
										type="text" data-rule="音频简介:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频专辑名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="albumName" id="albumName"
										type="text" data-rule="音频简介:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>										

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频专辑ID</label>
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
						<label class="col-sm-2 control-label no-padding-right text-right">音频频道ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="channelId" id="channelId"
										type="text" 
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
										type="text" 
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>	
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">排序</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="sortId" id="sortId"
										type="text" 
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>	
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">图书编码</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="cateId" id="cateId"
										type="text" 
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">评分</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="score" id="score"
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

<%@include file="addSoundToRecommend.jsp"%>
