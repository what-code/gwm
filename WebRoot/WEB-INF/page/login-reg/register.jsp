<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>购玩美频道-用户注册</title>
</head>
<body>

	<!--头部 s-->
	<c:import url="../common/head.jsp"></c:import>
	<!--头部 e-->

	<div class="wraper">
		<div class="wraper-inner register">
			<div class="register-tab clear-fix">
				<ul>
					<li class="def current">1.基本信息</li>
					<li class="def next">2.邮箱激活</li>
					<li class="def">3.频道信息</li>
				</ul>
			</div>
			<div class="register-base">
				<div class="frm-bd clear-fix">
					<p class="fail" style="display: ${msg==''||msg==null?'none':''};">
						<i>●</i><span>${msg }</span>
					</p>
					<div class="frm-section fl">
						<form id="register" action="/register-activation.html" method="post">
							<div class="frm-controls-group clear-fix">
								<label>邮箱</label>
								<div class="frm-controls">
									<input id="go-email" name="email" type="text" placeholder=""
										class="txt" />
									<p class="fail" style="display: none;">
										<i>●</i><span>请输入正确的邮箱地址</span>
									</p>
									<p>用来登录公众平台，接收到激活邮件才能玩成注册</p>
								</div>
							</div>
							<div class="frm-controls-group clear-fix">
								<label>密码</label>
								<div class="frm-controls">
									<input id="go-psw1" name="psw1" type="password" class="txt"
										onpaste="return false" />
									<p class="fail" style="display: none;">
										<i>●</i><span>密码长度不足6位，或者使用了非法字符</span>
									</p>
									<p>字母、数字或者英文符号，最短6位，区分大小写</p>
								</div>
							</div>
							<div class="frm-controls-group clear-fix">
								<label>确认密码</label>
								<div class="frm-controls">
									<input id="go-psw2" name="pwd" type="password" class="txt"
										onpaste="return false" />
									<p class="fail" style="display: none;">
										<i>●</i><span>两次输入的密码不一致</span>
									</p>
									<p>字母、数字或者英文符号，最短6位，区分大小写</p>
								</div>
							</div>
							<div class="frm-controls-group frm-controls-group-last clear-fix">
								<label>验证码</label>
								<div class="frm-controls">
									<input id="go-code" name="code" type="text" value=""
										class="txt" />
									<p>输入下面图片的字符，不区分大小写</p>
									<p class="auth-code">
										<img src="${rootPath }/servlet/Captcha"> <a href="javascript:void(0);" class="captcha-next">看不清，换一张</a>
									</p>
									<p class="fail" style="display: none;">
										<i>●</i><span>验证码错误，请重新输入</span>
									</p>
									<label for="agree" class="agree"> <input id="agree"
										name="agree" type="checkbox" /> 我同意并遵守 <a target="_blank"
										href="${rootPath }/pact.html">《购玩美公众平台服务协议》</a>
									</label>
								</div>
							</div>
						</form>
					</div>
					<div class="frm-tip fl">
						<p>
							<i>●</i> 已有购玩美公众帐号？ <a href="${rootPath }">立即登录</a>
						</p>
					</div>
				</div>
				<div class="frm-btn">
					<input type="button" class="btn" value="注册"/>
				</div>
			</div>
		</div>
	</div>

	<!--尾部 s-->
	<c:import url="../common/foot.jsp"></c:import>
	<!--尾部 e-->
	
	<script src="${rootPath }/js/login-regs.js"></script>
</body>
</html>