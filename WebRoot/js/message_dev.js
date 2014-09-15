/**
 * 上传图片
 */
function uploadImgs() {
	var form = $('#iform');
	form.submit();
}

/**
 * 保存单图文信息
 */
function submitMessage() {
	if (!validateUserInfo()) {
		return;
	}
	if (!validateSingleMes()) {
		return;
	}
	var form = $('#single_form');
	var bodyHtml = editor.getContent();
	// alert(bodyHtml);
	$('#mes_body').val(bodyHtml);
	form.submit();
}

/**
 * 保存多图文信息
 */
function submitMulti() {
	if (!validateUserInfo()) {
		return;
	}
	// 补充完善图文信息
	if ((MULTI_MSG_DATA.length + 1) == item_len) {
		// 保存当前的页面信息至数组
		saveOrUpdateMes(data_id, -1);
	}
	// 校验信息是否完整
	// if(!validateMultiMes()){
	// return;
	// }
	var result = "[";
	for ( var i = 0; i < MULTI_MSG_DATA.length; i++) {
		if (JSON.stringify(MULTI_MSG_DATA[i]) != "") {
			result = result + JSON.stringify(MULTI_MSG_DATA[i]) + ",";
		}
	}
	result = result.substring(0, result.length - 1) + "]";
	$('#multi_form_data').val(result);
	var form = $('#multi_form');
	form.submit();
}

/**
 * 校验单图文消息内容是否为空
 */
function validateSingleMes() {
	var bodyHtml = editor.getContent();
	var title = $('#mes_title').val();
	var author = $('#mes_author').val();
	var faceImg = $('#face_img').val();
	if ($.trim(title) == "") {
		alert("标题不能为空!");
		return false;
	}
	
	if($.trim(title.length) > 25){
		alert("标题长度大于25!");
		return false;
	}
	
	if ($.trim(author) == "") {
		alert("作者不能为空!");
		return false;
	}
	if ($.trim(faceImg) == "") {
		alert("封面不能为空!");
		return false;
	}
	if ($.trim(bodyHtml) == "") {
		alert("正文不能为空!");
		return false;
	}
	return true;
}

/**
 * 校验多图文消息是否完整
 */
function validateMultiMes() {
	$.each(MULTI_MSG_DATA, function(i, item) {
		var temp = eval(item);
		if ($.trim(temp.title) == "" || $.trim(temp.faceImg) == ""
				|| $.trim(temp.body) == "") {
			alert("请完善第" + (i + 1) + "条信息！");
			return false;
		}
	});
}

/**
 * 去消息发送页面
 */
function toMessagePage() {
	if (!validateUserInfo()) {
		return;
	}

	var form = $('#send_txt');
	form.attr("action", "/file_single.html");
	form.submit();
}

/**
 * 发送文本消息
 */
function submitTxt() {
	var form = $('#send_txt');
	var temp = $('#msg_sender_txt').html();
	var mesType = $('#mes_type').val();
	var faceImg = $('#face_img').val();
	if (!validateUserInfo()) {
		return;
	}

	if (mesType == '3') {
		if ($.trim(temp) == "") {
			alert("提示:请输入发送内容！");
			return;
		}
		if ($.trim(temp).length > 600) {
			alert("提示:文字长度不能大于600！");
			return;
		}
	}
	if (mesType == '4' && faceImg == '') {
		alert("提示:请上传图片！");
		return;
	}
	form.submit();
}

// 初始化图片路径
function initImgPath(url) {
	$('#face_img').val(url);
	/*
	 * $('#face_img_temp').attr('src',url); $('#face_img_temp').show();
	 * $('#face_i_temp').hide();
	 */
}

// 删除图片路径
function deleteImgPath() {
	$('#face_img').val("");
	$('#face_img_temp').attr('src', "");
	$('#face_i_temp').show();
}

// 多图文 表单数据
var MULTI_MSG_DATA = new Array();

/**
 * 获取页面数据
 */
function getMultiPageData(mid) {
	var title = $('#mes_title').val();
	var author = $('#mes_author').val();
	var faceImg = $('#face_img').val();
	var abst = $('#mes_abstract').val();
	var body = editor.getContent();
	var reg = /\"/ig;
	body = body.replace(reg, '');

	var message = '{"mid":"' + mid + '","title":"' + title + '","author":"'
			+ author + '","faceImg":"' + faceImg + '","abst":"' + abst
			+ '","body":"' + body + '"}';
	return jQuery.parseJSON(message);
}

// 保存或更新,mid:保存或更新的id uid:加载的id
function saveOrUpdateMes(mid, uid) {
	var flag = true;
	var falg1 = true;
	
	$.each(MULTI_MSG_DATA, function(i, item) {
		var temp = eval(item);
		var tid = temp.mid;
		if (tid == mid) {
			flag = false;
		}
		if (tid == uid) {
			falg1 = false;
		}
	});

	if (flag) {
		addMsgToMultiData(mid);
	} else {
		updMsgToMultiData(mid);
	}

	cleanFormData();

	if (!falg1) {
		getMsgToMultiData(uid);
	}
}

// 清空页面数据
function cleanFormData() {
	$('#face_img_temp').val();
	$('#mes_title').val("");
	$('#mes_author').val("");
	$('#face_img').val("");
	$('#mes_abstract').val("");
	editor.setContent("");
	$('.upfile-preview').hide();
	$('.upfile-preview').empty();
}

