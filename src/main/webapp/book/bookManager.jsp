<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
document.forms['addaccount'].action = "bookManager";
document.forms['addaccount'].submit();
}
//本地条件分页查询
function pageJump(page) {
PPage("page_select", page, totalPageCount, "pageJump", true);
$("#currentPage").val(page);
}
var newcontent = '';

var catData=null;

var data = ${jsonStr};

$(document).ready( function () {


var message= "${message}";
var errorCount = "${errorCount}";
if(message != '')
{
	//alert(message+'<br/><a href="#">aaaaa</a>');	
	if(errorCount ==1)
	{
		message = message +'<a href="./showExcelMessage?excelNumber=${excelNumber}">查看详情</a>';
	}
	$('#message').html(message);
	$('#keyWBtn').modal('show');
}


	$.ajax({
					type: "POST",
					data:"",
					dataType: "json",
					url: "<%=request.getContextPath() %>/book/bookCategoryView", 
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


	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="cataLog"]', {
			allowFileManager : true
		});

	});

	
	$("#sType").val("${queryDto.type}");
	$("#sStatus").val("${queryDto.status}");

			if(data !='')
			{
				var accountListTab = $('#accountListTab');
				$("#accountListTab tr:not(:has(th))").remove();
				accountListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		
				for(var i=0;i<data.infoList.length;i++){

					
					newcontent = newcontent + '<tr>';
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].cateID+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].name+'</nobr></td>';
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].author+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].publish+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].price+'</nobr></td>';
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].series+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].leftName+'-'+data.infoList[i].rightName+'</nobr></td>';
					if(data.infoList[i].count!= undefined)
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].count+'</nobr></td>';
					}
					else
					{
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>0</nobr></td>';
					}
					
					newcontent = newcontent + '<td><button class="btn btn-xs btn-info"  onclick="editBook('+i+')" data-toggle="modal" data-target="#mymodal-data">修改</button><button class="btn btn-xs btn-success"  onclick="addmallspecifications('+i+')" data-toggle="modal" data-target="#mymodal-data1">库存</button><button onclick="deleteCateGory('+i+')" class="btn btn-xs btn-danger">删除</button></td>';
					
					
					newcontent = newcontent + '</tr>';
				}
	
				$("#accountListTab tr:not(:has(th))").remove();
				accountListTab.append(newcontent);
			}
				
			
			

});








