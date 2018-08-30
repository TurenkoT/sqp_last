package com.severstal.sqp.dto;

import com.severstal.sqp.entity.SurveyQuestion;

import lombok.Data;

@Data
public class SurveyQuestionDto
{
	private Long id;
	private String text;
	private Long surveyId;

	public SurveyQuestionDto(SurveyQuestion entity)
	{
		this.setId(entity.getId());
		this.setText(entity.getText());
		this.setSurveyId(entity.getSurvey().getId());
	}

	public SurveyQuestionDto() {};
}
