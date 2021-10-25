<%@ page language="java" import="com.wechat.util.Keys" pageEncoding="utf-8"%>

<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>

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
document.forms['addUser'].action = "userManager";
document.forms['addUser'].submit();
}
//本地条件分页查询
function pageJump(page) {
PPage("page_select", page, totalPageCount, "pageJump", true);
$("#currentPage").val(page);
}
var newcontent = '';

var tempDate='';

$(document).ready( function () {
		


	$.ajax({
		type: "POST",
		data:"page=${queryDto.page}&pageSize=${queryDto.pageSize}",
		dataType: "json",
		url: "<%=request.getContextPath() %>/menu/menuManagerView", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
			
			var userListTab = $('#userListTab'); 
			$("#userListTab tr:not(:has(th))").remove();
			//userListTab.append("<tr><td colspan=\"20\" align=\"center\"><div class=\"jiazai\"><span>正在加载，请等待...</span></div></td></tr>");
			userListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
														
		},
		complete:function(){
			
			
		},
		success: function(data){

			//数据执行完成
			var userListTab = $('#userListTab');
			$("#userListTab tr:not(:has(th))").remove();
			tempDate  = data;
			for(var i=0;i<data.infoList.length;i++){
				
				
				
		
				newcontent = newcontent + '<tr><td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].typeName+'</td>';

				if(data.infoList[i].url !='')
				{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  data-toggle="modal" data-target="#keyWBtn" onclick="show('+i+')" >查看链接地址</button></td>';
				}
				else
				{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >&nbsp;</td>';
				}
				


				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].parentName+'</td>';	
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editUser('+data.infoList[i].id+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button>';
				newcontent = newcontent +'<button onclick="removeMenu('+data.infoList[i].id+','+data.infoList[i].isParentId+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button>';
				
				
				
				

				newcontent = newcontent + '</td></tr>';
				
	}
			
			userListTab.append(newcontent);
			
			
	}

	});
});


function show(index)
{
	
	$("#uContent").val(tempDate.infoList[index].url);
	
}


function removeMenu(id,type){

	var message = '你确定要删除数据吗?';
	if(type ==1)
	{
		message = '当前菜单下有子菜单,删除会把子菜单也一起删除,你确定要删除数据吗?';
	}
	if(confirm(message))
	{
		document.forms['addUser'].action ="deleteMenu?menuId="+id+"&type="+type;
		document.forms['addUser'].submit();
	}
}



function editUser(id){
	

				$("#dParentId").hide();
				
				$("#dType").show();  
				if(id ==''){

					$('#uName')[0].value='';
					$('#uUrl')[0].value='';
					$('#uType')[0].value='0';
					document.forms['addUser'].id.value ='';

				}
				else {
					  
						
						$.ajax({
							type: "POST",
							data:"menuId="+id,
							dataType: "json",
							url: "<%=request.getContextPath() %>/menu/menuManagerViewById", 
							context: document.body, 
							beforeSend:function(){											
							},
							complete:function(){
								
								
							},
							success: function(data){

								document.forms['addUser'].id.value = data.infoList[0].id;
								$('#uName')[0].value = data.infoList[0].name;
								$('#uUrl')[0].value = data.infoList[0].url;
								$("#dType").hide()
								$('#uParentId')[0].value = data.infoList[0].parentId;
								$('#uType')[0].value = data.infoList[0].type;
								document.forms['addUser'].tempDate.value = data.infoList[0].createDate;
								if(data.infoList[0].type ==0)
								{
									
									
								}
								if(data.infoList[0].type ==1)
								{
									$("#dUrl").show();
									$("#dParentId").show();
								}
								
								document.forms['searchForm'].id.value = id;
								
								 
							}

						});
				}
				
			} 


function updateMenu()
{


		$.ajax({
							type: "POST",
							data:"",
							dataType: "json",
							url: "<%=request.getContextPath() %>/menu/updateMenu", 
							context: document.body, 
							beforeSend:function(){											
							},
							complete:function(){
								
								
							},
							success: function(data){

								
								alert(data.data.status);
								
								
								 
							}

						});

}

function checkType(v)
{
	
	if(v ==0)
	{
		$("#dParentId").hide();
		$("#dUrl").hide();
	
	}
	else
	{
		$("#dParentId").show();
		$("#dUrl").show();
		
		
	}
}


function checkArticle()
{
	
	if($('input[name="checkId"]:checked ').val() != undefined)
	{
		$('#uUrl')[0].value = "<%=Keys.STAT_NAME%>/wechat/article/toArticle?articleId="+$('input[name="checkId"]:checked ').val();
		$('#mymodal-data1').modal('hide');
	}
	else
	{
		alert('请选择内容!');
	}
	
}