function editBook(index)
{

				if(index.length == '0' ){
					categorySelect(false, index);
				}else{
					categorySelect(true, index);
				}
				
				if(index.length == '0' )
				{
					$('#uBname')[0].value = '';
					$('#uCount')[0].value = '';
					$('#uContent')[0].value = '';
					$('#uSeries')[0].value = '';
					$('#uPublish')[0].value = '';
					$('#uTranslator')[0].value = '';
					$('#uAuthor')[0].value = '';
					$('#uPage')[0].value = '';
					$('#uPrice')[0].value = '';
					$('#uCataLog')[0].value = '';
					$('#uTempCateId')[0].value = '';	
					document.forms['bookForm'].cateID.value = '';
					$('.tagSelected').find('.tag-info').not('.tag-modal').remove();
					$('.tagLists').find('.tag-info').not('.tag-modal').remove();
					document.forms['bookForm'].tempName.value = '';
					$("#testMp3").html('');
					$("#mp3Name").html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
					editor.text('');
					$("#uTempCateId").removeAttr("readonly"); 
					
				}
				else
				{
					
					$("#mp3Nam3").val(data.infoList[index].mp3);
					$("#uRight").val(data.infoList[index].rightId);
					$("#uLeft").val(data.infoList[index].leftId);
					$('#uBname')[0].value = data.infoList[index].name;
					if(data.infoList[index].count!= undefined)
					{
						$('#uCount')[0].value = data.infoList[index].count;
					}
					else
					{
						$('#uCount')[0].value = 0;
					}
					
					$('#uContent')[0].value = data.infoList[index].content;
					$('#uSeries')[0].value = data.infoList[index].series;
					$('#uPublish')[0].value = data.infoList[index].publish;
					$('#uTranslator')[0].value = data.infoList[index].translator;
					$('#uAuthor')[0].value = data.infoList[index].author;
					$('#uPage')[0].value = data.infoList[index].page;
					$('#uPrice')[0].value = data.infoList[index].price;
					//$('#uCataLog')[0].value = data.infoList[index].cataLog;
					$('#uMp3Type')[0].value = data.infoList[index].mp3Type;		
					$('#uCode')[0].value = data.infoList[index].code;		
					$('#uTempCateId')[0].value = data.infoList[index].cateID;
					$("#uTempCateId").attr("readonly","readonly"); 
					document.forms['bookForm'].cateID.value = data.infoList[index].cateID;
					document.forms['bookForm'].cover1.value = data.infoList[index].cover;
					document.forms['bookForm'].oldLogo1.value = data.infoList[index].cover;
					document.forms['bookForm'].oldMp3.value = data.infoList[index].mp3;
					document.forms['bookForm'].testMp3.value = data.infoList[index].testMp3;
					document.forms['bookForm'].mp3.value = data.infoList[index].mp3;
					
					
					
					$("input[name=status]:eq("+data.infoList[index].status+")").attr("checked",'checked');
					$("#testMp3").html('<a target="_blank" href="<%=request.getContextPath() %>/wechatImages/book/mp3/'+data.infoList[index].testMp3+'">'+data.infoList[index].testMp3+'</a>');
					$("#mp3Name").html('<a target="_blank" href="<%=request.getContextPath() %>/wechatImages/book/mp3/'+data.infoList[index].mp3+'">'+data.infoList[index].mp3+'</a>');
					
					if(data.infoList[index].cover != undefined &&data.infoList[index].cover !='')
					{
									
							document.getElementById('preview1').innerHTML = '<img width="80" height="80" src="<%=request.getContextPath() %>/wechatImages/book/'+data.infoList[index].cover+'"/>';
									
									
					}
							
					$('.tagSelected').find('.tag-info').not('.tag-modal').remove();
					if(data.infoList[index].label != '' && data.infoList[index].label != undefined)
					{
									
									document.forms['bookForm'].labelId.value = data.infoList[index].label;

									
									for(var i=0;i<data.infoList[index].label.split(',').length;i++)
									{
										

										$("input[name='items']:checkbox").each(function() {
			 
											
											 if(data.infoList[index].label.split(',')[i] == $(this).val())
											 {
												
												 this.checked = true;
												 addItems(data.infoList[index].label.split(',')[i]);
											 }
											   
										})
									}
					}
					$('.tagLists').find('.tag-info').not('.tag-modal').remove();
					if(data.infoList[index].keyword != '' && data.infoList[index].keyword != undefined)
					{
									
									

									
									for(var i=0;i<data.infoList[index].keyword.split(',').length;i++)
									{
										document.forms['bookForm'].tempName.value = document.forms['bookForm'].tempName.value+data.infoList[index].keyword.split(',')[i]+",";
												
												 addDidstrict(data.infoList[index].keyword.split(',')[i]);
										
									}
					}

					editor.text(editor.html(data.infoList[index].cataLog));


					
				}
}




function updateOrderStatus()
{


	if(document.forms['orderForm'].orderStatus.value == 1)
	{
		
		
		var chk_value =[];
		$('input[name="checkId"]:checked').each(function(){  
			chk_value.push($(this).val());  
		}); 
		if(chk_value.length==0)
		{
			alert("请至少选择一条记录!");
			return;
		}
		else
		{
			document.forms['orderForm'].orderId.value = chk_value;

		}
		


	}
	
	if(confirm('你确定要结算吗？'))
	{
		document.forms['orderForm'].action = "<%=request.getContextPath()%>/accountOrder/updateOrderType";
		document.forms['orderForm'].submit();
	}
	

}




function deleteCateGory(index)
{
	
	if(index.length != '0' )
	{
		if(confirm('你确定删除该信息吗?'))
		{
			document.forms['searchForm'].action = "<%=request.getContextPath()%>/book/removeCategory?cateId="+data.infoList[index].cateID+"&cover="+data.infoList[index].cover;
			document.forms['searchForm'].submit();
		}
	}
	
	

}







function searchOrderManager()
{
	document.forms['searchForm'].action = "<%=request.getContextPath()%>/book/bookManager";
	document.forms['searchForm'].submit();
}


