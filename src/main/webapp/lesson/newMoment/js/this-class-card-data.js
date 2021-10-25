history.scrollRestoration = 'manual';
var result = {};
var type;

var studentId = getQueryString('student');
var teacherId = getQueryString('teacher');
var sn = getQueryString('sn');//最新1.1.1
var  type = getQueryString('type');
var classRoomId = getQueryString('classRoom');
var searchVal=null;
/*//var my = getQueryString("my");
/!*var  studentId = 672;
var teacherId =0;
var sn ;
type =2;*!/*/

//卡圈点赞状态
var likeStatus=1;


if (type==null){
    type=1;
}

localStorage.setItem("deviceType",type);



var list = [];
var loading = false;  //状态标记
var pageNumber = 1; //初始页数
var allList = [], myList = [];
var myVideo;
var myAudio;
var my = 0; //所有打卡为0，我的打卡为1
var index = 0;//点击下标
var pb;

var scrollT = null;
var LastScrollT = 0;
window.onscroll = function (e) {
    scrollT = getScrollOffset().y;//滚动条距离
}

function getScrollOffset() {
    if (window.pageXOffset) {
        return {
            x: window.pageXOffset,
            y: window.pageYOffset
        }
    } else {
        return {
            x: document.body.scrollLeft || document.documentElement.scrollLeft,
            y: document.body.scrollTop || document.documentElement.scrollTop
        }
    }
};

function hideBodyScroll() {
    // 在这里获取滚动的距离，赋值给body，好让他不要跳上去。
    document.body.style.overflow = 'hidden';
    document.body.style.position = 'fixed';
    document.body.style.top = -scrollT + 'px';//改变css中top的值，配合fixed使用
    // 然后找个变量存一下刚才的scrolltop，要不然一会重新赋值，真正的scrollT会变0
    LastScrollT = scrollT;
}

function showBodyScroll() {
    document.body.style.overflow = 'auto';
    document.body.style.position = 'static';
    // 关闭close弹层的时候，改变js中的scrollTop值为上次保存的LastScrollT的值。并根据兼容性赋给对应的值。
    if (window.pageXOffset) {
        window.pageYOffset = LastScrollT;
    } else {
        document.body.scrollTop = LastScrollT;
        document.documentElement.scrollTop = LastScrollT;
    }
}


$(function () {
    if (teacherId == 0) {
        $('.maikefeng').hide();
        $("#text").css("width", "75%");
    }
});

window.onload = function () {
    //getDate(my);
    getThisClassCardData();
}
$(document.body).infinite().on("infinite", function () {
    if (loading) return;
    pageNumber++;
    //getDate(my);
    getThisClassCardData();
});

function showMyCardBtn() {
    $('.public_and_private').show();
    $('.delete_moment_btn').show();
}

function showAllCardBtn() {
    $('.public_and_private').hide();
    $('.delete_moment_btn').hide();
}

