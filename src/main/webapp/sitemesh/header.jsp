<%@ page language="java"  pageEncoding="utf-8"%>
<%@ page import="com.wechat.entity.User" %>
<%@ page import="com.wechat.util.RedisUtil"%>

<script>
function updatePassword(){
	var oldPassWord = $("#oldPassWord").val();
	var newPassWord = $("#newPassWord").val();
	var formData = new FormData();
	formData.append("oldPassWord",oldPassWord);
	formData.append("newPassWord",newPassWord);
	$("#updatePassword").modal("hide");
	$.ajax({
		url: '<%=request.getContextPath() %>/api/updatePassWord' ,
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
			window.location.href = '/wechat/user/login';
	      },
	      error: function (returndata) {
	      	
	      }
	 });

}

</script>
<!-- 修改密码弹窗 -->
<div class="modal fade" id="updatePassword" tabindex="-1" 
	role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:600px;height: 400px;">
		<div class="modal-content">
			<div class="modal-body">
				<div class="form-group" style="height:35px;width:80%">
					<label class="col-sm-2 control-label no-padding-right text-right">旧密码</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<input class="form-control" name="oldPassWord" id="oldPassWord"  type="text" data-rule="学生卡号:required;"style="max-width:650px;width:100%;"  />
							</div>
						</div>
					</div>
				</div>
				<div class="form-group" style="height:35px;width:80%">
					<label class="col-sm-2 control-label no-padding-right text-right">新密码</label>
						<div class="col-sm-9">
						<div class="input-medium">
							<div class="input-group">
								<input class="form-control" name="newPassWord" id="newPassWord"  type="text" data-rule="学生名字:required;"style="max-width:650px;width:100%;"  />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="updatePassword()">提交</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
		</div>
	</div>
</div>

<div class="navbar navbar-default" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small> 凡豆科技运营管理系统	 </small> 	</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						

						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="<%=request.getContextPath()%>/avatars/logo.png" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎光临,</small>
								
													<%=((User)RedisUtil.getUserByCookie(request)).getName()%></span>

								<i class="icon-caret-down"></i>							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="#" type="button"  data-toggle="modal" data-target="#updatePassword">
										<i class="icon-cog"></i>
									修改密码</a>
								</li>
								<li>
								<!-- 

									<a href="#">
										<i class="icon-user"></i>
										个人资料									</a>								</li>

								<li class="divider"></li>
 								-->
								<li>
									<a href="<%=request.getContextPath()%>/user/out">
										<i class="icon-off"></i>
										退出</a></li>
							</ul>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>
		