function searchOrderInfoManager()
{
	document.forms['searchForm'].action = "<%=request.getContextPath()%>/accountOrder/qqOrderInfoManager";
	document.forms['searchForm'].submit();
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
			};
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
				};
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
		document.forms['bookForm'].cover1.value ='';
	};
}


function show(img)
{
	
	javascript:window.open(img);
}


function addmallspecifications(index)
{
	
	
	document.forms['bookForm'].cateID.value  = data.infoList[index].cateID;
	
	 $("#bookTable  tr:not(:first)").empty();
	 var $tr=$("#bookTable tr:last");
	 var tempRow = $("#bookTable").find("tr").length;
	 var trHtml = "";
	 
	
	 var tempBook = data.infoList[index].bookInfo.split(',');
	 var tempBarCode = "";
	 
	 if(data.infoList[index].bookInfo != '')
	 {
		
		for(var i=0;i<tempBook.length;i++)
		 {
			 
			var tempStr = tempBook[i].split('>');
			
			tempBarCode = tempStr[0].substring(1,tempStr[0].length);
			
			trHtml = '<tr id="tempRow'+tempRow+'">'

			trHtml = trHtml + '<td>'+tempStr[0]+'</td>';
			trHtml = trHtml + '<td>'+tempStr[2]+'</td>';
			if(tempStr[1] ==1)
			{
				trHtml = trHtml + '<td><font color="green">在库</font></td>';
			}
			if(tempStr[1] ==0)
			{
				trHtml = trHtml + '<td><font color="red">已借出</font></td>';
			}
			
			 if(tempStr[1] ==1)
			 {

				
				 trHtml = trHtml + '<td><button class="btn btn-xs btn-success" onclick="show(\''+tempStr[4]+'\')" > 查看二维码</button>&nbsp;<button onclick="delRows('+tempRow+',\''+ tempStr[0]+'\')" class="btn btn-xs btn-danger">删除</button></td></tr>';
			 }
			 else
			 {
					 trHtml = trHtml + '<td><a target="_blank" href="'+tempStr[4]+'"><button class="btn btn-xs btn-success" > 查看二维码</button></a>&nbsp;</td></tr>';
			
			 }
			 tempRow++;
			 $tr.after(trHtml);
		 }
     
	 }
	
    
     



}

function delRows(index,barcode)
{
	
	var tempCateId  =document.forms['bookForm'].cateID.value;

	//alert(document.forms['bookForm'].cateID.value+' ==='+barcode);
	
	$.ajax({
			type: "POST",
			data:"",
			dataType: "json",
			url: "<%=request.getContextPath()%>/book/removeBookByAjax?barcode="+barcode+"&cateId="+document.forms['bookForm'].cateID.value,
			context: document.body, 
			beforeSend:function(){
			},
			complete:function(){
			},
			success: function(data){
					$("#tempRow"+index).remove(); 
				
			}

		});
}


function showMp3(filename)
{
	

	if(filename =='file2')
	{
		$("#testMp3").html(document.getElementById(""+filename).value);
	}
	if(filename =='file3')
	{
		$("#mp3Name").html(document.getElementById(""+filename).value);
	}
	
					
}

function saveBook()
{
	
		$.ajax({
			type: "POST",
			data:"",
			dataType: "json",
			url: "<%=request.getContextPath()%>/book/saveBookByAjax?cateId="+document.forms['bookForm'].cateID.value,
			context: document.body, 
			beforeSend:function(){
			},
			complete:function(){
			},
			success: function(data){

			 var $tr=$("#bookTable tr:last");
			 var tempRow = $("#bookTable").find("tr").length;
			 var trHtml = '<tr id="tempRow'+tempRow+'">'
			 
			 trHtml = trHtml + '<td>'+data.infoList.barCode+'</td>';
			 trHtml = trHtml + '<td>'+data.infoList.belong+'</td>';
			 trHtml = trHtml + '<td>'+data.infoList.isexist+'</td>';
			 
			 
			
			 trHtml = trHtml + '<td><a target="_blank" href="'+data.infoList.url+'"><button class="btn btn-xs btn-success" > 查看二维码</button></a>&nbsp;<button onclick="delRows('+tempRow+','+data.infoList.barCode.substring(1,data.infoList.barCode.length)+')" class="btn btn-xs btn-danger">删除</button></td></tr>';
			 $tr.after(trHtml);
			}

		});
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
	
	document.forms['bookForm'].labelId.value = ids;
	
	$('#mymodal-data2').modal('hide');

}

