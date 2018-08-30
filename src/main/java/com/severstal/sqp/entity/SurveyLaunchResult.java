package com.severstal.sqp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SURVEY_LAUNCH_RESULT")
public class SurveyLaunchResult
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@ManyToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;

	@ManyToOne(targetEntity = SurveyLaunch.class, optional = false)
	@JoinColumn(name = "SURVEY_LAUNCH_ID", referencedColumnName = "ID")
	private SurveyLaunch surveyLaunch;

	@OneToMany(mappedBy = "surveyLaunchResult", cascade = CascadeType.ALL)
	private List<SurveyQuestionAnswer> surveyQuestionAnswers;

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(final Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(final User user)
	{
		this.user = user;
	}

	public SurveyLaunch getSurveyLaunch()
	{
		return surveyLaunch;
	}

	public void setSurveyLaunch(final SurveyLaunch surveyLaunch)
	{
		this.surveyLaunch = surveyLaunch;
	}

	public List<SurveyQuestionAnswer> getSurveyQuestionAnswers()
	{
		return surveyQuestionAnswers;
	}

	public void setSurveyQuestionAnswers(final List<SurveyQuestionAnswer> surveyQuestionAnswers)
	{
		this.surveyQuestionAnswers = surveyQuestionAnswers;
	}
}
