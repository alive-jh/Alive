<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/local/zh_CN.js"></script>

<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>
<link rel="stylesheet" href="css/commen.css"/>

<script>


var newcontent = '';

var jsonData = null;

var result = "${queryDto.result}";

$(document).ready( function () {
	
	//反馈结果
	if(result!=""){
		alert(result);
	}

	$.ajax({
		type: "POST",
		data:"",
		dataType: "json",
		url: "<%=request.getContextPath() %>/book/bookCategoryView", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
			
			var articleListTab = $('#articleListTab'); 
			$("#articleListTab tr:not(:has(th))").remove();
			
			articleListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
														
		},
		complete:function(){
			
			
		},
		success: function(data){

			jsonData = data;
			//数据执行完成
			var articleListTab = $('#articleListTab');
			$("#articleListTab tr:not(:has(th))").remove();
			for(var i=0;i<data.infoList.length;i++){
					
			
				newcontent = newcontent + '<tr><td nowrap="nowrap" align="left" ><a href="<%=request.getContextPath()%>/book/bookManager?catId='+data.infoList[i].cat_id+'">'+data.infoList[i].mark+data.infoList[i].cat_name+'</a></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].unique_id+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].description+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].sort+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editArticle('+i+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button><button onclick="deleteArticle('+data.infoList[i].cat_id+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>';
				
				newcontent = newcontent + '</tr>';
				
	        }
			
			articleListTab.append(newcontent);
			
			
	}

	});
});



function deleteArticle(id){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addarticle'].action ="deleteBookCate?cat_id="+id;
		document.forms['addarticle'].submit();
	}
}


//添加或者修改书籍分类
function editArticle(index)
{
	document.forms['addarticle'].action ="saveBookCate";
	if(index.length=='0'){
	//增加
		categorySelect(false,index);
	}else{
	//修改
		categorySelect(true,index);
	}
	
}


function getCategoryNoLevel(bData,parentId,currentId,fData){
	for(var i=0;i<bData.length;i++){
		if(bData[i].parent_id==parentId&&bData[i].cat_id!=currentId){
			var cat={cat_id:bData[i].cat_id,cat_name:bData[i].cat_name,parent_id:bData[i].parent_id,mark:bData[i].mark};
			fData.push(cat);
			getCategoryNoLevel(bData,bData[i].cat_id,currentId,fData);
		}
	}
	
	return fData;
}


