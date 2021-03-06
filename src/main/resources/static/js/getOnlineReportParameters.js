function setUntilDate() {
    var date = new Date();
    $('.js-to-date[type=datetime-local]').val(new Date(date.getTime() - date.getTimezoneOffset() * 60000).toISOString().substring(0, 19));
}

function setFromDateForYear() {
    var date = new Date($('.js-to-date').val());
    $('.js-from-date[type=datetime-local]').val(new Date(new Date(new Date(date.getTime() - date.getTimezoneOffset() * 60000).getFullYear(), 0, 1, 0, 0, 0, 0).getTime() - date.getTimezoneOffset() * 60000).toISOString().substring(0, 19));
}

function setFromDateForMonth() {
    var date = new Date($('.js-to-date').val());
    $('.js-from-date[type=datetime-local]').val(new Date(new Date(new Date(date.getTime() - date.getTimezoneOffset() * 60000).getFullYear(), new Date(date.getTime() - date.getTimezoneOffset() * 60000).getMonth(), 1, 0, 0, 0, 0).getTime() - date.getTimezoneOffset() * 60000).toISOString().substring(0, 19));
}

function setFromDateForDay() {
    var date = new Date($('.js-to-date').val());
    $('.js-from-date[type=datetime-local]').val(new Date(new Date(new Date(date.getTime() - date.getTimezoneOffset() * 60000).getFullYear(), new Date(date.getTime() - date.getTimezoneOffset() * 60000).getMonth(), new Date(date.getTime() - date.getTimezoneOffset() * 60000).getDate(), 0, 0, 0, 0).getTime() - date.getTimezoneOffset() * 60000).toISOString().substring(0, 19));
}

function setFromDate(days) {
    var fromDate = new Date(new Date($('.js-to-date').val()).getTime() - (days * 3600 * 1000));
    $('.js-from-date[type=datetime-local]').val(new Date(fromDate.getTime() - fromDate.getTimezoneOffset() * 60000).toISOString().substring(0, 19));
}

function getStartDate() {
    var date = new Date($('.js-from-date').val());
    return new Date(date.getTime() - date.getTimezoneOffset() * 60000).toISOString().split('.')[0].replace("T", " ");
}

function getEndDate() {
    var date = new Date($('.js-to-date').val());
    return new Date(date.getTime() - date.getTimezoneOffset() * 60000).toISOString().split('.')[0].replace("T", " ");
}

function getStartMonth(monthNumber) {
    var date = new Date();
    return new Date(new Date(new Date(date.getTime() - date.getTimezoneOffset() * 60000).getFullYear(), monthNumber, 1, 0, 0, 0, 0).getTime() - date.getTimezoneOffset() * 60000).toISOString().substring(0, 19);
}

function getLastDay(monthNumber) {
    var d = new Date((new Date()).getFullYear(), +monthNumber + 1, 1);
    d = new Date(d.setDate(d.getDate() - 1));
    return d.getDate();
}

function getEndMonth(monthNumber, lastDay) {
    var date = new Date();
    return new Date(new Date(new Date(date.getTime() - date.getTimezoneOffset() * 60000).getFullYear(), monthNumber, lastDay, 23, 59, 59, 0).getTime() - date.getTimezoneOffset() * 60000).toISOString().substring(0, 19);
}

function getHours() {
    var select = document.getElementById("hours");
    var value = select.value;
    setFromDate(value);
}

function saveValues(selectedSubdivisions, startDate, endDate) {
    $.ajax({
        type: 'POST',
        url: '/report/download',
        data: JSON.stringify({
            subdivs: selectedSubdivisions,
            startDate: startDate,
            endDate: endDate
        }),
        processData: false,
        contentType: 'application/json;',
        error: function (res) {
            console.log(res);
        },
        success: function (csvData) {
            saveAs(new Blob([csvData], {type: 'text/csv;charset=windows-1251'}), "report.csv")
        }
    });
}

function saveValuesOnlineReport(selectedSubdivisions, startDate, endDate) {
    $.ajax({
        type: 'POST',
        url: '/onlineReports/employees',
        data: JSON.stringify({
            subdivs: selectedSubdivisions,
            startDate: startDate,
            endDate: endDate
        }),
        processData: false,
        contentType: 'application/json;',
        success: funcShowTable
    });
}

