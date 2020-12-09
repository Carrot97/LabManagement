$(document).ready(function() {
    var lis = $(".activity_ul").find("li");
    if (lis.length === 0) {
        $(".activity-notice").html("<br/><br/><br/><br/><br/>" +
            "无活动,快去举办一个吧!<br/>" +
            "点击集体活动/个人中心->发布活动<br/><br/>")
    }
})
