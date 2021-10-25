<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/local/zh_CN.js"></script>



<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>

<script>

var editor;
$(document).ready( function () {

		if("${material.id}"=="")
		{
			document.getElementById("save").disabled = true;	
		}
		

		


			KindEditor.ready(function(K) {
				editor = K.create('textarea[name="content"]', {
					allowFileManager : true
				});

					if("${material.id}"!='')
					{
							$('#myTab li').eq("${material.contentType}").addClass('active').siblings().removeClass('active');
							$('#myTabContent .tab-pane').eq("${material.contentType}").addClass('in active').siblings().removeClass('in active');

							if("${material.contentType}"=='1')
							{
								$.ajax({
									type: "POST",
									data:"materialId=${material.id}",
									dataType: "json",
									url: "<%=request.getContextPath() %>/material/materialManagerView", 
									context: document.body, 
									beforeSend:function(){											
									},
									complete:function(){
										
										
									},
									success: function(data){

										if(data.infoList[0].content!= '')
										{
											editor.text(editor.html(data.infoList[0].content));
										}
										

									
									}

								});
							}
							
					}	
		

			});

			


			
		

});



function checkTypeId(id)
{
	document.forms['materialForm'].typeId.value = id;
	
	if(id == 0)
	{
		if("${material.id}"=="")
		{
		document.getElementById("save").disabled = true;
		document.getElementById("showDiv").title = "当按钮不可用时,请选择内容或栏目!";
		}
		
	}
	else
	{
		document.getElementById("save").disabled ="";
		document.getElementById("showDiv").title = "";
	}
}

function cleraImg(id)
{
	var div = document.getElementById('preview'+id);
	div.innerHTML = '';
	document.forms['materialForm'].logoStatus.value = 1;
}
function previewImage(file,id) {
//	$("#logoStatus").val("0");
	var MAXWIDTH = 70;
	var MAXHEIGHT = 70;
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





function addMaterial(id)
{
	document.forms['materialForm'].action = "toMaterial?type=1&userType=add&showMaterialId="+id;
	document.forms['materialForm'].submit();

}
function editMaterial(id,parentId)
{
	document.forms['materialForm'].action = "toMaterial?type=1&userType=edit&showMaterialId="+parentId+"&oldMaterialId="+id;
	document.forms['materialForm'].submit();

}
function removeMaterial(id)
{
	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['materialForm'].action = "removeMaterial?userType=add&materialId="+id;
		document.forms['materialForm'].submit();

	}
	
}

function checkArticle(id)
{
	
	document.forms['materialForm'].url.value = id;
	

	
}


function setUrl()
{

	if(document.forms['materialForm'].url.value == '')
	{
		alert("请选择内容!");
		return;
	}
	else
	{
		document.getElementById("save").disabled = "";
		document.getElementById("showDiv").title = "";
		$('#mymodal-data').modal('hide');
	}
}



</script>

