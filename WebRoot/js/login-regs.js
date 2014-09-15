(function($){
	var isMails = false,
		isPsw1 = false,
		isPsw2 = false,
		isCode = true;
	function isEmail(str){
	    return /^\w+([\-+\.]\w+)*@\w+([\-+\.]\w+)*\.\w+([\-+\.]\w+)*$/.test(str);
	}
	function isPass(str){
		return /^[\x21-\x7e]{6,}$/.test(str);
	}
	function verifyCode(str){
		return /^[\w\d]{4,4}$/i.test(str);
	}
	function setFail (b, c, el){
		var $fail = el.parent().find(c);
		if (!b) {
			$fail.show();
			return false;
		} else {
			$fail.hide();
			return true;
		}
	}
	function getAgree(t, n) {
		n.prop("disable", !0).addClass("btn-grey"), t.change(function() {
			var t = $(this).prop("checked");
			t ? n.attr("disable", !1).removeClass("btn-grey").addClass('btn-primary') : n.attr("disable", !0).removeClass("btn-primary").addClass("btn-grey");
		});
	}

	$('#go-email').on('blur', function(){
		var _this = $(this),
			$email = _this.val(),
			isMail = isEmail($email);
		if(isMail){// 邮箱验证
			$.ajax({
				type : "post",
				url : "/mialValidate.html",
				data : {
					"email":$email
				},
				success:function(data){
					var fail = _this.parent().find('.fail');
					if(data){
						fail.find('span').text('邮箱已经被占用，请更换邮箱');
						fail.show();
						isMails = false;
					}else{
						fail.hide();
						isMails = true;
					}
				},
				error:function(){
					alert('系统出错请稍后再试(mail)...');
				}
			});
		}else{
			_this.parent().find('.fail').find('span').text('请输入正确的邮箱地址');
			isMails = setFail(isMail, '.fail', _this);
		}
	});
	$('#go-psw1').on('blur', function(){
		var _this = $(this),
			$pass = _this.val();
		isPsw1 = setFail(isPass($pass), '.fail', _this);
	});
	$('#go-psw2').on('blur', function(){
		var _this = $(this),
			psw1 = $.trim($('#go-psw1').val()),
			psw2 = $.trim(_this.val());
		isPsw2 = setFail(((psw2 !=='') && (psw2 === psw1)), '.fail', _this);
	});
	$('#go-code').on('blur', function(){
		var _this = $(this),
			$verifycode = _this.val(),
			captcha = verifyCode($verifycode);
		if(captcha){
			codeVer(_this,$verifycode);
		}
		isCode = setFail(verifyCode($verifycode), '.fail', _this);
	});
	
	codeVer = function(_this,$verifycode){
		$.ajax({
			type : "post",
			url : "/codeValidate.html",
			data : {
				"code":$verifycode
			},
			success:function(data){
				var fail = _this.parent().find('.fail');
				if(!data){
					fail.show();
					isCode = false;
				}else{
					fail.hide();
					isCode = true;
				}
			},
			error:function(){
				alert('系统出错请稍后再试(code)...');
			}
		});
	};
	
	function checkVal(){
		var $email = $('#go-email').val(),
		$psw1 = $.trim($('#go-psw1').val()),
		$psw2 = $.trim($('#go-psw2').val()),
		$verifycode = $('#go-code').val(),
		obj = {
			'go-email': isEmail($email),
			'go-psw1' : isPass($psw1),
			'go-psw2' : ($psw2 !=='') && ($psw2 === $psw1),
			'go-code' : verifyCode($verifycode)
		};
		$.each(obj, function(n, t){
			setFail(t, '.fail', $('#' + n));
		});
		codeVer($('#go-code'),$verifycode);
	}
	
	$('.btn').on('click', function() {
		if($(this).attr('class') != 'btn btn-orange'){
			var agree = $('#agree').prop("checked");
			if(isCode && isPsw1 && isPsw2 && isMails && agree){
				$('#register').submit();
			}else{
				checkVal();
			}
		}
	});

	getAgree($("#agree"), $('.frm-btn .btn'));
	
	/**
	 * 更换验证码
	 */
	$('.captcha-next').on('click', function() {
		$(this).parent().siblings('.fail').show();
		isCode = false;
        $(this).siblings('img').attr("src","/servlet/Captcha?"+Math.random());
    });
	
	//--------------邮箱验证
	/**
	 * 重新发送邮件
	 */
	$('.form a').on('click',function(e){
		e.preventDefault();
		$('.form').submit();
	});
	
	//--------------频道信息
	/**
	 * 头像上传
	 */
//	$('input[name="filedata"]').on('click',function(e){
//		e.preventDefault();
//		$('.file').click();
	$('input[name="filedata"]').on('change',function(e){
		$('#iform').submit();
	});
//	});
	
	/**
	 * 回调函数
	 */
	window.upload = {
		uploadImgComplete: function(url, message){
            if ((url != '') && (message === 'success')) {
                $('#avatar').attr('src',url);
                $('#hdnAvatar').val(url);
            }
        }
	};
	
	var $chnName = false;
	/**
	 * 名字检测
	 */
	$('#chnName').on('blur', function(){
		var _this = $(this),
			chnName = _this.val();
		if(chnName != ''){
			$.ajax({
				type : "post",
				url : "/chnNameValidate.html",
				data : {
					"chnName":chnName
				},
				success:function(data){
					var fail = _this.parent().find('.fail');
					if(data){
						$chnName = false;
						fail.show();
					}else{
						$chnName = true;
						fail.hide();
					}
				},
				error:function(){
					alert('系统出错请稍后再试(name)...');
				}
			});
		}else{
			$('.name span').text('频道名称不能为空！！！');
			$('.name').show();
			$chnName = false;
			return;
		}
		
	});
	
	$('.btn-primary').on('click',function(e){
		e.preventDefault();
		var name = $('#chnName').val();
		if(name == ''){
			$('.name span').text('频道名称不能为空！！！');
			$('.name').show();
			return;
		}
		if($chnName){
			$('#info').submit();
		}
	});
	
	//登陆
	$('.btn-orange').on('click', function(e){
		e.preventDefault();
		formSubmit();
	});
	
	formSubmit=function(){
		var email = $('.login-user input').val();
		var pwd = $('.login-psw input').val();
		if(!isEmail(email) || pwd == ''){
			$('.fail').text('请输入正确的邮箱名或密码！');
			$('.fail').show();
			return;
		}else{
			$('.fail').hide();
			$('#isRemember').val($("#checkbox").prop("checked"));
			$('#login-form').submit();
		}
	};
	
	//密码框按回车键提交表单
	$('.login-psw input').on('keydown',function(e){
	    if(e.keyCode==13) {
	    	formSubmit();
	    }
	});
	
	//修改频道信息
	$('.account-btn a').on('click',function(e){
		e.preventDefault();
		$('#updateForm').submit();
	});
})(jQuery);