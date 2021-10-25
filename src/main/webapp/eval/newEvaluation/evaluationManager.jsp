<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validator/local/zh_CN.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/js/uploadify/uploadify.css" type="text/css"></link>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/uploadify/jquery.uploadify-3.1.min.js"></script>
<script type="text/javascript" src="http://www.jfinal.com/assets/jquery_form/jquery.form.min.js"></script>

<style>
.tbody a {
	height: 24px;
	line-height: 21px;
	padding: 0 11px;
	background: #ffffff;
	border: 1px #949494 solid;
	border-radius: 3px;
	/* color: #fff; */
	display: inline-block;
	text-decoration: none;
	font-size: 12px;
	outline: none;
	color: #000000;
}

.tbody button {
	height: 24px;
	line-height: 21px;
	padding: 0 11px;
	background: #ffffff;
	border: 1px #949494 solid;
	border-radius: 3px;
	/* color: #fff; */
	display: inline-block;
	text-decoration: none;
	font-size: 12px;
	outline: none;
}

.tbody .answer div {
	margin: 10px;
}

.glyphicon.glyphicon-ok.glyphicon-ok-green {
	color: green;
	left: 10px;
}

.tbody .answer div img {
	width: 50px;
	height: 50px;
}

.tbody .answer div label {
	margin-right: 10px;
}

.edit {
	display: inline-block;
	position: absolute;
	right: 20px;
}

.edit2 {
	display: inline-block;
	float: right;
}

.question-audio {
	height: 50px;
	margin-bottom: 50px;
}

.add-option {
	text-align: center;
}

.questionFile {
	display: none;
}

.navs{
	margin-right: 20px;
	float: left;
	line-height: 37px;
}
</style>

<div class="main-content">
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed')
			} catch (e) {
			}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="icon-home home-icon"></i>
				<a href="main.htm">首页</a>
			</li>
			<li class="active">知识库</li>
			<li class="active">我的知识库</li>
		</ul>
		<!-- .breadcrumb -->


	</div>

	<div class="page-content">
		<div class="page-header col-xs-12">
		
		<div class="navs">
			<button class="btn btn-purple" type="button" data-toggle="modal" data-target="#mymodal-data">添加问题</button>
		</div>
			
		<div class="navs">
			<label style="font-size: 16px">题目等级</label>
			<select id="evaluationLevel" class="sel-lg">
			<c:forEach var="label" items="${labels }">
				<option id="${label[0] }">${label[1] }</option>
			</c:forEach>
			</select>
		</div>
		
		<div class="navs">
			<label style="font-size: 16px">题目维度</label>
			<select id="evaluationLevel" class="sel-lg">
				<option id="0">全部</option>
				
				<c:forEach var="type" items="${questionTypes }">
					<option id="${type[0] }">${type[1] }</option>
				</c:forEach>
			</select>
		</div>
			
		</div>
		
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="row">
					<div class="col-xs-12">
						<div class="table-responsive" style="text-align: right;">
							<table id="articleListTab" class="table table-striped table-bordered table-hover" style="text-align: left;">
								<thead>
									<tr>
										<th width="50%">问题</th>
										<th width="50%">答案</th>
									</tr>
								</thead>
								<tbody class="tbody">
									<tr>
										<td class="question">
											<span>
												听：he is lonely
												<button>播放</button>
												<div class="edit2">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</span>
										</td>
										<td class="answer">
											<div>
												<label>A.</label>
												<button>播放</button>
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
											<div>
												<label>B.</label>
												<button>播放</button>
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
											<div>
												<label>C.</label>
												<button>播放</button>
												<span class="glyphicon glyphicon-ok glyphicon-ok-green"></span>
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
											<div>
												<label>D.</label>
												<button>播放</button>
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
										</td>
									</tr>

									<tr>
										<td class="question">
											<span>
												听：he is lonely
												<button>播放</button>
												<div class="edit2">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</span>
										</td>
										<td class="answer">
											<div>
												<label>A.</label>
												<span>播放</span>
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
											<div>
												<label>B.</label>
												<span>播放</span>
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
											<div>
												<label>C.</label>
												<span>播放</span>
												<span class="glyphicon glyphicon-ok glyphicon-ok-green"></span>
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
											<div>
												<label>D.</label>
												<span>播放</span>
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
										</td>
									</tr>

									<tr>
										<td class="question">
											<span>
												听：he is lonely
												<button>播放</button>
												<div class="edit2">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</span>
										</td>
										<td class="answer">
											<div>
												<label>A.</label>
												<img alt="" src="http://source.fandoutech.com.cn/1517298993808.jpg">
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
											<div>
												<label>B.</label>
												<img alt="" src="http://source.fandoutech.com.cn/1517298993808.jpg">
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
											<div>
												<label>C.</label>
												<img alt="" src="http://source.fandoutech.com.cn/1517298993808.jpg">
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
												<span class="glyphicon glyphicon-ok glyphicon-ok-green"></span>
											</div>
											<div>
												<label>D.</label>
												<img alt="" src="http://source.fandoutech.com.cn/1517298993808.jpg">
												<div class="edit">
													<button>编辑</button>
													<button>删除</button>
												</div>
											</div>
										</td>

									</tr>


								</tbody>


							</table>

						</div>
						<!-- /.table-responsive -->
					</div>
					<!-- /.col-xs-12 -->
				</div>
				<!-- /row -->
			</div>
			<!-- /.col-xs-12 -->
		</div>
		<!-- /.row -->

		<audio id="audio-controller" src=""></audio>

	</div>
	<!-- /.page-content -->
	<div style="text-align: right;padding-right: 30px;">
		<nav id="page-data" aria-label="Page navigation">
			<ul class="pagination">
				<li>
					<a href="#" aria-label="Previous">
						<span aria-hidden="false">&laquo;</span>
					</a>
				</li>
				<li>
					<a href="#">1</a>
				</li>
				<li>
					<a href="#">2</a>
				</li>
				<li>
					<a href="#">3</a>
				</li>
				<li>
					<a href="#">4</a>
				</li>
				<li>
					<a href="#">5</a>
				</li>
				<li>
					<a href="#" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</ul>
		</nav>
	</div>

