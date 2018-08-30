function showAnimation() {
    document.getElementById("spinner").style.display = "block";
}

$(document).ready(function () {
    $('.users-upload-button').on('click', function () {
        $('.users-upload-button').prop('hidden', true);
        showAnimation();
    })
});