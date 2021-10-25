<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/validator/jquery.validator.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/validator/local/zh_CN.js"></script>

<script charset="utf-8"
	src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8"
	src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>
<link rel="stylesheet" href="css/commen.css" />

<script>


var newcontent = '';

var jsonData = null;

$(document).ready( function () {
	
	$.ajax({
		type: "POST",
		data:"",
		dataType: "json",
		url: "<%=request.getContextPath()%>/api/findClassRoomsIndexTree",
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
			
			var articleListTab = $('#articleListTab'); 
			$("#articleListTab tr:not(:has(th))").remove();
			
			articleListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath()%>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
														
		},
		complete:function(){
			
		},
		success: function(data){

			jsonData = data.data;
			//数据执行完成
			var articleListTab = $('#articleListTab');
			$("#articleListTab tr:not(:has(th))").remove();
			for(var i=0;i<jsonData.length;i++){
			
				newcontent = newcontent + '<tr><td nowrap="nowrap" align="left" ><a href="<%=request.getContextPath()%>/lesson/findClassRooms?categoryId='+jsonData[i].id+'">'+jsonData[i].mark+jsonData[i].categoryName+'</a></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img src="'+jsonData[i].cover+'" width="50" height="50" /></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].summary+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].sort+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editArticle('+i+')" ><i class="icon-edit bigger-120"></i></button><button onclick="deleteArticle('+jsonData[i].id+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>';
				newcontent = newcontent + '</tr>';
				
	        }
			
			articleListTab.append(newcontent);
			
	}

	});
});



function deleteArticle(id){

	if(confirm('你确定要删除数据吗？'))
	{
		if(getChildrenList(jsonData,id,new Array()).length>0){
			alert("该分类下面还有子类不能删除");
		}else{
			document.forms['addarticle'].action ="delClassRoomCategory?id="+id;
			document.forms['addarticle'].submit();
		}
	}
}

//获取该分类下所有子类
function getChildrenList(bData,currentId,fData){
	for(var i=0;i<bData.length;i++){
		if(bData[i].parentId==currentId){
			var cat={id:bData[i].id,categoryName:bData[i].categoryName,parentId:bData[i].parentId,mark:bData[i].mark};
			fData.push(cat);
			getChildrenList(bData,bData[i].id,fData);
		}
	}
	return fData;
}


//添加或者修改书籍分类
function editArticle(index)
{
	$("#mymodal-data").modal("show");
	document.forms['addarticle'].action ="saveClassRoomCategory";
	if(index.length=='0'){
	//增加
		categorySelect(false,index);
	}else{
	//修改
		categorySelect(true,index);
	}
	
}

//获取除自身本类之外的所有分类
function getListLevel(bData,parentId,currentId,fData){
	for(var i=0;i<bData.length;i++){
		if(bData[i].parentId==parentId&&bData[i].id!=currentId){
			var cat={id:bData[i].id,categoryName:bData[i].categoryName,parentId:bData[i].parentId,mark:bData[i].mark};
			fData.push(cat);
			getListLevel(bData,bData[i].id,currentId,fData);
		}
	}
	return fData;
}


//添加分类
function categorySelect(flag,index){

	if(flag){
		//获取除自己和自己的子类之外的所有分类
		var bData=jsonData;
		var currentId=jsonData[index].id;
		var parentId=0;
		var fData=new Array();
		fData=getListLevel(bData,parentId,currentId,fData);
		$("#catId").val(jsonData[index].id);//填充内容 
		$("#catName").val(jsonData[index].categoryName);
		$("#uniqueId").val(jsonData[index].cover);
		$("#description").text(jsonData[index].summary);
		$("#sort").val(jsonData[index].sort);
		//修改
		$("#parentId option").remove();
		if(jsonData[index].parentId==0){
			$("#parentId").append("<option value='0' selected='selected' >无</option>");
		}else{
			$("#parentId").append("<option value='0' >无</option>");
		}
		for(var i=0;i < fData.length;i++){
			if(jsonData[index].parentId==fData[i].id){
				$("#parentId").append("<option value='"+fData[i].id+"' selected = 'selected' >"+fData[i].mark+fData[i].categoryName+"</option>");
			}else{
				$("#parentId").append("<option value='"+fData[i].id+"'>"+fData[i].mark+fData[i].categoryName+"</option>");
			}
		}	
		
	}else{
		$("#catId").val("");
		$("#catName").val("");
		$("#uniqueId").val("");
		$("#description").text("");
		$("#sort").val("");
		//增加
		$("#parentId option").remove();
		$("#parentId").append("<option value='0' selected='selected' >无</option>");
		for(var i=0;i<jsonData.length;i++){
			$("#parentId").append("<option value='"+jsonData[i].id+"'>"+jsonData[i].mark+jsonData[i].categoryName+"</option>");
		}	
	}
	
}



