var scriptIndex = 0;

var video = document.createElement("VIDEO");
video.controls = true;
video.autoplay = true;
var audio = new Audio();
var replyAudio = new Audio();
window.onload = function() {

    $.ajaxSetup({

        beforeSend: function(XMLHttpRequest) {
            $.showLoading();
        },
        complete: function() {
            $.hideLoading();
        },
        error: function(data) {
            $.toptip("网络似乎有点问题 ~", 'error');
        }

    });

    app.init();

    $.wechatShare(config = {
        debug: false,
        jsApiList: []
    });

}

var countdown_sec = 60;

var app = new Vue({
    el: "#app",
    data: data,
    methods: {
        openApp: function() {

            app.getInviteCode(function(result) {
                if (result.code == 200) {
                    app.mask = false;
                    app.copy(result.data.inviteCode);
                    /* window.open("/wechat/lesson/2c/openApp.html?inviteCode="+result.data.inviteCode); */
                    window.open("/wechat/lesson/2c/start.html?inviteCode=" + result.data.inviteCode);

                } else if (result.code == 20501) {
                    app.mask = true;
                    video.style.display = 'none';
                    video.pause();
                } else {
                    $.toptip(result.msg, 'error');
                }
            });

        },
        closeBindPanel: function() {
            app.mask = false;
            video.style.display = "";
        },
        getInviteCode: function(callBack) {

            $.ajax({
                url: '/wechat/v2/lesson/getInviteCode?classRoom=' + classRoomId + "&inviter=" + inviter,
                type: "get",
                async: false,
                success: function(result) {
                    callBack(result);
                }
            })

            /* $.get('/wechat/v2/lesson/getInviteCode?classRoom='+classRoomId+"&inviter="+inviter, function(result) {
                callBack(result);
            }); */

        },
        copy: function(str) {

            var oInput = document.createElement('input');
            oInput.value = str;
            document.body.appendChild(oInput);

            var u = navigator.userAgent
                , app = navigator.appVersion;
            var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
            //ios终端

            if (isIOS) {
                var range = document.createRange();
                range.selectNode(oInput);
                window.getSelection().addRange(range);
                var successful = document.execCommand('copy');
            } else {
                oInput.select();
                // 选择对象
                document.execCommand("Copy");
                // 执行浏览器复制命令
                oInput.className = 'oInput';
                oInput.style.display = 'none';
            }

        },
        sendCaptcha: function() {

            $.post('/wechat/v2/lesson/sendCaptcha', {
                mobile: app.mobile
            }, function(result) {

                if (result.code == 200) {

                    app.countdown_('.send-btn');

                } else if (result.code == 20501) {

                    $.toptip("请输入正确的手机号码", 'error');

                } else {
                    $.toptip(result.msg, 'error');
                }

            });
        },
        countdown_: function(element) {
            var $_element = $(element);
            if (countdown_sec == 0) {
                $_element.removeAttr('disabled');
                $_element.text('获取验证码')
                countdown_sec = 60;
                return false;
            } else {
                $_element.attr("disabled", 'disabled');
                $_element.text(countdown_sec + 'S');
                countdown_sec--;
            }

            setTimeout(function() {
                app.countdown_(element);
            }, 1000);
        },
        bind: function(callBack) {

            $.post('/wechat/v2/lesson/bindAccount', {
                mobile: app.mobile,
                code: this.getCode()
            }, function(result) {
                if (result.code == 200) {
                    app.openApp();
                } else {
                    $.toptip(result.msg, 'error');
                }
            })
        },
        inputChange: function(index) {

            $('#code' + (index + 1)).focus();

            if (this.getCode().length == 4) {
                this.isDisble = false;
            } else {
                this.isDisble = true;
            }

        },
        init: function() {

            $.get('/wechat/v2/lesson/lessonIntro?classRoom=' + classRoomId, function(result) {
                if (result.code == 200) {
                    app.lessonIntro = result.data;
                    app.playerbg = app.lessonIntro.cover;
                } else {
                    $.toptip(result.msg, 'error');
                }
            });

            this.getLessonScript();
            this.getLessonComment();
            this.getClassroomProduct();
            this.getClassRoomReply();

        },
        getLessonScript: function(classRoom) {

            $.get('/wechat/v2/lesson/getClassScriptNormalList?classRoomId=' + classRoomId, function(result) {
                if (result.code == 200) {
                    app.lessonScripts = result.data;
                    setTimeout(function() {
                        app.playBtn = true;
                    }, 1500)
                }
            });

        },
        play: function(index) {

            if (index == 0) {
                video.play();
            }

            app.playBtn = false;
            app.musicPlaying = false;
            app.playerbg = app.lessonIntro.cover;

            var scriptTypeId = app.lessonScripts[index].classScriptTypeId;

            if (scriptTypeId == 102) {

                app.playMusic(app.lessonScripts[index].classScriptContent[0].content, function() {
                    app.playerbg = 'http://image.fandoutech.com.cn/bg@3x.png';
                    app.musicPlaying = true;
                }, function() {
                    app.musicPlaying = false;
                    app.playerbg = app.lessonIntro.cover;
                    app.play(++scriptIndex);
                });

            } else if (scriptTypeId == 201) {

                for (var i = 0; i < app.lessonScripts[index].classScriptContent.length; i++) {
                    if (app.lessonScripts[index].classScriptContent[i].command == 201) {
                        app.playMusic(app.lessonScripts[index].classScriptContent[i].content, function() {
                        }, function() {
                            app.play(++scriptIndex);
                        });
                    } else if (app.lessonScripts[index].classScriptContent[i].command == 221) {
                        app.playerbg = app.lessonScripts[index].classScriptContent[i].content;
                    } else {
                        app.play(++scriptIndex);
                    }

                }

            } else if (scriptTypeId == 202) {
                $.get('/wechat/app/getAudioInfo2?audioId=' + app.lessonScripts[index].classScriptContent[0].content, function(result) {
                    if (result.success == 1) {
                        app.playMusic(result.data[0].src, function() {
                            app.playerbg = 'http://image.fandoutech.com.cn/bg@3x.png';
                            app.musicPlaying = true;
                        }, function() {
                            app.musicPlaying = false;
                            app.playerbg = app.lessonIntro.cover;
                            app.play(++scriptIndex);
                        });
                    }
                });
            } else if (scriptTypeId == 211) {

                app.playVideo(app.lessonScripts[index].classScriptContent[0].content);

            } else if (scriptTypeId == 212) {

                $.get('/wechat/v2/lesson/getAudioInfo?audioId=' + app.lessonScripts[index].classScriptContent[0].content, function(result) {
                    if (result.code == 200) {
                        app.playVideo(result.data[0].src);
                    }
                });

            } else if (scriptTypeId == 221) {
                app.scriptPic = app.lessonScripts[index].classScriptContent[0].content;
                app.picShow = true;
                setTimeout(function() {
                    app.picShow = false;
                    app.play(++scriptIndex);
                }, 30000);
            } else {
                app.openAPP = true;
            }

        },
        playMusic: function(src, befor, ended) {

            if (src != '' && src != 'underfined') {
                audio.src = src;
                befor();
                $(audio).bind('ended', ended);
            } else {
                ended();
            }

            if (this.isWeiXin()) {

                WeixinJSBridge.invoke('getNetworkType', {}, function(e) {

                    audio.play();

                });

            } else {
                audio.play();
            }

        },
        playVideo: function(src) {
            video.style.display = 'block';
            video.src = src;
            video.preload = 'auto';
            video.id = 'video-player';
            video.controls = false;
            $('#palyer').append(video);

            if (window.orientation == 90) {
                app.fullScreen();
            }

            video.onended = function() {
                video.style.display = 'none';
                app.play(++scriptIndex);
            }

            if (this.isWeiXin()) {

                WeixinJSBridge.invoke('getNetworkType', {}, function(e) {

                    video.play();

                });

            } else {
                video.play();
            }

        },
        pausedAudio: function() {
            if (audio.paused) {
                app.audioBtnImage = 'http://image.fandoutech.com.cn/ico_megaphone@3x.gif';
                audio.play();
            } else {
                app.audioBtnImage = 'http://image.fandoutech.com.cn/ico_megaphone@3x%281%29.png';
                audio.pause();
            }
        },
        sendComment: function() {

            $.post('/wechat/v2/lesson/saveLessonComment', {
                classRoom: classRoomId,
                comment: this.lessonCommont
            }, function(success) {
                if (success.code == 200) {
                    app.lessonComments.push(success.data.comment);
                    app.lessonCommont = '';
                    $.toptip('评论成功', 'success');
                } else {
                    $.toptip(success.msg, 'error');
                }
            }, 'json')
        },
        getLessonComment: function() {

            $.get('/wechat/v2/lesson/getLessonComment?classroom=' + classRoomId, function(result) {

                if (result.code == 200) {
                    app.lessonComments = result.data.comments;
                }
            });

        },
        delComment: function(commentid, index) {
            $.post('/wechat/v2/lesson/delLessonComment', {
                commentid: commentid
            }, function(result) {
                if (result.code == 200) {
                    app.lessonComments.splice(index, 1);
                }
            })
        },
        getClassroomProduct: function() {
            $.get('/wechat/v2/lesson/getClassroomProduct?classroom=' + classRoomId, function(result) {
                if (result.code == 200) {
                    app.product = result.data.productId;
                }
            })
        },
        toProduct: function(product) {
            location.href = "/wechat/lesson/2c/lessonProduct.html?product=" + product;
        },
        getClassRoomReply: function() {

            $.get('/wechat/v2/lesson/classRoomReply?pageSize=1000&member=' + app.user + '&classroom=' + classRoomId, function(result) {
                if (result.code == 200) {
                    app.classRoomReply = result.data.list;
                }
            })

        },
        createCover: function(script) {

            var type = script[0].type;

            if (type == 'picture') {
                return script[0].content;
            } else if (type == 'audio') {
                return 'http://image.fandoutech.com.cn/bg@3x.png';
            } else if (type == 'video') {
                return script[0].content + '?vframe/jpg/offset/1/w/480/h/360';
            }

        },
        getCode: function() {
            var code = '';
            $('.bind-box .code input').each(function(index, element) {
                code += element.value;
            });
            return code;
        },
        isWeiXin: function() {
            var ua = navigator.userAgent.toLowerCase();
            return ua.indexOf('micromessenger') != -1;
        },
        lessonReply: function(groupId, index) {

            video.play();
            replyAudio.play();

            $.get('/wechat/v2/lesson/lessonReplyGroup?member=0&group=' + groupId, function(result) {
                if (result.code == 200) {
                    app.replyPlayer(result.data.lessonReplys, index,0);
                }
            })

        },
        replyPlayer: function(lessonReplys, index,playIndex) {

            $playBtn = $('#start-lesson' + index);
            $playBtn.show();

            if (playIndex >= lessonReplys.length) {

            }else{
                $playBtn.hide();
                var replyId = lessonReplys[playIndex].classScriptContent[0].command;

                if (replyId == 226) {

                    $('#reply' + index).append('<img class="reply-pic" alt="" src="'+lessonReplys[playIndex].replyScript[0].content+'">');

                    audio.src = 'https://source.fandoutech.com.cn/lesson_record_bgmusic.mp3';
                    $(audio).bind('ended', function() {
                        $('#reply' + index).empty();
                        app.replyPlayer(lessonReplys, index,++playIndex);
                    });
                    audio.load();
                    audio.play();

                } else if (replyId == 216) {

                    var $video = $(video);
                    var sourceDom = $("<source src=\"" + lessonReplys[playIndex].replyScript[0].content + "\">");
                    $video.append(sourceDom);
                    $video.load();
                    $video[0].onended = function() {
                        $('#reply' + index).empty();
                        app.replyPlayer(lessonReplys, index,++playIndex);
                    }
                    $video.addClass('reply-video')
                    $('#reply' + index).append($video);
                    $video[0].play();

                } else if (replyId == 207) {

                    $('#reply' + index).append('<img width="100%" height="100%" class="reply-pic" alt="" src="'+lessonReplys[playIndex].replyScript[0].picture+'">');
                    audio.src = lessonReplys[playIndex].replyScript[0].content;
                    /*$('#reply' + index).append('<img class="ico-audio" alt="" src="http://image.fandoutech.com.cn/ico_megaphone@3x.gif">');
                    $('#reply' + index).append('<img class="reply-pic" alt="" src="http://image.fandoutech.com.cn/bg@3x.png">');*/
                    $(audio).bind('ended', function() {
                        $('#reply' + index).empty();
                        app.replyPlayer(lessonReplys, index,++playIndex);
                    });
                    audio.load();
                    audio.play();

                } else if (replyId == 301) {

                    audio.src = lessonReplys[playIndex].replyScript[0].content;
                    $('#reply' + index).append('<img class="ico-audio" alt="" src="http://image.fandoutech.com.cn/ico_megaphone@3x.gif">');
                    $('#reply' + index).append('<img class="reply-pic reply-pic-top" alt="" src="http://image.fandoutech.com.cn/bg@3x.png">');
                    $(audio).bind('ended', function() {
                        $('#reply' + index).empty();
                        app.replyPlayer(lessonReplys, index,++playIndex);
                    });
                    audio.load();
                    audio.play();

                    /* app.replyText = app.lessonReplys[index][0].remark;
                    app.showReplyText = true;
                    app.playMusic(app.lessonReplys[index].replyScript[0].content,function(){
                        app.showAudioBtn = false;
                        app.showReplyText = false;
                        app.play(++replyIndex);
                    }); */
                }
            }

        },
        openReplyComtents: function(group) {

            this.selectGroup = group;

            $.get('/wechat/v2/lesson/lessonReplsCommens?pageNumber=1&pageSize=5&group=' + group, function(result) {
                if (result.code == 200) {
                    app.replyComtentLis = result.data.list;
                }
            })
            $("#about").popup();
        },
        sendReplyComment: function() {
            $.post('/wechat/v2/lesson/saveLessonReplsCommens', {
                group: this.selectGroup,
                comment: this.replyComment,
                member: app.user
            }, function(result) {
                if (result.code == 200) {
                    app.replyComtentLis.push(result.data);
                    app.replyComment = '';
                }
            });
        },
        delReplyComment: function(id, index) {
            $.post('/wechat/v2/lesson/delComment', {
                id: id
            }, function(result) {
                if (result.code == 200) {
                    app.replyComtentLis.splice(index, 1);
                }
            })
        },
        replyLike: function(groupId, myLike, index) {
            $.post('/wechat/v2/lesson/lessonReplyLikeH5', {
                wechat: app.user,
                group: groupId,
                like: 1 ^ myLike
            }, function(result) {
                if (result.code == 200) {
                    app.classRoomReply[index].likeCount = result.data.likeInfo.likeCount;
                    app.classRoomReply[index].myLike = result.data.likeInfo.myLike;
                }
            });
        },
        closePopup: function() {
            $.closePopup();
            this.getClassRoomReply();

        },
        fullScreen: function() {
            var element = video;
            if (element.requestFullScreen) {
                element.requestFullScreen();
            } else if (element.mozRequestFullScreen) {
                element.mozRequestFullScreen();
            } else if (element.webkitRequestFullScreen) {
                element.webkitRequestFullScreen();
            }

        }
    },
    filters: {
    }

});

// Listen for orientation changes
window.addEventListener("orientationchange", function() {
    if (window.orientation == 90 || window.orientation == -90) {
        app.fullScreen();
    }

}, false);
