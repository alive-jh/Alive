<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script>

//展示搜索结果

var tagList;
function showTagList(index){
	$("#TagList").modal("show");
	var groupId = jsonData[index].id
	$.ajax({
			type: "POST",
			data:"groupId="+groupId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/searchTagListFromGroupId", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
				$("#tagListTable tr:not(:has(th))").remove();
		   		$("#tagListTable").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			},
			complete:function(){

			},
			success: function(data){
				tagList = data.data.items;
				var newcontent='';
				$("#tagListTable tr:not(:has(th))").remove();
				for(var i=0;i<tagList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumInfo" value="'+ tagList[i].id +'"></td>'
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].tagName+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+tagList[i].image+'" /></td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].intro+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].status+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+tagList[i].sortId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="addTag('+i+')" data-toggle="modal">编辑</button>';
						newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showChannels('+tagList[i].id+')" data-toggle="modal">频道</button>';
						newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showAlbums('+tagList[i].id+')" data-toggle="modal">专辑</button>';
						newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showSounds('+tagList[i].id+')" data-toggle="modal">音频</button>';
						newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteTag('+tagList[i].id+')" data-toggle="modal">删除标签</button></td>';
						newcontent = newcontent + '</tr>';
				}
				$("#tagListTable").append(newcontent);
			}
	});	
}

function addTag(index){
	$("#addTag").modal("show");
	if(index = undefined ){
		$("#tagName").val();
		$("#image").val();
		$("#intro").val();
		
	}else{
		alert(tagList[index].id);
		$("#tagName").val(tagList[index].tagName);
		$("#image").val(tagList[index].image);
		$("#intro").val(tagList[index].intro);
	}
}


function deleteTag(id){
	alert(id);
	var id = id;
	$.ajax({
			type: "POST",
			data:"id="+id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/deleteTagFromGroup", 
			context: document.body, 
			beforeSend:function(){
			},
			complete:function(){

			},
			success: function(data){
			}
	});
}

function updateData(){
	alert(id);
	var id = id;
	
	$.ajax({
			type: "POST",
			data:"id="+id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/saveTagInfo", 
			context: document.body, 
			beforeSend:function(){
			},
			complete:function(){

			},
			success: function(data){
			}
	});


}