</script>


<style>
#testform {
	display: none;
}

.label1 {
	display: block;
	width: 300px;
	height: 150px;
	margin: 30px auto;
	border-radius: 10px;
	border: 2px dashed #ddd;
	overflow: hidden;
	cursor: pointer;
}

.label1 span {
	display: block;
	width: 50px;
	height: 50px;
	border-radius: 100%;
	border: 3px solid #0983C7;
	margin: 30px auto 15px;
	position: relative;
}

.label1 em {
	font-style: normal;
	text-align: center;
	display: block;
}

.label1 span:before,.label1 span:after {
	display: block;
	content: '';
	background-color: #0983C7;
	position: absolute;
}

.label1 span:before {
	width: 3px;
	height: 30px;
	top: 10px;
	left: 24px;
}

.label1 span:after {
	width: 30px;
	height: 3px;
	top: 24px;
	left: 10px;
}
</style>

<script type="text/javascript">

var inputPic;

$(function(){
   //var domain = "http://7xocov.com1.z0.glb.clouddn.com/";
   var domain = "http://source.fandoutech.com.cn/";
   var $key = $('#key'); 
   var $userfile = $('#userfile'); 
   var tokenUrl='<%=request.getContextPath()%>/api/getUptoken?bucketName=source';
		$.get(tokenUrl, function(data) {
			var dat = jQuery.parseJSON(data);
			//$("#tokenfile").val("xozWSPMxkMjIVoHg2JyXq4-7-oJaEADLOKHVR0vU:LX3n6uOWuObogDlZ5w3R191b9Po=:eyJzY29wZSI6Impzc2RrIiwiZGVhZGxpbmUiOjE0ODc4MjAwNTh9");
			$("#tokenfile").val(dat.uptoken);
		});
		$("#userfile").change(
				function() {
					var selectedFile = $userfile.val();
					if (selectedFile) {
						// randomly generate the final file name
						var ramdomName = Math.random().toString(36).substr(2)
								+ $userfile.val().match(/\.?[^.\/]+$/);
						$key.val(ramdomName);
					} else {
						return false;
					}

					var f = new FormData(document
							.getElementById("uploadFileForm"));

					$.ajax({
						url : 'http://up.qiniu.com', // Different bucket zone has different upload url, you can get right url by the browser error massage when uploading a file with wrong upload url.
						type : 'POST',
						data : f,
						processData : false,
						contentType : false,
						xhr : function() {
							myXhr = $.ajaxSettings.xhr();
							if (myXhr.upload) {
								myXhr.upload.addEventListener('progress',
										function(e) {
											// console.log(e);
											if (e.lengthComputable) {
												var percent = e.loaded
														/ e.total * 100;
												//  $progress.html('上传：' + e.loaded + "/" + e.total+" bytes. " + percent.toFixed(2) + "%");
											}
										}, false);
							}
							return myXhr;
						},
						success : function(res) {
							alert("上传成功");
							console.log("成功：" + JSON.stringify(res));
							var str = '<span>已上传：' + res.key + '</span>';
							if (res.key
									&& res.key.match(/\.(jpg|jpeg|png|gif)$/)) {
								str += '<img src="' + domain + res.key + '"/>';
							}
							inputPic.value = domain + res.key;
							//alert(domain + res.key);
							$("#uploadFileModal").modal("hide");

						},
						error : function(res) {
							alert("上传失败");
							console.log("失败:" + JSON.stringify(res));
							// $uploadedResult.html('上传失败：' + res.responseText);
						}
					});
					return false;
				});
	});

	//Focus上传文件
	function uploadPic(pic) {
		inputPic = pic;
		$("#uploadFileModal").modal("show");
		return;
	}
