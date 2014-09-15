<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>发送消息</title>
</head>
<body>
	<!--头部 s-->
	<c:import url="../common/head.jsp"></c:import>
	<!--头部 e-->
		<div class="wraper">
			<div class="wraper-inner">
				<c:import url="../common/head_new.jsp"></c:import>
				<div class="col-main fr">
					<div class="main-title">
						<ul class="clear-fix">
							<li><a href="#" class="current">新建群发消息</a></li>
							<li><a href="${rootPath}/sended.html">已发送</a></li>
						</ul>
						<strong><a href="#">群发消息规则说明</a></strong>
					</div>
						<div class="main-inner">
							<div class="send-msg">
							<form id="send_txt" name="send_txt" method="post" action="/save_single_mes.html">
								<dl class="send-msg-option clear-fix">
									<dd>
										<label for="users">群发对象</label>
										<select id="users" id="target_id" name="target_id">
											<option value="0">全部用户</option>
										</select>
									</dd>
									<dd>
										<label for="sex">性别</label>
										<select id="target_sex" name="target_sex">
											<option value="">全部</option>
											<option value="0">男</option>
											<option value="1">女</option>
										</select>
									</dd>
								</dl>
								<input type="hidden" id="mes_type" name="mes_type" value="3">
								<input type="hidden" id="face_img" name="face_img" value="">
								<input type="hidden" name="mes_title" id="mes_title" value="">
								<input type="hidden" name="mes_left" id="mes_left" value="${messageLeft}">
							</form>
								<div class="msg-sender">
									<div class="tab-wrap">
										<ul class="tab clear-fix">
											<li class="selected"><a href="javascript:void(0);"><i class="tab-txt"></i>文字</a></li>
											<li><a href="javascript:void(0);"><i class="tab-img"></i>图片</a></li>
											<li><a href="javascript:toMessagePage()"><i class="tab-mixed"></i>图文消息</a></li>
										</ul>
										<div class="upload-img-box">
											<a class="close" href="javascript:void(0)"></a>
											<form id="iform" target="ifrm-upload-img" enctype="multipart/form-data" method="post" action="/upload_img.html" name="iform">
													<div class="upload-input clear-fix">
														<input class="file" type="file" name="filedata"/>
														<span class="loading"></span>
													</div>
													<p>大小：不超过2M 格式：bmp，png，jpeg，jpg，gif</p>
													<input type="hidden" id="mes_from" name="mes_from" value="index">
											</form>
											<iframe id="ifrm-upload-img" name="ifrm-upload-img" style="display:none;">
												<html>
													<head></head>
													<body></body>
												</html>
											</iframe>
										</div>
										<div class="upload-img-error"></div>
									</div>
									
									<div class="tab-content">
										<div class="msg-sender-txt" id="msg_sender_txt" onkeyup="countTxtWords()" contenteditable="true"></div>
										<div class="editor-toolbar">
											<span>还可以输入<em id="em_words">600</em>字</span>
										</div>
									</div>
									<div class="tab-content" style="display:none;">
										<div class="msg-sender-img"></div>
									</div>
								</div>
								<h6>您今天还能群发${messageLeft}条信息</h6>
							</div>
							<div class="send-msg-btn">
								<a href="javascript:submitTxt()" class="btn btn-primary">确定</a>
							</div>
						</div>
				</div>
			</div>
			<div class="faq"><p>联系客服<br />021-6886 0698</p></div>
		</div>
		
		<div class="mask" style="display:none;"></div>
		<div class="popup" style="width:918px;height:590px;margin:-296px 0 0 -460px;display:none;">
			<div class="popup-btn">
				<a href="#" class="btn btn-primary">确定</a>
				<a href="#" class="btn btn-grey">取消</a>
			</div>
		</div>
	<!--尾部 s-->
	<c:import url="../common/foot.jsp"></c:import>
	<!--尾部 e-->
</body>
<script type="text/javascript">	
	(function(){
		var sendFlag = $('#send_result').val();
		if(sendFlag == "1"){
			alert("发送成功！");
		}
		if(sendFlag == "0"){
			alert("发送失败！");
		}
		if(sendFlag == "0" || sendFlag == "1"){
			location.href = "/sended.html";
		}
	})();
</script>
</html>