function funcShowTable(data) {

    $('.trow').hide();
    $('#spinner').hide();
    $('.js-get-online-report').prop("disabled", false);

    if (data.length === 0) {
        alert("???? ?????????????????? ???????????????????? ?????? ???????????????????? ??????????????");
    }

    var passed = "????";
    var failed = "??????";
    var wrong  = "???????????????? ????????????";

    $.each(data, function (i, item) {
        var employeeRow =
            '<tr class="trow">' +
                '<td>' + item.organisationName + '</td>' +
                '<td>' + item.subdivisionName + '</td>' +
                '<td>' + item.fullName + '</td>' +
                '<td>' + item.personalNumber + '</td>' +
                '<td>' + item.stringDate + '</td>' +
                '<td>' + item.totalCountOfQuestions + '</td>' +
                '<td>' + item.passed + '</td>' +
            '</tr>';
        $('#tableEmployees tbody').append(employeeRow);
        $('td').each(function () {
            var x = $(this).text();
            if (x == failed) $(this).css({background: '#fcb0b0'});
            if (x == passed) $(this).css({background: '#9bf7b2'});
            if (x == wrong) $(this).css({background: '#f6f785'});
        });
    });
    $('#tableEmployees tbody').addClass("text-center");
}

function getSelectedSubdivisionsIds() {
    return $('.js-subdivision-checkbox:checked').map(function () {
        return $(this).val();
    }).get();
}

function setDefaultDates() {
    $('.js-from-date').prop('disabled', false);
    $('.js-to-date').prop('disabled', false);
    setUntilDate();
    setFromDate(1);
}

function getFullBeReport(startMonth, endMonth){
    $.ajax({
        type: 'POST',
        url: '/report/download/full_be',
        data: JSON.stringify({
            startMonth: startMonth,
            endMonth: endMonth
        }),
        processData: false,
        contentType: 'application/json;',
        error: function (res) {
            console.log(res);
        },
        success: function (csvData) {
            saveAs(new Blob([csvData], {type: 'text/csv;charset=windows-1251'}), "report.csv")
        }
    });
}

