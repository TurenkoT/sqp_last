function getSubjectId() {
    return $(".js-questions-table tr").map(function () {
        return $(this).data('value');
    }).get();
}

$(document).ready(function () {
    var timeout = null;
    var questionsTbodyContent = $(".js-questions-table tbody").html();

    $(".js-search-question").on("keyup paste", function () {
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            var value = $(".js-search-question").val().toUpperCase();
            var $rows = $(".js-questions-table tr").slice(1);
            if (value === '') {
                $(".js-questions-table tbody").html(questionsTbodyContent);
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

    $('.js-select-subjects').on("change", function () {
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            var value = $(".js-select-subjects").val().toUpperCase();
            var $rows = $(".js-questions-table tr");
            if (value === '') {
                $(".js-questions-table tbody").html(questionsTbodyContent);
                return;
            }

            $rows.each(function () {
               $row=$(this);
               if($(this).data('value')==value){
                   $row.show();
               } else {
                   $row.hide();
               }
            })
        }, 500);
    })
});