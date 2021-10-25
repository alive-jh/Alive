<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>
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
		
	$("#uType").val("${queryDto.type}");
			
			if(data !='')
			{

				
				var userListTab = $('#userListTab');
				$("#userListTab tr:not(:has(th))").remove();
				for(var i=0;i<data.infoList.length;i++)
				{
				
					newcontent = newcontent + '<tr><td class="left">'+data.infoList[i].id+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
					if(data.infoList[i].type ==0)
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >商城</td>';
					}
					else
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >书院</td>';
					}
					
					if(data.infoList[i].id<=7)
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ></td>';
					}
					else
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editUser('+i+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button>';
						newcontent = newcontent +'<button onclick="deleteUser('+i+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button>';
					}
					
				
				}
				$("#userListTab tr:not(:has(th))").remove();
				userListTab.append(newcontent);
			}
			
						
	
});


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
		document.forms['addUser'].action ="deleteLabel?laberId="+data.infoList[index].id;
		document.forms['addUser'].submit();
	}
}



function editUser(index){
	
				
				if(index.length == '0' ){
					$('#uName')[0].value = '';
					document.forms['addUser'].id.value = '';
				}
				else {
					  
					$('#uName')[0].value = data.infoList[index].name;
					
					$("#uStatus").val(data.infoList[index].type);
					document.forms['addUser'].id.value = data.infoList[index].id;

						
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
							<li class="active">书籍管理</li>
							<li class="active">标签管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post" name="searchForm" action="<%=request.getContextPath()%>/book/labelManager" style="float: left;width:100%">
								<table >
									<tr>
										<td align="left">标签名称:</td>
										<td><input type="text" name="name"  value="${queryDto.name}"></td>
										<td>标签类型</td>
										<td><select id="sType" name="type" style="width:100px;">
										<option value="">请选择</option>
										<option value="0">商城</option>
										<option value="1">书院</option>
										</select></td>
										<td> <button type="submit" class="btn btn-purple ml10 mb10">查询
										 <i class="icon-search icon-on-right bigger-110"></i>
										 </button></td>
										<td><button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editUser('')"><span class="icon-plus"></span>添加</button>
										</td></td>
										
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
											<table id="userListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														<th witch="20">ID</th>
														<th>标签名称</th>
														<th>标签类型</th>
														<th>操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
<f:page page="${resultPage}" url="./labelManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="user" method="post" name="addUser" action ="<%=request.getContextPath()%>/book/saveLabel">
			 <input type="hidden" name="id" >
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改标签信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">标签名称:</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uName" name ="name" type="text"data-rule="标签名称:required;" style="width:210px;"/>
												</div>
											</div>
										</div>
							</div>
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">标签类型:</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<select id="uStatus" name="type">
														<option value="0" >商城</option>
														<option value="1" >书院</option>
													</select>
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
					</div>
				</div>
			</div>
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->