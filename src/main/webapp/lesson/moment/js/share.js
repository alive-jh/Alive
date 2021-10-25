
function shareLeply(studentID,momentId) {
    //获取momentId
    //获取studentID

    var message = {
        'shareUrl': 'http://shaonp.fandoutech.com.cn/wechat/lesson/punch/html/sharecirclip.html?studentID='+studentID+'&momentId='+momentId
    };
    try {
        window.webkit.messageHandlers.jsCallOCFunction.postMessage(message);
    } catch (e) {
        console.log(e)
    }
    try {
        share.wechatShare(message.shareUrl)
    } catch (e) {
        console.log(e)
    }
}
