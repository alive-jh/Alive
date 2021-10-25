/**
 * Created by 何问起 午后的阳光 on 2016/5/14.
 */
var ran = 0;
var range = 0;
var myNumber;
var dataList;
var dataListLength;

function getDataList(){
	var formData = new FormData()
	$.ajax({
        url: '<%=request.getContextPath() %>/api/getQianDaoList' ,
        type: 'POST',
        data:formData,
        dataType: "json",  
        async: false,
        contentType: false,
        processData: false,
        beforeSend:function(){
	   //这里是开始执行方法
	  },
        success: function (returndata) {
        	dataList = returndata.data;
        	dataListLength = dataList.length;
        },
        error: function (returndata) {
        
        }
   });
	
}
/*将产生随机数的方法进行封装*/
function sjs(range) {
    ran = Math.random() * range;//[0,range)的随机数
    var result = parseInt(ran);//将数字转换成整数
    return result;
}
/*对显示随机数的方法进行封装*/
function showRandomNum() {
    var figure = sjs(dataListLength);
    $("#first").html(dataList[figure]);
}
$(function () {
    /*点击开始按钮,产生的事件*/
    $("#start").click(function () {
        
    	getDataList();
        /*将开始标签禁用，停止标签启用*/
        $("#start")[0].disabled = true;
        $("#stop")[0].disabled = false;
        if (range > 9999 || range<-999)
        {
            // by 何问起
            $("#bigDiv div").css("font-size", "60px");//http://hovertree.com/h/bjaf/omgdn4mu.htm
            //return;
        }
        myNumber = setInterval(showRandomNum, 50);//多长时间运行一次，单位毫秒
    });
    /*点击结束按钮*/
    $("#stop").click(function () {
        /*将开始标签启用，停止标签禁用*/
        $("#start")[0].disabled = false;
        $("#stop")[0].disabled = true;
        clearInterval(myNumber);
    });
});