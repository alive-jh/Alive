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
var data =${jsonStr};
$(document).ready( function () {
	


$('#sStatus')[0].value = '${queryDto.status}';		
var articleListTab = $('#articleListTab');

if( data != '')
{

		for(var i=0;i<data.infoList.length;i++){

			newcontent = newcontent + '<tr>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].orderNumber+'</td>';
				
				//3W>8>100,5W>12>150,3W>8>100,5W>12>150
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].userName+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].mobile+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].count+'</td>';
				
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].totalPrice+'</td>';
				
				
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].statusName+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].createDate+'</td>';
				if(data.infoList[i].status ==1)
				{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-success"  onclick="editUser('+i+')" data-toggle="modal" data-target="#mymodal-data">查看</button><button class="btn btn-xs btn-info"  onclick="setOrderId('+i+')" data-toggle="modal" data-target="#keyWBtn">订单发货</button></td>';
				}
				else
				{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-success"  onclick="editUser('+i+')" data-toggle="modal" data-target="#mymodal-data">查看订单</button></td>';
				}
				newcontent = newcontent +'</tr>';
				

				
			}
			articleListTab.append(newcontent);
			
			
			

}
})


function editUser(index)
{
	$("#mallTab  tr:not(:first)").empty();

			if(index.length != '0' )
			
			{
					
					document.forms['addMall'].orderNumber.value = data.infoList[index].orderNumber;
					document.forms['addMall'].totalPrice.value = data.infoList[index].totalPrice;
					
					document.forms['addMall'].status.value = data.infoList[index].statusName;
					document.forms['addMall'].express.value = data.infoList[index].express;
					document.forms['addMall'].expressNumber.value = data.infoList[index].expressNumber;
				
					document.forms['addMall'].id.value = data.infoList[index].id;

					if(data.infoList[index].bookList != undefined)
					{
						
						var $tr=$("#mallTab tr:last");
						 var tempRow = $("#mallTab").find("tr").length;
						
						for(var j=0;j<data.infoList[index].bookList.length;j++)
						{
							
							
							 var trHtml = '<tr id="tempRow'+tempRow+'">'
							 trHtml = trHtml + '<td><img witch="40" height="40" src = "'+data.infoList[index].bookList[j].cover+'">&nbsp;&nbsp;'+data.infoList[index].bookList[j].bName+'</td>';
							
							 trHtml = trHtml + '<td>'+data.infoList[index].bookList[j].cateID+'</td>';
							 trHtml = trHtml + '<td>'+data.infoList[index].bookList[j].author+'</td></tr>';
							

							
							 $tr.after(trHtml);
							

						}
					}

			}
}


