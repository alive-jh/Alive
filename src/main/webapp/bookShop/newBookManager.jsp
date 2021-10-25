<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>

<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/validator/local/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/jquery.base64.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/jquery.spinner.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/clipboard.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">


<script>

var newcontent = '';
var jsonData;
var currentBookName;
var currentCateId;
//更新总记录
function updateTotalCount(count){
	//更新总记录
	var total=$("font#fPageTotal").text();
	total=eval(total)+count;
	$("font#fPageTotal").text(total);
}

//更新界面的方法
function updateUI(){
	newcontent='';
	$("#bookListTab tr:not(:has(th))").remove();
	for(var i=0;i<jsonData.length;i++){
		newcontent = newcontent + '<tr>';	
		newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr><input type="checkbox" name="testCateId"  value="'+jsonData[i].cateID+'" /></nobr></td>';
		newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+jsonData[i].cateID+'</nobr></td>';
		newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+jsonData[i].name+'</nobr></td>';
		var author = jsonData[i].author;
		if(author.length >=10)
		{
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+jsonData[i].author.substring(0,10)+'...</nobr></td>';
		}
		else
		{
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+jsonData[i].author+'</nobr></td>';
		}
		
		if(jsonData[i].publish.length >=10)
		{
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+jsonData[i].publish.substring(0,10)+'...</nobr></td>';
		}
		else
		{
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+jsonData[i].publish+'</nobr></td>';
		}

		
		newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+jsonData[i].price+'</nobr></td>';
		
		newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+jsonData[i].series+'</nobr></td>';
		if(jsonData[i].count!= undefined)
		{
			newcontent = newcontent + '<td nowrap="nowrap" align="left" id="book_'+jsonData[i].cateID+'"><nobr><strong><font color="green">'+jsonData[i].count+'</font></strong></nobr></td>';
		}
		else
		{
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>0</nobr></td>';
		}
		
		if(jsonData[i].updateStatus ==0)
		{
			newcontent = newcontent + '<td><button class="btn btn-xs btn-info"  data-backdrop="static" data-keyboard="false" onclick="editBook('+i+')" data-toggle="modal" data-target="#mymodal-data">修改</button>';
			newcontent = newcontent + '<button class="btn btn-xs btn-success"  data-backdrop="static" data-keyboard="false" onclick="addmallspecifications('+i+')" data-toggle="modal" data-target="#mymodal-data1">库存管理</button>';
			newcontent = newcontent + '<button onclick="deleteCateGory('+i+')" class="btn btn-xs btn-danger">删除</button></td>';
		
		}
		//onclick="saveBookQRcode('+i+')"
		else 
		{
			newcontent = newcontent + '<td><button class="btn btn-xs btn-success"  onclick="showStock('+i+')">库存管理</button></td>';
		
		}
		
		
		newcontent = newcontent + '</tr>';
	}

	$("#bookListTab").append(newcontent);	
}


//初始化
$(document).ready( function () {
	
	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	updateUI();

});
function copyData(i){
	var codeInfo = currentBookStock[i].codeInfo;
	alert(codeInfo);
}

