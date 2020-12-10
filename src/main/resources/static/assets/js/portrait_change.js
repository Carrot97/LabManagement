$(".portrait").mouseenter(function () {
    var changeObj = $(".portrait-change")
    $(changeObj).css("display", "float")
    $(changeObj).fadeIn(300)
})

$(".portrait-change").mouseout(function () {
    $(this).fadeOut(300)
})

$(".portrait-change").click(function () {
    $("#portrait-change-form").submit()
    return false
})