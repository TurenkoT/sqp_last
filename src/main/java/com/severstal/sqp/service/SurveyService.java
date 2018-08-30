package com.severstal.sqp.service;

import java.util.Arrays;
import java.util.List;

import com.severstal.sqp.entity.Survey;
import com.severstal.sqp.entity.SurveyLaunch;
import com.severstal.sqp.entity.SurveyLaunchResult;
import com.severstal.sqp.entity.SurveyQuestion;
import com.severstal.sqp.entity.User;

public interface SurveyService
{
	void createSurvey(Survey survey);

	void updateSurvey(Survey survey);

	void deleteSurvey(long surveyId);

	Survey findSurveyById(long surveyId);

	List<Survey> getAllSurveys();

	void createSurveyQuestion(SurveyQuestion question);

	void deleteSurveyQuestion(long questionId);

	void createSurveyLaunch(SurveyLaunch surveyLaunch);

	List<SurveyLaunch> getAllSurveysLaunches();

	List<SurveyLaunch> getAllActiveSurveysLaunchesForUser(User user);

	SurveyLaunch findLaunchById(long surveyLaunchId);

	SurveyQuestion findSurveyQuestionById(Long questionId);

	void createSurveyLaunchResult(SurveyLaunchResult result);

	List<SurveyLaunch> findLaunchesBySurveyId(long surveyId);

	List<Survey> findSurveysByBusinessUnitId(Integer businessUnitId);
}
