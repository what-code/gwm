<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>购玩美-多图文</title>
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
						<li><a href="${rootPath}/file_single.html">单图文消息</a></li>
						<li><a href="${rootPath}/file_multi.html" class="current">多图文消息</a></li>
					</ul>
				</div>
					<div class="main-inner">
						<div class="main-uploader clear-fix">
							<div class="fl">
								<div class="uploader-pic">
									<div class="uploader-hd">
										<div
											class="uploader-item uploader-item-top uploader-item-multi">
											<h4>
												<span>标题</span>
											</h4>
											<p>
											<%-- 	<img class="thumb"
												src="${imgPath == null ? 'http://pic.junli.cc/259x155/ccc' : imgPath}" id="face_img_temp"/>
												<i class="default"id="face_i_temp">封面图片</i> --%>
												
												<img class="thumb" src="" width="259" height="155" id="face_img_temp"/>
												<i class="default"id="face_i_temp">封面图片</i>
											</p>
											<div class="item-mask"><a class="edit" href="javascript:void(0);">编辑</a></div>
										</div>
										<div class="uploader-item clear-fix">
											<h4><span>标题</span></h4>
											<p>
												<img class="thumb" src="" width="68" height="68" />
												<i class="default">缩略图</i>
											</p>
											<div class="item-mask">
												<a class="edit" href="javascript:void(0);">编辑</a>
												<a class="del" href="javascript:void(0);">删除</a>
											</div>
										</div>
									</div>
									<div class="uploader-add">
										<a href="javascript:void(0);"><em>+</em>添加</a>
									</div>
								</div>
							</div>
							<div class="fr">
								<div class="uploader-cont">
									
										<div class="uploader-txt">
											<label for="">标题(*)</label> <input type="text" value="" class="txt" id="mes_title" name="mes_title">
										</div>
										<div class="uploader-txt">
											<label for="">作者<em>（选项）</em></label> <input type="text" value="" class="txt" id="mes_author" name="mes_author">
										</div>
										<div class="uploader-txt">
											<label for=""><span>大图片建议尺寸：360像素 * 200像素</span>封面(*)</label>
											<div class="uploader-front">
												<div class="uploader-icon">
													<a class="upfile" href="javascript:void(0);">上传</a>
												</div>
												<div class="upfile-preview">
												</div>
											</div>
										</div>
										<div class="uploader-txt" style="display:none">
											<label for="">摘要</label>
											<textarea class="brief" id="mes_abstract" name="mes_abstract"></textarea>
										</div>
										<div class="uploader-txt">
											<label for="">正文(*)</label>
											<div class="uploader-word" id="myEditor"></div>
										</div>
										<i class="arrow-out"></i> <i class="arrow-in"></i>
										<input type="hidden" id="target_id" name="target_id" value="${msgGroup}"> 
										<input type="hidden" id="target_sex" name="target_sex" value="${msgSex}"> 
										<input type="hidden" id="face_img" name="face_img">
										<input type="hidden" id="mes_type" name="mes_type" value="2">
										<input type="hidden" name="mes_left" id="mes_left" value="${messageLeft}">
										<!-- <input type="hidden" id="mes_abstract" name="mes_abstract"> -->
									<!-- upload file s -->
									<div class="upload-img-box">
										<a class="close" href="javascript:void(0)"></a>
										<form id="iform" target="ifrm-upload-img"
											enctype="multipart/form-data" method="post"
											action="/upload_img.html" name="iform">
											<div class="upload-input clear-fix">
												<input class="file" type="file" name="filedata"/> <span
													class="loading"></span>
											</div>
											<p>大小：不超过2M 格式：bmp，png，jpeg，jpg，gif</p>
										</form>
										<iframe id="ifrm-upload-img" name="ifrm-upload-img"
											style="display: none;">
											<html>
												<head>
													
												</head>
												<body></body>
											</html>
										</iframe>
									</div>
									<!-- upload file e -->
								</div>
							</div>
						</div>
						<div class="uploader-btn">
							<a href="javascript:submitMulti()" class="btn btn-primary">确定</a> <a href="#" class="btn btn-grey">重设</a>
						</div>
					</div>
				
			</div>
		</div>
		<div class="faq">
			<p>
				联系客服<br />021-6886 0698
			</p>
		</div>
		<form id="multi_form" action="/save_multi_mes.html"  method="post" >
			<input type="hidden" id="multi_form_data" name="multi_form_data" value="">
			<input type="hidden" id="mes_type" name="mes_type" value="2">
		</form>
	</div>
	
	<!--尾部 s-->
	<c:import url="../common/foot.jsp"></c:import>
	<!--尾部 e-->
</body>
<script type="text/javascript">
	var editor = new UE.ui.Editor();
	editor.render("myEditor");
	
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