var userName;
function viewInit(my) {
    var html = '';
    var avBgSrc;
    var createTime;
    var className;
    var cover;
    var avatar;

    var classGradeName;
    var commentCount;
    var likeCount;
    var zanIcon;
    var bPublic;
    var text;
    var ulList;
    var audioSrc;
    var audioList=[];
    var uldisPlay = 'block';
    var textdisPlay = 'block';
    var audiodisPlay = 'block';
    var publicPicAndVideo;
    var publicCourse;
    var publicAudio;
    var TheLast;
    let allContent='';
    var tittle;
    var publicText;
    var studyRecord;
    let careConten='';
    let circlipContent;
    let  careCourse;

    for (var i = 0; i < list.length; i++) {

        /*打卡时间*/
        createTime = formatDateTime(list[i].createTime);

        //课程名称
        className = list[i].className;

        //课程图片
        cover = (typeof list[i].cover) == "string" ? list[i].cover : '../images/kaquan_merentu_bg.png';

        //用户头像
        avatar = (typeof list[i].avatar) == "string" ? list[i].avatar : '../images/defaultAvatar.png';
        //用户昵称
        userName = list[i].name;

        //班级名称
        classGradeName=list[i].classGradesName;

        //评论总数
        commentCount = list[i].mommentCount;

        //点赞数
        likeCount = list[i].likes;

        /*点赞图标*/
        zanIcon = list[i].likesStatus != 1 ? '../images/kaquan_zan_icon.png' : '../images/kaquan_dianzhan_press_icon.png';

        bPublic = list[i].bPublic == true ? '开放·所有人可见' : '私密·自己可见';

        publicTittle = '<div class="moment" id="moment' + i + '">\n' +
            '\n' +
            '        <div class="user_info">\n' +
            '            <img class="avatar" src="' + avatar + '">\n' +
            '\n' +
            '           <div class="name_and_grade">\n' +
            '               <span class="user_name">' + userName + '</span>\n' +
            '               <span class="class_grade_name"> &middot ' + classGradeName + '</span>\n' +
            '           </div>\n' +
            '           <div class="time_and_deleteBtn">\n' +
            '               <span class="time">' + createTime + '</span>\n' +
            '               <span class="delete_moment_btn" data-index="' + i + '" onclick="deleteMoment(this)">删除</span>\n' +
            '           </div>\n' +
            '        </div>\n';

        if (list[i].groupId==0){

            if (list[i].text != "") {
                textdisPlay = 'block';
                text = list[i].text;
                foldDisplay = 'block';
            } else {
                textdisPlay = "none";
                foldDisplay = "none";
            }

            if (list[i].mediaContent.pic.length == 0 && list[i].mediaContent.video.length == 0) {
                uldisPlay = 'none';
                //continue;
            } else if (list[i].mediaContent.pic == "" && list[i].mediaContent.video == "") {
                uldisPlay = 'none';
            } else {
                uldisPlay = 'block';
            }

            ulList = '';



            if (list[i].mediaContent.pic.length != 0 && list[i].mediaContent.pic != "") {
                for (var j = 0; j < list[i].mediaContent.pic.length; j++) {
                    //如果只有一张图片并且没有视频的情况下li的长度和宽度都扩大
                    if (list[i].mediaContent.pic.length == 1 && (list[i].mediaContent.video.length == 0 || list[i].mediaContent.video == "")) {
                        ulList += '<li style="width: 180px;height: 180px"><img class="av_bg" src="' + list[i].mediaContent.pic[j] + '" onerror="avBgError(this)" data-index="' + i + '" data-action="' + j + '" onclick="bigOrsmall(this)"></li>';
                    } else {
                        ulList += '<li><img class="av_bg" src="' + list[i].mediaContent.pic[j] + '"  data-index="' + i + '" onerror="avBgError(this)" data-action="' + j + '" onclick="bigOrsmall(this)"></li>';
                    }
                }
            }
            if (list[i].mediaContent.video.length != 0 && list[i].mediaContent.video != "") {
                avBgSrc = list[i].mediaContent.video[0] + '?vframe/jpg/offset/1/w/100/h/176';
                for (var j = 0; j < list[i].mediaContent.video.length; j++) {
                    //1.1.1版本调用playVideo2方法用手机系统播放器播放
                    if (sn != null && sn == "1.1.1") {
                        //如果只有一个视频并且没有图片的情况下li的长度和宽度都扩大
                        if (list[i].mediaContent.video.length == 1 && (list[i].mediaContent.pic.length == 0 || list[i].mediaContent.pic == "")) {
                            ulList +=
                                ' <li style="width: 180px;height: 180px">\n' +
                                '      <img class="av_bg" src="' + avBgSrc + '" data-url="' + list[i].mediaContent.video[j] + '" onerror="avBgError(this)" onclick="playVideo2(list[' + i + '].mediaContent.video[' + j + '])">\n' +
                                '      <img class="play_btn" src="../images/play.png" data-url="' + list[i].mediaContent.video[j] + '" onclick="playVideo2(list[' + i + '].mediaContent.video[' + j + '])">\n' +
                                ' </li>';
                        } else {
                            ulList +=
                                ' <li>\n' +
                                '      <img class="av_bg" src="' + avBgSrc + '" data-url="' + list[i].mediaContent.video[j] + '" onerror="avBgError(this)" onclick="playVideo2(list[' + i + '].mediaContent.video[' + j + '])">\n' +
                                '      <img class="play_btn" src="../images/play.png" data-url="' + list[i].mediaContent.video[j] + '" onclick="playVideo2(list[' + i + '].mediaContent.video[' + j + '])">\n' +
                                ' </li>';
                        }

                    } else {
                        //如果只有一个视频并且没有图片的情况下li的长度和宽度都扩大
                        if (list[i].mediaContent.video.length == 1 && (list[i].mediaContent.pic.length == 0 || list[i].mediaContent.pic == "")) {
                            ulList +=
                                ' <li style="width: 180px;height: 180px">\n' +
                                '      <img class="av_bg" src="' + avBgSrc + '" data-url="' + list[i].mediaContent.video[j] + '" onerror="avBgError(this)" onclick="playVideo(this)">\n' +
                                '      <img class="play_btn" src="../images/play.png" data-url="' + list[i].mediaContent.video[j] + '" onclick="playVideo(this)">\n' +
                                ' </li>';
                        } else {
                            ulList +=
                                ' <li>\n' +
                                '      <img class="av_bg" src="' + avBgSrc + '" data-url="' + list[i].mediaContent.video[j] + '" onerror="avBgError(this)" onclick="playVideo(this)">\n' +
                                '      <img class="play_btn" src="../images/play.png" data-url="' + list[i].mediaContent.video[j] + '" onclick="playVideo(this)">\n' +
                                ' </li>';
                        }

                    }

                }
            }

            if (list[i].mediaContent.voice.length != 0 && list[i].mediaContent.voice != "") {
                audiodisPlay = "block";
                audioList.push(i);

                audioSrc = list[i].mediaContent.voice[0];
            } else {
                audiodisPlay = "none";
            }

            publicText = ' <div class="publish_text" style="display: ' + textdisPlay + '">\n' +
                '            <p class="paragraph" id="paragraph' + i + '" data-index="' + i + '" onclick="viewDetails(this)">' + text + '</p>\n' +
                '        </div>';


            publicPicAndVideo = ' <div class="publish_pic_and_video" style="display: ' + uldisPlay + '">\n' +
                '                <ul>\n' + ulList +
                '                </ul>\n' +
                '        </div>\n';

            publicAudio = '<div class="publish_audio" style="display: ' + audiodisPlay + '">\n' +
                /* '            <audio  controls id="myAudio' + i + '" class="myAudio" src="' + audioSrc + '" data-index="' + i + '" onplay="playAudio(this)"></audio>\n' +*/

                '          <div class="audio-wrapper">\n' +
                '            <audio id="myAudio' + i + '" class="myAudio" src="' + audioSrc + '" data-index="' + i + '" ></audio>\n' +

                '            <div class="audio-left"><img  class="audioPlayer" id="audioPlayer' + i + '" src="../images/audio-play.png"></div>\n' +

                '            <div class="audio-right">\n' +
                '                <div class="progress-bar-bg" id="progressBarBg' + i + '">\n' +
                '                      <span id="progressDot' + i + '"></span>\n' +
                '                      <div class="progress-bar" id="progressBar' + i + '"></div>\n' +
                '                </div>\n' +
                '                <div class="audio-time"><span class="audio-length-current" id="audioCurTime' + i + '">00:00</span></div>\n' +
                '            </div>\n' +

                '          </div>\n' +
                '        </div>\n'

            TheLast = '<div class="comment_and_like">\n' +
                '            <div class="share_btn" data-index="' + i + '" onclick="shareLeply(list[' + i + '].studentId,list[' + i + '].id,list[' + i + '].text,list[' + i + '].name,list[' + i + '].className,list[' + i + '].classCourseId)">\n' +
                '                <img  class="share_icon" src="../images/kaquan_fenxiang_icon.png">\n' +
                '                <span class="share_span">分享</span>\n' +
                '            </div>\n' +
                '\n' +
                '            <div class="like">\n' +
                '                <span id="likeCount' + i + '" class="like_counts">' + likeCount + '</span>\n' +
                '                <img id="likeIcon' + i + '" class="like_icon"  src="' + zanIcon + '"  data-index="' + i + '" onclick="like(this)">\n' +
                '            </div>\n' +
                '\n' +
                '            <div class="comment_">\n' +
                '                <span id="counts' + list[i].id + '" class="comment_counts" >' + commentCount + '</span>\n' +
                '                <img   class="comment_icon" src="../images/kaquan_pinglun_icon.png" data-index="' + i + '" onclick="openComment(list[' + i + '].id)">\n' +
                '            </div>\n' +
                '        </div>\n' +
                '\n' +
                '       <div   class="public_and_private">\n' +
                '            <button class="public_btn" id="publicBtn' + i + '" type="button"  data-index="' + i + '" onclick="publicBtnAction(this)">' + bPublic + '</button>\n' +
                '       </div>\n' +
                '    </div>';

        }

        studyRecord='<div>\n' +
            '        <div class="record_coin" >\n' +
            '            <img class="record_btn"   src="../images/kaquan_record_icon@2x.png">\n' +
            '        </div>\n' +
            '<div class="record_tittle">' +
            '                <p class="record_text" onclick="playRecord(list['+i+'].groupId,list['+i+'].name,list['+i+'].avatar)">点击播放学习记录</p>\n' +
            '            </div>\n'
        '    </div>'


        publicCourse = ' <div class="course">\n' +
            '            <div class="course_info" onclick="thisClassCard(list[' + i + '].classRoomId)">\n' +
            '                <img class="course_img" src="' + cover + '" onerror="imgError(this)">\n' +
            '                <span class="course_name">' + className + '</span>\n' +
            '            </div>\n' +
            '        </div>\n';

        careCourse = ' <div class="course">\n' +
            '            <div class="course_info">\n' +
            '                <img class="course_img"  src="' + cover + '" onerror="imgError(this)">\n' +
            '                <span class="course_name">' + className + '</span>\n' +
            '            </div>\n' +
            '        </div>\n';


        if (list[i].groupId==0){
            //卡圈
            circlipContent = publicTittle+publicText+publicPicAndVideo+publicAudio+publicCourse+TheLast;
            careConten = ''
        } else if (list[i].groupId>0){
            //care分享记录
            circlipContent = '';
            careConten= publicTittle + studyRecord +careCourse +"</div></div>";

        }

        allContent += circlipContent+careConten;
        //allContent += circlipContent ==''?careConten:circlipContent;

    }
    //$('#all_moment').html(html);
    $('#all_moment').html(allContent);
    $('#all_card_span').html('所有打卡');
    $('#my_card_span').html('我的打卡')

    if (my == 0) {
        showAllCardBtn();
    } else if (my == 1) {
        showMyCardBtn();
    }

    initAudioEvent(audioList);
}
var body_move = true;

