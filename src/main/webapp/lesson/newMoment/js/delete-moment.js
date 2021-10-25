function deleteMoment(e) {
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
        var momentId = list[index].id;
        $.post('/snp/v2/student/grade/deleteCard?momentId=' + momentId, function (res) {
            if (res.code == 200) {
                console.log(res);
                $('#moment' + index).remove();
                if (!result.data.isLastPage) {
                    //pageNumber++;
                    myList = [];
                    getDate(1);
                } else {
                    //$(document.body).destroyInfinite();
                    $('#infinite_div').hide();
                }


            }
        })
    }
}