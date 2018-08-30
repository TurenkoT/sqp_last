$(document).ready(function () {
    var timeout = null;
    var surveysTbodyContent = $(".js-survey-table tbody").html();

    $(".js-search-surveys").on("keyup paste", function () {
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            var value = $(".js-search-surveys").val().toUpperCase();
            var $rows = $(".js-survey-table tr").slice(1);
            if (value === '') {
                $(".js-survey-table tbody").html(surveysTbodyContent);
                return;
            }

            $rows.each(function (index) {
                $row = $(this);
                var rowText = $row.text().toUpperCase();
                if (rowText.indexOf(value) > -1) {
                    $row.show();
                } else {
                    $row.hide();
                }
            });
        }, 500);
    });
});