//查看库存
var currentBookStock;
function showStock(i){
	$("#bookStock").modal('show');
	currentCateId = jsonData[i].cateID;
	currentBookName = jsonData[i].name;
	shopId = jsonData[i].shopId;
	var formData = new FormData();
	formData.append("cateId",currentCateId);
	formData.append("shopId",shopId);
	$.ajax({
		type: "POST",
		data:"cateId="+ currentCateId,
		dataType: "json",
		url: "<%=request.getContextPath() %>/bookApi/getBookStockList", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
		   $("#bookStockTab tr:not(:has(th))").remove();
		   $("#bookStockTab").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		},
		complete:function(){
			
		},
		success: function(data){
			currentBookStock = data.data.items;
			updateBookStockUI();
		}
	});	
	
	
	
}
function updateBookStockUI(){
		var courseContent='';
		$("#bookStockTab tr:not(:has(th))").remove();
		$("#bookStockCountTemp").empty();
		$("#bookNameTemp").empty();
		$("#bookStockCountTemp").append(currentBookStock.length);
		$("#bookNameTemp").append(currentBookName);
		for(var i=0;i<currentBookStock.length;i++){
				courseContent = courseContent + '<tr>';	
				courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+currentBookStock[i].barCode+'</td>';
				courseContent = courseContent + '<td nowrap="nowrap" align="left" id="'+currentBookStock[i].barcode+'">'+currentBookStock[i].codeInfo+'</td>';
				if (currentBookStock[i].isexist == 1){
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >在库</td>';
				}else{
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >借出</td>';
				}
				if(currentBookStock[i].copy_times == undefined){
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >0</td>';
				}else{
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+currentBookStock[i].copy_times+'</td>';
				}
				
				
				if(currentBookStock[i].status == 0){
					courseContent = courseContent + '<td nowrap="nowrap" align="left" ><select id = "bookStockStatus_'+i+'" onchange="changeBookStockStatus('+i+')"><option value="0" selected = "selected">正常</option><option value="1">废弃</option ><option value="-1">书籍损坏</option></select></td>';
				}else if(currentBookStock[i].status == 1){
					courseContent = courseContent + '<td nowrap="nowrap" align="left" ><select id = "bookStockStatus_'+i+'" onchange="changeBookStockStatus('+i+')"><option value="0">正常</option><option value="1" selected = "selected">废弃</option ><option value="-1">书籍损坏</option></select></td>';
				
				}else if(currentBookStock[i].status == -1){
					courseContent = courseContent + '<td nowrap="nowrap" align="left" ><select id = "bookStockStatus_'+i+'" onchange="changeBookStockStatus('+i+')"><option value="0">正常</option><option value="1">废弃</option ><option value="-1" selected = "selected">书籍损坏</option></select></td>';
				}else{
				
					courseContent = courseContent + '<td nowrap="nowrap" align="left" ><select id = "bookStockStatus_'+i+'" onchange="changeBookStockStatus('+i+')"><option value="0" selected = "selected">正常</option><option value="1">废弃</option ><option value="-1">书籍损坏</option></select></td>';
				}
				courseContent = courseContent + '<td nowrap="nowrap" align="left" ><button id="copy-barcode" class="btn btn-xs btn-success" data-clipboard-text="'+currentBookStock[i].codeInfo+'"  onClick="addCopyTimes('+i+')" value="点击复制">点击复制</button><br><br>';
				courseContent = courseContent + '&nbsp;&nbsp;</td>';
				courseContent = courseContent + '</tr>';
		}
		$("#bookStockTab").append(courseContent);
		$("#book_"+currentCateId).empty();
		var tempContent = '<nobr><strong><font color="green">'+currentBookStock.length+'</font></strong></nobr>';
		$("#book_"+currentCateId).append(tempContent);

}

function changeBookStockStatus(index){
	var barCode = currentBookStock[index].barCode;
	var status = $("#bookStockStatus_" + index).val();
	$.ajax({
			type: "POST",
			data:"barCode="+barCode + "&status=" + status,
			dataType: "json",
			url: "<%=request.getContextPath() %>/bookApi/updateBookStockStatus", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				var data = data.data;
				currentBookStock[index].status = data.status;
				updateBookStockUI();
			}
		});
}




function addCopyTimes(index){
	var barCode = currentBookStock[index].barCode;
	var codeInfo = currentBookStock[index].codeInfo;
	clipboard.copy(codeInfo);
	$.ajax({
			type: "POST",
			data:"barCode="+barCode + "&copyTimes=1",
			dataType: "json",
			url: "<%=request.getContextPath() %>/bookApi/updateBookStockStatus", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				var data = data.data;
				currentBookStock[index].copy_times = data.copyTimes;
				updateBookStockUI();
			}
		});
}



//新增库存
function addBookStock() {
	$("#addbookStock").modal("show");
	$("#addBookStockCount").val("");
}
//删除库存
function deleteBookStock(index){
	$.ajax({
			type: "POST",
			data:"id="+answerData[index].id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/deleteAnswerById", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
	              answerData.remove(index);
				  updateAnswerUI();
			}
		});	
}
//添加书籍到图书馆
function addBookLibrary(){
	$("#addBookDialog").modal("show");
	$("#bookName").val("");
	currentBookStock=null;
	searchResultData = null;

	$("#fandouBookList tbody").html("");
}
</script>


