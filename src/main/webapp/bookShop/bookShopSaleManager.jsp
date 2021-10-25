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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">

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
document.forms['addUser'].action = "labelManager";
document.forms['addUser'].submit();
}
//本地条件分页查询
function pageJump(page) {
PPage("page_select", page, totalPageCount, "pageJump", true);
$("#currentPage").val(page);
}
var newcontent = '';

var data = ${jsonStr};

//初始化
$(document).ready( function () {
	
		if(data !='')
			{

				var userListTab = $('#userListTab');
				$("#userListTab tr:not(:has(th))").remove();

				newcontent = newcontent + '<tr>';	
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >合计:</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><font color="red">${saleInfo[0]}</font></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><font color="red">${saleInfo[1]}</font></td>';

				newcontent = newcontent + '</tr>';

				
				
				for(var i=0;i<data.infoList.length;i++)
				{
				
					newcontent = newcontent + '<tr>';	
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].count+'</td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].totalPrice+'</td>';

					newcontent = newcontent + '</tr>';
					
				
				}
				$("#userListTab tr:not(:has(th))").remove();
				userListTab.append(newcontent);
			}
			

});


//删除数据

function deleteData(index){
	if(confirm('你确定要删除数据吗？'))
	{
		//通过Ajax删除服务器上的数据，并更新界面
		var dataPar='id='+jsonData[index].id;
		$.ajax({
		type: "POST",
		data:dataPar,
		dataType: "json",
		url: "<%=request.getContextPath() %>/bookApi/deleteBookShop", 
		context: document.body, 
		beforeSend:function(){
			//这里是开始执行方法
			var dataListTab = $('#dataListTab'); 
			$("#dataListTab tr:not(:has(th))").remove();
			dataListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
														
		},
		complete:function(){
			
		},
		success: function(data){
			//数组更新
			jsonData.remove(index);
			//数据执行完成
			updateUI();
			updateTotalCount(-1);
	    }
	   });
	   
	}
}






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
		};
		var reader = new FileReader();
		reader.onload = function(evt) {
			img.src = evt.target.result;
		};
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


//预添加或者修改上传文件
function editData(index)
{
	
	if(index.length == '0'){
		$("#index").val("");
		$("#id").val("");
		$("#logo").val("");
		$("#preview1")[0].innerHTML ='<img src="<%=request.getContextPath() %>/images/picture.png" style="width:80px;height:80px;"/>';
		$("#name").val("");
		$("#account").val("");
		$("#password").val("");
		$("#upFile")[0].outerHTML=$("#upFile")[0].outerHTML;
		$("#email").val("");
		$("#memberCardPrice").val("");
		$("#telephone").val("");
		$("#contacts").val("");
		//$("#status").val("");
		
	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		$("#logo").val(myData.logo);
		$("#preview1")[0].innerHTML = '<img width="80" height="80" src="'+myData.logo+'"/>';
		$("#name").val(myData.name);
		$("#account").val(myData.account);
		$("#password").val("");
		$("#upFile")[0].outerHTML=$("#upFile")[0].outerHTML;
		$("#email").val(myData.email);
		$("#memberCardPrice").val(myData.memberCardPrice);
		$("#telephone").val(myData.telephone);
		$("#contacts").val(myData.contacts);
		$("#status option").remove();
		if(myData.status==1){
			$("#status").append("<option value='1' selected='selected' >开通</option>");
			$("#status").append("<option value='0' >关闭</option>");
		}else{
			$("#status").append("<option value='0' selected='selected' >关闭</option>");
			$("#status").append("<option value='1' >开通</option>");
		}
	}
}


//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/bookApi/saveBookShop' ,
          type: 'POST',
          data: formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		   var dataListTab = $('#dataListTab'); 
		   $("#dataListTab tr:not(:has(th))").remove();
		   dataListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		  },
          success: function (returndata) {
              var result=$.parseJSON(returndata);
              var index=$("#index").val();
			  if(index.length == '0'){
					//更新数组数据
					//添加到最后
					jsonData.push(result.data); 
					updateTotalCount(1);
				}else{
					//更新数组数据
					jsonData[index]=result.data;
			  }
			  //更新界面
			  updateUI();
          },
          error: function (returndata) {
          
          }
     });
}


</script>



<div class="main-content">
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed');
			} catch (e) {

			}
		</script>

		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active">书院店铺管理</li>
			<li class="active">业绩统计</li>
		</ul>
		<!-- .breadcrumb -->

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/bookShop/bookShopSaleManager"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>店铺名称</td>
						<td><input type="text" name="name" value="${queryDto.name}">
						</td>
						<td>
							<div class="form-group col-lg-6" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right">创建日期</label>
								<div class="col-sm-9">
									<div class="input-medium" style="">
										<div class="input-group">
											<input class="input-medium date-picker" type="text"
												name="startDate" value="${queryDto.startDate}"
												data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
												class="input-group-addon"> 
											</span> <span class="input-group-addon"
												style="border:none;background-color:transparent;"> <i>--</i>
											</span> <input class="input-medium date-picker" type="text"
												name="endDate" value="${queryDto.endDate}"
												data-date-format="yyyy-mm-dd" placeholder="年-月-日" /> <span
												class="input-group-addon"> 
										</div>
									</div>
								</div>
							</div></td>
					</tr>

				</table>

				<div class="btn-group col-md-12">
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
							<table id="userListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
									   
										<th width="100">店铺名称</th>
										<th width="100">会员数</th>
										<th width="100">金额</th>
								
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./bookShopSaleManager"
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
	<input type="hidden" name="logo" id="logo" value="">
	<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:900px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">添加/修改店铺信息</h4>
				</div>
				<div class="modal-body">
				
					<div id="divInput" class="form-group" style="height:135px;">
						<label class="col-sm-2 control-label no-padding-right text-right">店招</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<div style="display: inline;position: relative;">
										<button class="btn btn-info btn-sm">上传图片</button>
										<input type="file" name="upFile" id="upFile"
											onchange="previewImage(this,'1');"
											style="opacity:0;position: absolute;max-width: 80px;min-height:30px;top:0;bottom:0;left:0;right:0;" />
										<div
										style="position: relative;display: inline-block;margin:15px;"
										onmouseover="$(this).find('input').css('display','');"
										onmouseout="$(this).find('input').css('display','none');">
										<input type="button" class="btn btn-danger" value=&times;
											title='删除'
											style="min-width:0px;display:none;position: absolute;right:-10px;top:-10px;border:none;border-radius: 40px;z-index:100;"
											onclick="cleraImg('1')" />
										<div id="preview1">
											<img src="<%=request.getContextPath()%>/images/picture.png"
												style="width:80px;height:80px;" />
										</div>
									    </div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
				

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">店铺名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="name" id="name" type="text"
										data-rule="店铺名称:required;" style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>


					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">店主账号</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="account" id="account"
										type="text" data-rule="店主账号:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">密码</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="password" id="password"
										type="text" data-rule="密码:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">电子邮箱</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="email" id="email" type="text"
										data-rule="电子邮箱:required;" style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">会员卡价格</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="memberCardPrice"
										id="memberCardPrice" type="text" data-rule="会员卡价格:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">联系电话</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="telephone" id="telephone"
										type="text" data-rule="联系电话:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">联系地址</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="contacts" id="contacts"
										type="text" data-rule="联系地址:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>


					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">店铺状态</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<select name="status" id="status">
										<option value="1" selected="selected">开通</option>
										<option value="0">关闭</option>
									</select>
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
</form>