</div>
<!-- /.main-content -->
</div>
<!-- /.main-container-inner -->
<!-- 模态弹出窗内容 -->
<form id="question-form" action="/wechat/h5/evaluation/saveQuestion" method="post" enctype="multipart/form-data">
	<div class="modal fade" id="mymodal-data" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 1100px; height: 1000px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
						<span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">添加/修改问题</h4>
				</div>
				<div class="modal-body" style="height: 300px;">

					<input id="questionLevel" name="level" type="hidden" value="1">
					<div id="divAccount" class="form-group" style="height: 35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">
							<span id="name1"></span>
							问题描述:
						</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width: 710px;">
									<input class="form-control" name="questionText" id="questionText" type="text" style="max-width: 650px; width: 100%;" />
								</div>
							</div>
						</div>
					</div>
					<div id="divAccount" class="form-group" style="height: 35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">
							<span id="name1"></span>
							问题类型
						</label>
						<div class="col-sm-9">
							<div class="input-medium" style="display: inline-block;">
								<div class="input-group" style="width: 710px;">
									<select id="questionType" style="max-width: 650px; width: 100%;">
										<option id="1" selected="selected">文本</option>
										<option id="2">音频</option>
									</select>
								</div>

								<div class="questionFile">
									<br>
									<br>
									<input type="file" name="file" id="file">
									<br>
									<br>
								</div>

							</div>

						</div>

					</div>

					<div id="divAccount" class="form-group" style="height: 35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">
							<span id="name1"></span>
							分值
						</label>
						<div class="col-sm-9">
							<div class="input-medium" style="display: inline-block;">
								<div class="input-group" style="width: 710px;">
									<select id="scoreSel" style="max-width: 650px; width: 100%;">
										<option id="1" selected="selected">5分</option>
										<option id="2">10分</option>
										<option id="3">15分</option>
										<option id="4">20分</option>
									</select>
								</div>

								<input id="score" name="score" type="hidden" value="5">
							</div>
						</div>

					</div>

					<div id="divAccount" class="form-group" style="height: 35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">
							<span id="name1"></span>
							所属维度
						</label>
						<div class="col-sm-9">
							<div class="input-medium" style="display: inline-block;">
								<div class="input-group" style="width: 710px;">
									<select id="question_weidu" style="max-width: 650px; width: 100%;">
										<option id="187" selected="selected">词汇量</option>
									</select>
								</div>

								<input id="questionWeidu" name="questionWeidu" type="hidden" value="5">
							</div>
						</div>

					</div>


				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">保存</button>

					<button class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>
<form id="option-form" action="/wechat/h5/evaluation/saveOption" method="post" enctype="multipart/form-data">
	<div class="modal fade" id="mymodal-data2" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 1100px; height: 1000px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
						<span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">添加答案选项</h4>
				</div>
				<div class="modal-body" style="height: 300px;">

					<input id="level" name="level" type="hidden" value="1">
					<div id="divAccount" class="form-group" style="height: 35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">
							<span id="name1"></span>
							答案文本:
						</label>
						<div class="col-sm-9">
							<div class="input-medium">
								<div class="input-group" style="width: 710px;">
									<input class="form-control" name="optionText" id="optionText" type="text" style="max-width: 650px; width: 100%;" />
								</div>
							</div>
						</div>
					</div>
					<div id="divAccount" class="form-group" style="height: 35px;">
						<label class="col-sm-2 control-label no-padding-right text-right">
							<span id="name1"></span>
							答案类型
						</label>
						<div class="col-sm-9">
							<div class="input-medium" style="display: inline-block;">
								<div class="input-group" style="width: 710px;">
									<select id="answerType" style="max-width: 650px; width: 100%;">
										<option id="1" selected="selected">文本</option>
										<option id="2">音频</option>
										<option id="3">图片</option>
									</select>
								</div>

								<div>
									<br>
									<br>
									<input type="file" name="file" id="file">
									<br>
									<br>
								</div>

								<div>
									<br>
									<br>
									<span>正确答案 </span>
									<input type="checkbox" name="isCorrect">
									<br>
									<br>
								</div>

								<input type="hidden" id="questionId" name="questionId">

								<input type="hidden" id="optionType" name="optionType" value="1">
							</div>

						</div>

					</div>

				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">保存</button>

					<button class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>
</div>
<!-- /.main-container -->
<script type="text/javascript" src="<%=request.getContextPath() %>/eval/newEvaluation/evaluation.js"></script>