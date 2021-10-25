
//var  teacherList=[];
var  commentList=[];
var momentId;
//var data = {"msg":"OK","code":200,"data":[{"studentId":672,"name":"little boy","id":2,"avatar":"http://word.fandoutech.com.cn/app/StudentInfo/westson/95367de18014350ca1d101e6e65e644c.jpg","momentId":137,"content":"卡卡是领导看见阿萨啊","createDate":1562059768000},{"studentId":672,"name":"little boy","id":1,"avatar":"http://word.fandoutech.com.cn/app/StudentInfo/westson/95367de18014350ca1d101e6e65e644c.jpg","momentId":137,"content":"asdasdasdas","createDate":1562059728000}]};
//var studentId=672;

var txt;

var randCommentId;

var Number=1;

var loading2 = false;  //状态标记

function openComment(momentid){
    loading2 = true;
    //$(document.body).css("overflow-y","hidden");
    //document.body.style.position = 'fixed';
    //document.body.style.overflow = 'hidden';
    document.getElementById("randcomment").style.display = 'block';
    // 在这里获取滚动的距离，赋值给body，好让他不要跳上去。
    document.body.style.overflow = 'hidden';
    document.body.style.position = 'fixed';
    document.body.style.top = -scrollT + 'px';//改变css中top的值，配合fixed使用
    // 然后找个变量存一下刚才的scrolltop，要不然一会重新赋值，真正的scrollT会变0
    LastScrollT = scrollT;

    momentId=momentid;
    $("#about").popup();
    $("#test").empty();
    commentList=[];
    //teacherList=[];
    Number=1;
    getComment(momentId);
}


$(document.getElementById("randcomment")).on("resize scroll",function(){

    var windowHeight = $(document.getElementById("View")).height();//当前窗口的高度
    var scrollTop = $(document.getElementById("randcomment")).scrollTop();//当前滚动条从上往下滚动的距离
    var docHeight = $(document.getElementById("test")).height(); //当前文档的高度
    console.log(scrollTop, windowHeight, docHeight);
    //当 滚动条距底部的距离 + 滚动条滚动的距离 >= 文档的高度 - 窗口的高度
    //换句话说：（滚动条滚动的距离 + 窗口的高度 = 文档的高度）  这个是基本的公式


    if (scrollTop + windowHeight >= docHeight&&loading2) {
        loading2=false;
        Number++;
        getComment(momentId);
        console.log("===加载更多数据===");
    }

});

function getComment(momentId){
    $.ajax({
        url:"/wechat/v2/student/grade/getRandComment",
        data:{
            "momentId":momentId,
            "pageNumber":Number,
            "pageSize":3,
        },
        async: false,
        type:"get",
        success:function (res) {
            if (res.code == 200) {
                // commentList=[];
                //commentList=res.data.list;
                res.data.list.forEach(function (v){
                    commentList.push(v);
                });
                loading2=true;
                query();
                if (res.data.isLastPage){
                    loading2=false;
                    return;
                }
            }
        }
    })
}


/*
* ①老师数据与学生数据分开
* ②先把学生数据正常填上
* ③把老师的部分用append插到前面
* 放老师下面的方法：记录老师评论数，学生评论插到老师评论下
* */

var teacherStudentIcon;
var teacherStudentSpan;
var teacherStudentname;
var teacherStudenAvatar;
var displayStr;
var  Dele;

function query() {

    /* $("#test").empty();
     commentList=[];
     */
    var html=''
    for (var i = 0; i <commentList.length;i++){

        //评论id
        randCommentId=commentList[i].id;

        var  time =formatDateTime(commentList[i].createDate);

        //内容
        var content = commentList[i].content;

        if(commentList[i].teacherId!=0&&commentList[i].teacherId!=null){

            teacherStudentIcon="teacher_span";
            teacherStudentSpan="老师";
            displayStr="inline";

            //头像
            if ((typeof commentList[i].teacherAvatar)=="string"){
                teacherStudenAvatar=commentList[i].teacherAvatar;
            }else {
                teacherStudenAvatar ="../images/defaultAvatar.png"
            }

            //名字
            teacherStudentname=commentList[i].teacherName;

            //根据学生id判断隐藏删除
            if (commentList[i].teacherId != teacherId ){
                Dele = '';
            }else{
                Dele ='<span id="del" class="delete" onclick="remove('+randCommentId+')">删除</span>\n'
            }

        }else if (commentList[i].studentId!=0&&commentList[i].studentId!=null){

            //头像
            if ((typeof commentList[i].studentAvatar)=="string"){
                teacherStudenAvatar=commentList[i].studentAvatar;
            }else {
                teacherStudenAvatar ="../images/defaultAvatar.png"
            }

            teacherStudentname=commentList[i].studentName;

            //根据学生id判断隐藏删除
            if (commentList[i].studentId != studentId ){
                Dele = '';
                displayStr="none";
            }else{
                displayStr="inline";
                teacherStudentIcon="student_span";
                teacherStudentSpan="我的";

                Dele ='<span id="del" class="delete" onclick="remove('+randCommentId+')">删除</span>\n'
            }
        }


        // var fontNum = content.length/24;
        // console.log("行数"+fontNum)
        // var height = 128;
        // if (fontNum>2){
        //     height = (110+(fontNum)*30);
        // }
        html += '<div id="start'+randCommentId+'" class="images_and_name">\n         ' +
            '       <img  class="student_avatar" src="'+teacherStudenAvatar+'">\n        ' +
            /*'    <div>\n               ' +*/
            '       <span class="parentName">'+teacherStudentname+'</span>\n'+
            '       <span class="'+teacherStudentIcon+' textMarginLeft" style="display:'+displayStr+';">'+teacherStudentSpan+'</span>'+Dele +
            '       <span  class="Time">'+time+'</span>\n          ' +
            '          <span class="commentContent">'+content+'</span>\n             ' +
            /*'      </div>\n' +*/
            '      </div>'
    }

    $("#test").html(html);
    for (var i = 0; i <commentList.length;i++){

        var height = $("#start"+commentList[i].id+" .commentContent").height();
        var parentNameWidth = $("#start"+commentList[i].id+" .parentName").width();
        $("#start"+commentList[i].id).css("height",(103+height)+"px")
        $("#start"+commentList[i].id+" .textMarginLeft").css("left",(60+parentNameWidth)+"px")
    }

}

