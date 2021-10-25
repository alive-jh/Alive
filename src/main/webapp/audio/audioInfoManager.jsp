<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/local/zh_CN.js"></script>



<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>
<link rel="stylesheet" href="css/commen.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>

<script>
var pageSize = "${queryDto.pageSize}";//每页大小
var totalPageCount = "${resultPage.indexes}";//总页数
var totalCount = "${totalCount}";//总记录数
var page = "${queryDto.page}";
var now=new Date().getTime();
//修改每页记录条数
function changePageSize(el) {
	var newPageSize = el.value;
	$("#currentPage").val("1");
	$("#pageSize").val(newPageSize);
	document.forms['addarticle'].action = "articleManager";
	document.forms['addarticle'].submit();
}
//本地条件分页查询
function pageJump(page) {
	PPage("page_select", page, totalPageCount, "pageJump", true);
	$("#currentPage").val(page);
}
var newcontent = '';
var editor;
var data =${jsonStr};
var jsonData = ${jsonStr};

$(document).ready( function () {
	var message= "${message}";
	var errorCount = "${errorCount}";
	if(message != ''){
		if(errorCount ==1){
			message = message +'<a href="./showExcelMessage">查看详情</a>';
		}
		$('#message').html(message);
		$('#keyWBtn').modal('show');
	}
	var articleListTab = $('#articleListTab');
	if( data != ''){
			for(var i=0;i<data.infoList.length;i++){
					newcontent = newcontent + '<tr>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="50" height="50" src ="'+data.infoList[i].cover+'"></td>';
					
	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
	
					if(data.infoList[i].src!= '')
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><a target ="_blank" href="'+data.infoList[i].src+'">点击试听</a></td>';
					}
					else
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><i class="icon-remove  bigger-110"></td>';
					}
					if(data.infoList[i].cn!= '')
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><a target ="_blank" href="'+data.infoList[i].cn+'">点击试听</a></td>';
					}
					else
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><i class="icon-remove  bigger-110"></td>';
					}
					if(data.infoList[i].exp!= '')
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><a target ="_blank" href="'+data.infoList[i].exp+'">点击试听</a></td>';
					}
					else
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><i class="icon-remove  bigger-110"></td>';
					}
					if(data.infoList[i].mediainfo!= '')
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><a target ="_blank" href="'+data.infoList[i].mediainfo+'">点击查看</a></td>';
					}
					else
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><i class="icon-remove  bigger-110"></td>';
					}
					if(data.infoList[i].picrecog!= '')
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><a target ="_blank" href="'+data.infoList[i].picrecog+'">点击查看</a></td>';
					}
					else
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><i class="icon-remove  bigger-110"></td>';
					}
					
					if(data.infoList[i].status ==0){
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editUser('+i+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button>';
						newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="refreshCach('+data.infoList[i].id+')">刷新缓存</button>';
						newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showSourceBindList('+data.infoList[i].pid+')" data-toggle="modal" data-target="#sourceBindList"><i class="icon-edit bigger-120">查看</i></button>';
						newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="deleteArticle('+data.infoList[i].id+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>';
					}else{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editUser('+i+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button>';
						newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="refreshCach('+data.infoList[i].id+')">刷新缓存</button>';
						newcontent = newcontent + '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showSourceBindList('+data.infoList[i].pid+')" data-toggle="modal" data-target="#sourceBindList"><i class="icon-edit bigger-120">查看</i></button></td>';
					}
					newcontent = newcontent +'</tr>';
				}
				articleListTab.append(newcontent);
	}
})

function refreshCach(audioId){
	$.ajax({
			type: "GET",
			data:"noCach=true&audioId="+audioId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/app/getAudioInfo", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				alert("已经刷新");
			}
		});	
}
function deleteArticle(id){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['audioInfoForm'].action ="deleteAudioInfo?audioId="+id;
		document.forms['audioInfoForm'].submit();
	}
}


