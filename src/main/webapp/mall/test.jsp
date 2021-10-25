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


var jsonData = null;

var catData = null;

var editor;
$(document).ready( function () {

			
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			allowFileManager : true
		});

	});
	
	$.ajax({
					type: "POST",
					data:"",
					dataType: "json",
					url: "<%=request.getContextPath() %>/mall/mallCategoryView", 
					context: document.body, 
					beforeSend:function(){
						//这里是开始执行方法
					},
					complete:function(){
					
					},
					success: function(data){
						catData = data;
						
						
					}
			
	});	
		

	$.ajax({
		type: "POST",
		data:"page=${queryDto.page}&pageSize=${queryDto.pageSize}&startDate=${queryDto.startDate}&endDate=${queryDto.endDate}&name=${queryDto.name}&catIds=${queryDto.catIds}",
		dataType: "json",
		url: "<%=request.getContextPath() %>/mall/searchMallManagerView", 
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
				
				newcontent = newcontent + '<tr><td class="center"><input type="checkbox"  id="checkId" name="checkId" value ="'+data.infoList[i].id+'"/></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
				
				//3W>8>100,5W>12>150,3W>8>100,5W>12>150
				
				if(data.infoList[i].info != undefined)
				{
					var tempStr = data.infoList[i].info.split(',');
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
					for(var j=0;j<tempStr.length;j++)
					{
						var str = tempStr[j].split('>');
						newcontent = newcontent + str[0]+'<br/>'

					}
					newcontent = newcontent + '</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
					for(var j=0;j<tempStr.length;j++)
					{
						var str = tempStr[j].split('>');
						newcontent = newcontent + str[1]+'<br/>'
						

					}
					newcontent = newcontent + '</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
					for(var j=0;j<tempStr.length;j++)
					{
						var str = tempStr[j].split('>');
						newcontent = newcontent + str[2]+'<br/>'
						

					}
					newcontent = newcontent + '</td>';
					
				}
				else
				{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ></td>';
				}
				
				
				
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].createDate+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].catName+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editArticle('+i+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button><button onclick="deleteArticle('+data.infoList[i].id+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button></td>';

				newcontent = newcontent + '</td>';
				
	}
			
			articleListTab.append(newcontent);
			
			
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
			
        this.checked = false;  });
	}
}

function deleteArticle(id){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addarticle'].action ="deleteMall?mallId="+id;
		document.forms['addarticle'].submit();
	}
}

//添加分类
function categorySelect(flag,index){

	if(flag){
	
		//修改
		$("#catId option").remove();
		if(jsonData.infoList[index].catId==0){
			$("#catId").append("<option value='0'  selected = 'selected' >无</option>");
		}else{
			$("#catId").append("<option value='0' >无</option>");
		}
		//alert(jsonData.infoList[index].catId);
		for(var i=0;i<catData.infoList.length;i++){
			if(catData.infoList[i].cat_id==jsonData.infoList[index].catId){
				$("#catId").append("<option value='"+catData.infoList[i].cat_id+"' selected='selected' >"+catData.infoList[i].mark+catData.infoList[i].cat_name+"</option>");
			}else{
				$("#catId").append("<option value='"+catData.infoList[i].cat_id+"'>"+catData.infoList[i].mark+catData.infoList[i].cat_name+"</option>");
			}
			
		}	
		
	}else{
	
		//增加
		$("#catId option").remove();
		$("#catId").append("<option value='0' select='selected' >无</option>");
		for(var i=0;i<catData.infoList.length;i++){
			$("#catId").append("<option value='"+catData.infoList[i].cat_id+"'>"+catData.infoList[i].mark+catData.infoList[i].cat_name+"</option>");
		}	
	}
	
}






