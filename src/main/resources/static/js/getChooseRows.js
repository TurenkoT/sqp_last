function getIds() {
    return $(".js-check-question:checked").map(function () {
        return $(this).val();
    }).get();
}

function removeQuestions(ids) {
    $.ajax({
        type: 'POST',
        url: '/adminPanel/questions/deleteStatus?arr=' + ids,
        error: function (res) {
            console.log(res);
        },
        success: function (res) {
            console.log(res);
            $(".js-check-question:checked").closest('tr').remove();
            $('.js-delete-checked-querstions').prop('disabled', true);
        }
    });
}

function sendIdsQuestions(ids, complexityId) {
    $.ajax({
        type: 'POST',
        url: '/adminPanel/questions/set/complexity?arr=' + ids,
        data: JSON.stringify({
            questionsIds: ids,
            complexityId: complexityId
        }),
        contentType: 'application/json;',
        error: function (res) {
            console.log(res);
        },
        success: function (res) {
            console.log(res);
            window.location.replace('/adminPanel/questions/');
            $('.js-set-complexity-button').prop('disabled', true);
        }
    });
}

$(document).ready(function () {

    var ids = [];
    $(document).on('change', '.js-check-question', function () {
        if ($('.js-check-question:checked').length > 0) {
            // $('.js-delete-checked-querstions').prop('disabled', false);
            $('.js-set-complexity-button').prop('disabled', false);
        } else {
            // $('.js-delete-checked-querstions').prop('disabled', true);
            $('.js-set-complexity-button').prop('disabled', true);
        }
        ids = getIds();
    });
    $(document).on('click', '.js-delete-checked-querstions', function () {
        removeQuestions(ids);
    });

    $(document).on('click', '.js-delete-one-question', function () {
        var id = $(this).val();
        $('.js-check-question[value="' + id + '"]').prop('checked', true);
        removeQuestions(id);
    });

    $(document).on('click', '.js-set-complexity-button', function () {
        var complexityId = $('.js-select-complexity').val();
        sendIdsQuestions(ids, complexityId);
    })
});