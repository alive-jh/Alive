/**
 * Created by chaozhong on 2015/12/21.
 */
window.jqValidate = (function () {
    var REGULAR = {
        email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/g,
        //手机号码
        mobile: /^1\d{10}$/,
        //所有电话号码（手机、座机）
        phone: /^((\d{3,4}-)|\d{3,4})?\d{7,8}$/
    };

    return {
        email: function (str) {
            return REGULAR.email.test(str);
        },
        mobile: function (str) {
            return REGULAR.mobile.test(str);
        },
        phone: function (str) {
            return REGULAR.phone.test(str);
        }
    };
})();;
/**
 * 定义一些通用方法
 * Created by chaozhong on 2015/11/13.
 */
window.jqTools = {
    /*预处理接口返回数据*/
    pretreatment: function (originalData) {
        var data = {};
        try {
            if (originalData && typeof originalData === "string") {
                data = JSON.parse(originalData);
            } else {
                data = originalData;
            }
        } catch (e) {
            throw new Error(e.message);
        }
        return data;
    },

    /*打开新页面*/
    openPage: function (url, parameters) {
        try {
            var isFirst = true;
            for (var key in parameters) {
                if (isFirst) {
                    url += ("?" + key + "=" + parameters[key]);
                    isFirst = false;
                } else {
                    url += ("&" + key + "=" + parameters[key]);
                }
            }
            window.location.href = url;
        } catch (e) {
            throw new Error(e.message);
        }
    },

    /*获取URL参数*/
    getRequestParameters: function () {
        var url;
        try {
            url = decodeURI(location.search);
        } catch (e) {
            url = location.search;
        }
        var parameters = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                parameters[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
            }
        }
        return parameters;
    },

    /*格式化日期时间*/
    formateDate: function (date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();

        if (month < 10) month = "0" + month;
        if (day < 10) day = "0" + day;
        if (hour < 10) hour = "0" + hour;
        if (minute < 10) minute = "0" + minute;
        if (second < 10) second = "0" + second;
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    },

    /*获取当前星期*/
    getWeek: function () {
        var dayNames = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
        return dayNames[new Date().getDay()];
    },

    /*根据相对路径获取绝对路径*/
    getAbsoluteUrl: function (url) {
        var img = new Image();
        img.src = url; // 设置相对路径给Image, 此时会发送出请求
        url = img.src; // 此时相对路径已经变成绝对路径
        img.src = null; // 取消请求
        return url;
    },

    /*判断是否是微信环境*/
    isWeChat: function () {
        return navigator.userAgent.toLowerCase().indexOf('micromessenger') >= 0;
    },

    /*填充页面,根据key值设置页面中拥有data-inflate属性且值为key的元素的value属性值为key对应的value*/
    inflate: function (data) {
        for (var key in data) {
            var value = data[key];
            var ele = $("[data-inflate='" + key + "']");
            if (ele.is(":checkbox")) {
                var checkedList = value.split(",");
                $.each(checkedList, function (index, item) {
                    ele.filter("[value='" + item + "']").attr("checked", "checked");
                });
            } else if (ele.is(":radio")) {
                ele.filter("[value='" + value + "']").attr("checked", "checked");
            } else {
                ele.val(value);
            }
        }
    }
};
;
/**
 * 通过JavaScript动态控制页面外观
 * Created by chaozhong on 2015/11/9.
 */
var jqContentScroll;//用于刷新需要
document.addEventListener("DOMContentLoaded", function () {
    /*设置内容区域高度，使之填充除头部和尾部剩余空间*/
    var layouts = document.getElementsByClassName("jq-layout");
    if (layouts.length > 0) {
        var index = 0, L = layouts[index];
        while (L) {
            var childNodes = L.childNodes;
            for (var i = 0; i < childNodes.length; i++) {
                if (childNodes[i].nodeType !== 1) continue;
                if (childNodes[i].nodeName === "HEADER" && window.getComputedStyle(childNodes[i], null).display === "block") {
                    L.querySelector(".jq-content").style.top = window.getComputedStyle(childNodes[i], null).height;
                }
                if (childNodes[i].nodeName === "FOOTER" && window.getComputedStyle(childNodes[i], null).display === "block") {
                    L.querySelector(".jq-content").style.bottom = window.getComputedStyle(childNodes[i], null).height;
                }
            }
            L = layouts[++index];
        }
    }

    /*设置可滚动区域高度，目的是使内容区被占满*/
    var scrollContainer = $(".jq-scroll-container");
    if (scrollContainer.length > 0) {
        for (var wrapperIndex = 0; wrapperIndex < scrollContainer.length; wrapperIndex++) {
            //计算前面兄弟元素的总高度
            var top = 0;
            var bottom = 0;
            var prevNodes = scrollContainer.eq(wrapperIndex).prevAll();
            var nextNodes = scrollContainer.eq(wrapperIndex).nextAll();
            for (var j = 0; j < prevNodes.length; j++) {
                top += prevNodes.eq(j).height();
            }
            for (var k = 0; k < nextNodes.length; k++) {
                bottom += nextNodes.eq(k).height();
            }
            scrollContainer.eq(wrapperIndex).css("top", top + "px").css("bottom", bottom + "px");
        }
    }

    /*
     * 若content容器中只有一个子元素，且该子元素有jq-scroll-wrapper类，
     * 则将content容器设置成iScroll对象，滚动内容为子元素
     * */
    var scrollWrappers = $(".jq-scroll-wrapper");
    if (iScroll && scrollWrappers.length && !scrollWrappers.siblings().length) {
        document.addEventListener("touchmove", function (e) {
            e.preventDefault();
        }, false);

        jqContentScroll = new iScroll(document.getElementsByClassName("jq-content")[0]);

        $("input,textarea").blur(function () {
            jqContentScroll.refresh();
        }).focus(function () {
            jqContentScroll.refresh();
        });
    }
}, false);

