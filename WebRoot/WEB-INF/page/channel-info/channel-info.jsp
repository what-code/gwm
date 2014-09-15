<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>购玩美频道-修改频道信息</title>
</head>
<body>
	<!--头部 s-->
	<c:import url="../common/head.jsp"></c:import>
	<!--头部 e-->
	
	<div class="wraper">
		<div class="wraper-inner">
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
					<dd><a href="${rootPath }/channel-info.html" class="current">频道信息</a></dd>
				</dl>
			</div>
			<div class="col-main fr">
				<ul class="main-title clear-fix">
					<li><a href="${rootPath }/channel-info.html" class="current">频道信息</a></li>
				</ul>
				<div class="main-inner">
					<ul class="account-setting">
						<li>
							<h4>头像</h4>
							<div class="cont"><img id="avatar" width="88" height="88" src="${chnMap.imgUrl=='null'?'':chnMap.imgUrl }" onerror="this.src='http://ucenter.b5m.com/images/ucenter/avatar130.png';"/></div>
							<div class="fun">
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
						</li>
						<form id="updateForm" action="/channel-update.html" method="post">
							<li>
								<input type="hidden" name="avatar" id="hdnAvatar" value="${chnMap.imgUrl}"/>
								<h4>名称</h4>
								<div class="cont">${chnMap.name }</div>
								<input type="hidden" name="chnName" id="chnName" value="${chnMap.name }"/>
								<div class="fun"><span>(不可修改)</span></div>
							</li>
							<li>
								<h4>登录邮箱</h4>
								<div class="cont">${chnMap.jid }</div>
								<input type="hidden" name="email" id="email" value="${chnMap.jid }"/>
								<div class="fun"><span>(不可修改)</span></div>
							</li>
							<li>
								<h4>频道ID</h4>
								<div class="cont">${chnMap.id }</div>
								<input type="hidden" name="chnId" id="chnId" value="${chnMap.id }"/>
								<div class="fun"></div>
							</li>
							<li>
								<h4>内容介绍</h4>
								<div class="cont">
									<span class="brief-info">${chnMap.description }</span>
									<textarea class="brief-textarea" name="content" style="display:none;" maxlength="250">${chnMap.description }</textarea>
								</div>
								<div class="fun"><a class="modify-info" href="javascript:void(0);">修改</a></div>
							</li>
						</form>
					</ul>
					<div class="account-btn">
						<a href="javascript:void(0);" class="btn btn-primary">确定</a>
					</div>
				</div>
			</div>
		</div>
		<div class="faq"><p>联系客服<br />021-6886 0698</p></div>
	</div>
	
	<!--尾部 s-->
	<c:import url="../common/foot.jsp"></c:import>
	<!--尾部 e-->
	<script src="${rootPath }/js/login-regs.js"></script>
</body>
</html>