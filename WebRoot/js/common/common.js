/**
 * @fileoverview 此文件是前端公用方法、定义。包含常用方法、命名空间、接口定义等,依赖框架：jQuery。 还包含公用方法的事件
 * @version 1.0
 * @author B2C F2E team
 * @public
 */
(function(window, $) {


    var b5m = window.b5m = {};
    $.b5m = {};

    function _namespace(ns_string) {
        var parts = ns_string.split('.'),
            parent = this,
            i;

        if(parts[0] === 'b5m') {
            parts = parts.slice(1);
        }

        for(i=0;i<parts.length;i++) {
            if(typeof parent[parts[i]] === 'undefined') {
                parent[parts[i]] = {};
            }
            parent = parent[parts[i]];
        }
        return parent;
    }

    b5m.namespace = function(s) {
        return _namespace.call(this,s);
    }

    $.b5m.namespace = function(s) {
        return _namespace.call(this,s);
    }

    b5m.ui = {};
    b5m.utils = {};
    b5m.info = {};


    /********************************公共函数***********************************/
    var arrProto = Array.prototype;
    var slice = arrProto.slice;
    if (!window['console']) {
        //兼容console调试代码，如果觉得没控制台的浏览器alert麻烦，可以设置 .window.console.debug = false;
        window.console = {
            debug: false,
            log: function() {
                this.debug && alert(slice.call(arguments));
            },
            dir: function() {
                if (!this.debug) {
                    return;
                }
                var args = slice.call(arguments);
                for (var k in args) {
                    for (var j in args[k]) {
                        alert([args[k], args[k][j], j]);
                    }
                }
            }
        };
    }
    /**
     * @description 命名空间管理，防止命名污染,具体查看man方法的doc
     *
     */
    window.NS = {
        /**
         * 管理操作的man 函数
         * @param {string} ns  命名空间的完整string
         * @param {object/function/other} fn  可选，如有此参数，则会赋值给第一个参数形成的命名空间
         * @return {object} 返回定义对象object / {}
         * @example window.NS.man('public.tab');
         * @example window.NS.man('public.tab',function(){});
         */
        man: function(ns, fn) {
            if (typeof ns === 'string') {
                ns = ns.split('.');
                var i = 0;
                var len = ns.length - 1;
                var argsLen = arguments.length;
                var root = window.NS;
                while (ns[i]) {
                    root[ns[i]] = (argsLen > 1 && i === len) ? fn : (root[ns[i]] || {});
                    root = root[ns[i]];
                    i++;
                }
                return root;
            }
        }
    };

    /**
     * @description 前端接口定义，所有接口存储在window.INTERFACE全局变量下，具体查看man方法的定义
     */
    window.INTERFACE = window.INTERFACE || {
        length: 0,
        /**
         * @description 前端接口定义，所有接口存储在window.INTERFACE全局变量下,提供man方法管理
         * @namespace window
         * @param {string} type set|get|del
         * @param {string} name 要操作的接口名
         * @param {all} value 接口内容，仅在type为set的时候起效
         * @return {window.INTERFACE}
         * @example window.INTERFACE.man('set','comment',function(){alert('commint')});//这样 其他地方就可以调用到 window.INTERFACE.comment方法了
         */
        man: function(type, name, value) {
            switch (type) {
                case 'set':
                    if (arguments.length === 3) {
                        this[name] = value;
                        this.length++;
                    }
                    break;
                case 'get':
                    return this[name];
                    break;
                case 'del':
                    delete this[name];
                    this.length--;
                    break;
                default:
            }
            return this;
        }
    };
    
    window.NS.man('functions.supportsCss3');

    /**
     * @description 判断浏览器是否支持某项css3属性
     * @namespace NS.functions.supportsCss3
     * @return {Boolean} function(css3Name){} 返回的function，使用了闭包
     * @example window.NS.functions.supportsCss3('box-shadow');
     * @public
     */
    window.NS.functions.supportsCss3 = (function() {
        var div = document.createElement('div'),
            vendors = 'Khtml O Moz Webkit'.split(' '),
            len = vendors.length;
        /**
         * @param {String} css3AttrName css3的属性名
         * @return {Boolean}
         */
        return function(prop) {
            if (window.ActiveXObject && parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1], 10) <= 8) {
                return false;
            }
            if (prop in div.style)
                return true;
            if ('-ms-' + prop in div.style)
                return true;
            prop = prop.replace(/^[a-z]/, function(val) {
                return val.toUpperCase();
            });
            while (len--) {
                if (vendors[len] + prop in div.style) {
                    return true;
                }
            }
            return false;
        };
    })();


    /* cookie */
    window.Cookies = {
        set:function(name,value) {
            var argv = arguments;
            var argc = arguments.length;
            var expires = (argc > 2) ? argv[2] : null;
            var path = (argc > 3) ? argv[3] : '/';
            var domain = (argc > 4) ? argv[4] : null;
            var secure = (argc > 5) ? argv[5] : false;
            document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
        },
        get:function(name) {
            var arg = name + "=";
            var alen = arg.length;
            var clen = document.cookie.length;
            var i = 0;
            var j = 0;
            while (i < clen) {
                j = i + alen;
                if (document.cookie.substring(i, j) == arg)
                    return Cookies.getCookieVal(j);
                i = document.cookie.indexOf(" ", i) + 1;
                if (i == 0)
                    break;
            }
            return null;
        },
        clear:function(name) {
            if (Cookies.get(name)) {
                var expdate = new Date();
                expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
                Cookies.set(name, "", expdate);
            }
        },
        getCookieVal:function(offset) {
            var endstr = document.cookie.indexOf(";", offset);
            if (endstr == -1) {
                endstr = document.cookie.length;
            }
            return unescape(document.cookie.substring(offset, endstr));
        }
    };


    window.NS.man('ui.slider');

    window.NS.ui.slider = function(element, options){
        var settings = $.extend({
            autoplay: true,
            time: 300
        }, options || {});
        console.log(element+"||"+options);
    };
    /**************************ui组件**************************/
    window.NS.man('publics.tab');
    /**
     * @description 实现tab的函数，点击几个标签切换对应的内容，依赖jQuery
     * @namespace NS.public.tab
     * @param {jQuery Object} nav 标签的jq对象，类型为jq的nodeList
     * @param {jQuery Object} content 标签对应内容的jq对象，类型为jq的nodeList
     * @param {String} navClass 标签选中时候的className 可选
     * @param {String} contentClass 内容显示时的className  可选
     * @example window.NS.public.tab($nav.find('>li'),$navCont.find('div'),'nav-hover'.'content-hover',);
     * @example window.NS.public.tab($nav.find('>li'));
     * @public
     */
    window.NS.publics.tab = function(nav, content, navClass, contentClass, prev, next, fn) {
        var getPageHtml = function(pageLen) {
            var html = '';
            var i = pageLen - 1;
            while (i >= 0) {
                html = '<a ' + (i == 0 ? 'class="cur"' : '') + ' href="javascript:void(0)">' + (i + 1) + '</a>' + html;
                i--;
            }
            return html;
        };
        nav.each(function(index, item) {
            $(this).css('width', $(this).width()).addClass('slider-nav-fix');
            $(this).on('click.tab', function(e) {
                e.preventDefault();
                if (navClass) {
                    nav.filter('.' + navClass).removeClass(navClass);
                    $(this).addClass(navClass);
                }
                content.filter(':visible').scrollTop(0).stop().hide();
                content.eq(index).hide().fadeIn(0);
                content.find('img').trigger('lazyload');
                if (fn) {
                    fn.call(this, getPageHtml(Math.ceil(content.eq(index).find('li').length / 4)));
                    $(this).trigger('callback');
                }
            }).find('a').on('click', function(e) {
                    e.preventDefault();
                });
            content.scrollTop(0);
        }).eq(0).trigger('click.tab');
        if (prev && prev.length) {
            prev.on('click', function(e) {
                e.preventDefault();
                var navCur = navClass ? nav.filter('.' + navClass) : nav.filter(':visible');
                var newNav = navCur.prev().length ? navCur.prev() : nav.eq(-1);
                newNav.trigger('click.tab');
            });
        }
        if (next && next.length) {
            next.on('click', function(e) {
                e.preventDefault();
                var navCur = navClass ? nav.filter('.' + navClass) : nav.filter(':visible');
                var newNav = nav.index(navCur) <= nav.length - 2 ? navCur.next() : nav.eq(0);
                newNav.trigger('click.tab');
            });
        }
    };



    /*
     * 延迟加载功能
     * 在页面元素上设置data-delay='href'
     * href可以是图片 iframe的url 或者javascript方法格式必须是javascript:开头，"()"和“;”可以不加 同时在全局作用域下必须可以找到此方法，调用方式是js中的call(node)，此方法中的this就是需要延迟的node元素
     * demo ../../html/demo-delay.html
     * 页面加载则会获取所有需要延迟的元素
     */

    //页面没有需要延迟加载的元素 不执行以下代码
    //document ready后再获取需要延迟加载的元素，以免common.js是在head区引入的，取不到元素
    (function() {
        var delayNodes = $('[data-delay]').toArray();
        if (delayNodes.length > 0) {

            //检查第一个data-delay的图片，如果需要显示，那么递归，反之终止操作，解除绑定事件
            var checkFirstNode = function() {
                if (delayNodes.length) {
                    var _node = $(delayNodes[0]);
                    var offsetTop = _node.offset().top;
                    var _wd = {
                        sTop: $(window).scrollTop(),
                        H: $(window).height()
                    };
                    if (offsetTop < _wd.sTop + _wd.H) {
                        var href = _node.attr('data-delay');
                        if (href.indexOf('javascript:') == 0) {
                            //如果是javascript方法，那么call方法
                            href = href.replace(/(javascript:)|(\;)|(\(\))/g, '');
                            $.isFunction(window[href]) && window[href].call(_node.get(0));
                        } else {
                            _node.attr('src', href);
                        }
                        _node.removeAttr('data-delay');
                        delayNodes = delayNodes.slice(1); //去掉第一个，循环调用
                        checkFirstNode();
                    }
                } else {
                    $(window).unbind('scroll.delay resize.delay');
                }
            };
            var _timer = false;
            //类似taobao首页，禁止浏览器记住滚动条位置，每次都从顶部开始加载
            setTimeout(function() {
                $(window).scrollTop(0);
                checkFirstNode();
                $(window).on('scroll.delay resize.delay', function() {
                    clearTimeout(_timer);
                    _timer = setTimeout(function() {
                        checkFirstNode();
                    }, 100);
                });
            }, 100);
        }
    })();
    /* END delay function */







    /**************************b5m公共部分**************************/


    /*
     * jQuery placeholder plugin
     *
     * 给不支持placeholder的浏览器增加文本域的placeholder功能，统一为浏览器默认交互的placeholder
     *
     * 使用方法：
     * 参数1  需要设置的元素
     * 参数2  placeholder的className
     * 参数3  为4个方法 分别是获取/失去焦点，显示/隐藏placeholder
     * 测试代码在../../html/placeholder.html
     * $.setPlaceholder($('input.text),'placeholder',{
     *      focus : function(){},
     *      blur  : function(){},
     *      show  : function(){},
     *      hide  : function(){}
     * })
     *
     * 注意：placeholder会优先获取元素data-placeholder属性，如果无此属性或为空，则去获取attr('placeholder')，推荐使用data-placeholder，这样浏览器不会再js加载完成后有颜色位置变化的效果
     * 注意： 如果元素是不可见的，自身或者父级设置了display none，那么在显示的时候再绑定placeholder，否则获取到的offset为0
     */
    //判断是否支持placeholder
    $.setPlaceholder = (function() {
        var _createMask = function(className) {
            var self = $(this).get(0).tagName.toLowerCase();
            var mask = document.createElement(self); //clone的话如果是input[type=password]会无法显示文字
            mask.className = this.className || '';
            mask = $(mask);
            var placeholder = $(this).attr('data-placeholder') || $(this).attr('placeholder');
            $(this).attr('data-placeholder', placeholder);
            try {
                self == 'input' && mask.attr('type', 'input');
                //在ie10的ie7模式下，此行报错（设置为空同样会报错）
                $(this).removeAttr('placeholder');
            } catch (e) {}
            //在offsetParent中插入元素，若为body则设置父级为offsetParent，以免改变窗口时候偏移
            var $parent = $(this).offsetParent();
            if ($parent.is('body') || $parent.is('html')) {
                $parent = $(this).parent();
                $parent.css('position', 'relative');
            }
            $parent.append(mask);
            var oParent = $parent.offset();
            var oThis = $(this).offset();
            var pos = {
                left: oThis.left - oParent.left + 'px',
                top: oThis.top - oParent.top + 'px'
            };
            mask.val(placeholder).addClass(className);
            mask.get(0).style.cssText = 'left:' + pos.left + ';top:' + pos.top + ';overflow:hidden;resize:none;outline:none;cursor:text;float:none;margin:0;text-shadow:0px 0px 0px black;border-color:transparent;position:absolute;background:none;';
            mask.attr({
                readonly: 'readonly',
                'class': $(this).attr('class')
            }).show();
            return mask;
        };
        //ie10 ie9 placeholder与其他不同，focus则消失，用js模拟
        var isie = window.navigator.userAgent.indexOf('MSIE') > -1;
        var isie9 = isie && (window.navigator.appVersion.split(";")[1].replace(/[ ]/g, "").replace('MSIE', '') | 0) === 9;
        var useCustomPh = !'placeholder' in document.createElement('input') || window.navigator.userAgent.indexOf('MSIE') >= 0;

        var filter = {
            prev: true,
            next: true,
            siblings: true
        };
        return function(elements, className, fns) {
            className = className || 'placeholder';
            fns = fns || {};
            elements.each(function() {
                var mask;
                var $self = $(this);
                var phIsShow = true;
                //如果设置了placeholder的元素，那么获得它的jq对象
                var labelSelector = $self.attr('data-placeholder-label');
                if (labelSelector) {
                    var _ls = labelSelector.split('|');
                    if (filter[_ls[0]]) { //如果是prev、next、siblings
                        mask = $self[_ls[0]]();
                        mask = _ls[1] ? mask.filter(_ls[1]) : mask;
                    } else {
                        mask = $(labelSelector);
                    }
                    mask = mask.eq(0); //取结果的第一个
                } else if (useCustomPh) {
                    mask = _createMask.call(this, className);
                }
                if (mask && mask.length) {
                    mask.on('mousedown', function(e) {
                        e.preventDefault();
                        $self.focus();
                    });
                }
                //设置显示
                var setStaic = function() {
                    var val = $.trim($self.val());
                    if (phIsShow === true && val !== '') {
                        phIsShow = false;
                        mask && mask.hide();
                        $.isFunction(fns.hide) && fns.hide.call($self.get(0));
                    } else if (phIsShow === false && val === '') {
                        phIsShow = true;
                        mask && mask.show();
                        $.isFunction(fns.show) && fns.show.call($self.get(0));
                    }
                };
                //绑定方法
                if (isie9) {
                    //ie9支持oninput检查value值变化(已经不支持onpropertychange了)，但backspace和delete按键不会触发oninput
                    $self.on('keyup', function(e) {
                        if (e.keyCode == 8 || e.keyCode == 46) {
                            $self.trigger('input');
                        }
                    });
                }
                $self.on('input propertychange', function(e) {
                    if (e.originalEvent && e.originalEvent.propertyName && e.originalEvent.propertyName !== 'value') {
                        return false;
                    }
                    setStaic();
                });
                //挂上focus和blur方法
                $self.on('focus.ph', function() {
                    //在ie7中，会focus到placeholder点击的位置上，以下代码是判断文本域显示了placeholder的时候，focus将光标移到到最前
                    if (phIsShow === true) {
                        try {
                            var textNode = this;
                            var Rng = textNode.createTextRange();
                            Rng.moveStart('character', 0);
                            Rng.collapse(true);
                            Rng.select();
                        } catch (e) {}

                    }
                    $.isFunction(fns.focus) && fns.focus.call($self.get(0));
                });
                $self.on('blur.ph', function() {
                    $.isFunction(fns.blur) && fns.blur.call($self.get(0));
                });
                $self.data('initPlaceholder', true);
                setStaic();
            });

        };
    })();
    //快捷方式
    $.fn.placeholder = function(Options) {
        var _options = $.extend({
            className: 'placeholder'
        }, Options || {});
        $.setPlaceholder($(this), _options.className);
        return this;
    };



