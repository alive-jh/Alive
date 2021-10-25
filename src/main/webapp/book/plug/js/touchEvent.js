/**
 * Created by aspire V on 2015/12/20.
 */


(function(a){
    a.fn.touchwipe=function(c){

        /*
        * a:jquery
        * b:默认设置
        * c:自定义设置
        * d:触摸移动函数
        * e:设置计时器
        * f:触摸开始函数
        * g:鼠标原始y位置
        * h:鼠标原始x位置
        * i:判断是否是长按事件
        * j:判断是否是点击事件
        * k:点击事件函数
        * l:长按事件函数
        * m:移除计时器
        * n:鼠标当前x位置
        * o:鼠标y轴差值
        * p:鼠标x轴插值
        * q:event
        * r:鼠标当前y位置
        *
        *
        * */

        var b={
            target:this,
            drag:false,
            min_move_x:20,
            min_move_y:20,
            wipeLeft:function(){/*向左滑动*/},
            wipeRight:function(){/*向右滑动*/},
            wipeUp:function(){/*向上滑动*/},
            wipeDown:function(){/*向下滑动*/},
            wipe:function(){/*点击*/},
            wipehold:function(){/*触摸保持*/},
            wipeDrag:function(x,y){/*拖动*/},
            preventDefaultEvents:true
        };
        if(c){a.extend(b,c)};
        this.each(function(){
            var h,g,j=false,i=false,e;
            var supportTouch = "ontouchstart" in document;//判断设备是否支持触摸事件
            var moveEvent = supportTouch ? "touchmove" : "mousemove",
                startEvent = supportTouch ? "touchstart" : "mousedown",
                endEvent = supportTouch ? "touchend" : "mouseup"


            /* 移除 touchmove 监听 */
            function m(){
                window.removeEventListener(moveEvent,d);
                h=null;
                j=false;
                clearTimeout(e)
            };

            /* 事件处理方法 */
            function d(q){
                if(b.preventDefaultEvents){
                    q.preventDefault()
                };
                if(j){
                    var n = supportTouch ? q.touches[0].pageX : q.pageX;
                    var r = supportTouch ? q.touches[0].pageY : q.pageY;
                    var p = h-n;
                    var o = g-r;
                    if(b.drag){
                        h = n;
                        g = r;
                        clearTimeout(e);
                        b.wipeDrag(p,o,n,r);
                    }
                    else{
                        if(Math.abs(p)>=b.min_move_x){
                            m();
                            if(p>0){b.wipeLeft()}
                            else{b.wipeRight()}
                        }
                        else{
                            if(Math.abs(o)>=b.min_move_y){
                                m();
                                if(o>0){b.wipeUp()}
                                else{b.wipeDown()}
                            }
                        }
                    }
                }
            };

            /*wipe 处理方法*/
            function k(){clearTimeout(e);if(!i&&j){b.wipe()};i=false;j=false;};
            /*wipehold 处理方法*/
            function l(){i=true;b.wipehold()};

            function f(n){
                //if(n.touches.length==1){
                h = supportTouch ? n.touches[0].pageX : n.pageX;
                g = supportTouch ? n.touches[0].pageY : n.pageY;
                j=true;
                window.addEventListener(moveEvent,d,false);
                e=setTimeout(l,750)
                //}
            };

            //if("ontouchstart"in document.documentElement){
            this.addEventListener(startEvent,f,false);
            this.addEventListener(endEvent,k,false)
            //}
        });
        return this
    };
})(jQuery);