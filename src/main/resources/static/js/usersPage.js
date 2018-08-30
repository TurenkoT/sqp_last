$(document).ready(function () {
    var timeout = null;
    var usersTbodyContent = $(".js-users-table tbody").html();

    $(".js-search-users").on("keyup paste", function () {
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            var value = $(".js-search-users").val().toUpperCase();
            var $rows = $(".js-users-table tr").slice(1);
            if (value === '') {
                $(".js-users-table tbody").html(usersTbodyContent);
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