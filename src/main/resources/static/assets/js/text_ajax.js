$('#input-box').change(function () {
    var str = $(this).val();
    $.post({
        url: 'http://222.28.68.186:4321/work/transform',
        data: {
            text: str,
        },
        success: function (response) {
            $('#output-box').text(response)
        }
    })
})