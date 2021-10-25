//var timeSn = '34459_1563935983812';
//var timeSn = '22403_1562344535800';


var product = 0;
var student = 0;

var recordList=[];

var  html = '';

var timeSn=getQueryString('timeSn');

var audioList=[];
view();

var follow;

getlowWord();

var index = 0;

var totalScore=[];

var photolist = [];

function isWeiXin(){
    return window.navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == 'micromessenger'
}

function view(){
    $.get('/snp/v2/lesson/getLessonReply?timeSn='+timeSn,function (res) {
        console.log(res);
        if (res.code==200){

            if (res.data.product && isWeiXin()){
                $('.want').show();
                product = res.data.product;
                student = res.data.classStudent.id;
            };

            recordList=res;

            $('#className').text(recordList.data.classRoom.class_name);
            
            if (typeof recordList.data.classRoom.cover=="string" ){
                $('#studentImg').attr("src",recordList.data.classRoom.cover);
            } else {
                $('#studentImg').attr("src","../images/kaquan_merentu_bg.png");
            }



            //跟读内容list
            var lessonScriptReplies=recordList.data.lessonScriptReplies;

            console.log(lessonScriptReplies);

            var audioBg;
            for (var i = 0; i <lessonScriptReplies.length ; i++) {
                if(recordList.data.lessonScriptReplies[i].replyScript[0].type=="video"&&lessonScriptReplies[i].replyScript[0].content!=""){
                    audioBg=lessonScriptReplies[i].replyScript[0].content+'?vframe/jpg/offset/0/w/180/h/180';
                }
                else{
                    audioBg="../images/jilu_bg@2x.png";
                }
                html='';
                var content = lessonScriptReplies[i].replyScript[0].content;
                console.log(recordList.data.lessonScriptReplies[i].replyScript[0].type+":"+i)
                if (recordList.data.lessonScriptReplies[i].replyScript[0].type=="audio" && recordList.data.lessonScriptReplies[i].classScriptContent[0].command ==301 ){

                    var picLib = recordList.data.lessonScriptReplies[i].classScriptContent[0].resource.picLib;
                    var followPic= picLib==""?"../images/image.png":picLib;

                    html = '<div class="video_and_content">\n' +
                        '    <div class="class_tittle">\n' +
                        '        <p class="class_follow">课堂跟读</p>\n' +
                        '    </div>\n' +
                        '\n' +
                        '    <!--跟读图片-->\n' +
                        '       <div class="background_images">\n' +
                        '       <img style="border-radius:4px; width: 100%;" src='+followPic+' >\n' +
                        '       <audio   onended="endfollow(this)" id="follow_audio'+i+'" src="'+content+'"  style="display: none"></audio>\n' +
                        '           <img class= "audio_ing"  id="audioGif'+i+'" onclick="followStop(this)" src="../images/voice.gif"   >\n' +
                        '       <img class="followBtn" id="Follow_Btn'+i+'" src="../images/jilu_bofang_icon.png"   onclick="followPlay('+i+'); " >\n' +
                        '       <img class="stopPlay" id="stopPlay_Btn'+i+'" src="../images/jilu_bofang_icon.png"   onclick="followPlay('+i+'); " >\n' +
                        '       </div>\n' +
                        '    <div class="follow_read">\n' +
                        '        <span  style="font-size: 13px;font-weight:bold; top:4px; margin-left: 12px "> <strong>跟读内容:</strong></span>\n' +
                        '        <!--跟读内容-->\n' +
                        '        <div id="followContent"  class="follow_content">'+lessonScriptReplies[i].classScriptContent[0].ext.readContent+'</div>\n' +
                        '    </div>\n' +
                        '\n' +
                        '</div>'
                } else if (recordList.data.lessonScriptReplies[i].replyScript[0].type=="video"){
                    html = '<div class="video_work">\n' +
                        '            <p class="class_word">视频作业</p>\n' +
                        '        <img class="playBtn" id="playBtn1'+i+'" src="../images/jilu_bofang_icon.png" data-url="'+content+'" onclick="playvideo(this)" style="margin-left: 87px ">\n' +
                        '        <div class="video_images">\n' +
                        '           <img id="VideoPtnImg'+i+'" src="'+audioBg+'"  style="width: 180px; height: 180px; margin-left: 12px ;border-radius:4px;">\n' +
                        '               <video controls  onended="videoEnd(this,'+i+')" id="playVideo'+i+'" src="'+content+'" class="videoWork"webkit-playsinline="true"  x-webkit-airplay="true"  playsinline="true"  x5-video-player-type="h5"  x5-video-orientation="h5"  x5-video-player-fullscreen="true"  preload="auto"></video>' +
                        '        </div>\n' +
                        '      <div class="CloseScreen'+i+'">' +
                        '       <img  onclick="closeFulooScreen(this,'+i+')" id="OutFullScreen'+i+'"   class="fullScreen" src="../images/kaquan_guanbi_icon@2x.png">' +
                        '      </div> ' +
                        '    </div>'


                }else if (recordList.data.lessonScriptReplies[i].replyScript[0].type=="audio"){
                    audioList.push(i);
                    html = '<div class="audio_work">\n' +
                        '        <p class="audio_word">音频作业</p>\n' +
                       /* '        <!--音频-->\n' +
                        '        <audio id="audio'+i+'"  onplaying="playAudio(this,'+i+')" class="audio_sound" src="'+content+'" controls></audio>\n' +*/
                        '          <div class="audio-wrapper">\n' +
                        '            <audio id="myAudio' + i + '" class="myAudio" src="' + content + '" data-index="' + i + '" ></audio>\n' +

                        '            <div class="audio-left"><img  class="audioPlayer" id="audioPlayer'+i+'" src="../images/audio-play.png"></div>\n' +

                        '            <div class="audio-right">\n' +
                        '                <div class="progress-bar-bg" id="progressBarBg'+i+'">\n' +
                        '                      <span id="progressDot'+i+'"></span>\n' +
                        '                      <div class="progress-bar" id="progressBar'+i+'"></div>\n' +
                        '                </div>\n' +
                        '                <div class="audio-time"><span class="audio-length-current" id="audioCurTime'+i+'">00:00</span></div>\n' +
                        '            </div>\n' +

                        '          </div>\n' +
                        '    </div>'
                }else if (recordList.data.lessonScriptReplies[i].replyScript[0].type=="picture"){
                    photolist.push(content);

                    html = '<div class="photo">\n' +
                        '        <p class="photo_work">图片作业</p>\n' +
                        '        <div class="photo_background">\n' +
                        '            <img  onclick="bigOrsmall(this)" src="'+content+'" class="photo_show" >\n' +
                        '        </div>\n' +
                        '    </div>'
                }
                $("#Bigdiv").append(html);
                totalScore.push(recordList.data.lessonScriptReplies[i].score)
            }

            initAudioEvent(audioList);
            var statImg='<img class="star" src="../images/jilu_xingixng_normal_icon(1).png">';
            //半颗星
            var  halfStartImg = '<img  class="star" src="../images/jilu_xingixng_b_normal_icon.png">';
            //灰星
            var   failStarImg = '<img  class="star" src="../images/jilu_xingixng_press_icon (1).png">';

            //总分
            var max = Math.max.apply(null, totalScore);
            if (max<=40){
                var recordScore = max;
                for (var i = 0; i <2 ; i++) {
                    $('#starCount').append(statImg);
                }
                for (var i = 0; i <3 ; i++) {
                    $('#starCount').append(failStarImg);
                }
            } else{
                var score = max;
                for (var i=1;i<=5;i++){
                    if (score>20){
                        $('#starCount').append(statImg);
                        score-=20;
                    }else if (score>0) {
                        $('#starCount').append(halfStartImg);
                        score-=20;
                    }else {
                        $('#starCount').append(failStarImg);
                    }
                }
            }
        }

    })
}


    function videoEnd(e,i) {
        $("#playVideo"+i).css("display","none");
        $("#VideoPtnImg"+i).css("display","block");
        $("#playBtn1"+i).css("display","block");

    }

    var  followNum = 0;
    function followStop() {
        document.getElementById("follow_audio"+followNum).pause()
        $('#audioGif'+followNum).css('display','none');
        $("#stopPlay_Btn"+followNum).css("display","block");

    }

    function stopPlayfollow(i) {

        if (followNum!=0){
            i=followNum;
        }

        $('#audioGif'+i).css('display','block');
        $("#stopPlay_Btn"+i).css("display","none");
        document.getElementById("follow_audio"+i).play();
    }

    //結束課堂播放
    function endfollow(e) {
        console.log("播放完了")
        $('#audioGif'+index).css('display','none');
        $('#Follow_Btn'+index).css('display','block');
        $('#stopPlay_Btn'+index).css("display",'none');
    }

     var  followNum=0;
    //播放课堂跟读
     function followPlay(i){
    console.log("followPlay")
    $('#stopPlay_Btn'+followNum).css('display','none');

    if (audioNum!=0&&document.getElementById("audio"+audioNum).played) {
        document.getElementById("audio"+audioNum).pause();
    }



    // document.getElementById("follow_audio"+i).play();
    if (followNum==0 || followNum==i){
        //隱藏 播放按鈕
        $('#Follow_Btn'+i).css('display','none');
        //顯示播放狀態
        $('#audioGif'+i).css('display','block');
        document.getElementById("follow_audio"+i).play();
    }else if (followNum!=i){
        document.getElementById("follow_audio"+followNum).pause();
        $('#audioGif'+followNum).css('display','none');
        $('#Follow_Btn'+followNum).css('display','block');
    }
    followNum=i;
    index=i;

    }

    //点击按钮播放视频事件
    function playvideo(e,i) {

        var url = $(e).data('url');

        var video = document.getElementById("player");
        video.src = url;

        document.getElementById("v_box").style.display = 'flex';
        video.play();

        $("#CloseScreen"+i).css("display","block");
        $("#OutFullScreen"+i).css("display","block");

        $("#CloseScreen"+i).css("display","block");
        $("#OutFullScreen"+i).css("display","block");

        index=i;

        var  audios= document.getElementsByTagName("audio");

        for (var j = 0; j < audios.length; j++) {

            audios[j].pause();
        }

        if (followNum !=0 && document.getElementById("playVideo"+i).paused ) {
            document.getElementById("follow_audio"+followNum).pause();
            $('#audioGif'+followNum).css('display','none');
            $('#stopPlay_Btn'+followNum).css('display','block');
        }


        document.getElementById("playVideo"+i).play();
        $('#playBtn1'+i).css('display','none');
        $('#VideoPtnImg'+i).css('display','none');
        $('#playVideo'+i).css('display','block');
    }

    let audioNum=0;
    //获取音频播放事件
    function playAudio(e,i) {
        try {
            document.getElementById("follow_audio"+followNum).pause();
            if (document.getElementById("follow_audio"+followNum).paused){
                $('#audioGif'+followNum).css('display','none');
                $('#stopPlay_Btn'+followNum).css('display','block');
            }

        }catch (e) {
            console.log(e)
        }
        if (i==audioNum){
            return;
        }
        try {
            document.getElementById("audio"+audioNum).pause();
        }catch (e) {
            console.log(e)
        }
        audioNum = i;



    }

    //获得低分单词
    var lowList=[];
    function getlowWord() {
        $.get('/snp/v2/lesson/getLowWordList?timeSn='+timeSn,function (res){
            if (res.code==200){
                alert("111");
                lowList=res.data;
                if (lowList!=null){
                    for (var i = 0; i <lowList.length ; i++) {
                        $('.show_word').append('<span class="word_"">'+lowList[i]+'</span>');
                    }
                } else if (lowList==null) {
                    $(".low").css('display',"none");
                }
            }else{
                $(".low").css('display',"none");
            }
        })
    }

    //点击图片放大
    function bigOrsmall(v) {
        $('#gallery').show();
        $('#gallery .weui-gallery__img').css('background-image',"url("+v.src+")");
    }








