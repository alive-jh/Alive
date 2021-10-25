function shareLeply(studentId,momentId,content,studentName,className,classCourseId) {
    //获取momentId
    //获取studentID
    var type = localStorage.getItem("deviceType");
    var localhostUrl = window.location.host;

    var message = {
        'shareUrl': localhostUrl+'/snp/lesson/punch/html/sharecirclip.html?courseId='+classCourseId+'&studentID='+studentId+'&momentId='+momentId+"&type="+type,
        'content':content,
        'studentName':studentName,
        'className':className
    };
    try {
        window.webkit.messageHandlers.jsCallOCFunction.postMessage(message);
    } catch (e) {
        console.log(e);
    }
    try {
        share.wechatShare(message.shareUrl,message.content,message.studentName,message.className);
    } catch (e) {
        console.log(e);
    }
}

function playVideo2(videoUrl) {
    console.log(videoUrl);
    var message = {
        'videoUrl': videoUrl
    };
    try {
        window.webkit.messageHandlers.jsCallOCPlayVideoFunction.postMessage(message);
    } catch (e) {
        console.log(e);
    }
    try {
        jsVideo.play(message.videoUrl);
    } catch (e) {
        console.log(e);
    }
}
