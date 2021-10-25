<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/local/zh_CN.js"></script>



<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/js/kindeditor/lang/zh_CN.js"></script>
<link rel="stylesheet" href="css/commen.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>

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
	

var message= "${message}";
var errorCount = "${errorCount}";
if(message != '')
{
	//alert(message+'<br/><a href="#">aaaaa</a>');	
	if(errorCount ==1)
	{
		message = message +'<a href="./showExcelMessage">查看详情</a>';
	}
	$('#message').html(message);
	$('#keyWBtn').modal('show');
}

$('#sStatus')[0].value = '${queryDto.status}';		
var articleListTab = $('#articleListTab');

if( data != '')
{

		for(var i=0;i<data.infoList.length;i++){

			newcontent = newcontent + '<tr>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
				
				
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].count+'</td>';
				
				
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-success"  onclick="editUser('+i+')" data-toggle="modal" data-target="#mymodal-data">导入书籍</button></td>';
				
				newcontent = newcontent +'</tr>';
				

				
			}
			articleListTab.append(newcontent);
			
			
			

}
})




function editUser(index)
{
	

			if(index.length != '0' )
			
			{
					

				
					document.forms['excelForm'].shopId.value = data.infoList[index].id;

				

			}
}


function setOrderId(index)
{
	
	document.forms['materialForm'].orderId.value = data.infoList[index].id;
	document.forms['materialForm'].userName.value = data.infoList[index].userName;
	document.forms['materialForm'].mobile.value = data.infoList[index].mobile;
	document.forms['materialForm'].address.value = data.infoList[index].address;
}
function upExcel()
{


document.forms['excelForm'].action = "./upExcel";
document.forms['excelForm'].submit();




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
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="main.htm">首页</a>							</li>
							<li class="active">书院管理</li>
							<li class="active">库存管理</li>
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
														
														<th width="100">店铺名称</th>
														<th width="100">书籍库存</th>
														
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
			 <form class="article" method="POST" id="form" name="excelForm" enctype="multipart/form-data" action ="<%=request.getContextPath()%>/book/upExcel">
			 <input type="hidden" name="shopId" value="">
			 
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
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
									</div>
									<BR/>
									<a href="<%=request.getContextPath()%>/images/test.xls">excel2013导入模板</a>
								</div>
							</div>
							
					</div>

								
							
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="upExcel()">导入</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>
						</form>
					</div>
				</div>
			</div>


			<!-- 模态弹出窗内容 -->
			<form class="form-horizontal" role="form" name="materialForm" method="post" action ="<%=request.getContextPath() %>/material/saveKeywordByMaterialId">
			
			<input type="hidden" name="orderId">
			<input type="hidden" name="express">
			
			

			<div class="modal  fade" id="keyWBtn" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
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
										
									<label class="col-sm-6 control-label no-padding-right text-right"><span id="message"></span></label>
									</div>
								</div>
							</div>
					</div>

								
							
						</div>
						
						</form>
					</div>
				</div>
			</div>


			
</form>
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->