function editUser(index){
	if(index.length != '0' ){
		document.forms['audioInfoForm'].id.value = data.infoList[index].pid;
		document.forms['audioInfoForm'].cn.value = data.infoList[index].cn;
		document.forms['audioInfoForm'].exp.value = data.infoList[index].exp;
		document.forms['audioInfoForm'].src.value = data.infoList[index].src;
		document.forms['audioInfoForm'].picrecog.value = data.infoList[index].picrecog;
		document.forms['audioInfoForm'].name.value = data.infoList[index].name;
		document.forms['audioInfoForm'].mediainfo.value = data.infoList[index].mediainfo;
		document.forms['audioInfoForm'].cover.value = data.infoList[index].cover;
		$("#cn1").attr('src',data.infoList[index].cn);
		$("#exp1").attr('src',data.infoList[index].exp); 
		$("#src1").attr('src',data.infoList[index].src); 
		$("#picrecog1").attr('src',data.infoList[index].picrecog); 
		$("#cover1").attr('src',data.infoList[index].cover);  
	}else{
		document.forms['audioInfoForm'].id.value = '';
		document.forms['audioInfoForm'].cn.value =  '';
		document.forms['audioInfoForm'].exp.value =  '';
		document.forms['audioInfoForm'].src.value =  '';
		document.forms['audioInfoForm'].picrecog.value =  '';
		document.forms['audioInfoForm'].name.value =  '';
		document.forms['audioInfoForm'].mediainfo.value =  '';
		document.forms['audioInfoForm'].cover.value =  '';
	}
}

function setOrderId(index){
	document.forms['materialForm'].orderId.value = data.infoList[index].id;
	document.forms['materialForm'].userName.value = data.infoList[index].userName;
	document.forms['materialForm'].mobile.value = data.infoList[index].mobile;
	document.forms['materialForm'].address.value = data.infoList[index].address;
}
function upExcel(){
	document.forms['excelForm'].action = "./upExcel";
	document.forms['excelForm'].submit();
}

function show(file,name,media){

		//document.getElementById(""+name).value = document.getElementById(""+file).value;
		var span = $("#"+file).prev();
		var formData = new FormData(); 
        formData.append('file',$("#"+file)[0].files[0]);
        span.css("display","inline");
        span.append("<img src='<%=request.getContextPath() %>/css/images/loading.gif' />正在上传中，请等待...");
        $.ajax({
                url:"<%=request.getContextPath()%>/api/uploadFiles",
                type:"post",
                data:formData,
                processData:false,
                contentType:false,
                success:function(result){
                    if(result.key!=null&&result.key!=""&&result.key!=undefined){
                    $("#"+name).val(result.key);
                    $("#"+media).attr('src',result.key);
                    alert("上传成功");
                    span.html("");
                    }
                },
                error:function(e){
                    alert("上传文件失败");
                }});			
}

function selectFile(fnUpload) {
	var filename = fnUpload.value; 
	var mime = filename.toLowerCase().substr(filename.lastIndexOf(".")); 
	if(mime!=".xls"){ 
		alert("请选择Excel文件,暂不支持Excel2010!");
		return;
	}
}

function off(){
		$("#cn1").attr('src','');
		$("#exp1").attr('src',''); 
		$("#src1").attr('src',''); 
		$("#picrecog1").attr('src',''); 
		$("#cover1").attr('src','');
}
</script>

<div class="main-content">
	<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="main.htm">首页</a>							</li>
							<li class="active">在线课堂管理</li>
							<li class="active">资源管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

	<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post"  name="searchForm" action="<%=request.getContextPath()%>/app/audioInfoManager" style="float: left;width:100%">
								<table class="filterTable">
									
									<tr>
										<td>资源名称</td>
										<td><input type="text" name="name" value="${queryDto.name}"></td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										
										<td>&nbsp;</td>
										<td colspan="3">&nbsp;
										</td>
									</tr>
								</table>
							   
									<div class="btn-group col-md-12">
										 <button type="submit" class="btn btn-purple ml10 mb10">查询
										 <i class="icon-search icon-on-right bigger-110"></i>
										 </button>
										 <button type="buttion" class="btn btn-purple ml10 mb10"  onclick="editUser('')" data-toggle="modal" data-target="#mymodal-data">添加
										 <i class="icon-plus icon-on-right bigger-110"></i>
										 </button>
								</div>	
						
							</form>
						</div><!-- /.page-header -->
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
	
								<div class="row">
									<div class="col-xs-12">
										<div class="table-responsive" style="text-align:right;">
											<table id="articleListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														
														<th width="30">封面</th>
														
														<th width="300">名称</th>
														<th width="20">原音</th>
														<th width="20">翻译</th>
														<th width="20">跟读</th>
														<th width="20">分词</th>
														<th width="20">书页识别</th>
														<th width="120">操作</th>
													</tr>
												</thead>
	
												
											</table>
											    
						<f:page page="${resultPage}" url="./audioInfoManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
</div><!-- /.main-content -->

