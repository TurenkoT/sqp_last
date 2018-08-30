package com.severstal.sqp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.severstal.sqp.dao.SurveyDao;
import com.severstal.sqp.entity.Survey;
import com.severstal.sqp.entity.SurveyLaunch;
import com.severstal.sqp.entity.SurveyLaunchResult;
import com.severstal.sqp.entity.SurveyQuestion;
import com.severstal.sqp.entity.User;

@Repository
public class SurveyDaoImpl implements SurveyDao
{
	@PersistenceContext
	private EntityManager em;

	@Override
	public void createSurvey(final Survey survey)
	{
		em.persist(survey);
	}

	@Override
	public void updateSurvey(final Survey survey)
	{
		em.merge(survey);
	}

	@Override
	public void deleteSurvey(final Survey survey)
	{
		em.remove(survey);
	}

	@Override
	public Survey findSurveyById(final long surveyId)
	{
		return em.find(Survey.class, surveyId);
	}

	@Override
	public List<Survey> getAllSurveys()
	{
		return em.createQuery("select s from Survey s").getResultList();
	}

	@Override
	public void createSurveyQuestion(final SurveyQuestion question)
	{
		em.persist(question);
	}

	@Override
	public void deleteQuestion(SurveyQuestion surveyQuestion)
	{
		em.remove(surveyQuestion);
	}

	@Override
	public SurveyQuestion findSurveyQuestionById(final long questionId)
	{
		return em.find(SurveyQuestion.class, questionId);
	}

	@Override
	public void createSurveyLaunch(final SurveyLaunch surveyLaunch)
	{
		em.persist(surveyLaunch);
	}

	@Override
	public List<SurveyLaunch> getAllSurveysLaunches()
	{
		return em.createQuery("select sl from SurveyLaunch sl").getResultList();
	}

	@Override
	public SurveyLaunch findLaunchById(final long surveyLaunchId)
	{
		return em.find(SurveyLaunch.class, surveyLaunchId);
	}

	@Override
	public void createSurveyLaunchResult(final SurveyLaunchResult result)
	{
		em.persist(result);
	}

	@Override
	public List<SurveyLaunch> findLaunchesBySurveyId(final long surveyId)
	{
		return em.createQuery("select sl from SurveyLaunch sl where sl.survey.id = :surveyId")
		         .setParameter("surveyId", surveyId)
		         .getResultList();
	}

    @Override
    public List<Survey> findSurveysByBusinessUnitId(Integer businessUnitId) {
        return em.createQuery(
                "select s from Survey s where s.author.subdivision.organisation.businessUnit.id = :businessUnitId")
                 .setParameter("businessUnitId", businessUnitId).getResultList();
    }
}
