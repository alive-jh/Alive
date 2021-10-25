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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">


<script>

var newcontent = '';
var jsonData;
var answerData;
var questionId;
//更新总记录
function updateTotalCount(count){
	//更新总记录
	var total=$("font#fPageTotal").text();
	total=eval(total)+count;
	$("font#fPageTotal").text(total);
}

//更新界面的方法
function updateUI(){
	newcontent='';
	var dataListTab = $('#dataListTab');
	$("#dataListTab tr:not(:has(th))").remove();
	for(var i=0;i<jsonData.length;i++){
			newcontent = newcontent + '<tr>';	
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+i+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].deviceCount+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].onlineCount+'</td>';
			newcontent = newcontent + '<td nowrap="nowrap" align="left" >'+jsonData[i].insertDate+'</td>';
			newcontent = newcontent + '</tr>';
	}
	dataListTab.append(newcontent);
	
}


//初始化
$(document).ready( function () {
	
	jsonData=$.parseJSON($("#myJson").text()).data;
	//更新界面
	showRecord("hour");

});


</script>


<div class="main-content">
	<div id="myJson" style="display: none;">${jsonData}</div>
	<div class="breadcrumbs" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="main.htm">首页</a>
			</li>
			<li class="active">统计管理</li>
			<li class="active">在线情况统计</li>
		</ul>

	</div>
	
	<script src="http://echarts.baidu.com/dist/echarts.js"></script>
	<div class="page-content">
		<script>
			
			function showRate(){
				var myChart = echarts.init(document.getElementById('RecordTable'));
				myChart.clear();
				option = {
				    title: {
				        text: "活跃率",
				        subtext: ''
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data: []
				    },
				    toolbox: {
				        show: true,
				        feature: {
				            dataZoom: {
				                yAxisIndex: 'none'
				            },
				            dataView: {
				                readOnly: false
				            },
				            magicType: {
				                type: ['line', 'bar']
				            },
				            restore: {},
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: ['2017-07-09', '2017-07-10', '2017-07-11', '2017-07-12', '2017-07-13', '2017-07-14', '2017-07-15']
				    },
				    yAxis: {
				        type: 'value',
				        axisLabel: {
				            formatter: '{value}%'
				        }
				    },
				    series: [{
				        name: '最高',
				        type: 'line',
				        data: [11, 11, 15, 13, 12, 13, 10],
				        markPoint: {
				            data: [{
				                type: 'max',
				                name: '最大值'
				            },
				            {
				                type: 'min',
				                name: '最小值'
				            }]
				        },
				        markLine: {
				            data: [{
				                type: 'average',
				                name: '平均值'
				            }]
				        }
				    }]
				};

			var formData = new FormData();
			formData.append("type","day");
			$.ajax({
		          url: '<%=request.getContextPath() %>/api/statistical/getReportDataByDay' ,
		          type: 'POST',
		          data:formData,
		          dataType: "json",  
		          async: false,
		          contentType: false,
		          processData: false,
		          beforeSend:function(){
				   //这里是开始执行方法
				  },
		          success: function (returndata) {
					  var info = returndata.data.items;
					  var xData = [];
					  var yData = [];

					  for(var i=0;i<info.length;i++){
					  		xData.push(info[i].date);
					  		yData.push(parseInt((info[i].showCount*100)/(info[i].showActiveCount)));
					  }
                      xData = xData.reverse();
                      yData = yData.reverse();

					  option["xAxis"]["data"] = xData;
				   	  option["series"][0]["data"] = yData;
		          },
		          error: function (returndata) {
		          
		          }
	     		});
				myChart.setOption(option);
			}
			
			function showRecord(type){
				var myChart = echarts.init(document.getElementById('RecordTable'));
				myChart.clear();
				if(type == 'hour'){
					typeContent = '按小时统计';
				}else if(type == 'day'){
					typeContent = '按天统计';
				}else if(type == 'week'){
					typeContent = '按周统计';
				}else if(type == 'month'){
					typeContent = '按月统计';
				}else if(type == 'active'){
					typeContent = '日激活';
				}
				option = {
				    title: {
				        text: typeContent,
				        subtext: ''
				    },
				    tooltip: {
				        trigger: 'axis'
				    },
				    legend: {
				        data: []
				    },
				    toolbox: {
				        show: true,
				        feature: {
				            dataZoom: {
				                yAxisIndex: 'none'
				            },
				            dataView: {
				                readOnly: false
				            },
				            magicType: {
				                type: ['line', 'bar']
				            },
				            restore: {},
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: ['2017-07-09', '2017-07-10', '2017-07-11', '2017-07-12', '2017-07-13', '2017-07-14', '2017-07-15']
				    },
				    yAxis: {
				        type: 'value',
				        axisLabel: {
				            formatter: '{value}人'
				        }
				    },
				    series: [{
				        name: '最高人数',
				        type: 'line',
				        data: [11, 11, 15, 13, 12, 13, 10],
				        markPoint: {
				            data: [{
				                type: 'max',
				                name: '最大值'
				            },
				            {
				                type: 'min',
				                name: '最小值'
				            }]
				        },
				        markLine: {
				            data: [{
				                type: 'average',
				                name: '平均值'
				            }]
				        }
				    }]
				};
			var formData = new FormData();
			formData.append("type",type);
			$.ajax({
		          url: '<%=request.getContextPath() %>/api/statistical/getOnlineDeviceCountList' ,
		          type: 'POST',
		          data:formData,
		          dataType: "json",  
		          async: false,
		          contentType: false,
		          processData: false,
		          beforeSend:function(){
				   //这里是开始执行方法
				  },
		          success: function (returndata) {
					  var info = returndata.data;
					  var xData = [];
					  var yData = [];
					  var activeData = [];
					  var count = 0;
					  var epalIdList = [];
					  for(var key in info){//遍历json对象的每个key/value对,p为key
 							if(count == 0){

 							
 							}else{
	 							if(type=="day"){
	 								xData.push(key);
	 								yData.push(parseInt(info[key].length*(3.5-count*0.05)));
	 								
	 								
	 							}else if(type=="week"){
	 								if(count == 1){
	 								}else{
	 						 			xData.push(key);
	 									yData.push(parseInt(info[key].length*(3.5)));		
	 								}
								}else{
	 								xData.push(key);
	 								yData.push(parseInt(info[key].length*(6)));		
	 							}
 							}
 							count = count + 1;
 					  }
 					  xData = xData.reverse();
					  yData = yData.reverse();
					  option["xAxis"]["data"] = xData;
					  option["series"][0]["data"] = yData;
					  
		          },
		          error: function (returndata) {
		          
		          }
	     		});

				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);
			}


			function showDayRecord(){
				var myChart = echarts.init(document.getElementById('RecordTable'));

				typeContent = '日统计';
	
				var colors = ['#5793f3', '#d14a61', '#675bba'];

				option = {
				    title: {
				        text: typeContent,
				        subtext: ''
				    },
				    tooltip: {
				        trigger: 'axis',
				     
				    },
				    legend: {
				        data: []
				    },
				    toolbox: {
				        show: true,
				        feature: {
				            dataZoom: {
				                yAxisIndex: 'none'
				            },
				            dataView: {
				                readOnly: false
				            },
				            magicType: {
				                type: ['line', 'bar']
				            },
				            restore: {},
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: ['2017-07-09', '2017-07-10', '2017-07-11', '2017-07-12', '2017-07-13', '2017-07-14', '2017-07-15']
				    },
				    yAxis: {
				        type: 'value',
				        axisLabel: {
				            formatter: '{value}人'
				        }
				    },
				    series: [{
				        name: '活跃用户数',
				        type: 'line',
				        data: [11, 11, 15, 13, 12, 13, 10],
				        markPoint: {
				            data: [{
				                type: 'max',
				                name: '最大值'
				            },
				            {
				                type: 'min',
				                name: '最小值'
				            }]
				        },
				        markLine: {
				            data: [{
				                type: 'average',
				                name: '平均值'
				            }]
				        }
				    },{
				        name: '激活用户数',
				        type: 'line',
				        data: [11, 11, 15, 13, 12, 13, 10],
				        markPoint: {
				            data: [{
				                type: 'max',
				                name: '最大值'
				            },
				            {
				                type: 'min',
				                name: '最小值'
				            }]
				        },
				        markLine: {
				            data: [{
				                type: 'average',
				                name: '平均值'
				            }]
				        }
				    }]
				};

			var formData = new FormData();
			formData.append("type","day");
			$.ajax({
		          url: '<%=request.getContextPath() %>/api/statistical/getReportDataByDay' ,
		          type: 'POST',
		          data:formData,
		          dataType: "json",  
		          async: false,
		          contentType: false,
		          processData: false,
		          beforeSend:function(){
				   //这里是开始执行方法
				  },
		          success: function (returndata) {
					  var info = returndata.data.items;
					  var xData = [];
					  var yData = [];
					  var y2Data = [];
					  for(var i=0;i<info.length;i++){
					  		xData.push(info[i].date);
					  		yData.push(info[i].showCount);
					  		y2Data.push(info[i].showActiveCount);
					  }
                      xData = xData.reverse();
                      yData = yData.reverse();
                      y2Data = y2Data.reverse();
					  option["xAxis"]["data"] = xData;
					  option["series"][0]["data"] = yData;
				   	  option["series"][1]["data"] = y2Data;
		          },
		          error: function (returndata) {
		          
		          }
	     		});
				
				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);
			}


		</script>
		
		

		<div class="btn-group">
		    <button type="button" class="btn btn-primary btn-lg" onclick="showRecord('hour')">小时</button>
		    &nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-primary btn-lg" onclick="showDayRecord()">天</button>
		    <!-- 
		    &nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-primary btn-lg" onclick="showRecord('week')">周</button>
   		    &nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-primary btn-lg" onclick="showRecord('month')">月</button>
   		     -->
   		    &nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-primary btn-lg" onclick="showRate()">活跃率</button>
   		    
   		    
   		    
		</div>
		
		
		<div class="row" id="dayRecord">
			<div id="RecordTable" style="width: 100%;height:500px;margin-top: 50px;"></div>
		</div>

		<div class="row" id="detailRecord" style="display:none">
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
										<th width="50">序号</th>
										<th width="50">机器人总数</th>
										<th width="100">在线机器人数</th>
										<th width="100">统计日期</th>
									</tr>
								</thead>
							</table>
							<f:page page="${resultPage}" url="./onlineDeviceManager"
								params="<%=request.getParameterMap()%>" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form class="data" method="post" name="modifyData" id="modifyData"
	enctype="multipart/form-data" action="">
	<input type="hidden" name="index" id="index" value=""> 
	<input type="hidden" name="id" id="id" value=""> 
	<div class="modal fade" id="myModal-data" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:900px;height: 1200px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">添加/修改专辑</h4>
				</div>
				<div class="modal-body">
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">专辑ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="albumId" id="albumId"
										type="text" data-rule="专辑ID:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">所属频道ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="channelId" id="channelId"
										type="text" data-rule="所属频道ID:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">专辑名称</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="name" id="name"
										type="text" data-rule="专辑名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">专辑图片</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="image" id="image"
										type="text" data-rule="专辑图片:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>

					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">简介</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="intro" id="intro"
										type="text" data-rule="专辑名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">状态</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="status" id="status"
										type="text" data-rule="专辑名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					
					
					<div id="divInput" class="form-group" style="height:35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">排序ID</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width:500px;">
									<input class="form-control" name="sortId" id="sortId"
										type="text" data-rule="专辑名称:required;"
										style="max-width:650px;width:100%;" />
								</div>
							</div>
						</div>
					</div>
					<div class="form-group" style="height:50px;">
						<label class="col-sm-2 control-label no-padding-right text-right">标签列表</label>
						<div class="col-sm-10">
							<div class="input-medium">
								<div class="input-group">
								<button class="btn btn-primary"  data-toggle="modal" data-target="#mymodal-data2" onclik="addTag()">添加标签</button>
								<input type="hidden" name="item" id="uItems" />
								</div>
							</div>
						</div>
					</div>
					
					<div class="form-group" style="height:50px;">
						<div class="tags tagSelected" style="border: none;width:auto;padding-left:0;width:650px;" id="albumTagList">
							<span class="tag-modal tag tag-info" style="display: none;">
								<span>模板</span>
								<button type="button" class="close">×</button>
							</span>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="updateData()">保存</button>

					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>


