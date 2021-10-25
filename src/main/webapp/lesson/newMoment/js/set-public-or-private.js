function publicBtnAction(e) {
    var index = $(e).data("index");
    var momentid = list[index].id;
    console.log(momentid)
    $.actions({
        actions: [{
            text: "私密·自己可见",
            onClick: function () {
                setPublicOrPrivateData(0, momentid, index);
            }
        }, {
            text: "开放·所有人可见",
            onClick: function () {
                setPublicOrPrivateData(1, momentid, index);
            }
        }]
    });
}

function setPublicOrPrivateData(v, momentid, index) {
    $.post('/snp/v2/student/grade/MomentIsNotOrPublic?momentId=' + momentid + '&b_public=' + v, function (res) {
        console.log(res.data);
        if (res.code == 200) {
            if (res.data) {
                list[index].bPublic = true;
                $('#publicBtn' + index).text("开放·所有人可见");
            } else {
                list[index].bPublic = false;
                $('#publicBtn' + index).text("私密·自己可见");
            }
        }
    })
}