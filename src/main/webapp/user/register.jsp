<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<title>用户注册</title>
<link rel="stylesheet" href="/wechat/frozenui-1.3.0/css/frozen.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<header class="ui-header ui-header-positive ui-border-b">
		<h1>用户注册</h1>
	</header>
	<footer class="ui-footer ui-footer-btn">
		<ul class="ui-tiled ui-border-t">
		</ul>
	</footer>
	<section class="ui-container">
		<div class="login">
			<div class="ui-form ">
				<form action="#">
					<div class="ui-form-item ui-form-item-pure ui-border-b">
						<input id="mobile" type="text" placeholder="请输入手机号">
					</div>
					<div class="ui-form-item ui-form-item-r ui-border-b">
						<input id="code" name="" type="text" placeholder="请输入验证码">
						<!-- 若按钮不可点击则添加 disabled 类 -->
						<button id="verification-code" type="button" class="ui-border-l" onclick="sendVerificationCode()">获取验证码</button>
					</div>
				</form>
			</div>
		</div>

		<div class="ui-btn-wrap">
			<button class="ui-btn-lg" onclick="regist()">确定</button>
		</div>

	</section>
	<script src="/wechat/frozenui-1.3.0/lib/zepto.min.js"></script>
	<script src="/wechat/frozenui-1.3.0/js/frozen.js"></script>
	<script>
		var countdown = 60;

		function sendVerificationCode() {

			var mobile = $('#mobile').val();
			if (mobile == "" || mobile == null || mobile == undefined) {
				alert("请填写手机号码");
				return false;
			} else {
				if (!(/^1[34578]\d{9}$/.test(mobile))) {
					alert("手机号码有误，请重填");
					return;
				}
			}

			$.post("/wechat/h5/user/regisiter", {
				mobile : mobile
			}, function(success) {
				if (success.code == 200) {
					$('#verification-code').attr("onclick", "");
					settime();
				}
			}, "json");
		}

		function settime() {

			if (countdown == 0) {
				$('#verification-code').attr("onclick", "sendVerificationCode()");
				$('#verification-code').removeClass('disabled');
				$('#verification-code').html('获取验证码');
				countdown = 60;
			} else {
				$('#verification-code').addClass('disabled');
				$('#verification-code').html('(' + (countdown - 1) + 's)')
				countdown--;
				setTimeout(function() {
					settime()
				}, 1000)
			}

		}
		
		function regist(){
			
			$.post("/wechat/h5/user/verificationCode", {
				code : $('#code').val(),
				mobile : $('#mobile').val()
			}, function(success) {
				if (success.code == 200) {
					if(success.status==1){
						if(success.registed==0){
							alert("未注册");
						}else{
							alert("已注册")
						}
					}else{
						alert("验证码错误");
					}
				}
				
			}, "json");
		}
	</script>
</body>
</html>