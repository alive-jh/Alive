<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery-2.1.1.min.js" type="text/javascript"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/local/zh_CN.js"></script>

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
document.forms['searchForm'].action = "accountOrderManager";
document.forms['searchForm'].submit();
}
//本地条件分页查询
function pageJump(page) {
PPage("page_select", page, totalPageCount, "pageJump", true);
$("#currentPage").val(page);
}
var newcontent = '';


var data = ${jsonStr};
$(document).ready( function () {
	

			if(data !='')
			{
				var accountListTab = $('#accountListTab');
				$("#accountListTab tr:not(:has(th))").remove();
				accountListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		
				for(var i=0;i<data.infoList.length;i++){

					
					newcontent = newcontent + '<tr><td class="center">'+data.infoList[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
					newcontent = newcontent + '<td><button class="btn btn-xs btn-info"  onclick="editaccount('+i+')" data-toggle="modal" data-target="#mymodal-data">修改</button><button onclick="deleteAccountOrder('+i+')" class="btn btn-xs btn-danger">删除</button></td>';
					newcontent = newcontent + '</tr>';
				}
				
				$("#accountListTab tr:not(:has(th))").remove();
				accountListTab.append(newcontent);
			}
				
			
			

});



function editaccount(index){
	
				
				if(index.length == '0' )
				{
					$("input[name='items']:checkbox").each(function() {

							this.checked = false;
							
					})
				
					$('#uName')[0].value='';
					document.forms['addUser'].id.value ='';
					document.forms['addUser'].modularIds.value ='';
					
				}
				else
				{
					
					$('#uName')[0].value = data.infoList[index].name;
					document.forms['addUser'].id.value = data.infoList[index].id;

						
						if(data.infoList[index].modularIds != '' && data.infoList[index].modularIds != undefined)
								{
									
									document.forms['addUser'].modularIds.value = data.infoList[index].modularIds;
									
									for(var i=0;i<data.infoList[index].modularIds.split(',').length;i++)
									{
										

										$("input[name='items']:checkbox").each(function() {
			 
											
											 if(data.infoList[index].modularIds.split(',')[i] == $(this).val())
											 {
												
												 this.checked = true;
												
											 }
											   
										})
									}
								}

							
							
							
					
				}
				
			} 
function deleteAccountOrder(index){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addUser'].action ="deleteRole?roleId="+data.infoList[index].id;
		document.forms['addUser'].submit();
	}
}



function setItems()
{
	
	var ids = '';
	$("input[name='items']:checkbox").each(function() {
		 
	if(this.checked == true)
	{
		ids = ids + $(this).val()+',';
		
	}
	})
	if(ids =='')
	{
		alert('请至少选择一条记录!');
		return;
	}
	else
	{
		ids = ids.substring(0,ids.length -1);
	}
	
	document.forms['addUser'].modularIds.value = ids;
	document.forms['addUser'].action ="<%=request.getContextPath()%>/role/saveRole";
	document.forms['addUser'].submit();

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
							<li class="active">系统管理</li>
							<li class="active">角色管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post" name="searchForm" action="<%=request.getContextPath()%>/role/roleManager" style="float: left;width:100%">
								<table >
									<tr>
										<td align="left">角色名称:</td>
										<td><input type="text" name="name"  value="${queryDto.name}"></td>
										<td> <button type="submit" class="btn btn-purple ml10 mb10">查询
										 <i class="icon-search icon-on-right bigger-110"></i>
										 </button></td>
										<td><button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editaccount('')"><span class="icon-plus"></span>添加</button>
										</td></td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									
									
								</table>
							   
								
						
							</form>
						</div><!-- /.page-header -->
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
	
								<div class="row">
									<div class="col-xs-12">
										<div class="table-responsive" style="text-align:right;">
											<table id="accountListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														<th witch="5">ID</th>
														<th>角色</th>
														<th>操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
									<f:page page="${resultPage}" url="./roleManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="user" method="post" name="addUser" action ="<%=request.getContextPath()%>/role/saveRole">
			 <input type="hidden" name="id" >
			  <input type="hidden" name="modularIds" >
			<div class="modal  fade" id="mymodal-data"   tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width:700px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改角色信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">角色名称:</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uName" name ="name"class="input-medium date-picker" type="text"data-rule="角色名称:required;" style="width:210px;"/>
												</div>
											</div>
										</div>
							</div>
							<div class="form-group" style="height:300px;">
								<label class="col-sm-3 control-label no-padding-right text-right">选择模块:</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
												<table id="mallTab" style="width:450px;"  class="table table-striped table-bordered table-hover"  style="text-align:left;">
												<thead>
													<tr>
														<th width="10">模块</th>
														<th width="50">子功能</th>
													</tr>
												</thead>
													<c:forEach items="${tempModularList}" var ="parentModular">
													<c:if test="${parentModular.parentId ==0}">
													<tr>
														<td width="30"><input  id="items" name="items"  value="${parentModular.id}" type="checkbox" />&nbsp;${parentModular.name}</td>

														<td width="50">
														<c:forEach items="${tempModularList}" var ="tempModulars">
														<c:if test="${tempModulars.parentId == parentModular.id}">
														<input  id="items" name="items"  value="${tempModulars.id}" type="checkbox" />&nbsp;${tempModulars.name}
														</c:if>
														</c:forEach>
														</td>
													</tr>
													</c:if>
													</c:forEach>
												
												

											</table>
												</div>
											</div>
										</div>
							</div>
							

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="setItems()">保存</button>
							
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
						</form>
					</div>
				</div>
			</div>
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->