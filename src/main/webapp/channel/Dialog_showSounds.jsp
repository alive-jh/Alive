<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	
<script>
var currentAlbumId;
function addSoundToAlbum(){
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
				albumList = data.data.items;
				var newcontent='';
				
				$("#soundListTab tr:not(:has(th))").remove();
				for(var i=0;i<albumList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="soundInfo" value="'+ albumList[i].id +'"></td>'
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].albumId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].name+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+albumList[i].image+'" /></td>';
						newcontent = newcontent + '</tr>';
				}
				$("#soundListTab").append(newcontent);
			}
	});	
}

function saveSoundToAlbum(){
	$("#addSoundToAlbumDialog").modal("hide");
	 var albumId = currentAlbumId;
     var sounds = $("input[name='soundInfo']:checked").val([]);
     var soundIdList = "";
     for(var i=0;i<sounds.length;i++){
 		soundIdList = soundIdList + sounds[i].value + ",";
 	 }
 	 var formData = new FormData();
 	 formData.append("albumId",albumId);
 	 formData.append("soundIdList",soundIdList);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveSoundToAlbum' ,
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
			  showSounds(albumId);
          },
          error: function (returndata) {
          
          }
     });
}
var soundList;
function showSounds(id){
	currentAlbumId = id;
	$("#SoundsDialog").modal("show");
	$.ajax({
			type: "POST",
			data:"albumId="+id + "&page=1&pageSize=1000",
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/getSoundFromAlbum", 
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
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].soundId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].name+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+soundList[i].image+'" /></td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >不显示</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].playTimes+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><audio id="music1'+i+'" src="'+soundList[i].playUrl+'"  loop="loop">你的浏览器不支持audio标签。</audio>' +'<a id="audio_btn" onclick="audioBtnClick1('+i+')" ><img src="../images/pause.png" width="48" height="50" id="music_btn1'+i+'" border="0"></a></td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].albumName+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteSoundFromAlbum('+soundList[i].albumId+','+soundList[i].id+')" data-toggle="modal" data-target="#answer-data">从专辑中删除音频</button>';				
						newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onClick="moveUp(this)" class="btn btn-xs btn-info">上移</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onClick="moveDown(this)" class="btn btn-xs btn-info">下移</a>&nbsp;&nbsp;&nbsp;&nbsp<a href="javascript:void(0)" onClick="moveTop(this)" class="btn btn-xs btn-info">置顶</a></td>';
						newcontent = newcontent + '</tr>';
				}
				$('#soundsListTab').append(newcontent);
			}
		});	
	
}

//删除专辑和音频的对应关系
function deleteSoundFromAlbum(albumId,soundId){
	if(window.confirm('你确定要从专辑中删除音频吗？')){
          
    }else{
          return false;
    }
	$.ajax({
			type: "POST",
			data:"albumId="+albumId + "&soundId=" + soundId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/deleteSoundFromAlbum", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				showSounds(albumId);
			}
		});	

}

function moveUp(obj)  
{  
    var current=$(obj).parent().parent();  
    var prev=current.prev();  
    if(current.index()>0)  
    {  
        current.insertBefore(prev);  
    }  
}  
function moveDown(obj)  
{  
    var current=$(obj).parent().parent();  
    var next=current.next();  
    if(next)  
    {  
        current.insertAfter(next);  
    }  
}  

function moveTop(obj)  
{  
    var current=$(obj).parent().parent();  
    var prev=current.prev();
    if(current.index() != 0){
    	current.prepend(prev);
    }  
    
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
				<button class="btn btn-purple ml10 mb10" onclick="addSoundToAlbum()" data-toggle="modal" value="添加">
				添加音频到专辑</button>
				<button class="btn btn-purple ml10 mb10" onclick="addSoundToRecommend()" data-toggle="modal" value="添加">
				添加音频到推荐</button>
			</div>
			<div class="modal-body">
				
				<table id="soundsListTab" name="soundsListTab"
							class="table table-striped table-bordered table-hover"
							style="word-break:break-all">
							<thead>
								<tr>
									<th width="2%"><input type="checkbox" name="" value="" onclick=""></th>
									<th width="5%">音频ID</th>
									<th width="5%">XMLY音频ID</th>
									<th width="15%">音频名称</th>
									<th width="10%">音频海报</th>
									<th width="5%">简介</th>
									<th width="5%">播放次数</th>
									<th width="5%">播放地址</th>
									<th width="5%">专辑名称</th>
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

<!-- 专辑添加音频弹窗框--> 
<div class="modal fade" id="addSoundToAlbumDialog" tabindex="-1" role="dialog" data="1"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">专辑添加音频</h4>
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
								<th width="100">xmly音频ID</th>
								<th width="100">音频名称</th>
								<th width="100">图片</th>
							</tr>
						</thead>
					</table>
				</div>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="saveSoundToAlbum()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="addSoundToRecommend.jsp"%>