<div class="main-container" id="main-container">
			

			<div class="main-container-inner">
				
				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="main.htm">首页</a>
							</li>
							<li class="active">关键词回复</li>
							<li class="active">图文回复</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						
						<div class="row">
							<div class="col-xs-12 no-padding">
								<!-- PAGE CONTENT BEGINS -->
								
								<div class="col-xs-3">
									<div class="mBox">
										<div class="mBody">
											<div class="mTitle">
											<c:if test="${showMaterial.logo == null}">
												<img src="<%=request.getContextPath() %>/images/picture.png" class="big" style="width:100%;"/>
											</c:if>
											<c:if test="${showMaterial.logo != null}">
												<img src="<%=request.getContextPath() %>/wechatImages/${showMaterial.logo}" class="big" style="width:100%;"/>
											</c:if>
												<p class="mtitleText">${showMaterial.title}</p>
											</div>
											
											
					
											<c:if test="${material.type ==0}">
												<div class="sBody">
													
													<p class="mainText">${showMaterial.summary}</p>
												</div>
												
												<div style="clear: both;"></div>
											
											<hr style="margin:8px 0;"/>
											<div class="backBtn">
												<button class="btn">返回</button>
											</div>
											</c:if>

											<c:if test="${material.type ==1}">
											
											<c:forEach items="${materialList}" var="showMaterial">
												<div class="mMesg">
												<div class="col-xs-8" class="mesgText">
													${showMaterial.title}
												</div>
												<div class="col-xs-4 no-padding" class="mesgImg">
													<img src="<%=request.getContextPath() %>/wechatImages/${showMaterial.logo}" style="width:100%;height:80px;"/>
												</div>
												<div class="editBtns">
													<span class="col-xs-6 no-padding btnBg" onclick="editMaterial('${showMaterial.id}','${showMaterial.parentId}')">
														<i class="icon-pencil bigger-120"></i>
													</span>

													<span class="col-xs-6 no-padding btnBg" onclick="removeMaterial('${showMaterial.id}')">
														<i class="icon-trash bigger-120"></i>
													</span>
												</div>
												<div style="clear: both;"></div>
												<hr style="margin:8px 0;"/>
											</div>
											</c:forEach>

											<div class="mMesg add" onclick="$(this).before($('#mesgTemp').html());">
												<div class="col-xs-12" class="mesgText" style="text-align: center;">
													<i class="icon-plus" style="font-size:20px;"></i>
												</div>
												
												<div style="clear: both;"></div>
											</div>
											<div id="mesgTemp" style="display: none;">
												<div class="mMesg">
													<div class="col-xs-8" class="mesgText">
														标题
													</div>
													<div class="col-xs-4 no-padding" class="mesgImg">
														<img src="<%=request.getContextPath() %>/images/picture.png" style="width:100%;"/>
													</div>
													<div class="editBtns">
													<span class="col-xs-6 no-padding btnBg" onclick="addMaterial('${showMaterial.id}');">
														<i class="icon-pencil bigger-120"></i>
													</span>
													<span class="col-xs-6 no-padding btnBg" onclick="$(this).parent().parent().remove();">
														<i class="icon-trash bigger-120"></i>
													</span>
												</div>
													<div style="clear: both;"></div>
													<hr style="margin:8px 0;"/>
												</div>
											</div>

														</c:if>

										</div>
										
										<div style="clear: both;"></div>
									</div><!-- /.mBox -->
								</div><!-- /.col-xs-3 -->
								
								<div class="col-xs-9 editBox">
									<form class="form-horizontal" role="form" name="materialForm" method="post" enctype="multipart/form-data" action ="saveMaterial">
									<input type="hidden" name="typeId" value="${material.contentType}">
									<input type="hidden" name="type" value="${type}">
									<input type="hidden" name="logo" value="${material.logo}">
									<input type="hidden" name="parentId" value="${material.parentId}">
									


									<input type="hidden" name="logoStatus" value="0">
									<input type="hidden" name="id" value="${material.id}" >
									<input type="hidden" name="tempDate" value="${material.createDate}" >
										<!-- 编辑区域-->
										<!-- tab信息 -->
										<ul id="myTab" class="nav nav-tabs">
										   <li class="active"><a href="#edit1" data-toggle="tab" onclick="checkTypeId('0')">显示微网站页面</a></li>
										   <li><a href="#edit2" data-toggle="tab" onclick="checkTypeId('1')">显示正文</a></li>
										   <li><a href="#edit3" data-toggle="tab" onclick="checkTypeId('2')">显示指定网址</a></li>
										</ul>
										<div id="myTabContent" class="tab-content">
											
										   <div class="tab-pane fade in active" id="edit1">
										   		<div class="button-group">
													<button class="btn btn-primary"  data-toggle="modal" data-target="#mymodal-data" >选择内容页</button>
													<button class="btn btn-info"  data-toggle="modal" data-target="#mymodal-data1" >选择栏目页</button>
												</div>
										   		<div class="space-4"> </div>
													
												<div class="col-xs-12">
													<label class="control-label no-padding-right">标题</label>
													<div class="">
														<div class="inputLine">
															<div class="input-group" style="width: 100%;">
																<input class="input-medium"  name="title0" value ="${material.title}" style="width: 100%;" type="text" placeholder="请输入标题" />
															</div>
														</div>
													</div>
												</div>
												
												<div class="space-4"> </div>	
												<div class="col-xs-12" style="height:auto;">
													<label class="control-label no-padding-right" for="form-field-date">封面<small>&nbsp;&nbsp;(大图片建议尺寸：360像素 * 200像素，小图片建议尺寸：200像素 * 200像素)</small></label>
													<div class="col-xs-12 no-padding inputLine" style="border:1px solid #ccc;">
														<div style="position: relative;display: inline-block;margin:15px;" onmouseover="$(this).find('input').css('display','');" onmouseout="$(this).find('input').css('display','none');">
															<input type="button" class="btn btn-danger" value=&times; title='删除' style="min-width:0px;display:none;position: absolute;right:-10px;top:-10px;border:none;border-radius: 40px;z-index:100;" onclick="cleraImg('1')"/>
															<div id ="preview1">
															
															<c:if test="${material.logo == null}">
																<img src="<%=request.getContextPath() %>/images/picture.png" class="big" style="width:80px;height:80px;"/>
															</c:if>
															<c:if test="${material.logo != null}">
																<img src="<%=request.getContextPath() %>/wechatImages/${material.logo}" class="big" style="width:80px;height:80px;"/>
															</c:if>
															
															
															</div>
														
														</div>
														<div style="display: inline;position: relative;">
															<button class="btn btn-info btn-sm">上传图片</button>
															<input type="file"  name = "file1" id="myfile" onchange="previewImage(this,'1');" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
														</div>
													</div>
												</div>
												
												<div class="space-4"> </div>	
												<div class="col-xs-12" style="height:auto;">
													<label class="control-label no-padding-right" for="form-field-date">摘要</label>
													<div class="">
														<div class="inputLine">
																<textarea name="summary0" style="width:100%;">${material.summary}</textarea>
															
														</div>
													</div>
												</div>
												
												<div style="clear: both;"></div>
										   	
										   </div><!-- /#edit2 -->
										   
										   <div class="tab-pane fade" id="edit2">
										   		<div class="space-4"> </div>
													
												<div class="col-xs-12">
													<label class="control-label no-padding-right">标题</label>
													<div class="">
														<div class="inputLine">
															<div class="input-group" style="width: 100%;">
																<input class="input-medium" name="title1" value ="${material.title}"style="width: 100%;" type="text" placeholder="请输入标题" />
															</div>
														</div>
													</div>
												</div>
												
												<div class="space-4"> </div>	
												<div class="col-xs-12" style="height:auto;">
													<label class="control-label no-padding-right" for="form-field-date">封面<small>&nbsp;&nbsp;(大图片建议尺寸：360像素 * 200像素，小图片建议尺寸：200像素 * 200像素)</small></label>
													<div class="col-xs-12 no-padding inputLine" style="border:1px solid #ccc;">
														<div class="col-xs-12 no-padding inputLine" style="border:1px solid #ccc;">
														<div style="position: relative;display: inline-block;margin:15px;" onmouseover="$(this).find('input').css('display','');" onmouseout="$(this).find('input').css('display','none');">
															<input type="button" class="btn btn-danger" value=&times; title='删除' style="min-width:0px;display:none;position: absolute;right:-10px;top:-10px;border:none;border-radius: 40px;z-index:100;" onclick="cleraImg('2')"/>
															<div id ="preview2">
															<c:if test="${material.logo == null}">
																<img src="<%=request.getContextPath() %>/images/picture.png" class="big" style="width:80px;height:80px;"/>
															</c:if>
															<c:if test="${material.logo != null}">
																<img src="<%=request.getContextPath() %>/wechatImages/${material.logo}" class="big" style="width:80px;height:80px;"/>
															</c:if>
															</div>
														
														</div>
														<div style="display: inline;position: relative;">
															<button class="btn btn-info btn-sm">上传图片</button>
															<input type="file"  name = "file2" id="myfile" onchange="previewImage(this,'2');" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
														</div>
													</div>
													</div>
												</div>
												
												<div class="space-4"> </div>	
												<div class="col-xs-12" style="height:auto;">
													<label class="control-label no-padding-right" for="form-field-date">摘要</label>
													<div class="">
														<div class="inputLine">
																<textarea name="summary1"style="width:100%;">${material.summary}</textarea>
															
														</div>
													</div>
												</div>
												
												<div class="space-4"> </div>
													
												<div class="col-xs-12">
													<label class="control-label no-padding-right">正文</label>
													<div class="">
														<div class="inputLine">
															<div>
																<!-- PAGE CONTENT BEGINS -->
																
																
								
																
																<textarea id = "uContent" name ="content" style="width:960px;height:350px;" >${account.content}</textarea>
										
								
																<!-- PAGE CONTENT ENDS -->
															
															</div><!-- /.col -->
														</div>
													</div>
												</div> 
												<div style="clear: both;"></div>
										   </div><!-- /#edit3 -->
										   
										   <div class="tab-pane fade" id="edit3">
										      <div class="space-4"> </div>
													
												<div class="col-xs-12">
													<label class="control-label no-padding-right">标题</label>
													<div class="">
														<div class="inputLine">
															<div class="input-group" style="width: 100%;">
																<input class="input-medium" name = "title2" value="${material.title}" style="width: 100%;" type="text" placeholder="请输入标题" />
															</div>
														</div>
													</div>
												</div>
												
												<div class="space-4"> </div>	
												<div class="col-xs-12" style="height:auto;">
													<label class="control-label no-padding-right" for="form-field-date">封面<small>&nbsp;&nbsp;(大图片建议尺寸：360像素 * 200像素，小图片建议尺寸：200像素 * 200像素)</small></label>
													<div class="col-xs-12 no-padding inputLine" style="border:1px solid #ccc;">
														<div style="position: relative;display: inline-block;margin:15px;" onmouseover="$(this).find('input').css('display','');" onmouseout="$(this).find('input').css('display','none');">
															<input type="button" class="btn btn-danger" value=&times; title='删除' style="min-width:0px;display:none;position: absolute;right:-10px;top:-10px;border:none;border-radius: 40px;z-index:100;" onclick="cleraImg('3')"/>
															<div id ="preview3">
															<c:if test="${material.logo == null}">
																<img src="<%=request.getContextPath() %>/images/picture.png" class="big" style="width:80px;height:80px;"/>
															</c:if>
															<c:if test="${material.logo != null}">
																<img src="<%=request.getContextPath() %>/wechatImages/${material.logo}" class="big" style="width:80px;height:80px;"/>
															</c:if>
															</div>
														
														</div>
														<div style="display: inline;position: relative;">
															<button class="btn btn-info btn-sm">上传图片</button>
															<input type="file"  name = "file3" id="myfile" onchange="previewImage(this,'3');" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
														</div>
													</div>
												</div>
												
												<div class="space-4"> </div>	
												<div class="col-xs-12" style="height:auto;">
													<label class="control-label no-padding-right" for="form-field-date">摘要</label>
													<div class="">
														<div class="inputLine">
																<textarea name="summary2" style="width:100%;">${material.summary}</textarea>
															
														</div>
													</div>
												</div>
												
												<div class="space-4"> </div>
													
												<div class="col-xs-12">
													<label class="control-label no-padding-right">链接到网址</label>
													<div class="">
														<div class="inputLine">
															<div class="input-group" style="width:100%;">
																<input class="input-medium" name="url" value="${material.url}" style="width:100%;" type="text" placeholder="请输入网址" />
															</div>
														</div>
													</div>
												</div> 
												<div style="clear: both;"></div>
										   </div><!-- /#edit3 -->
										</div>
										 
										<div class="backBtn" id="showDiv" style="margin-top:20px;"  title="当按钮不可用时,请选择内容或者栏目">
											<button class="btn btn-info" id="save" >保存</button>
										</div>
									</form>
								</div>
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
						
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			
			<!-- 模态弹出窗内容 -->
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	
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
											
											<th>所属栏目</th>
											<th width="80%">标题</th>
										</tr>
									</thead>

									<tbody>
									
										<c:forEach items="${resultPage.items}" var="material">
										<tr>
											<td class="center">
													<label>
														<input type="radio" name="articleId" onclick="checkArticle('${material.id}')" value="${material.id}" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
											
											<td>
											<c:if test="${material.parentId == 1}">微官网</c:if>
											<c:if test="${material.parentId == 2}">最新动态</c:if>
											<c:if test="${material.parentId == 3}">产品介绍</c:if>
											<c:if test="${material.parentId == 4}">业务分类</c:if>
											<c:if test="${material.parentId == 5}">服务流程</c:if>
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
							<button type="button"  onclick="setUrl()" class="btn btn-primary">保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			
			</div><!-- /.modal -->
			
			<!-- 模态弹出窗内容 2-->
			<div class="modal  fade" id="mymodal-data1" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">请选择栏目</h4>
						</div>
						<div class="modal-body">
							
							<div class="table-responsive" style="max-height: 350px;overflow: auto;">
								<table  class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th class="center">
												<label>
													<input type="radio" name="checkId" value="1" class="ace" />
													<span class="lbl"></span>
												</label>
											</th>
											<th>微官网</th>
										</tr>
									</thead>

									<tbody>
										<tr>
											<td class="center">
													<label>
														<input type="radio" name="checkId" value="2" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
											<td>最新动态</td>
										</tr>
										<tr>
											<td class="center">
													<label>
														<input type="radio" name="checkId" value="3" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
											<td>产品介绍</td>
										</tr>
										<tr>
											<td class="center">
													<label>
														<input type="radio" name="checkId" value="4" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
											<td>业务分类</td>
										</tr>
										<tr>
											<td class="center">
													<label>
														<input type="radio" name="checkId" value="5" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
											<td>服务流程</td>
										</tr>
									
									</tbody>
								</table>
								
							</div><!-- /.table-responsive -->
						</div><!-- /.modal-body -->
						<div class="modal-footer">
							<button type="button" class="btn btn-primary">保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div><!-- /.modal-content -->
				</div><!-- /.modal-dialog -->
			
			</div><!-- /.modal -->
			

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

