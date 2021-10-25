<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>
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
document.forms['addUser'].action = "labelManager";
document.forms['addUser'].submit();
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

				
				var userListTab = $('#userListTab');
				$("#userListTab tr:not(:has(th))").remove();
				for(var i=0;i<data.infoList.length;i++)
				{
				
					newcontent = newcontent + '<tr><td class="left">'+data.infoList[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].iosName+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].androidName+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].wechatName+'</td>';
					
					
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
						newcontent = newcontent +'<button class="btn btn-xs btn-info"  onclick="editArticle('+i+')" data-toggle="modal" data-target="#mymodal-data1"><i class="icon-edit bigger-120"></i></button><button onclick="deleteUser('+i+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button>';
					
					
				
				}
				$("#userListTab tr:not(:has(th))").remove();
				userListTab.append(newcontent);
			}
			
						
	
});


function checkValueIos(v)
{
	if(v ==0 )
	{
		document.forms['searchForm'].ios.value =1;
	}
	else
	{
		document.forms['searchForm'].ios.value =0;
	}
	
}


function checkValueAndroid(v)
{
	if(v ==0 )
	{
		document.forms['searchForm'].android.value =1;
	}
	else
	{
		document.forms['searchForm'].android.value =0;
	}
	
}


function checkValueWechat(v)
{
	if(v ==0 )
	{
		document.forms['searchForm'].wechat.value =1;
	}
	else
	{
		document.forms['searchForm'].wechat.value =0;
	}
	
}

function editArticle(index)
{
	
	
	$("[name=ios]:checkbox").prop("checked", false);
	$("[name=wechat]:checkbox").prop("checked", false);
	$("[name=android]:checkbox").prop("checked", false);
	document.forms['searchForm'].ios.value =0;
	document.forms['searchForm'].wechat.value =0;
	document.forms['searchForm'].android.value =0;

	document.forms['searchForm'].id.value =data.infoList[index].id;
	if(data.infoList[index].ios ==0)
	{
		
		$("[name=ios]:checkbox").prop("checked", true);
		document.forms['searchForm'].ios.value =0;
	}
	else
	{
		$("[name=ios]:checkbox").prop("checked", false);
		document.forms['searchForm'].ios.value =1;
	}

	if(data.infoList[index].wechat ==0)
	{
		
		$("[name=wechat]:checkbox").prop("checked", true);
		document.forms['searchForm'].wechat.value =0;
	}
	else
	{
		$("[name=wechat]:checkbox").prop("checked", false);
		document.forms['searchForm'].wechat.value =1;
	}

	if(data.infoList[index].android ==0)
	{
		
		$("[name=android]:checkbox").prop("checked", true);
		document.forms['searchForm'].android.value =0;
	}
	else
	{
		$("[name=android]:checkbox").prop("checked", false);
		document.forms['searchForm'].android.value =1;
	}
	
}


function updateMallLabel()
{

	document.forms['searchForm'].action ="updateMallLabel?id="+document.forms['searchForm'].id.value;
	document.forms['searchForm'].submit();
}

function checkAll()
{
	 var checkAllId = document.getElementById("checkAllId");
	 if(checkAllId.checked == true)
	 {
		$("input[name='checkId']:checkbox").each(function() {
			 
        this.checked = true;;  })
                              	
	}
	else
	{
		$("input[name='checkId']:checkbox").each(function() { 
			
        this.checked = false;  }) 
	}
}

function deleteUser(index){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['searchForm'].action ="deleteMallLabel?id="+data.infoList[index].id;
		document.forms['searchForm'].submit();
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
	
	document.forms['searchForm'].labelId.value = ids;
	document.forms['searchForm'].action = "./saveMallLabel";
	document.forms['searchForm'].submit();
	
	//$('#mymodal-data').modal('hide');

}





