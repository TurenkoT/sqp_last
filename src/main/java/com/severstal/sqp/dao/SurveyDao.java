package com.severstal.sqp.dao;

import java.util.List;

import com.severstal.sqp.entity.Survey;
import com.severstal.sqp.entity.SurveyLaunch;
import com.severstal.sqp.entity.SurveyLaunchResult;
import com.severstal.sqp.entity.SurveyQuestion;

public interface SurveyDao
{
	void createSurvey(Survey survey);

	void updateSurvey(Survey survey);

	void deleteSurvey(Survey survey);

	Survey findSurveyById(long surveyId);

	List<Survey> getAllSurveys();

	void createSurveyQuestion(SurveyQuestion question);

	void deleteQuestion(SurveyQuestion surveyQuestion);

	SurveyQuestion findSurveyQuestionById(long questionId);

	void createSurveyLaunch(SurveyLaunch surveyLaunch);

	List<SurveyLaunch> getAllSurveysLaunches();

	SurveyLaunch findLaunchById(long surveyLaunchId);

	void createSurveyLaunchResult(SurveyLaunchResult result);

	List<SurveyLaunch> findLaunchesBySurveyId(long surveyId);

    List<Survey> findSurveysByBusinessUnitId(Integer businessUnitId);
}
