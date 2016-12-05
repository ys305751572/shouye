window.onload = function(){
    //文字初始化
    var deviceWidth = document.documentElement.clientWidth;
    if (deviceWidth > 750) deviceWidth = 750;
    document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';


    $('.c-top-t').on('click',function(event) {
        event.preventDefault();
        window.location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.liumai.yiciyuan";
    });
    
    //计算背景宽度
    textIng();
    
}
function textIng(){
    var textIng = $('.use-ing span').html();
    $('.use-ing span').css({
        width: textIng
    });
}