package com.severstal.sqp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SURVEY_QUESTION_ANSWER")
public class SurveyQuestionAnswer
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TEXT", length = 10000)
	private String text;

	@ManyToOne(targetEntity = SurveyQuestion.class, optional = false)
	@JoinColumn(name = "SURVEY_QUESTION_ID", referencedColumnName = "ID")
	private SurveyQuestion surveyQuestion;

	@ManyToOne(targetEntity = SurveyLaunchResult.class, optional = false)
	@JoinColumn(name = "SURVEY_LAUNCH_RESULT_ID", referencedColumnName = "ID")
	private SurveyLaunchResult surveyLaunchResult;

	public long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(final String text)
	{
		this.text = text;
	}

	public SurveyQuestion getSurveyQuestion()
	{
		return surveyQuestion;
	}

	public void setSurveyQuestion(final SurveyQuestion surveyQuestion)
	{
		this.surveyQuestion = surveyQuestion;
	}

	public SurveyLaunchResult getSurveyLaunchResult()
	{
		return surveyLaunchResult;
	}

	public void setSurveyLaunchResult(final SurveyLaunchResult surveyLaunchResult)
	{
		this.surveyLaunchResult = surveyLaunchResult;
	}
}
