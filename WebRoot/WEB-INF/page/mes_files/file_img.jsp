<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>购玩美-单图文</title>
</head>
<body>
	<!--头部 s-->
	<c:import url="../common/head.jsp"></c:import>
	<!--头部 e-->
	<div class="wraper">
		<div class="wraper-inner">
			<div class="mask" ></div>
			<div class="popup"
				style="width: 918px; height: 590px; margin: -296px 0 0 -460px;">
				<a href="#" class="popup-close"></a>
				<h2 class="popup-title">选择素材</h2>
				<div class="popup-send">
					<div class="select-pic">
						<p>暂无素材</p>
						<p class="select-pic-btn">
							<a href="#" class="btn btn-grey">上传</a>
						</p>
						<p>
							大小：不超过2M<span>格式：bmp，png，jpeg，jpg，gif</span>
						</p>
					</div>
				</div>
				<div class="popup-btn">
					<a href="#" class="btn btn-primary">确定</a> <a href="#"
						class="btn btn-grey">取消</a>
				</div>
			</div>
		</div>
	</div>
	<!--尾部 s-->
	<c:import url="../common/foot.jsp"></c:import>
	<!--尾部 e-->
</body>
<script type="text/javascript">
	var editor = new UE.ui.Editor();
	editor.render("myEditor");
</script>
</html>