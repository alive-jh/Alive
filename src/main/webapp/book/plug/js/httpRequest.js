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
    }
};


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
    _windowHTML: function (id) {
        return '<div class="jq-popup-window jq-mask-content" id="' + id + '">\
            <div class="tool-bar"><i class="close-btn"></i></div>\
                <div class="content-area"></div>\
            </div>';
    },
    _adjustPosition: function () {
        var w = $(".jq-popup-window");
        w.css("margin-top", w.height() / -2 + "px");
    },
    /*移除/关闭，只移除指定ID元素*/
    _close: function (maskID, contentID) {
        var s = "#" + maskID + ",#" + contentID;
        $(s).hide().remove();
    },

    /*移除/关闭，移除所有蒙版和弹窗*/
    close: function () {
        $(".jq-mask,.jq-mask-content").hide().remove();
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

function httpRequest(url, data, successCallback, hasMask, options) {
    if (hasMask == undefined) hasMask = true;
    var _default = {
        url: url,
        type: "GET",
        data: data,
        timeout: 5000,
        beforeSend: function (XHR) {
            if (hasMask) {
                jqPopup.progress("网络请求中...");
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