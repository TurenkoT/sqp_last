package com.severstal.sqp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.severstal.sqp.dao.SurveyDao;
import com.severstal.sqp.entity.Survey;
import com.severstal.sqp.entity.SurveyLaunch;
import com.severstal.sqp.entity.SurveyLaunchResult;
import com.severstal.sqp.entity.SurveyQuestion;
import com.severstal.sqp.entity.User;
import com.severstal.sqp.service.SurveyService;

@Service
@Transactional
public class SurveyServiceImpl implements SurveyService
{
	@Autowired
	private SurveyDao surveyDao;

	@Override
	public void createSurvey(final Survey survey)
	{
		surveyDao.createSurvey(survey);
	}

	@Override
	public void updateSurvey(final Survey survey)
	{
		surveyDao.updateSurvey(survey);
	}

	@Override
	public void deleteSurvey(final long surveyId)
	{
		final Survey survey = surveyDao.findSurveyById(surveyId);
		surveyDao.deleteSurvey(survey);
	}

	@Override
	public Survey findSurveyById(final long surveyId)
	{
		return surveyDao.findSurveyById(surveyId);
	}

	@Override
	public List<Survey> getAllSurveys()
	{
		return surveyDao.getAllSurveys();
	}

	@Override
	public void createSurveyQuestion(final SurveyQuestion question)
	{
		surveyDao.createSurveyQuestion(question);
	}

	@Override
	public void deleteSurveyQuestion(final long questionId)
	{
		final SurveyQuestion surveyQuestion = surveyDao.findSurveyQuestionById(questionId);
		surveyDao.deleteQuestion(surveyQuestion);
	}

	@Override
	public void createSurveyLaunch(final SurveyLaunch surveyLaunch)
	{
		surveyDao.createSurveyLaunch(surveyLaunch);
	}

	@Override
	public List<SurveyLaunch> getAllSurveysLaunches()
	{
		return surveyDao.getAllSurveysLaunches();
	}

	@Override
	public List<SurveyLaunch> getAllActiveSurveysLaunchesForUser(final User user)
	{
		final List<SurveyLaunch> allSurveysLaunches = this.getAllSurveysLaunches();
        final List<SurveyLaunch> userActiveSurveysLaunches = allSurveysLaunches.stream().filter(e ->
                                                {
                                                    boolean professionMatch
                                                            = e
                                                            .getProfessions()
                                                            .stream()
                                                            .anyMatch(
                                                                    p ->
                                                                            user
                                                                                    .getProfessions()

                                                                                    .stream()
                                                                                    .anyMatch(
                                                                                            up -> up.getId()
                                                                                                    .equals(p.getId()))
                                                            );
                                                    boolean subdivisionMatch
                                                            = e
                                                            .getSubdivisions()
                                                            .stream()
                                                            .anyMatch(
                                                                    s -> user
                                                                            .getSubdivision()
                                                                            .getId()
                                                                            .equals(s.getId()));

                                                    boolean completed
                                                            = e
                                                            .getSurveyLaunchResults()
                                                            .stream()
                                                            .anyMatch(
                                                                    r -> r.getUser()
                                                                          .getId()
                                                                          .equals(user.getId()));

                                                    return professionMatch &&
                                                            subdivisionMatch &&
                                                            !completed;
                                                })
                                               .collect(Collectors.toList());

		return userActiveSurveysLaunches;
	}

	@Override
	public SurveyLaunch findLaunchById(final long surveyLaunchId)
	{
		return surveyDao.findLaunchById(surveyLaunchId);
	}

	@Override
	public SurveyQuestion findSurveyQuestionById(final Long questionId)
	{
		return surveyDao.findSurveyQuestionById(questionId);
	}

	@Override
	public void createSurveyLaunchResult(final SurveyLaunchResult result)
	{
		surveyDao.createSurveyLaunchResult(result);
	}

	@Override
	public List<SurveyLaunch> findLaunchesBySurveyId(final long surveyId)
	{
		return surveyDao.findLaunchesBySurveyId(surveyId);
	}

	@Override
	public List<Survey> findSurveysByBusinessUnitId(Integer businessUnitId) {
		return surveyDao.findSurveysByBusinessUnitId(businessUnitId);
	}
}
