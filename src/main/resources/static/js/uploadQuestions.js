function showAnimation() {
    document.getElementById("spinner").style.display = "block";
}

$(document).ready(function () {
    $('.questions-upload-button').on('click', function () {
        $('.questions-upload-button').prop('hidden', true);
        showAnimation();
    })
});