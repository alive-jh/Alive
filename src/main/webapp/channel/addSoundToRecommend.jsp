<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script>

function contains(arr, obj) {  
    var i = arr.length;  
    while (i--) {  
        if (arr[i] == obj) {  
            return true;  
        }  
    }  
    return false;  
}  

function addSoundToRecommend(){
	 $("#addSoundToRecommendDialog").modal("show");
     var sounds = $("input[name='soundInfo']:checked").val([]);

	 var soundsInfo = [];
     var soundIds = new Array();
     for(var i=0;i<sounds.length;i++){
 		soundIds.push(sounds[i].value);
 	 }

     for(var i=0;i<soundList.length;i++){
     	var result = contains(soundIds,soundList[i].id);
     	if(result == true){
     		soundsInfo.push(soundList[i]);
     	}else{

     	}
     	
     }
     if(soundsInfo.length == 0){
		alert("请选择音频");
		$("#addSoundToRecommendDialog").modal("hide");
	 }else{
		newcontent='';
		$("#recommendSoundList tr:not(:has(th))").remove();
		for(var i=0;i<soundsInfo.length;i++){
				newcontent = newcontent + '<tr>';	
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="soundsRecommend" value="'+ soundsInfo[i].id +'" checked></td>'
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundsInfo[i].id+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundsInfo[i].name+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+soundsInfo[i].image+'" /></td>';
				newcontent = newcontent + '</tr>';
		}
		$('#recommendSoundList').append(newcontent);
	}
}

function saveRecommendSound(){
    var sounds = $("input[name='soundsRecommend']:checked").val([]);
	var tag = $("select[ name='tag' ] ").val();
	var soundIds = "";
	for(var i=0;i<sounds.length;i++){
		soundIds = soundIds +  sounds[i].value + ",";
	}
	var formData = new FormData();
	formData.append("tag",tag);
	formData.append("soundIds",soundIds);
	$.ajax({
		 url: "<%=request.getContextPath() %>/api/channel/saveRecommendSound", 
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
          		alert("添加音频到推荐成功！");
              	$("#addSoundToRecommendDialog").modal("hide");
          },
          error: function (returndata) {
          	alert("保存失败，请稍后再试。");
          }
		});	
}

</script>



<!-- 专辑添加音频到推荐弹窗框--> 
<div class="modal fade" id="addSoundToRecommendDialog" tabindex="-1" role="dialog" data="0"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
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
					<table id="recommendSoundList"
									class="table table-striped table-bordered table-hover"
									style="text-align:left;">
									<thead>
										<tr>
											<th width="20"><input type="checkbox" name="" value="" onclick="" checked></th>
											<th width="50">音频ID</th>
											<th width="100">音频名称</th>
											<th width="100">音频海报</th>
										</tr>
									</thead>
	
						</table>
					</div>		
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" onclick="saveRecommendSound()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>