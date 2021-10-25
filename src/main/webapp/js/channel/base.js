/*
 * 	通过专辑ID获取专辑中的音频列表，并且弹窗显示
 * 
 * */
function showSoundsDialog(albumId){
	$("#SoundsDialog").modal("show");
	$.ajax({
			type: "POST",
			data:"albumId="+albumId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/getSoundsFromAlbum", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
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
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data">编辑专辑</button>';
						newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showSound('+i+')" data-toggle="modal" data-target="#answer-data">查看音频</button>';
						newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteQuestion('+i+')" data-toggle="modal" data-target="#answer-data">删除专辑</button></td>';
						newcontent = newcontent + '</tr>';
				}
				$('#albumsListTab').append(newcontent);
			}
		});	
	
}