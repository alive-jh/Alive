<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script>

function addAlbumToChannel(){
	$("#addAlbumToChannel").modal("show");

}

//专辑搜索
function searchAlbums(){
	var albumNameStr = $(" input[ name='albumNameStr' ] ").val();
	$.ajax({
			type: "POST",
			data:"albumNameStr="+albumNameStr,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/searchAlbums", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
				$("#albumListTab tr:not(:has(th))").remove();
		   		$("#albumListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			},
			complete:function(){
				
			},
			success: function(data){
				albumList = data.data.items;
				var newcontent='';
				
				$("#albumListTab tr:not(:has(th))").remove();
				for(var i=0;i<albumList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumInfo" value="'+ albumList[i].id +'"></td>'
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].albumId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].name+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+albumList[i].image+'" /></td>';
						newcontent = newcontent + '</tr>';
				}
				$("#albumListTab").append(newcontent);
			}
	});	
}

//保存频道和专辑的对应关系
function saveAlbumsToChannel(){
 	$("#addAlbumToChannel").modal("hide");
	 var channelId = currentChannelId;

     var albums = $("input[name='albumInfo']:checked").val([]);
     var albumsIdList = "";
     for(var i=0;i<albums.length;i++){
 		albumsIdList = albumsIdList + albums[i].value + ",";
 	 }
 	 var formData = new FormData();
 	 formData.append("albumsIds",albumsIdList);
 	 formData.append("channelId",channelId);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveAlbumsToChannel' ,
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
			  showAlbums(channelId);
          },
          error: function (returndata) {
          
          }
     });

}

function showAlbums(id){
	currentChannelId = id;
	$("#albumsList").modal("show");
	var formData = new FormData();
 	 formData.append("channelId",id);
 	 formData.append("page","1");
 	 formData.append("pageSize","1000");
	$.ajax({
			type: "POST",
			data:"channelId="+id + "&page=1&pageSize=1000",
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/getAlbumsFromChannel", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
				$("#albumsListTab tr:not(:has(th))").remove();
		   		$("#albumsListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			},
			complete:function(){
				
			},
			success: function(data){
				newcontent='';
				$("#albumsListTab tr:not(:has(th))").remove();
				albumList = data.data.items;
				for(var i=0;i<albumList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="'+ albumList[i].id + '" value="checkBox_'+ albumList[i].id +'"></td>'
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].albumId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].name+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+albumList[i].image+'" /></td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].channelId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].status+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].sortId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
						newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showSounds('+albumList[i].id+')" data-toggle="modal">查看专辑下的音频</button>';
						newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteAlbumFromChannel('+albumList[i].id+')" data-toggle="modal" data-target="#answer-data">删除频道专辑</button></td>';
						newcontent = newcontent + '</tr>';
				}
				$('#albumsListTab').append(newcontent);
			}
		});	
	
}
//删除专辑和频道的对应关系
function deleteAlbumFromChannel(albumId){
	var channelId = currentChannelId;
	if(window.confirm('你确定要从改频道删除专辑吗？')){
          
    }else{
          return false;
    }
	$.ajax({
			type: "POST",
			data:"channelId="+channelId + "&albumId=" + albumId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/deleteAlbumFromChannel", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				showAlbums(channelId);
			}
		});	

}
</script>
<!-- 专辑列表弹窗--> 
<div class="modal fade" id="albumsList" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style=width:auto;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">专辑列表</h4>
				<button class="btn btn-purple ml10 mb10" onclick="addAlbumToChannel()" data-toggle="modal" value="添加">
				为频道添加专辑</button>
				<!-- <button class="btn btn-purple ml10 mb10" onclick="addAlbumToRecommend()" data-toggle="modal" value="添加">
				添加到推荐</button>--> 
			</div>
			<div class="modal-body">
				
				<table id="albumsListTab" name="albumsListTab"
							class="table table-striped table-bordered table-hover"
							style="word-break:break-all">
							<thead>
								<tr>
									<th width="2%"><input type="checkbox" name="" value="" onclick=""></th>
									<th width="5%">专辑ID</th>
									<th width="10%">XMLY专辑ID</th>
									<th width="20%">专辑名称</th>
									<th width="10%">专辑海报</th>
									<th width="5%">频道ID</th>
									<th width="5%">状态</th>
									<th width="5%">排序ID</th>
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


<!-- 频道添加专辑弹窗框--> 
<div class="modal fade" id="addAlbumToChannel" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">频道添加专辑</h4>
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
					<label class="col-sm-2 control-label no-padding-right text-right">专辑名称：</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="albumNameStr" id="albumNameStr"
									type="text" 
									style="max-width:650px;width:80%;" />&nbsp;&nbsp;&nbsp;&nbsp;
								<button onclick="searchAlbums()" style="max-width:650px;width:15%;">搜索专辑</button>
							</div>
						</div>
					</div>
				</div>

					<div class="modal-body">
					<table id="albumListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
						<thead>
							<tr>
								<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">专辑ID</th>
								<th width="100">专辑名称</th>
								<th width="100">图片</th>
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