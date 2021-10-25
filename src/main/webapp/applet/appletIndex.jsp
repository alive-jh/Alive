<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
  <head>
    <title>jQuery WeUI</title>
    <meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

<meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.
">

<link rel="stylesheet" href="lib/weui.min.css">
<link rel="stylesheet" href="css/jquery-weui.css">
<link rel="stylesheet" href="css/demos.css">

<script type="text/javascript" src="https://cdn.goeasy.io/goeasy.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>


<script>


	wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数
    appId: '${appId}', // 必填，公众号的唯一标识
    timestamp: ${timestamp},
	nonceStr: '${nonceStr}',
	signature: '${signature}',
    jsApiList: [  'checkJsApi',
            'chooseImage',
            'uploadImage',
            'downloadImage',
            'previewImage',
            'getNetworkType',
            'scanQRCode'
			] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});





  // 9.1.1 扫描二维码并返回结果
  function senQRCode()
  {
 
   wx.scanQRCode({
    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
    success: function (res) {
    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
    
   
    alert('= '+result.split(":")[1]);
    
		}
	});
 }
 
  


  function epalRelation()
  {
	 



   wx.scanQRCode({
    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
    success: function (res) {
    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
    
   
    		
    		
    		$.ajax({
			type: "POST",
			data:"",
			dataType: "json",
			url: "<%=request.getContextPath()%>/applet/epalRelation?memberId=${memberId}&epalId="+result.split(":")[1],
			context: document.body, 
			beforeSend:function(){
			},
			complete:function(){
			},
			success: function(data){
				
				
				if(data.status ==0)
				{
					

					   // loading
					  $.showLoading();

	  				   var goEasy = new GoEasy({
                              appkey: 'BC-82bffd0bf6bd4219a509ed45eb162f78'
                          });
                          //GoEasy-OTP可以对appkey进行有效保护，详情请参考：GoEasy-Reference
                          
                          goEasy.subscribe({
							channel: data.mobile,
							onMessage: function (message) {
							//alert("Channel:" + message.channel + " content:" + message.content);
							$.hideLoading();
							if(message ==0)
							{
								$.alert("", "绑定成功!");
							}
							else
							{
								$.alert("", "绑定失败!");
							}
							
						 



						}
						});
				}
				else
				{
					alert('绑定失败!');
				}
				
			}

		});
		
		
    
		}
	});
	
  }


function test()
{
	$.showLoading();

	$.alert("", "绑定成功");

}


</script>
  </head>

  <body ontouchstart>

 <header class='demos-header'>
      <h1 class="demos-title">蛋蛋机器人</h1>
    </header>

    <div class="weui-grids">
      <a href="javascript:wifi()" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="images/btn_robot_wifi_nor.png" alt="">
        </div>
        <p class="weui-grid__label">
          WIFI连接
        </p>
      </a>
      <a href="javascript:epalRelation()" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="images/saoma.png" alt="">
        </div>
        <p class="weui-grid__label">
          绑定机器人
        </p>
      </a>
      <a href="javascript:test();" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="images/ketang.png" alt="">
        </div>
        <p class="weui-grid__label">
          在线课堂
        </p>
      </a>
      <a href="" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="images/shuyuan.png" alt="">
        </div>
        <p class="weui-grid__label">
          未来书院
        </p>
      </a>
      <a href="" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="images/richeng.png" alt="">
        </div>
        <p class="weui-grid__label">
          日程管理
        </p>
      </a>
      <a href="" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="images/yinyue.png" alt="">
        </div>
        <p class="weui-grid__label">
          音频资源
        </p>
      </a>
      <a href="" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="images/shipin.png" alt="">
        </div>
        <p class="weui-grid__label">
          视频通话
        </p>
      </a>
      <a href="" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="images/haoyou.png" alt="">
        </div>
        <p class="weui-grid__label">
         好友列表
        </p>
      </a>
      <a href="" class="weui-grid js_grid">
        <div class="weui-grid__icon">
          <img src="images/geren.png" alt="">
        </div>
        <p class="weui-grid__label">
          个人中心
        </p>
      </a>
    </div>



    <script src="lib/jquery-2.1.4.js"></script>
<script src="lib/fastclick.js"></script>
<script>
  $(function() {
    FastClick.attach(document.body);
  });
</script>
<script src="js/jquery-weui.js"></script>

    <script>


	function wifi()
{
	
	$.login({
          title: '配置WIFI',
          text: '请输入WIFI名称和密码',
          onOK: function (username, password) {
            //console.log(username, password);
			
            var url = "http://qr.liantu.com/api.php?text=FD_SET_WIFI:{AP="+username+",PWD="+password+",mode=0}";
			window.location.href = url;
          },
          onCancel: function () {
            //$.toast('取消登录!', 'cancel');
          }
        });
}


    </script>
  </body>
</html>
