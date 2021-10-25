var result={};

var momentId=getQueryString('momentId');
var studentId=getQueryString('studentID');
var  type1 = getQueryString('type');
/*var momentId=363;*/
var myVideo;

var likeed=0;

window.onload = function () {
    getDate();
}

function isWeiXin(){
    return window.navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == 'micromessenger'
}

var product = 0;
var student = studentId;

function getDate(){
    $.ajax({
        url:"/snp/v2/student/grade/getMomentInfo",
        data:{
            "momentId":momentId
        },
        async: false,
        type:"get",
        success:function (res) {
            if(res.code==200){
                result=res;
                console.log(result);
                viewInit();
                console.log(res.data.product);
                if (res.data.product && isWeiXin()){
                    product = res.data.product;
                    $('.join').show();
                }
            }
        }
    })
}


function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute;
};



var likeCount;
function viewInit() {
    var html = '';
    var avBgSrc;
    var createTime;
    var className;
    var cover;
    var avatar;
    var userName;

    var zanIcon;
    var text;
    var ulList;
    var audioSrc;
    var uldisPlay = 'block';
    var textdisPlay = 'block';
    var audiodisPlay = 'block';

    if (result.data.mediaContent.pic.length == 0 && result.data.mediaContent.video.length == 0) {
        uldisPlay = 'none';
        //continue;
    } else if (result.data.mediaContent.pic == "" && result.data.mediaContent.video == "") {
        uldisPlay = 'none';
    } else {
        uldisPlay = 'block';
    }

    /*打卡时间*/
    createTime = formatDateTime(result.data.createTime);

    //课程名称
    className = result.data.className;

    //课程图片
    cover = (typeof result.data.cover) == "string" ? cover = result.data.cover : '../images/kaquan_merentu_bg.png';

    //用户头像
    avatar = (typeof result.data.avatar) == "string" ? result.data.avatar : '../images/defaultAvatar.png';
    //用户昵称
    userName = result.data.name;

    //点赞数
    likeCount = result.data.likes;

    /*点赞图标*/
    zanIcon = result.data.likesStatus != 1 ? '../images/kaquan_zan_icon.png' : '../images/kaquan_dianzhan_press_icon.png';


    if (result.data.text != "") {
        textdisPlay = 'block';
        text = result.data.text;
    } else {
        textdisPlay = "none";
    }

    ulList = '';
    if (result.data.mediaContent.pic.length != 0 && result.data.mediaContent.pic != "") {
        for (var j = 0; j < result.data.mediaContent.pic.length; j++) {
            ulList += '<li><img class="av_bg" src="' + result.data.mediaContent.pic[j] + '"  data-action="'+j+'" onclick="bigOrsmall(this)"></li>';
        }
    }
    if (result.data.mediaContent.video.length != 0 && result.data.mediaContent.video != "") {
        avBgSrc = result.data.mediaContent.video[0] + '?vframe/jpg/offset/1/w/100/h/176';
        for (var j = 0; j < result.data.mediaContent.video.length; j++) {
            ulList +=
                ' <li>\n' +
                '      <img class="av_bg" src="' + avBgSrc + '">\n' +
                '      <img class="play_btn" src="../images/play.png" data-url="' + result.data.mediaContent.video[j] + '" onclick="playVideo(this)">\n' +
                ' </li>';
        }
    }

    if (result.data.mediaContent.voice.length != 0 && result.data.mediaContent.voice != "") {
        audiodisPlay = "block";
        for (var j = 0; j < result.data.mediaContent.voice.length; j++) {
             audioSrc = result.data.mediaContent.voice[j];
        }
    } else {
        audiodisPlay = "none";
    }

    html +=
        ' <div class="moment" >\n' +
        '\n' +
        '        <div class="user_info">\n' +
        '            <img class="avatar" src="' + avatar + '">\n' +
        '\n' +
        '            <span class="user_name">' + userName + '</span>\n' +
        '            <span class="time">' + createTime + '</span>\n' +
        '\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="publish_text" style="display: ' + textdisPlay + '">\n' +
        '            <p class="paragraph">' + text + '</p>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="publish_pic_and_video" style="display: ' + uldisPlay + '">\n' +
        '                <ul>\n' +
        ulList +
        '                </ul>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="publish_audio" style="display: ' + audiodisPlay + '">\n' +
        '            <audio controls id="myAudio" class="myAudio" src="' + audioSrc + '"></audio>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="course">\n' +
        '            <div class="course_info">\n' +
        '                <img class="course_img" src="' + cover + '">\n' +
        '                <span class="course_name">' + className + '</span>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="comment_and_like">\n' +
        '            <div class="like">\n' +
        '                <span id="likeCount" class="like_counts">' + likeCount + '</span>\n' +
        '                <img id="likeIcon" class="like_icon"  src="../images/kaquan_zan_icon.png"   onclick="like(result.data.id)">\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '    </div>';

    $('#moment').html(html);
}


//点击图片放大
function bigOrsmall(e) {
    //var index = $(e).data("index");
    var actionIndex=$(e).data("action");
    var imgItems=[];
    for (var i=0;i<result.data.mediaContent.pic.length;i++){
        imgItems.push(result.data.mediaContent.pic[i]);
    }
    pb = $.photoBrowser({
        items:imgItems
    });
    pb.open(actionIndex);
    $('.weui-photo-browser-modal').on('click',function () {
        pb.close();
    })
}

function playVideo(e) {
    var src = $(e).data("url");
    window.location.href = src;
    console.log(src);
}


function like(momentId) {
    if (likeed!=1) {
    $.post('/wechat/v2/student/grade/isNotOrLike?userId=' + 0 + '&momentId=' + momentId, function (res) {
        if (res.code == 200) {
                $('#likeIcon').attr('src', '../images/kaquan_dianzhan_press_icon.png');
                $('#likeCount').text(++likeCount);
                likeed=1;
            }
    })
    }else {
        return;
    }
}



















