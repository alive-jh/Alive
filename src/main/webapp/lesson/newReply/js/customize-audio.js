function initAudioEvent(audioList) {
    var audio;
    var totalTime;
    for (var i=0;i<audioList.length;i++) {
        audio = document.getElementById('myAudio' + audioList[i]);
        audio.load();
        (function(i){
            audio.addEventListener("canplaythrough", function () {//监听audio是否加载完毕，如果加载完毕，则读取audio播放时间
                totalTime=transTime( document.getElementById('myAudio' + audioList[i]).duration);
                $('#audio-length-total'+audioList[i]).html(totalTime);
            });

            // 点击播放/暂停图片时，控制音乐的播放与暂停
            $('#audioPlayer'+audioList[i]).click(function () {

                console.log(audio==document.getElementById('myAudio' + (this.id).substring(11)))
                if (audio==document.getElementById('myAudio' + (this.id).substring(11))){
                    console.log(this);
                    //document.getElementById('myAudio' + (this.id).substring(11)).pause();
                    //$('#audioPlayer'+(this.id).substring(11)).attr('src', '../images/audio_play_icon.png');
                    this.src='../images/audio_play_icon.png';
                }else {
                    console.log("pause");
                    audio.pause();
                    audio.load();
                    $('.audioPlayer').attr('src', '../images/audio_play_icon.png');
                }
                audio = document.getElementById('myAudio' + audioList[i]);
                // 监听音频播放时间并更新进度条
                audio.addEventListener('timeupdate', function () {
                    updateProgress(audio,audioList[i]);
                }, false);

                // 监听播放完成事件
                audio.addEventListener('ended', function () {
                    audioEnded(audioList[i]);
                }, false);

                // 改变播放/暂停图片
                if (audio.paused) {
                    // 开始播放当前点击的音频
                    audio.play();
                    $('#audioPlayer'+audioList[i]).attr('src', '../images/audio_pause_icon.png');
                } else {
                    audio.pause();
                    $('#audioPlayer'+audioList[i]).attr('src', '../images/audio_play_icon.png');
                }

                // 点击进度条跳到指定点播放progressBar
                // PS：此处不要用click，否则下面的拖动进度点事件有可能在此处触发，此时e.offsetX的值非常小，会导致进度条弹回开始处（简直不能忍！！）
                $('#progressBarBg'+audioList[i]).on('mousedown', function (e) {
                    // 只有音乐开始播放后才可以调节，已经播放过但暂停了的也可以
                    if (!audio.paused || audio.currentTime != 0) {
                        var pgsWidth = $('#progressBarBg'+audioList[i]).width();
                        var rate = e.offsetX / pgsWidth;
                        audio.currentTime = audio.duration * rate;
                        updateProgress(audio,audioList[i]);
                    }
                });
                //dragProgressDotEvent(audio,audioList[i]);
            });
        })(i)
    }
}

/**
 * 鼠标拖动进度点时可以调节进度
 * @param {*} audio
 */
function dragProgressDotEvent(audio,audioIndex) {
    var dot = document.getElementById('progressDot'+audioIndex);

    var position = {
        oriOffestLeft: 0, // 移动开始时进度条的点距离进度条的偏移值
        oriX: 0, // 移动开始时的x坐标
        maxLeft: 0, // 向左最大可拖动距离
        maxRight: 0 // 向右最大可拖动距离
    };
    var flag = false; // 标记是否拖动开始

    // 鼠标按下时
    dot.addEventListener('mousedown', down, false);
    dot.addEventListener('touchstart', down, false);

    // 开始拖动
    document.addEventListener('mousemove', move, false);
    document.addEventListener('touchmove', move, false);

    // 拖动结束
    document.addEventListener('mouseup', end, false);
    document.addEventListener('touchend', end, false);

    function down(event) {
        if (!audio.paused || audio.currentTime != 0) { // 只有音乐开始播放后才可以调节，已经播放过但暂停了的也可以
            flag = true;

            position.oriOffestLeft = dot.offsetLeft;
            position.oriX = event.touches ? event.touches[0].clientX : event.clientX; // 要同时适配mousedown和touchstart事件
            position.maxLeft = position.oriOffestLeft; // 向左最大可拖动距离
            position.maxRight = document.getElementById('progressBarBg'+audioIndex).offsetWidth - position.oriOffestLeft; // 向右最大可拖动距离

            // 禁止默认事件（避免鼠标拖拽进度点的时候选中文字）
            if (event && event.preventDefault) {
                event.preventDefault();
            } else {
                event.returnValue = false;
            }

            // 禁止事件冒泡
            if (event && event.stopPropagation) {
                event.stopPropagation();
            } else {
                window.event.cancelBubble = true;
            }
        }
    }

    function move(event) {
        if (flag) {
            var clientX = event.touches ? event.touches[0].clientX : event.clientX; // 要同时适配mousemove和touchmove事件
            var length = clientX - position.oriX;
            if (length > position.maxRight) {
                length = position.maxRight;
            } else if (length < -position.maxLeft) {
                length = -position.maxLeft;
            }
            var pgsWidth = $('#progressBarBg'+audioIndex).width();
            var rate = (position.oriOffestLeft + length) / pgsWidth;
            audio.currentTime = audio.duration * rate;
            updateProgress(audio,audioIndex);
        }
    }

    function end() {
        flag = false;
    }
}

/**
 * 更新进度条与当前播放时间
 * @param {object} audio - audio对象
 */
function updateProgress(audio,audioIndex) {
    var value = audio.currentTime / audio.duration;
    $('#progressBar'+audioIndex).css('width', value * 100 + '%');
    $('#progressDot'+audioIndex).css('left', value * 100 + '%');
    $('#audioCurTime'+audioIndex).html(transTime(audio.currentTime));
}

/**
 * 播放完成时把进度调回开始的位置
 */
function audioEnded(audioIndex) {
    $('#progressBar'+audioIndex).css('width', 0);
    $('#progressDot'+audioIndex).css('left', 0);
    $('#audioCurTime'+audioIndex).html(transTime(0));
    $('#audioPlayer'+audioIndex).attr('src', '../images/audio_play_icon.png');
}

/**
 * 音频播放时间换算
 * @param {number} value - 音频当前播放时间，单位秒
 */
function transTime(value) {
    var time = "";
    var h = parseInt(value / 3600);
    value %= 3600;
    var m = parseInt(value / 60);
    var s = parseInt(value % 60);
    if (h > 0) {
        time = formatTime(h + ":" + m + ":" + s);
    } else {
        time = formatTime(m + ":" + s);
    }

    return time;
}

/**
 * 格式化时间显示，补零对齐
 * eg：2:4  -->  02:04
 * @param {string} value - 形如 h:m:s 的字符串
 */
function formatTime(value) {
    var time = "";
    var s = value.split(':');
    var i = 0;
    for (; i < s.length - 1; i++) {
        time += s[i].length == 1 ? ("0" + s[i]) : s[i];
        time += ":";
    }
    time += s[i].length == 1 ? ("0" + s[i]) : s[i];

    return time;
}