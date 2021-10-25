//获取屏幕宽度
let htmlWidth = document.documentElement.clientWidth || document.body.clientWidth;
//获取html的dom
let htmlDom = document.getElementsByTagName("html")[0];
//设置屏幕适应的fontsize
htmlDom.style.fontSize = htmlWidth / 10 + "px";

window.addEventListener('resize', function (e) {
    let htmlWidth = document.documentElement.clientWidth || document.body.clientWidth;
    htmlDom.style.fontSize = htmlWidth / 10 + "px";
});
