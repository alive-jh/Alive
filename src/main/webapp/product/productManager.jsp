<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		

$('#uProductInfoId')[0].value = "${queryDto.productInfoId}";

	$.ajax({
		type: "POST",
		data:"page=${queryDto.page}&pageSize=${queryDto.pageSize}&number=${queryDto.number}&productInfoId=${queryDto.productInfoId}&startDate=${queryDto.startDate}&endDate=${queryDto.endDate}",
		dataType: "json",
		url: "<%=request.getContextPath() %>/product/productManagerView", 
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
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].number+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].power+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].createDate+'</td>';
	
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ></td>';



				
			
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editUser('+data.infoList[i].id+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button>';

				
					newcontent = newcontent +'<button onclick="deleteProductInfo('+data.infoList[i].id+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button>';
				
				
				

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


function deleteUsers()
{
	 
	 var ids ='';
	 
	var obj=document.getElementsByName('checkId'); 
	for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) 
		{
			ids+=obj[i].value+','; 
			
		}
	} 

	if(ids=='')
	{
		alert('请至少选择一条记录!');
	}
	else
	{
		ids = ids.substring(0,ids.length-1);
		document.forms['addUser'].action ="deleteProduct?productId="+ids;
		document.forms['addUser'].submit();
	}

}

function deleteProductInfo(id){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addUser'].action ="deleteProduct?productId="+id;
		document.forms['addUser'].submit();
	}
}


function editUser(id){
	
				
				if(id ==''){
					$('#uNumber')[0].value='';
					$('#uProductInfoId')[0].value='';
					document.forms['addUser'].img = '';
					document.forms['addUser'].url = '';
					
					



					//$('#uType option')[0].selected=true;
					//$('#uStatus option')[0].selected=true;
			
					document.forms['addUser'].id.value ='';
				}
				else {
					  
					  
						$.ajax({
							type: "POST",
							data:"productId="+id,
							dataType: "json",
							url: "<%=request.getContextPath() %>/product/productManagerView", 
							context: document.body, 
							beforeSend:function(){											
							},
							complete:function(){
								
								
							},
							success: function(data){



								$('#uNumber')[0].value = data.infoList[0].number;
								$('#uProductInfoId')[0].value=data.infoList[0].productInfoId;
								$('#uTodate')[0].value = data.infoList[0].createDate;

								document.forms['addUser'].img.value = data.infoList[0].img;
							
								document.forms['addUser'].url.value = data.infoList[0].url;
								
								document.forms['addUser'].tempDate.value = data.infoList[0].createDate;
								document.forms['addUser'].id.value = data.infoList[0].id;
								
								
								document.getElementById('dImg').innerHTML = '<img width="120" height="120"  src="<%=request.getContextPath() %>/wechatImages/qrcode/'+data.infoList[0].img+'"/>';
									
								 
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
							<li class="active">产品管理</li>
							<li class="active">产品管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post" name="searchForm" action="<%=request.getContextPath()%>/product/productManager" style="float: left;width:100%">
								<table class="filterTable">
									<tr>
										<td>编号</td>
										<td><input type="text" name="number" value="${queryDto.number}"></td>
										<td> 型号</td>
										<td><select id="uProductInfoId" name="productInfoId">
												<option value="">请选择</option>
												<c:forEach items="${tempList}" var="tempProduct">
													<option value="${tempProduct.id}">${tempProduct.name}</option>
												</c:forEach >
											</select>
										</td>
										<td>创建日期</td>
										<td colspan="3">
											 <div style="display:inline-block"><input type="text" name ="startDate"  onclick="WdatePicker({isShowClear:true,readOnly:true})" value="${queryDto.startDate}"></div>
											 --
											 <div style="display:inline-block"><input type="text" name="endDate" onclick="WdatePicker({isShowClear:true,readOnly:true})" value="${queryDto.endDate}"></div>
										   	
										</td>
									</tr>
									
									
								</table>
							   
								<div class="btn-group col-md-12">
										 <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editUser('')"><span class="icon-plus"></span>添加</button>
										 <button class="btn  btn-purple" type="button"  onclick="deleteUsers('')">批量删除</button>
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
														<th>编号</th>
														<th>型号</th>
														<th>生产日期</th>
														<th>二维码</th>
														<th>操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
				<f:page page="${resultPage}" url="./productManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="user" method="post" name="addUser" action ="<%=request.getContextPath()%>/product/saveProduct">
			 <input type="hidden" name="id" value="${product.id}">
			  <input type="hidden"  name="tempDate" value="${product.createDate}">
			    <input type="hidden"  name="img" >
			      <input type="hidden"  name="url" >
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改用户信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">编号</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uNumber" name ="number"  type="text"data-rule="编号:required;" style="width:210px;"/>
												</div>
											</div>
										</div>
									</div>
							
							
									
									
									
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">型号</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group" >
													<select id = "uProductInfoId" style="width:210px;"name="productInfoId">
													<c:forEach items="${tempList}" var="tempProduct">
														<option value="${tempProduct.id}">${tempProduct.name}</option>
													</c:forEach >
													</select>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">生产日期</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uTodate" name ="addDate" value="${newDate}" onclick="WdatePicker({isShowClear:true,readOnly:true})" type="text"  data-rule="生产日期:required;"  style="width:210px;"/>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group" style="height:80px;">
										<label class="col-sm-3 control-label no-padding-right text-right">二维码</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div id="dImg" class="input-group">
													
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