<div class="main-content">
	<div id="myJson" style="display: none;">${jsonData}</div>
	<div class="breadcrumbs" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active">书院管理</li>
			<li class="active">书籍管理</li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/bookShop/bookManager"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>书籍名称</td>
						<td><input type="text" name="searchStr"
							value="${queryDto.searchStr}"></td>
							<!-- 
						<td>
							<div class="form-group col-lg-6" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right">创建日期</label>
								<div class="col-sm-9">
									<div class="input-medium" style="">
										<div class="input-group">
											<input class="input-medium date-picker" type="text"
												name="startDate" value="${queryDto.startDate}"
												data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
												class="input-group-addon"> <i class="icon-calendar"></i>
											</span> <span class="input-group-addon"
												style="border:none;background-color:transparent;"> <i>--</i>
											</span> <input class="input-medium date-picker" type="text"
												name="endDate" value="${queryDto.endDate}"
												data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
												class="input-group-addon"> <i class="icon-calendar"></i>
										</div>
									</div>
								</div>
							</div>
						</td>
						 -->
					</tr>

				</table>

				<div class="btn-group col-md-12">
					<button class="btn  btn-purple" type="button"  onclick="addBookLibrary()"><span class="icon-plus"></span>添加图书</button>
					<button type="submit" class="btn btn-purple ml10 mb10">
						查询 <i class="icon-search icon-on-right bigger-110"></i>
					</button>
					</div>

			</form>
		</div>


		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="row">
					<div class="col-xs-12">
						<div class="table-responsive" style="text-align:right;">
							<table id="bookListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th><input type="checkbox" value=""  /></th>
										<th>编号</th>
										<th>名称</th>
										<th>作者</th>
										<th>出版社</th>
										<th>价格</th>
										<th>系列</th>
										<th>库存</th>
										<th>操作</th>
									</tr>
								</thead>
							</table>

							<f:page page="${resultPage}" url="./bookManager"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form class="data" method="post" name="modifyData" id="modifyData"
	enctype="multipart/form-data" action="">
	<input type="hidden" name="index" id="index" value=""> 
	<input type="hidden" name="id" id="id" value=""> 
	<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:900px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">编辑问题</h4>
				</div>
				<div class="modal-body">
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">问题描述</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="question" id="question"
										type="text" data-rule="问题描述:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>


				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="updateData()">保存</button>

					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>



<!-- 存库查看弹窗 -->
<div class="modal fade" id="bookStock" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:800px;height: auto;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">库存列表</h4>
				<div><h6>书名：<span id="bookNameTemp"></span></h6>&nbsp;&nbsp;&nbsp;&nbsp;<h6>当前库存：<span id="bookStockCountTemp"></span></h6></div>
				<button class="btn btn-purple ml10 mb10" onclick="addBookStock()" data-toggle="modal" value="添加">
				新增库存</button>
			</div>
			<div class="modal-body">
				
				<table id="bookStockTab"
							class="table table-striped table-bordered table-hover"
							style="text-align:left;">
							<thead>
								<tr>
									<th width="100">编码</th>
									<th width="100">二维码字符串</th>
									<th width="120">在库状态</th>
									<th width="120">复制次数</th>
									<th width="120">书籍状态</th>
									<th width="120">操作</th>
								</tr>
							</thead>

				</table>
			
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
function saveAddBookStock(){
	$("#addbookStock").modal("hide");
	var addBookStockCount = $("#addBookStockCount").val();
	var tempCateId = currentCateId;
	var kucunCount = addBookStockCount
	 var formData = new FormData();
 	 formData.append("tempCateId",tempCateId);
 	 formData.append("kucunCount",kucunCount);
     $.ajax({
          url: '/wechat/bookApi/saveBook' ,
          type: 'POST',
          data:formData,
          dataType: "json",  
          async: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (returndata) {
			  //更新界面
			  var data = returndata.data;
			  for(var i=0;i<data.length;i++){
			  	var temp = data[i];
			  	temp["copy_time"] = 0;
			  	currentBookStock.splice(i, 0,temp);	
			  
			  }
			  updateBookStockUI();
			  
          },
          error: function (returndata) {
          
          }
     });

	
}
</script>
<!-- 新增库存弹窗 -->
<div class="modal fade" id="addbookStock" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:600px;height: auto;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">新增库存</h4>
			</div>
			<div class="modal-body">
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">新增库存数量</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="addBookStockCount" id="addBookStockCount"
									type="text" data-rule="填写数量:required;"
									style="max-width:250px;width:50%;" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" onclick="saveAddBookStock()">保存</button>
			</div>
		</div>
	</div>
</div>

