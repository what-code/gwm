<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>购玩美频道-邮箱验证</title>
</head>
<body>
	<!--头部 s-->
	<c:import url="../common/head.jsp"></c:import>
	<!--头部 e-->
	
	<div class="wraper">
		<div class="wraper-inner register">
			<div class="register-tab clear-fix">
				<ul>
					<li class="def prev">1.基本信息</li>
					<li class="def current">2.邮箱激活</li>
					<li class="def next">3.频道信息</li>
				</ul>
			</div>
			<div class="activation">
				<div class="activation-tip">
					<h2>激活频道账号</h2>
					<p>感谢注册！确认邮件已发送至你的注册邮箱：${email } 请进入邮箱查看邮件，并激活频道账号。</p>
				</div>
				<div class="activation-qa">
					<p>没有收到邮件？</p>
					<p>1. 请检查邮箱地址是否正确，你可以返回<a href="${rootPath }/register-base.html">重新填写</a>。</p>
					<p>2. 检查你点右键垃圾箱</p>
					<form class="form" action="/register-activation.html" method="post">
						<input type="hidden" name="email" value="${email }"/>
						<input type="hidden" name="chnId" value="${chnId }"/>
						<p>3. 若仍未收到确认，请尝试<a href="javascript:void(0);">重新发送</a>。</p>
					</form>
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