<!-- 模态弹出窗内容 -->
<form class="article" method="post" name="audioInfoForm" enctype="multipart/form-data" action="<%=request.getContextPath()%>/app/saveAudioInfo">
	<input type="hidden" name="id" id="id" value="">

	<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1100px;zheneight: 500px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
						<span class="sr-only">Close</span></button>
					<h4 class="modal-title">添加/修改书籍分类</h4></div>
				<div class="modal-body">
					<div id="divAccount" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频名称:</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<input class="form-control" name="name" id="name" type="text" data-rule="音频名称:required;" style="max-width:350px;width:100%;" /></div>
							</div>
						</div>
					</div>
					<div id="divAccount" class="form-group" style="height:45px;">
						<label class="col-sm-2 control-label no-padding-right text-right">音频封面:</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<img id="cover1" src="" style="width: 100px;height: 100px;margin-top: 5px;">
									<input class="form-control" name="cover" id="cover" type="text" style="max-width:350px;width:100%;display:none" />
									<div style="display: inline;position: relative;">
										<button type="buttion" class="btn btn-info btn-sm">上传图片</button>
										<span></span>
										<input type="file" name="file1" id="file1" onchange="show('file1','cover','cover1')" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;" /></div>
								</div>
							</div>
						</div>
					</div>
					<div id="divAccount" class="form-group" style="height:45px;">
						<label class="col-sm-2 control-label no-padding-right text-right">原音音频:</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<audio id="src1" src="" controls="controls" style="margin-top: 10px;"></audio>
									<input class="form-control" name="src" id="src" type="text" style="max-width:350px;width:100%; display:none" />
									<div style="display: inline;position: relative;">
										<button type="buttion" class="btn btn-info btn-sm">上传音频</button>
										<span></span>
										<input type="file" name="file2" id="file2" onchange="show('file2','src','src1')" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;" /></div>
								</div>
							</div>
						</div>
					</div>
					<div id="divAccount" class="form-group" style="height:45px;">
						<label class="col-sm-2 control-label no-padding-right text-right">翻译音频:</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<audio id="cn1" src="" controls="controls"></audio>
									<input class="form-control" name="cn" id="cn" type="text" style="max-width:350px;width:100%; display:none" />
									<div style="display: inline;position: relative;">
										<button type="buttion" class="btn btn-info btn-sm">上传音频</button>
										<span></span>
										<input type="file" name="file3" id="file3" onchange="show('file3','cn','cn1')" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;" /></div>
								</div>
							</div>
						</div>
					</div>
					<div id="divAccount" class="form-group" style="height:45px;">
						<label class="col-sm-2 control-label no-padding-right text-right">跟读音频:</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<audio id="exp1" src="" controls="controls"></audio>
									<input class="form-control" name="exp" id="exp" type="text" style="max-width:350px;width:100%;display:none" />
									<div style="display: inline;position: relative;">
										<button type="buttion" class="btn btn-info btn-sm">上传音频</button>
										<span></span>
										<input type="file" name="file4" id="file4" onchange="show('file4','exp','exp1')" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;" /></div>
								</div>
							</div>
						</div>
					</div>
					<div id="divAccount" class="form-group" style="height:45px;">
						<label class="col-sm-2 control-label no-padding-right text-right">分词文件:</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<input class="form-control" name="mediainfo" id="mediainfo" type="text" style="max-width:350px;width:100%;margin-top: 10px;" />
									<div style="display: inline;position: relative;">
										<button type="buttion" class="btn btn-info btn-sm">上传文件</button>
										<span></span>
										<input type="file" name="file5" id="file5" onchange="show('file5','mediainfo')" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;" /></div>
								</div>
							</div>
						</div>
					</div>
					<div id="divAccount" class="form-group" style="height:45px;">
						<label class="col-sm-2 control-label no-padding-right text-right">书页识别:</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<img id="picrecog1" src="" style="width: 100px;height: 100px;">
									<input class="form-control" name="picrecog" id="picrecog" type="text" style="max-width:350px;width:100%;display:none" />
									<div style="display: inline;position: relative;">
										<button type="buttion" class="btn btn-info btn-sm">上传文件</button>
										<span></span>
										<input type="file" name="file6" id="file6" onchange="show('file6','picrecog','picrecog1')" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;" /></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="off()">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>

<!-- 模态弹出窗内容 -->
<form class="form-horizontal" role="form" name="materialForm" method="post" action="<%=request.getContextPath() %>/material/saveKeywordByMaterialId">
	<input type="hidden" name="orderId">
	<input type="hidden" name="express">
	<div class="modal  fade" id="keyWBtn" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
						<span class="sr-only">Close</span></button>
					<h4 class="modal-title">书籍导入</h4></div>
				<div class="modal-body">
					<div id="divAccount" class="form-group" style="height:35px;">
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<label class="col-sm-6 control-label no-padding-right text-right">
										<span id="message"></span>
									</label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>


