<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/local/zh_CN.js"></script>



<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>
<link rel="stylesheet" href="css/commen.css"/>

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
var data = ${jsonStr};
$(document).ready( function () {

			
			if(data!= ''){

			//数据执行完成
			var articleListTab = $('#articleListTab');
			$("#articleListTab tr:not(:has(th))").remove();
			for(var i=0;i<data.infoList.length;i++){
				
				
				
				
				newcontent = newcontent + '<tr><td nowrap="nowrap" align="left" ><img witch="200" right="100" src="<%=request.getContextPath()%>/wechatImages/mall/'+data.infoList[i].banner+'"></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].title+'</td>';
				
				
				if(data.infoList[i].status ==0)
				{
						
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr><button onclick="updateSpecialStatus('+i+',1)" class="btn btn-xs btn-danger">停用</button></nobr></td>';
					
				}
				else
				{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr><button class="btn btn-xs btn-success"onclick="updateSpecialStatus('+i+',0)">启用</button></nobr></td>';
				}
				
				

				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editArticle('+i+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button><button onclick="deleteArticle('+i+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>';

				newcontent = newcontent + '</td>';
				
	}
			
			articleListTab.append(newcontent);
			
			
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

function deleteArticle(index){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addarticle'].action ="deleteMallBanner?bannerId="+data.infoList[index].id;
		document.forms['addarticle'].submit();
	}
}



var tempCateId;
var tempName;
var tempAuthor;
var tempIds = '';
function editArticle(index)
{
	
	if(index.length == '')
	{
		 $('#uTitle')[0].value = '';	
		 $('#uUrl')[0].value = '';	
		 document.getElementById('preview1').innerHTML =''
		 document.forms['addarticle'].logo.value = '';	    
		 document.forms['addarticle'].oldLogo.value = '';
		 document.forms['addarticle'].id.value = '';
	}
	else
	{
		document.forms['addarticle'].id.value = data.infoList[index].id;	
	  
		$('#uTitle')[0].value = data.infoList[index].title;
		$('#uUrl')[0].value = data.infoList[index].url;
		$("input[name=status]:eq("+data.infoList[index].status+")").attr("checked",'checked');
		$("input[name=urlType]:eq("+data.infoList[index].urlType+")").attr("checked",'checked');

		$("input[name=type]:eq("+data.infoList[index].type+")").attr("checked",'checked');

		
		if(data.infoList[index].banner != undefined &&data.infoList[index].banner !='')
		{
			
			document.getElementById('preview1').innerHTML = '<img width="80" height="80" src="<%=request.getContextPath() %>/wechatImages/mall/'+data.infoList[index].banner+'"/>';
			document.forms['addarticle'].logo.value = data.infoList[index].banner;
			document.forms['addarticle'].oldLogo.value = data.infoList[index].banner;
			
		}
		
	

	}
				
						
				
				
} 


var id_coin="";
//上传图片前预览功能
		function previewImage(file,id) {
			
			var MAXWIDTH = 80;
			var MAXHEIGHT = 80;
			
			var div = document.getElementById('preview'+id);
			
			
			if (file.files && file.files[0]) {
				
				
				div.innerHTML = '<img id=imghead'+id+'>';
				var img = document.getElementById('imghead'+id);
				
			
				img.onload = function() {
					var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
							img.offsetWidth, img.offsetHeight);
					img.width = rect.width;
					img.height = rect.height;
					img.style.marginLeft = rect.left + 'px';
					img.style.marginTop = rect.top + 'px';
				}
				var reader = new FileReader();
				reader.onload = function(evt) {
					img.src = evt.target.result;
				}
				reader.readAsDataURL(file.files[0]);
			} else {
				
				var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
				file.select();
				var src = document.selection.createRange().text;
				
				div.innerHTML = '<img id=imghead'+id+'>';
				var img = document.getElementById('imghead'+id);
			
		
				img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
				var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
						img.offsetWidth, img.offsetHeight);
				status = ('rect:' + rect.top + ',' + rect.left + ','
						+ rect.width + ',' + rect.height);
				div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;margin-left:"+rect.left+"px;"+sFilter+src+"\"'></div>";
			}
		}


		function clacImgZoomParam(maxWidth, maxHeight, width, height) {
			var param = {
				top : 0,
				left : 0,
				width : width,
				height : height
			};
			if (width > maxWidth || height > maxHeight) {
				rateWidth = width / maxWidth;
				rateHeight = height / maxHeight;
				if (rateWidth > rateHeight) {
					param.width = maxWidth;
					param.height = Math.round(height / rateWidth);
				} else {
					param.width = Math.round(width / rateHeight);
					param.height = maxHeight;
				}
			}
			param.left = Math.round((maxWidth - param.width) / 2);
			param.top = Math.round((maxHeight - param.height) / 2);
			return param;
		}

function cleraImg(id)
{
	var div = document.getElementById('preview'+id);
	div.innerHTML = '';
	if(id ==1)
	{
		document.forms['addarticle'].logo.value ='';
	}
	
	
}



function addmallspecifications()
{
	
	
	
	 var $tr=$("#mallTab tr:last");
	 var tempRow = $("#mallTab").find("tr").length;
	 var trHtml = '<tr id="tempRow'+tempRow+'">'
     trHtml = trHtml + '<td>'+tempCateId+'</td>';
	 trHtml = trHtml + '<td>'+tempName+'</td>';
	 trHtml = trHtml + '<td>'+tempAuthor+'</td>';
	 var str = '<td><a href="javascript:delRows(@'+tempRow+'@,@'+tempCateId+',@)">删除</a></td></tr>';
	 str = str.replace(/@/g,"'")
	 
     trHtml = trHtml + str;
     $tr.after(trHtml);
	
	tempIds = tempIds+tempCateId+",";

	document.forms['addarticle'].tempIds.value = tempIds;
	
	
}



function delRows(index,id)
{

	tempIds = tempIds.replace(id,"");
	document.forms['addarticle'].tempIds.value = tempIds;
	$("#tempRow"+index).remove();  

}



function updateSpecialStatus(index,status)
{

	document.forms['addarticle'].action = "./updateMallBannerStatus?bannerId="+data.infoList[index].id+"&tempStatus="+status;
	document.forms['addarticle'].submit();
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
							<li class="active">首页封面</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post"  name="searchForm" action="<%=request.getContextPath()%>/specia/specialManager" style="float: left;width:100%">
								
							   
								<div class="btn-group col-md-12">
										 <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editArticle('')"><span class="icon-plus"></span>添加</button><!--
										 <button type="submit" class="btn btn-purple ml10 mb10">查询
										 <i class="icon-search icon-on-right bigger-110"></i>
										 </button>-->
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
														<th width="100">封面</th>
														<th width="100">标题</th>
														<th width="100">状态</th>
														
														<th width="120">操作</th>
													</tr>
												</thead>
	
												
											</table>
											    
						<f:page page="${resultPage}" url="./specialManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="article" method="post" name="addarticle" enctype="multipart/form-data" action ="<%=request.getContextPath()%>/mall/saveMallBanner">
			 <input type="hidden" name="id" value="">
			  <input type="hidden" name="sort" >
			 <input type="hidden" name="tempDate" >
			 <input type="hidden" name="tempIds" >
			 <input type="hidden" name="logo" >
			 <input type="hidden" name="oldLogo" >
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
				<div class="modal-dialog" style="width:1100px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改封面信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">标题</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:710px;">
													<input class="form-control" name="title" id="uTitle"  type="text" data-rule="标题:required;"style="max-width:650px;width:100%;"  />
													</div>
											</div>
										</div>
									</div>
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">封面</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:910px;">
													
														&nbsp;&nbsp;&nbsp;&nbsp;<div style="display: inline;position: relative;">
															<button class="btn btn-info btn-sm">上传图片</button>
																<input type="file"  name = "file1" id="file1" onchange="previewImage(this,'1');" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
														
														</div>
														

													</div>
											</div>
										</div>
									</div>
							
							<div class="form-group" id="picLogo"style="height:100px;">
										<label class="col-sm-2 control-label no-padding-right text-right">图片预览</label>
											<div class="col-sm-10">
											<div class="input-medium"style="width:910px;">
												<div class="input-group" style="width:910px;">
													
														
														<div style="position: relative;display: inline-block;margin:15px;"onmouseover="$(this).find('input').css('display','');" onmouseout="$(this).find('input').css('display','none');">
															<input type="button" class="btn btn-danger" value=&times; title='删除' style="min-width:0px;display:none;position: absolute;right:-10px;top:-10px;border:none;border-radius: 40px;z-index:100;" onclick="cleraImg('1')"/>
															
															
															<div  id ="preview1"><img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/></div>
														
														</div>

														
														
												</div>
											</div>
										</div>
									</div>

									<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">是否显示</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
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

								<div class="form-group" style="height:35px">
										<label class="col-sm-2 control-label no-padding-right text-right">显示范围</label>
											<div class="col-sm-9">
											<div class="input-medium"  style="width:710px;">
												<div class="input-group"  style="width:710px;">
													<label class="inline">
														<input name="type" value="0" checked type="radio" class="ace" />
														<span class="lbl">全部</span>
													</label>
			
													&nbsp; &nbsp; &nbsp;
													<label class="inline">
														<input name="type"  value="1" type="radio" class="ace" />
														<span class="lbl">微信</span>
													</label>
													&nbsp; &nbsp; &nbsp;
													<label class="inline">
														<input name="type"  value="2" type="radio" class="ace" />
														<span class="lbl">凡豆伴</span>
													</label>
												</div>
											</div>
										</div>
									</div>



								<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">链接类型</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:710px;">
												<label class="inline">
														<input name="urlType" value="0" checked type="radio" class="ace" />
														<span class="lbl">跳转地址</span>
													</label>
													&nbsp; &nbsp; &nbsp;
													<label class="inline">
														<input name="urlType"  value="1" type="radio" class="ace" />
														<span class="lbl">商品地址</span>
													</label>
												</div>
											</div>
										</div>
								</div>
								
								<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">链接地址</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:710px;">
													<input class="form-control" name="url" id="uUrl"  type="text" data-rule="链接地址:required;"style="max-width:650px;width:100%;"  />
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