function playRecord(groupId,name,avatar) {
    var uri =encodeURI(avatar);
    sessionStorage.setItem("my",my);
    sessionStorage.setItem("scrollT",scrollT);
    sessionStorage.setItem("pageNumber",pageNumber);
    window.sessionStorage.setItem("name",name);
    window.location="./studyRecord.html?avatar="+uri+"&groupId="+groupId;
}


//点击图片放大
function bigOrsmall(e) {
    body_move= false;
    hideBodyScroll();
    /*$('#gallery').show();
    $('#gallery .weui-gallery__img').css('background-image', "url(" + e.src + ")");*/
    var index = $(e).data("index");
    var actionIndex = $(e).data("action");
    var imgItems = [];
    for (var i = 0; i < list[index].mediaContent.pic.length; i++) {
        imgItems.push(list[index].mediaContent.pic[i]);
    }
    pb = $.photoBrowser({
        items: imgItems,
        initIndex:actionIndex
    });
    pb.open(actionIndex);
    $('.weui-photo-browser-modal').on('click', function () {
        showBodyScroll();
        body_move = true;
        pb.close();
    })
}
function imgError(e) {
    e.src = '../images/kaquan_merentu_bg.png';
}

myVideo = document.getElementById("myVideo");
myVideo.addEventListener('ended', videoEnd);

