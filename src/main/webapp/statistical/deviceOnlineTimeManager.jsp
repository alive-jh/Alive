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
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/jquery.base64.min.js"></script>
	
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/channel/base.js"></script>
	
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">

<style>
.gw_num{border: 1px solid #dbdbdb;width: 110px;line-height: 26px;overflow: hidden;}
.gw_num em{display: block;height: 26px;width: 26px;float: left;color: #7A7979;border-right: 1px solid #dbdbdb;text-align: center;cursor: pointer;}
.gw_num .num{display: block;float: left;text-align: center;width: 52px;font-style: normal;font-size: 14px;line-height: 24px;border: 0;}
.gw_num em.add{float: right;border-right: 0;border-left: 1px solid #dbdbdb;}
</style>
<script>

var newcontent = '';
var jsonData;
var answerData;
var currentChannelId;
//更新总记录
function updateTotalCount(count){
	//更新总记录
	var total=$("font#fPageTotal").text();
	total=eval(total)+count;
	$("font#fPageTotal").text(total);
}

//切换频道状态
function switchChannelStatus(index){
	var id = jsonData[index].id;
	var status = jsonData[index].status;
	var nextStatus = 0;
	if(status == 1){
		nextStatus = 0;
	}else{
		nextStatus = 1;
	}
	$.ajax({
			type: "POST",
			data:"id="+id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/switchChannelStatus", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				jsonData[index].status = nextStatus;
				
				updateUI();
			}
		});	

}

//更新界面的方法
function updateUI(){
	newcontent='';
	var dataListTab = $('#dataListTab');
	$("#dataListTab tr:not(:has(th))").remove();
	for(var i=0;i<jsonData.length;i++){
			newcontent = newcontent + '<tr>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].id+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].channelId+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >2017-04-25 10:09:09</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >6000</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data">查看课程</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showAlbums('+jsonData[i].id+')"">在线情况</button>';
			newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="updateChannelInfo('+i+')">立即更新</button></td>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}

//初始化
$(document).ready( function () {
	
	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	updateUI();
	$(".add").click(function(){
		var n=$(this).prev().val();
		var name = $(this).prev().attr("name");
		var id = name.replace("updateCycle_","");
		var num=parseInt(n)+1;
		updateCycle(id,num)
		if(num==0){ return;}
		$(this).prev().val(num);
	});
	//减的效果
	$(".jian").click(function(){
		var n=$(this).next().val();
		var num=parseInt(n)-1;
		var name = $(this).next().attr("name");
		var id = name.replace("updateCycle_","");
		updateCycle(id,num)
		if(num==0){ return}
		$(this).next().val(num);
	});

});

//修改更新周期
function updateCycle(id,num) {
	var formData = new FormData();
	formData.append("id",id);
	formData.append("num",num);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/channel/updateCycle' ,
          type: 'POST',
          data:formData,
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (returndata) {
          },
          error: function (returndata) {
          
          }
     });
}

//预添加或者修改
function editData(index)
{	
	if(index.length == '0'){
		$("#index").val("");
		$("#id").val("");
		$("#channelId").val("");
		$("#name").val("");
		$("#intro").val("");
		$("#image").val("");
		$("#fans").val("");
		$("#status").val("");
		$("#level").val("");
	}else{
		//第一步，传输数组数据到弹出框
		var myData=jsonData[index];
		$("#index").val(index);
		$("#id").val(myData.id);
		$("#channelId").val(myData.channelId);
		$("#name").val(myData.name);
		$("#intro").val(myData.intro);
		$("#image").val(myData.image);
		$("#fans").val(myData.fans);
		$("#status").val(myData.status);
		$("#level").val(myData.level);
		$("#nextUpdateTime").val(myData.nextUpdateTime);
		$("#lastUpdateTime").val(myData.lastUpdateTime);
		$("#updateCycle").val(myData.updateCycle);
		alert(myData.updateCycle);

	}
}





//修改数据
function updateData(index) {
     //隐藏modal
	 $("#myModal-data").modal("hide");
     var formData = new FormData($("#modifyData")[0]);
     $.ajax({
          url: '<%=request.getContextPath() %>/api/channel/saveChannel' ,
          type: 'POST',
          data: formData,
          
          async: false,
          cache: false,
          contentType: false,
          processData: false,
          beforeSend:function(){
		   //这里是开始执行方法
		  },
          success: function (returndata) {
        	  var data = returndata.data;
              var index=$("#index").val();
			  if(index == ''){
					//更新数组数据
					//添加到最后
					jsonData.push(data); 
					updateTotalCount(1);
				}else{
					//更新数组数据
					jsonData[index]=returndata.data;
			  }
			  //更新界面
			  updateUI();
          },
          error: function (returndata) {
          
          }
     });
}

//删除频道
function deleteChannel(index){
	if(window.confirm('你确定要删除频道吗？')){
          
    }else{
          return false;
    }
    if(window.confirm('请再次确认？')){
          
    }else{
          return false;
    }
    if(window.confirm('删除频道就没有了哦？')){
          
    }else{
          return false;
    }
	$.ajax({
			type: "POST",
			data:"id="+jsonData[index].id,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/deleteChannel", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				jsonData.remove(index);
				updateTotalCount(-1);
				updateUI();
			}
		});	

}

