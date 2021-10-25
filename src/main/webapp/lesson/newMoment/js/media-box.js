

Vue.component('media-box', {
    template: '<div class="media_box_style">\n' +
        '    <img v-bind:src="imgSrc" v-show="isShowImg" ref="bgImg" class="bg_img" />\n' +
        '    <video id="video_play" v-on:ended="end" ref="myVideo" v-bind:src="audioSrc"  x5-video-player-fullscreen="true"\n' +
        'webkit-playsinline="true" x-webkit-airplay="true" playsinline="true" airplay="true"  x5-video-player-type="h5-page"\n' +
        '    ></video>\n' +
        '    </div>',
    data: function() {
        return {
            count: 0,
            replyList: [],
            imgSrc: "",
            audioSrc: "",
            isShowImg: true,
            temp: []
        };
    },
    mounted() {

        function getQueryName(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)
                return unescape(r[2]);
            return;
        }

        let group = getQueryName("groupId");

        let that = this;
        $.ajax({
            url:
                "http://test.fandoutech.com.cn/wechat/v2/lesson/lessonReplyGroup",
            data: {
                member:0,
                group:group,
            },
            type: "post",
            success: function(res) {
                console.log(res)
                that.classRoom = res.data.classRoom;
                that.momentData = res.data.lessonReplys;
                temp = res.data.lessonReplys;
                console.log(temp);
                if (that.momentData.length <= 0) {
                    return;
                }
                console.log(12312, that.momentData);
                that.momentData.forEach(function(v) {
                    that.replyList.push(v);
                });
                that.setHome();
            }
        });

    },
    methods: {
        init: function() {
            let that = this;
            temp.forEach(function(v) {
                that.replyList.push(v.replyScript[0]);
            });
            console.log(that.replyList);
            this.setHome();
        },
        stop: function() {

        },
        showClssRoom:function(){

        },
        end: function() {
            this.isShowImg = true;
            console.log("播放完毕");
            this.count++;
            if (this.count >= this.replyList.length) {
                this.count = 0;
                this.$parent.stopPlay();

                let that = this;
                let json = that.replyList[0].replyScript[0];

                if (json.type == "video") {
                    this.imgSrc = json.content + "?vframe/jpg/offset/1/w/375/h/375";
                }
                if (json.type == "audio") {
                    if (json.picture == null || json.picture == undefined) {
                        console.log("音频背景");
                        that.imgSrc =
                            "http://image.fandoutech.com.cn/ico_megaphone@3x.gif";
                    } else {
                        that.imgSrc = json.picture;
                    }
                }
                if (json.type == "picture") {
                    this.imgSrc = json.content;
                }
                return;

            }
            this.start();
        },

        start: function() {
            let that = this;
            console.log("打印数据", this.replyList);
            let json = this.replyList[this.count].replyScript[0];
            console.log(json);

            switch (json.type) {
                case "audio":
                    if (json.picture == null || json.picture == undefined) {
                        console.log("音频背景");
                        this.imgSrc =
                            "http://image.fandoutech.com.cn/ico_megaphone@3x.gif";
                    } else {
                        this.imgSrc = json.picture;
                    }

                    console.log(this.$refs.myVideo + this.count);
                    this.$refs.myVideo.src = json.content;
                    this.$refs.myVideo.play();

                    break;
                case "picture":
                    this.imgSrc = json.content;

                    setTimeout(function() {
                        that.imgSrc = "";
                        that.end();
                    }, 5000);
                    break;

                case "video":
                    this.isShowImg = false;
                    this.$refs.myVideo.src = json.content;
                    this.$refs.myVideo.play();
                    this.$refs.myVideo.style = "block";
                    break;

                default:
                    break;
            }
        },
        setHome: function() {
            let that = this;
            let json = that.replyList[0].replyScript[0]
            if (json.type == "video") {
                this.imgSrc = json.content + "?vframe/jpg/offset/1/w/375/h/375";
            }
            if (json.type == "audio") {
                if (json.picture == null || json.picture == undefined) {
                    console.log("音频背景");
                    that.imgSrc =
                        "http://image.fandoutech.com.cn/ico_megaphone@3x.gif";
                } else {
                    that.imgSrc = json.picture;
                }
            }
            if (json.type == "picture") {
                this.imgSrc = json.content;
            }

        }
    }
});