/*function playVideo(e) {
    var src = $(e).data("url");
    console.log(src);
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/iPhone/i) == "iphone") {
        window.location.href = src;
    } else {
        myVideo.style.display = 'block';
        document.getElementById('v_box').style.display = 'flex';
        document.getElementById('header').style.display = 'none';
        document.getElementById('v_close').style.display = 'inline';

        myVideo.src = src;
        myVideo.play();

        hideBodyScroll();
    }
}*/

function playVideo(e) {
    body_move= false;
    var src = $(e).data("url");

    myVideo.style.display = 'block';
    document.getElementById('v_box').style.display = 'flex';
    //document.getElementById('header').style.display = 'none';
    document.getElementById('v_close').style.display = 'inline';

    myVideo.src = src;
    myVideo.play();

    hideBodyScroll();

}

function closeVideo() {
    body_move= true;
    hideVideoBox();
    showBodyScroll();
}

function videoEnd() {
    body_move= true;
    hideVideoBox();
    showBodyScroll();
}

/*隐藏video播放器*/
function hideVideoBox() {
    myVideo.pause();
    document.getElementById('v_close').style.display = 'none';
    document.getElementById('v_box').style.display = 'none';
    //document.getElementById('header').style.display = 'block';
    myVideo.style.display = 'none';
}

