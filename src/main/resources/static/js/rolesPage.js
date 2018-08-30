function getUserIds() {
    return $(".js-choose-user:checked").map(function () {
        return $(this).val();
    }).get();
}

function setRoles(ids,role){
    $.ajax({
        type: 'POST',
        url: '/adminPanel/roles/save',
        data: JSON.stringify({
            ids: ids,
            role: role
        }),
        contentType : 'application/json',
        error: function (res) {
            console.log(res);
            window.location.replace('/adminPanel/roles');
        },
        success: function (res) {
            console.log(res);
            window.location.replace('/adminPanel/roles');
        }
    });
}

$(document).ready(function () {
    var timeout = null;
    var rolesTbodyContent = $(".js-roles-table tbody").html();
    var role = $('.js-chosen-role').prop('selected', true).val();

    $(".js-search-by-personal-number").on("keyup paste", function () {
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            var value = $(".js-search-by-personal-number").val().toUpperCase();
            var $rows = $(".js-roles-table tr").slice(1);
            if (value === '') {
                $(".js-roles-table tbody").html(rolesTbodyContent);
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

    $(document).on('change', '.js-choose-user', function () {
        if ($('.js-choose-user:checked').length > 0) {
            $('.js-save-roles').prop('disabled', false);
        } else {
            $('.js-save-roles').prop('disabled', true);
        }
        ids = getUserIds();
    });

    $(document).on('click', '.js-save-roles', function () {
        role = $('#js-chosen-role option:selected').val();
        setRoles(ids, role);
    })

});