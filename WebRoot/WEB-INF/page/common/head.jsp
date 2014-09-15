<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="http://staticcdn.b5m.com/css/gowanmei/gowanmei.css?t=${version}" />
<input type="hidden" id="rootPath" value="${rootPath}">
<input type="hidden" id="user_session_login" name="user_session_login" value="${user_session_login}">
<input type="hidden" id="user_session_check_status" name="user_session_check_status" value="${user_session_check_status}">
<div class="header">
	<div class="wp clear-fix">
		<c:choose>
			<c:when test="${user_session_login==true }">
				<h1 class="logo fl">
					<a href="${rootPath }/home.html"><img src="http://staticcdn.b5m.com/images/gowanmei/logo.png" width="217" height="45" alt="购玩美频道" /></a>
				</h1>
				<div class="account fr">
					<div class="account-wanmei fl"><a href="${rootPath }/home.html">${user_session_name }</a></div>
					<div class="account-email fl"><a href="javascript:void(0);" title="通知"></a></div>
					<div class="account-exit fl"><a href="${rootPath }/exit-login.html">退出</a></div>
				</div>
			</c:when>
			<c:otherwise>
				<h1 class="logo fl">
					<a href="${rootPath }"><img src="http://staticcdn.b5m.com/images/gowanmei/logo.png" width="217" height="45" alt="购玩美频道" /></a>
				</h1>
<%-- 				<div class="account account-reg fr">已有微信公众帐号？<a href="${rootPath }">立即登陆</a></div> --%>
			</c:otherwise>
		</c:choose>
	</div>
</div>