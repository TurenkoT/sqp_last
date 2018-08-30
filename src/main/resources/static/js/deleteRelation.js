function getRelationIds() {
    return $(".js-delete-relation").map(function () {
        return $(this).val();
    }).get();
}

function removeRelation(ids) {
    $.ajax({
        type: 'POST',
        url: '/adminPanel/users/relationsProfToSub/deleteStatus?arr=' + ids,
        error: function (res) {
            console.log(res);
        },
        success: function (res) {
            console.log(res);
        }
    });
}

$(document).ready(function () {
    $(document).on('click', '.js-delete-relation', function () {
        var id = $(this).val();
        $(this).closest('tr').remove();
        removeRelation(id);
    });
});