function save(txt,momentId,studentId,teacherId) {
    txt = document.getElementById("text").value;


    if (txt.trim()=="") {
        $.alert("输入内容不能为空");
        return;
    }

    $.post('/wechat/v2/student/grade/saveRandComment?momentId='+momentId+'&studentId='+studentId+"&content="+txt+'&teacherId='+teacherId,function (res) {

        if (txt.trim()!="") {

            if (res.code == 200) {


                 if (teacherId !=0 && teacherId !=null ) {
                    var Alias= '       <span class= "teacher_span textMarginLeft" style="display:inline">老师</span>\n'
                }else {
                     var Alias= '       <span class= "student_span textMarginLeft" style="display:inline">我的</span>\n'
                 }



                var createTime =  formatDateTime(res.data.randComment.createDate.time);
                $("#test").prepend(
                    '<div id="start'+res.data.randComment.id+'" class="images_and_name" >\n         ' +
                    '       <img  class="student_avatar" src="'+(res.data.Info.avatar==""?"../images/defaultAvatar.png":res.data.Info.avatar)+'">\n        ' +
                    '    <div>\n               ' +
                    '       <span class="parentName">'+res.data.Info.name+'</span>\n       ' +
                    Alias+
                    '       <span id="del" class="delete" onclick="remove('+res.data.randComment.id+')">删除</span>\n' +
                    '       <span  class="Time">'+createTime+'</span>\n          ' +
                    '          <span class="commentContent">'+res.data.randComment.content+'</span>\n             ' +
                    '      </div>\n               \n      ' +
                    ' </div>'
                );
                var height = $("#start"+res.data.randComment.id+" .commentContent").height();
                $("#start"+res.data.randComment.id).css("height",(103+height)+"px")
                var parentNameWidth = $("#start"+res.data.randComment.id+" .parentName").width();
                $("#start"+res.data.randComment.id+" .textMarginLeft").css("left",(60+parentNameWidth)+"px")


                $("#text").val('');
                var num = $("#counts"+momentId).text();
                $("#counts"+momentId).text(parseInt(num)+1);
                if (studentId !=0 && studentId !=null){
                    commentList.unshift({
                        studentAvatar: res.data.Info.avatar,
                        content: res.data.randComment.content,
                        createDate: res.data.randComment.createDate.time,
                        momentId: res.data.randComment.id,
                        studentName: res.data.Info.name,
                        studentId:studentId
                    });
                }else if (teacherId !=0 && teacherId !=null ) {
                    commentList.unshift({
                        teacherAvatar: res.data.Info.avatar==""?"../images/defaultAvatar.png":res.data.Info.avatar,
                        content: res.data.randComment.content,
                        createDate: res.data.randComment.createDate.time,
                        momentId: res.data.randComment.id,
                        teacherName: res.data.Info.name,
                        teacherId:teacherId
                    });

                }

                document.getElementById("text").style.height="20px";
                document.getElementById("randcomment").scrollTop = document.documentElement.scrollTop = 0;
            } else {
                $.alert("发表评论失败", function () {
                    window.location.reload();
                });
                $("#text").val('');
            }

        }
    })
}

function remove(randCommentId){
    $.confirm({
        title: '真的要删除吗？',
        onOK: function () {
            //点击确认
            confirmDeleteComment();
        },
        onCancel: function () {
            return;
        }
    });
    function confirmDeleteComment() {
        $.post('/wechat/v2/student/grade/deleteRandComment?randCommentId=' + randCommentId, function (res) {
            if (res.code == 200) {

                $('#start' + randCommentId).remove();
                var num = $("#counts" + momentId).text();
                $("#counts" + momentId).text(parseInt(num) - 1);
                commentList = [];
                Number = 1;
                getComment(momentId);

            } else {
                $.alert("删除评论失败");
            }
        })
    }
}

function closeComment() {

    //$(document.body).css("overflow-y","auto");\
    //document.body.style.position = 'relative';
    //document.body.style.overflow = 'auto';

    console.log(LastScrollT)
    document.getElementById("randcomment").style.display = 'none';
    document.body.style.overflow = 'auto';
    document.body.style.position = 'static';

    // 关闭close弹层的时候，改变js中的scrollTop值为上次保存的LastScrollT的值。并根据兼容性赋给对应的值。
    if (window.pageXOffset) {
        window.pageYOffset = LastScrollT;
    }else{
        document.body.scrollTop = LastScrollT;
        document.documentElement.scrollTop = LastScrollT;
    }

    $.closePopup();
    $("#test").empty();
    commentList=[];
    Number=1;

    document.getElementById("text").style.height="20px";
    $("#text").val('');
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

var scrollT = null;
var LastScrollT = 0;
window.onscroll = function (e) {
    scrollT = getScrollOffset().y;//滚动条距离
}