function checkMaterial()
{
	
	
	if($('input[name="checkIdss"]:checked ').val() != undefined)
	{
		$('#uUrl')[0].value = "materialId="+$('input[name="checkIdss"]:checked ').val();
		$('#mymodal-data2').modal('hide');
	}
	else
	{
		alert('请选择图文!');
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
							<li class="active">微信设置</li>
							<li class="active">自定义菜单</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" name="searchForm" action="#" style="float: left;width:100%">
							
							   
								<div class="btn-group col-md-12">
										 <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editUser('')"><span class="icon-plus"></span>添加</button>
										 
										 <button type="button" onclick="updateMenu()" class="btn btn-purple ml10 mb10">同步到微信
										
										 </button>
								</div>	
						
							
						</div><!-- /.page-header -->
						</form>
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
	
								<div class="row">
									<div class="col-xs-12">
										<div class="table-responsive" style="text-align:right;">
											<table id="userListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														
														<th width="350">菜单名称</th>
														<th width="150">类型</th>
														<th width="150">链接地址</th>
														<th width="150">父菜单</th>
														
														<th width="100">操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
				<f:page page="${resultPage}" url="./menuManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="user" method="post" name="addUser" action ="<%=request.getContextPath()%>/menu/saveMenu">
			 <input type="hidden" name="id">
			  <input type="hidden" name="tempDate">
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改自定义菜单信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">菜单名称</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group"  style="width:310px;">
													
													<input class="form-control" name="name" id="uName"   type="text" data-rule="菜单名称:required;"style="max-width:210px;width:100%;"  />
													
												</div>
											</div>
										</div>
									</div>
							
									<div id="dType"class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">类型</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group" >
													<select id = "uType" name="type" style="width:210px;" class="input-medium" onchange="checkType(this.value)">
														<option value="0">一级菜单</option>
														<option value="1">二级菜单</option>
														
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group" id ="dParentId" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">上级菜单</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group" >
													<select id = "uParentId" name="parentId" style="width:210px;" class="input-medium" >
														
														<c:forEach items="${parentList}" var ="tempMenu">
														<option value="${tempMenu.id}">${tempMenu.name}</option>
														</c:forEach>
														
													</select>
												</div>
											</div>
										</div>
									</div>

									
									<div  id="dUrl" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">链接地址</label>
											<div  class="col-sm-9">
											<div class="input-medium">
												<div class="input-group"  style="width:510px;">
													
													<input class="form-control" name="url" id="uUrl"  required type="text" style="max-width:210px;width:100%;"  />
													&nbsp;<button type="button" data-toggle="modal" data-target="#mymodal-data1" class="btn btn-primary">选择内容</button>
													&nbsp;<button type="button" data-toggle="modal" data-target="#mymodal-data2" class="btn btn-primary">选择图文</button>
												</div>
											</div>
										</div>
									</div>
									



						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">保存</button>
							
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
						
					</div>
				</div>
			</div>

			<!-- 模态弹出窗内容 -->
			<div class="modal  fade" id="mymodal-data1" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">请选择内容</h4>
						</div>
						<div class="modal-body">
							
							<div class="table-responsive" style="max-height: 350px;overflow: auto;">
								<table  class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th class="center">
												<label>
													
												</label>
											</th>
											<th>封面图片</th>
											<th>所属栏目</th>
											<th width="80%">标题</th>
										</tr>
									</thead>

									<tbody>
									
										<c:forEach items="${articleList}" var="article">
										<tr>
											<td class="center">
													<label>
														<input type="radio" name="checkId" value="${article.id}" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
											<td style="text-align: center;"><img src="<%=request.getContextPath() %>/images/image-5.jpg" style="width: 40px;"/></td>
											<td>
											<c:if test="${article.parentId == 1}">微官网</c:if>
											<c:if test="${article.parentId == 2}">最新动态</c:if>
											<c:if test="${article.parentId == 3}">产品介绍</c:if>
											<c:if test="${article.parentId == 4}">业务分类</c:if>
											<c:if test="${article.parentId == 5}">服务流程</c:if>
											</td>
											<td style="max-width:300px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
												${article.title}
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								
							</div><!-- /.table-responsive -->
						</div><!-- /.modal-body -->
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="checkArticle()" >保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			
			</div><!-- /.modal -->
			
			

			<!-- 模态弹出窗内容 -->
			<div class="modal  fade" id="mymodal-data2" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">请选择图文</h4>
						</div>
						<div class="modal-body">
							
							<div class="table-responsive" style="max-height: 350px;overflow: auto;">
								<table  class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th class="center">
												<label>
													
												</label>
											</th>
											<th>封面图片</th>
											<th>图文类型</th>
											<th width="70%">标题</th>
										</tr>
									</thead>

									<tbody>
									
										<c:forEach items="${materialList}" var="material">
										<tr>
											<td class="center">
													<label>
														<input type="radio" name="checkIdss" value="${material.id}" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
											<td style="text-align: center;"><img src="<%=request.getContextPath() %>/images/image-5.jpg" style="width: 40px;"/></td>
											<td>
											<c:if test="${material.type == 0}">单图文</c:if>
											<c:if test="${material.type == 1}">多图文</c:if>
											
											</td>
											<td style="max-width:300px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
												${material.title}
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								
							</div><!-- /.table-responsive -->
						</div><!-- /.modal-body -->
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="checkMaterial()" >保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			
			</div><!-- /.modal -->


				
			<div class="modal  fade" id="keyWBtn" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">链接地址</h4>
						</div>
						<div class="modal-body">
							<div class="form-group" style="height:55px;">
								
								<div class="form-group" style="height:55px;">
										
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													
													<textarea id = "uContent" name ="content" style="width:500px;height:80px;"></textarea>
												</div>
											</div>
										</div>
									</div>
								
								
								
							</div>
							
						</div>
						
					</div>
				</div>
			</div>



</form>
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->