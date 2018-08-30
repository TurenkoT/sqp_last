function sendComplexity(complexityId, complexityName, complexityPoints, complexityPenalty) {
    $.ajax({
        type: 'POST',
        url: '/settings/complexity/change/' + complexityId + '/save',
        data: JSON.stringify({
            id: complexityId,
            name: complexityName,
            points: complexityPoints,
            penalty: complexityPenalty
        }),
        contentType: 'application/json',
        error: function (res) {
            console.log(res);
        },
        success: function (res) {
            window.location.replace('/adminPanel/settings');
        }
    });
}

$(document).ready(function () {
    var complexityId = [];
    var complexityName = [];
    var complexityPoints = [];
    var complexityPenalty = [];
    $(document).on('click', '.js-complexity-edit', function () {
        complexityId = $('.js-complexity-input-id').val();
        complexityName = $('.js-complexity-input-name').val();
        complexityPoints = $('.js-complexity-input-points').val();
        complexityPenalty = $('.js-complexity-input-penalty').val();
        sendComplexity(complexityId, complexityName, complexityPoints, complexityPenalty);
    })
});