window.addEventListener("load", function () {
    if (jqContentScroll) {
        jqContentScroll.refresh();
    }
}, false);;
/**
 * 定义一些jQuery插件
 * Created by chaozhong on 2015/11/10.
 */

document.addEventListener("DOMContentLoaded", function () {
    /*
     * 表单验证
     * */
    $(".jq-input").focus(function () {
        $(this).removeClass("invalid");
    });
    $(".jq-input[required]").blur(function () {
        if (!$(this).val()) $(this).addClass("invalid");
    });
}, false);

/*
 * 列表形式菜单
 * */
$.fn.jqMenuList = function (menuData) {
    var i = 0, data = menuData[i];
    while (data) {
        var html = '<li class="jq-menu-list-item">\
                <div class="icon" style="background-image:url(\'' + data.icon + '\')"></div>\
                <span class="name">' + data.name + '</span>\
                <i class="fa fa-angle-right pointer"></i>\
            </li>';
        $(this).append(html);
        $(this).children("li").last().bind("click", data.clickListener);
        data = menuData[++i];
    }
};

/*
 * 表单验证
 * */
$.fn.jqFormValidate = function () {
    var result = true;
    if ($(this).get(0).nodeName !== "FORM") throw new Error("非FORM表单！不能使用本方法！");
    //输入类型判断
    $(this).find("input,textarea").each(function () {
        //1、不能为空判断
        if ($(this).get(0).hasAttribute("required") && !$(this).val()) {
            result = false;
            $(this).addClass("invalid");
        }
        //TODO 2、？？
    });
    //TODO 选择类型判断

    return result;
};


/*
 * 获取表单填写数据
 * */
$.fn.jqFormData = function () {
    var result = {};
    if ($(this).get(0).nodeName !== "FORM") throw new Error("非FORM表单！不能使用本方法！");

    //获取拥有jq-input属性的输入框的值
    $(this).find(".jq-input").each(function () {
        if ($(this).is(":checkbox")) {
            if ($(this).is(":checked")) {
                if (!result.hasOwnProperty($(this).attr("name"))) {
                    result[$(this).attr("name")] = [];
                }
                result[$(this).attr("name")].push($(this).val());
            }
        } else if ($(this).is(":radio")) {
            if ($(this).is(":checked")) {
                result[$(this).attr("name")] = $(this).val();
            }
        } else {
            result[$(this).attr("name")] = $(this).val();
        }
    });

    return result;
};

/*
 * 获取图片列表数据
 * */
$.fn.jqImgListData = function () {
    var result = {};
    //图片
    var picturePath = "";
    var pictureName = "";
    $(this).find("li[data-path]").each(function (index) {
        var path = $(this).data().path;
        if (index == 0) {
            picturePath += path;
            pictureName += path.substring(path.lastIndexOf("/") + 1);
        } else {
            picturePath += ("," + path);
            pictureName += ("," + path.substring(path.lastIndexOf("/") + 1));
        }
    });
    result.picturePath = picturePath;
    result.pictureName = pictureName;
    return result;
};

/*
 * 弹出窗口形式的选择器
 * */
