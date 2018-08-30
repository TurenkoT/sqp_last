function getAdditionalAnswers() {
    return $(".js-answer-value").map(function () {
        return $(this).val();
    }).get();
}

function getCorrectnessAnswers() {
    return $(".js-answer-checkbox").map(function () {
        return $(this).prop('checked');
    }).get();
}

function sendNewData(questionText, subjectId, complexityId, typeQuestionId, additionalAnswers, correctnessAnswers){
    var url = '/adminPanel/questions/add/save';

    $.ajax({
        type: "POST",
        url: url,
        data:
            JSON.stringify({
                questionText: questionText,
                subjectId: subjectId,
                complexityId: complexityId,
                typeQuestionId: typeQuestionId,
                additionalAnswers: additionalAnswers,
                correctnessAnswers: correctnessAnswers
            }),
        contentType: 'application/json',
        success:
            function () {
                window.location.replace('/adminPanel/questions');
            },
        error: function (res) {
            console.log(res);
        }
    });
}

$(document).ready(function () {
    var counter = $('.js-answers').length;
    var limit = 6;
    var minlimit = 2;


    $(document).on('click', '.js-add-one-more-answer', function () {
        if (counter == limit) {
            alert("Лимит возможных вариантов ответа " + limit);
        } else {
            var $clone = $('.js-answers').eq(counter - 1).clone();
            $clone.find('.js-answer-value').val('');
            $clone.find('.js-answer-checkbox').prop('checked', false);
            $('.js-additional-answers-wrapper').append($clone);
            counter++;
        }
    });

    $(document).on('click', '.js-remove-one-answer', function () {
        if (counter == minlimit) {
            alert("количество ответов не может быть меньше " + minlimit);
        } else {
            $(this).parent('div').parent('div').remove();
            counter--;
        }
    });

    $(document).on('click', '.js-question-add', function () {
        var questionText = $('.js-text-question-input').val();
        var subjectId = $('.js-select-subjects').val();
        var complexityId = $('.js-select-complexity').val();
        var typeQuestionId = $('.js-select-type-question').val();
        var additionalAnswers = getAdditionalAnswers();
        var correctnessAnswers = getCorrectnessAnswers();

        sendNewData(questionText, subjectId, complexityId, typeQuestionId, additionalAnswers, correctnessAnswers);
    });

    $(document).on('change', '.js-text-question-input', function () {
        if ($('.js-text-question-input').val() == '') {
            $('.js-question-add').prop('disabled', true);
        } else {
            $('.js-question-add').prop('disabled', false);
        }
    });
});