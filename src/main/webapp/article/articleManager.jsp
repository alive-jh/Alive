<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/local/zh_CN.js"></script>



<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>
		

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
$(document).ready( function () {

			
			KindEditor.ready(function(K) {
				editor = K.create('textarea[name="content"]', {
					allowFileManager : true
				});

			});
			
		
$("#sParentId").val("${queryDto.parentId}");

	$.ajax({
		type: "POST",
		data:"page=${queryDto.page}&pageSize=${queryDto.pageSize}&parentId=${queryDto.parentId}&title=${queryDto.title}&content=${queryDto.content}",
		dataType: "json",
		url: "<%=request.getContextPath() %>/article/articleManagerView", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
			
			var articleListTab = $('#articleListTab'); 
			$("#articleListTab tr:not(:has(th))").remove();
			//articleListTab.append("<tr><td colspan=\"20\" align=\"center\"><div class=\"jiazai\"><span>正在加载，请等待...</span></div></td></tr>");
			articleListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
														
		},
		complete:function(){
			
			
		},
		success: function(data){

			//数据执行完成
			var articleListTab = $('#articleListTab');
			$("#articleListTab tr:not(:has(th))").remove();
			for(var i=0;i<data.infoList.length;i++){
				
				
				
				newcontent = newcontent + '<tr><td class="center"><input type="checkbox"  id="checkId" name="checkId" value ="'+data.infoList[i].id+'"/></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].title+'</td>';
				
				
				
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].parentName+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].releaseDate+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].createDate+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-success"  onclick="toArticle('+data.infoList[i].id+')" ><i class="icon-eye-open bigger-120"></i></i></button><button class="btn btn-xs btn-info"  onclick="editArticle('+data.infoList[i].id+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button><button onclick="deleteArticle('+data.infoList[i].id+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>';
				

				
				
				
				

				newcontent = newcontent + '</td>';
				
	}
			
			articleListTab.append(newcontent);
			
			
	}

	});
});


function toArticle(id)
{

	var openUrl = "";//弹出窗口的url
	var iWidth=400; //弹出窗口的宽度;
	var iHeight=700; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;

	window.open ('<%=request.getContextPath() %>/article/toArticle?articleId='+id,'内容预览','height=700,width=400,top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') 

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

function deleteArticle(id){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addarticle'].action ="deleteArticle?articleId="+id;
		document.forms['addarticle'].submit();
	}
}



function editArticle(id){
	
				
				
					$('#uTitle')[0].value = '';		
					$('#uSource')[0].value = '';
				    $("#uParentId").value = '';		
					document.forms['addarticle'].id.value ='';
				
					  
				if(id!='')
				{
						$.ajax({
							type: "POST",
							data:"articleId="+id,
							dataType: "json",
							url: "<%=request.getContextPath() %>/article/articleManagerView", 
							context: document.body, 
							beforeSend:function(){											
							},
							complete:function(){
								
								
							},
							success: function(data){

								document.forms['addarticle'].id.value = data.infoList[0].id;
								document.forms['addarticle'].tempDate.value = data.infoList[0].createDate;
								$('#uTitle')[0].value = data.infoList[0].title;
								
								$('#uSource')[0].value = data.infoList[0].source;
								$("#uParentId")[0].value = data.infoList[0].parentId;
								$("#uReleaseDate")[0].value = data.infoList[0].releaseDate;
								
								//$('#uContent')[0].value=data.infoList[0].content;
								
								editor.text(editor.html(data.infoList[0].content));
								
								
								
								 
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
							<li class="active">微信设置</li>
							<li class="active">文章管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post" name="searchForm" action="<%=request.getContextPath()%>/article/articleManager" style="float: left;width:100%">
								<table class="filterTable">
									
									<tr>
										<td>标题</td>
										<td><input type="text" name="title" value="${queryDto.title}"></td>
										<td>内容</td>
										<td>
											<input type="text" name="content" value="${queryDto.content}">
										</td>
										<td>上级栏目</td>
										<td>
											<select id="sParentId" name="parentId">
												<option value="">请选择</option>
												<option value="1">微官网</option>
												<option value="2">最新动态</option>
												<option value="3">产品介绍</option>
												<option value="4">业务分类</option>
												<option value="5">服务流程</option>
											</select>
										</td>
									</tr>
									
								</table>
							   
								<div class="btn-group col-md-12">
										 <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editArticle('')"><span class="icon-plus"></span>添加</button>
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
											<table id="articleListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														<th width="10" class="center">
															<label>
																<input id="checkAllId" name="checkAllId" onclick="checkAll()" type="checkbox" class="ace" />
																<span class="lbl"></span>
															</label>
														</th>
														<th width="500">标题</th>
														<th width="30">上级栏目</th>
														<th width="100">发布日期</th>
														<th width="100">创建日期</th>
								
														<th width="120">操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
						<f:page page="${resultPage}" url="./articleManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="article" method="post" name="addarticle" action ="<%=request.getContextPath()%>/article/saveArticle">
			 <input type="hidden" name="id" value="${keyword.id}">
			 <input type="hidden" name="tempDate" >
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
				<div class="modal-dialog" style="width:1000px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改用户信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">文章标题</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:710px;">
													<input class="form-control" name="title" id="uTitle"  value="${article.title}" type="text" data-rule="标题:required;"style="max-width:650px;width:100%;"  />
													</div>
											</div>
										</div>
									</div>
							
								<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">上级栏目</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<select id="uParentId" style="width:210px;" name="parentId" >
														<option value="1">微官网</option>
														<option value="2">最新动态</option>
														<option value="3">产品介绍</option>
														<option value="4">业务分类</option>
														<option value="5">服务流程</option>
														
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">来源</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uSource" name ="source" class="input-medium" type="text"data-rule="来源:required;" style="width:710px;"/>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">发布日期</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uReleaseDate" name ="releaseDate" class="input-medium" placeholder="" onclick="WdatePicker({isShowClear:true,readOnly:true})" type="text" data-rule="发布日期:required;" style="width:710px;"/>
												</div>
											</div>
										</div>
									</div>

										<div class="form-group" style="height:335px;">
										<label class="col-sm-2 control-label no-padding-right text-right">文章内容</label>
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group">
													
													<textarea id = "uContent" name ="content" style="width:650px;height:350px;" data-rule="文章内容:required;">${account.content}</textarea>
										
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