/*
var playingAudio = -1;

function playAudio(e) {
    index = $(e).data("index");
    if (playingAudio != -1 && playingAudio != index) {
        document.getElementById("myAudio" + playingAudio).pause();
    }
    playingAudio = index;
}
*/


$(document.body).pullToRefresh(function () {
    allList = [];
    myList = [];
    list=[];
    $('#all_moment').html("");
    pageNumber = 1;
    //getDate(my);



    if (!isSearch){
        getThisClassCardData();
    }else {
        getSearchCardData();
    }

    if (loading) loading = false;
    $(document.body).pullToRefreshDone();
});
function thisClassCard(thisClassRoomId) {
    if (thisClassRoomId!=classRoomId) {
        classRoomId = thisClassRoomId;

        getThisClassCardData();
    }
}
function getThisClassCardData() {
    console.log(studentId);
    $.ajax({
        url: "/snp/v2/student/grade/getCardByClassRoom",
        data: {
            "studentId": studentId,
            "classRoomId":classRoomId,
            "pageNumber": pageNumber,//1
            "pageSize": 5,
            "my": my,
            "type": type
        },
        async: false,
        type: "get",
        success: function (res) {
            if (res.code == 200 && res.data.list.length > 0) {
                result = res;
                res.data.list.forEach(
                    function (v) {
                        list.push(v)
                    }
                )
                viewInit(my);
                if (res.data.list.length >= res.data.totalRow || res.data.isLastPage) {
                    //$(document.body).destroyInfinite();
                    $('#infinite_div').hide();
                }
            } else {
                //$(document.body).destroyInfinite();
                $('#infinite_div').hide();
            }
            loading = false;
        }
    })
}
function viewDetails(e) {
    let index = $(e).data("index");
    if ($('#paragraph'+index).attr("class")=="paragraph") {
        $('#paragraph'+index).attr("class","paragraph2");
    }else {
        $('#paragraph'+index).attr("class","paragraph");
    }
}
function searchBtn(){
    //locationUrl();
    searchVal=$('#searchInput').val();
    if (searchVal!=""&&searchVal!=null) {
        getSearchCardData();
    }
}
$('#searchInput').on('keypress',function (event) {
    if (event.keyCode==13){
        //locationUrl();
        searchVal=$('#searchInput').val();
        if (searchVal!=""&&searchVal!=null) {
            getSearchCardData();
        }
    }
})
var isSearch=false;

function getSearchCardData() {
    console.log(studentId);
    $.ajax({
        url: "/snp/v2/student/grade/serarchStudendCard",
        data: {

            "name": searchVal,
            "studentId":studentId,
            "classRoomId":classRoomId,
            //"pageNumber": pageNumber,//1
            // "pageSize": 5,
            //"my": my,
            // "type": type
        },
        async: false,
        type: "get",
        success: function (res) {
            console.log(res)
            if (res.code == 200 && res.data.list.length > 0) {
                isSearch=true;
                $('#searchInput').val(searchVal);
                list=[];
                result = res;
                res.data.list.forEach(
                    function (v) {
                        list.push(v)
                    }
                )
                viewInit(my);
                if (res.data.list.length >= res.data.totalRow || res.data.isLastPage) {
                    //$(document.body).destroyInfinite();
                    $('#infinite_div').hide();
                }
            } else {
                //$(document.body).destroyInfinite();
                $('#infinite_div').hide();
                $('#all_moment').html("<p class='not_found_hint'>该课程没有此学生</p>")
                //$("body").css("background","rgba(255,255,255,1)");
            }
            loading = false;
        }
    })
}
