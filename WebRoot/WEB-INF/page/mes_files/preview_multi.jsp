<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>购玩美-多图文预览</title>
</head>
<body>
	<!--头部 s-->
	<%-- <c:import url="../common/head.jsp"></c:import> --%>
	<!--头部 e-->
	
	<c:forEach var="m" items="${list}" varStatus="s">
		<a href="${rootPath}/detail/${m.id}.html">${m.title}</a><br><a href="${rootPath}/detail/${m.id}.html"><img src="${m.faceImg}"></a><br>
	</c:forEach>

	<!--尾部 s-->
	<%-- <c:import url="../common/foot.jsp"></c:import> --%>
	<!--尾部 e-->
</body>
</html>