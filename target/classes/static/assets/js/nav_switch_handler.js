$(function () {
    $('.main-nav li:first').addClass('active')
})

$('.main-nav li a').click(function () {
    if ($(this).parent().hasClass('active')) {
        return false
    }
    $(".main-nav").find("li").each(function () {
        $(this).removeClass("active");
    });
    $(this).parent().addClass('active')
})

$('.navbar-right li a').click(function () {
    $(".main-nav").find("li").each(function () {
        $(this).removeClass("active");
    });
})