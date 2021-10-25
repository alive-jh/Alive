<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%> 
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>onlineDeviceView</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome.min.css" />
		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome-ie7.min.css" />
		<![endif]-->
		
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/fontsgoogleapis.css" />
		

		<!-- page specific plugin styles（jquery验证框架） -->	
		

		<!-- page specific plugin styles（插件css样式） -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/datepicker.css" /><!-- 日历插件样式 -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/stepsStyle.css" /><!-- 审核状态样式 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/documentList.css" type="text/css"><!-- 文件列表样式 -->
		
		<!-- fonts （字体）

		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />
			-->
		<!-- ace styles -->

		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page （行内样式）-->

		<!-- ace settings handler -->

		<script src="<%=request.getContextPath()%>/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=request.getContextPath()%>/js/html5shiv.js"></script>
		<script src="<%=request.getContextPath()%>/js/respond.min.js"></script>
		<![endif]--><script src="<%=request.getContextPath()%>/js/page.js"></script>
	</head>
		<!-- basic scripts （基本脚本代码）-->

		<!--[if !IE]> -->

		<!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>-->

		<!-- <![endif]-->

		<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=request.getContextPath()%>/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='<%=request.getContextPath()%>/js/jquery-1.10.2.min.js'>"+"<"+"script>");
		</script>
		<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=request.getContextPath()%>/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
		</script>
		<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/typeahead-bs2.min.js"></script>
		
		

		<!-- page specific plugin scripts （页面插件脚本）-->
		<script src="<%=request.getContextPath()%>/js/date-time/bootstrap-datepicker.min.js"></script><!-- 日历插件脚本 -->
		<script src="<%=request.getContextPath()%>/js/x-editable/bootstrap-editable.min.js"></script><!-- 图片编辑******* -->
		<script src="<%=request.getContextPath()%>/js/x-editable/ace-editable.min.js"></script><!-- 图片编辑******* -->
		<script src="<%=request.getContextPath()%>/js/jquery.gritter.min.js"></script><!-- 图片编辑******* -->
		<script src="<%=request.getContextPath()%>/js/jquery.maskedinput.min.js"></script>
		
		<!--[if lte IE 8]>
		  <script src="<%=request.getContextPath()%>/js/excanvas.min.js"></script>
		<![endif]-->

		<script src="<%=request.getContextPath()%>/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.ui.touch-punch.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.slimscroll.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.easy-pie-chart.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.sparkline.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/flot/jquery.flot.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/flot/jquery.flot.pie.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/flot/jquery.flot.resize.min.js"></script>
		
		

		<!-- ace scripts -->

		<script src="<%=request.getContextPath()%>/js/ace-elements.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/ace.min.js"></script>

		<!-- inline scripts related to this page （页面内脚本）-->
		<script type="text/javascript">
			jQuery(function($) {
				$('.date-picker').datepicker({autoclose:true}).next().on(ace.click_event, function(){
					$(this).prev().focus();
				});
				
			});
			
			
			Array.prototype.remove=function(dx)
			{
			    if(isNaN(dx)||dx>this.length){return false;}
			    for(var i=0,n=0;i<this.length;i++)
			    {
			        if(this[i]!=this[dx])
			        {
			            this[n++]=this[i];
			        }
			    }
			    this.length-=1;
			};
			
		</script><!--日历脚本-->
		
		<script type="text/javascript">
			jQuery(function($) {
				
				$('table th input:checkbox').on('click' , function(){
					var that = this;
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});
						
				});
				$('table tr > td:first-child input:checkbox').on('click',function(){
					var that=this;
					var checked=true;
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						if(this.checked ==false)
						{checked=false;}
					});
					if(checked==true){
						$(this).closest('table').find('th input:checkbox')[0].checked=true;
					}else{
						$(this).closest('table').find('th input:checkbox')[0].checked=false;
					}
				})
			
			})
		</script><!-- 全选复选框脚本 -->
		
		
		<script type="text/javascript">
			jQuery(function($) {
			
				//editables on first profile page
				$.fn.editable.defaults.mode = 'inline';
				$.fn.editableform.loading = "<div class='editableform-loading'><i class='light-blue icon-2x icon-spinner icon-spin'></i></div>";
			    $.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="icon-ok icon-white"></i></button>'+
			                                '<button type="button" class="btn editable-cancel"><i class="icon-remove"></i></button>';    
				
				// *** editable avatar *** //
				try {//ie8 throws some harmless exception, so let's catch it
			
					//it seems that editable plugin calls appendChild, and as Image doesn't have it, it causes errors on IE at unpredicted points
					//so let's have a fake appendChild for it!
					if( /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()) ) Image.prototype.appendChild = function(el){}
			
					var last_gritter
					$('#avatar').editable({
						type: 'image',
						name: 'avatar',
						value: null,
						image: {
							//specify ace file input plugin's options here
							btn_choose: '修改图片',
							droppable: true,
							/**
							//this will override the default before_change that only accepts image files
							before_change: function(files, dropped) {
								return true;
							},
							*/
			
							//and a few extra ones here
							name: 'avatar',//put the field name here as well, will be used inside the custom plugin
							max_size: 110000,//~100Kb
							on_error : function(code) {//on_error function will be called when the selected file has a problem
								if(last_gritter) $.gritter.remove(last_gritter);
								if(code == 1) {//file format error
									last_gritter = $.gritter.add({
										title: 'File is not an image!',
										text: 'Please choose a jpg|gif|png image!',
										class_name: 'gritter-error gritter-center'
									});
								} else if(code == 2) {//file size rror
									last_gritter = $.gritter.add({
										title: 'File too big!',
										text: 'Image size should not exceed 100Kb!',
										class_name: 'gritter-error gritter-center'
									});
								}
								else {//other error
								}
							},
							on_success : function() {
								$.gritter.removeAll();
							}
						},
					    url: function(params) {
							// ***UPDATE AVATAR HERE*** //
							//You can replace the contents of this function with examples/profile-avatar-update.js for actual upload
			
			
							var deferred = new $.Deferred
			
							//if value is empty, means no valid files were selected
							//but it may still be submitted by the plugin, because "" (empty string) is different from previous non-empty value whatever it was
							//so we return just here to prevent problems
							var value = $('#avatar').next().find('input[type=hidden]:eq(0)').val();
							if(!value || value.length == 0) {
								deferred.resolve();
								return deferred.promise();
							}
			
			
							//dummy upload
							setTimeout(function(){
								if("FileReader" in window) {
									//for browsers that have a thumbnail of selected image
									var thumb = $('#avatar').next().find('img').data('thumb');
									if(thumb) $('#avatar').get(0).src = thumb;
								}
								
								deferred.resolve({'status':'OK'});
			
								if(last_gritter) $.gritter.remove(last_gritter);
								last_gritter = $.gritter.add({
									title: '头像上传成功！',
									text: '',
									class_name: 'gritter-info gritter-center'
								});
								
							 } , parseInt(Math.random() * 800 + 800))
			
							return deferred.promise();
						},
						
						success: function(response, newValue) {
						}
					})
				}catch(e) {}
				
				
			
				//another option is using modals
				$('#avatar2').on('click', function(){
					var modal = 
					'<div class="modal hide fade">\
						<div class="modal-header">\
							<button type="button" class="close" data-dismiss="modal">&times;</button>\
							<h4 class="blue">Change Avatar</h4>\
						</div>\
						\
						<form class="no-margin">\
						<div class="modal-body">\
							<div class="space-4"></div>\
							<div style="width:75%;margin-left:12%;"><input type="file" name="file-input" /></div>\
						</div>\
						\
						<div class="modal-footer center">\
							<button type="submit" class="btn btn-small btn-success"><i class="icon-ok"></i> Submit</button>\
							<button type="button" class="btn btn-small" data-dismiss="modal"><i class="icon-remove"></i> Cancel</button>\
						</div>\
						</form>\
					</div>';
					
					
					var modal = $(modal);
					modal.modal("show").on("hidden", function(){
						modal.remove();
					});
			
					var working = false;
			
					var form = modal.find('form:eq(0)');
					var file = form.find('input[type=file]').eq(0);
					file.ace_file_input({
						style:'well',
						btn_choose:'Click to choose new avatar',
						btn_change:null,
						no_icon:'icon-picture',
						thumbnail:'small',
						before_remove: function() {
							//don't remove/reset files while being uploaded
							return !working;
						},
						before_change: function(files, dropped) {
							var file = files[0];
							if(typeof file === "string") {
								//file is just a file name here (in browsers that don't support FileReader API)
								if(! (/\.(jpe?g|png|gif)$/i).test(file) ) return false;
							}
							else {//file is a File object
								var type = $.trim(file.type);
								if( ( type.length > 0 && ! (/^image\/(jpe?g|png|gif)$/i).test(type) )
										|| ( type.length == 0 && ! (/\.(jpe?g|png|gif)$/i).test(file.name) )//for android default browser!
									) return false;
			
								if( file.size > 110000 ) {//~100Kb
									return false;
								}
							}
			
							return true;
						}
					});
			
					form.on('submit', function(){
						if(!file.data('ace_input_files')) return false;
						
						file.ace_file_input('disable');
						form.find('button').attr('disabled', 'disabled');
						form.find('.modal-body').append("<div class='center'><i class='icon-spinner icon-spin bigger-150 orange'></i></div>");
						
						var deferred = new $.Deferred;
						working = true;
						deferred.done(function() {
							form.find('button').removeAttr('disabled');
							form.find('input[type=file]').ace_file_input('enable');
							form.find('.modal-body > :last-child').remove();
							
							modal.modal("hide");
			
							var thumb = file.next().find('img').data('thumb');
							if(thumb) $('#avatar2').get(0).src = thumb;
			
							working = false;
						});
						
						
						setTimeout(function(){
							deferred.resolve();
						} , parseInt(Math.random() * 800 + 800));
			
						return false;
					});
							
				});
			
				
			
				//////////////////////////////
				$('#profile-feed-1').slimScroll({
				height: '250px',
				alwaysVisible : true
				});
			
				$('.profile-social-links > a').tooltip();
			
				$('.easy-pie-chart.percentage').each(function(){
				var barColor = $(this).data('color') || '#555';
				var trackColor = '#E2E2E2';
				var size = parseInt($(this).data('size')) || 72;
				$(this).easyPieChart({
					barColor: barColor,
					trackColor: trackColor,
					scaleColor: false,
					lineCap: 'butt',
					lineWidth: parseInt(size/10),
					animate:false,
					size: size
				}).css('color', barColor);
				});
			  
				///////////////////////////////////////////
			
				//show the user info on right or left depending on its position
				$('#user-profile-2 .memberdiv').on('mouseenter', function(){
					var $this = $(this);
					var $parent = $this.closest('.tab-pane');
			
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $this.offset();
					var w2 = $this.width();
			
					var place = 'left';
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) place = 'right';
					
					$this.find('.popover').removeClass('right left').addClass(place);
				}).on('click', function() {
					return false;
				});
			
			
				///////////////////////////////////////////
				$('#user-profile-3')
				.find('input[type=file]').ace_file_input({
					style:'well',
					btn_choose:'Change avatar',
					btn_change:null,
					no_icon:'icon-picture',
					thumbnail:'large',
					droppable:true,
					before_change: function(files, dropped) {
						var file = files[0];
						if(typeof file === "string") {//files is just a file name here (in browsers that don't support FileReader API)
							if(! (/\.(jpe?g|png|gif)$/i).test(file) ) return false;
						}
						else {//file is a File object
							var type = $.trim(file.type);
							if( ( type.length > 0 && ! (/^image\/(jpe?g|png|gif)$/i).test(type) )
									|| ( type.length == 0 && ! (/\.(jpe?g|png|gif)$/i).test(file.name) )//for android default browser!
								) return false;
			
							if( file.size > 110000 ) {//~100Kb
								return false;
							}
						}
			
						return true;
					}
				})
				.end().find('button[type=reset]').on(ace.click_event, function(){
					$('#user-profile-3 input[type=file]').ace_file_input('reset_input');
				})
				.end().find('.date-picker').datepicker().next().on(ace.click_event, function(){
					$(this).prev().focus();
				})
				$('.input-mask-phone').mask('(999) 999-9999');
			
			
			
				////////////////////
				//change profile
				$('[data-toggle="buttons"] .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					$('.user-profile').parent().addClass('hide');
					$('#user-profile-'+which).parent().removeClass('hide');
				});
			});
		</script><!-- 图片编辑 -->

<body style='background-color:#fff;'>
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
<page:applyDecorator page="/sitemesh/footer.jsp" name="footer" />
</body>
</html>

