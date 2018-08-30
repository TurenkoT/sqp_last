function getProfIds() {
    return $(".js-check-prof:checked").map(function () {
        return $(this).val();
    }).get();
}

function getSubIds() {
    return $(".js-check-subj:checked").map(function () {
        return $(this).val();
    }).get();
}

function saveRef(profIds, subjIds) {
    $.ajax({
        type: 'POST',
        url: '/adminPanel/users/relationsProfToSub/status',
        data: JSON.stringify({
            profsValues: profIds,
            subdivValues: subjIds
        }),
        contentType : 'application/json',
        error: function (res) {
            console.log(res);
        },
        success: function (res) {
            console.log('done');
            window.location.replace('/adminPanel/users/relationsProfToSub');
            console.log(res);
        },
        error: function() {
            $('.js-save-changes').prop('disabled', false);
        }
    });
}

$(document).ready(function () {
    var profIds = [];
    var subjIds = [];
    $(document).on('change', '.js-check-prof, .js-check-subj', function () {
        if ($('.js-check-prof:checked').length > 0 && $('.js-check-subj:checked').length > 0) {
            $('.js-save-changes').prop('disabled', false);
        } else {
            $('.js-save-changes').prop('disabled', true);
        }
        profIds = getProfIds();
        subjIds = getSubIds();
    });
    $(document).on('click', '.js-save-changes', function () {
        $('.js-save-changes').prop('disabled', true);
        saveRef(profIds, subjIds);
    });
    $(document).on('click', '.js-checked-all-prof', function () {
        if ($('.js-check-prof:checked').length == ($('.js-check-prof').length)) {
            $('.js-check-prof').prop('checked', false);
            $('.js-save-changes').prop('disabled', true);
        } else {
            $('.js-check-prof').prop('checked', true);
            profIds = getProfIds();
            if ($('.js-check-prof:checked').length > 0 && $('.js-check-subj:checked').length > 0) {

                $('.js-save-changes').prop('disabled', false);
            }
        }
    });

    $(document).on('click', '.js-checked-all-subj', function () {
        if ($('.js-check-subj:checked').length == ($('.js-check-subj').length)) {
            $('.js-check-subj').prop('checked', false);
            $('.js-save-changes').prop('disabled', true);
        } else {
            $('.js-check-subj').prop('checked', true);
            subjIds = getSubIds();
            if ($('.js-check-prof:checked').length > 0 && $('.js-check-subj:checked').length > 0) {
                $('.js-save-changes').prop('disabled', false);
            }
        }
    });

});