$.fn.jqPopupSelect = function (selectableInfo, callback) {
    var _thisEle = $(this);
    _thisEle.unbind("click");
    _thisEle.click(function () {
        _thisEle.children("input[type='text']").removeClass("invalid");//解决表单验证的逻辑bug
        //对当前选择项添加选中样式
        for (var i = 0; i < selectableInfo.length; i++) {
            selectableInfo[i].selected = (_thisEle.children("input[type='text']").val() === selectableInfo[i].name);
        }
        jqPopup.singlePopupSelect(selectableInfo, callback ? callback : function (value, name) {
            _thisEle.children("input[type='text']").val(name);
            _thisEle.children("input[type='hidden']").val(value);
        });
    });
};

/*
 * 弹出窗口形式的选择器(多选)
 * */
$.fn.jqPopupMultiSelect = function (selectableInfo, callback) {
    var _thisEle = $(this);
    _thisEle.unbind("click");
    _thisEle.click(function () {
        jqPopup.multiPopupSelect(selectableInfo, callback);
    });
};

/*
 * 底部滑出单选选择器
 * */
$.fn.jqSlipSelect = function (selectableInfo, callback) {
    var _thisEle = $(this);
    _thisEle.unbind("click");
    _thisEle.click(function () {
        jqPopup.singleSlipSelect(selectableInfo, callback);
    });
};


var jqWidget = {
    /*
     * 上拉加载、下拉刷新列表
     * */
    jqList: function (ele, pullDownAction, pullUpAction) {
        //阻止默认触屏滑动行为
        document.addEventListener('touchmove', function (e) {
            e.preventDefault();
        }, false);

        var pullDownEl, pullDownOffset, pullUpEl, pullUpOffset;
        pullDownEl = document.getElementById('pullDown');
        pullDownOffset = pullDownEl.offsetHeight;
        pullUpEl = document.getElementById('pullUp');
        pullUpOffset = pullUpEl.offsetHeight;
        var _scoll = new iScroll(ele, {
            useTransition: true,
            topOffset: pullDownOffset || 0,
            onRefresh: function () {
                if (pullDownEl.className.match('loading')) {
                    pullDownEl.className = '';
                    pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
                } else if (pullUpEl.className.match('loading')) {
                    pullUpEl.className = '';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
                }
            },
            onScrollMove: function () {
                if (this.y > 5 && !pullDownEl.className.match('flip')) {
                    pullDownEl.className = 'flip';
                    pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Release to refresh...';
                    this.minScrollY = 0;
                } else if (this.y < 5 && pullDownEl.className.match('flip')) {
                    pullDownEl.className = '';
                    pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
                    this.minScrollY = -pullDownOffset;
                } else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
                    pullUpEl.className = 'flip';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Release to refresh...';
                } else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
                    pullUpEl.className = '';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
                    this.maxScrollY = pullUpOffset;
                }
            },
            onScrollEnd: function () {
                if (pullDownEl.className.match('flip')) {
                    pullDownEl.className = 'loading';
                    pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Loading...';
                    pullDownAction();
                } else if (pullUpEl.className.match('flip')) {
                    pullUpEl.className = 'loading';
                    pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Loading...';
                    pullUpAction();
                }
            }
        });
        pullDownAction();
        return _scoll;
    }
};
;
/**
 * 定义一些常用“弹出”控件
 * Created by chaozhong on 2015/11/11.
 */
