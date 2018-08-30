$(document).ready(function() {
	$('.js-organisation-checkbox').each(function () {
		var organisationCheckbox = $(this);

		$(organisationCheckbox).on('click', function() {
			var checked = organisationCheckbox.prop('checked');
			var organisationId = organisationCheckbox.attr('data-organisation-id');
			var checkboxes = $('.js-subdivisions-box[data-organisation-id=' + organisationId + '] ' + 'input[type=checkbox]');
			checkboxes.each(function() {
				var subdivisionCheckbox = $(this);
				if (checked) {
					subdivisionCheckbox.prop('disabled', false);
					subdivisionCheckbox.prop('checked', true);
					subdivisionCheckbox.next().removeClass('checkbox-label-disabled')
				} else {
					subdivisionCheckbox.prop('checked', false);
					subdivisionCheckbox.prop('disabled', true);
					subdivisionCheckbox.next().addClass('checkbox-label-disabled');
				}

			});

		});

	});

	$('.js-save-btn').on('click', function() {
		var surveyId = $('#survey-id').val();
		var pids = getSelectedProfessionsIds();
		var sids = getSelectedSubdivisionsIds();

		$.ajax({
			type: 'POST',
			url: '/adminPanel/surveys/launch/save',
			data: JSON.stringify({
				surveyId : surveyId,
				professionIds : pids,
				subdivisionIds : sids,
			}),
			contentType : 'application/json',
			success: function(data) {
				window.location.replace("/adminPanel/surveys");
			}
    });
	});

	function getSelectedSubdivisionsIds() {
		return $('.js-subdivision-checkbox:checked').map(function() {
			return $(this).val();
		}).get();
	}

	function getSelectedProfessionsIds() {
		return $('.js-profession-checkbox:checked').map(function() {
			return $(this).val();
		}).get();
	}

});