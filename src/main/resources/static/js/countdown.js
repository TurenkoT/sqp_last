(function () {
    $(document).ready(function () {
        var $countdown = $('#countdown span'),
            timer,
            startFrom = 60;
            yellowHighLimit = startFrom  * 0.75;
            yellowBottomLimit = startFrom * 0.31;
            redLimit = startFrom * 0.3;

        $countdown.text(startFrom).parent('p').show();

        timer = setInterval(function () {
            if (startFrom < yellowHighLimit && startFrom > yellowBottomLimit) {
                $('.js-chart').css({
                    'border': '10px solid #e5ca1b',
                    'box-shadow': '0 0 0 0.2rem rgba(236, 151, 31, 0.28)',
                    'background-color' :'#f5efb385'
                });
            }
            if (startFrom <= redLimit) {
                $('.js-chart').css({
                    'border': '10px solid #f44336',
                    'box-shadow': 'box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.29)',
                    'background-color' :'#f5bbb385'
                });
            }
            $countdown.text(--startFrom);
            if (startFrom <= 0) {
                clearInterval(timer);

                $.ajax({
                  type: 'POST',
                  url: '/question/timeIsUp',
                  success: function(data) {
                    window.location.replace("/question/timeIsUp");
                  }
                });
            }
        }, 1000);
    });
})();