window.jqPopup = {
    _default: {
        hasMask: true,
        timeout: 1000,
        content: ""
    },
    _maskHTML: function (id) {
        return '<div class="jq-mask" id="' + id + '"></div>';
    },
    _windowHTML: function (id, hasBtn) {
        var html = [];
        html.push('<div class="jq-popup-window jq-mask-content" id="' + id + '">');
        html.push('<div class="tool-bar"><i class="close-btn"></i></div>');
        html.push('<div class="content-area"></div>');
        if (hasBtn) {
            html.push('<div class="btn-area">');
            html.push('<a class="btn cancel" href="javascript:void(0);">取消</a><a class="btn submit" href="javascript:void(0);">确定</a>');
            html.push('</div>');
        }
        html.push('</div>');
        return html.join("\n");
    },
    _adjustPosition: function () {
        var w = $(".jq-popup-window");
        w.css("margin-top", w.height() / -2 + "px");
    },
    /*移除/关闭，只移除指定ID元素*/
    _close: function (maskID, contentID) {
        var s = "#" + maskID + ",#" + contentID;
        $(s).fadeOut().remove();
    },

    /*移除/关闭，移除所有蒙版和弹窗*/
    close: function () {
        $(".jq-mask,.jq-mask-content").fadeOut().remove();
    },

    /*消息弹框*/
    message: function (message, options, callback) {
        var maskID = "M" + new Date().getTime();
        var contentID = "C" + new Date().getTime();

        var _options = {};
        $.extend(_options, jqPopup._default, options);

        if (_options.hasMask) {
            $("body").append(jqPopup._maskHTML(maskID));
        }
        $("body").append(jqPopup._windowHTML(contentID));
        $(".content-area").append('<p class="message">' + message + '</p>');
        jqPopup._adjustPosition();

        $(".close-btn").click(function () {
            jqPopup._close(maskID, contentID);
            if (callback) callback();
        });

        setTimeout(function () {
            jqPopup._close(maskID, contentID);
            if (callback) callback();
        }, _options.timeout);
    },

    /*模拟Android Toast提示*/
    toast: function (content, timeout) {
        $("body").append('<div class="jq-toast"><p><span>' + content + '</span></p></div>');
        setTimeout(function () {
            $(".jq-toast").fadeOut().remove();
        }, timeout ? timeout : 1000);
    },

    /*弹出一个可关闭窗口*/
    window: function (options) {
        var maskID = "M" + new Date().getTime();
        var contentID = "C" + new Date().getTime();
        var _options = {};
        $.extend(_options, jqPopup._default, options);
        if (_options.hasMask) {
            $("body").append(jqPopup._maskHTML(maskID));
        }
        $("body").append(jqPopup._windowHTML(contentID));
        $(".content-area").append(_options.content);
        jqPopup._adjustPosition();

        $(".close-btn").click(function () {
            jqPopup._close(maskID, contentID);
        });
    },

    //进度显示
    progress: function (msg) {
        $("body")
            .append(jqPopup._maskHTML)
            .append('<div class="jq-progress jq-mask-content"><div><i class="fa fa-spinner jq-circle"></i><p>'
            + msg + '</p></div></div>');
    },

    /*
     * 弹出式单选框
     * @param data [{name:"",value:"",selected:true}]
     * @callback(value,name)
     * */
    singlePopupSelect: function (data, callback) {
        var maskID = "M" + new Date().getTime();
        var contentID = "C" + new Date().getTime();
        var html = '<ul class="jq-popup-select-options">';
        for (var i = 0; i < data.length; i++) {
            html += ('<li class="' + (data[i].selected ? 'selected' : '')
            + ' " data-value="' + data[i].value + '" " data-name="' + data[i].name + '">'
            + data[i].name + '<i class="fa fa-check"></i></li>');
        }
        html += '</ul>';

        $("body").append(jqPopup._maskHTML(maskID)).append(jqPopup._windowHTML(contentID));
        $(".content-area").append(html);
        jqPopup._adjustPosition();

        $(".close-btn").click(function () {
            jqPopup._close(maskID, contentID);
        });

        $(".jq-popup-select-options li").click(function () {
            $(".jq-popup-select-options .selected").removeClass("selected");
            $(this).addClass("selected");
            callback($(this).data().value, $(this).data().name);
            jqPopup._close(maskID, contentID);
        });

        new iScroll(document.querySelector("#" + contentID + " .content-area"));
    },


    /*
     * 弹出式多选选框
     * @param data [{name:"",value:"",selected:true}]
     * @callback(value,name)
     * */
    multiPopupSelect: function (data, callback) {
        var maskID = "M" + new Date().getTime();
        var contentID = "C" + new Date().getTime();
        var html = '<ul class="jq-popup-select-options">';
        for (var i = 0; i < data.length; i++) {
            html += ('<li class="' + (data[i].selected ? 'selected' : '')
            + ' " data-value="' + data[i].value + '" " data-name="' + data[i].name + '">'
            + data[i].name + '<i class="fa fa-check"></i></li>');
        }
        html += '</ul>';

        $("body").append(jqPopup._maskHTML(maskID)).append(jqPopup._windowHTML(contentID, true));
        $(".content-area").append(html);
        jqPopup._adjustPosition();

        $(".jq-popup-select-options li").click(function () {
            if ($(this).hasClass("selected")) {
                $(this).removeClass("selected");
            } else {
                $(this).addClass("selected");
            }
        });

        $(".close-btn,.btn.cancel").click(function () {
            jqPopup._close(maskID, contentID);
        });

        $(".btn.submit").click(function () {
            var selected = [];
            $(".jq-popup-select-options .selected").each(function (index) {
                selected.push({name: $(this).data().name, value: $(this).data().value});
            });
            callback(selected);
            jqPopup._close(maskID, contentID);
        });

        new iScroll(document.querySelector("#" + contentID + " .content-area"));
    },


    /*
     * 确认对话框
     * */
    comfirm: function (message, callback) {
        var maskID = "M" + new Date().getTime();
        var contentID = "C" + new Date().getTime();

        $("body").append(jqPopup._maskHTML(maskID)).append(jqPopup._windowHTML(contentID, true));
        $(".content-area").append('<p class="message">' + message + '</p>');
        jqPopup._adjustPosition();

        $(".close-btn,.btn.cancel").click(function () {
            jqPopup._close(maskID, contentID);
        });

        $(".btn.submit").click(function () {
            jqPopup._close(maskID, contentID);
            callback();
        });
    },

    /*
     * 底部滑出选择器
     * */
    singleSlipSelect: function (data, callback) {
        var maskID = "M" + new Date().getTime();
        var contentID = "C" + new Date().getTime();
        var selectableItems = "";
        for (var i = 0; i < data.length; i++) {
            selectableItems += ('<li data-name="' + data[i].name
            + '" data-value="' + data[i].value + '">'
            + data[i].name + '</li>');
        }
        var html = '<div class="jq-slip-select jq-mask-content"><ul>' + selectableItems
            + '</ul><a href="javascript:void(0);">取消</a></div>';
        $("body").append(jqPopup._maskHTML(maskID)).append(html);

        $(".jq-slip-select").slideToggle();

        $(".jq-slip-select>a").click(function () {
            $(".jq-slip-select").slideToggle(function () {
                jqPopup._close(maskID, contentID);
            });
        });
        $(".jq-slip-select>ul>li").click(function () {
            callback($(this).data().value, $(this).data().name);
            $(".jq-slip-select").slideToggle(function () {
                jqPopup._close(maskID, contentID);
            });
        });
    }
};

