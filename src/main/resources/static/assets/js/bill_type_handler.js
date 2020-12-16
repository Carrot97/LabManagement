$('#incomeType').click(function () {
    $('.sourceDiv').fadeIn();
    $('.agentDiv').fadeOut();
    $('.reasonDiv').fadeOut();
})
$('#expenseType').click(function () {
    $('.sourceDiv').fadeOut();
    $('.agentDiv').fadeIn();
    $('.reasonDiv').fadeIn();
})
