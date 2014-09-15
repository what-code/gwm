<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>购玩美频道-频道信息</title>
</head>
<body>
	<!--头部 s-->
	<c:import url="../common/head.jsp"></c:import>
	<!--头部 e-->
	
	<div class="wraper">
		<div class="wraper-inner register">
			<div class="register-tab clear-fix">
				<ul>
					<li class="def pprev">1.基本信息</li>
					<li class="def prev">2.邮箱激活</li>
					<li class="def current">3.频道信息</li>
				</ul>
			</div>
			<div class="register-info">
				<div class="reg-info clear-fix">
					<label>头像</label>
					<div class="reg-info-md">
						<img id="avatar" width="88" height="88" src="${avatar=='null'?'':avatar }" onerror="this.src='http://ucenter.b5m.com/images/ucenter/avatar130.png';">
					</div>
					<div class="reg-info-sm">
						<form id="iform" target="ifrm-upload-img" enctype="multipart/form-data" method="post" action="/imgUpload.html" name="iform">
							<div class="fun-up">
								<a href="javascript:void(0);">上传</a>
								<input type="file" size="3" name="filedata" title="支持jpg、jpeg、gif、png格式，文件小于2M" />
							</div>
						</form>
						<iframe id="ifrm-upload-img" name="ifrm-upload-img" style="display: none;">
							<html>
								<head>
									
								</head>
								<body></body>
							</html>
						</iframe>
					</div>
				</div>
				<form id="info" action="/reg-home.html" method="post">
					<input type="hidden" name="avatar" id="hdnAvatar" value="${avatar }"/>
					<div class="reg-info clear-fix">
						<label>名称</label>
						<div class="reg-info-md">
							<input id="chnName" name="chnName" type="text" value="">
							<p class="fail name" style="display:none;"><i>●</i><span>此名称已被注册，请更换名称</span></p>
						</div>
						<div class="reg-info-sm">
							<span>(保存后不可修改)</span>
						</div>
					</div>
					<div class="reg-info clear-fix">
						<label>登录邮箱</label>
						<div class="reg-info-md">${email }</div>
						<input type="hidden" name="email" id="email" value="${email }"/>
					</div>
					<div class="reg-info clear-fix">
						<label>频道ID</label>
						<div class="reg-info-sm">${chnId }</div>
						<input type="hidden" name="chnId" id="chnId" value="${chnId }"/>
					</div>
					<div class="reg-info clear-fix">
						<label>内容介绍</label>
						<div class="reg-info-lg">
							<textarea name="content" maxlength="250"></textarea>
						</div>
					</div>
				</form>
				<div class="reg-info-btn">
					<a href="javascript:void(0);" class="btn btn-primary">保存</a>
				</div>
			</div>
		</div>
	</div>
	
	<!--尾部 s-->
	<c:import url="../common/foot.jsp"></c:import>
	<!--尾部 e-->
	<script src="${rootPath }/js/common/ajaxfileupload.js"></script>
	<script src="${rootPath }/js/login-regs.js"></script>
</body>
</html>