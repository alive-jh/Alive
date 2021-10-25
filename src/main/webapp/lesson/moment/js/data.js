//var studentId=getQueryString('student');
history.scrollRestoration = 'manual';
var result={};

var studentId=getQueryString('student');
var teacherId=getQueryString('teacher');
/*var studentId=672;
var teacherId=0;*/
var list= [];
var myVideo;
var loading = false;  //状态标记
var pageNumber=1;
var allList=[],myList=[];
var my=0;




//“所有打卡”的点击事件
$('#allCard_span').click(function() {
    if($(this).className!='checkedSapn'){
        //“所有打卡”字体加粗
        $(this).attr('class','checked_span');
        //“我的打卡”的光标隐藏
        $('#myCard_hr').css('display','none');
        //“所有打卡”的光标显示
        $('#allCard_hr').css('display','block');
        //“我的打卡”字体变暗
        $('#myCard_span').attr('class','unChecked_span');

        $('#div1').empty();

        document.body.scrollTop = document.documentElement.scrollTop = 0;
        pageNumber=1;
        my=0;
        myList = [];
        allList = [];
        getDate(my);

    }
});


//“我的打卡”的点击事件
$('#myCard_span').click(function () {
    if($(this).className!='checkedSapn'){
        //“我的打卡”字体加粗
        $(this).attr('class','checked_span');
        //“我的打卡”的光标显示
        $('#myCard_hr').css('display','block');
        //“所有打卡”的光标隐藏
        $('#allCard_hr').css('display','none');
        //“所有打卡”字体变暗
        $('#allCard_span').attr('class','unChecked_span');

        document.body.scrollTop = document.documentElement.scrollTop = 0;
        $('#div1').empty();

        pageNumber=1;
        my=1;
        allList = [];
        myList = [];
        getDate(my);
    }
});



window.onload = function (ev) {
    getDate(my);
}

$(document.body).infinite().on("infinite", function() {
    if(loading) return;
    pageNumber++;
    getDate(my);
    /*alert($(document.body).scrollTop());*/
});
function getDate(my){
    $.ajax({
        url:"/wechat/v2/student/grade/getCard",
        data:{
            "studentId":studentId,
            "pageNumber":pageNumber,//1
            "pageSize":5,
            "my":my
        },
        async: false,
        type:"get",
        success:function (res) {
            if(res.code==200&&res.data.list.length>0){
                console.log(res);
                result=res;
                if (my==0){
                    res.data.list.forEach(function (v) {
                        allList.push(v)
                    })
                    list = allList;
                }else {
                    res.data.list.forEach(function (v) {
                        myList.push(v)
                    })
                    list = myList
                }
                viewInit(my);
                if (res.data.list.length>=res.data.totalRow||res.data.isLastPage){
                    //$(document.body).destroyInfinite();
                    $('#infinite_div').hide();
                }
            }else {
                //$(document.body).destroyInfinite();
                $('#infinite_div').hide();
            }

            loading = false;


        }
    })
}



function publicBtnAction(e){
    var index = $(e).data("index");
    var momentid=list[index].id;
    console.log(momentid)
    $.actions({
        actions: [{
            text: "私密·自己可见",
            onClick: function() {
                setPublicOrPrivateData(0,momentid,index);
            }
        },{
            text: "开放·所有人可见",
            onClick: function() {
                setPublicOrPrivateData(1,momentid,index);
            }
        }]
    });
}



