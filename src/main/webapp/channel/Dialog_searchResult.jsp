<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script>

//展示搜索结果
function showSearchResult(index){
	$("#searchResult").modal("show");
	var soundName = jsonData[index].soundName
	$.ajax({
			type: "POST",
			data:"soundName="+soundName,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/sound/search", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
				$("#soundSearchList tr:not(:has(th))").remove();
		   		$("#soundSearchList").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			},
			complete:function(){

			},
			success: function(data){
				var soundList = data.data;
				var newcontent='';
				
				$("#soundSearchList tr:not(:has(th))").remove();
				for(var i=0;i<soundList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="albumInfo" value="'+ soundList[i].id +'"></td>'
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].soundName+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+soundList[i].image+'" /></td>';
												newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].play_count+'</td>';
						newcontent = newcontent + '</tr>';
				}
				$("#soundSearchList").append(newcontent);
			}
	});	
}
</script>
<!-- 搜索结果列表弹窗--> 
<div class="modal fade" id="searchResult" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">音频搜索结果</h4>
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
					<table id="soundSearchList"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
						<thead>
							<tr>
								<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">音频ID</th>
								<th width="100">音频名称</th>
								<th width="100">音频图片</th>
								<th width="100">音频播放次数</th>
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