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
@Table(name = "SURVEY_QUESTION")
public class SurveyQuestion
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TEXT", length = 10000)
	private String text;

	@ManyToOne(targetEntity = Survey.class, optional = false)
	@JoinColumn(name = "SURVEY_ID", referencedColumnName = "ID")
	private Survey survey;

	@ManyToOne(targetEntity = User.class, optional = false)
	@JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
	private User author;

	public Long getId()
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

	public Survey getSurvey()
	{
		return survey;
	}

	public void setSurvey(final Survey survey)
	{
		this.survey = survey;
	}

	public User getAuthor()
	{
		return author;
	}

	public void setAuthor(final User author)
	{
		this.author = author;
	}
}
