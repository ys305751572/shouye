//点击筛选项
function createCho(name,index,type) {
    var tp = name; //获取点击对应值
    var cl = index; //标记
    var type = type; //获取本筛选项的类型
    //构造选中div
    var div = "<div class='o_choose " + type + "' data-type='" + type + "'>" + tp + "<span class='o_close " + cl + "' onclick='o_delete(this)' data-percent='" + cl + "'>x</span></div>"
    if ($('.' + cl).length == 1) {
        $('.options').prepend(div);
    }
}

//删除筛选项
function o_delete(obtn) {
    $(obtn).parent().remove();
}

//清楚所有筛选项
function removeAllClose() {
    $('.person').remove();
    $('.o_choose,.clearAll').remove();
    o_delete();
}