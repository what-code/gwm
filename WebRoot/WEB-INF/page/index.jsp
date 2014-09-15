<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>购玩美-登陆页面</title>
<link rel="stylesheet" href="http://staticcdn.b5m.com/css/gowanmei/gowanmei.css?t=${version}" />
</head>
<body>
	<div class="header">
		<div class="wp clear-fix">
			<h1 class="logo fl">
				<a href="/"><img src="http://staticcdn.b5m.com/images/gowanmei/logo.png" width="217" height="45" alt="购玩美频道" /></a>
			</h1>
			<div class="back fl"><a target="_blank" href="http://www.gwmei.com/">返回官网</a></div>
			<div class="account account-reg fr">第一次使用频道运营平台？<a href="${rootPath }/register-base.html">立即注册</a></div>
		</div>
	</div>
	<div class="login">
		<div class="wp">
			<div class="login-panel">
				<h3>登录</h3>
				<div class="login-mod">
					<div class="fail" style="display: ${msg==''||msg==null?'none':''};">${msg }</div>
					<form id="login-form" action="/login-home.html" method="post">
						<div class="login-user">
							<input type="text" name="email" placeholder="邮箱" autocomplete="off" value="${email }"/>
						</div>
						<div class="login-psw">
							<input type="password" name="pwd" placeholder="密码" value="${pwd }"/>
							<input type="hidden" name="pwdRemember" value="${pwdRemember }"/>
						</div>
						<input type="hidden" id="isRemember" name="isRemember" value="${remember }"/>
					</form>
						<div class="login-help-panel clear-fix">
							<span class="fl">
								<input type="checkbox" ${remember?'checked="checked"':''} name="checkbox" id="checkbox"/>
								<label for="checkbox">记住密码</label>
							</span>
						</div>
					<div class="login-btn-panel"><a href="javascript:void(0);" class="btn btn-orange">登录</a></div>
				</div>
			</div>
		</div>
	</div>
	<div class="wraper">
		<div class="wraper-case">
			<h3>成功案例</h3>
			<div class="case-title">
				<p>
					<img src="http://staticcdn.b5m.com/images/gowanmei/case.png" width="72" height="72" alt="购玩美" />
					<span>购玩美</span>
				</p>
			</div>
			<div class="case-cont clear-fix">
				<img class="fl" src="http://staticcdn.b5m.com/images/gowanmei/case/case_1_1.jpg" width="322" height="482" alt="" />
				<img class="fl" src="http://staticcdn.b5m.com/images/gowanmei/case/case_1_2.jpg" width="322" height="482" alt="" />
				<p class="fr"><strong>瑞丽宝贝</strong>频道号 -- 致力打造最前沿的瑞丽时尚，服饰、鞋帽、配饰、美女、搭配、化妆，一切与美有关的内容，我们为您呈现；关注我们，走在时尚前端，成为生活中的亮点。</p>
				<i class="arrow_out"></i>
				<i class="arrow_in"></i>
			</div>
		</div>
	</div>
	
	<!--尾部 s-->
	<c:import url="common/foot.jsp"></c:import>
	<!--尾部 e-->
	<script src="${rootPath }/js/login-regs.js"></script>
</body>
</html>