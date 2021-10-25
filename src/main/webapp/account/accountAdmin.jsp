<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>





<script>

function checkKeyword(id){

	
	document.forms['materialForm'].keywordId.value = id;
	
	
}


function updateKeyword(id){

	
	
	if(document.forms['materialForm'].keywordId.value == '')
	{
		alert('请选择关键词');
		return;
	}
	else
	{

	}
		document.forms['materialForm'].action ="updateKeyword?accountId="+id+"&keywordId="+document.forms['materialForm'].keywordId.value;
		document.forms['materialForm'].submit();
	
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
							<li class="active">关注推送</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="button-group">
						<span class="keyWord red" style="display: inline-block;margin-top:3px;">
													
								<c:if test="${account.keywordId ==0}">暂无绑定关键词!</c:if>
								<c:if test="${account.keywordId !=0}">绑定关键词:&nbsp;${keywordName}</c:if>
								
								
								
						</span>
													&nbsp;&nbsp;&nbsp;
							<button class="btn btn-primary" data-toggle="modal" data-target="#mymodal-data" >选择关键词</button>
					
						</div>
						
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
			
			<!-- 模态弹出窗内容 -->
			<form class="form-horizontal" role="form" name="materialForm" method="post" action ="<%=request.getContextPath() %>/material/saveKeywordByMaterialId">
			
			<input type="hidden" name="keywordId">
			
			<div class="modal  fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
							<h4 class="modal-title">编辑关键词</h4>
						</div>
						<div class="modal-body">
							<div class="table-responsive" style="max-height: 350px;overflow: auto;">
								<table  class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th class="center">
												<label>
													
												</label>
											</th>
											<th>关键词</th>
											<th width="20%">类型</th>
											<th width="70%">回复内容</th>
										</tr>
									</thead>

									<tbody>
									
										<c:forEach items="${resultPage.items}" var="keyword">
										<tr>
											<td class="center">
													<label>
														
														<input type="radio" name="checkId" value="${keyword[0]}" onclick="checkKeyword(this.value)"class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
											<td style="text-align: center;">${keyword[1]}</td>
											<td>
											<c:if test="${keyword[5] == 0}">文本回复</c:if>
											<c:if test="${keyword[5] == 1}">单图文</c:if>
											<c:if test="${keyword[5] == 2}">多图文</c:if>
										
											</td>
											<td style="max-width:300px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
											<c:if test="${keyword[5] == 0}">${keyword[2]}</c:if>
											<c:if test="${keyword[5] == 1}">单图文内容,请到图文回复管理查看</c:if>
											<c:if test="${keyword[5] == 2}">多图文内容,请到图文回复管理查看</c:if>
												
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								
							</div><!-- /.table-responsive -->
							<div class="modal-footer">
							<button type="button" onclick="updateKeyword('${account.id}')"class="btn btn-primary">保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
				</div>
			</div>
</form>
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

	
