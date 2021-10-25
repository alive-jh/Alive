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


$(document).ready( function () {
		

//$('#sMatchingrules')[0].value="${queryDto.matchingrules}";
//$('#sStatus')[0].value="${queryDto.status}";



var data = ${jsonStr};

			
	if(data != '')
	{
			//数据执行完成
			var userListTab = $('#userListTab');
			$("#userListTab tr:not(:has(th))").remove();
			for(var i=0;i<data.infoList.length;i++){
				
				
				
				newcontent = newcontent + '<tr><td class="center"><input type="checkbox"  id="checkId" name="checkId" value ="'+data.infoList[i].id+'"/></td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
				
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >';
				if(data.infoList[i].prizeList != undefined)
				{

						var tempStr = data.infoList[i].prizeList.split(',');
							
						for(var k=0;k<tempStr.length;k++)
						{
							var str = tempStr[k].split('<');
							newcontent = newcontent +str[1]+',中奖率:'+str[2]+',数量:'+str[3]+'&nbsp;&nbsp;<a>编辑</a>&nbsp;<a>删除</a><br/>';
							

						}
				}
					
					
				newcontent = newcontent + '</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].createDate+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].endDate+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].statusName+'</td>';

				
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editUser('+i+')" data-toggle="modal" data-target="#mymodal-data">查看详情</button>&nbsp;<button class="btn btn-xs btn-info"  onclick="setUser('+i+')" data-toggle="modal" data-target="#mymodal-data1">新增奖品</button>&nbsp;';
					newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editUser('+i+')" data-toggle="modal" data-target="#mymodal-data"><i class="icon-edit bigger-120"></i></button>';

					
					newcontent = newcontent +'<button onclick="removeKeyword('+i+')" class="btn btn-xs btn-danger"><i class="icon-trash bigger-120"></i></button>';
				
				
				
				

				newcontent = newcontent + '</td>';
				
	}
			
			userListTab.append(newcontent);
			
			
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

function removeKeyword(id){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addUser'].action ="removeKeyword?keywordId="+id;
		document.forms['addUser'].submit();
	}
}

function updateUserStatus(id,status){

	
	document.forms['addUser'].action ="updateUserStatus?userId="+id+"&status="+status;
	document.forms['addUser'].submit();
	
}

var tempData =  ${jsonStr};
function editUser(index){
	
				
				
				
				if(index.length == '0' ){
					$('#uName')[0].value='';
					$('#uCreateDate')[0].value='';
					$('#uEndDate')[0].value='';
					
					
					document.forms['addUser'].id.value ='';
				}
				else 
					{
						
						$('#uStatus')[0].value = tempData.infoList[index].status;
						$('#uName')[0].value = tempData.infoList[index].name;
						$('#uCreateDate')[0].value = tempData.infoList[index].createDate;
						$('#uEndDate')[0].value = tempData.infoList[index].endDate;
						document.forms['addUser'].id.value = tempData.infoList[index].id;
						
				}
				
			} 




function setUser(index){
	
				
						
						
	document.forms['activity'].activityId.value = tempData.infoList[index].id;
		
				
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
							<li class="active">活动管理</li>
							<li class="active">活动设置</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" name="searchForm" action="<%=request.getContextPath()%>/activity/activityManager" style="float: left;width:100%">
						
							   
								<div class="btn-group col-md-12">
										 <button class="btn  btn-purple" type="button"  data-toggle="modal" data-target="#mymodal-data" onclick="editUser('')"><span class="icon-plus"></span>添加</button>
										 
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
														<th>活动名称</th>
														<th width="30%">奖品信息</th>
														<th>创建日期</th>
														<th>截止日期</th>
														<th>状态</th>
														<th>活动详情</th>
														<th>操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
				<f:page page="${resultPage}" url="./activityManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->

			<!-- 模态弹出窗内容 -->
			 <form class="user" method="post" name="addUser" action ="<%=request.getContextPath()%>/activity/saveActivity">
			 <input type="hidden" name="id">
			  <input type="hidden" name="tempDate">
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改活动信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">活动名称</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group"  style="width:310px;">
													
													<input class="form-control" name="name" id="uName"  value="${article.title}" type="text" data-rule="活动名称:required;"style="max-width:210px;width:100%;"  />
													
												</div>
											</div>
										</div>
									</div>
							
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">开始日期</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group"  style="width:310px;">
													<input class="form-control" name="date1" id="uCreateDate"  onclick="WdatePicker({isShowClear:true,readOnly:true})" type="text" data-rule="开始日期:required;"style="max-width:210px;width:100%;"  />
												</div>
											</div>
										</div>
									</div>

									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">截止日期</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group"  style="width:310px;">
													<input class="form-control" name="date2" id="uEndDate"  onclick="WdatePicker({isShowClear:true,readOnly:true})" type="text" data-rule="截止日期:required;"style="max-width:210px;width:100%;"  />
												</div>
											</div>
										</div>
									</div>


										<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">状态</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group"  style="width:310px;">
													<select id = "uStatus" style="width:210px;" class="input-medium" name="status">
														<option value="0">正常</option>
														<option value="1">关闭</option>
														
													</select>
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
			 <form class="user" method="post" name="activity" action ="<%=request.getContextPath()%>/activity/saveActivityPrize">
			 <input type="hidden" name="activityId">
			<div class="modal  fade" id="mymodal-data1" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改活动信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">奖品名称</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group"  style="width:310px;">
													
													<input class="form-control" name="jiangpin" id="uJiangpin"  value="${article.title}" type="text" data-rule="奖品名称:required;"style="max-width:210px;width:100%;"  />
													
												</div>
											</div>
										</div>
									</div>
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">中奖几率</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group"  style="width:310px;">
													
													<input class="form-control" name="percentage" id="uPercentage"  value="${article.title}" type="text" data-rule="中奖几率:required;"style="max-width:210px;width:100%;"  />
													
												</div>
											</div>
										</div>
									</div>
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">奖品数量</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group"  style="width:310px;">
													
													<input class="form-control" name="count" id="uCount"  value="${article.title}" type="text" data-rule="奖品数量:required;"style="max-width:210px;width:100%;"  />
													
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