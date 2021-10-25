<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script>

//展示搜索详情
function showDetail(index){
	var searchName = jsonData[index].soundName
	$("#searchDetailList").modal("show");
	$.ajax({
		type: "POST",
		data:"searchName="+searchName,
		dataType: "json",
		url: "<%=request.getContextPath() %>/api/channel/getSearchDetail", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
		},
		complete:function(){
			
		},
		success: function(data){
			newcontent='';
			$("#soundSearchDetailList tr:not(:has(th))").remove();
			var searchList = data.data.items;
			for(var i=0;i<searchList.length;i++){
					newcontent = newcontent + '<tr>';	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="searchDetailIndex" value="'+ searchList[i].id +'"></td>'
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+searchList[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+searchList[i].soundName+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+searchList[i].userName+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].page+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].ISBN+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].remoteIP+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+albumList[i].insertDate+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
					newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteAlbumFromChannel('+albumList[i].id+')" data-toggle="modal" data-target="#answer-data">删除频道专辑</button></td>';
					newcontent = newcontent + '</tr>';
			}
			$('#soundSearchDetailList').append(newcontent);
		}
	});
}

</script>
<!-- 搜索结果列表弹窗--> 
<div class="modal fade" id="searchDetailList" tabindex="-1" role="dialog"
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
					<table id="soundSearchDetailList"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
						<thead>
							<tr>
								<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">ID</th>
								<th width="100">搜索关键字</th>
								<th width="100">用户名称</th>
								<th width="100">页码</th>
								<th width="100">ISBN</th>
								<th width="100">用户IP</th>
								<th width="100">搜索时间</th>
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