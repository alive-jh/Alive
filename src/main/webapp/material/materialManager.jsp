<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.faceye.com/core"%>





<script>

function deleteMaterial(id){

	if(confirm('你确定要删除数据吗？'))
	{
		document.forms['materialForm'].action ="removeMaterial?materialId="+id;
		document.forms['materialForm'].submit();
	}
}


function editMaterial(id,type,keywordName,status,matchingrules)
{
	document.forms['materialForm'].materialId.value = id;
	document.forms['materialForm'].contentType.value = type;
	document.forms['materialForm'].keywordName.value = keywordName;
	$("input[name=status]:eq("+status+")").attr("checked",'checked');
	$('#sMatchingrules')[0].value = matchingrules;
}

function addKeyword()
{


	if(document.forms['materialForm'].keywordName.value == '')
	{
		alert('请输入关键词!');
		return;
	}
		$.ajax({
		
				type: "POST",
				data:"keywordName="+document.forms['materialForm'].keywordName.value,
				dataType: "json",
				url: "<%=request.getContextPath() %>/keyword/searchKeyword", 
				context: document.body, 
				beforeSend:function(){											
				},
				complete:function(){
					
					
				},
				success: function(data){

					if(data.data.status == 'ok')
					{
						
						document.forms['materialForm'].keywordId.value = data.data.keywordId;
						document.forms['materialForm'].submit();
						
					}
					else
					{
						if(confirm('关键词不存在,你要新增吗?'))
						{

							document.forms['materialForm'].submit();
						}
						
					}
					

				
				}

			});
}
</script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css">
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				
				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="main.htm">首页</a>
							</li>
							<li class="active">素材管理</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="button-group">
							<button class="btn btn-primary" onclick="window.location='<%=request.getContextPath() %>/material/toMaterial?type=0'">单图文回复</button>
							<button class="btn btn-info" onclick="window.location='<%=request.getContextPath() %>/material/toMaterial?type=1'">多图文回复</button>
						</div>
						<hr style="margin:8px 0;"/>
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<c:forEach items="${resultPage.items}" var="material">
								<div class="col-xs-3">
									<div class="mBox">
										<div class="mHead">
											<h5>${material.title}</h5>
											<span class="col-xs-4 col-lg-6 no-padding" style="margin-top: 6px;">${material.createDate}</span>
											<div class="col-xs-8 col-lg-6 no-padding align-right">
												<div class="col-xs-10 keyWBox no-padding" style="float:right;">
													<span class="keyWord red" style="display: inline-block;margin-top:3px;">${material.keywordName}</span>
													
													<c:if test="${material.keywordType ==0}">
														<button type="button" class="btn btn-info btn-sm keyWBtn"  onclick="editMaterial('${material.id}',${material.type},'','1','0')" data-toggle="modal" data-target="#keyWBtn">点击绑定关键词</button>
													</c:if>
												<c:if test="${material.keywordType ==1}">
													<button type="button" class="btn btn-info btn-sm keyWBtn"  onclick="editMaterial('${material.id}',${material.type},'${material.keywordName}','${material.keywordStatus}','${material.keywordMatchingRules}')" data-toggle="modal" data-target="#keyWBtn">修改</button>
												</c:if>

													
												</div>
											</div>
										</div>
										<div class="sBody">
											
											<c:if test="${material.logo == null}">
												<img src="<%=request.getContextPath() %>/wechatImages/${material.logo}" style="width:100%;"/>
											</c:if>
											<c:if test="${material.logo != null}">
												<img src="<%=request.getContextPath() %>/wechatImages/${material.logo}" class="big" style="width:80px;height:80px;"/>
											</c:if>
											<p class="mainText">${material.summary}</p>
										</div>
										<c:if test="${material.type ==1}">
										<c:forEach items="${material.materialList}" var="tempMaterial">
												<div class="mMesg">
													<div class="col-xs-8" class="mesgText">
														${tempMaterial.title}
													</div>
													<div class="col-xs-4 no-padding" class="mesgImg">
														<img src="<%=request.getContextPath() %>/wechatImages/${tempMaterial.logo}" style="width:100%;"/>
													</div>
													<div style="clear: both;"></div>
													<hr style="margin:8px 0;"/>
												</div>
												</c:forEach>
										</c:if>
										<div class="col-xs-12 sFooter">
											<div class="col-xs-6 align-center" style="border-right:1px solid #ccc;">
												<button type="button" class="btn btn-xs btn-info" title="编辑" onclick="window.location='toMaterial?materialId=${material.id}'">
													<i class="icon-pencil bigger-120"></i>
												</button>
											</div>
											<div class="col-xs-6 align-center">
												<button type="button" class="btn btn-xs btn-danger" title="删除" onclick="deleteMaterial('${material.id}')">
													<i class="icon-trash bigger-120"></i>
												</button>
											</div>
										</div>
										<div style="clear: both;"></div>
									</div><!-- /.sBox -->
								</div><!-- /.col-xs-4 -->
								</c:forEach>
								
								
								
								
							</div><!-- /.col-xs-12 -->
						</div><!-- /.row -->
						
						<div class="col-xs-12 " style="text-align: left;font-family:'微软雅黑';line-height: 40px;margin-top:20px;">
							<f:page page="${resultPage}" url="./materialManager" params="<%=request.getParameterMap()%>" />
							
						</div>

					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			
			<!-- 模态弹出窗内容 -->
			<form class="form-horizontal" role="form" name="materialForm" method="post" action ="<%=request.getContextPath() %>/material/saveKeywordByMaterialId">
			
			<input type="hidden" name="keywordId">
			<input type="hidden" name="materialId">
			<input type="hidden" name="contentType">
			
			<div class="modal  fade" id="keyWBtn" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">编辑关键词</h4>
						</div>
						<div class="modal-body">
							<div class="form-group" style="height:35px;">
								<div class="form-group" style="height:35px;">
									<label class="col-xs-3 control-label no-padding-right text-right">关键词</label>
										<div class="col-xs-9">
										<div class="input-medium">
											<div class="input-group">
												
													<input type="text" name ="keywordName" class="form-control input-medium" placeholder="请输入关键词" />
												
											</div>
										</div>
									</div>
								</div>
								
								<div class="form-group" style="height:35px;">
									<label class="col-xs-3 control-label no-padding-right text-right">匹配规则</label>
										<div class="col-xs-9">
										<div class="input-medium">
											<div class="input-group">
												<select id="sMatchingrules" name="matchingRules" class="form-control input-medium">
													<option value = "0">精准匹配</option>
													<option value = "1">模糊匹配</option>
												</select>
											</div>
										</div>
									</div>
								</div>
								
								<div class="form-group" style="height:35px;">
									<label class="col-sm-3 control-label no-padding-right text-right">是否启用</label>
									<div class="col-sm-9" style="line-height:26px;">
										<label class="inline">
											<input name="status" value="0" checked type="radio" class="ace" />
											<span class="lbl">是</span>
										</label>

										&nbsp; &nbsp; &nbsp;
										<label class="inline">
											<input name="status"  value="1" type="radio" class="ace" />
											<span class="lbl">否</span>
										</label>
									</div>
								</div>
							</div>
							<div style="clear: both;"> </div>		

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="addKeyword()">提交</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>
					</div>
				</div>
			</div>
</form>
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

	
