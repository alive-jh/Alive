<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


		<!-- basic styles -->
		<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/datepicker.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/zTreeStyle.css"/>
		<!-- fonts -->

		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

		<!-- ace styles -->

		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="<%=request.getContextPath()%>/js/ace-extra.min.js"></script>

	

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				
				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="main.htm">首页</a>
							</li>
							<li class="active">控制台</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content no-padding">
						<div>
							<div class="zTreeDemoBackground left col-md-6">
								<div class="treeTitle" style="background-color:#6289FE;display: inline-block;width:100%;margin-top:10px;line-height: 30px;padding-left: 10px;color:#fff;">
									<h4>系统模块</h4>
								</div>
								<ul id="departmentTree" class="ztree" style="border: 1px solid #C5CDE4;"> </ul>
							</div>
							<div class="departementEdit right col-md-6">
								<div class="treeTitle" style="background-color:#6289FE;display: inline-block;width:100%;margin-top:10px;line-height: 30px;padding-left: 10px;color:#fff;">
									<h4>模块信息</h4>
								</div>
								<div class="editContent" style="border: 1px solid #C5CDE4;padding-top:10px;">
									<form  name="moduleForm" action ="saveModule" method="post" class="form-horizontal" role="form">
											<input type="hidden" id="sParentId" name="parentId" value="${module.parentId}">
											<div class="form-group col-xs-12">
											<label class="col-sm-3 control-label no-padding-right">上级菜单<span class="red">*</span></label>
												<div class="col-sm-9">
												<div class="input-medium" style="width:80%;">
													<div class="input-group" style="width:100%;">
														<input class="form-control" name="parentName" 
														id="sParentName"
														value="${module.tempName}" type="text" style="max-width: 100%;width:100%;"  />
													</div>
												</div>
											</div>
										</div>
										
										<div class="form-group col-xs-12">
											<label class="col-sm-3 control-label no-padding-right">名称<span class="red">*</span></label>
												<div class="col-sm-9">
												<div class="input-medium" style="width:80%;">
													<div class="input-group" style="width:100%;">
														<input class="form-control" name="name" id="sName" value="${module.name}" type="text" style="max-width: 100%;width:100%;"  />
													</div>
												</div>
											</div>
										</div>
										
										<div class="space-4"> </div>
										<div class="form-group col-xs-12">
											<label class="col-sm-3 control-label no-padding-right">URL<span class="red">*</span></label>
												<div class="col-sm-9">
												<div class="input-medium" style="width:80%;">
													<div class="input-group" style="width:100%;">
														<input class="form-control input-medium"  name="url" id="sUrl" value="${module.url}"style="max-width: 100%;width:100%;"  type="text" placeholder="请输入序号" />
													</div>
												</div>
											</div>
										</div>
										
										<div class="space-4"> </div>
										<div class="form-group  col-xs-12">
											<label class="col-sm-3 control-label no-padding-right">排序</label>
												<div class="col-sm-9">
												<div class="input-medium" style="width:80%;">
													<div class="input-group" style="width:100%;">
														<input class="form-control input-medium"  name="sort" id="sSort"value="${module.sort}"style="max-width: 100%;width:100%;" type="text" placeholder="" />
													</div>
												</div>
											</div>
										</div>
										
										
										<div class="clearfix form-actions col-xs-12" style="margin:0;">
											<div class="col-xs-12 align-center">
												<button class="btn btn-info" type="submit">
													增加
												</button>
			
												&nbsp; &nbsp;
												<button class="btn btn-info" type=""submit"">
													修改
												</button>
			
												&nbsp; &nbsp;
												<button class="btn btn-info" type="button">
													删除
												</button>
			
												&nbsp; &nbsp;
												<button class="btn" type="reset">
													取消
												</button>
											</div>
										</div>
								
										</form><!-- /.form-horizontal -->
										
									<div style="clear: both;"></div>
								</div>
							</div>
							<div style="clear: both;"></div>
						</div>

					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div>

		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=request.getContextPath()%>/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>

	
		<script src="<%=request.getContextPath()%>/js/date-time/bootstrap-datepicker.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/ztree/jquery.ztree.core-3.5.min.js"></script>
		
		<!-- 导航条代码 -->
		
		<!-- inline scripts related to this page -->
		
		<script type="text/javascript">
			
		</script>
		
		
		<SCRIPT type="text/javascript">
			<!--
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =[${jsonStr}];

		$(document).ready(function(){
			$.fn.zTree.init($("#departmentTree"), setting, zNodes);
		});
		
		var log, className = "dark";
		function beforeClick(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			return (treeNode.click != false);
		}
		function onClick(event,treeId, treeNode) {
			alert('aa');
			showLog(treeNode.name,treeNode.id,treeNode.moduleUrl);
		}		
		function showLog(nodeName,nodeId,moduleUrl) {
			
			

			$('#sName').val(nodeName);
			$('#sSort').val(nodeId);
			$('#sUrl').val(moduleUrl);
		}


		function editModule(id)
		{
			$.ajax({
			type: "POST",
			data:"moduleId="+id,
			dataType: "json",
			url: "<%=request.getContextPath()%>/module/moduleManagerView", 
			context: document.body, 
			beforeSend:function(){},
			complete:function(){},
			success: function(data){

				document.forms['moduleForm'].name.value = data.infoList[0].name;
				document.forms['moduleForm'].parentId.value = data.infoList[0].parentId;
				document.forms['moduleForm'].sort.value = data.infoList[0].sort;
				document.forms['moduleForm'].id.value = data.infoList[0].id;
				
			}

		});
		}
		//-->
	</SCRIPT>
		

</body>
</html>

