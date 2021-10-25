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
document.forms['addUser'].action = "userManager";
document.forms['addUser'].submit();
}
//本地条件分页查询
function pageJump(page) {
PPage("page_select", page, totalPageCount, "pageJump", true);
$("#currentPage").val(page);
}
var newcontent = '';


$(document).ready( function () {
		

$('#sMatchingrules')[0].value="${queryDto.matchingrules}";
$('#sStatus')[0].value="${queryDto.status}";

	$.ajax({
		type: "POST",
		data:"page=${queryDto.page}&pageSize=${queryDto.pageSize}&keyword=${queryDto.keyword}&status=${queryDto.status}&type=${queryDto.type}&matchingrules=${queryDto.matchingrules}",
		dataType: "json",
		url: "<%=request.getContextPath() %>/keyword/keywordManagerView", 
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
			for(var i=0;i<data.infoList.length;i++){
				
				
				
				newcontent = newcontent + '<tr><td class="center"><input type="checkbox"  id="checkId" name="checkId" value ="'+data.infoList[i].id+'"/></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].keyword+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].content+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].matchingRulesName+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].statusName+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].createDate+'</td>';

				
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editUser('+data.infoList[i].id+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button>';

					
					newcontent = newcontent +'<button onclick="removeKeyword('+data.infoList[i].id+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button>';
				
				
				
				

				newcontent = newcontent + '</td>';
				
	}
			
			userListTab.append(newcontent);
			
			
	}

	});
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

function removeKeyword(id){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addUser'].action ="removeKeyword?keywordId="+id;
		document.forms['addUser'].submit();
	}
}

function updateUserStatus(id,status){

	
	document.forms['addUser'].action ="updateUserStatus?userId="+id+"&status="+status;
	document.forms['addUser'].submit();
	
}

function editUser(id){
	
				
				if(id ==''){
					$('#uKeyword')[0].value='';
					$('#uContent')[0].value='';
					
					document.forms['addUser'].id.value ='';
				}
				else {
					  
					  $("#divPwd").hide();
					  $("#divPwd1").hide();
						$.ajax({
							type: "POST",
							data:"keywordId="+id,
							dataType: "json",
							url: "<%=request.getContextPath() %>/keyword/keywordManagerView", 
							context: document.body, 
							beforeSend:function(){											
							},
							complete:function(){
								
								
							},
							success: function(data){


								$('#uKeyword')[0].value = data.infoList[0].keyword;
								$('#uContent')[0].value = data.infoList[0].content;
								
								
								$('#uMatchingRules')[0].value = data.infoList[0].matchingRules;
								$('#uStatus')[0].value = data.infoList[0].status;
								document.forms['addUser'].id.value = id;
								
								 
							}

						});
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
							<li class="active">关键词管理</li>
							<li class="active">文本回复</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" name="searchForm" action="<%=request.getContextPath()%>/keyword/keywordManager" style="float: left;width:100%">
								<table class="filterTable">
									
									<tr>
										<td>关键词</td>
										<td><input type="text" name="keyword" value="${queryDto.keyword}"></td>
										<td>匹配规则</td>
										<td>
											<select id="sMatchingrules" name = "matchingrules">
												<option value="">请选择</option>
												<option value="0">精准匹配</option>
												<option value="1">模糊匹配</option>
												
											</select>
										</td>
										<td>状态</td>
										<td>
											<select id="sStatus" name="status">
												<option value="">请选择</option>
												<option value="0">启用</option>
												<option value="1">关闭</option>
											</select>
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
														<th>关键词</th>
														<th width="50%">回复内容</th>
														<th>匹配规则</th>
														<th>状态</th>
														<th>创建日期</th>
														<th>操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
				<f:page page="${resultPage}" url="./keywordManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="user" method="post" name="addUser" action ="<%=request.getContextPath()%>/keyword/saveKeyword">
			 <input type="hidden" name="id">
			  <input type="hidden" name="tempDate">
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改关键词信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">关键词</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group"  style="width:310px;">
													
													<input class="form-control" name="keyword" id="uKeyword"  value="${article.title}" type="text" data-rule="关键词:required;"style="max-width:210px;width:100%;"  />
													
												</div>
											</div>
										</div>
									</div>
							
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">匹配规则</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group" >
													<select id = "uMatchingRules" style="width:210px;" class="input-medium" name="matchingRules">
														<option value="0">精准匹配</option>
														<option value="1">模糊匹配</option>
														
													</select>
												</div>
											</div>
										</div>
									</div>
										<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">是否启用</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group" >
													<select id = "uStatus" style="width:210px;" class="input-medium" name="status">
														<option value="0">启用</option>
														<option value="1">关闭</option>
														
													</select>
												</div>
											</div>
										</div>
									</div>
									
									<div id="divEmail" class="form-group" style="height:65px;">
										<label class="col-sm-3 control-label no-padding-right text-right">回复内容</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<textarea  id="uContent" name ="content"  type="textarea" id="inputEmail" data-rule="回复内容:required"class="form-control" style="width:300px;;" rows="5"></textarea>
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