var currentChannelSourceList;
var currentAlbumSourceList;
var currentSoundSourceList;
//展示频道列表
function showChannels(tagId){
	$("#showChannel").modal("show");
	var tagId = tagId;
	$.ajax({
		type: "POST",
		data:"tagId="+tagId,
		dataType: "json",
		url: "<%=request.getContextPath() %>/api/channel/getChannelFromTag", 
		context: document.body, 
		beforeSend:function(){
		},
		complete:function(){

		},
		success: function(data){
			var sourceList = data.data.items;
			currentChannelSourceList = sourceList;
			var newcontent='';
			$("#channelListTab tr:not(:has(th))").remove();
			for(var i=0;i<sourceList.length;i++){
					newcontent = newcontent + '<tr>';	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumInfo" value="'+ sourceList[i].id +'"></td>'
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+sourceList[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+sourceList[i].name+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+sourceList[i].image+'" /></td>';
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;" >'+sourceList[i].intro+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+sourceList[i].fans+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >&nbsp;&nbsp;';
					newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteChannelFromTag('+i+')">从标签中删除频道</button></td>';
					newcontent = newcontent + '</tr>';
			}
			$("#channelListTab").append(newcontent);
		}
	});

}

function deleteChannelFromTag(index){
	var id = currentChannelSourceList[index].id;
	var formData = new FormData();
	formData.append("sourceId",id);
	formData.append("type","channel");
	$.ajax({
        type: 'POST',
        data:formData,
        dataType: "json",  
        async: false,
        contentType: false,
        processData: false,
		url: "<%=request.getContextPath() %>/api/channel/deleteSourceFromTag", 
		context: document.body, 
		beforeSend:function(){
		},
		complete:function(){

		},
		success: function(data){
			currentChannelSourceList.remove(index);
			var newcontent='';
			$("#channelListTab tr:not(:has(th))").remove();
			for(var i=0;i<currentChannelSourceList.length;i++){
					newcontent = newcontent + '<tr>';	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumInfo" value="'+ currentChannelSourceList[i].id +'"></td>'
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+currentChannelSourceList[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+currentChannelSourceList[i].name+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+currentChannelSourceList[i].image+'" /></td>';
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;" >'+currentChannelSourceList[i].intro+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+currentChannelSourceList[i].fans+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >&nbsp;&nbsp;';
					newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteChannelFromTag('+i+')">从标签中删除频道</button></td>';
					newcontent = newcontent + '</tr>';
			}
			$("#channelListTab").append(newcontent);
		}
	});

}

function deleteAlbumFromTag(index){
	var id = currentAlbumSourceList[index].id;
	var formData = new FormData();
	formData.append("sourceId",id);
	formData.append("type","ablum");
	$.ajax({
        type: 'POST',
        data:formData,
        dataType: "json",  
        async: false,
        contentType: false,
        processData: false,
		url: "<%=request.getContextPath() %>/api/channel/deleteSourceFromTag", 
		context: document.body, 
		beforeSend:function(){
		},
		complete:function(){

		},
		success: function(data){

			currentAlbumSourceList.remove(index);
			var newcontent='';
			$("#albumListTab tr:not(:has(th))").remove();
			for(var i=0;i<currentAlbumSourceList.length;i++){
					newcontent = newcontent + '<tr>';	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumInfo" value="'+ currentAlbumSourceList[i].id +'"></td>'
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+currentAlbumSourceList[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+currentAlbumSourceList[i].name+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+currentAlbumSourceList[i].image+'" /></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >&nbsp;&nbsp;';
					newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteAlbumFromTag('+i+')">从标签中删除专辑</button></td>';
					newcontent = newcontent + '</tr>';
			}
			$("#albumListTab").append(newcontent);
		}
	});

}

function deleteSoundFromTag(index){
	var id = currentSoundSourceList[index].id;
	var formData = new FormData();
	formData.append("sourceId",id);
	formData.append("type","sound");
	$.ajax({
        type: 'POST',
        data:formData,
        dataType: "json",  
        async: false,
        contentType: false,
        processData: false,
		url: "<%=request.getContextPath() %>/api/channel/deleteSourceFromTag", 
		context: document.body, 
		beforeSend:function(){
		},
		complete:function(){

		},
		success: function(data){
			currentSoundSourceList.remove(index);
			var newcontent='';
			$("#soundListTab tr:not(:has(th))").remove();
			for(var i=0;i<currentSoundSourceList.length;i++){
					newcontent = newcontent + '<tr>';	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumInfo" value="'+ currentSoundSourceList[i].id +'"></td>'
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+currentSoundSourceList[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+currentSoundSourceList[i].name+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+currentSoundSourceList[i].image+'" /></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+currentSoundSourceList[i].playTimes+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><a href="'+currentSoundSourceList[i].playUrl+'" target="_blank">试听</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >&nbsp;&nbsp;';
					newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteSoundFromTag('+i+')">从标签中删除音频</button></td>';
					newcontent = newcontent + '</tr>';
			}
			$("#soundListTab").append(newcontent);
		}
	});
	
}


//展示专辑
function showAlbums(tagId){
	$("#showAlbum").modal("show");
	var tagId = tagId;
	var formData = new FormData();
	formData.append("tagId",tagId);
	formData.append("page","1");
	formData.append("pageSize","10000");
	$.ajax({
        type: 'POST',
        data:formData,
        dataType: "json",  
        async: false,
        contentType: false,
        processData: false,
		url: "<%=request.getContextPath() %>/api/channel/getAlbumFromTag", 
		context: document.body, 
		beforeSend:function(){
		},
		complete:function(){

		},
		success: function(data){
			var sourceList = data.data.items;
			currentAlbumSourceList = sourceList;
			var newcontent='';
			$("#albumListTab tr:not(:has(th))").remove();
			for(var i=0;i<sourceList.length;i++){
					newcontent = newcontent + '<tr>';	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumInfo" value="'+ sourceList[i].id +'"></td>'
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+sourceList[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+sourceList[i].name+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+sourceList[i].image+'" /></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >&nbsp;&nbsp;';
					newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteAlbumFromTag('+i+')">从标签中删除专辑</button></td>';
					newcontent = newcontent + '</tr>';
			}
			$("#albumListTab").append(newcontent);
		}
	});

}


//展示标签下的音频列表
function showSounds(tagId){
	$("#showSound").modal("show");
	var tagId = tagId;
	var formData = new FormData();
	formData.append("tagId",tagId);
	formData.append("page","1");
	formData.append("pageSize","10000");
	$.ajax({
        type: 'POST',
        data:formData,
        dataType: "json",  
        async: false,
        contentType: false,
        processData: false,
		url: "<%=request.getContextPath() %>/api/channel/getSoundFromTag", 
		context: document.body, 
		beforeSend:function(){
		},
		complete:function(){

		},
		success: function(data){
			var sourceList = data.data.items;
			currentSoundSourceList = sourceList;
			var newcontent='';
			$("#soundListTab tr:not(:has(th))").remove();
			for(var i=0;i<sourceList.length;i++){
					newcontent = newcontent + '<tr>';	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumInfo" value="'+ sourceList[i].id +'"></td>'
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+sourceList[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+sourceList[i].name+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+sourceList[i].image+'" /></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+sourceList[i].playTimes+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><a href="'+sourceList[i].playUrl+'" target="_blank">试听</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >&nbsp;&nbsp;';
					newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteSoundFromTag('+i+')">从标签中删除音频</button></td>';
					newcontent = newcontent + '</tr>';
			}
			$("#soundListTab").append(newcontent);
		}
	});

}
</script>
<!-- 标签列表弹窗--> 
<div class="modal fade" id="TagList" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">标签列表</h4>
			</div>
			<div class="modal-body">
				<div>
					<button onclick="addTag();">添加标签</button>
				</div>
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
					<div class="modal-body">
					<table id="tagListTable"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
						<thead>
							<tr>
								<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">序号ID</th>
								<th width="100">名称</th>
								<th width="100">图片</th>
								<th width="100">简介</th>
								<th width="100">状态</th>
								<th width="100">排序ID</th>
								<th width="100">操作</th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="saveAlbumsToChannel()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 添加标签弹窗--> 
<div class="modal fade" id="addTag" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">标签添加</h4>
				</div>
				<div class="modal-body">
				
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="tagName" id="tagName"
										type="text"
										style="max-width:650px;width:100%;">
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">图片</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="image" id="image"
										type="text" 
										style="max-width:650px;width:80%;" />&nbsp;&nbsp;&nbsp;&nbsp;
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
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="updateData()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 显示频道弹窗--> 
<div class="modal fade" id="showChannel" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1200px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">频道列表</h4>
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
					<div class="modal-body">
					<table id="channelListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left; table-layout: fixed;width:XXXpx;">
						<thead>
							<tr>
								<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">序号ID</th>
								<th width="100">名称</th>
								<th width="100">图片</th>
								<th width="100">简介</th>
								<th width="100">粉丝数</th>
								<th width="100">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 显示专辑弹窗--> 
<div class="modal fade" id="showAlbum" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1200px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">专辑列表</h4>
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
					<div class="modal-body">
					<table id="albumListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left; table-layout: fixed;width:XXXpx;">
						<thead>
							<tr>
								<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">序号ID</th>
								<th width="100">名称</th>
								<th width="100">图片</th>
								<th width="100">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 显示音频弹窗--> 
<div class="modal fade" id="showSound" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1200px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">音频列表</h4>
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
					<div class="modal-body">
					<table id="soundListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
						<thead>
							<tr>
								<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">序号ID</th>
								<th width="100">名称</th>
								<th width="100">图片</th>
								<th width="100">播放次数</th>
								<th width="100">播放地址</th>
								<th width="100">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>