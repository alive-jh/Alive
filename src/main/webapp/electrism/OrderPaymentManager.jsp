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

					
					newcontent = newcontent + '<tr><td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].electrismName+'</nobr></td>';
					
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].orderCount+'</nobr></td>';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><nobr>'+data.infoList[i].payment+'</nobr></td>';
					
					
				
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editArticle('+i+')" data-toggle="modal" data-target="#mymodal-data">处理提现</button></td>';

					
					
					
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



function editArticle(index){
	
				
				
						
					$("#mallTab  tr:not(:first)").empty();
					document.forms['addarticle'].id.value = data.infoList[index].id;
					document.forms['addarticle'].nickName.value = data.infoList[index].nickName;
					document.forms['addarticle'].electrismName.value= data.infoList[index].electrismName;
					document.forms['addarticle'].card.value= data.infoList[index].card;
					document.forms['addarticle'].bank.value= data.infoList[index].bank;
					document.forms['addarticle'].payment.value = data.infoList[index].payment;
					
					
					if(data.infoList[index].info != undefined)
					{
						
						var $tr=$("#mallTab tr:last");
						 var tempRow = $("#mallTab").find("tr").length;
						
						var tempInfo = data.infoList[index].info.split(',');
						for(var j=0;j<tempInfo.length;j++)
						{
							
							var tempStr  = tempInfo[j].split('>');
							var trHtml = '<tr id="tempRow'+tempRow+'">'
							trHtml = trHtml + '<td>'+tempStr[0]+'</td>';
							if(tempStr[1] ==1)
							{
								trHtml = trHtml + '<td>上门服务</td>';
							}
							if(tempStr[1] ==2)
							{
								trHtml = trHtml + '<td>电器维护</td>';
							}
							if(tempStr[1] ==3)
							{
								trHtml = trHtml + '<td>线路维护</td>';
							}
							if(tempStr[1] ==4)
							{
								trHtml = trHtml + '<td>其他服务</td>';
							}
							trHtml = trHtml + '<td>'+tempStr[2]+'</td>';
							trHtml = trHtml + '<td>'+tempStr[3]+'</td><tr>';

							
							 $tr.after(trHtml);
							

						}
					}								
						
				
				
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

function update()
{
	document.forms['addarticle'].submit();

	
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
							<li class="active">预约订单</li>
							<li class="active">提现管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post"  name="searchForm" action="<%=request.getContextPath()%>/electrism/orderPaymentManager" style="float: left;width:100%">
								
							<!--	<table class="filterTable">
									
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
								-->
						
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
														
														<th width="100">电工名称</th>
														<th width="100">订单数</th>
														<th width="100">提现金额</th>
														<th width="100">操作</th>
														
													</tr>
												</thead>
	
												
											</table>
											    
						<f:page page="${resultPage}" url="./orderPaymentManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="article" method="post" name="addarticle" enctype="multipart/form-data" action ="<%=request.getContextPath()%>/electrism/updateOrderPaymentManager">
			 <input type="hidden" name="id">
			 
			 
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" >
				<div class="modal-dialog" style="width:900px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">查看订单详情</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label text-right">电工名称:</label>
							<div class="col-sm-9">
								<div class="input-medium" >
									<div class="input-group" style="width:710px;">
										
									<input type="text" name ="electrismName" readonly class="form-control input-medium" />
									</div>
								</div>
							</div>
					</div>

							<div class="form-group" style="height:100px;">
										<label class="col-sm-2 control-label no-padding-right text-right">订单详情</label>
											<div class="col-sm-10">
											<div class="input-medium">
												<div class="input-group">
												
												<table id="mallTab"style="width:650px;" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														<th width="50">订单编号</th>
														<th width="50">服务项目</th>
														<th width="50">服务金额</th>
														<th width="50">订单时间</th>
													</tr>
												</thead>
											</table>
												</div>
											</div>
										</div>
									</div>
									
							<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">提现金额</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													
													<input type="text" name ="payment" readonly class="form-control input-medium" />
												</div>
											</div>
										</div>
								</div>

								
								<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">开户名称</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													
													<input type="text" name ="nickName" readonly class="form-control input-medium" />
												</div>
											</div>
										</div>
								</div>
								<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">开户银行</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													
													<input type="text" name ="bank" readonly class="form-control input-medium" />
												</div>
											</div>
										</div>
								</div>
								<div class="form-group" style="height:35px;">
										<label class="col-sm-2 control-label no-padding-right text-right">银行卡号</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													
													<input type="text" name ="card" readonly class="form-control input-medium" />
												</div>
											</div>
										</div>
									</div>
						<div class="modal-footer">
							<button type="button"  onclick="update()" class="btn btn-primary">保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
									
						</div>
						
						</form>
					</div>
				</div>
			</div>
			


			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->