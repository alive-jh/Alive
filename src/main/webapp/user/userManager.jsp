<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
document.forms['addUser'].action = "userManager";
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
		

$("#sStatus").val('${queryDto.status}');
$("#sRoleId").val('${queryDto.roleId}');
	


		if(data!= ''){

			//数据执行完成
			var userListTab = $('#userListTab');
			$("#userListTab tr:not(:has(th))").remove();
			for(var i=0;i<data.infoList.length;i++){
				
				
				
				newcontent = newcontent + '<tr><td class="center"><input type="checkbox"  id="checkId" name="checkId" value ="'+data.infoList[i].id+'"/></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].account+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].mobile+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].email+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].roleName+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].createdate+'</td>';

				
				if(data.infoList[i].status =='正常')
				{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><span class="label label-md label-success">'+data.infoList[i].status+'</span></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editUser('+i+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button>';

					newcontent = newcontent + '<button class="btn btn-xs btn-warning"dropdown-toggle" onclick="updateUserStatus('+i+',1)"data-toggle="dropdown"><i class="icon-lock bigger-120"></i></i></button>';
					newcontent = newcontent +'<button onclick="deleteUser('+i+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button>';
				}
				else
				{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><span class="label label-md label-warning">'+data.infoList[i].status+'</span></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  data-toggle="modal" onclick="editUser('+i+')" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button>';
					newcontent = newcontent +'<button class="btn btn-xs btn-success"onclick="updateUserStatus('+i+',0)"><i class="icon-unlock bigger-120"></i></button>';
					newcontent = newcontent +'<button onclick="deleteUser('+i+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button>';
				}
				
				

				newcontent = newcontent + '</td>';
				
	}
			
			userListTab.append(newcontent);
			
			
	}

});



function checkAll()
{
	 var checkAllId = document.getElementById("checkAllId");
	 if(checkAllId.checked == true)
	 {
		$("input[name='checkId']:checkbox").each(function() {
			 
        this.checked = true;  });
                              	
	 }
	 else
	 {
		$("input[name='checkId']:checkbox").each(function() { 
			
        this.checked = false;  }); 
	 }
}

function deleteUser(index){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addUser'].action ="deleteUser?userId="+data.infoList[index].id;
		document.forms['addUser'].submit();
	}
}

function updateUserStatus(index,status){

	
	document.forms['addUser'].action ="updateUserStatus?userId="+data.infoList[index].id+"&status="+status;
	document.forms['addUser'].submit();
	
}


