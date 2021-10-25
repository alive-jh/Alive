<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>凡豆科技运营管理系统</title>
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
	<page:applyDecorator page="/sitemesh/header.jsp" name="header" />
	
	
	
<div id="divGlobal" style="min-height:500px">
<table style="width:100%">
<tr style="vertical-align:top">
<td style="padding:0;"><!--左侧功能菜单--> 
  <div id="divMainLeft">  
     <page:applyDecorator page="/sitemesh/menu.jsp" name="menuer" />
  </div>
 </td>
<td style="width:10000px;padding:0;background-color: #fff;">
<!--右块内容区--> 
  <div id="divMainRight">
   <decorator:body />   
  </div>
</td>
</tr>
</table>


</div>
<page:applyDecorator page="/sitemesh/footer.jsp" name="footer" />
</body>
</html>

