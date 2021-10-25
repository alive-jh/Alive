<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="http://echarts.baidu.com/dist/echarts.js"></script>
<script>
//初始化
$(document).ready( function () {

});
	function showRecord(type){
		var myChart = echarts.init(document.getElementById('RecordTable'));
		if(type == 'hour'){
			typeContent = '当前活跃用户';
		}else if(type == 'day'){
			typeContent = '按天统计';
		}else if(type == 'week'){
			typeContent = '按周统计';
		}else if(type == 'month'){
			typeContent = '按月统计';
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
			  for(var key in info){//遍历json对象的每个key/value对,p为key
						xData.push(key);
						yData.push(info[key].length);
				  }
			  option["xAxis"]["data"] = xData.reverse();
			  option["series"][0]["data"] = yData.reverse();
			  
          },
          error: function (returndata) {
          
          }
    		});

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	}

	
</script>
<div class="main-container-inner"><!-- /.main-container-inner -->
	<div class="main-content">
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb" style="line-height:40px;">
				<li>
					<i class="icon-home home-icon"></i>
					<a href="#">首页</a>							
				</li>			
			</ul><!-- .breadcrumb -->
		</div>
		<div id="chart">
			
		
		</div>
		<div class="row" id="dayRecord">
			<div id="RecordTable" style="width: 100%;height:500px;margin-top: 50px;"></div>
		</div>
	</div>
</div>

