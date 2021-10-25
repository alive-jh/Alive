var ua = window.navigator.userAgent.toLowerCase();
//微信
if (ua.match(/MicroMessenger/i) == 'micromessenger') {
    window.location.href = 'downLoadForPhone';
} else {//非微信浏览器
    if (navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {
        // var loadDateTime = new Date();
        // window.setTimeout(function () {
        //     var timeOutDateTime = new Date();
        //     if (timeOutDateTime - loadDateTime < 5000) {
        //         window.location = "https://itunes.apple.com/cn/app/classin/id1226361488?mt=8";//ios下载地址
        //     } else {
        //         window.close();
        //     }
        // }, 2000);
        // window.location = "schema://";
        document.getElementById("download_msg").innerHTML = "<span>- 课程由专业外教直播平台提供，请前往App Store搜索“ClassIn”下载安装。-</span>";
    } else if (navigator.userAgent.match(/android/i)) {
        // var state = null;
        // try {
        //     window.location = 'weixin://';
        //     $('#btn2').hide();
        //     setTimeout(function () {
        //         window.location = "https://itunes.apple.com/cn/app/classin/id1226361488?mt=8"; //android下载地址
        //
        //     }, 500);
        // } catch (e) {
        // }
    }
}

function openApp() {
}