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
var tagList;
var answerData;
var CurrentDateId;
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
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].dateName+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].intro+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].createTime+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data">编辑</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showTagList('+jsonData[i].id+')"">分组管理</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteDailyDate('+i+')">删除日期</button></td>';
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
		$("#dateName").val("");
		$("#intro").val("");
	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		$("#dateName").val(myData.dateName);
		$("#intro").val(myData.intro);
		$("#createTime").val(myData.createTime);
	}
}





//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/sound/saveDailyRecommendDate' ,
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

//删除频道
function deleteDailyDate(index){
	if(window.confirm('你确定要删除吗？')){
          
    }else{
          return false;
    }
    if(window.confirm('请再次确认？')){
          
    }else{
          return false;
    }
    if(window.confirm('删除就没有了哦？')){
          
    }else{
          return false;
    }
	$.ajax({
			type: "POST",
			data:"id="+jsonData[index].id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/sound/deleteDailyDate", 
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

</script>


<div class="main-content">
	<div id="myJson" style="display: none;">${jsonData}</div>
	<div class="breadcrumbs" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active"><a href="">音频管理</a></li>
			<li class="active"><a href="">每日推荐</a></li>
		</ul>
	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/sound/DailyRecommendManage"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>频道名称</td>
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
										<th width="30">序号</th>
										<th width="30">日期</th>
										<th width="100">简介</th>
										<th width="100">创建时间</th>
										<th width="150">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./DailyRecommendManage"
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
					<h4 class="modal-title">频道添加</h4>
				</div>
				<div class="modal-body">

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">日期</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="dateName" id="dateName"
										type="text"
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
										type="text"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">创建时间</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="createTime" id="createTime"
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


<script>

function showTagList(dateId){
	CurrentDateId = dateId;
	$("#TagList").modal("show")
	$.ajax({
			type: "POST",
			data:"dateId="+dateId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/sound/getTagListFromDateId", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
				$("#albumsListTab tr:not(:has(th))").remove();
		   		$("#albumsListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			},
			complete:function(){
				
			},
			success: function(data){
				tagList = data.data.items;
				updateTagListUI();
			}
		});	
}

function updateTagListUI(){
	newcontent='';
	$("#TagListTab tr:not(:has(th))").remove();
	for(var i=0;i<tagList.length;i++){
			newcontent = newcontent + '<tr>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="'+ tagList[i].id + '" value="checkBox_'+ tagList[i].id +'"></td>'
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].tagName+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].intro+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].createTime+'</td>';

			newcontent = newcontent + '<td nowrap="nowrap" align="left" >&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showSounds('+tagList[i].id+')" data-toggle="modal">查看音频</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showTagList('+tagList[i].id+')"">编辑</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="up('+i+')"">上移</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="down('+i+')"">下移</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteTagFromDate('+i+')" data-toggle="modal" data-target="#answer-data">删除标签</button></td>';
			newcontent = newcontent + '</tr>';
	}
	$("#TagListTab").append(newcontent);
}


function deleteTagFromDate(index){
	if(window.confirm('你确定要删除吗？')){
          
    }else{
          return false;
    }
    if(window.confirm('请再次确认？')){
          
    }else{
          return false;
    }
    if(window.confirm('删除就没有了哦？')){
          
    }else{
          return false;
    }
    var tagId = tagList[index].id;
	$.ajax({
			type: "POST",
			data:"id="+tagId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/sound/deleteTagFromDate", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				tagList.remove(index);
				updateTagListUI();
			}
		});	
}

function addTag(){
	$("#mymodal-data2").modal("show");
}

function saveTagToDate(){
	$("#mymodal-data2").modal("hide");
	var id = $("#id").val();
	var tagName = $("#tagName").val();
	var intro = $("#tagIntro").val();
	var createTime = $("#tagcreateTime").val();
	var formData = new FormData();
	formData.append("id",id);
	formData.append("dateId",CurrentDateId);
	formData.append("tagName",tagName);
	formData.append("intro",intro);
	formData.append("createTime",createTime);
	$.ajax({
		url: "<%=request.getContextPath() %>/api/sound/saveDailyRecommendTag", 
        type: 'POST',
        data:formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
			
		},
		complete:function(){
			
		},
		success: function(data){
			tagList.push(data.data);
			updateTagListUI();
			
		}
	});	
}

function up(index){
	var formData = new FormData();
	var data = "";
	for(var i=0;i<tagList.length;i++){
		var tagId = tagList[i].id;
		if(index-1==i){
			var temp = tagId + "_" + (i+2) + ",";
		}else if(index==i){
			var temp = tagId + "_" + i + ",";
		}else{
			var temp = tagId + "_" + (i+1) + ",";
		}
		data = data + temp;
	}
	formData.append("data",data);
	$.ajax({
          url: '<%=request.getContextPath() %>/api/sound/updateDailyRecommendTagSort' ,
          type: 'POST',
          data:formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   	
		  },
          success: function (returndata) {
          		var dateId = tagList[index].dateId;
				updateTagListData(dateId);
          },
          error: function (returndata) {
          
          }
     });
}