</script>



<div class="main-content">
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed')
			} catch (e) {
			}
		</script>

		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active">在线课堂</li>
			<li class="active">首页管理</li>
		</ul>
		<!-- .breadcrumb -->


	</div>

	<div class="page-content">
		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/mall/mallManager"
				style="float: left;width:100%">


				<div class="btn-group col-md-12">
					<button class="btn  btn-purple" type="button" data-toggle="modal"
						data-target="#mymodal-data" onclick="editArticle('')">
						<span class="icon-plus"></span>添加
					</button>
				</div>

			</form>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="row">
					<div class="col-xs-12">
						<div class="table-responsive" style="text-align:right;">
							<table id="articleListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th width="100">分类名称</th>
										<th width="100">封面</th>
										<th width="100">简单描述</th>
										<th width="100">排序</th>
										<th width="120">操作</th>
									</tr>
								</thead>


							</table>


						</div>
						<!-- /.table-responsive -->
					</div>
					<!-- /.col-xs-12 -->
				</div>
				<!-- /row -->
			</div>
			<!-- /.col-xs-12 -->
		</div>
		<!-- /.row -->


	</div>
	<!-- /.page-content -->

</div>
<!-- /.main-content -->
<!-- /.main-container-inner -->
<!-- 模态弹出窗内容 -->
<form id="addarticle" class="article" method="post" name="addarticle"
	action="<%=request.getContextPath()%>/mall/saveMallCate">

	<input type="hidden" name="catId" id="catId" value="">

	<div class="modal fade" id="mymodal-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:1100px;">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">添加/修改首页信息</h4>
				</div>
				<div class="modal-body">


					<div id="divAccount" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">班级名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<input class="form-control" name="categoryName" id="catName"
										type="text" data-rule="分类名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>


					<div id="divAccount" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">封面</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<input class="form-control" name="cover"
										onFocus=uploadPic(this) id="uniqueId" type="text"
										data-rule="别名:required;" style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divAccount" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">上级分类</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:910px;">

									<select name="parentId" id="parentId">

									</select>

								</div>
							</div>
						</div>
					</div>

					<div id="divAccount" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">排序</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:710px;">
									<input class="form-control" name="sort" id="sort" type="text"
										data-rule="排序:required;" style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>


					<div class="form-group" style="height:335px;">
						<label class="col-sm-2 control-label no-padding-right text-right">简单描述</label>
						<div class="col-sm-10">
							<div class="input-medium">
								<div class="input-group">

									<textarea name="summary" id="description"
										style="width:650px;height:350px;" data-rule="简单描述:required;"></textarea>

								</div>
							</div>
						</div>
					</div>



				</div>

				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">保存</button>

					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
</form>



<!-- 上传文件到七牛模态框 -->
<form class="data" method="post" name="uploadFileForm"
	id="uploadFileForm" enctype="multipart/form-data" action="">
	<div class="modal fade" id="uploadFileModal" tabindex="-1"
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:500px;height: 500px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">上传文件到七牛</h4>
				</div>
				<div class="modal-body">

					<div id="testform" class="form-group" style="height:500px;">
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input name="key" id="key" type="hidden" value=""> <input
										id="tokenfile" name="token" type="hidden" value=""> <input
										id="userfile" name="file" type="file" /> <input name="accept"
										type="hidden" />

								</div>
							</div>
						</div>
					</div>

					<!-- add file -->
					<label class="label1" for="userfile"> <span></span> <em>添加文件</em>
					</label>

				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="saveUploadFile()">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>