function setPublicOrPrivateData(v,momentid,index){
    $.post('/wechat/v2/student/grade/MomentIsNotOrPublic?momentId='+momentid+'&b_public='+v,function (res) {
        console.log(res.data);
        if (res.code == 200){
            if(res.data){
                $('#publicBtn'+index).text("开放·所有人可见");
            }else {
                $('#publicBtn'+index).text("私密·自己可见");
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
    return  m + '-' + d+' '+h+':'+minute;
};


function showPublicBtn() {
    $('.avatar').hide();
    $('.studentName').hide();
    $('.publicBtn').show();

    /*显示删除按钮*/
    $('.deleteBtn').show();


}
function showAvatar() {
    $('.avatar').show();
    $('.studentName').show();
    $('.publicBtn').hide();

    /*隐藏删除按钮*/
    $('.deleteBtn').hide();


}


function viewInit(my) {


    var html = '';
    console.log(list);
    for(var i=0;i<list.length;i++){

        var audioBg;

        if (list[i].mediaContent.pic.length==0&&list[i].mediaContent.voice.length==0&&list[i].mediaContent.video.length==0){
            continue;
        }else if (list[i].mediaContent.pic==""&&list[i].mediaContent.voice==""&&list[i].mediaContent.video==""){
            continue;
        }

        if(list[i].mediaContent.video.length!=0&&list[i].mediaContent.video!=""){
            audioBg=list[i].mediaContent.video[0]+'?vframe/jpg/offset/1/w/100/h/176';
        }
        else{
            audioBg="../images/audio-bg-default.png";
        }


        /*添加视频创建时间*/
        var createTime=formatDateTime(list[i].createTime);

        //课程名称
        var className=list[i].className;

        //课程图片
        if ((typeof list[i].cover)=="string"){
            var cover=list[i].cover;
        } else {
            var cover="../images/kaquan_merentu_bg.png";
        }

        //用户头像
        if ((typeof list[i].avatar)=="string"){
            var avatar=list[i].avatar;
        } else {
            var avatar="../images/defaultAvatar.png";
        }

        //用户昵称
        var name=list[i].name;

        //评论总数
        var mommentCount=list[i].mommentCount;

        //点赞数
        var likes=list[i].likes;

        if (list[i].likesStatus!=1){
            var zanIcon='../images/kaquan_zan_icon.png';
        }
        else {
            var zanIcon='../images/kaquan_dianzhan_press_icon.png';
        }

        if(list[i].bPublic){
            var bPublic='开放·所有人可见';
        }else{
            var bPublic='私密·自己可见';
        }

        html +=
            '<div class="mvContent" id="mvContent'+i+'">\n' +
            '    <div class="av_div" id="av_div'+i+'">\n' +


            '        <img class="avBg" id="avBg'+i+'" src="'+audioBg+'">\n' +
            '        <img class="megaphoneGif"  id="megaphoneGif'+i+'" src="../images/voice.gif">\n'+


            '        <video id="myVideo'+i+'"  class="myVideo" style="display: none;position: absolute"  src=""    playsinline x5-playsinline="true" playsinline="true"  webkit-playsinline="true" x5-video-player-type="h5-page"></video>\n'+

            '        <div class="createTime">\n' +
            '           <span class="time_span">'+createTime+'</span>\n' +
            '        </div>\n' +

            '        <div class="deleteBtn" data-index="'+i+'" onclick="deleteFun(this)">\n' +
            '           <span class="delete_span">删除</span>\n' +
            '        </div>\n' +

            '        <div class="shareBtn" data-index="'+i+'" onclick="shareLeply(list['+i+'].studentId,list['+i+'].id)">\n' +
            '           <span class="share_span">分享</span>\n' +
            '        </div>\n' +

            '        <div id="paragraph'+i+'" class="paragraph">\n' +
            '            <p id="text'+i+'" class="text"></p>\n' +
            '        </div>\n'+
            '        <img id="playBtn'+i+'" class="playBtn" data-index="'+i+'" src="../images/kaquan_play_icon@2x.png"  onclick="playPause(this)">\n' +
            '    </div>\n' +
            '\n' +
            '    <div class="course">\n' +
            '        <img class="classImg" src="'+cover+'">\n' +
            '        <span class="className">'+className+'</span>\n' +
            '    </div>\n' +
            '\n' +
            '    <div class="userInfo">\n' +
            '        <div class="avatar_and_username">\n' +
            '           <img class="avatar" src="'+avatar+'">\n' +
            '           <span class="studentName">'+name+'</span>\n' +
            '           <button id="publicBtn'+i+'" class="publicBtn" type="button" data-index="'+i+'" onclick="publicBtnAction(this)">'+bPublic+'</button>\n'+
            '        </div>\n' +
            /*'\n' +
            '        <div class="user_pinglun">\n' +
            '            <span class="pinglun_and_zan_span">'+mommentCount+'</span>\n' +
            '            <img class="pinglun_and_zan_img" src="../images/kaquan_pinglun_icon.png">\n' +
            '        </div>\n' +
            '\n' +*/
            '\n' +
            '        <div class="user_pinglun">\n' +
            '            <span id="counts'+list[i].id+'" class="pinglun_and_zan_span" >'+mommentCount+'</span>\n' +
            '            <img   class="pinglun_and_zan_img" src="../images/kaquan_pinglun_icon.png" data-index="'+i+'" onclick="openComment(list['+i+'].id)">\n' +
            '        </div>\n' +
            '\n' +
            '        <div class="user_zan">\n' +
            '            <span id="likeCount'+i+'" class="pinglun_and_zan_span">'+likes+'</span>\n' +
            '            <img id="likeImg'+i+'" class="pinglun_and_zan_img"  src="'+zanIcon+'" data-index="'+i+'" onclick="like(this)">\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</div>'

    }

    $('#div1').html(html);


    if(my==0){
        showAvatar();
    }
    else if (my==1){
        showPublicBtn();
    }

    myVideo = document.getElementById("myVideo"+index);
    videoListener();

    $('#allCard_span').text('所有打卡');
    $('#myCard_span').text('我的打卡');
    $('#weui-loadmore__tips').text('正在加载');
    $('#pinglunqu').text('评论区');
    $('#weui-loadmore__tips2').text('正在加载');
    $('#fabiaoBtn').text('发布');

}


var index=0;

var AVsource=[];

var ImageTimeout;

var curr=0;

//var reg=RegExp(/.mp4/);


function videoListener(){
    /*监听播放结束事件*/
    myVideo.addEventListener('ended', endFun);

    /*监听播放暂停事件*/
    myVideo.addEventListener('pause', pauseFun);

    /*监听播放开始播放事件*/
    myVideo.addEventListener('play', playFun);
}



function pauseFun() {
    $('#playBtn'+index).css('display','inline');
}

function playFun() {
    $('#playBtn'+index).css('display','none');
}

function noneStyle() {
    $('#megaphoneGif'+index).css('display','none');
    $('#myVideo'+index).css('display','none');
    $('#paragraph'+index).css('display','none');
    $('#text'+index).text('');
}
function avBg() {
    if (list[index].mediaContent.video.length!=0&&list[index].mediaContent.video!=""){
        $('#avBg'+index).attr('src', list[index].mediaContent.video[0]+'?vframe/jpg/offset/1/w/100/h/176');
    }else{
        $('#avBg'+index).attr('src', '../images/audio-bg-default.png');
    }
}
/*点击播放事件*/
function playPause(e) {

    $('#avBg'+index).css('display','block');
    $('#playBtn'+index).css('display','inline');

    avBg();

    noneStyle();

    window.clearTimeout(ImageTimeout);
    myVideo.pause();

    /*获取点击指令的下标*/
    index = $(e).data("index");
    //隐藏play按钮
    $('#playBtn'+index).css('display','none');

    console.log("index",index)
    AVsource=[];
    curr = 0;

    myVideo = document.getElementById("myVideo"+index);
    myVideo.addEventListener('ended', endFun);
    myVideo.addEventListener('pause', pauseFun);
    myVideo.addEventListener('play', playFun);



    for (var i=0;i<list[index].mediaContent.voice.length;i++){
        if (list[index].mediaContent.voice.length!=0&&list[index].mediaContent.voice!=""){
            AVsource.push(list[index].mediaContent.voice[i]);
        }else {
            break;
        }
    }

    for (var i=0;i<list[index].mediaContent.video.length;i++){
        if (list[index].mediaContent.video.length!=0&&list[index].mediaContent.video!=""){
            AVsource.push(list[index].mediaContent.video[i]);
        }else {
            break;
        }
    }
    for (var i=0;i<list[index].mediaContent.pic.length;i++){
        if (list[index].mediaContent.pic.length!=0&&list[index].mediaContent.pic!=""){
            AVsource.push(list[index].mediaContent.pic[i]);
        }else {
            break;
        }
    }


    //console.log(AVsource[1].toString().substring(AVsource[1].toString().lastIndexOf("."))=='.jpg')

    console.log("AVsource:"+AVsource[curr]);

    //调用自定义播放事件
    customizePlay();
}

/*自定义播放事件*/
function customizePlay() {
    $('#playBtn'+index).css('display','none');
    noneStyle();

    if(AVsource.length<=0){
        $('#playBtn'+index).css('display','inline');
        return;
    }

    /*获取播放源*/
    if (AVsource[curr].toString().substring(AVsource[curr].toString().lastIndexOf("."))=='.mp3') {
        $('#avBg'+index).attr('src', '../images/audio-bg-default.png');
        $('#megaphoneGif'+index).css('display','inline');
        if(list[index].text!=""){
            $('#paragraph'+index).css('display','block');
            $('#text'+index).text(list[index].text);
        }
    }

    else if (AVsource[curr].toString().substring(AVsource[curr].toString().lastIndexOf("."))=='.mp4') {
        //$('#avBg'+index).attr('src', '../images/audio-bg-default.png');
        $('#avBg'+index).css('display', 'none');
        $('#myVideo'+index).css('display','block');
    }

    else if (AVsource[curr].toString().substring(AVsource[curr].toString().lastIndexOf("."))=='.jpg') {
        imageShow();
        return;
    }


    myVideo.src = AVsource[curr];

    myVideo.load();
    myVideo.play();
    curr++;
}



function endFun(){

    console.log('上条播放结束');
    if(curr>=AVsource.length){
        console.log("已播完最后一条");
        $('#playBtn'+index).css('display','inline');
        $('#avBg'+index).css('display','block');
       /* if (reg.exec(AVsource.toString())){
            console.log(list[index].mediaContent.video[0]+'?vframe/jpg/offset/1/w/100/h/176')
            $('#avBg'+index).attr('src', list[index].mediaContent.video[0]+'?vframe/jpg/offset/1/w/100/h/176');
        }else{
            $('#avBg'+index).attr('src', '../images/audio-bg-default.png');
        }*/
        avBg();
        //$('#myVideo'+index).css('display','none');
        noneStyle();
        curr=0;
        return;
    }

    //调用自定义播放事件
    customizePlay();
}


function imageShow() {
    console.log("放图片")
    //放图片
    $('#avBg'+index).css('display', 'block');
    $('#avBg'+index).attr('src', AVsource[curr].toString());
    $('#megaphoneGif'+index).css('display','none');

    ImageTimeout = setTimeout(function () {
        //关闭图片
        console.log("关闭图片")
        //$('#avBg'+index).attr('src', '../images/audio-bg-default.png');
        curr++;
        endFun();
    }, 4000)
}

function like(e) {
    var index = $(e).data("index");
    var momentId=list[index].id;
    $.post('/wechat/v2/student/grade/isNotOrLike?userId='+studentId+'&momentId='+momentId,function (res) {
        if (res.code == 200){
            console.log(res);
            console.log(res.data.status)
            if (res.data.status!=1){
                $('#likeImg'+index).attr('src','../images/kaquan_zan_icon.png');
                $('#likeCount'+index).text(res.data.likeCount);
            }
            else {
                $('#likeImg'+index).attr('src','../images/kaquan_dianzhan_press_icon.png');
                $('#likeCount'+index).text(res.data.likeCount);
            }

            console.log(res.data.status);
        }
    })
}

function deleteFun(e){

    $.confirm({
        title: '真的要删除吗？',
        onOK: function () {
            //点击确认
            confirmDelete();
        },
        onCancel: function () {
            return;
        }
    });

    function confirmDelete() {
        var index = $(e).data("index");
        var momentId=list[index].id;
        $.post('/wechat/v2/student/grade/deleteCard?momentId='+momentId,function (res) {
            if (res.code == 200){
                console.log(res);
                $('#mvContent'+index).remove();
                if (document.getElementById('div1').scrollHeight<=698){
                    if (!result.data.isLastPage){
                        //pageNumber++;
                        myList=[];
                        getDate(1);
                    }else{
                        //$(document.body).destroyInfinite();
                        $('#infinite_div').hide();
                    }
                }

            }
        })
    }
}























