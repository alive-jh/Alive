<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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





var data = ${jsonStr};
$(document).ready( function () {
	
	
	
	$("#sStatus").val("${queryDto.status}");
	$("#sAccount").val("${queryDto.account}");
	


	
			if(data !='')
			{
				var accountListTab = $('#accountListTab');
				$("#accountListTab tr:not(:has(th))").remove();
				accountListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		
				for(var i=0;i<data.infoList.length;i++){

					
					newcontent = newcontent + '<tr><td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].orderNumber+'</nobr></td>';
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].electrismName+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].memberName+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].contacts+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].mobile+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].payment+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].serviceName+'</td>';
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].statusName+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].orderDate+'</nobr></td>';
					
				
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="setOrder('+i+',0)" data-toggle="modal" data-target="#mymodal-data">编辑</button></td>';

					
					
					
					newcontent = newcontent + '</tr>';
				}
	
				$("#accountListTab tr:not(:has(th))").remove();
				accountListTab.append(newcontent);
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

function updateElectrism(id,status){

	
		document.forms['addarticle'].action ="updateElectrismStatus?electrismId="+id+"&status="+status;
		document.forms['addarticle'].submit();
	
}


function deleteArticle(id){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addarticle'].action ="deleteElectrism?electrismId="+id;
		document.forms['addarticle'].submit();
	}
}



function editArticle(id){
	
				
				
					$('#uName')[0].value = '';		
						
					$('#uMobile')[0].value = '';		
					$('#uArea')[0].value = '';		
					$('#uHobbies')[0].value = '';		
					document.forms['addarticle'].id.value ='';
				    document.getElementById('preview').innerHTML =''
				    document.forms['addarticle'].headImg.value = '';
					document.forms['addarticle'].oldLogo.value = '';
					$('#uAddress')[0].value ='';
					$('.tagLists').find('.tag-info').not('.tag-modal').remove();
					editor.text('');
					$("#mallTab  tr:not(:first)").empty();
					$("input[name='didstrictId']:checkbox").each(function() {						
						 this.checked = false;
					})
					$("input[name='items']:checkbox").each(function() {						
						 this.checked = false;
					})
				 
				if(id!='')
				{
						$.ajax({
							type: "POST",
							data:"electrismId="+id,
							dataType: "json",
							url: "<%=request.getContextPath() %>/electrism/electrismManagerView", 
							context: document.body, 
							beforeSend:function(){											
							},
							complete:function(){
								
								
							},
							success: function(data){

								document.forms['addarticle'].id.value = data.infoList[0].id;
								document.forms['addarticle'].tempDate.value = data.infoList[0].createDate;
								$('#uName')[0].value = data.infoList[0].name;
								$('#uMobile')[0].value = data.infoList[0].mobile;
								$('#uArea')[0].value = data.infoList[0].area;
								$('#uHobbies')[0].value = data.infoList[0].hobbies;
								$('#uAddress')[0].value = data.infoList[0].address;
																
								if(data.infoList[0].headImg != undefined &&data.infoList[0].headImg !='')
								{
																	
									document.getElementById('preview').innerHTML = '<img width="80" height="80" src="<%=request.getContextPath() %>/wechatImages/electrism/'+data.infoList[0].headImg+'"/>';
									document.forms['addarticle'].headImg.value = data.infoList[0].headImg;
									document.forms['addarticle'].oldLogo.value = data.infoList[0].headImg;
									
								}
								
								if(data.infoList[0].didstrict != '' && data.infoList[0].didstrict != undefined)
								{
									
									document.forms['addarticle'].didstrict.value = data.infoList[0].didstrict;
									for(var i=0;i<data.infoList[0].didstrict.split(',').length;i++)
									{
										

										$("input[name='didstrictId']:checkbox").each(function() {
			 
											 
											 if(data.infoList[0].didstrict.split(',')[i] == $(this).val())
											 {
												
												 this.checked = true;
												 addDidstrict(data.infoList[0].didstrict.split(',')[i]);
											 }
											   
										})
									}
								}
								
								if(data.infoList[0].item != '' && data.infoList[0].item != undefined)
								{
									
									document.forms['addarticle'].item.value = data.infoList[0].item;
									for(var i=0;i<data.infoList[0].item.split(',').length;i++)
									{
										

										$("input[name='items']:checkbox").each(function() {
			 
											
											 if(data.infoList[0].item.split(',')[i] == $(this).val())
											 {
												
												 this.checked = true;
												 addItems(data.infoList[0].item.split(',')[i]);
											 }
											   
										})
									}
								}

							
								
								
								editor.text(editor.html(data.infoList[0].content));
								
								
								
								 
							}

						});
				}
						
				
				
			} 