$(document).ready(function () {
    var selectedSubdivisions = [];
    var startDate;
    var endDate;
    var startMonth;
    var endMonth;

    $(document).on('click', '.js-date-day', function () {
        setFromDateForDay();
    });
    $(document).on('click', '.js-date-month', function () {
        setFromDateForMonth();
    });
    $(document).on('click', '.js-date-year', function () {
        setFromDateForYear();
    });

    $(document).on('click', '.js-get-report', function () {
        selectedSubdivisions = getSelectedSubdivisionsIds();
        startDate = getStartDate();
        endDate = getEndDate();
        saveValues(selectedSubdivisions, startDate, endDate);
    });

    $(document).on('click', '.js-get-online-report', function () {
        $('#spinner').show();
        $('.js-get-online-report').prop("disabled", true);
        selectedSubdivisions = getSelectedSubdivisionsIds();
        startDate = getStartDate();
        endDate = getEndDate();
        saveValuesOnlineReport(selectedSubdivisions, startDate, endDate);
    });

    $(document).on('click','.js-get-full-be-report', function () {
        getFullBeReport(startMonth, endMonth);
    });

    $(document).on('click', '.js-all-business-unit', function () {
        if ($('.js-organisation-checkbox:checked').length > 0) {
            $('.js-organisation-checkbox:checked').trigger('click');
            $('.js-get-report').prop('disabled', true);
            $('.js-hour-from-select').prop('disabled', true);
            $('.js-get-online-report').prop('disabled', true);
        } else {
            $('.js-organisation-checkbox').trigger('click');
            if ($('.js-organisation-checkbox:checked').length == 0 && $('.js-subdivision-checkbox:checked').length == 0) {
                $('.js-get-report').prop('disabled', false);
                $('.js-hour-from-select').prop('disabled', false);
                $('.js-get-online-report').prop('disabled', false);
            }
        }
    });


    $(document).on('change', '.js-month-from-select, .js-month-until-select', function () {
        var fromMonth = parseInt($('.js-month-from-select option:selected').val());
        var untilMonth = parseInt($('.js-month-until-select option:selected').val());
        if (fromMonth <= untilMonth) {
            startMonth = getStartMonth(fromMonth);
            endMonth = getEndMonth(untilMonth, getLastDay(untilMonth));
            $('.js-get-full-be-report').prop('disabled', false);
            $('.js-get-subdivision-report').prop('disabled', false);
            $('.js-get-profession-report').prop('disabled', false);
            // $('.js-get-employee-report').prop('disabled', false);
            // $('.js-get-subject-report').prop('disabled', false);
        } else {
            $('.js-get-full-be-report').prop('disabled', true);
            $('.js-get-subdivision-report').prop('disabled', true);
            $('.js-get-profession-report').prop('disabled', true);
            // $('.js-get-employee-report').prop('disabled', true);
            // $('.js-get-subject-report').prop('disabled', true);
        }
    });

    $('.js-organisation-checkbox').each(function () {
        var organisationCheckbox = $(this);
        $(organisationCheckbox).on('click', function () {
            var checked = organisationCheckbox.prop('checked');
            var organisationId = organisationCheckbox.attr('data-organisation-id');
            var checkboxes = $('.js-subdivisions-box[data-organisation-id=' + organisationId + '] ' + 'input[type=checkbox]');
            var rows = $('.js-subdivisions-box li input');

            checkboxes.each(function () {
                var subdivisionCheckbox = $(this);
                if (checked) {
                    subdivisionCheckbox.prop('disabled', false);
                    //subdivisionCheckbox.prop('checked', true);
                    subdivisionCheckbox.next().removeClass('checkbox-label-disabled')
                } else {
                    subdivisionCheckbox.prop('checked', false);
                    subdivisionCheckbox.prop('disabled', true);
                    subdivisionCheckbox.next().addClass('checkbox-label-disabled');
                }
                if ($('.js-subdivision-checkbox:disabled').length > 0) {
                    rows.each(function () {
                        $row = $(this).parent('li').parent('div');
                        if (!($(this).prop('disabled'))) {
                            $row.show()
                        } else {
                            $row.hide()
                        }
                    })
                } else {
                    rows.each(function () {
                        $row = $(this).parent('li').parent('div');
                        $row.show()
                    })
                }
            });

            checkboxes.each(function () {
                var subdivisionCheckbox = $(this);
                $(subdivisionCheckbox).on('click', function () {
                    var checked = subdivisionCheckbox.prop('checked');
                    if (checked) {
                        $('.js-get-online-report').prop('disabled', false);
                    } else {
                        if ($('.js-subdivision-checkbox:checked').length === 0) {
                            $('.js-get-online-report').prop('disabled', true);
                        }
                    }
                })
            });

            if (checked) {
                $('.js-from-date').prop('disabled', false);
                $('.js-to-date').prop('disabled', false);
                setDefaultDates();
                startDate = getStartDate();
                endDate = getEndDate();
                $('.js-date-day').prop('disabled', false);
                //hidden while  functions is not developed
                // $('.js-date-month').prop('disabled', false);
                // $('.js-date-year').prop('disabled', false);
                $('.js-get-report').prop('disabled', false);
                $('.js-hour-from-select').prop('disabled', false);
            } else {
                if ($('.js-organisation-checkbox:checked').length == 0) {
                    $('.js-get-report').prop('disabled', true);
                    $('.js-get-online-report').prop('disabled', true);
                    $('.js-hour-from-select').prop('disabled', true);
                    $('.js-date-day').prop('disabled', true);
                    //hidden while  functions is not developed
                    // $('.js-date-month').prop('disabled', true);
                    // $('.js-date-year').prop('disabled', true);
                    $('.js-from-date').val('').prop('disabled', true);
                    $('.js-to-date').val('').prop('disabled', true);
                }
            }
        });
    });

    $(document).on('click', '.js-all-subdivisions', function () {
        if ($('.js-subdivision-checkbox:checked').length === 0) {
            $('.js-subdivision-checkbox:visible').prop('checked', true);
            $('.js-get-online-report').prop('disabled', false);
        } else {
            $('.js-subdivision-checkbox').prop('checked', false);
            $('.js-get-online-report').prop('disabled', true);
        }

    });

});