//返回顶部
    (function() {

        var $goTop = $('<a href="javascript:void(0)" class="gotop" title="返回顶部" hidefocus="true"></a>').appendTo(document.body);

        var scroll = true;
        var goTop = {
            isHide: true,
            show: function() {
                if (this.isHide) {
                    this.isHide = false;
                    $goTop.show().addClass('gotop-show');
                }
            },
            hide: function() {
                if (this.isHide === false) {
                    this.isHide = true;
                    $goTop.removeClass('gotop-show');
                }

            }
        };

        //点击返回顶部，隐藏自己，同时返回顶部动画期间不执行onscroll事件
        $goTop.on('click', function(e) {
            e.preventDefault();
            scroll = false;
            goTop.hide();
            $('html,body').animate({
                scrollTop: 0
            }, function() {
                scroll = true;
                $goTop.hide();

            });
        });

        var showGotop = function() {
            var t = $(window).scrollTop();
            if (t < 100) {
                goTop.hide();
            } else {
                goTop.show();
            }
        };
        if ($goTop.css('position') === 'fixed') {
            // $goTop.css('right', ($(document.body).width() - 980) / 2 - 60);
            $(window).on('scroll.gotop', function() {
                scroll && showGotop();
            }).trigger('scroll.top');
        } else {
            //ie6不支持fixed，用js来做
            // if ($goTop.css('display') == 'block') {
            //     // $goTop.show().css('top', $(window).height() - 130 + $(window).scrollTop());
            // }
            // $goTop.css('left', ($(document.body).width() - 980) / 2 + 980 + 20).css('right', 'auto');
            var timer = false;
            $(window).on('scroll.gotop', function() {
                if (scroll) {
                    clearTimeout(timer);
                    timer = setTimeout(function() {
                        var t = $(window).scrollTop();
                        if ($goTop.css('display') == 'none' && $(window).scrollTop() >= 100) {
                            $goTop.show().css('top', $(window).height() - 130 + t);
                        } else {
                            $goTop.css({
                                top: $(window).height() - 130 + t
                            }).fadeIn(300, function() {
                                    if ($(window).scrollTop() < 100) {
                                        $goTop.hide();
                                    } else {
                                        $goTop.show();
                                    }
                                });
                        }
                    }, 300);
                }
            });
        }

    })();


    /**
     * [setLinkByOs description]
     * @param {[type]} o [description]
     */
    $.fn.setLinkByOs = function(o) {
        return this.each(function() {
            var _this = this;
            $.fn.setLinkByOs.dft = {
                windows: '',
                mac: '',
                linux: '',
                unix: ''
            };
            var setting = $.extend(
                $.fn.setLinkByOs.dft, o);

            $(this).attr('href', setting[check_os()]);
        });


        function check_os() {
            var windows = (navigator.userAgent.indexOf("Windows", 0) != -1) ? 1 : 0,
                mac = (navigator.userAgent.indexOf("mac", 0) != -1) ? 1 : 0,
                linux = (navigator.userAgent.indexOf("Linux", 0) != -1) ? 1 : 0,
                unix = (navigator.userAgent.indexOf("X11", 0) != -1) ? 1 : 0;

            if (windows) os_type = "windows";
            else if (mac) os_type = "mac";
            else if (linux) os_type = "lunix";
            else if (unix) os_type = "unix";

            return os_type;
        }
    };




    //顶部通栏生成
    (function() {

        var _url = 'ucenter.b5m.com';
        var _domain = '.b5m.com';

        _url = (function() {
         if(location.hostname.indexOf('ucenter.stage.bang5mai.com') !== -1) {
           _domain = '.bang5mai.com';
           return 'ucenter.stage.bang5mai.com';
         }
        if(location.hostname.indexOf('ucenter.prod.bang5mai.com') !== -1) {
           _domain = '.bang5mai.com';
           return 'ucenter.prod.bang5mai.com';
         }
           _domain = '.b5m.com';
           return 'ucenter.b5m.com';


        })();

        //登录标识
        var userType = (function() {


            if(location.href.indexOf('www.b5m.com/forum.php') !== -1) {
                return 17;
            }

            switch(location.host) {
                case 'www.b5m.com':
                    return 8;
                case 's.b5m.com':
                    return 22;
                case 'haiwai.b5m.com':
                    return 23;
                case 'zdm.b5m.com':
                    return 20;
                case 'tejia.b5m.com':
                    return 6;
                case 'tuan.b5m.com':
                    return 16;
                case 'you.b5m.com':
                    return 13;
                case 'piao.b5m.com':
                    return 19;
                case 'she.b5m.com':
                    return 17;
                case 't.b5m.com':
                    return 5;
                case 'korea.b5m.com':
                    return 18;
                case 'plus.gwmei.com':
                    return 15;
                case 'www.gwmei.com':
                    return 4;
                case 'hao.b5m.com':
                    return 7;
                case 'tiao.b5m.com':
                    return 9;
                case 'guang.b5m.com':
                    return 25;
                case 'daikuan.b5m.com':
                    return 24;
                case 'gzx.b5m.com':
                    return 26;
                case 'yisheng.b5m.com':
                    return 21;
                case 'usa.b5m.com':
                    return 27;
                default:
                    return 0;
            }
        })();

        var topHdbanner = $('.top-hdbanner');
      //  if(!Cookies.get('topbarbanner-close')) {
            topHdbanner.empty().append('<a href="javascript:void(0)" class="top-banner-close">关闭</a><a href="http://oreg.jj.cn/gpbd/bang5w.html?siteid=4501182" target="_blank"><img src="http://staticcdn.b5m.com/static/images/huodong/jj/bop-banner.jpg" alt="“帮5买杯”第1届JJ斗地主帮豆回馈赛" width="980" height="60" /></a>');
            $('.top-banner-close').one('click',function() {
                topHdbanner.animate({height:0});
                return false;
              //  Cookies.set('topbarbanner-close','true',new Date(new Date().getTime()+  1000*60*60*24*30),'','.b5m.com');
            });
    //    }


        var topBarContent = $('.topbar').eq(0),
            isLogin = Cookies.get('login') === 'true' && Cookies.get('token'),
            userData= {};

        b5m.info.isLogin = isLogin;

        //topbar reset
        if($('.topbar-inner').length) {
            $('.topbar-inner').detach();
        }
        //登录之后跳回当前页面地址        
        var refererUrl = location.href;
        refererUrl = encodeURIComponent(refererUrl);

        //生成topbar
        topBarContent.append(function() {
            var _content = '<div class="topbar-inner wp cf">';
            //加载左部链接
            _content += '<div class="topbar-nav"><a class="home" href="http://www.b5m.com">帮5买首页</a><span class="line"></span><a class="buy" href="http://hao.b5m.com">购物导航</a><span class="line"></span><a class="korea" href="http://korea.b5m.com">韩国馆</a><span class="line"></span><a class="tao" href="http://t.b5m.com">帮5淘</a><span class="line"></span><a class="app" href="http://app.b5m.com">手机帮5买</a></div>';

            //已登录状态
            if(isLogin) {

                _content += '<div class="topbar-user topbar-user-login">';

                $.ajax({
                    url:'http://'+ _url +'/user/user/data/info.htm?isSimple=1',
                    // url:'http://ucenter.stage.bang5mai.com/user/user/data/info.htm?isSimple=1',
                    dataType:'jsonp',
                    jsonp:'jsonpCallback',
                    success:function(data) {
                        userData = data;

                        if(!userData.ok) {
                            Cookies.set('login','false',new Date(),'',_domain);
                            location.href = 'http://'+ _url;
                            return false;
                        }

                        //用户名
                        $('#b5muser').text(decodeURIComponent(userData.data.showName));
                        //签到
                        if(userData.data.isSign){
                            topBarContent.find('.userreg').addClass('off');
                        }
                    }
                });

                $.ajax({
                    // url:'http://ucenter.stage.bang5mai.com/user/message/data/count.htm',
                    url:'http://'+ _url +'/user/message/data/count.htm',
                    dataType:'jsonp',
                    jsonp:'jsonpCallback',
                    success:function(data) {
                        userDataMessage = data;
                        //消息数目
                        $('.topbar-msg-num').text(userDataMessage.data);
                    }
                });

                _content += '<span class="hi">你好，</span><span data-hover="user-hover" class="user"><a target="_self" id="b5muser" href="http://'+ _url +'"></a> <em class="arr"></em><span class="user-link"><a class="userreg" target="_self" href="javascript:void(0)">签到</a><a href="http://'+ _url +'/forward.htm?method=/user/trade/common/record/index" target="_self">帮豆</a><a href="http://'+ _url +'/user/user/logout.htm" target="_self">退出</a></span></span><span class="line"></span>';

                _content += '<span class="user msg" data-hover="user-hover"><a href="http://'+ _url +'/forward.htm?method=/user/msg/system/index" target="_self" class="msg-t">消息<em class="topbar-msg-num"></em></a><em class="arr"></em><span class="user-link"><a href="http://'+ _url +'/forward.htm?method=/user/msg/system/index" target="_self">系统消息<em class="topbar-msg-num"></em></a></span></span>';

            }
            //未登录状态
            else {

                _content += '<div class="topbar-user topbar-user-unlogin">';
                _content += '<a href="http://'+ _url +'/user/third/login/auth.htm?type=2&amp;refererUrl=' + refererUrl + '&amp;userType='+ userType +'" rel="fav" class="weibo" target="_self">微博登录</a><span class="line"></span><a href="http://'+ _url +'/user/third/login/auth.htm?type=1&amp;refererUrl=' + refererUrl +'&amp;userType='+ userType +'" rel="fav" class="qq" target="_self">QQ登录</a><span class="line"></span>';
                _content += '<a rel="nofollow" target="_self" href="http://'+ _url +'" class="high">登录</a><span class="line"></span><a rel="nofollow" target="_self" href="http://'+ _url +'/forward.htm?method=/user/info/register&amp;userType='+ userType +'">注册</a>';
            }

            _content += '<span class="line"></span><a target="_self" class="J_addFav" rel="fav" href="javascript:void(0)">收藏本站</a>';
            _content += '<span class="line"></span>';
            _content += '<div class="topbar-more" data-hover="topbar-more-hover"><a href="javascript:void(0)" id="J_addFav" target="_self" class="more">网站导航 <em class="arr"></em></a><div class="topbar-prod"><div class="top-border"></div><div class="item"><a target="_blank" href="http://www.b5m.com">帮5买</a><a target="_blank" href="http://tejia.b5m.com">淘特价</a><a target="_blank" href="http://you.b5m.com">帮5游</a><a target="_blank" href="http://guang.b5m.com">帮5逛</a><a target="_blank" href="http://tuan.b5m.com/">帮团购</a><a target="_blank" href="http://piao.b5m.com">帮票务</a><a target="_blank" href="http://www.b5m.com">比价网</a><a target="_blank" href="http://haiwai.b5m.com">海外馆</a><a target="_blank" href="http://t.b5m.com/">帮5淘</a><a target="_blank" href="http://hao.b5m.com/">购物导航</a><a class="lar" target="_blank" href="http://app.b5m.com/">手机帮5买</a></div><div class="item weixin"><img src="http://staticcdn.b5m.com/static/images/common/weixin_b5m.png"><span>扫二维码，加帮5买微信好友</span></div></div></div>';

            _content += '</div></div>';
            return _content;
        });


        //签到
        topBarContent.find('.userreg').click(function(e) {

            if(userData.data.isSign) {
                return false;
            }

            $.ajax({
                url:'http://'+ _url +'/user/task/data/sign.htm',
                dataType:'jsonp',
                jsonp:'jsonpCallback',
                success:function(data) {
                    if(data.ok) {
                        if(location.href.indexOf(_url) != -1) {
                            location.href = location.href;
                        }else {
                            var dialog = $('<div class="dialog-qiandao"><a href="javascript:void(0)" class="close"></a><span></span><b></b><i></i><em></em></div>').appendTo('body').show();
                            var _signday = userData.data.signDay || 0;
                            dialog.find('span').text(decodeURIComponent(userData.data.showName));
                            dialog.find('b').text(data.data == 5 ? 1 : (_signday + 1));
                            dialog.find('i').text('+' + data.data);
                            dialog.find('em').text('+' + (data.data === 50 ? 50 : data.data+5));
                            dialog.find('a').click(function() {
                                dialog.detach();
                                return false;
                            });
                            $(e.currentTarget).addClass('off').attr('href','javascript:void(0)').off();
                            if($('#signscroe1').length) {
                                $('#signscroe1').hide();
                                $('#signscroe2').show();
                            }
                        }
                    }
                }
            });

            return false;

        });


        //收藏功能
        var $J_addFav = $(".J_addFav");
        if (!$J_addFav.length) {
            $J_addFav = $(".topbar a[rel=fav]");
        }
        $J_addFav.length && $J_addFav.on('click', function(e) {
            e.preventDefault();
            var url = encodeURI(window.location.host);
            var title = $('title').html();
            if (window.external) {
                try {
                    window.external.addFavorite(window.location.href, document.title);
                } catch (e) {
                    alert("加入收藏失败，请使用Ctrl+D进行添加");
                }

            } else if (window.sidebar) {
                window.sidebar.addPanel(document.title, window.location.href, "");
            } else {
                alert("加入收藏失败，请使用Ctrl+D进行添加");
            }
        });

    })();



    //全网寻宝
    var activeFed = {
        /*全网寻宝*/
        quanwangXb:function(){
            var _content = this.content();

            var xbHtml = '<div id="huodong-quanwangXb" style="display:none;"><div class="huodong-quanwangXb-bg"></div><a href="http://www.b5m.com/huodong.php?mod=zajindan" target="_blank"></a><s></s></div>';
            $(xbHtml).appendTo($("body")).css("top",_content.offset().top+ _content.height() + parseInt(_content.css('padding-top')) + parseInt(_content.css('padding-bottom')) ).show().animate({height:300},320);

            var closeBtn = $("s","#huodong-quanwangXb").click(function(){
                $("#huodong-quanwangXb").slideUp();
                var val = window.hddatacount == 30 ? 'full' : window.hddatacount;
                Cookies.set('hddatacount',val,new Date(parseInt(window.hddatatime*1000)),'','.b5m.com');
                return false;
            });

            $('#huodong-quanwangXb').find('a').click(function() {
                closeBtn.click();
            });
        },
        loadFun:function(){

            var _count = Cookies.get('hddatacount');

            if(_count == 'full' && window.hddatacount==30) {
                return;
            }

            if(_count && _count == window.hddatacount) {
                return;
            }

            if($('body.lotterypage').length) {
                return;
            }

            var _this = this;
            setTimeout(function(){
                _this.quanwangXb();
            },2000);

        },
        content:function() {
            //判断头部dom

            //淘特价
            var head0 = $('.header-content.tao');
            if(head0.length) {
                return head0.find('.header-nav');
            }

            //韩国馆,淘特价 等
            var head1= $('.header-content .header-search');
            if(head1.length) {
                return head1;
            }
            //www,zdm 等
            var head2 = $('.header .search-bar');
            if(head2.length) {
                return head2;
            }
            //帮5淘
            var head3 = $('.header-content .search-bar');
            if(head3.length) {
                return head3;
            }

            //new head
            var head4 = $('.head .nav-bar');
            if(head4.length) {
                return head4;
            }

            //new head sub
            var head5 = $('.header-nav-box');
            if(head5.length) {
                return head5;
            }

            //you
            return $('.header .nav');
        }
    };




    //左侧栏导航
    var lefttoolbar = (function() {

        if($('.cloud-nav').length) {
            return;
        }

        $('body').append('<div class="left-toolbar"><ul><li><a target="_blank" data-attr="1011" href="http://tuan.b5m.com">帮团购</a></li><li><a target="_blank" data-attr="1011" href="http://tejia.b5m.com">淘特价</a></li><li><a target="_blank" data-attr="1011" href="http://korea.b5m.com">韩国馆</a></li><li><a target="_blank" data-attr="1011" href="http://you.b5m.com">帮5游</a></li><li><a target="_blank" data-attr="1011" href="http://piao.b5m.com">帮票务</a></li></ul><a href="javascript:void(0)" data-attr="1011" class="feedback-icon">意见反馈<em class="fd-tip"></em></a></div>');

        var bar = $('.left-toolbar'),
            list = bar.find('li a'),
            fdback = bar.find('.feedback-icon'),
            fdbacktip = fdback.find('em'),
            host= location.host;

        list.each(function(i,n) {
           if($(n).attr('href').indexOf(host)!=-1) {
               $(n).addClass('cur');
               try {
                 bar.show().addClass(host.match(/[^\.]+/)[0]);
               }catch(e) {}
               return false;
           }
        });

        fdback.click(function() {
            feedback.open();
            return false;
        });

        return {
            fdback:fdback,
            fdbacktip:fdbacktip
        };

    })();



    //左侧栏意见反馈
    var feedback = {
        _init:false,
        //初始化dom
        init:function() {

            if(this._init) return;
            var _this = this;

            $('body').append('<div class="left-feedback-box"><div class="fb-title"><span>意见反馈</span><a href="javascript:void(0)">关闭</a></div><div class="fb-content"><div class="panel-input"><span class="ico-text">您的建议和意见：<em class="verify-err">不能为空！</em></span><textarea  placeholder="请输入您的意见"></textarea><span class="ico-phone">您的联系方式：</span><input type="text" placeholder="请输入您的联系方式"/><a href="javascript:void(0)" class="dis"  data-attr="1011">提交</a></div><div class="panel-success"><span class="ico-text dis">您的建议和意见：</span><p class="info-text"></p><span class="ico-phone dis">您的联系方式：</span><p class="info-phone"></p><a href="javascript:void(0)" class="dis">提交</a><span class="ico-success">反馈成功，非常感谢您的宝贵意见。</span></div></div></div>');

            _this.content = $('.left-feedback-box').click(function() {
                return false;
            });

            _this.panel1 = _this.content.find('.panel-input');
            _this.panel2 = _this.content.find('.panel-success');
            _this.input1 = _this.content.find('textarea');
            _this.input2 = _this.content.find(':text');
            _this.btn = _this.panel1.find('a');

            //绑定事件
            _this.btn.click(function() {
                if(!$(this).data('on')) return;
                _this.submit();


            });

            _this.input1.on('keyup',function() {
              if(this.value) {
                 _this.btn.removeClass('dis').data('on',true);
              }else {
                 _this.btn.addClass('dis').data('on',false);
              }
            });

            _this.content.find('.fb-title a').click(function() {
                _this.close();
            });

        },
        //打开意见反馈
        open:function() {
            var _this = this;
            _this.init();
            _this.content.show();
            _this.panel1.show();
            _this.panel2.hide();
            _this.input1.focus();
            $(document).one('click',function() {
                _this.close();
            });
            _this._init = true;
        },
        //关闭
        close:function() {
           this.content.hide();
        },
        //提交
        submit:function() {
            var _this = this;
            if(_this.verify()) {

                _this.btn.addClass('dis').data('on',false);

                $.ajax({
                    url:'http://zdm.b5m.com/feedback.htm',
                    type:"POST",
                    data:{'url':location.href,'content':_this.input1.val(),'contact':_this.input2.val()},
                    dataType:"jsonp",
                    jsonp: 'jsonpCallback',
                    success:function(data){
                        if(data.code=='100') {
                            _this.panel1.hide();
                            _this.panel2.show();
                            _this.panel2.find('.info-text').text(_this.input1.val());
                            _this.panel2.find('.info-phone').text(_this.input2.val());
                            setTimeout(function() {
                                _this.input1.val('');
                                _this.btn.addClass('dis').data('on',false);
                                _this.close();
                                lefttoolbar.fdbacktip.show();
                                setTimeout(function() {
                                    lefttoolbar.fdbacktip.hide();
                                },2000);
                            },3000);
                        }
                    }
                });

            }
        },
        //验证
        verify:function() {
            if($.trim(this.input1.val()) === '') {
                this.panel1.find('.verify-err').show();
                return false;
            }
            return true;
        }
    };


    //ajax request ，主要用户活动全网寻宝功能
    window.hddata = 0;
    (function() {
        var times = location.href.indexOf('huodong.php?mod=zajindan') !== -1 ? 0 : 15000;
        setTimeout(function() {
            var _url = encodeURIComponent(location.href);
            $.ajax({
                url:'http://www.b5m.com/hd-ajax.php',
                data: {mod:'index',url:_url},
                dataType: "jsonp",
                jsonp: 'jsonpCallback',
                success: function(data) {

                    window.hddata = data.code;
                    window.hddatacount = data.data;
                    window.hddatatime = data['time_end'];
                    if(window.hddata==='1') {
                        activeFed.loadFun();
                    }
                }
            });
        },times);
    })();



/*************************hack调整**************************/
(function() {

    //IE6
    if(typeof document.body.style.maxHeight ==='undefined') {
        //顶部提示下载
        $('body').prepend('<div class="topbar-ie6">您的浏览器版本过旧，推荐您使用更快更安全的360浏览器 <a href="http://se.360.cn/" target="_blank">立即下载</a></div>');
    }


})();



    /* bottom banner */
    b5m.namespace('ui.bottomBanner');
    b5m.ui.bottomBanner.init = function() {

    };

   b5m.ui.bottomBanner.init();


})(window, window.jQuery);