<!-- 添加资源绑定课程列表--> 
		
		
<script>
var currentAudioId;
function showSourceBindList(audioId){
	var audioId = audioId;
	$("#audioId").val(audioId);
	$.ajax({
			type: "GET",
			data:"audioId="+audioId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/app/getAudioBindCourseList", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				newcontent='';
				$("#CourseAudioListTab tr:not(:has(th))").remove();
				soundList = data.data;
				for(var i=0;i<soundList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+soundList[i].name+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="http://wechat.fandoutech.com.cn/wechat/wechatImages/mall/'+soundList[i].logo1+'" /></td>';
						newcontent = newcontent + '</tr>';
				}
				$('#CourseAudioListTab').append(newcontent);
			}
		});	
}
</script>
<div class="modal fade" id="sourceBindList" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style=width:1000px;height: auto;">
		<div class="modal-content">
			<div class="modal-header">
				<input id="audioId" style="display:none;">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">资源绑定列表</h4>
				<button class="btn btn-purple ml10 mb10" onclick="addCourseToAudio()" data-toggle="modal" value="添加">
				添加</button>
			</div>
			<div class="modal-body">
				
				<table id="CourseAudioListTab" name="CourseAudioListTab"
							class="table table-striped table-bordered table-hover"
							style="word-break:break-all">
							<thead>
								<tr>
									<th width="15%">课程ID</th>
									<th width="10%">课程名字</th>
									<th width="5%">课程海报1</th>
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

		
		
<script>
function addCourseToAudio(){
	$("#addCurriculumAudio").modal("show");
}
function searchMallProduct(){
	var courseStr = $("#courseStr").val(); 
	$.ajax({
			type: "GET",
			data:"name="+courseStr,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/seaechMall", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
				$("#CourseListTab tr:not(:has(th))").remove();
		   		$("#CourseListTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
			},
			complete:function(){
				
			},
			success: function(data){
				newcontent='';
				$("#courseListTab tr:not(:has(th))").remove();
				courseList = data.infoList;
				for(var i=0;i<courseList.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><input type="checkbox" name="soundInfo" value="'+ courseList[i].id +'"></td>'
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+courseList[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+courseList[i].name+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+courseList[i].logo+'" /></td>';
						newcontent = newcontent + '</tr>';
				}
				$('#courseListTab').append(newcontent);
			}
		});	
}

function saveCourseToAudio(){
	$("#addCurriculumAudio").modal("hide");
	 var audioId = $("#audioId").val();
     var sounds = $("input[name='soundInfo']:checked").val([]);
     var pidList = "";
     for(var i=0;i<sounds.length;i++){
 		pidList = pidList + sounds[i].value + ",";
 	 }
 	 var formData = new FormData();
 	 formData.append("audioId",audioId);
 	 formData.append("pidList",pidList);
     $.ajax({
          url: '<%=request.getContextPath() %>/app/saveCourseToAudio' ,
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
			  var audioId = $("#audioId").val();
			  showSourceBindList(audioId);
          },
          error: function (returndata) {
          
          }
     });
}
</script>
<!-- 添加资源和课程的对应关系--> 
<div class="modal fade" id="addCurriculumAudio" tabindex="-1" role="dialog" data="1" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:900px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
					<span class="sr-only">Close</span></button>
				<h4 class="modal-title">添加课程绑定关系</h4></div>
			<div class="modal-body">
				<div id="divInput" class="form-group" style="height:35px;display: none;">
					<label class="col-sm-2 control-label no-padding-right text-right">序号</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="id" id="id" type="text" style="max-width:650px;width:100%;" readonly/></div>
						</div>
					</div>
				</div>
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">课程名称：</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="courseStr" id="courseStr" type="text" style="max-width:650px;width:80%;" />&nbsp;&nbsp;&nbsp;&nbsp;
								<button onclick="searchMallProduct()" style="max-width:650px;width:15%;">搜索课程</button></div>
						</div>
					</div>
				</div>
				<div class="modal-body">
					<table id="courseListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
						<thead>
							<tr>
								<th width="20">
									<input type="checkbox" name="" value="" onclick=""></th>
								<th width="100">课程ID</th>
								<th width="100">课程名字</th>
								<th width="100">图片</th></tr>
						</thead>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="saveCourseToAudio()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div>

			</div>
		</div>
	</div>
</div>