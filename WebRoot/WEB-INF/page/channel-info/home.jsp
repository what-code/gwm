<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>购玩美-频道首页</title>
</head>
<body>
	<!--头部 s-->
	<c:import url="../common/head.jsp"></c:import>
	<!--头部 e-->
	
	<div class="wraper">
		<div class="wraper-inner clear-fix">
			<div class="col-side fl">
				<dl>
					<dt>
						<i class="icon-menu icon-menu-function fl"></i>
						<i class="icon-arr fr"></i>
						功能
					</dt>
					<dd><a href="${rootPath }/send.html">群发功能</a></dd>
				</dl>
				<dl>
					<dt>
						<i class="icon-menu icon-menu-manage fl"></i>
						<i class="icon-arr fr"></i>
						管理
					</dt>
					<dd><a href="javascript:void(0);">用户管理</a></dd>
				</dl>
				<dl>
					<dt>
						<i class="icon-menu icon-menu-setting fl"></i>
						<i class="icon-arr fr"></i>
						设置
					</dt>
					<dd>
						<a href="${rootPath }/channel-info.html">频道信息</a>
					</dd>
				</dl>
			</div>
			<div class="col-main fr">
				<div class="main-inner">
					<div class="main-show clear-fix">
						<div class="main-tab add fl">
							<ul>
								<li>
									<p class="br">
										<i class="icon-msg"></i>
										<em>0</em>
										<strong>新消息</strong>
									</p>
								</li>
								<li>
									<p>
										<i class="icon-count"></i>
										<em>${chnMap.todayAddAmount }</em>
										<strong>新增人数</strong>
									</p>
								</li>
							</ul>
						</div>
						<div class="main-tab total fr">
							<ul>
								<li>
									<p>
										<i class="icon-total"></i>
										<em>${chnMap.amount }</em>
										<strong>总用户数</strong>
									</p>
								</li>
							</ul>
						</div>
					</div>
					<div class="main-notice">
						<h3>系统公告</h3>
						<ul>
							<c:forEach items="${sysList }" var="sys">
								<li>
									<a href="javascript:void(0);">
										<strong>${sys.body }</strong>
										<span>${sys.sendTime }</span>
									</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="faq"><p>联系客服<br />021-6886 0698</p></div>
	</div>
	
	<!--尾部 s-->
	<c:import url="../common/foot.jsp"></c:import>
	<!--尾部 e-->
	<input type="hidden" id="chnCheck" value="${chnCheck}">
</body>
<script type="text/javascript">
	(function(){
		var chnCheck = $('#chnCheck').val();
		if(chnCheck == "0"){
			alert("提示:您的频道还未通过审核，暂不能发送消息！");
		}
	})();
</script>
</html>