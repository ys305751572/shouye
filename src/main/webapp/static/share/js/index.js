window.onload = function(){
    //文字初始化
    var deviceWidth = document.documentElement.clientWidth;
    if (deviceWidth > 750) deviceWidth = 750;
    document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';

    
    //计算背景宽度
    textIng();
    //结识对方
    $("#jsdf").on('click', function(event) {
        toDownLoadPage();
    });
    //电话联系
    $("#iphone-call").on('click', function(event) {
        toDownLoadPage();
    });

    $("#download").on('click', function(event) {
        toDownLoadPage();
    });


    //分页
    $(".c-top-t").on('click',  function(e) {
        var index = $(this).index();

        if ( index == '0') {

            $(this).addClass('c-top-t-active');
            $(this).siblings('.c-top-t').removeClass('c-top-t-active');
            $('#c-contain-one').siblings('.c-contain').addClass('dis-none');
            $('#c-contain-one').removeClass('dis-none');
            

        }else if(index == '1'){

            $(this).addClass('c-top-t-active');
            $(this).siblings('.c-top-t').removeClass('c-top-t-active');
            $('#c-contain-two').siblings('.c-contain').addClass('dis-none');
            $('#c-contain-two').removeClass('dis-none');
            
        }else if(index == '2'){

            $(this).addClass('c-top-t-active');
            $(this).siblings('.c-top-t').removeClass('c-top-t-active');
            $('#c-contain-three').siblings('.c-contain').addClass('dis-none');
            $('#c-contain-three').removeClass('dis-none');
            
        }else if(index == '3'){

            $(this).addClass('c-top-t-active');
            $(this).siblings('.c-top-t').removeClass('c-top-t-active');
            $('#c-contain-four').siblings('.c-contain').addClass('dis-none');
            $('#c-contain-four').removeClass('dis-none');    

        }else if(index == '4'){

            $(this).addClass('c-top-t-active');
            $(this).siblings('.c-top-t').removeClass('c-top-t-active');
            $('#c-contain-five').siblings('.c-contain').addClass('dis-none');
            $('#c-contain-five').removeClass('dis-none');
            
        }
    });
}
function toDownLoadPage () {
    window.location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.leoman.shouye";
}

function textIng(){
    var textIng = $('.use-ing span').html();
    $('.use-ing span').css({
        width: textIng
    });
}