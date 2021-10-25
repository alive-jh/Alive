<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


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


var data = ${jsonStr}
$(document).ready( function () {
		

$('#sSex')[0].value="${queryDto.sex}";

			if(data != ''){

			//数据执行完成
			var userListTab = $('#userListTab');
			$("#userListTab tr:not(:has(th))").remove();
			for(var i=0;i<data.infoList.length;i++){
				
				
				
				newcontent = newcontent + '<tr></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img witch="30" height="30" src="'+data.infoList[i].headImg+'"/>&nbsp;&nbsp;'+data.infoList[i].name+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].sex+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].province+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].city+'</td>';
				if(data.infoList[i].mobile!=undefined)
				{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].mobile+'</td>';
				}
				else
				{
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ></td>';
				}
				
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].createDate+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].fraction+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-success"  onclick="searchFraction('+i+')" >查看积分</button></td>';
				

				newcontent = newcontent + '</tr>';
				
				}
			
			userListTab.append(newcontent);	
		}


});




function searchFraction(index){

	
		document.forms['addUser'].action ="searchIntegral?memberId="+data.infoList[index].id;
		document.forms['addUser'].submit();
	
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
								<a href="#">首页</a>							</li>
							<li class="active">客户管理</li>
							<li class="active">客户管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post" name="searchForm" action="<%=request.getContextPath()%>/member/memberManager" style="float: left;width:100%">
								<table class="filterTable">
									
									<tr>
										<td>客户名称</td>
										<td><input type="text" name="name" value="${queryDto.name}"></td>
										<td>手机号码</td>
										<td>
											<input type="text" name="mobile" value="${queryDto.mobile}">
										</td>

										<td>性别</td>
										<td>
											<select id="sSex" name="sex">
												<option value="">请选择</option>
												<option value="男">男</option>
												<option value="女">女</option>
											</select>
										</td>
									</tr>
									<tr>
										<td>创建日期</td>
										<td colspan="3">
											 <div style="display:inline-block"><input type="text" name ="startDate" onclick="WdatePicker({isShowClear:true,readOnly:true})" value="${queryDto.startDate}"></div>
											 --
											 <div style="display:inline-block"><input type="text" name="endDate" onclick="WdatePicker({isShowClear:true,readOnly:true})" value="${queryDto.endDate}"></div>
										   	
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
											<table id="userListTab" class="table table-striped table-bordered table-hover" style="text-align:left;">
												<thead>
													<tr>
														
														<th width="20%">客户名称</th>
														<th >性别</th>
														<th>省份</th>
														<th>城市</th>
														<th>手机号码</th>
														<th>创建日期</th>
														<th>积分数</th>
														<th>操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
				<f:page page="${resultPage}" url="./memberManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			
			 
			
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->