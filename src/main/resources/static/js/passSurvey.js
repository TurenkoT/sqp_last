$(document).ready(function() {

	$('.js-save-btn').on('click', function() {

			var surveyLaunchId = $('#survey-launch-id').val();
			var url = '/profile/surveys/pass/' + surveyLaunchId + '/save';

			var answers = getAnswers();

			$.ajax({
			  type: 'POST',
			  url: url,
			  data: JSON.stringify(answers),
			  contentType : 'application/json',
			  success: function(data) {
			    window.location.replace("/profile/surveys");
			  }
			});
	});

	function getAnswers() {
	  var result = [];

	  $('.js-answer-textarea').each(function() {

	    var questionId = $(this).attr('data-question-id');
	    var text = $(this).val();

	    result.push({
	      'surveyQuestionId' : questionId,
	      'text' : text
	    })
	  });

	  return result;
	}

});