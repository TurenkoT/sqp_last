package com.severstal.sqp.dto;

import lombok.Data;

@Data
public class SurveyQuestionAnswerDto
{
	private Long id;
	private String text;
	private Long surveyQuestionId;
}
