function like(e) {
    var index = $(e).data("index");
    var momentId = list[index].id;
    $.post('/snp/v2/student/grade/isNotOrLike?userId=' + studentId + '&momentId=' + momentId +'&status='+likeStatus, function (res) {
        if (res.code == 200) {
            console.log(res);
            console.log(res.data.status)
            if (res.data.status != 1) {
                $('#likeIcon' + index).attr('src', '../images/kaquan_zan_icon.png');
                $('#likeCount' + index).text(res.data.likeCount);
            } else {
                $('#likeIcon' + index).attr('src', '../images/kaquan_dianzhan_press_icon.png');
                $('#likeCount' + index).text(res.data.likeCount);
            }
            console.log(res.data.status);
        }
    })
}