function  showSoundIntro(){

}
function AutomaticFilling(){
	channelId = $("input[ name='channelId' ] ").val();
	$.ajax({
			type: "POST",
			data:"channelId="+channelId,
			dataType: "json",
			url: "<%=request.getContextPath() %>/api/channel/getChanelInfoFromWeb", 
			context: document.body, 
			beforeSend:function(){
				//这里是开始执行方法
			},
			complete:function(){
				
			},
			success: function(data){
				newcontent='';
				var dataListTab = $('#dataListTab');
				$("#dataListTab tr:not(:has(th))").remove();
				for(var i=0;i<jsonData.length;i++){
						newcontent = newcontent + '<tr>';	
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].id+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].channelId+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].name+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].intro+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><img width="60" height="60" src="'+jsonData[i].image+'" /></td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].fans+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].status+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].level+'</td>';
						newcontent = newcontent + '<td nowrap="nowrap" align="left" ><button class="btn btn-xs btn-info"  onclick="editData('+i+')" data-toggle="modal" data-target="#myModal-data">编辑</button>';
						newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="showAlbums('+i+')" data-toggle="modal" data-target="#answer-data">查看专辑</button>';
						newcontent = newcontent + '&nbsp;&nbsp;<button class="btn btn-xs btn-info"  onclick="deleteChannel('+i+')">删除频道</button></td>';
						newcontent = newcontent + '</tr>';
				}
				dataListTab.append(newcontent);
			}
		});	

}
</script>


<div class="main-content">
	<div id="myJson" style="display: none;">${jsonData}</div>
	<div class="breadcrumbs" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active"><a href="">统计管理</a></li>
			<li class="active"><a href="">在线时间管理</a></li>
		</ul>

	</div>

	<div class="page-content">

		<div class="page-header col-xs-12" style="float: left;width:100%">
			<form class="form-inline" role="form" method="post" name="searchForm"
				action="<%=request.getContextPath()%>/channel/channelManage"
				style="float: left;width:100%">
				<table class="filterTable">

					<tr>
						<td>机器人ID</td>
						<td><input type="text" name="searchStr"
							value="${queryDto.searchStr}"></td>
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
			<div style="text-align:center;"><h4>机器人在线情况</h4></div>
			<div class="" style="text-align:left;" id="onlineStatus">
				<span>500/20000</span>
			</div>

			<div style="text-align:center;"><h4>最近七天统计</h4></div>
			<div class="" style="text-align:left;">
			
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="row">
					<div class="col-xs-12">
						<div class="table-responsive" style="text-align:right;">
							<div style="text-align:center;"><h4>机器人统计</h4></div>
							<table id="dataListTab"
								class="table table-striped table-bordered table-hover"
								style="text-align:left;">
								<thead>
									<tr>
										<th width="30">序列号</th>
										<th width="30">机器ID</th>
										<th width="100">最后使用时间</th>
										<th width="100">平均时长</th>
										<th width="100">操作</th>
									</tr>
								</thead>

							</table>

							<f:page page="${resultPage}" url="./deviceOnlineTimeManager"
								params="<%=request.getParameterMap()%>" />

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>