jQuery(function($) {
	
		$('.tag').css('cursor','pointer');
		$('.tag .close').click(function(){
		
		var tempIds = document.forms['bookForm'].labelId.value;
		tempIds = tempIds.replace($(this).parent().find('span').html()+",","");
		tempIds = tempIds.replace($(this).parent().find('span').html(),"");
		document.forms['bookForm'].labelId.value = tempIds;
		$(this).parent().remove();
	});
});
		


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
	var tempStr = document.forms['bookForm'].tempName.value;
	var temp = tempStr.split(',');

	for(var i=0;i<temp.length;i++)
	{
		if(temp[i]!= '')
		{
			addDidstrict(temp[i]);
		}
	}
}


//添加分类
function categorySelect(flag,index){

	if(flag){
	    // alert(data.infoList[index].book_cate_id);
		//修改
		$("#catId option").remove();
		if(data.infoList[index].book_cate_id==0){
			$("#catId").append("<option value='0'  selected = 'selected' >无</option>");
		}else{
			$("#catId").append("<option value='0' >无</option>");
		}
		//alert(jsonData.infoList[index].catId);
		if(catData!= null)
		{
			for(var i=0;i<catData.infoList.length;i++){
				if(catData.infoList[i].cat_id==data.infoList[index].book_cate_id){
					$("#catId").append("<option value='"+catData.infoList[i].cat_id+"' selected='selected' >"+catData.infoList[i].mark+catData.infoList[i].cat_name+"</option>");
				}else{
					$("#catId").append("<option value='"+catData.infoList[i].cat_id+"'>"+catData.infoList[i].mark+catData.infoList[i].cat_name+"</option>");
				}
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


function autoMatch(){
	var uCode=$("#uCode").val();
	if(null!=uCode&&''!=uCode){
		 $.getJSON("<%=request.getContextPath()%>/book/spider",  
                {key: ''+uCode},    
                function(data){  
                    $("#uBname").val(data.data[0].uBname);
                    $("#uAuthor").val(data.data[0].uAuthor);
                    $("#uTranslator").val(data.data[0].uTranslator);
                    $("#uPublish").val(data.data[0].uPublish);
                    $("#uPage").val(data.data[0].uPage);
                    $("#uSeries").val(data.data[0].uSeries);
                    $("#uPrice").val(data.data[0].uPrice);
                    $("#uContent").val(data.data[0].uContent);
                    editor.text(editor.html(data.data[0].uCataLog));
                });    
           
	}else{
		alert("请填写国际标准书号ISBN");
	}
}





function upExcel()
{

	document.forms['bookForm'].action = "./upExcelBook";
	document.forms['bookForm'].submit();


}



function selectFile(fnUpload) {
	var filename = fnUpload.value; 
	var mime = filename.toLowerCase().substr(filename.lastIndexOf(".")); 
		if(mime!=".xls") 
		{ 
			alert("请选择Excel文件,暂不支持Excel2010!");
			return;
		}

}





</script>

<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="main.htm">首页</a>							</li>
							<li class="active">书院管理</li>
							<li class="active">书籍管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="user" method="post" name="searchForm">
								<table class="filterTable">
									
								
									<tr>
										<td>书籍名称</td>
										<td><input  name ="name" type="text" value="${queryDto.name}" ></td>
										
										<td>作者</td>
										<td>
											<input  name ="author" type="text" value="${queryDto.author}" >
										</td>
										<td>出版社</td>
										<td>
											<input  name ="publish" type="text" value="${queryDto.publish}" >
										</td>
										
									</tr>
									<tr>
										
										<td>语言分类</td>
										<td><select name ="status" id="sStatus"><option value="">全部</option>
											
											<c:forEach items="${leftList}" var="tempLeft">
												<option value="${tempLeft.id}">${tempLeft.name}</option>
											</c:forEach>
														
										</select></td>
										<td>年龄段</td>
										<td><select name ="type"  id ="sType" >
										<option value="">全部</option>
											<c:forEach items="${rightList}" var="tempRight">
												<option value="${tempRight.id}">${tempRight.name}</option>
											</c:forEach>
										</select></td>
										
										
									</tr>
								</table>
							   
								<div class="btn-group col-md-12">
										
										 
										 
										  <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editBook('')"><span class="icon-plus"></span>添加</button>
										  <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data2" onclick="editBook('')"><span class="icon-plus"></span>批量添加</button>
										  <button type="button" onclick="searchOrderManager()" class="btn btn-purple ml10 mb10">查询
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
														<th>编号</th>
														<th>名称</th>
														<th>作者</th>
														<th>出版社</th>
														<th>价格</th>
														<th>系列</th>
														<th>分类</th>
														<th>库存</th>
														<th>操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
					<f:page page="${resultPage}" url="./bookManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			<form class="article" method="post" name="bookForm" enctype="multipart/form-data" action ="<%=request.getContextPath()%>/book/saveCategory">
			 <input type="hidden" name="cateID" value="">
			 <input type="hidden" name="tempDate" >
			 <input type="hidden" name="cover1" >
			 <input type="hidden" name="oldLogo1" >
			 <input type="hidden" name="oldMp3" >
			 <input type="hidden" name="testMp3" >
			 <input type="hidden" name="mp3" >
			 <input type="hidden" name="labelId" >

			
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
				<div class="modal-dialog" style="width:1100px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改图书信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">封面:</label>
											<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:910px;">
													
														
														<div style="display: inline;position: relative;">
															<button class="btn btn-info btn-sm">上传图片</button>
																<input type="file"  name = "file1" id="file1" onchange="previewImage(this,'1');" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
														
														</div>
														

													</div>
											</div>
										</div>
							</div>
							
							<div  id="showType" class="form-group" style="height:35px;">
							<label class="col-sm-2 control-label no-padding-right text-right"><span id="name2"></span>书籍分类</label>
										<div class="col-sm-9">
										<div class="input-medium" >
											<div class="input-group" style="width:910px;">
												<select name="book_cate_id" id="catId">
													
												</select>
												</div>
										</div>
								</div>
							</div>
							
							<div class="form-group" id="picLogo"style="height:100px;">
										<label class="col-sm-2 control-label no-padding-right text-right"></label>
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
<!--
	<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">试听音频:</label>
										<div class="col-sm-9">
											<div style="display: inline;position: relative;">
															<button class="btn btn-info btn-sm">上传音频</button>
																<input type="file"  name = "file2" id="file2" onchange="showMp3('file2')" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
																&nbsp;&nbsp;&nbsp;
																<span id="testMp3"></span>
														
												</div>
										</div>
							</div>
							-->

<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right"> 音频文件:</label>
										<div class="col-sm-9">
											<div style="display: inline;position: relative;">
															<button class="btn btn-info btn-sm">上传音频</button>
																<input type="file"  name = "file3" id="file3" onchange="showMp3('file3')" style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;"/>
																&nbsp;&nbsp;&nbsp;
																<span id="mp3Name"></span>
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;音频类型:
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
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">条形码 </label>
										<div class="col-sm-9">
										
											<div class="input-medium" >
												<div class="input-group" style="width:710px;">
													<input class="form-control" name="code" id="uCode"  type="text" style="max-width:250px;width:100%;"  />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<button type="button" onclick="autoMatch();" class="btn btn-primary">自动匹配</button>
												</div>
											</div>
												
										</div>
							</div>
						<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">名称:</label>
										<div class="col-sm-9">
											<div class="input-medium" >
												<div class="input-group" style="width:710px;">
													<input class="form-control" name="bName" data-rule="required;" id="uBname"  type="text" style="max-width:250px;width:100%;"  />

													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作者&nbsp;&nbsp;&nbsp;&nbsp;:
													<input class="form-control" name="author" id="uAuthor" style="max-width:250px;width:100%;"  />
												</div>
											</div>
										</div>
							</div>
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">译者:</label>
										<div class="col-sm-9">
												<div class="input-group" style="width:710px;">
													<input class="form-control" name="translator" id="uTranslator"  type="text" data-rule="required;"style="max-width:250px;width:100%;"  />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出版社:
													<input class="form-control" name="publish" id="uPublish"  type="text" data-rule="required;"style="max-width:250px;width:100%;"  />
												</div>

										</div>
							</div>
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">系列:</label>
										<div class="col-sm-9">
												<div class="input-group" style="width:710px;">
													<input class="form-control" name="series" id="uSeries"  type="text" data-rule="required;"style="max-width:250px;width:100%;"  />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;页数&nbsp;&nbsp;&nbsp;&nbsp;:
													<input class="form-control" name="page" id="uPage"  type="text" data-rule="required;"style="max-width:250px;width:100%;"  />
												</div>

										</div>
							</div>
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">语言:</label>
										<div class="col-sm-9">
												<div class="input-group" style="width:710px;">
													<select id="uLeft" name="left">
													<option value="1">中文</option>
													<option value="2">英文</option>
													</select>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级别:
													<select id="uRight" name="rightId">
													<option value="1">0-3岁</option>
													<option value="2">3-6岁</option>
													<option value="3">6-12岁</option>
													<option value="4">初级</option>
													<option value="5">中级</option>
													<option value="6">高级</option>
													<option value="7">3-4岁</option>
													<option value="8">4-5岁</option>
													<option value="9">5-6岁</option>
													</select>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;图书编码:
													<input class="form-control" name="tempCateId"  id="uTempCateId"  type="text" style="max-width:250px;width:100%;"  />
												</div>

										</div>
							</div>
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-2 control-label no-padding-right text-right">价格:</label>
										<div class="col-sm-9">
												<div class="input-group" style="width:710px;">
													<input class="form-control" name="price" id="uPrice"  type="text" data-rule="required;"style="max-width:250px;width:100%;"  />
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库存&nbsp;&nbsp;&nbsp;&nbsp;:
													<input class="form-control" name="count" id="uCount"  type="text" data-rule="required;"style="max-width:250px;width:100%;"  />
												</div>

										</div>
							</div>

							<div class="form-group" style="height:50px;">
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
									


									<div class="form-group" style="height:50px;">
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


								<div class="form-group" style="height:150px;">
										<label class="col-sm-2 control-label no-padding-right text-right">目录</label>
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group">
													
													<textarea id = "uCataLog" name ="cataLog" style="width:650px;height:150px;" data-rule="目录:required;"></textarea>
										
												</div>
											</div>
										</div>
									</div>
									<br/>
									<div class="form-group" style="height:150px;">
										<label class="col-sm-2 control-label no-padding-right text-right">内容</label>
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group">
													
													<textarea id = "uContent" name ="content" style="width:650px;height:150px;" data-rule="内容:required;"></textarea>
										
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


 		
			
			 
			<div class="modal  fade" id="mymodal-data2" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
				<div class="modal-dialog" style="width:500px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">书籍导入</h4>
						</div>
						<div class="modal-body">
							
							
					<div  id="divAccount" class="form-group" style="height:35px;">
						
							<div class="col-sm-9">
								<div class="input-medium" >
									<div class="input-group" style="width:710px;">
										
									<input type="file" name ="excelFile" onchange="selectFile(this)" />
									<BR/>
									<a href="/wechat/images/excelbook.xls">excel2013导入模板</a>
									</div>
								</div>
							</div>
					</div>

								
							
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="upExcel()">导入</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>
						
					</div>
				</div>
			</div>




			<!-- 模态弹出窗内容 2-->
			<div class="modal  fade" id="mymodal-data1" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">库存管理</h4>
						</div>
						<div class="modal-body">
							
							<div class="table-responsive" style="max-height: 350px;overflow: auto;">
								<table id="bookTable" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											
											<th>编码</th>
											<th>店铺</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
			
								</table>
								
							</div><!-- /.table-responsive -->
						</div><!-- /.modal-body -->
						<div class="modal-footer">
							<button type="button" onclick="saveBook()"class="btn btn-primary">新增库存</button>
							<button type="button" onclick="location.reload() "class="btn btn-default" data-dismiss="modal">关闭</button>
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
</form>

			
			
		</div><!-- /.main-container -->
