// 使已经选取的标签不可再点击
$('.sub-nav li a').click(function () {
    if ($(this).parent().hasClass('active')) {
        return false
    }})