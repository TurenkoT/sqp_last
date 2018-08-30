package com.severstal.sqp.dto;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.severstal.sqp.entity.Survey;
import com.severstal.sqp.entity.SurveyLaunch;

import lombok.Data;

@Data
public class SurveyDto
{
	private Long                    id;
	private String                  name;
	private String                  authorName;
	private Date                    createdDate;
	private Date                    lastLaunchDate;
	private List<SurveyQuestionDto> questions;

	public SurveyDto() {}

	public SurveyDto(Survey entity)
	{
		this.setId(entity.getId());
		this.setName(entity.getName());
		this.setAuthorName(entity.getAuthor().getFullName());
		this.setCreatedDate(entity.getCreatedDate());

		final Optional<SurveyLaunch> lastSurveyLaunch = entity.getSurveyLaunches()
		                                           .stream()
		                                           .sorted((e1, e2) -> e2.getCreatedDate().compareTo(e1.getCreatedDate()))
		                                           .findFirst();
		lastSurveyLaunch.ifPresent(l -> this.setLastLaunchDate(l.getCreatedDate()));

		this.setQuestions(entity.getQuestions().stream().map((e) -> new SurveyQuestionDto(e)).collect(Collectors.toList()));
	}

}
