function adCallJSFunction(url, time) {
    // document.getElementById("test").innerHTML = "" + text
    // $("#text1").text(text)
    addComment(url, time);
}

function callDevice() {
    try {
        window.webkit.messageHandlers.jsCallOCRecordFunction.postMessage("开始录音");
    } catch (e) {
        console.log(e)
    }
    try {
        window.comment.tape()
    } catch (e) {
        console.log(e)
    }
}

function ocCallJSFunction(data) {
    // $("#text1").text(data)
    // let textJSON = JSON.parse(data);
    // document.getElementById("test").innerHTML = "" + data
    addComment(data.url, data.timeInt);
}

function addComment(url, timeInt) {
    $.post('/snp/v2/student/grade/saveRandComment?momentId=' + momentId + '&studentId=' + studentId + "&content=" + "" + '&teacherId=' + teacherId + "&audioUrl=" + url + "&audioTime=" + timeInt, function (res) {


        if (res.code == 200) {
            var Alias = '       <span class= "student_span textMarginLeft" style="display:inline">我的</span>\n'

            var createTime = formatDateTime(res.data.randComment.createDate.time);
            // $("#test").prepend(
            //     '<div id="start' + res.data.randComment.id + '" class="images_and_name" >\n         ' +
            //     '       <img  class="student_avatar" src="' + (res.data.Info.avatar == "" ? "../images/defaultAvatar.png" : res.data.Info.avatar) + '">\n        ' +
            //     '    <div>\n               ' +
            //     '       <span class="parentName">' + res.data.Info.name + '</span>\n       ' +
            //     Alias +
            //     '       <span id="del" class="delete" onclick="remove(' + res.data.randComment.id + ')">删除</span>\n' +
            //     '       <span  class="Time">' + createTime + '</span>\n          ' +
            //     '          <span class="commentContent">' + res.data.randComment.content + '</span>\n             ' +
            //     '      </div>\n               \n      ' +
            //     ' </div>'
            // );
            $("#test").prepend(
                '<div id="start' + res.data.randComment.id + '" class="images_and_name">\n         ' +
                '       <img  class="student_avatar" src="' + (res.data.Info.avatar == "" ? "../images/defaultAvatar.png" : res.data.Info.avatar) + '">\n        ' +
                '       <span class="parentName">' + res.data.Info.name + '</span>\n' +
                Alias +
                '       <span id="del" class="delete" onclick="remove(' + res.data.randComment.id + ')">删除</span>\n' +
                '       <span  class="Time">' + createTime + '</span>\n      ' +
                '       <div class="audio-box" onclick="playCommentAudio(' + res.data.randComment.id + ')" style="width: ' + (20 + res.data.randComment.audioTime * 0.5) + '%"><img src="../images/pinglun_yinping_icon@2x.png" alt=""><span>' + res.data.randComment.audioTime + '\'\'</span></div>' +
                '       <audio id="commentAudio' + res.data.randComment.id + '" onended="commentAudioEnded('+res.data.randComment.id+')" src="' + res.data.randComment.audioUrl + '"></audio>' +
                '      </div>'
            );

            //
            // var height = $("#start" + res.data.randComment.id + " .commentContent").height();
            // $("#start" + res.data.randComment.id).css("height", (103 + height) + "px")
            // var parentNameWidth = $("#start" + res.data.randComment.id + " .parentName").width();
            // $("#start" + res.data.randComment.id + " .textMarginLeft").css("left", (60 + parentNameWidth) + "px")


            $("#text").val('');
            var num = $("#counts" + momentId).text();
            $("#counts" + momentId).text(parseInt(num) + 1);
            if (studentId != 0 && studentId != null) {
                commentList.unshift({
                    studentAvatar: res.data.Info.avatar,
                    content: res.data.randComment.content,
                    createDate: res.data.randComment.createDate.time,
                    momentId: res.data.randComment.id,
                    audioUrl: res.data.randComment.audioUrl,
                    audioTime: res.data.randComment.audioTime,
                    studentName: res.data.Info.name,
                    studentId: studentId
                });
            } else if (teacherId != 0 && teacherId != null) {
                commentList.unshift({
                    teacherAvatar: res.data.Info.avatar == "" ? "../images/defaultAvatar.png" : res.data.Info.avatar,
                    content: res.data.randComment.content,
                    createDate: res.data.randComment.createDate.time,
                    momentId: res.data.randComment.id,
                    audioUrl: res.data.randComment.audioUrl,
                    audioTime: res.data.randComment.audioTime,
                    teacherName: res.data.Info.name,
                    teacherId: teacherId
                });

            }

            document.getElementById("text").style.height = "20px";
            document.getElementById("randcomment").scrollTop = document.documentElement.scrollTop = 0;
        } else {
            $.alert("发表评论失败", function () {
                window.location.reload();
            });
            $("#text").val('');
        }

    })
}

var commentAudioIndex = 0;

function playCommentAudio(index) {
    if (commentAudioIndex != 0 && commentAudioIndex != index) {
        $("#commentAudio" + commentAudioIndex)[0].pause();
        $("#start" + commentAudioIndex + " .audio-box img").attr("src", "../images/pinglun_yinping_icon@2x.png")
    }
    // console.log($("#commentAudio" + index)[0]);
    var player = $("#commentAudio" + index)[0]; /*jquery对象转换成js对象*/
    if (player.paused) { /*如果已经暂停*/
        player.play(); /*播放*/
        $("#start" + index + " .audio-box img").attr("src", "../images/yinpin_gif.gif")
    } else {
        player.pause();/*暂停*/
        $("#start" + index + " .audio-box img").attr("src", "../images/pinglun_yinping_icon@2x.png")
    }
    commentAudioIndex = index;
}


function commentAudioEnded(index) {
    $("#start" + index + " .audio-box img").attr("src", "../images/pinglun_yinping_icon@2x.png")
}