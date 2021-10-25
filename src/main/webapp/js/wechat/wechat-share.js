(function($) {
	
	var wechatShare = $.wechatShare;
	
	var signatureResult;
	
	$.wechatShare = function (config){
		
		this.config = config;
		
		var signatureServlet="/wechat/api/wechatShareSignature";
		
		signature(signatureServlet);
		
		if(signatureResult==undefined)return;

		wx.config({
		    debug: config.debug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: signatureResult.appid, // 必填，公众号的唯一标识
		    timestamp: signatureResult.timestamp, // 必填，生成签名的时间戳
		    nonceStr: signatureResult.noncestr,  // 必填，生成签名的随机串
		    signature: signatureResult.signature,// 必填，签名，见附录1
		    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		
		wx.ready(function(){
			wx.onMenuShareAppMessage({
			    title: config.title, // 分享标题
			    desc: config.desc, // 分享描述
			    /* link: 'https://wechat.fandoutech.com.cn/wechat/api/wechatShare?vid='+"${video[0]}", */ // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
			    link: config.link,
			    imgUrl: config.imgUrl, // 分享图标
			    type: config.type?config.type:'link', // 分享类型,music、video或link，不填默认为link
			    dataUrl: config.dataUrl?config.dataUrl:'', // 如果type是music或video，则要提供数据链接，默认为空
			    success: config.success,
			    cancel: config.cancel
			});
			
			wx.onMenuShareTimeline({
			    title: config.title, // 分享标题
			    link: config.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
			    imgUrl: config.imgUrl, // 分享图标
			    success:  config.success,
			    cancel: config.cancel
			});
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
                alert("ajax请求错误！！");
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