//添加分类
function categorySelect(flag,index){

	if(flag){
		//获取出自己和自己的子类之外的所有分类
		var bData=jsonData.infoList;
		var currentId=jsonData.infoList[index].cat_id;
		var parentId=0;
		var fData=new Array();
		fData=getCategoryNoLevel(bData,parentId,currentId,fData);
		$("#catId").val(jsonData.infoList[index].cat_id);//填充内容 
		$("#catName").val(jsonData.infoList[index].cat_name);
		$("#uniqueId").val(jsonData.infoList[index].unique_id);
		$("#keywords").val(jsonData.infoList[index].keywords);
		$("#description").text(jsonData.infoList[index].description);
		$("#sort").val(jsonData.infoList[index].sort);
		//修改
		$("#parentId option").remove();
		if(jsonData.infoList[index].parent_id==0){
			$("#parentId").append("<option value='0' selected='selected' >无</option>");
		}else{
			$("#parentId").append("<option value='0' >无</option>");
		}
		for(var i=0;i<fData.length;i++){
			if(jsonData.infoList[index].parent_id==fData[i].cat_id){
				$("#parentId").append("<option value='"+fData[i].cat_id+"' selected = 'selected' >"+fData[i].mark+fData[i].cat_name+"</option>");
			}else{
				$("#parentId").append("<option value='"+fData[i].cat_id+"'>"+fData[i].mark+fData[i].cat_name+"</option>");
			}
		}	
		
	}else{
		$("#catId").val("");
		$("#catName").val("");
		$("#uniqueId").val("");
		$("#keywords").val("");
		$("#description").text("");
		$("#sort").val("");
		//增加
		$("#parentId option").remove();
		$("#parentId").append("<option value='0' selected='selected' >无</option>");
		for(var i=0;i<jsonData.infoList.length;i++){
			$("#parentId").append("<option value='"+jsonData.infoList[i].cat_id+"'>"+jsonData.infoList[i].mark+jsonData.infoList[i].cat_name+"</option>");
		}	
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
							<li class="active">书籍分类</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post"  name="searchForm" action="<%=request.getContextPath()%>/mall/mallManager" style="float: left;width:100%">
						
							   
								<div class="btn-group col-md-12">
										 <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editArticle('')"><span class="icon-plus"></span>添加</button>
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
														<th width="100">分类名称</th>
														<th width="100">别名</th>
														<th width="100">简单描述</th>
														<th width="100">排序</th>
								
														<th width="120">操作</th>
													</tr>
												</thead>
	
												
											</table>
											    
						
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
		    <form class="article" method="post" name="addarticle" enctype="multipart/form-data" action ="<%=request.getContextPath()%>/mall/saveMallCate">
			<input type="hidden" name="catId" id="catId" value="">
	
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
				<div class="modal-dialog" style="width:1100px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改书籍分类</h4>
						</div>
						<div class="modal-body">
							
							
						<div  id="divAccount" class="form-group" style="height:35px;">
							<label class="col-sm-2 control-label no-padding-right text-right">分类名称</label>
										<div class="col-sm-9">
										<div class="input-medium" >
											<div class="input-group" style="width:710px;">
												<input class="form-control" name="catName" id="catName"  type="text" data-rule="分类名称:required;"style="max-width:650px;width:100%;"  />
												</div>
										</div>
								</div>
						</div>
						
						
						<div  id="divAccount" class="form-group" style="height:35px;">
							<label class="col-sm-2 control-label no-padding-right text-right">别名</label>
										<div class="col-sm-9">
										<div class="input-medium" >
											<div class="input-group" style="width:710px;">
												<input class="form-control" name="uniqueId" id="uniqueId"  type="text" data-rule="别名:required;"style="max-width:650px;width:100%;"  />
												</div>
										</div>
								</div>
						</div>

					    <div  id="divAccount" class="form-group" style="height:35px;">
							<label class="col-sm-2 control-label no-padding-right text-right">上级分类</label>
										<div class="col-sm-9">
										<div class="input-medium" >
											<div class="input-group" style="width:910px;">
												
												<select name="parentId" id="parentId">
													
												</select>

												</div>
										</div>
								</div>
						</div>

						<div  id="divAccount" class="form-group" style="height:35px;">
							<label class="col-sm-2 control-label no-padding-right text-right">关键字</label>
										<div class="col-sm-9">
										<div class="input-medium" >
											<div class="input-group" style="width:710px;">
												<input class="form-control" name="keywords" id="keywords"  type="text" data-rule="关键字:required;"style="max-width:650px;width:100%;"  />
												</div>
										</div>
								</div>
						</div>
						
						<div  id="divAccount" class="form-group" style="height:35px;">
							<label class="col-sm-2 control-label no-padding-right text-right">排序</label>
										<div class="col-sm-9">
										<div class="input-medium" >
											<div class="input-group" style="width:710px;">
												<input class="form-control" name="sort" id="sort"  type="text" data-rule="排序:required;"style="max-width:650px;width:100%;"  />
												</div>
										</div>
								</div>
						</div>
						
						
						<div class="form-group" style="height:335px;">
							<label class="col-sm-2 control-label no-padding-right text-right">简单描述</label>
								<div class="col-sm-10">
								<div class="input-medium">
									<div class="input-group">
										
										<textarea name ="description" id = "description"  style="width:650px;height:350px;" data-rule="简单描述:required;"></textarea>
							
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
			
			
	</div><!-- /.main-container -->