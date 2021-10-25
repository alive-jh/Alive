<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/js/validator/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/validator/local/zh_CN.js"></script>

<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="main.htm">首页</a>							</li>
							<li class="active">系统管理</li>
							<li class="active">修改密码</li>
						</ul><!-- .breadcrumb -->

						
					</div>

					<div class="page-content">
						<div class="row">
						<form class="form-horizontal" method="post" name="pwdForm" action="<%=request.getContextPath()%>/user/updateUserPwd">
												

							<div class="tab-content profile-edit-tab-content">
								
								<form>						
								<div id="edit-basic" class="tab-pane in active">
									<h4 class="header blue bolder smaller">修改密码</h4>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right">用户名</label>
										<label class="col-sm-9 control-label align-left">${user.name}</label>
										</div>
									</div>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right">原密码</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input class="input-medium" name="oldPwd" type="password" data-rule="原始密码: required;remote[getUserPwd?oldPwd&userId=${user.id}]"/ style="width:310px;"/>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right">新密码</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input class="input-medium" name="password" type="password" data-rule="新密码:required;password;" data-ok="新密码可以使用"style="width:310px;"/>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-group" style="height:35px;">
										<label class="col-sm-3 control-label no-padding-right">确认密码</label>
											<div class="col-sm-9">
											<div class="input-medium">
												<div class="input-group">
													<input class="input-medium" name="newPwdAgain" type="password" data-rule="确认密码: required;match(password);" data-ok="点击保存完成密码修改" style="width:310px;"/>
												</div>
											</div>
										</div>
									</div>
								</div><!-- /.tab-pane-->	
										
								<div class="clearfix form-actions">
									<div class="col-md-offset-3 col-md-9">
										<button class="btn btn-info" type="submit">
											<i class="icon-ok bigger-110"></i>
											保存
										</button>
	
										&nbsp; &nbsp;
										<button class="btn" type="reset">
											<i class="icon-undo bigger-110"></i>
											重置
										</button>
									</div>
								</div>
							</form>
							</div><!-- /.tab-content -->
						
						</form><!-- /.form-horizontal -->
						</div>

					</div><!-- /.page-content -->
				</div><!-- /.main-content -->