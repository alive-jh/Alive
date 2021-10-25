//菜单按钮点击效果
$(function () {
    //菜单按钮效果
    $('.switch-btn').click(function () {
        var $parentEle = $(this).parent().parent();
        if ($parentEle.hasClass('on')) {
            $parentEle.addClass('off');
            $parentEle.removeClass('on');
        } else {
            $parentEle.addClass('on');
            $parentEle.removeClass('off');
        }

    });

    //编辑列表效果
    var selectedList = [];//选中项
    var totalPrice=0;//总价格
    $('#orderEdit').click(function () {
        //$(this).toggleClass('delete');
        if ($(this).hasClass('delete')) {
            $(this).html('删除');
            $('.book-list li a .list-num').hide();
            $('.book-list li a .check-group').show();
            $('.book-list li').removeClass('selected');
            $('.book-list li').find('input[type="checkbox"]').prop('checked', '');
            $('.select-all').removeClass('selected');
            $('.select-all').find('input[type="checkbox"]').prop('checked', '');
            $('.select-all').show();

            deleteList();
            selectedList = [];
            $('.total-price').html(0.00);
            $('.price-info b').show();
        } else {
            $(this).html('编辑');
            $('.book-list li a .list-num').show();
            $('.book-list li a .check-group').hide();
            $('.book-list li').removeClass('selected');
            $('.book-list li').find('input[type="checkbox"]').prop('checked', '');
            $('.select-all').removeClass('selected');
            $('.select-all').find('input[type="checkbox"]').prop('checked', '');
            $('.select-all').hide();

            //删除选中项
            /*deleteList();
            selectedList = []
            $('.total-price').html(0.00);
            $('.price-info b').hide();*/
        }
        
    });
    $('#orderEdit').trigger('click');
    //取消按钮点击
    /*$('#cancelBtn').click(function(){
        if ($('#orderEdit').hasClass('delete')) {
            $('#orderEdit').removeClass('delete');
            $('#orderEdit').html('编辑');
            $('.book-list li a .list-num').show();
            $('.book-list li a .check-group').hide();
            $('.book-list li').removeClass('selected');
            $('.book-list li').find('input[type="checkbox"]').prop('checked', '');
            $('.select-all').removeClass('selected');
            $('.select-all').find('input[type="checkbox"]').prop('checked', '');
            $('.select-all').hide();

            selectedList = [];
            $('.total-price').html(0.00);
            $('.price-info b').hide();
        }
    });*/

    //单击选择按钮效果
    $('.book-list li a').click(function () {
        if ($('#orderEdit').hasClass('delete')) {
            var $parentEle = $(this).parent();
            $parentEle.toggleClass('selected');
            if ($parentEle.hasClass('selected')) {
                $parentEle.find('input[type="checkbox"]').prop('checked', 'true');
            } else {
                $parentEle.find('input[type="checkbox"]').prop('checked', '');
            }
            updateSelectedList();
            if(selectedList.length==$('.book-list li').length){
                $('.select-all').addClass('selected');
                $('.select-all').find('input[type="checkbox"]').prop('checked', 'true');
            }else{
                $('.select-all').removeClass('selected');
                $('.select-all').find('input[type="checkbox"]').prop('checked', '');
            }
            $('.total-price').html(numFormat(totalPrice));
        }
    });



    //全选按钮效果
    $('.select-all').click(function(){
        //本身样式改变
        $(this).toggleClass('selected');
        $(this).find('input[type="checkbox"]').prop('checked', 'true');
        //其他选择按钮改变
        if($(this).hasClass('selected')){
            $('.book-list li').addClass('selected');
            $('.book-list li input[type="checkbox"]').prop('checked', 'true');
            $(this).find('input[type="checkbox"]').prop('checked', 'true');
        }else{
            $('.book-list li').removeClass('selected');
            $('.book-list li input[type="checkbox"]').prop('checked', '');
            $(this).find('input[type="checkbox"]').prop('checked', '');
        }
        updateSelectedList();
        //价格改变
        $('.total-price').html(numFormat(totalPrice));
    });

    //更新已选项
    function updateSelectedList() {
        selectedList = [];
        totalPrice=0;
        var $bookList = $('.book-list li.list-info');
        //console.log($bookList);
        for (var i = 0; i < $bookList.length; i++) {
            if ($bookList.eq(i).hasClass('selected')) {
                selectedList.push($bookList.eq(i).attr('data-id'));
                totalPrice+=$bookList.eq(i).find('.price span').html()-0;
            }
        }
    }

    //删除选中项
    function deleteList() {
        var $bookList = $('.book-list li.list-info');
        for (var i = 0; i < selectedList.length; i++) {
            for(var j=0;j<$bookList.length;j++){
				
                if($bookList.eq(j).attr('data-id')==selectedList[i]){
					document.forms['bookForm'].ids.value =$bookList.eq(j).attr('data-id')+",";
                  
					$bookList.eq(j).remove();
                }
            }

        }
		
    }

    //保留两位小数
    function numFormat(num){
        num+='';
        numA=num.split('.');
        numA[1]=numA[1]?numA[1]:'0';
        numA[1]+='0000000000000000';
        return numA[0]+('.'+numA[1].slice(0,2));
    }

});


