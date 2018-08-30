package com.severstal.sqp.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.severstal.sqp.entity.SurveyLaunch;

import lombok.Data;

@Data
public class SurveyLaunchDto
{
	private Long          id;
	private Long          surveyId;
	private Date          createdDate;
	private List<Integer> professionIds;
	private List<Integer> subdivisionIds;
	private List<String> professionNames;
	private List<String> subdivisionNames;

	public SurveyLaunchDto() {}

	public SurveyLaunchDto(SurveyLaunch entity)
	{
		setId(entity.getId());
		setSurveyId(entity.getSurvey().getId());
		setCreatedDate(entity.getCreatedDate());

		// TODO: this part should be correctly rewritten
		setProfessionIds(entity.getProfessions().stream().map(e -> e.getId()).collect(Collectors.toList()));
		setSubdivisionIds(entity.getSubdivisions().stream().map(e -> e.getId()).collect(Collectors.toList()));
		setProfessionNames(entity.getProfessions().stream().map(e -> e.getName()).collect(Collectors.toList()));
		setSubdivisionNames(entity.getSubdivisions().stream().map(e -> e.getName()).collect(Collectors.toList()));
	}
}