function editArticle(index,type){
	


				if(type ==0)
				{
					$("#showLabel").show();
					$("#showKeyword").hide();
					$("#showMp3").hide();
					$("#showType").hide();
					

					

				}
				if(type ==1)
				{
					$("#showLabel").show();
					$("#showKeyword").show();
					$("#showMp3").show();
					$("#showType").hide();
					$("#name1").html('音频');
					$("#name2").html('音频');
					$("#name3").html('音频');
					$("#name4").html('添加音频');
					$("#name5").html('添加音频');
					$("#name6").html('音频名称');
				}
				if(type ==2)
				{
					$("#showLabel").hide();
					$("#showKeyword").hide();
					$("#showMp3").show();
					$("#name1").html('课程');
					$("#name2").html('课程');
					$("#name3").html('课程');
					$("#name4").html('添加课程');
					$("#name5").html('添加音频');
					$("#name6").html('音频名称');
				}
				


				$('#mymodal-data3').modal('hide');
				if(index.length == '0' ){
					categorySelect(false, index);
				}else{
					categorySelect(true, index);
				}
			
				if(index.length == '0' )
				{
					
					$('#uName')[0].value = '';		
					document.forms['addarticle'].id.value ='';
				    document.getElementById('preview1').innerHTML ='<img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/>';
				    document.getElementById('preview2').innerHTML ='<img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/>';
				    document.getElementById('preview3').innerHTML ='<img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/>';
				    document.forms['addarticle'].logo1.value = '';
				    document.forms['addarticle'].logo2.value = '';
				    document.forms['addarticle'].logo3.value = '';
					document.forms['addarticle'].oldLogo1.value = '';
					document.forms['addarticle'].oldLogo2.value = '';
					document.forms['addarticle'].oldLogo3.value = '';
					document.forms['addarticle'].mp3.value = '';

					$('.tagSelected').find('.tag-info').not('.tag-modal').remove();
					editor.text('');
					
					$("#mallTab  tr:not(:first)").empty();
				}
				else
				{
					document.forms['addarticle'].tempName.value = '';
					var data = jsonData;
					document.forms['addarticle'].id.value = data.infoList[index].id;
					document.forms['addarticle'].tempDate.value = data.infoList[index].createDate;
					$('#uName')[0].value = data.infoList[index].name;
					
					document.forms['addarticle'].mp3.value = data.infoList[index].mp3;
					
					//$('#uMp3Type')[0].value = data.infoList[index].mp3Type;		

					$("input[name=status]:eq("+data.infoList[index].status+")").attr("checked",'checked');
					
					
					document.getElementById('preview1').innerHTML ='<img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/>';
					document.getElementById('preview2').innerHTML ='<img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/>';
					document.getElementById('preview3').innerHTML ='<img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/>';

					if(data.infoList[index].logo1 != undefined &&data.infoList[index].logo1 !='')
					{
						
						
						
						document.getElementById('preview1').innerHTML = '<img width="80" height="80" src="<%=request.getContextPath() %>/wechatImages/mall/'+data.infoList[index].logo1+'"/>';
						document.forms['addarticle'].logo1.value = data.infoList[index].logo1;
						document.forms['addarticle'].oldLogo1.value = data.infoList[index].logo1;
						
					}
					if(data.infoList[index].logo2 != undefined &&data.infoList[index].logo2 !='')
					{
						document.getElementById('preview2').innerHTML = '<img width="80" height="80"  src="<%=request.getContextPath() %>/wechatImages/mall/'+data.infoList[index].logo2+'"/>';
						document.forms['addarticle'].logo2.value = data.infoList[index].logo2;
						document.forms['addarticle'].oldLogo2.value = data.infoList[index].logo2;
					}
					
					
					if(data.infoList[index].logo3 != undefined &&data.infoList[index].logo3 !='')
					{
						document.getElementById('preview3').innerHTML = '<img width="80" height="80"  src="<%=request.getContextPath() %>/wechatImages/mall/'+data.infoList[index].logo3+'"/>';
						document.forms['addarticle'].logo3.value = data.infoList[index].logo3;
						document.forms['addarticle'].oldLogo3.value = data.infoList[index].logo3;
						
					}

					$("#mallTab  tr:not(:first)").empty();
					if(data.infoList[index].info != undefined)
					{
						var tempStr = data.infoList[index].info.split(',');
						var $tr=$("#mallTab tr:last");
						var tempRow = $("#mallTab").find("tr").length;
						for(var j=0;j<tempStr.length;j++)
						{
							
							 var str = tempStr[j].split('>');
							 var trHtml = '<tr id="tempRow'+tempRow+'">'
							 trHtml = trHtml + '<td><input type="text" name="names" required value="'+str[0]+'"></td>';
							 trHtml = trHtml + '<td><input type="text" name="prices" required value="'+str[1]+'"></td>';
							 trHtml = trHtml + '<td><input type="text" name="counts" required value="'+str[2]+'"></td>';
							 trHtml = trHtml + '<td><a href="javascript:delRows('+tempRow+')">删除</a></td></tr>';
							 $tr.after(trHtml);
							

						}
					}
					
					$("#mp3Name").html('<a target="_blank" href="<%=request.getContextPath() %>/wechatImages/book/mp3/'+data.infoList[index].mp3+'">'+data.infoList[index].mp3+'</a>');
		

					$('.tagSelected').find('.tag-info').not('.tag-modal').remove();
					if(data.infoList[index].labelInfo != '' && data.infoList[index].labelInfo != undefined)
					{
									
									document.forms['addarticle'].labelId.value = data.infoList[index].labelInfo;

									
									for(var i=0;i<data.infoList[index].labelInfo.split(',').length;i++)
									{
										

										$("input[name='items']:checkbox").each(function() {
			 
											if(data.infoList[0].labelInfo != undefined)
											{
												
												if(data.infoList[0].labelInfo.split(',')[i] == $(this).val())
												{
												
													 this.checked = true;
													addItems(data.infoList[index].labelInfo.split(',')[i]);
												 }
											}
											 
											   
										})
									}
					}
					
					$('.tagLists').find('.tag-info').not('.tag-modal').remove();
					
					
					if(data.infoList[index].keyword != '' && data.infoList[index].keyword != undefined)
					{
									
									

									
									for(var i=0;i<data.infoList[index].keyword.split(',').length;i++)
									{
										document.forms['addarticle'].tempName.value = document.forms['addarticle'].tempName.value+data.infoList[index].keyword.split(',')[i]+",";
												
										addDidstrict(data.infoList[index].keyword.split(',')[i]);
										
									}
					}


								editor.text(editor.html(data.infoList[index].content));
								
								
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
		document.forms['addarticle'].logo1.value ='';
	}
	if(id ==2)
	{
		document.forms['addarticle'].logo2.value ='';
	}
	if(id ==3)
	{
		document.forms['addarticle'].logo3.value ='';
	}
	
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


function showMp3(filename)
{
	

	
	$("#mp3Name").html(document.getElementById(""+filename).value)
	
	
					
}



jQuery(function($) {
	
		$('.tag').css('cursor','pointer');
		$('.tag .close').click(function(){
		
		var tempIds = document.forms['addarticle'].labelId.value;
		tempIds = tempIds.replace($(this).parent().find('span').html()+",","");
		tempIds = tempIds.replace($(this).parent().find('span').html(),"");
		document.forms['addarticle'].labelId.value = tempIds;
		$(this).parent().remove();
	});
});



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
	
	document.forms['addarticle'].labelId.value = ids;
	
	$('#mymodal-data2').modal('hide');

}





