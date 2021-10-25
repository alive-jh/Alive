/**
 * Created by cha on 2015/12/24.
 */
(function($){
    $.fn.dateRange=function(config){
    	var _config={
    		callback:null,
    		mask:true,
    		loop:false,
    		listClass:'cha-scorll-list',
    		oldData:'',
    		listData:['请选择']
    	}
    	this.config={};
    	$.extend(true,this.config,_config,config);
        this.opt=new Date().getTime();
        this.dataShow=$(this).find('input[type="text"]');
        init.call(this);
        listener.call(this);
        var that=this;
        this.swiperScorll = new Swiper('.'+this.config.listClass+this.opt, {
            direction : 'vertical',
            slidesPerView : 9,
            centeredSlides : true,
            mousewheelControl : true,
            loop:that.config.loop,
            onInit:function(swiper){
            },
            onSlideChangeEnd: function(swiper){
            }

        });

    };
    function init(){
        var that=this;
        var $dateSlider;
        var $mask;
        if(!$mask){$mask=$('<div id="cha-scorll-mask"></div>');}
        $scorllSlider=$('<div class="cha-scorll-slider cha-scorll-slider'+that.opt+'"><div class="cha-scorll-box"></div></div>');
        var _strHead='<div class="cha-scorll-head">' +
            '<input type="button" class="blue" id="cha-scorll-sub-btn'+that.opt+'" value="确定"/>' +
            '<input type="button" class="gray" id="cha-scorll-cancel-btn'+that.opt+'" value="取消"/>' +
            '</div>';
        var _strBody='<div class="cha-scorll-body"><div class="select-box"></div>' +
            '<div class="cha-scorll-block"></div>' +
            '</div>';
        var _strList='<div style="overflow:hidden;position: relative;background-color:#D1D5DB;">' +
            '<div class="cha-scorll-list '+that.config.listClass+that.opt+' swiper-container">' +
            '<ul class="cha-scorll-box swiper-wrapper" id="scorll-box'+that.opt+'">' +
            '<li class="swiper-slide">'+that.config.listData[0]+'</li>' +
            '</ul></div></div>';
        $scorllSlider.find('.cha-scorll-box').html(_strHead+_strBody);
        $scorllSlider.find('.cha-scorll-block').html(_strList);

        $('body').append($scorllSlider);
        $('body').append($mask);
        that.dataShow.attr('value',that.config.oldData);
        that.dataShow.val(that.config.oldData);


    }
    function listener(){
        var that=this;
        
        //显示选择信息
        that.click(function(){
            var defaultD = that.dataShow.attr('value');
            setScorll(that,that.dataShow, 'scorll-box'+that.opt,defaultD);
            that.dataShow.attr('value', that.config.oldData ? that.config.oldData : '');
            if(that.config.mask) $('#cha-scorll-mask').show();
            $('.cha-scorll-slider'+that.opt).animate({bottom: '0'}, 400);
        });
        

        //确认按钮点击
        $('#cha-scorll-sub-btn'+that.opt).click(function () {
            that.config.oldData = that.dataShow.attr('value');
           
			$('.cha-scorll-slider'+that.opt).animate({'bottom': '-100%'}, 100);
            if(that.config.mask) $('#cha-scorll-mask').hide();
            that.config.callback&&that.config.callback(that);
        });
        //取消按钮点击
        $('#cha-scorll-cancel-btn'+that.opt).click(function () {
            that.dataShow.attr('value', that.config.oldData ? that.config.oldData : '');
            that.dataShow.val(that.config.oldData ? that.config.oldData : '');
            $('.cha-scorll-slider'+that.opt).animate({'bottom': '-100%'}, 100);
            if(that.config.mask) $('#cha-scorll-mask').hide();
        });
        if(that.config.mask){
        	$('#cha-scorll-mask').click(function () {
	            that.dataShow.attr('value', that.config.oldData ? that.config.oldData : '');
	            that.dataShow.val(that.config.oldData ? that.config.oldData : '');
	            $('.cha-scorll-slider'+that.opt).animate({'bottom': '-100%'}, 100);
	            if(that.config.mask) $('#cha-scorll-mask').hide();
	        });
        }
        
    }
    //设置数据
    function setScorll(that,dInput,scorllBox,defaultD){
        var $dInput=dInput;//数据显示框
        var $scorllBox=$('#'+scorllBox);//数据列表框
        var endValue='';
        var listData=that.config.listData;
        //初始化数据
        that.swiperScorll['params'].onInit=function(){
            swiInitScorll(that,listData,swiperScorll,$scorllBox,defaultD,endValue);
        };
        swiInitScorll(that,listData,that.swiperScorll,$scorllBox,defaultD,endValue);
		changeEnd($dInput,$scorllBox,endValue);

        //设置数据改变函数
        that.swiperScorll['params'].onSlideChangeEnd=function(){

            changeEnd($dInput,$scorllBox,endValue);
        };

    }

    //初始化数据
    function swiInitScorll(that,listData,swiper,scorllBox,defaultD,endValue){
        var s=[];
        var defaultIndex=0;
        var defaultD=defaultD?defaultD:"请选择";
        scorllBox.html('');
        if(listData.length==1){
        	swiper['params'].loop=false;
        	scorllBox.html('');
        	swiper.appendSlide('<li class="swiper-slide">'+listData[0]+'</li>');
        	return;
        }else if(that.config.loop){
        	swiper['params'].loop=true;
        }
    	for(var i=1,j=0;i<listData.length;i++,j++){
            if(listData[i]==defaultD){
                defaultIndex=that.config.loop?(j+8):j;
            }
            s[i]='<li class="swiper-slide">'+listData[i]+'</li>';
        }
    	swiper.appendSlide(s);
    	if(defaultIndex==0&&that.config.loop){
    		defaultIndex+=8;
    	}
        swiper.slideTo(defaultIndex,0);
        endValue=scorllBox.find('li.swiper-slide-active').html();
    }

    function changeEnd($dInput,scorllBox,endValue){
        //改变最终数据
        endValue=scorllBox.find('li.swiper-slide-active').html();
        $dInput.attr('value',endValue);
        $dInput.val(endValue);
    }

})(Zepto);