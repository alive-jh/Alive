(function($) {
	
	var wechatShare = $.wechatShare;
	
	var signatureResult;
	
	$.wechatShare = function (config){
		
		this.config = config;
		
		var signatureServlet="/wechat/api/qYJssdkSignature";
		
		signature(signatureServlet);
		
		console.log(config.jsApiList);
		if(signatureResult==undefined)return;

		wx.config({
		    debug: config.debug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: signatureResult.appid, // 必填，公众号的唯一标识
		    timestamp: signatureResult.timestamp, // 必填，生成签名的时间戳
		    nonceStr: signatureResult.noncestr,  // 必填，生成签名的随机串
		    signature: signatureResult.signature,// 必填，签名，见附录1
		    /*jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2*/
		    jsApiList: config.jsApiList
		});
		
		
	}
	
	function signature(signatureServlet){
		
		$.ajax({
            type: "POST",
            url:signatureServlet,
            dataType: "json",
            data:{pageUrl:window.location.href},
            async: false,
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert("网络请求失败,请刷新一下");
                console.log("XMLHttpRequest.status----"+XMLHttpRequest.status);
                console.log("XMLHttpRequest.readyState----"+XMLHttpRequest.readyState);
                console.log("textStatus----"+textStatus);
                return;
            },
            success: function(result) {
            	if(result.code==200){
            		signatureResult = result;
            	}
            }
        });
			
		
		
	}
})(jQuery);