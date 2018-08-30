function sendComplexity(complexityId){
    $.ajax({
        type: 'POST',
        url: '/savecomplexity',
        data: JSON.stringify({
            complexityId: complexityId
        }),
        contentType : 'application/json',
        error: function (res) {
            console.log(res);
        },
        success: function (res) {
         window.location.replace('/question');
        }
    });
}

$(document).ready(function () {
    var complexityId = [];
    $(document).on('click', '.js-go-to-complexity-test-btn', function () {
        complexityId = $(this).val();
        sendComplexity(complexityId);
    })    
});