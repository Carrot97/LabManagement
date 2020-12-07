$('.delete-btn').click(function () {
    $('#delete-form').attr('action', $(this).attr('uri')).submit()
    return false
})