/*
 * AJAX网络请求
 * @param url 接口地址
 * @param data 接口参数（JSON对象）
 * @param successCallback 请求成功回调方法
 * @param hasMask 布尔值，请求时候是否有蒙版（默认有）
 * @param options 可选配置，可配置信息参见jQuery ajax 方法（JSON对象）；
 * */
function httpRequest(url, data, successCallback, hasMask, options) {
    if (hasMask == undefined) hasMask = true;
    var _default = {
        url: url,
        type: "GET",
        data: data,
        timeout: 20000,
        beforeSend: function (XHR) {
            if (hasMask) {
                jqPopup.progress("努力加载中...");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (hasMask) {
                jqPopup.close();
            }
            if (textStatus === "error") {
                jqPopup.message("网络请求出错！请检查您的请求是否正确", {hasMask: true, timeout: 2000});
            } else if (textStatus === "timeout") {
                jqPopup.message("请求超时，请检查您的网络！", {hasMask: true, timeout: 2000});
            } else {
                jqPopup.message("出错了！网络请求出错！", {hasMask: true, timeout: 2000});
            }
        },
        success: function (data, textStatus) {
            if (hasMask) {
                jqPopup.close();
            }
            if (successCallback) successCallback(jqTools.pretreatment(data), textStatus);
        }
    };
    var settings = {};
    $.extend(settings, _default, options);
    $.ajax(settings);
};
/**
 * Created by chaozhong on 2016/1/4.
 */
$.fn.jqScrollTable = function (param, data) {
    if (!param.height) {
        $(this).css("height", "100%");
    }

    var _config = {
        "height": $(this).height() - 2,
        "columns": [],
        "data": data
    };

    var config = {};
    $.extend(config, _config, param);

    $(this).addClass("jq-scroll-table");
    $(".jq-scroll-table").append('' +
        '<tr>' +
        '   <td class="left-part">' +
        '       <div class="title-format"></div>' +
        '       <div class="row-title-box">' +
        '           <table></table>' +
        '       </div>' +
        '   </td>' +
        '   <td class="right-part">' +
        '       <div class="column-title-box">' +
        '           <table><tr></tr></table>' +
        '       </div>' +
        '       <div class="data-area">' +
        '           <table></table>' +
        '       </div>' +
        '   </td>' +
        '</tr>');

    var $rowTitle = $(".row-title-box>table");
    var $columnTitle = $(".column-title-box>table tr");
    var $dataTable = $(".data-area>table");
    var $dataArea = $(".data-area");

    //第一列，行名
    for (var rowIndex = 0; rowIndex < config.data.length; rowIndex++) {
        $rowTitle.append('<tr><td><div class="row-title">' + config.data[rowIndex][config.columns[0].field] + '</div></td></tr>');
    }
    //第一行，列名
    $(".title-format").append(config.columns[0].title);
    for (var columnIndex = 1; columnIndex < config.columns.length; columnIndex++) {
        $columnTitle.append('<td><div class="column-title" style="width: '
            + config.columns[columnIndex].width + 'px;">'
            + config.columns[columnIndex].title + '</div></td>');
    }

    //填充数据区
    for (var dataIndex = 0; dataIndex < config.data.length; dataIndex++) {
        var trHTML = "";
        trHTML += "<tr>";
        for (var i = 1; i < config.columns.length; i++) {
            trHTML += '<td><div class="data-area-item">' + config.data[dataIndex][config.columns[i].field] + '</div></td>';
        }
        trHTML += "</tr>";
        $dataTable.append(trHTML);
    }

    $dataArea.height(config.height - 40);
    $(".left-part").width(config.columns[0].width);
    $(".column-title-box").height(40).css("line-height", "40px");
    $(".title-format").height(40).width(config.columns[0].width);
    $(".row-title-box").height($dataArea.height());

    $(".data-area>table tr").each(function () {
        $(this).find(".data-area-item").each(function (i) {
            var columnTitle = $(".column-title").eq(i);
            if (columnTitle.width() < $(this).width()) {
                columnTitle.width($(this).width());
            } else {
                $(this).width(columnTitle.width());
            }
        });
    }).each(function (index) {
        $(".row-title").eq(index).height($(this).find("td").height() + 1).css("line-height", $(this).find("td").height() + 1 + "px");
    });

    $dataArea.bind("touchmove", function () {
        $(".row-title-box>table").css("margin-top", $(this).scrollTop() * -1 + "px");
        $(".column-title-box>table").css("margin-left", $(this).scrollLeft() * -1 + "px");
    });
    $dataArea.bind("touchend", function () {
        $(".row-title-box>table").css("margin-top", $(this).scrollTop() * -1 + "px");
        $(".column-title-box>table").css("margin-left", $(this).scrollLeft() * -1 + "px");
    });
};;
/**
 * Created by chaozhong on 2016/2/19.
 */
window.jqContact = {
    multiSelect: function (data, callback) {
        /*
         * 层级信息堆栈
         * 一级信息展开显示后，就压入堆栈
         * 栈顶元素为当前显示的信息
         * */
        var levelStack = [];
        //已选人员
        var selected = [];
        //待选信息滑动对象
        var selectableScroll;
        //已选人员显示区域滑动对象
        var selectedContactsScroll;

        if (typeof data.selectableInfo.personal === "function") {
            data.selectableInfo.personal(function (result) {
                levelStack.push(result);
                init(result);
            });
        } else {
            levelStack.push(data.selectableInfo.personal);
            init(data.selectableInfo.personal);
        }

        function init(initData) {
            var html = [];
            html.push('<section class="jq-layout contact-container">');
            html.push('<header>');
            html.push('<a href="javascript:void(0);" class="left-btn"><i class="fa fa-angle-left"></i></a>');
            html.push('<span class="title">请选择人员</span>');
            html.push('<a href="javascript:void(0);" class="right-btn selected-confirm"></a>');
            html.push('</header>');
            html.push('<div class="searcher-container" id="search-people-container">');
            html.push('<input type="search" class=""/>');
            html.push('<span class="searcher-icon">');
            html.push('<i class="fa fa-search"></i>');
            html.push('搜索</span>');
            html.push('</div>');
            html.push('<div class="jq-mask1"  id="search-people-mask"></div>');
            html.push('<div class="searcher-on-mask"  id="search-people-on-mask">');
            html.push('<input type="search"/>');
            html.push('<span class="clear">');
            html.push('<i class="fa fa-close"></i>');
            html.push('</span>');
            html.push('<a href="javascript:void(0);" class="btn" id="search-person-btn">搜索</a>');
            html.push('</div>');
            html.push('<section class="contact-type"><div>');
            html.push(createTypeHTML());
            html.push('</div></section>');
            //html.push('<p class="selectable-title">可选人员</p>');
            html.push('<div class="jq-content selectable-contacts-wrapper">');
            html.push('<ul class="jq-menu-list">');
            html.push(createHTML(initData));
            html.push('<ul>');
            html.push('</div>');
            html.push('<footer class="selected-contacts-wrapper">');
            html.push('<ul class="selected-contacts">');
            html.push('</ul>');
            html.push('</footer>');
            html.push('</section>');
            $("body").append(html.join("\n"));

            //引用fastClick插件
            FastClick.attach(document.body);

            $(".selected-contacts").width(recountSelectedContactsScrollWidth());
            selectableScroll = new iScroll(document.getElementsByClassName("selectable-contacts-wrapper")[0], {
                click: true
            });
            selectedContactsScroll = new iScroll(document.getElementsByClassName("selected-contacts-wrapper")[0], {
                vScroll: false,
                click: true,
                hScrollbar: false,
                vScrollbar: false
            });

            //列表点击事件
            $(".contact-container .jq-menu-list-item").unbind("click").bind("click", function () {
                clickHandler($(this),$(this).data().index);
            });

            //返回按钮点击事件
            $(".contact-container .left-btn").click(function () {
                levelStack.pop();
                if (levelStack.length) {
                    $(".jq-menu-list").empty().append(createHTML(levelStack[levelStack.length - 1]));
                    selectableScroll.refresh();
                    $(".contact-container .jq-menu-list-item").unbind("click").bind("click", function () {
                        clickHandler($(this),$(this).data().index);
                    });
                } else {
                    $(".contact-container").remove();
                }
            });

            //点击确定
            $(".contact-container .right-btn").click(function () {
                $(".contact-container").remove();
                callback(selected);
            });

            //个人/企业通讯录切换
            $("#corporate-contact,#personal-contact").click(function () {
                $(".contact-type .selected").removeClass("selected");
                $(this).addClass("selected");
                var type;
                if ($(this).attr("id") === "corporate-contact") {
                    type = data.selectableInfo.corporate;
                }else {
                    type = data.selectableInfo.personal;
                }
                levelStack = [];
                if (typeof type === "function") {
                    type(function (result) {
                        levelStack.push(result);
                        $(".jq-menu-list").empty().append(createHTML(result));
                        selectableScroll.refresh();
                        $(".contact-container .jq-menu-list-item").unbind("click").bind("click", function () {
                            clickHandler($(this),$(this).data().index);
                        });
                    });
                } else {
                    levelStack.push(type);
                    $(".jq-menu-list").empty().append(createHTML(type));
                    selectableScroll.refresh();
                    $(".contact-container .jq-menu-list-item").unbind("click").bind("click", function () {
                        clickHandler($(this),$(this).data().index);
                    });
                }
                selectableScroll.refresh();
            });



            //搜索部分

            //1、隐藏遮罩层
            //2、点击输入框显示遮罩
            // 3、遮罩中的输入框有值时隐藏删除按钮/隐藏搜索图标
            // 4、点击删除按钮清除输入框内容
            // 5、点击搜索按钮开始搜索，查询数据，显示数据
            hideMask(true);

            //点击蒙版取消搜索
            $('#search-people-mask').click(function(){
                $('#search-people-on-mask input').val('');
                hideMask(true);
            });

            $('#search-people-container input,#search-people-container span').click(function(){
                hideMask(false);
                $('#search-people-on-mask .clear').hide();
                $('#search-people-on-mask input').focus();
            });

            //输入框输入内容
            $('#search-people-on-mask input').on('keyup',function(){
                if($(this).val()){
                    $('#search-people-on-mask .clear').show();
                    $('#search-people-container .searcher-icon').hide();
                }else{
                    $('#search-people-on-mask .clear').hide();
                    $('#search-people-container .searcher-icon').show();
                }
                $('#search-people-container input').val($(this).val());
                $('#search-people-container input').attr('value',$(this).val());
            });

            //清除输入框内容

            $('#search-people-on-mask .clear').click(function(){
                $('#search-people-on-mask input').val('');
                $('#search-people-on-mask input').trigger('keyup');
            });

            //开始搜索
            $('#search-people-on-mask .btn').click(function(){
                var keyword=$('#search-people-on-mask input').val();
                //以keywork为关键字查询数据
                //TODO

                levelStack = [];
                data.selectableInfo.searchPerson(keyword,function (result) {
                    levelStack.push(result);
                    $(".jq-menu-list").empty().append(createHTML(result));
                    selectableScroll.refresh();
                    $(".contact-container .jq-menu-list-item").unbind("click").bind("click", function () {
                        clickHandler($(this), $(this).data().index);
                    });
                    setTimeout(function(){selectableScroll.refresh()},1000);
                    $('#search-people-container input').val('');
                    $('#search-people-on-mask input').val('');
                    $('#search-people-container .searcher-icon').show();
                    hideMask(true);
                });

            });
        }


        function recountSelectedContactsScrollWidth() {
            var width = 0;
            $(".selected-contacts li").each(function () {
                width += ($(this).width() + 20);
            });
            return width;
        }


        //生成企业/个人通讯录切换按钮
        function createTypeHTML() {
            if (data.selectableInfo.personal) {
                return '<span id="corporate-contact">企业通讯录</span><span class="selected" id="personal-contact">常用联系人</span>';
            } else {
                return '<span class="selected" style="width: 100%;">通讯录</span>';
            }
        }

        //生成可选内容
        function createHTML(data) {
            var items = [];
            for (var j = 0; j < data.length; j++) {
                if (true||data[j].isEnd) {//TODO 判断是否是企业通讯员

                    items.push('<li class="jq-menu-list-item staff" data-index="' + j + '">');
                    items.push('<div class="check-group">');
                    items.push('<input type="checkbox"/>');
                    items.push('<i class="check-mark"></i>');
                    items.push('</div>');
                    items.push('<a href="javascript:void(0);">');
                    items.push('<i class="head-img '+data[j].sex+'"></i>');
                    items.push('<div><h3>'+data[j].name+'</h3>');
                    items.push('<p>'+data[j].phoneList[0]+'</p>');
                    items.push('</div></a>');
                    items.push('</li>');
                } else {
                    items.push('<li class="jq-menu-list-item" data-index="' + j + '">');
                    items.push('<span class="name">' + data[j].name + '</span>');
                    items.push('<i class="fa fa-angle-right pointer"></i>');
                    items.push('</li>');
                }
            }
            return items.join("\n");
        }

        //显示已选人员
        function showSelected(deleteable) {
            var selectedHTML = [];
            for (var i = 0; i < selected.length; i++) {
                selectedHTML.push('<li data-unid="' + selected[i].unid + '" class="'+selected[i].sex+'' + (deleteable ? " jq-long-press" : "") + '">' + selected[i].name + '</li>');
            }
            $(".contact-container .selected-contacts").empty().append(selectedHTML.join("\n"));
            $(".selected-contacts").width(recountSelectedContactsScrollWidth());
            $(".selected-contacts li").bind("click", function () {
                if ($(this).hasClass("jq-long-press")) {
                    var unid = $(this).data().unid;
                    var temp = [];
                    $.each(selected, function (index, item) {
                        if (item.unid != unid) {
                            temp.push(item);
                        }
                    });
                    selected = temp;
                    showSelected(true);
                }
            }).jqLongPress();//添加长按事件
            selectedContactsScroll.refresh();
        }

        //选人点击事件（点部门或人员）
        function clickHandler(targetObj,index) {
            var clickedContact = levelStack[levelStack.length - 1][index];

            if (true||clickedContact.isEnd) {//TODO 判断是否是企业通讯录
                var contain = false;
                for (var i = 0; i < selected.length; i++) {
                    if (selected[i].unid === clickedContact.unid) {
                        contain = true;
                        break;
                    }
                }
                if (!contain) selected.unshift(clickedContact);
                $('.selected-confirm').html('完成('+selected.length+')人');
                showSelected();
                /*targetObj.toggleClass('selected');
                var contain = false;
                for (var i = 0; i < selected.length; i++) {
                    if (selected[i].unid === clickedContact.unid) {
                        contain = true;
                        break;
                    }
                }
                if (targetObj.hasClass('selected')) {
                    targetObj.find('input[type="checkbox"]').prop('checked', 'true');
                    selected.unshift(clickedContact);
                } else {
                    targetObj.find('input[type="checkbox"]').prop('checked', '');
                    selected.shift(clickedContact);
                }
                showSelected();*/
            } else {
                /*将当前层级信息压入栈顶*/
                levelStack.push(clickedContact.children);
                $(".jq-menu-list").empty().append(createHTML(clickedContact.children));
                selectableScroll.refresh();
                $(".contact-container .jq-menu-list-item").unbind("click").bind("click", function () {
                    clickHandler($(this),$(this).data().index);
                });
            }
        }

        //人员搜索事件

        function hideMask(isHide){
            if(isHide){
                $('#search-people-mask').hide();
                $('#search-people-on-mask').hide();
            }else{
                $('#search-people-mask').show();
                $('#search-people-on-mask').show();
            }
        }
    }
};;
/**
 * Created by chaozhong on 2016/2/25.
 *
 * 长按会在目标元素上添加jq-long-press类
 */
$.fn.jqLongPress = function () {
//长按事件相关配置
    var time = 0;
    var timer = null;
    var holdTime = 1100;//长按时长，超过这个时长才触发事件

    var _this = $(this);

    //点击屏幕任何其他地方，撤销长按效果
    document.body.addEventListener("click", function (event) {
        if (!event.target.className.match("jq-long-press")) {
            _this.removeClass("jq-long-press");
        }
    }, false);

    _this.bind("touchstart", function (event) {
        time = new Date().getTime();
        timer = setTimeout(function () {
            _this.addClass("jq-long-press");
        }, holdTime);
    });
    _this.bind("touchend", function (event) {
        if (new Date().getTime() - time < holdTime) {
            clearTimeout(timer);
        } else {
            event.preventDefault();
            event.stopPropagation();
        }
    });
    _this.bind("touchmove", function (event) {
        if (new Date().getTime() - time < holdTime) {
            clearTimeout(timer);
            timer = setTimeout(function () {
                _this.addClass("jq-long-press");
            }, holdTime);
        } else {
            event.preventDefault();
            event.stopPropagation();
        }
    });
};