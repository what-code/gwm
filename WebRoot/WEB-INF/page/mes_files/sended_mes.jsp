<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${rootPath}/favicon.ico" type="image/x-ion"/>
<title>已发送消息</title>
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
							<li><a href="${rootPath}/send.html" >新建群发消息</a></li>
							<li><a href="${rootPath}/sended.html" class="current">已发送</a></li>
						</ul>
						<!-- <strong><a href="#">群发消息规则说明</a></strong> -->
					</div>
					<div class="page">
                        <span class="page-area">
                            <a href="javascript:void(0)" id="pre_page">上一页</a>
                            <span class="page-num">
                                <label>${sendedPageNo}</label>
                                <span>/</span>
                                <label>${sendedList.totalPages}</label>
                            </span>
                            <a href="javascript:void(0)" id="next_page">下一页</a>
                            <input type="text" value="${sendedPageNo}" id="page_num_text"/>
                        </span>
                    </div>
					<div class="main-inner send-history">
						<ul class="send-list">
							<c:forEach var="send" items="${sendedList.all}" varStatus="s">
								<li>
									<c:choose>
										<c:when test="${(send.mesType == '1' || send.mesType == '2' || send.mesType == '4')}">
											<div class="fl list-cont">
												<c:if test="${send.mesType == '2'}">
													<img src="${send.faceImg}" width="80" height="80">
													<input type="hidden" value="${send.mesId}">
												</c:if>
												<c:if test="${send.mesType == '1' || send.mesType == '4'}">
													<a href="${rootPath}/detail/${send.mesId}.html"><img src="${send.faceImg}" width="80" height="80"></a>
												</c:if>
												<c:if test="${send.mesType != '4'}">
													<h6>${(send.mesType == '1' ? '单图文' : '多图文')}</h6>
													<c:if test="${send.mesType == '1'}">
														<p><a href="${rootPath}/detail/${send.mesId}.html">${send.mesName}</a></p>
													</c:if>
													<c:if test="${send.mesType == '2'}">
														<p>${send.mesName}</p>
													</c:if>
												</c:if>
											</div>
										</c:when>
										<c:otherwise>
											<div class="fl list-cont">
												<c:choose>
													<c:when test="${fn:length(send.mesName) > 12}">
														<a href="${rootPath}/detail/${send.mesId}.html">${fn:substring(fn:replace(send.mesName,"<br>",""), 0, 11)}...</a>
													</c:when>
													<c:otherwise>
														<a href="${rootPath}/detail/${send.mesId}.html">${fn:replace(send.mesName,"<br>","")}</a>
													</c:otherwise>
												</c:choose>
											</div>
										</c:otherwise>
									</c:choose>
									<div class="fl list-tips">${send.mesStatus == 1 ? '发送成功' : '发送失败'}</div>
									<div class="fl list-time">${fn:substring(send.mesSendTime,0,16)}</div>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="page">
                        <span class="page-area">
                            <a href="javascript:void(0)" id="pre_page01">上一页</a>
                            <span class="page-num">
                                <label>${sendedPageNo}</label>
                                <span>/</span>
                                <label>${sendedList.totalPages}</label>
                            </span>
                            <a href="javascript:void(0)" id="next_page01">下一页</a>
                            <input type="text" value="${sendedPageNo}" id="page_num_text01"/>
                        </span>
                    </div>
					</div>
			</div>
			<div class="faq"><p>联系客服<br />021-6886 0698</p></div>
		</div>
		<form id="sended_form" action="/sended.html" method="post">
			<!-- <input type="button" id="pre_page" value="上一页" > <input id="next_page" type="button" value="下一页"> -->
			<input type="hidden" value="${sendedPageNo}" id="page_no" name="page_no">
			<input type="hidden" value="${sendedList.totalPages}" id="total_page" name="total_page">
		</form>
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
})();
</script>
</html>