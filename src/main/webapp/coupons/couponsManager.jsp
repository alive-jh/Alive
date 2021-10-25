<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery-2.1.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/local/zh_CN.js"></script>

<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>
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
document.forms['addaccount'].action = "accountOrderManager";
document.forms['addaccount'].submit();
}
//本地条件分页查询
function pageJump(page) {
PPage("page_select", page, totalPageCount, "pageJump", true);
$("#currentPage").val(page);
}
var newcontent = '';




var data = ${jsonStr};
$(document).ready( function () {
	
	
	if("${queryDto.dateType}"!='')
	{
		
		$("#sDateType").val("${queryDto.dateType}");
	}

	$("#sType").val("${queryDto.type}");


	
			if(data !='')
			{
				var accountListTab = $('#accountListTab');
				$("#accountListTab tr:not(:has(th))").remove();
				accountListTab.append("<tr><td colspan=\"20\" style=\"text-align: center;\"><img src=\"<%=request.getContextPath() %>/css/images/loading.gif\" />正在加载中，请等待...</td></tr>");
		
				for(var i=0;i<data.infoList.length;i++){

					
					
					newcontent = newcontent + '<tr><td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].title+'</nobr></td>';
					
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].money+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].typeName+'</nobr></td>';
				
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].endDate+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].createDate+'</nobr></td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr><button class="btn btn-xs btn-info"  data-toggle="modal" data-target="#keyWBtn" onclick="show('+i+')" >查看链接地址</button></nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="deleteCoupons('+i+')" >删除</button></td>';

					newcontent = newcontent + '</tr>';
				}
	
				$("#accountListTab tr:not(:has(th))").remove();
				accountListTab.append(newcontent);
			}
				
			
			

});

function show(index)
{
	
	$("#uContent").val(data.infoList[index].url);
	
}

function deleteCoupons(index)
{
	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['searchForm'].action ="deleteCoupons?couponsId="+data.infoList[index].id;
		document.forms['searchForm'].submit();
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
							<li class="active">优惠券管理</li>
							<li class="active">优惠券</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="user" method="post"  name="searchForm" action ="">
								<table class="filterTable">
									<tr>
										<td>日期</td>
										<td colspan="3"><select name ="dateType" id="sDateType">
										<option value="0">创建时间</option>
										<option value="1">截止时间</option>
										</select>
											 <div style="display:inline-block"><input type="text" value="${queryDto.startDate}" name ="startDate"  onclick="WdatePicker({isShowClear:true,readOnly:true})" ></div>
											 --
											 <div style="display:inline-block"><input type="text" value="${queryDto.endDate}" name="endDate" onclick="WdatePicker({isShowClear:true,readOnly:true})" ></div>
										   	
										</td>
									</tr>
								<tr>
										<td>标题</td>
										<td><input type="text" name="title" value="${queryDto.title}" ></td>
										
										<td>状态</td>
										<td>	<select name ="type" id="sType">
										<option value="">全部</option>
													
										<option value="0">无使用门槛</option>
										<option value="1">满额使用</option>
														
										</select></td>
										<td></td>
										<td></td>
										
										
									</tr>
									
									
								</table>
							   
								<div class="btn-group col-md-12">
										
										<button type="button" data-toggle="modal" data-target="#mymodal-data" class="btn btn-purple ml10 mb10"><span class="icon-plus"></span>添加
										 </button>
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
													
														<th>标题</th>
														<th>金额</th>
														<th>类型</th>
														<th>截止日期</th>
														<th>创建日期</th>
														<th>链接地址</th>
														<th>操作</th>
														
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
			<f:page page="${resultPage}" url="./couponsManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="account" method="post" id="upExcel" name="orderForm"  action ="<%=request.getContextPath()%>/coupons/saveCoupons">
			 <input type="hidden" name="id" >
			  
			
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">编辑优惠券</h4>
						</div>
						<div class="modal-body">
							
							
							
								

							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">标题:</label>
									<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input type="text" name="title" data-rule="标题:required;">
												</div>
											</div>
									</div>
							</div>
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">金额</label>
									<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">

													<input type="text" name="money" data-rule="金额:required;">
												
												</div>
											</div>
									</div>
							</div>
						<div id ="divPwd1"class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">类型</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group" style="width:230px;"><select id="uType" name ="type" style="width:150px;" >
													<option value="0">无使用门槛</option>
														<option value="1">满额使用</option>
													</select>
													<input id="uPrice" name ="price"  class="input-medium "  maxlength="18"type="text"  value="0" style="width:57px;"/>
												</div>
											</div>
										</div>
									</div>
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">截止日期</label>
									<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">

													<input type="text" name="tempDate" readonly onclick="WdatePicker({isShowClear:true,readOnly:true})" data-rule="截止日期:required;">
												
												</div>
											</div>
									</div>
							</div>

						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary"  >保存</button>
							
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
							<h4 class="modal-title">链接地址</h4>
						</div>
						<div class="modal-body">
							<div class="form-group" style="height:55px;">
								
								<div class="form-group" style="height:55px;">
										
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													
													<textarea id = "uContent" name ="content" style="width:500px;height:80px;"></textarea>
												</div>
											</div>
										</div>
									</div>
								
								
								
							</div>
							
						</div>
						
					</div>
				</div>
			</div>
</form>
		</div><!-- /.main-container -->