function editUser(){
	
				
var tempIds = "${labelIds}";
	
	for(var i=0;i<tempIds.split(',').length;i++)
	{
		

		$("input[name='items']:checkbox").each(function() {

			
			 if(tempIds.split(',')[i] == $(this).val())
			 {
				
				 this.checked = true;
				 //this.disabled = "disabled";
				 
			 }
			   
		})
	}

				
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
							<li class="active">商城管理</li>
							<li class="active">首页标签</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post" name="searchForm" action="<%=request.getContextPath()%>/mall/mallLabelManager" style="float: left;width:100%">
							<input type="hidden" name="labelId">
							<input type="hidden" name="id">
						
								<table >
									<tr>
										<td align="left">标签名称:</td>
										<td><input type="text" name="name"  value="${queryDto.name}"></td>
										<td> <button type="submit" class="btn btn-purple ml10 mb10">查询
										 <i class="icon-search icon-on-right bigger-110"></i>
										 </button></td>
										<td><button class="btn  btn-purple" type="button"  data-toggle="modal" onclick="editUser()" data-target="#mymodal-data" ><span class="icon-plus"></span>添加</button>
										</td></td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</tr>
									
									
								</table>
							   
								
						
							
						</div><!-- /.page-header -->
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
	
								<div class="row">
									<div class="col-xs-12">
										<div class="table-responsive" style="text-align:right;">
											<table id="userListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														<th witch="20">ID</th>
														<th>标签名称</th>
														<th>IOS</th>
														<th>安卓</th>
														<th>微信</th>
														<th>操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
							<f:page page="${resultPage}" url="./mallLabelManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	
				<div class="modal-dialog" style="width:800px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">请选择标签</h4>
						</div>
						<div class="modal-body">
							
							<div class="table-responsive" style="max-height: 550px;overflow: auto;">
								<table  class="table table-striped table-bordered table-hover">
								

									
									<% int i=1;%>	
									<tr>
										<c:forEach items="${labelPage.items}" var="label">
									
											
											<% if(i%5 == 0)
											{
												%>
												<td nowrap="nowrap">
													<label>
														
														<input  id="items"name="items" value="${label.id}" type="checkbox"/>&nbsp;&nbsp;${tempLabel.name}
														
													</label>
												</td>
											
											<td nowrap="nowrap" witch="500">${label.name}</td></tr>

										<%
											}
											else
											{
											%>
											
											<td nowrap="nowrap">
													<label>
														<input  id="items"name="items" value="${label.id}" type="checkbox"/>&nbsp;&nbsp;${tempLabel.name}
													</label>
												</td>
											
											<td nowrap="nowrap" witch="500">${label.name}</td>

											<%
											}
											%>
										
										<%i++;%>
									</c:forEach>
									
								</table>
								
							</div><!-- /.table-responsive -->
						</div><!-- /.modal-body -->
						<div class="modal-footer">
							<button type="button"  onclick="setItems()" class="btn btn-primary">保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			
			</div><!-- /.modal -->



				<!-- 模态弹出窗内容 -->
			<div class="modal  fade" id="mymodal-data1" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	
				<div class="modal-dialog" style="width:800px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">标签设置</h4>
						</div>
						<div class="modal-body">
							
							<div class="table-responsive" style="max-height: 550px;overflow: auto;">
								<table  class="table table-striped table-bordered table-hover">
								

									<tr>
									<td nowrap="nowrap" witch="500"><input onclick="checkValueIos(this.value)" id="ios"name="ios" value="0" type="checkbox"/>&nbsp;&nbsp;IOS</td>
									<td nowrap="nowrap" witch="500"><input onclick="checkValueAndroid(this.value)"  id="android"name="android" value="0" type="checkbox"/>&nbsp;&nbsp;安卓</td>
									<td nowrap="nowrap" witch="500"><input onclick="checkValueWechat(this.value)"  id="wechat"name="wechat" value="0" type="checkbox"/>&nbsp;&nbsp;微信</td>
									</tr>
									
									
								</table>
								
							</div><!-- /.table-responsive -->
						</div><!-- /.modal-body -->
						<div class="modal-footer">
							<button type="button"  onclick="updateMallLabel()" class="btn btn-primary">保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			
			</div><!-- /.modal -->

</form>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->