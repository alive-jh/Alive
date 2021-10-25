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
document.forms['infoForm'].action = "labelManager";
document.forms['infoForm'].submit();
}
//本地条件分页查询
function pageJump(page) {
PPage("page_select", page, totalPageCount, "pageJump", true);
$("#currentPage").val(page);
}
var newcontent = '';

var data = ${jsonStr};
$(document).ready( function () {
		
	$("#sType").val("${queryDto.type}");
	$("#sStatus").val("${queryDto.status}");
			
			if(data !='')
			{

				
				var userListTab = $('#userListTab');
				$("#userListTab tr:not(:has(th))").remove();
				for(var i=0;i<data.infoList.length;i++)
				{
				
					 newcontent = newcontent + '<tr>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + data.infoList[i].title + '</td>';
					
					if(data.infoList[i].type ==0)
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >标签集合</td>';
					}
					if(data.infoList[i].type ==1)
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >课程集合</td>';
					}
					if(data.infoList[i].type ==2)
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >专题集合</td>';
					}
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + data.infoList[i].infoId + '</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >' + data.infoList[i].sort + '</td>';
					if(data.infoList[i].status ==0)
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >显示</td>';
					}
					if(data.infoList[i].status ==1)
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >不显示</td>';
					}
					
					 
					
				  
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editUser(' + i + ')" data-toggle="modal" data-target="#myModal-data"><i class="icon-edit bigger-120"></i></button><button onclick="deleteUser(' + i + ')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>';
					newcontent = newcontent + '</tr>';
					
				
				}
				$("#userListTab tr:not(:has(th))").remove();
				userListTab.append(newcontent);
			}
			
						
	
});



function deleteUser(index){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['infoForm'].action ="deleteLabel?laberId="+data.infoList[index].id;
		document.forms['infoForm'].submit();
	}
}



function editUser(index){
	
				
				if(index.length == '' ){

					$('#uTitle')[0].value = '';
					$("#uType").val("");
					$("#uSort").val("");
					$("#uInfoId").val('');
					document.forms['infoForm'].id.value = '';
					$("#uInfoId").empty();
				}
				else {
					  
					$('#uTitle')[0].value = data.infoList[index].title;
					$("#uType").val(data.infoList[index].type);
					$("#uSort").val(data.infoList[index].sort);
					addItems(data.infoList[index].type);
					$("#uInfoId").val(data.infoList[index].infoId);
					$("input[name=status]:eq("+data.infoList[index].status+")").attr("checked",'checked');
					document.forms['infoForm'].id.value = data.infoList[index].id;

						
				}
				
			} 



function addItems(id)
{


	$("#uInfoId").empty();
	var tempData = "";
	
	if(id ==0)
	{
		tempData = ${labelData};
		
		for(var i=0;i<tempData.labelData.length;i++)
		{
			
			$("#uInfoId").append('<option value="'+tempData.labelData[i].id+'">'+tempData.labelData[i].name+'</option>');
		}
	}
	if(id ==1)
	{
		tempData = ${categoryData};
		
		for(var i=0;i<tempData.categoryData.length;i++)
		{
			
			$("#uInfoId").append('<option value="'+tempData.categoryData[i].cat_id+'">'+tempData.categoryData[i].unique_id+'</option>');
		}
	}


}



function deleteUser(index)
{
	document.forms['infoForm'].action = "<%=request.getContextPath()%>/appIndex/deleteAppIndex?appIndexId="+data.infoList[index].id;
	document.forms['infoForm'].submit();

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
			<li class="active">商城管理</li>
			<li class="active">APP首页管理</li>
		</ul>
		<!-- .breadcrumb -->

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/appIndex/appIndexManager"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>标题</td>
						<td><input type="text" name="title" value="${queryDto.title}">
						</td>
						<td>类型</td>
						<td><select name="type" id="sType">
							<option value="">请选择</option>
							<option value="0">标签集合</option>
							<option value="1">课程集合</option>
							<option value="2">专题栏目</option>
						</select>
						</td>
						<td>状态</td>
						<td><select name="status" id="sStatus">
							<option value="">请选择</option>
							<option value="0">显示</option>
							<option value="1">不显示</option>
							
						</select>
						</td>
					</tr>


				</table>

				<div class="btn-group col-md-12">
					<button type="submit" class="btn btn-purple ml10 mb10">
						查询 <i class="icon-search icon-on-right bigger-110"></i>
					</button>
					<button class="btn  btn-purple" type="button" data-toggle="modal"
						data-target="#myModal-data" onclick="editUser('')">
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
							<table id="userListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th width="30%">标题</th>
										<th width="10%">类型</th>
										<th width="20%">数据集合</th>
										<th width="10%">排序</th>
										<th width="10%">状态</th>
										<th width="10%">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./appIndexManager"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form class="data" method="post" name="infoForm"
	enctype="multipart/form-data" action="<%=request.getContextPath()%>/appIndex/saveAppIndex">
	
	<input type="hidden" name="id" id="id" value="">
	<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:600px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">添加/修改信息</h4>
				</div>
				<div class="modal-body">


					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">标题</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:610px;">
									<input class="form-control" name="title" id="uTitle"
										type="text" data-rule="标题:required;"
										style="max-width:250px;width:100%;" />
								</div>
							</div>
						</div>
					</div>


					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">类型</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:610px;">
									<select id="uType" name="type" style="width:250px;" data-rule="类型:required;" onchange="addItems(this.value)">
									<option value="">请选择</option>
									<option value="0">标签集合</option>
									<option value="1">课程集合</option>
									<option value="2">专题栏目</option>
									</select>
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">选择集合</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:610px;" data-rule="集合:required;">
									<select id="uInfoId" name="infoId" style="width:250px;">
									
									</select>
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">是否显示</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:610px;">
									<label class="inline">
										<input name="status" value="0" checked type="radio" class="ace" />
											<span class="lbl">是</span>
										</label>

										&nbsp; &nbsp; &nbsp;
										<label class="inline">
											<input name="status"  value="1" type="radio" class="ace" />
											<span class="lbl">否</span>
										</label>
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">排序</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:610px;">
									<input class="form-control" name="sort" id="uSort"
										type="text" data-rule="排序:required;"
										style="max-width:250px;width:100%;" />
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