function down(index){
	var formData = new FormData();
	var data = "";
	for(var i=0;i<tagList.length;i++){
		var tagId = tagList[i].id;
		if(i==index){
			var temp = tagId + "_" + (i+2) + ",";
		}else if(i==index+1){
			var temp = tagId + "_" + i + ",";
		}else{
			var temp = tagId + "_" + (i+1) + ",";
		}
		data = data + temp;
	}
	formData.append("data",data);
	$.ajax({
          url: '<%=request.getContextPath() %>/api/sound/updateDailyRecommendTagSort' ,
          type: 'POST',
          data:formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   	
		  },
          success: function (returndata) {
          		var dateId = tagList[index].dateId;
				updateTagListData(dateId);
          },
          error: function (returndata) {
          
          }
     });
}


function updateTagListData(dateId){
	$.ajax({
		type: "POST",
		data:"dateId="+dateId,
		dataType: "json",
		url: "<%=request.getContextPath() %>/api/sound/getTagListFromDateId", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
			$("#albumsListTab tr:not(:has(th))").remove();
	   		$("#albumsListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		},
		complete:function(){
			
		},
		success: function(data){
			tagList = data.data.items;
			updateTagListUI();
		}
	});	

}
</script>
<!-- 分组列表弹窗--> 
<div class="modal fade" id="TagList" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style=width:auto;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">分组列表</h4>
				<button class="btn btn-purple ml10 mb10" onclick="addTag()" data-toggle="modal" value="添加">
				添加分组</button>
				<!-- <button class="btn btn-purple ml10 mb10" onclick="addAlbumToRecommend()" data-toggle="modal" value="添加">
				添加到推荐</button>--> 
			</div>
			<div class="modal-body">
				
				<table id="TagListTab" name="#dateTagTab"
							class="table table-striped table-bordered table-hover"
							style="word-break:break-all">
							<thead>
								<tr>
									<th width="2%"><input type="checkbox" name="" value="" onclick=""></th>
									<th width="5%">ID</th>
									<th width="10%">标签名称</th>
									<th width="20%">简介</th>
									<th width="10%">创建时间</th>
									<th width="20%">操作</th>
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


<!-- 添加分组弹窗框--> 
<div class="modal  fade" id="mymodal-data2" tabindex="-3" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:800px;">
		<div class="modal-content">
			<input type="hidden" name="dateId" id="dateId" value="">
			<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">分组添加</h4>
				</div>
			<div class="modal-body">

				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">分组名称</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="tagName" id="tagName"
									type="text"
									style="max-width:650px;width:100%;" />
							</div>
						</div>
					</div>
				</div>

				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">分组简介</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="tagIntro" id="tagIntro"
									type="text"
									style="max-width:650px;width:100%;" />
							</div>
						</div>
					</div>
				</div>
				
				
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">创建时间</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="tagcreateTime" id="tagcreateTime"
									type="text"
									style="max-width:650px;width:100%;" />
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="saveTagToDate()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>

		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!----------------------------- 音频操作--------------------->
	
<script>
var currentTagId;

function addSoundToTag(){
	$("#addSoundToAlbumDialog").modal("show");

}

function searchSound(){
	var soundNameStr = $(" input[ name='soundNameStr' ] ").val();
	$.ajax({
			type: "POST",
			data:"soundNameStr="+soundNameStr,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/searchSounds", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
				$("#soundListTab tr:not(:has(th))").remove();
		   		$("#soundListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			},
			complete:function(){
				
			},
			success: function(data){
				soundList = data.data.items;
				var newcontent='';
				
				$("#soundListTab tr:not(:has(th))").remove();
				for(var i=0;i<soundList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="soundInfo" value="'+ soundList[i].id +'"></td>'
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].albumName+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].name+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].intro+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><a class="btn btn-xs btn-info" href="'+soundList[i].playUrl+'" target="_blank">播放</a>';
						newcontent = newcontent + '</td>';
						newcontent = newcontent + '</tr>';
				}
				$("#soundListTab").append(newcontent);
			}
	});	
}


function saveZhuboAlias(soundId){
	var aliasName = $("#sound_zhubo_" + soundId).val();
	 var formData = new FormData();
	 formData.append("soundId",soundId);
	 formData.append("aliasName",aliasName);
	 formData.append("type","zhubo");
	$.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveAlias' ,
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

          },
          error: function (returndata) {
          
          }
     });
}
function saveAlbumAlias(soundId){
	var aliasName = $("#sound_album_" + soundId).val();
	 var formData = new FormData();
	 formData.append("soundId",soundId);
	 formData.append("aliasName",aliasName);
	 formData.append("type","album");
	$.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveAlias' ,
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

          },
          error: function (returndata) {
          
          }
     });
}
function saveSoundAlias(soundId){
	var aliasName = $("#sound_sound_" + soundId).val();
	 var formData = new FormData();
	 formData.append("soundId",soundId);
	 formData.append("aliasName",aliasName);
 	 formData.append("type","sound");
	$.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveAlias' ,
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

          },
          error: function (returndata) {
          
          }
     });
}
function showLabelList(soundId){
	alert(soundId);


}
function saveSoundToTag(){
	$("#addSoundToAlbumDialog").modal("hide");
	 var tagId = currentTagId;
     var sounds = $("input[name='soundInfo']:checked").val([]);
     var soundIdList = "";
     for(var i=0;i<sounds.length;i++){
 		soundIdList = soundIdList + sounds[i].value + ",";
 	 }
 	 var formData = new FormData();
 	 formData.append("tagId",tagId);
 	 formData.append("soundIdList",soundIdList);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/sound/saveSoundToTag' ,
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
			  //更新界面
			  showSounds(tagId);
          },
          error: function (returndata) {
          
          }
     });
}

