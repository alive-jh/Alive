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
		



	$.ajax({
		type: "POST",
		data:"page=${queryDto.page}&pageSize=${queryDto.pageSize}&name=${queryDto.name}&model=${queryDto.model}&power=${queryDto.power}",
		dataType: "json",
		url: "<%=request.getContextPath() %>/product/productInfoManagerView", 
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
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].name+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].model+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].power+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].factor+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].colortTemperature+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].cri+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].dimming+'</td>';
				newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+data.infoList[i].luminousFlux+'</td>';


				
			
					
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

function deleteProductInfo(id){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['addUser'].action ="deleteProductInfo?productInfoId="+id;
		document.forms['addUser'].submit();
	}
}



function editUser(id){
	
				
				if(id ==''){
					$('#uName')[0].value='';
					$('#uModel')[0].value='';
					
					$('#uFactor')[0].value='';
					$('#uColortTemperature')[0].value ='';
					$('#uCri')[0].value='';
					$('#uDimming')[0].value='';
					$('#uLuminousFlux')[0].value='';



					//$('#uType option')[0].selected=true;
					//$('#uStatus option')[0].selected=true;
			
					document.forms['addUser'].id.value ='';
				}
				else {
					  
					  
						$.ajax({
							type: "POST",
							data:"productId="+id,
							dataType: "json",
							url: "<%=request.getContextPath() %>/product/productInfoManagerView", 
							context: document.body, 
							beforeSend:function(){											
							},
							complete:function(){
								
								
							},
							success: function(data){



								$('#uName')[0].value = data.infoList[0].name;
								$('#uModel')[0].value = data.infoList[0].model;
								$('#uPower')[0].value = data.infoList[0].power;
								$('#uFactor')[0].value = data.infoList[0].factor;
								$('#uColortTemperature')[0].value = data.infoList[0].colortTemperature;
								$('#uCri')[0].value = data.infoList[0].cri;
								$('#uDimming')[0].value = data.infoList[0].dimming;
								$('#uLuminousFlux')[0].value = data.infoList[0].luminousFlux;
								$('#uDescription')[0].value = data.infoList[0].description;
								$('#uCharacteristic')[0].value = data.infoList[0].characteristic;

								
								
								document.forms['addUser'].tempDate.value = data.infoList[0].createDate;
								document.forms['addUser'].id.value = data.infoList[0].id;
								
								
								 
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
							<li class="active">产品型号管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="page-header col-xs-12" style="float: left;width:100%">
							<form class="form-inline" role="form" method="post" name="searchForm" action="<%=request.getContextPath()%>/product/productInfoManager" style="float: left;width:100%">
								<table class="filterTable">
									<tr>
										<td>名称</td>
										<td><input type="text" name="name" value="${queryDto.name}"></td>
										<td>型号</td>
										<td><input type="text" name="model" value="${queryDto.model}"></td>
										<td>功率</td>
										<td><input type="text" name="power" value="${queryDto.power}"></td>
										<td></td>
										<td></td>
									</tr>
									
									
								</table>
							   
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
														<th>名称</th>
														<th>型号</th>
														<th>功率</th>
														<th>功率因数</th>
														<th>色温</th>
														<th>显指</th>
														<th>调光</th>
														<th>光通量</th>
														<th>操作</th>
													</tr>
												</thead>
	
												<tbody>
													
													
													
												</tbody>
											</table>
											    
				<f:page page="${resultPage}" url="./productInfoManager" params="<%=request.getParameterMap()%>" />
										</div><!-- /.table-responsive -->
									</div><!-- /.col-xs-12 -->
								</div><!-- /row -->
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
					

					</div><!-- /.page-content -->
				
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			<!-- 模态弹出窗内容 -->
			 <form class="user" method="post" name="addUser" action ="<%=request.getContextPath()%>/product/saveProductInfo">
			 <input type="hidden" name="id" value="${productInfo.id}">
			  <input type="hidden"  name="tempDate" value="${productInfo.createDate}">
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">添加/修改用户信息</h4>
						</div>
						<div class="modal-body">
							
							
							<div  id="divAccount" class="form-group" style="height:35px;">
								<label class="col-sm-3 control-label no-padding-right text-right">名称</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uName" name ="name" value="${productInfo.account}" type="text"data-rule="名称:required;" style="width:210px;"/>
												</div>
											</div>
										</div>
									</div>
							
							
									
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">型号</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id = "uModel" name ="model" value="${productInfo.model}" maxlength="30"type="text" 
													data-rule="型号:required"
													style="width:250px;"/>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">功率</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group" >
													<select id = "uPower" style="width:210px;"name="power">
														<option value="3W">3W</option>
														<option value="5W">5W</option>
														
													</select>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">功率因数</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uFactor" name ="factor" value="${productInfo.factor}" type="text"  data-rule="功率因数:required;"  style="width:210px;"/>
												</div>
											</div>
										</div>
									</div>
									
									<div id="divEmail" class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">色温</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uColortTemperature" name ="colortTemperature" value="${productInfo.colortTemperature}" type="text" data-rule="色温:required;"class="form-control" style="width:210px;">
												</div>
											</div>
										</div>
									</div>

									<div id="divEmail" class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">显指</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uCri"name ="cri" value="${productInfo.cri}" type="text"  data-rule="显指:required;"class="form-control" style="width:210px;">
												</div>
											</div>
										</div>
									</div>
									<div id="divEmail" class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">光通量</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input  id="uLuminousFlux"name ="luminousFlux" value="${productInfo.luminousFlux}" type="text" data-rule="光通量:required"class="form-control" style="width:210px;">
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">是否调光</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
						<select id="uDimming" style="width:210px;"class="input-medium" name="dimming" >
														<option value="是">是</option>
														<option value="否">否</option>
								
														
													</select>
												</div>
											</div>
										</div>
									</div>

									<div id="divEmail" class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">产品描述</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">

												
													<textarea   id="uDescription"name ="description" value="${productInfo.description}"  id="inputEmail" data-rule="required"class="form-control" style="width:310px;;" rows="2"></textarea>
												</div>
											</div>
										</div>
									</div>

									<div id="divEmail" class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right text-right">产品特点</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<textarea  id="uCharacteristic" name ="characteristic" value="${productInfo.characteristic}" type="textarea" id="inputEmail" data-rule="required"class="form-control" style="width:310px;;" rows="3"></textarea>
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