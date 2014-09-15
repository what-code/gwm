var item_len = item_index = 2, //item_len为item的个数，item_index最后一个item的data-id值
    data_id = 1, //当前编辑的item，默认为第一个
    current_id = 1;//移出编辑的item，默认为第一个
(function(window,$){
    //登录
    var loginForm = $('#login-form');
    $('.login-user, .login-psw').focusin(function(){
        var _this = $(this),
            _cn = _this.attr('class');
        loginForm.removeClass().addClass(_cn + '-on');
    });
    $('.login-user, .login-psw').focusout(function(){
        loginForm.removeClass();
    });
	//tab切换
    var tab = $('.col-side dt'),
        tabCont = $('.col-side dd');
    tab.on('click', function(){
        var _this = $(this);
        _this.toggleClass('close');
        _this.next('dd').toggle();
    });
    //频道信息-内容介绍
    $('.modify-info').on('click', function(e){
        e.preventDefault();
        $(this).hide();
        var el_briefinfo = $('.brief-info'),
            el_brieftextarea = $('.brief-textarea'),
            s = el_briefinfo.text();
        el_brieftextarea.val(s);
        el_briefinfo.hide();
        el_brieftextarea.show().select();
    });
    //表情
    $('.icon-emotion').click(function(e){
        var emWrap = $('.emotion-wrap');
    	e.preventDefault();
        e.stopPropagation();
    	emWrap.show().animate({'opacity': 1}, 200);
        emWrap.click(function(e){
            e.stopPropagation();
        });
        $(document).one('click', function(){
            emWrap.animate({'opacity': 0}, 200, function(){
                $(this).hide();
            })
        });
    });
    var eList = $('.emotion-wrap li');
    eList.each(function(){
    	var _this = $(this),
    		_gifIcon = _this.find('i'),
    		_gifSrc = _gifIcon.attr('data-gifurl'),
    		_gifTitle = _gifIcon.attr('data-title');
    	_this.on({
    		mouseenter: function(){
    			$('.emotion-preview').append('<img src="' + _gifSrc + '"/>');
    		},
    		mouseleave: function(){
    			$('.emotion-preview').empty();
    		},
    		click: function(){
    			var msgHtml = $('.msg-sender-txt').html();
    			msgHtml += '<img src="' + _gifSrc + '" alt="' + _gifTitle + '" />';
    			$('.msg-sender-txt').html(msgHtml);
    		}
    	});
    });
    //多图文消息操作
    var uploader_hd   = $('.uploader-hd'),
        uploader_item = $('.uploader-item'),
        uploader_add  = $('.uploader-add a'),
        uploader_cont = $('.uploader-cont'),
        uploader_txt = $('.uploader-txt').eq(0);
    var uploaderItem = {
        init: function(){
            var _this = this;
            $.each(uploader_item, function(i, v){
                var $this = $(this),
                    i = i + 1;
                $this.attr({
                    'id': 'msgItem' + i,
                    'data-id': i
                });
            });
            uploader_hd.on({
                mouseenter: function(){
                    $(this).find('.item-mask').show();
                },
                mouseleave: function(){
                    $(this).find('.item-mask').hide();
                }
            }, '.uploader-item');
            uploader_add.on('click', function(){
                if (window['item_len'] < 4) {
                    _this.addItem();
                } else {
                    alert('无法添加，多图文最多4条消息!');
                }
            });
            uploader_hd.on('click', '.item-mask .del', function(e){
                e.preventDefault();
                if (window['item_len'] < 3) {
                    alert('无法删除，多图文至少需要2条消息!');
                } else {
                    var _parent = $(this).parents('.uploader-item');
                    window['data_id'] = $(_parent).attr('data-id');
                    _this.deleteItem(_parent);
                    delMsgToMultiData(window['data_id']);
                    uploader_cont.css('margin-top', 0);
                    saveOrUpdateMes(-1, 1);
                    window['current_id'] = 1;
                }
            });
            uploader_hd.on('click', '.item-mask .edit', function(e){
                e.preventDefault();
                var $this = $(this),
                    _parent = $this.parents('.uploader-item'),
                    _index = _parent.index(),
                    _top = (_index - 1) * 101 + 195;
                window['data_id'] = $(_parent).attr('data-id');
                if (_index == 0) {
                    uploader_cont.css('margin-top', 0);
                } else {
                    uploader_cont.css('margin-top', _top);
                }
                if (window['current_id'] == window['data_id']) return false;
                saveOrUpdateMes(window['current_id'], window['data_id']);
                window['current_id'] = window['data_id'];
            });
            uploader_txt.find('.txt').keyup(function(){
                var value = $.trim($(this).val()),
                    title = $('#msgItem' + window['data_id']).find('h4 span');
                if (value === '') {
                    title.html('标题');
                } else {
                    title.html(value);
                }
            });
        },
        addItem: function(){
            window['item_len']++;
            window['item_index']++;
            var itemStr = '<div class="uploader-item clear-fix"><h4><span>标题</span></h4><p><img class="thumb" src="" width="259" height="155" /><i class="default">缩略图</i></p><div class="item-mask"><a class="edit" href="javascript:void(0);">编辑</a>&nbsp;<a class="del" href="javascript:void(0);">删除</a></div></div>';
            $(itemStr).appendTo(uploader_hd).attr({
                'id': 'msgItem' + window['item_index'],
                'data-id': window['item_index']
            });
        },
        deleteItem: function(el){
            window['item_len']--;
            el.remove();
        }
    };
    uploaderItem.init();
    //添加摘要和原文链接
    $('.uploader-link').click(function(){
        var _this = $(this);
        _this.hide();
        _this.next('.uploader-txt').toggle();
    });
    //群发消息
    var _tabMsg = $('.tab li:lt(2)');
    _tabMsg.click(function(){
        var _this = $(this),
            _index = _this.index(),
            _tabcont = $('.tab-content');
        _tabMsg.removeClass('selected');
        _this.addClass('selected');
        _tabcont.hide();
        _tabcont.eq(_index).show();
        //add by duanyu
        $('#mes_type').val(_this.index() + 3);
        if (_index == 0) {
            $('.msg-sender-txt').focus();
        }
    });

    //单/多图文消息
    var el_upfile = $('.upfile'),
        el_upfilepreview = $('.upfile-preview'),
        el_uploadimgbox = $('.upload-img-box'),
        el_close = el_uploadimgbox.find('.close'),
        el_file = el_uploadimgbox.find('.file'),
        el_form = el_uploadimgbox.find('form'),
        el_loading = el_uploadimgbox.find('.loading'),
        el_uploadimgerror = $('.upload-img-error'),
        el_msgsenderimg = $('.msg-sender-img');
    window.goMsg = {
        init: function(el){
            this.uploading(el);
        },

        //上传图片功能
        uploading: function(el){
            el_uploadimgbox.click(function(e){
                e.stopPropagation();
            });
            el.click(function(e){
                e.preventDefault();
                e.stopPropagation();

                el_uploadimgbox.show();
                $(document).one('click', function(){
                    el_uploadimgbox.hide();
                });
            });
            el_close.click(function(e){
                e.preventDefault();
                el_uploadimgbox.hide();
            });
            el_file.change(function(){
                el_loading.show();
                el_form.submit();
            });
            el_upfilepreview.on('click', 'a', function(e){
                e.preventDefault();
                el_upfilepreview.empty();
                el_upfilepreview.hide();
                $('#msgItem' + window['data_id']).find('.default').show()
                .end()
                .find('.thumb').hide().attr('src', '');
            });
        },
        uploadImgComplete: function(url, message){
            if ((url != '') && (message === 'success')) {
                initImgPath(url);
                el_upfilepreview.html('<img src="' + url + '" width="100" height="100" /><a href="javascript:void(0);">删除</a>');
                el_loading.hide();
                el_uploadimgbox.hide();
                el_upfilepreview.show();
                $('#msgItem' + window['data_id']).find('.thumb').show().attr('src', url)
                .end()
                .find('.default').hide();
            } else {
                el_loading.hide();
                el_uploadimgbox.hide();
                el_uploadimgerror.html(url).show().delay(2000).animate({
                    'opacity': 0
                }, 1000, function(){
                    $(this).hide().html('');
                });
                alert(url);
            }
        }
        ,
        uploadImgDone: function(url, message){
            if ((url != '') && (message === 'success')) {
                initImgPath(url);
                el_msgsenderimg.html('<img src="' + url + '" width="100" height="100" />');
                el_loading.hide();
                el_uploadimgbox.hide();
            } else {
                el_loading.hide();
                el_uploadimgbox.hide();
                el_uploadimgerror.html(url).show().delay(2000).animate({
                    'opacity': 0
                }, 1000, function(){
                    $(this).hide().html('');
                });
                alert(url);
            }
        }
    };
    window.goMsg.init(el_upfile);
    window.goMsg.init(_tabMsg.eq(1));
})(window, jQuery);