<style>
.add{
	font-size:30px;

}
.jian{

	font-size:30px;

}
h6{
	display : inline;
}
</style>
<script type="text/javascript">
var searchResultData;
function searchBookByName(){
	var bookName = $("#bookName").val();
	$.ajax({
		type: "POST",
		data:"bookName="+bookName,
		dataType: "json",
		url: "<%=request.getContextPath() %>/bookApi/searchBookByName", 
		context: document.body, 
		beforeSend:function(){
			$("#fandouBookList tr:not(:has(th))").remove();
		   $("#fandouBookList").append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		},
		complete:function(){
			
		},
		success: function(data){
			var bookList = data.data.items;
			searchResultData = bookList;
			var courseContent='';
			$("#fandouBookList tr:not(:has(th))").remove();
			if(bookList.length == 0){
				alert("暂无搜索结果，请联系运营人员！");
			}
			for(var i=0;i<bookList.length;i++){
					courseContent = courseContent + '<tr>';	
					courseContent = courseContent + '<th width="20"><input type="checkbox" name="bookInfo" value="'+bookList[i].cateID+'" onclick=""></th>';
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+bookList[i].cateID+'</td>';
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >&nbsp;&nbsp;&nbsp;&nbsp;<span onclick="jian('+i+')">-</span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="' + bookList[i].cateID +'" style="width: 40%;" value=1 />&nbsp;&nbsp;&nbsp;&nbsp;<span onclick="add('+ i +')">+</span></p></td>';
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+bookList[i].name+'</td>';
					var cover = bookList[i].cover;
					if(cover.indexOf("http") >= 0){
						courseContent = courseContent + '<td nowrap="nowrap" align="left"><img width="50" height="50" src="'+bookList[i].cover+'"></td>';
					}else{
						courseContent = courseContent + '<td nowrap="nowrap" align="left"><img width="50" height="50" src="https://wechat.fandoutech.com.cn/wechat/wechatImages/book/'+bookList[i].cover+'"></td>';
					}
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+bookList[i].translator+'</td>';
					courseContent = courseContent + '<td nowrap="nowrap" align="left" >'+bookList[i].series+'</td>';
					
					courseContent = courseContent + '</tr>';
			}
			$("#fandouBookList").append(courseContent);
		}
	});	

}

function add(index){
	var cateId = searchResultData[index].cateID;
	var kucun = $("#"+cateId).val();
	var nextKucun = parseInt(kucun) + 1;
	$("#"+cateId).val(nextKucun);
}
function jian(index){
	var cateId = searchResultData[index].cateID;
	var kucun = $("#"+cateId).val();
	if(parseInt(kucun) > 0){
		var nexKucun = parseInt(kucun) - 1;
		$("#"+cateId).val(nexKucun);
	}else{
		$("#"+cateId).val(0);
	}
	
	
}

function saveBookToLibrary(){
	$("#addBookDialog").modal("hide");
    var cateIDS = $("input[name='bookInfo']:checked").val([]);
    var  cates= "";
    for(var i=0;i<cateIDS.length;i++){
    	var cateID = cateIDS[i].value;
    	var kucun = $("#"+cateID).val();
 		cates = cates + cateIDS[i].value + ":"+ kucun + ",";
 	}
 	var formData = new FormData();
 	formData.append("cates",cates);
	$.ajax({
		type: "POST",
		data:"cates="+cates,
		dataType: "json",
		url: "<%=request.getContextPath() %>/bookApi/saveBookToLibrary", 
		context: document.body, 
		beforeSend:function(){
		},
		complete:function(){
			
		},
		success: function(data){
			var bookList = data.data;
			for(var i=0;i<bookList.length;i++){
				var iscunzai = 0;
				for(var j=0;j<jsonData.length;j++){
					if(bookList[i].cateID == jsonData[j].cateID){
						iscunzai = j+1;
					}
				}
				if(iscunzai != 0){
					jsonData.remove(iscunzai-1);
					jsonData.splice(i, 0, bookList[i]);
					
				}else{
					jsonData.splice(i, 0, bookList[i]);
				}
				
			}
			updateUI();
			updateTotalCount(1);
			
		}
	});


}
</script>
<!-- 添加数据到图书馆弹窗 -->
<div class="modal fade" id="addBookDialog" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1200px;height: 1200px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title">添加图书到书库</h4>
			</div>
			<div class="modal-body">
				<div id="divInput" class="form-group" style="height:35px;">
					<label class="col-sm-2 control-label no-padding-right text-right">图书名字</label>
					<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group" style="width:500px;">
								<input class="form-control" name="bookName" id="bookName"
									type="text" 
									style="max-width:250px;width:50%;" />
								<button onclick="searchBookByName()">搜索</button>
							</div>
						</div>
					</div>
				</div>
				<div style="position:relative;margin:10px">			
					<table id="fandouBookList"
									class="table table-striped table-bordered table-hover"
									style="text-align:left;">
									<thead>
										<tr>
											<th width="20"><input type="checkbox" name="" value="" onclick=""></th>
											<th width="50">编号</th>
											<th width="100">新增库存数</th>
											<th width="100">名称</th>
											<th width="100">封面</th>
											<th width="100">出版社</th>
											<th width="100">系列名称</th>
										</tr>
									</thead>
	
						</table>
					</div>		
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" onclick="saveBookToLibrary()">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>