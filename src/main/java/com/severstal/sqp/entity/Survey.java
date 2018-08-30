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
@Table(name = "SURVEY")
public class Survey
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@ManyToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
	private User author;

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
	List<SurveyQuestion> questions;

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
	List<SurveyLaunch> surveyLaunches;

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
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

	public List<SurveyQuestion> getQuestions()
	{
		return questions;
	}

	public void setQuestions(final List<SurveyQuestion> questions)
	{
		this.questions = questions;
	}

	public List<SurveyLaunch> getSurveyLaunches()
	{
		return surveyLaunches;
	}

	public void setSurveyLaunches(final List<SurveyLaunch> surveyLaunches)
	{
		this.surveyLaunches = surveyLaunches;
	}
}