function addDidstrict(info)
{
	var _html = info;
					
	var newTag = $('.tag-modal').clone(true);
	$(newTag).find('span').html(_html);
	$(newTag).appendTo($('.tagLists')).css('display','').removeClass('tag-modal');


}



function addKeyword()
{
	$('.tagLists').find('.tag-info').not('.tag-modal').remove();
	var tempStr = document.forms['addarticle'].tempName.value;
	var temp = tempStr.split(',');

	for(var i=0;i<temp.length;i++)
	{
		if(temp[i]!= '')
		{
			addDidstrict(temp[i]);
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
							<li class="active">商城管理</li>
							<li class="active">商城列表</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post"  name="searchForm" action="<%=request.getContextPath()%>/mall/mallManager" style="float: left;width:100%">
								<table class="filterTable">
									
									<tr>
										<td>名称</td>
										<td><input type="text" name="name" value="${queryDto.name}"></td>
										
										<td colspan="4">
											
										<div class="form-group col-lg-6" style="height:35px;">
											<label class="col-sm-3 control-label no-padding-right">创建日期</label>
											<div class="col-sm-9">
												<div class="input-medium" style="">
													<div class="input-group">
														<input class="input-medium date-picker" type="text" name="startDate" value="${queryDto.startDate}" data-date-format="yyyy-mm-dd" placeholder="年-月-日" />
														<span class="input-group-addon">
															<i class="icon-calendar"></i>
														</span>
														<span class="input-group-addon" style="border:none;background-color:transparent;">
															<i>--</i>
														</span>
														<input class="input-medium date-picker" type="text" name="endDate" value="${queryDto.endDate}" data-date-format="yyyy-mm-dd" placeholder="年-月-日" />
														<span class="input-group-addon">
															<i class="icon-calendar"></i>
													</div>
												</div>
											</div>
										</div>
											
										   	
										</td>
									</tr>
									
								</table>
							   
								<div class="btn-group col-md-12">
										 <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data3" onclick="editArticle('')"><span class="icon-plus"></span>添加</button>
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
														<th width="100">名称</th>
														<th width="100">规格</th>
														<th width="100">价格(元)</th>
														<th width="100">库存</th>
														<th width="100">创建日期</th>
														<th width="100">商品类型</th>
														<th width="120">操作</th>
													</tr>
												</thead>
	
												
											</table>
											    
											<f:page page="${resultPage}" url="./mallManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="article" method="post" name="addarticle" enctype="multipart/form-data" action ="<%=request.getContextPath()%>/mall/saveMall">
			 <input type="hidden" name="id" value="">
			 <input type="hidden" name="tempDate" >
			 <input type="hidden" name="logo1" >
			 <input type="hidden" name="logo2" >
			 <input type="hidden" name="logo3" >
			 <input type="hidden" name="oldLogo1" >
			 <input type="hidden" name="oldLogo2" >
			 <input type="hidden" name="oldLogo3" >
			 <input type="hidden" name="mp3" >
             <input type="hidden" name="labelId" >
			 
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
				<div class="modal-dialog" style="width:1100px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改商品信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right"><span id="name1">商品</span>名称</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:710px;">
													<input class="form-control" name="name" id="uName"  type="text" data-rule="商品名称:required;"style="max-width:650px;width:100%;"  />
													</div>
											</div>
										</div>
							</div>
							
							<div  id="showType" class="form-group" style="height:35px;">
							<label class="col-sm-2 control-label no-padding-right text-right"><span id="name2">商品</span>分类</label>
										<div class="col-sm-9">
										<div class="input-medium" >
											<div class="input-group" style="width:910px;">
												<select name="catId" id="catId">
													
												</select>
												</div>
										</div>
								</div>
							</div>
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right"><span id="name3">商品LOGO</span></label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:910px;">
													
														&nbsp;&nbsp;&nbsp;&nbsp;<div style="display: inline;position: relative;">
															<button class="btn btn-info btn-sm">上传图片</button>
																<input type="file"  name = "file1" id="file1" onchange="previewImage(this,'1');" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
														
														</div>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<div style="display: inline;position: relative;">
															 <button class="btn btn-info btn-sm">上传图片</button>
															<input type="file"  name = "file2" id="file2"  style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
														</div>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<div style="display: inline;position: relative;">
															 <button class="btn btn-info btn-sm">上传图片</button>
															<input type="file"  name = "file3" id="file3" onchange="previewImage(this,'3');" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
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

														
														<div style="position: relative;display: inline-block;margin:15px;"onmouseover="$(this).find('input').css('display','');" onmouseout="$(this).find('input').css('display','none');">
															<input type="button" class="btn btn-danger" value=&times; title='删除' style="min-width:0px;display:none;position: absolute;right:-10px;top:-10px;border:none;border-radius: 40px;z-index:100;" onclick="cleraImg('2')"/>
															<div  id ="preview2"><img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/></div>
														</div>

															
														<div style="position: relative;display: inline-block;margin:15px;"onmouseover="$(this).find('input').css('display','');" onmouseout="$(this).find('input').css('display','none');">
															<input type="button" class="btn btn-danger" value=&times; title='删除' style="min-width:0px;display:none;position: absolute;right:-10px;top:-10px;border:none;border-radius: 40px;z-index:100;" onclick="cleraImg('3')"/>
															<div  id ="preview3"><img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/></div>
														</div>
												</div>
											</div>
										</div>
									</div>
								<div  id="showMp3" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">音频文件</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:910px;">
													
														<div style="display: inline;position: relative;">
															 <button class="btn btn-info btn-sm">上传音频</button>
															<input type="file"  name = "file4" id="file4" onchange="showMp3('file4');" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
														</div>
														&nbsp;&nbsp;&nbsp;<span id="mp3Name"></span>

													</div>
											</div>
										</div>
									</div>

							<!--
								<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">音频类型</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:910px;">
													
														
													<select name="mp3Type" id="uMp3Type">
														
														<option value="1">中文</option>
														<option value="2">英语</option>
														<option value="3">中+英</option>
														<option value="4">粤语</option>
														<option value="5">中+粤</option>
														<option value="8">中英</option>
														
														
														
														
													</select>

													</div>
											</div>
										</div>
									</div>
							-->

								<div class="form-group" style="height:200px;">
										<label class="col-sm-2 control-label no-padding-right text-right"><span id="name4">商品规格</span></label>
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group">
												<button class="btn btn-info btn-sm" onclick="addmallspecifications()"><span id="name5">添加规格</span></button>
												<table id="mallTab"style="width:650px;" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														<th width="100"><span id="name6">规格</span></th>
														<th width="100"><span id="name7">价格</span></th>
														<th width="100"><span id="name8">库存</span></th>
														<th width="120">操作</th>
													</tr>
												</thead>
												

											</table>


												</div>
											</div>
										</div>
									</div>


							<div id="showLabel" class="form-group" style="height:50px;">
										<label class="col-sm-2 control-label no-padding-right text-right">标签列表</label>
											<div class="col-sm-10">
												<div class="input-medium">
													<div class="input-group">
													<button class="btn btn-primary"  data-toggle="modal" data-target="#mymodal-data2" >选择标签</button>
													<input type="hidden" name="item" id="uItems" />
													</div>
												</div>

												<div class="tags tagSelected" style="border: none;width:auto;padding-left:0;width:650px;">
													<span class="tag-modal tag tag-info" style="display: none;">
														<span>模板</span>
														<button type="button" class="close">×</button>
													</span>

												</div>
												
											</div>
										</div>


									
							<div id="showKeyword"  class="form-group" style="height:50px;">
										<label class="col-sm-2 control-label no-padding-right text-right">关键词列表</label>
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group" style="max-width:250px;width:100%;">
												<input type="text" style="max-width:250px;width:100%;" name="tempName">
												 <button type="button" onclick="addKeyword()"placeholder="请输关键词,多个以,号分隔"  class="btn btn-purple ml10 mb10"><span class="icon-plus">添加 </button>
										<div class="tagLists tags" style="border: none;width:auto;padding-left:0;width:650px;"></div>

											</div>
										</div>
									</div>
								</div>
									<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">是否上架</label>
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

									<div class="form-group" style="height:335px;">
										<label class="col-sm-2 control-label no-padding-right text-right">商品描述</label>
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
			<div class="modal  fade" id="mymodal-data2" tabindex="-3" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">

				<div class="modal-dialog" style="width:800px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">选择标签</h4>
						</div>
						<div class="modal-body">
							
							<div class="table-responsive" style="max-height: 550px;overflow: auto;">
								<table  class="table table-striped table-bordered table-hover">
									<thead>

										<tr>
											
											<th style="width:30px;">标签列表</th>
											
											
										</tr>
									</thead>

									<tbody>
									
										
										<tr>
											<td>

										<c:forEach items="${labelList}" var="tempLabel">
										<input  id="items"name="items" value="${tempLabel.name}" type="checkbox"/>&nbsp;&nbsp;${tempLabel.name}&nbsp;&nbsp;&nbsp;
										</c:forEach>
											
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

		<!-- 模态弹出窗内容 -->
			<div class="modal  fade" id="mymodal-data3" tabindex="-4" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">

				<div class="modal-dialog" style="width:400px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">选择商品类型</h4>
						</div>
						
						<div class="modal-footer">
						 <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editArticle('',0)">商品</button>
							<button  type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editArticle('','1')"  class="btn btn-primary">音频</button>
							<button  type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editArticle('','2')"  class="btn btn-primary">课程</button>
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
							<h4 class="modal-title">选择标签</h4>
						</div>
						<div class="modal-body">
							
							<div class="table-responsive" style="max-height: 550px;overflow: auto;">
								<table  class="table table-striped table-bordered table-hover">
									<thead>

										<tr>
											
											<th style="width:30px;">标签列表</th>
											
											
										</tr>
									</thead>

									<tbody>
									
										
										<tr>
											<td>

										<c:forEach items="${labelList}" var="tempLabel">
										<input  id="items"name="items" value="${tempLabel.name}" type="checkbox"/>&nbsp;&nbsp;${tempLabel.name}&nbsp;&nbsp;&nbsp;
										</c:forEach>
											
											
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



			
		</div><!-- /.main-container -->