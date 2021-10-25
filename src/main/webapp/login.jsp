<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String path = request.getContextPath(); %><!DOCTYPE html>
<html lang="en" style="overflow-y: hidden;min-width:1024px;">
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

		<!-- page specific plugin styles -->
		
		<!-- ace styles -->

		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace.min.css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace-rtl.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=request.getContextPath()%>/js/html5shiv.js"></script>
		<script src="<%=request.getContextPath()%>/js/respond.min.js"></script>
		<![endif]-->
		<style>
		
				input[name="account"]:-webkit-autofill {
				    -webkit-box-shadow: 0 0 0px 1000px #2EFF6E inset !important;
				}
				input[name="password"]:-webkit-autofill {
				    -webkit-box-shadow: 0 0 0px 1000px #FDA728 inset !important;
				}
				
			body,{
				
				background: url('<%=request.getContextPath()%>/images/bgImage.jpg') no-repeat center;
				background-size:100% 100%;
				background-color:#747972;
				position: absolute;
				top:0;
				bottom:0;
				left:0;
				right:0;
			}
			.login-container{
				min-width: 800px;
			}
			.widget-main{
				text-align:center;
			}
			.widget-main a,.widget-main{
				font: 16px/90px "微软雅黑","黑体";
				color: #fff;
			}
			.formLeft{
				width:20%;
				float:left;
				min-width: 130px;
				display:inline-block;
			}
			.formCenter{
				width:54%;
				min-width: 130px;
				display:inline-block;
				margin:0px 20px;
			}
			.formRight{
				width:20%;
				float:right;
				min-width: 130px;
				display:inline-block;
			}
			.loginIcon,.arrowIcon{
				height:200px;
				margin-bottom: 20px;
				background:url('<%=request.getContextPath()%>/images/login/loginIcon.png') no-repeat center;
				background-color: rgba(102,200,240,0.8);
			}
			.forgetPaw,.userName,.password,.rememberMe,.registe,.backLogin{
				height:90px;	
			}
			.forgetPaw{
				background-color: rgba(255,71,46,.8);
			}
			.userName{
				background-color:#2eff6e;
			}
			.password{
				margin:20px 0;
				background-color:#fda728;
			}
			.rememberMe{
				text-align:right;
				background-color:rgba(174,93,161,.7);
			}
			.arrowIcon{
				background:url('<%=request.getContextPath()%>/images/login/arrowIcon.png') no-repeat center;
				background-color:rgba(25,244,231,.7);
			}
			.registe{
				background-color:rgba(25,184,88,.7);
			}
			.userNameIcon{
				height:90px;
				width:50px;
				position:absolute;
				left:10px;
				background: url('<%=request.getContextPath()%>/images/login/userIcon.png') no-repeat center;
			}
			.keyIcon{
				background: url('<%=request.getContextPath()%>/images/login/keyIcon.png') no-repeat center;
			}
			.emailIcon{
				background: url('<%=request.getContextPath()%>/images/login/emailIcon.png') no-repeat center;
			}
			.findPawIcon{
				background: url('<%=request.getContextPath()%>/images/login/findPaw.png') no-repeat center;
				background-color:#ccff44;
			}
			.registeIcon{
				background: url('<%=request.getContextPath()%>/images/login/registeIcon.png') no-repeat center;
				background-color:#ccff44;
			}
			.repeatIcon{
				background: url('<%=request.getContextPath()%>/images/login/repeatIcon.png') no-repeat center;
			}
			.inputStyle{
				height: 90px;
				width:90%;
				outline:none;
				float:right;
			}
			.backLogin{
				width:100%;
				top:-40px;
				display:inline-block;
				position:relative;
				background-color:rgba(255,71,46,.7);
			}
		</style>
	</head>
<script>

function login()
{
	document.forms['loginForm'].submit();
}
</script>
	<body class="login-layout" style="">
		
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="" style="width:70%;margin: 0 auto;">
						<div class="center">
							<h1>
								<span style="display: inline-block;">
								<span style='background-image: url("<%=request.getContextPath()%>/images/sysLogo.png");float:left; width: 134px; height: 86px; '> </span>
								<span style="line-height: 86px;float:left;font: 32px/86px '微软雅黑';color: #fff;">凡豆科技运营管理系统</span>
								</span>
							</h1>
						</div>
						<div class="login-container" style="width:100%;">
							

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border" style="background-color: transparent;">
									<div class="widget-body" style="background-color: transparent;border: none;">
										<div class="widget-main" style="padding:40px 0;background-color: transparent;">
											<form method="post" name="loginForm" action="<%=request.getContextPath()%>/user/login">
												<fieldset>
													<div class="formLeft">
														<div class="loginIcon">
															
														</div>
														<div class="forgetPaw">
															<a href="#"  onclick="show_box('forgot-box'); return false;">忘记密码？</a>
														</div>
													</div>
													<div class="formCenter">
														<div class="userName">
															<span class="block input-icon input-icon-left">
															<i class="userNameIcon"> </i>
															<input type="text" name="account" class="form-control inputStyle" required placeholder="请输入用户名" style="border:none;background-color: transparent;color: #fff;font:18px/18px '微软雅黑';"/>
														</span>
														</div>
														<div class="password">
															<span class="block input-icon input-icon-left">
															<i class="userNameIcon keyIcon"> </i>
															<input name="password" type="password" required class="form-control inputStyle" style="border:none;background-color: transparent;color: #fff;font:18px/18px '微软雅黑';"/>
														</span>
														</div>
														<div class="rememberMe">
															<span style="float: left;margin-left: 20px;color: red;font-weight: bold;">${message}</span>
															<label class="inline" style="margin-right: 40px;">
															<!-- 
																<input type="checkbox" class="ace" />
																<span class="lbl" style="line-height: 18px;">记住密码</span>
																 -->
															</label>
														</div>
													</div>
													<div class="formRight">
														
														<div class="arrowIcon">
															<button type="submit" class="arrowIcon" style="width:100%;border:none;" > </button>
														</div>
														<div class="registe">
															<a href="#">联系我们</a>
														</div>
													</div>
													
													
												</fieldset>
											</form>

										</div><!-- /widget-main -->

									</div><!-- /widget-body -->
								</div><!-- /login-box -->

								

								
						</div>
						
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->
		<div class="center" style="width:100%;position: absolute;bottom:-5px;color:#eee;">
			<h4 class="" style="font: 14px/20px '微软雅黑'">&copy; 深圳市凡豆教育科技有限公司</h4>
		</div>
		

		

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>

		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			function show_box(id) {
			 jQuery('.widget-box.visible').removeClass('visible');
			 jQuery('#'+id).addClass('visible');
			}
		</script>
	
</body>
</html>
