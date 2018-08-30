package com.severstal.sqp.service.impl;

/**
 * Question service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.dto.ComplexityDto;
import com.severstal.sqp.entity.*;
import com.severstal.sqp.service.QuestionService;
import com.severstal.sqp.dao.QuestionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Stack;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    private Stack<Complexity> currentComplexityContainer;

    @Autowired
    private Stack<Integer> currentCountOfTriesContainer;

    @Autowired
    private Stack<Double> currentResultPoints;

    public Question findQuestionByRandomId(int id) {
        return questionDao.findQuestionByRandomId(id);
    }

    public Question findQuestionById(int id) {
        return questionDao.findQuestionById(id);
    }

    public List<Question> findAllQuestions() {
        return questionDao.findAllQuestions();
    }

    public List<Answer> findAllAnswerByQuestionId(int id) {
        return questionDao.findAllAnswerByQuestionId(id);
    }

    public List<Answer> findRightAnswer(int questionId) {
        return questionDao.findRightAnswer(questionId);
    }

    public Subject findRandomSubjectById(int id) {
        return questionDao.findRandomSubjectById(id);
    }

    @Transactional
    public List<Question> findQuestionsBySubjectId(int subjectId) {
        return questionDao.findQuestionsBySubjectId(subjectId);
    }

    public List<Subject> findAllSubjects() {
        return questionDao.findAllSubjects();
    }

    public Subject findSubjectById(int id) {
        return questionDao.findSubjectById(id);
    }

    public Subject createNewSubjectByName(String name) {
        return questionDao.createNewSubjectByName(name);
    }

    public Subject findSubjectByName(String name) {
        return questionDao.findSubjectByName(name);
    }

    public Question createNewQuestionByValue(String value, int subjectId, String article) {
        return questionDao.createNewQuestionByValue(value, subjectId, article);
    }

    public Question findQuestionByValue(String value) {
        return questionDao.findQuestionByValue(value);
    }

    public Answer createNewAnswer(String value, Question question) {
        return questionDao.createNewAnswer(value, question);
    }

//    public void populateQuestionToAnswer(int questionid, int answerId) {
//        questionDao.populateQuestionToAnswer(questionid, answerId);
//    }

    @Transactional
    public void deleteQuestionById(int id) {
        questionDao.deleteQuestionById(id);
    }

    @Transactional
    public List<Answer> findAnswersByQuestionId(int questionId) {
        return questionDao.findAllAnswerByQuestionId(questionId);
    }

    @Transactional
    public void deleteAnswersForQuestion(List<Answer> answerList) {
        questionDao.deleteAnswersForQuestion(answerList);
    }

    @Transactional
    public void deletePopulationAnswerToQuestion(List<Answer> answerList, int questionId) {
        questionDao.deletePopulationAnswerToQuestion(answerList, questionId);
    }

//    @Transactional
//    public void deletePopulationTestToQuestion(int questionId) {
//        questionDao.deletePopulationTestToQuestion(questionId);
//    }

    @Transactional
    public TypeQuestion findTypeQuestionByArticle(String article) {
        return questionDao.findTypeQuestionByArticle(article);
    }

//    @Transactional
//    public void deleteChosenQuestions(String[] tdValues) {
//        questionDao.deleteChosenQuestions(tdValues);
//    }

    @Transactional
    public List<TypeQuestion> findAllTypeQuestion() {
        return questionDao.findAllTypeQuestion();
    }

    @Transactional
    public void updateEditQuestion(Subject newSubject, Question newQuestion, String questionValue, TypeQuestion typeQuestion) {
        questionDao.updateEditQuestion(newSubject, newQuestion, questionValue, typeQuestion);
    }

    @Transactional
    public List<String> updateAnswers(List<String> newAnswers, int questionId) {
        return questionDao.updateAnswers(newAnswers, questionId);
    }

    @Transactional
    public List<Subject> getSubjectByProfessionAndSubdivision(Profession profession, Subdivision subdivision) {
        return questionDao.getSubjectByProfessionAndSubdivision(profession, subdivision);
    }

    @Transactional
    public List<Subject> findSubjectByBusinessUnitId(String businessUnitId) {
        return questionDao.findSubjectByBusinessUnitId(businessUnitId);
    }

    @Transactional
    public Subject createNewSubjectByNameAndBusinessUnit(String item, BusinessUnit businessUnit) {
        return questionDao.createNewSubjectByNameAndBusinessUnit(item, businessUnit);
    }

    @Transactional
    public List<Question> findAllQuestionsByBusinessUnit(String businessUnitId) {
        return questionDao.findAllQuestionsByBusinessUnit(businessUnitId);
    }

    @Override
    public List<Complexity> getAllActiveComplexityForUser(User user) {
        return questionDao.getAllActiveComplexityForUser(user);
    }

    @Override
    public Complexity getCurrentComplexity() {
        if (!currentComplexityContainer.empty()) {
            return currentComplexityContainer.peek();
        }
        return null;
    }

    @Override
    public void setCurrentComplexity(Complexity currentComplexity) {
        if (!currentComplexityContainer.empty()) {
            currentComplexityContainer.pop();
        }
        currentComplexityContainer.push(currentComplexity);
    }

    @Override
    public Integer getCurrentCountOfTriesContainer() {
        if (!currentCountOfTriesContainer.empty()) {
            return currentCountOfTriesContainer.peek();
        }
        return null;
    }

    @Override
    public void setCurrentCountOfTriesContainer(int count) {
        if (!currentCountOfTriesContainer.empty()) {
            currentCountOfTriesContainer.pop();
        }
        currentCountOfTriesContainer.push(count);
    }

    @Override
    public Double getCurrentResultPoints() {
        if (!currentResultPoints.empty()) {
            return currentResultPoints.peek();
        }
        return null;
    }

    @Override
    public void setCurrentResultPoints(double points) {
        if (!currentResultPoints.empty()) {
            currentResultPoints.pop();
        }
        currentResultPoints.push(points);
    }

    @Override
    public Complexity getComplexityById(Long id) {
        return questionDao.getComplexityById(id);
    }

    @Override
    public List<Question> findQuestionsBySubjectIdAndComplexity(int subjectId, Complexity complexity) {
        return questionDao.findQuestionsBySubjectIdAndComplexity(subjectId, complexity);
    }

    @Override
    public void resetCurrentSession() {
        setCurrentComplexity(null);
        setCurrentCountOfTriesContainer(0);
        setCurrentResultPoints(0);
    }

    @Override
    public void updateComplexity(ComplexityDto complexityDto) {
        questionDao.updateComplexity(complexityDto);
    }

    @Override
    public TypeQuestion findTypeQuestionById(int id) {
        return questionDao.findTypeQuestionById(id);
    }

    @Override
    public void updateQuestion(String questionText, Boolean activeStatus, Question question, Subject subject, Complexity complexity, TypeQuestion typeQuestion, String[] additionalAnswers, Boolean[] correctnessAnswers) {
        questionDao.updateQuestion(questionText, activeStatus, question, subject, complexity, typeQuestion, additionalAnswers, correctnessAnswers);
    }

    @Override
    public void saveNewQuestion(String questionText, Subject subject, Complexity complexity, TypeQuestion typeQuestion, String[] additionalAnswers, Boolean[] correctnessAnswers) {
        questionDao.saveNewQuestion(questionText, subject, complexity, typeQuestion, additionalAnswers, correctnessAnswers);
    }

    @Override
    public void setComplexityForQuestions(String[] questionsIds, int complexityId) {
        questionDao.setComplexityForQuestions(questionsIds, complexityId);
    }
}