function setOrderId(index)
{
	
	document.forms['materialForm'].orderId.value = data.infoList[index].id;
	document.forms['materialForm'].userName.value = data.infoList[index].userName;
	document.forms['materialForm'].mobile.value = data.infoList[index].mobile;
	document.forms['materialForm'].address.value = data.infoList[index].address;
}
function updateMallOrderStatus()
{

	document.forms['materialForm'].express.value = document.forms['materialForm'].kuaidi.value+":"+document.forms['materialForm'].danhao.value;
	document.forms['materialForm'].action = "<%=request.getContextPath()%>/book/updateBookOrderStatus";

	document.forms['materialForm'].submit();
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
							<li class="active">书院管理</li>
							<li class="active">订单列表</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post"  name="searchForm" action="<%=request.getContextPath()%>/book/bookExpressManager" style="float: left;width:100%">
								<table class="filterTable">
									
									<tr>
										<td>订单编号</td>
										<td><input type="text" name="number" value="${queryDto.number}"></td>
										<td>订单状态</td>
										<td><select id="sStatus" name="status">
											<option value="">请选择</option>
											<option value="1">待发货</option>
											<option value="2">已发货</option>
											<option value="3">已确认</option>
											<option value="4">退款中</option>
											<option value="5">已退款</option>
											</select></td>
										
										<td>创建日期</td>
										<td colspan="3">
											 <div style="display:inline-block"><input type="text" name ="startDate"  onclick="WdatePicker({isShowClear:true,readOnly:true})" value="${queryDto.startDate}"></div>
											 --
											 <div style="display:inline-block"><input type="text" name="endDate"onclick="WdatePicker({isShowClear:true,readOnly:true})" value="${queryDto.endDate}"> </span></div>
										   	
										</td>
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
											<table id="articleListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														
														<th width="100">订单编号</th>
														<th width="100">收货人</th>
														<th width="100">手机号码</th>
														<th width="100">书籍数</th>
														<th width="100">订单金额</th>
														<th width="100">状态</th>
														<th width="100">创建日期</th>
														<th width="120">操作</th>
													</tr>
												</thead>
	
												
											</table>
											    
						<f:page page="${resultPage}" url="./bookExpressManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="article" method="post" name="addMall" enctype="multipart/form-data" action ="<%=request.getContextPath()%>/mall/saveMall">
			 <input type="hidden" name="id" value="">
			 
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
				<div class="modal-dialog" style="width:900px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">查看订单详情</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label text-right">订单编号:</label>
							<div class="col-sm-9">
								<div class="input-medium" >
									<div class="input-group" style="width:710px;">
										
									<input type="text" name ="orderNumber" readonly class="form-control input-medium" />
									</div>
								</div>
							</div>
					</div>

							<div class="form-group" style="height:100px;">
										<label class="col-sm-2 control-label no-padding-right text-right">商品详情</label>
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group">
												
												<table id="mallTab"style="width:650px;" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														<th width="150">书籍编码</th>
														<th width="50">书籍名称</th>
														<th width="50">作者</th>
														
													</tr>
												</thead>
											</table>
												</div>
											</div>
										</div>
									</div>				
							<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">订单总价</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													
													<input type="text" name ="totalPrice" readonly class="form-control input-medium" />
												</div>
											</div>
										</div>
									</div>
								<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">订单状态</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													
													<input type="text" name ="status" readonly class="form-control input-medium" />
												</div>
											</div>
										</div>
									</div>
								<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">快递公司</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													
													<input type="text" name ="express" readonly class="form-control input-medium" />
												</div>
											</div>
										</div>
									</div>

									<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">快递单号</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													
													<input type="text" name ="expressNumber" readonly class="form-control input-medium" />
												</div>
											</div>
										</div>
									</div>
									<div class="form-group" style="height:235px;">
										
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group">
													
													
										
												</div>
											</div>
										</div>
									</div>
						</div>
						
						</form>
					</div>
				</div>
			</div>


			<!-- 模态弹出窗内容 -->
			<form class="form-horizontal" role="form" name="materialForm" method="post" action ="<%=request.getContextPath() %>/material/saveKeywordByMaterialId">
			
			<input type="hidden" name="orderId">
			<input type="hidden" name="express">
			
			
			<div class="modal  fade" id="keyWBtn" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">订单发货</h4>
						</div>
						<div class="modal-body">
							<div class="form-group" style="height:200px;width:710px;">

							<div class="form-group" style="height:35px;">
									<label class="col-xs-3 control-label no-padding-right text-right">收货人</label>
										<div class="col-xs-9">
										<div class="input-medium">
											<div class="input-group">
												

												<input type="text" name ="userName" readonly class="form-control input-medium" />	
												
											</div>
										</div>
									</div>
								</div>

								<div class="form-group" style="height:35px;">
									<label class="col-xs-3 control-label no-padding-right text-right">手机号码</label>
										<div class="col-xs-9">
										<div class="input-medium">
											<div class="input-group">
												
												<input type="text" name ="mobile" readonly class="form-control input-medium" />	
												
											</div>
										</div>
									</div>
								</div>

								<div class="form-group" style="height:35px;" style="width:710px;">
									<label class="col-xs-3 control-label no-padding-right text-right">收货地址</label>
										<div class="col-xs-9">
										<div class="input-medium">
											<div class="input-group" style="width:710px;">
												
												<input type="text" name ="address" readonly class="form-control input-medium" style="max-width:350px;width:100%;" />	
												
											</div>
										</div>
									</div>
								</div>

								<div class="form-group" style="height:35px;">
									<label class="col-xs-3 control-label no-padding-right text-right">快递公司</label>
										<div class="col-xs-9">
										<div class="input-medium">
											<div class="input-group">
												
													<input type="text" name ="kuaidi" data-rule="快递公司:required;" class="form-control input-medium" />
												
											</div>
										</div>
									</div>
								</div>
								
								
								<div class="form-group" style="height:35px;">
									<label class="col-xs-3 control-label no-padding-right text-right">快递单号</label>
										<div class="col-xs-9">
										<div class="input-medium">
											<div class="input-group">
												
													<input type="text" name ="danhao" data-rule="快递单号:required;" class="form-control input-medium" />
												
											</div>
										</div>
									</div>
								</div>
								
								
								
							</div>
							
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="updateMallOrderStatus()">提交</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>
					</div>
				</div>
			</div>
</form>
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->