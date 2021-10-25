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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">

<script>

//html存储全局变量
var newcontent = '';
//服务器json字符串
var jsonStr ='${jsonData}';
//json字符串转json数组
var jsonData = eval(jsonStr);    

//系统内置课程数组 
var systemProjects=eval('${systemProjects}');
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
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].name+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].logo+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].summary+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data"><i class="icon-edit bigger-120"></i></button><button onclick="deleteData('+i+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}

//初始化
$(document).ready( function () {
	
	//更新界面
	updateUI();

});


//删除数据

function deleteData(index){
	if(confirm('你确定要删除数据吗？'))
	{
		//通过Ajax删除服务器上的数据，并更新界面
		var dataPar='id='+jsonData[index].id;
		$.ajax({
		type: "POST",
		data:dataPar,
		dataType: "json",
		url: "<%=request.getContextPath() %>/bookApi/deleteAnchor", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
			var dataListTab = $('#dataListTab'); 
			$("#dataListTab tr:not(:has(th))").remove();
			dataListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		},
		complete:function(){
			
		},
		success: function(data){
			//数组更新
			jsonData.remove(index);
			//数据执行完成
			updateUI();
			updateTotalCount(-1);
	    }
	   });
	   
	}
}



//预添加或者修改上传文件
function editData(index)
{
	
	if(index.length == '0'){
		$("#index").val("");
		$("#id").val("");
		$("#anchorName").val("");
		$("#logo").val("");
		$("#summary").val("");
	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		$("#anchorName").val(myData.name);
		$("#logo").val(myData.logo);
		$("#summary").val(myData.summary);
	}
}


//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/bookApi/saveAnchor' ,
          type: 'POST',
          data: formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		   var dataListTab = $('#dataListTab'); 
		   $("#dataListTab tr:not(:has(th))").remove();
		   dataListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		   
		  },
          success: function (returndata) {
              var result=returndata;
              var index=$("#index").val();
			  if(index.length == '0'){
					//更新数组数据
					//添加到最后
					jsonData.push(result.data); 
					updateTotalCount(1);
				}else{
					//更新数组数据
					jsonData[index]=result.data;
			  }
			  //更新界面
			  updateUI();
          },
          error: function (returndata) {
          	
          }
     });
}


</script>



<div class="main-content">
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed');
			} catch (e) {

			}
		</script>

		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active">课程管理</li>
			<li class="active">大V管理</li>
		</ul>
		<!-- .breadcrumb -->

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/bookShop/anchorManager"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>名称</td>
						<td><input type="text" name="name" value="${queryDto.name}">
						</td>
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
							</div></td>
					</tr>

				</table>

				<div class="btn-group col-md-12">
					<button type="submit" class="btn btn-purple ml10 mb10">
						查询 <i class="icon-search icon-on-right bigger-110"></i>
					</button>
					<button class="btn  btn-purple" type="button" data-toggle="modal"
						data-target="#myModal-data" onclick="editData('')">
						<span class="icon-plus"></span>添加
					</button>
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
										<th width="100">ID</th>
										<th width="100">名称</th>
										<th width="100">LOGO</th>
										<th width="100">简介</th>
										<th width="120">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./anchorManager"
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
					<h4 class="modal-title">添加/修改大V信息</h4>
				</div>
				<div class="modal-body">
				

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">大V名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="anchorName"
										id="anchorName" type="text" data-rule="大V名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">LOGO</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="logo"
										id="logo" type="text" data-rule="LOGO:required;"
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
									<input class="form-control" name="summary"
										id="summary" type="text" data-rule="简介:required;"
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
</form>