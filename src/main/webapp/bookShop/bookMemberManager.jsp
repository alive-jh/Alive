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

//html存储全局变量
var newcontent = '';
//服务器json字符串
var jsonStr ='${jsonData}';
//json字符串转json数组
var jsonData = eval(jsonStr);


//更新界面的方法
function updateUI(){
	newcontent='';
	var dataListTab = $('#dataListTab');
	$("#dataListTab tr:not(:has(th))").remove();
	for(var i=0;i<jsonData.length;i++){
			var staStr=jsonData[i].status==1?"开通":"关闭";
			var staCss=jsonData[i].status==1?"success":"danger";
			newcontent = newcontent + '<tr>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img src="'+jsonData[i].headimgurl+'" style="width:40px;"/></td>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].account+'</td>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].name+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].nickname+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].card+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><span class="label label-md label-'+staCss+'">'+staStr+'</span></td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].price+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].createdate+'</td>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}

//初始化
$(document).ready( function () {
	
	//更新界面
	updateUI();

});





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
			<li class="active">店铺会员管理</li>
		</ul>
		<!-- .breadcrumb -->

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/bookShop/bookMemberManager"
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
							<table id="dataListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th width="100">会员头像</th>
										<th width="100">店铺管理员</th>
										<th width="100">店铺名称</th>
										<th width="100">会员昵称</th>
										<th width="100">会员卡卡号</th>
										<th width="100">会员卡状态</th>
										<th width="100">会员卡价格</th>
										<th width="100">创建时间</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./bookMemberManager"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>