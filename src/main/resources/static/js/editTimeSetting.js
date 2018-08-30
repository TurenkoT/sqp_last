function sendComplexity(timeSettingId, timeSettingName, timeSettingDate) {
    $.ajax({
        type: 'POST',
        url: '/settings/time/settings/change/' + timeSettingId + '/save',
        data: JSON.stringify({
            id: timeSettingId,
            name: timeSettingName,
            date: timeSettingDate
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

function getTimeSettingDate() {
    var date = new Date($('.js-time-setting-input-date').val());
    return new Date(date.getTime() - date.getTimezoneOffset() * 60000);
}

function setFromDateForMonth() {
    var date = new Date();
    $('.js-time-setting-input-date[type=datetime-local]').val(new Date(new Date(new Date(date.getTime() - date.getTimezoneOffset() * 60000).getFullYear(), new Date(date.getTime() - date.getTimezoneOffset() * 60000).getMonth(), 1, 0, 0, 0, 0).getTime() - date.getTimezoneOffset() * 60000).toISOString().substring(0, 19));
}

function setFromDateForSixMonth() {
    var date = new Date();
    $('.js-time-setting-input-date[type=datetime-local]').val(new Date(new Date(new Date(date.getTime() - date.getTimezoneOffset() * 60000).getFullYear(), new Date(date.getTime() - date.getTimezoneOffset() * 60000).getMonth()-6, 1, 0, 0, 0, 0).getTime() - date.getTimezoneOffset() * 60000).toISOString().substring(0, 19));
}

function setFromDateForYear() {
    var date = new Date();
    $('.js-time-setting-input-date[type=datetime-local]').val(new Date(new Date(new Date(date.getTime() - date.getTimezoneOffset() * 60000).getFullYear(), 0, 1, 0, 0, 0, 0).getTime() - date.getTimezoneOffset() * 60000).toISOString().substring(0, 19));
}

$(document).ready(function () {
    var timeSettingId = [];
    var timeSettingName = [];
    var timeSettingDate = [];
    $(document).on('click', '.js-time-setting-edit', function () {
        timeSettingId = $('.js-time-setting-input-id').val();
        timeSettingName = $('.js-time-setting-input-name').val();
        timeSettingDate = getTimeSettingDate();
        sendComplexity(timeSettingId, timeSettingName, timeSettingDate);
    });

    $(document).on('click', '.js-time-settings-month', function () {
        $('.js-time-setting-input-name').val('Последний месяц');
        setFromDateForMonth();
    });

    $(document).on('click', '.js-time-settings-half-year', function () {
        $('.js-time-setting-input-name').val('Последние 6 месяцев');
        setFromDateForSixMonth();
    });

    $(document).on('click', '.js-time-settings-year', function () {
        $('.js-time-setting-input-name').val('Последний год');
        setFromDateForYear();
    });
});