// 向数组中添加数据
function addMsgToMultiData(mid) {
	var message = getMultiPageData(mid);
	MULTI_MSG_DATA.push(message);
}

// 获取某条消息的内容
function getMsgToMultiData(mid) {
			$.each(MULTI_MSG_DATA,function(i, item) {
						var temp = eval(item);
						var tid = temp.mid;
						if (tid == mid) {
							$('#mes_title').val(temp.title);
							$('#mes_author').val(temp.author);
							$('#face_img_temp').val(temp.faceImg);
							$('#mes_abstract').val(temp.abst);
							editor.setContent(temp.body);
							if (temp.faceImg != "") {
								$('.upfile-preview').show();
								$('.upfile-preview')
										.html(
												'<img src="'
														+ temp.faceImg
														+ '" width="100" height="100" /><a href="javascript:void(0);">删除</a>');
							}
							return;
						}
					});
}

// 更新某条消息
function updMsgToMultiData(id) {
	$.each(MULTI_MSG_DATA, function(i, item) {
		var temp = eval(item);
		var tid = temp.mid;
		if (tid == id) {
			MULTI_MSG_DATA[i] = getMultiPageData(id);
			return;
		}
	});
}

// 删除某条消息
function delMsgToMultiData(id) {
	$.each(MULTI_MSG_DATA, function(i, item) {
		var temp = eval(item);
		var tid = temp.mid;
		if (tid == id) {
			MULTI_MSG_DATA[i] = "";
			return;
		}
	});
}

// 计算字数
function countTxtWords() {
	var temp = $('#msg_sender_txt').html();
	$('#mes_title').val(temp);
	if (temp.length > 600) {
		$('#em_words').html(0);
		$('#msg_sender_txt').html(temp.substr(0, 600));
		return;
	}
	$('#em_words').html(600 - (temp.length));
}

// 用户是否登录
function isUserLogin() {
	// return $.cookie("isLogin");
	return $('#user_session_login').val();
}

// 用户是否通过审核
function isChnValidate() {
	var flag = $.cookie("checkStatus");
	flag = $('#user_session_check_status').val();
	// 通过
	if ("1" == flag) {
		return true;
	}
	return false;
}

// 判断发送的消息数目是否超过限制
function mesLimit() {
	var count = $('#mes_left').val();
	if (parseInt(count, '10') > 0) {
		return true;
	}
	return false;
}

// 用户综合校验
function validateUserInfo() {
	if (isUserLogin() == "false") {
		alert("提示:您还未登录，暂不能发送消息！");
		location.href = $('#rootPath').val();
		return false;
	}
	if (!isChnValidate()) {
		alert("提示:您的频道还未通过审核，暂不能发送消息！");
		return false;
	}

	if (!mesLimit()) {
		alert("提示:您今天发送的消息已达上限(3条)，今天暂不能发送消息！");
		return false;
	}
	return true;
}

/**
 * 获取字符长度
 * @param s
 * @returns {Number}
 */
function getLength(s) {
	var l = 0;
	var a = s.split("");
	for ( var i = 0; i < a.length; i++) {
		if (a[i].charCodeAt(0) < 299) {
			l++;
		} else {
			l += 2;
		}
	}
	return l;
}

//已发送 前一页
function prePage(){
	var form = $('#sended_form');
	var temp = $('#page_no').val();
	if(parseInt(temp,'10') > 1){
		$('#page_no').val(parseInt(temp,'10') - 1);
		form.submit();
	}
}

//已发送 后一页
function nextPage(){
	var form = $('#sended_form');
	var temp = $('#page_no').val();
	var total = $('#total_page').val();
	if(parseInt(temp,'10') < parseInt(total,'10')){
		$('#page_no').val(parseInt(temp,'10') + 1);
		form.submit();
	}
}

//去某页
function goToPage(){
	var e = event || window.event || arguments.callee.caller.arguments[0];
	this.value = this.value.replace(/\D/g, "");
	if(e && e.keyCode==13){ // enter 键
         //要做的事情
    }
}

(function(window,$){
	$('#pre_page').on('click',prePage);
	$('#next_page').on('click',nextPage);
	$('#pre_page01').on('click',prePage);
	$('#next_page01').on('click',nextPage);
	
	var temp = $('#page_no').val();
	var total = $('#total_page').val();
	
	$('.page .page-area > input').on('keyup',function(e){
		this.value = this.value.replace(/\D/g, "");
		var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) { // enter 键
	        if(parseInt(this.value,'10') > parseInt(total,'10')){
	        	this.value = total;
	        }
	        if(this.value < 1){
	        	this.value = 1;
	        }
	        $('#page_no').val(parseInt(this.value,'10'));
	        $('#sended_form').submit();
	    }
	});
	$('#page_num_text01').on('click',goToPage);
	
	if(parseInt(temp,'10') > 1){
		$('#pre_page').attr("class","");
		$('#pre_page01').attr("class","");
	}else{
		$('#pre_page').attr("class","disabled");
		$('#pre_page01').attr("class","disabled");
	}
	
	if(parseInt(temp,'10') < parseInt(total,'10')){
		$('#next_page').attr("class","");
		$('#next_page01').attr("class","");
	}else{
		$('#next_page').attr("class","disabled");
		$('#next_page01').attr("class","disabled");
	}
})(window, jQuery);