var soundList;
function showSounds(tagId){
	currentTagId = tagId;
	$("#SoundsDialog").modal("show");
	$.ajax({
			type: "POST",
			data:"tagId="+tagId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/sound/getSourceFromTagId", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
				$("#soundsListTab tr:not(:has(th))").remove();
		   		$("#soundsListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			},
			complete:function(){
				
			},
			success: function(data){
				newcontent='';
				$("#soundsListTab tr:not(:has(th))").remove();
				soundList = data.data.items;
				for(var i=0;i<soundList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="soundInfo" value="'+ soundList[i].id +'"></td>'
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].soundId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].channelName+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].albumName+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].soundName+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="text" id="sound_zhubo_'+soundList[i].soundId+'" name="zhuboAlias" onblur="saveZhuboAlias('+soundList[i].soundId+')" value="'+soundList[i].zhuboAliasName+'"></td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="text" id="sound_album_'+soundList[i].soundId+'" name="albumAlias" onblur="saveAlbumAlias('+soundList[i].soundId+')" value="'+soundList[i].albumAliasName+'"></td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="text" id="sound_sound_'+soundList[i].soundId+'" name="soundAlias" onblur="saveSoundAlias('+soundList[i].soundId+')" value="'+soundList[i].soundAliasName+'"></td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><a class="btn btn-xs btn-info" href="'+soundList[i].playUrl+'" target="_blank">播放</a>&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteSoundFromAlbum('+soundList[i].id+')">删除</button>';
						newcontent = newcontent + '</td>';
						newcontent = newcontent + '</tr>';
				}
				$('#soundsListTab').append(newcontent);
			}
		});	
	
}

//删除专辑和音频的对应关系
function deleteSoundFromAlbum(id){
	if(window.confirm('你确定要从分类中删除音频吗？')){
          
    }else{
          return false;
    }
	$.ajax({
			type: "POST",
			data:"id="+id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/sound/deleteSoundFromTag", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				showSounds(currentTagId);
			}
		});	

}

</script>



<div class="modal fade" id="SoundsDialog" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style=width:auto;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">音频列表</h4>
				<button class="btn btn-purple ml10 mb10" onclick="addSoundToTag()" data-toggle="modal" value="添加">
				添加音频</button>
			</div>
			<div class="modal-body">
				
				<table id="soundsListTab" name="soundsListTab"
							class="table table-striped table-bordered table-hover"
							style="word-break:break-all">
							<thead>
								<tr>
									<th width="2%"><input type="checkbox" name="" value="" onclick=""></th>
									<th width="100">音频ID</th>
									<th width="100">主播名称</th>
									<th width="100">专辑名称</th>
									<th width="100">音频名称</th>
									<th width="100">主播别名</th>
									<th width="200">专辑别名</th>
									<th width="200">音频别名</th>
									<th width="100">操作</th>
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

<!-- 分类添加音频弹窗框--> 
<div class="modal fade" id="addSoundToAlbumDialog" tabindex="-1" role="dialog" data="1"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style=width:auto;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">分类添加音频</h4>
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
					<label class="col-sm-2 control-label no-padding-right text-right">音频名称：</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="soundNameStr" id="albumNameStr"
									type="text" 
									style="max-width:650px;width:80%;" />&nbsp;&nbsp;&nbsp;&nbsp;
								<button onclick="searchSound()" style="max-width:650px;width:15%;">搜索音频</button>
							</div>
						</div>
					</div>
				</div>

					<div class="modal-body">
					<table id="soundListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
						<thead>
							<tr>
								<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">音频ID</th>
								<th width="100">专辑名称</th>
								<th width="100">音频名称</th>
								<th width="100">音频简介</th>
								<th width="100">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="saveSoundToTag()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 音频标签弹窗框--> 
<div class="modal fade" id="SoundLabelDialog" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style=width:60%;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">音频标签添加</h4>
			</div>
			<div class="modal-body">
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">标签名称</label>
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
						<label class="col-sm-2 control-label no-padding-right text-right">分组</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<select>
										<option>主播</option>
										<option>专辑标签</option>
									</select>
								</div>
							</div>
						</div>
					</div>
			
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

