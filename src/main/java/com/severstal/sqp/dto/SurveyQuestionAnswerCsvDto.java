package com.severstal.sqp.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import com.severstal.sqp.entity.SurveyQuestionAnswer;

import lombok.Data;

@Data
public class SurveyQuestionAnswerCsvDto
{
	private final static DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	private String userName;
	private String subdivisionName;
	private String professionNames;
	private String  answerDate;
	private String questionText;
	private String answerText;
	private String organisationName;

	public SurveyQuestionAnswerCsvDto() {}

	public SurveyQuestionAnswerCsvDto(SurveyQuestionAnswer entity)
	{
		setUserName(entity.getSurveyLaunchResult().getUser().getFullName());
		setSubdivisionName(entity.getSurveyLaunchResult().getUser().getSubdivision().getName());
		setProfessionNames(entity.getSurveyLaunchResult().getUser().getProfessions().stream().map(p -> p.getName()).collect(Collectors.joining(",")));
		setAnswerDate(df.format(entity.getSurveyLaunchResult().getCreatedDate()));
		setQuestionText(entity.getSurveyQuestion().getText());
		setAnswerText(entity.getText());
		setOrganisationName(entity.getSurveyLaunchResult().getUser().getSubdivision().getOrganisation().getName());
	}
}