function editUser(index){
	
				
				if(data.infoList[index] == undefined)
					{
					$('#uAccount')[0].value='';
					$('#uPassword')[0].value='';
					$('#uPassword1')[0].value='';
					$('#uName')[0].value='';
					$('#uMobile')[0].value='';
					$('#uEmail')[0].value='';
					$('#uRoleId option')[0].selected=true;
					$('#uStatus option')[0].selected=true;
					$("#divPwd").show();
					$("#divPwd1").show();
					document.forms['addUser'].id.value ='';
				}
				else {
					  
					  $("#divPwd").hide();
					  $("#divPwd1").hide();
					

								$('#uAccount')[0].value = data.infoList[index].account;
								
								$('#uPassword')[0].value=data.infoList[index].password;
								$('#uPassword1')[0].value=data.infoList[index].password;
								$('#uName')[0].value=data.infoList[index].name;
								$('#uMobile')[0].value=data.infoList[index].mobile;
								$('#uEmail')[0].value=data.infoList[index].email;
								
								 document.forms['addUser'].id.value = data.infoList[index].id;
								 $("#uAccount").attr("data-rule","账号:required;username;remote[getUser?userId="+data.infoList[index].id+"]");
								 $("#uPassword").attr("data-rule","密码:required;");
								 $("#uPassword1").attr("data-rule","确认密码:required;");
								 $("#uEmail").attr("data-rule","email:required;email;remote[getUser?userId="+data.infoList[index].id+"]");
								
								//$('#uType option')[0].selected=true;
								 $('#uStatus option')[index].selected=true;
								 if(data.infoList[index].status =='正常')
								 {
									 $("#uStatus").val(0);
									 $("#uStatus").get(0).selectedIndex=0;
								 }
								 if(data.infoList[index].status =='锁定')
								 {
									 $("#uStatus").val(1);
									 $("#uStatus").get(0).selectedIndex=1;
								 }
								$("#uRoleId").val(data.infoList[index].roleId);
								 
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
							<li class="active">系统管理</li>
							<li class="active">用户管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" name="searchForm" action="<%=request.getContextPath()%>/user/userManager" style="float: left;width:100%">
								<table class="filterTable">
									<tr>
										<td>账号</td>
										<td><input type="text" name="account" value="${queryDto.account}"></td>
										<td>名称</td>
										<td><input type="text" name="name" value="${queryDto.name}"></td>
										<td>手机号码</td>
										<td><input type="text" name="mobile" value="${queryDto.mobile}"></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td>email</td>
										<td><input type="text" name="email" value="${queryDto.email}"></td>
										<td>用户权限</td>
										<td>
											<select id="sRoleId" name = "roleId">
												
												<option value="">请选择</option>
												<c:forEach items="${roleList}" var="tempRole">
													<option value="${tempRole.id}">${tempRole.name}</option>	
												</c:forEach>
											
											</select>
										</td>
										<td>状态</td>
										<td>
											<select id="sStatus" name="status">
												<option value="">请选择</option>
												<option value="0">正常</option>
												<option value="1">锁定</option>
											</select>
										</td>
									</tr>
									<tr>
										<td>创建日期</td>
										<td colspan="3">
											 <div style="display:inline-block"><input type="text" name ="startDate" onclick="WdatePicker({isShowClear:true,readOnly:true})" value="${queryDto.startDate}"></div>
											 --
											 <div style="display:inline-block"><input type="text" name="endDate" onclick="WdatePicker({isShowClear:true,readOnly:true})" value="${queryDto.endDate}"></div>
										   	
										</td>
									</tr>
								</table>
							   
								<div class="btn-group col-md-12">
										 <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editUser('')"><span class="icon-plus"></span>添加</button>
										
										 <button type="submit" class="btn btn-purple ml10 mb10">查询
										 <i class="icon-search icon-on-right bigger-110"></i>
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
											<table id="userListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														<th class="center">
															<label>
																<input id="checkAllId" name="checkAllId" onclick="checkAll()" type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</th>
														<th>账号</th>
														<th>名称</th>
														<th>手机号码</th>
														<th>email</th>
														<th>用户权限</th>
														<th>创建日期</th>
														<th>状态</th>
														<th>操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
				<f:page page="${resultPage}" url="./userManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="user" method="post" name="addUser" action ="<%=request.getContextPath()%>/user/saveUser">
			 <input type="hidden" name="id" value="${user.id}">
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改用户信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								    <label class="col-sm-3 control-label no-padding-right text-right">账号</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uAccount" name ="account" value="${user.account}"class="input-medium " type="text"data-rule="账号:required;username;remote[getUser]" style="width:210px;"/>
												</div>
											</div>
										</div>
									</div>
							
							
									<div id ="divPwd" class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">密码</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input id="uPassword" name ="password" value="${user.password}" class="input-medium "  maxlength="18"type="password"  data-rule="密码: required;password;" style="width:210px;"/>
												</div>
											</div>
										</div>
									</div>
									
									<div id ="divPwd1"class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">确认密码</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input id="uPassword1" name ="password1" value="${user.password}" class="input-medium "  maxlength="18"type="password"  data-rule="确认密码: required;match(password);" style="width:210px;"/>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">用户名称</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id = "uName" name ="name" value="${user.name}" maxlength="30"type="text" 
													data-rule="用户名称:required"
													style="width:210px;"/>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">性别</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group" >
													<select id = "uSex" style="width:150px;" class="input-medium" name="sex">
														<option value="0">男</option>
														<option value="1">女</option>
														
													</select>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">手机号码</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uMobile" name ="mobile" value="${user.mobile}"class="input-medium " type="text"  data-rule="手机号码:required;mobile"  style="width:210px;"/>
												</div>
											</div>
										</div>
									</div>
									
									<div id="divEmail" class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">EMAIL</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uEmail"name ="email" value="${user.email}" type="text" id="inputEmail" data-rule="email:required;email;remote[getUser?userId=${tempUser.id}]"class="form-control" style="width:210px;">
												</div>
											</div>
										</div>
									</div>

									

									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">用户权限</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<select id="uRoleId" style="width:150px;"class="selectpicker bla bla bli" data-live-search="true" name="roleId" >
														<c:forEach items="${roleList}" var="tempRole">
															<option value="${tempRole.id}">${tempRole.name}</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
									</div>
									
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">状态</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<select id="uStatus" style="width:150px;"class="input-medium" name="status">
														<option value="0">正常</option>
														<option value="1">锁定</option>
														
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