var id_coin="";
//上传图片前预览功能
		function previewImage(file) {
			
			var MAXWIDTH = 80;
			var MAXHEIGHT = 80;
			
			var div = document.getElementById('preview');
			
			
			if (file.files && file.files[0]) {
				
				
				div.innerHTML = '<img id="imghead">';
				var img = document.getElementById('imghead');
				
			
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
				
				div.innerHTML = '<img id="imghead">';
				var img = document.getElementById('imghead');
			
		
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

function cleraImg()
{
	var div = document.getElementById('preview');
	div.innerHTML = '';
	
	document.forms['addarticle'].headImg.value ='';
	
	
	
}



function addmallspecifications()
{
	
	
	
	 var $tr=$("#mallTab tr:last");
	 var tempRow = $("#mallTab").find("tr").length;
	 var trHtml = '<tr id="tempRow'+tempRow+'">'
     trHtml = trHtml + '<td><input type="text" name="names" required value=""></td>';
	 trHtml = trHtml + '<td><input type="text" name="prices" required value=""></td>';
	 trHtml = trHtml + '<td><input type="text" name="counts" required value=""></td>';
     trHtml = trHtml + '<td><a href="javascript:delRows('+tempRow+')">删除</a></td></tr>';
     $tr.after(trHtml);



}


function delRows(index)
{
	$("#tempRow"+index).remove();  
}


function addDidstrict(info)
{
	var _html = info;
					
	var newTag = $('.tag-modal').clone(true);
	$(newTag).find('span').html(_html);
	$(newTag).appendTo($('.tagLists')).css('display','').removeClass('tag-modal');
}

function setDidstrict()
{
	$('.tagLists').find('.tag-info').not('.tag-modal').remove();
	var ids = '';
				$("input[name='didstrictId']:checkbox").each(function() {
					 
				if(this.checked == true)
				{
					ids = ids + $(this).val()+',';
					addDidstrict($(this).val());
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
				
				document.forms['addarticle'].didstrict.value = ids;
				$('#mymodal-data1').modal('hide');

}

function addItems(info)
{
	var _html = info;
	var newTag = $('.tag-modal').clone(true);
	$(newTag).find('span').html(_html);
	$(newTag).appendTo($('.tagSelected')).css('display','').removeClass('tag-modal');

	
}


function setItems()
{
	$('.tagSelected').find('.tag-info').not('.tag-modal').remove();
	var ids = '';
	$("input[name='items']:checkbox").each(function() {
		 
	if(this.checked == true)
	{
		ids = ids + $(this).val()+',';
		
		addItems($(this).val());
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
	
	document.forms['addarticle'].item.value = ids;
	
	$('#mymodal-data2').modal('hide');

}





	jQuery(function($) {
	
		$('.tag').css('cursor','pointer');
		$('.tag .close').click(function(){
		
		var tempIds = document.forms['addarticle'].didstrict.value;
		tempIds = tempIds.replace($(this).parent().find('span').html()+",","");
		tempIds = tempIds.replace($(this).parent().find('span').html(),"");
		document.forms['addarticle'].didstrict.value = tempIds;
		$(this).parent().remove();
	});
			
				
			
				
			});

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
							<li class="active">预约订单</li>
							<li class="active">订单列表</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post"  name="searchForm" action="<%=request.getContextPath()%>/electrism/electrismOrderManager" style="float: left;width:100%">
								<table class="filterTable">
									
									<tr>
										
										
										<td>订单日期</td>
										<td colspan="3">
											 <div style="display:inline-block"><input type="text" name ="startDate"  onclick="WdatePicker({isShowClear:true,readOnly:true})" value="${queryDto.startDate}"></div>
											 --
											 <div style="display:inline-block"><input type="text" name="endDate"onclick="WdatePicker({isShowClear:true,readOnly:true})" value="${queryDto.endDate}"> </span></div>
										   	
										</td>
									</tr>
									<tr>
										<td>订单编号</td>
										<td><input type="text" name="number" value="${queryDto.number}"></td>
										<td>手机号码</td>
										<td><input type="text" name="mobile" value="${queryDto.mobile}"></td>

										<td>选择电工</td>
										<td><select id="sAccount" name="account">
										<option value="">请选择</option>
										<c:forEach items="${electrismList}" var="temp" >
											<option value="${temp[0]}">${temp[1]}</option>
											
										</c:forEach>
										</select></td>
										<td>订单状态</td>
										<td><select id="sStatus" name="status">
										<option value="">请选择</option>
										<option value="0">待处理</option>
										<option value="1">进行中</option>
										<option value="2">已取消</option>
										<option value="5">已完成</option>
										<option value="6">已评论</option>
										</select></td>
										
										
									</tr>
									
								</table>
							   
								<div class="btn-group col-md-12">
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
											<table id="accountListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														
														<th width="100">订单编号</th>
														<th width="100">服务电工</th>
														<th width="100">会员名称</th>
														<th width="100">联系人</th>
														<th width="100">手机号码</th>
														<th width="100">订单金额</th>
														<th width="100">服务项目</th>
														<th width="100">订单状态</th>
														<th width="100">订单时间</th>
														<th width="120">操作</th>
													</tr>
												</thead>
	
												
											</table>
											    
						<f:page page="${resultPage}" url="./electrismOrderManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="article" method="post" name="addarticle" enctype="multipart/form-data" action ="<%=request.getContextPath()%>/electrism/saveElectrism">
			 <input type="hidden" name="id" value="">
			 <input type="hidden" name="tempDate" >
			 <input type="hidden" name="headImg" >
			 <input type="hidden" name="oldLogo" >
	
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
				<div class="modal-dialog" style="width:1100px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改电工信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div class="form-group" id="picLogo"style="height:100px;">
										<label class="col-sm-2 control-label no-padding-right text-right"></label>
											<div class="col-sm-10">
											<div class="input-medium"style="width:910px;">
												<div class="input-group" style="width:910px;">
													<div style="align:left;position:relative;display: inline-block;margin:15px;"onmouseover="$(this).find('input').css('display','');" onmouseout="$(this).find('input').css('display','none');">
															<input type="button" class="btn btn-danger" value=&times; title='删除' style="min-width:0px;display:none;position: absolute;right:-10px;top:-10px;border:none;border-radius: 40px;z-index:100;" onclick="cleraImg()"/>
															
															
															<div  id ="preview"><img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/></div>
														
														</div>

														
														
												</div>
											</div>
										</div>
									</div>
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">头像</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:910px;">
												<div style="display: inline;position: relative;">
															<button class="btn btn-info btn-sm">上传图片</button>
																<input type="file"  name = "file1" id="file1" onchange="previewImage(this);" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
														
														</div>
														

													</div>
											</div>
										</div>
							</div>
							
							

						<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">真实姓名</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:240px;">
													<input class="form-control" name="name" id="uName"  type="text" data-rule="真实姓名:required;"style="max-width:650px;width:100%;"  />
													</div>
											</div>
										</div>
									</div>
						<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">籍贯</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:240px;">
													<input class="form-control" name="area" id="uArea"  type="text" data-rule="籍贯:required;"style="max-width:650px;width:100%;"  />
													</div>
											</div>
										</div>
									</div>
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">手机号码</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:240px;">
													<input class="form-control" name="mobile" id="uMobile"  type="text" data-rule="手机号码:required;mobile;remote[getMember]"style="max-width:650px;width:100%;"  />
													</div>
											</div>
										</div>
									</div>
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">居住地址</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:240px;">
													<input class="form-control" name="address" id="uAddress"  type="text" data-rule="居住地址:required;"style="max-width:650px;width:100%;"  />
													</div>
											</div>
										</div>
									</div>
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">兴趣爱好</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:240px;">
													<input class="form-control" name="hobbies" id="uHobbies"  type="text" data-rule="商品名称:required;"style="max-width:650px;width:100%;"  />
													</div>
											</div>
										</div>
									</div>

								<div class="form-group" style="height:50px;">
										<label class="col-sm-2 control-label no-padding-right text-right">服务商圈</label>
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group" style="width:650px;">
												<button class="btn btn-primary"  data-toggle="modal" data-target="#mymodal-data1" >选择服务商圈</button>

												<input type="hidden" name="didstrict" id="uDidstrict" />
												
												</div>
												<div class="tagLists tags" style="border: none;width:auto;padding-left:0;width:650px;">
													<span class="tag-modal tag tag-info" style="display: none;">
														<span>模板</span>
														<button type="button" class="close">×</button>
													</span>

												</div>
											</div>
										</div>
								</div>

								<div class="form-group" style="height:50px;">
										<label class="col-sm-2 control-label no-padding-right text-right">服务项目</label>
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group">
											<button class="btn btn-primary"  data-toggle="modal" data-target="#mymodal-data2" >选择服务项目</button>
											<input type="hidden" name="item" id="uItems" />
											</div>
											
											<div class="tags tagSelected" style="border: none;width:auto;padding-left:0;width:650px;">
													
											</div>
												
											</div>
										</div>
									</div>

									
									

									<div class="form-group" style="height:335px;">
										<label class="col-sm-2 control-label no-padding-right text-right">个人介绍</label>
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group">
													
													<textarea id = "uContent" name ="content" style="width:650px;height:350px;" data-rule="商品描述:required;">${account.content}</textarea>
										
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
			<!-- 模态弹出窗内容 -->
			<div class="modal  fade" id="mymodal-data1" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">

				<div class="modal-dialog" style="width:800px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">请选择内容</h4>
						</div>
						<div class="modal-body">
							
							<div class="table-responsive" style="max-height: 550px;overflow: auto;">
								<table  class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											
											<th style="width:60px;">区域</th>
											<th>商圈</th>
											
										</tr>
									</thead>

									<tbody>
									
										<c:forEach items="${tempList}" var="didstrict">
										<c:if test="${didstrict.parentId ==0}">
										<tr>
											<td>
											${didstrict.name}
											</td>
											<td>
											<c:forEach items="${tempList}" var="tempDidstrict">
												<c:if test="${didstrict.id ==tempDidstrict.parentId}">
													<input  id="didstrictId"name="didstrictId" value="${tempDidstrict.name}" type="checkbox" />&nbsp;${tempDidstrict.name}&nbsp;
												</c:if>
											</c:forEach>
											</td>
											
										</tr>
										</c:if>
									</c:forEach>
									</tbody>
								</table>
								
							</div><!-- /.table-responsive -->
						</div><!-- /.modal-body -->
						<div class="modal-footer">
							<button type="button"  onclick="setDidstrict()" class="btn btn-primary">保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
		
			</div><!-- /.modal -->


			<!-- 模态弹出窗内容 -->
			<div class="modal  fade" id="mymodal-data2" tabindex="-3" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">

				<div class="modal-dialog" style="width:800px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">请选择服务项目</h4>
						</div>
						<div class="modal-body">
							
							<div class="table-responsive" style="max-height: 550px;overflow: auto;">
								<table  class="table table-striped table-bordered table-hover">
									<thead>

											
											
										
									
										<tr>
											<th style="width:5px;"></th>
											<th style="width:30px;">服务项目</th>
											<th style="width:20px;">服务均价</th>
											<th>服务详情</th>
											
										</tr>
									</thead>

									<tbody>
									
										
										<tr>
											<td><input  id="items"name="items" value="上门换灯" type="checkbox" /></td>
											<td>
											上门换灯
											</td>
											<td>
											30元
											</td>
											<td>
											上门服务,安装LED灯
											</td>
										</tr>
										<tr>
										<td><input  id="items"name="items" value="电器维护" type="checkbox" /></td>
											
											<td>
											电器维护
											</td>
											<td>
											30元
											</td>
											<td>
											各种家用电器安装维护
											</td>
										</tr>
										<tr>
										<td><input  id="items"name="items" value="线路维护" type="checkbox" /></td>
											
											<td>
											线路维护
											</td>
											<td>
											30元
											</td>
											<td>
											老电路维护,新房布线等!
											</td>
										</tr>
									</tbody>
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
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->