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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SURVEY_LAUNCH")
public class SurveyLaunch
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@ManyToOne(targetEntity = Survey.class, optional = false)
	@JoinColumn(name = "SURVEY_ID", referencedColumnName = "ID")
	private Survey survey;

	@ManyToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
	private User author;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "SURVEY_LAUNCH_TO_SUBDIVISION", joinColumns = {@JoinColumn(name = "SURVEY_LAUNCH_ID")}, inverseJoinColumns
					= {
					@JoinColumn(name = "SUBDIVISION_ID")
	})
	private List<Subdivision> subdivisions;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "SURVEY_LAUNCH_TO_PROFESSION", joinColumns = {@JoinColumn(name = "SURVEY_LAUNCH_ID")}, inverseJoinColumns
					= {
					@JoinColumn(name = "PROFESSION_ID")
	})
	private List<Profession> professions;

	@OneToMany(mappedBy = "surveyLaunch", cascade = CascadeType.ALL)
	private List<SurveyLaunchResult> surveyLaunchResults;

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

	public User getAuthor()
	{
		return author;
	}

	public void setAuthor(final User author)
	{
		this.author = author;
	}

	public List<Subdivision> getSubdivisions()
	{
		return subdivisions;
	}

	public void setSubdivisions(final List<Subdivision> subdivisions)
	{
		this.subdivisions = subdivisions;
	}

	public List<Profession> getProfessions()
	{
		return professions;
	}

	public void setProfessions(final List<Profession> professions)
	{
		this.professions = professions;
	}


	public Survey getSurvey()
	{
		return survey;
	}

	public void setSurvey(final Survey survey)
	{
		this.survey = survey;
	}

	public List<SurveyLaunchResult> getSurveyLaunchResults()
	{
		return surveyLaunchResults;
	}

	public void setSurveyLaunchResults(final List<SurveyLaunchResult> surveyLaunchResults)
	{
		this.surveyLaunchResults = surveyLaunchResults;
	}
}
