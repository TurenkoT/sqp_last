package com.severstal.sqp.dao;

/**
 * Question interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao
 */

import com.severstal.sqp.dto.ComplexityDto;
import com.severstal.sqp.entity.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface QuestionDao {

    Question findQuestionByRandomId(int id);

    Question findQuestionById(int id);

    List<Question> findAllQuestions();

    List<Answer> findAllAnswerByQuestionId(int id);

    List<Answer> findRightAnswer(int questionId);

    List<Question> findQuestionsBySubjectId(int subjectId);

    Subject findRandomSubjectById(int id);

    List<Subject> findAllSubjects();

    Subject findSubjectById(int id);

    Subject createNewSubjectByName(String name);

    Subject findSubjectByName(String name);

    Question createNewQuestionByValue(String value, int subjectId, String article);

    Question findQuestionByValue(String value);

    Answer createNewAnswer(String value, Question question);

//    void populateQuestionToAnswer(int questionid, int answerId);

    void deleteQuestionById(int id);

    List<Answer> findAnswersByQuestionId(int questionId);

    void deleteAnswersForQuestion(List<Answer> answerList);

    void deletePopulationAnswerToQuestion(List<Answer> answerList, int questionId);
//
//    void deletePopulationTestToQuestion(int questionId);

    TypeQuestion findTypeQuestionByArticle(String article);

//    void deleteChosenQuestions(String[] tdValues);

    List<TypeQuestion> findAllTypeQuestion();

    void updateEditQuestion(Subject newSubject, Question newQuestion, String questionValue, TypeQuestion typeQuestion);

    List<String> updateAnswers(List<String> newAnswers, int questionId);

    List<Subject> getSubjectByProfessionAndSubdivision(Profession profession, Subdivision subdivision);

    List<Subject> findSubjectByBusinessUnitId(String businessUnitId);

    Subject createNewSubjectByNameAndBusinessUnit(String item, BusinessUnit businessUnit);

    List<Question> findAllQuestionsByBusinessUnit(String businessUnitId);

    List<Complexity> getAllActiveComplexityForUser(User user);

    Complexity getComplexityById(Long id);

    List<Question> findQuestionsBySubjectIdAndComplexity(int subjectId, Complexity complexity);

    void updateComplexity(ComplexityDto complexityDto);

    TypeQuestion findTypeQuestionById(int id);

    void updateQuestion(String questionText, Boolean activeStatus, Question question, Subject subject, Complexity complexity, TypeQuestion typeQuestion, String[] additionalAnswers, Boolean[] correctnessAnswers);

    Question setFieldsOfQuestion(String questionText, Boolean activeStatus, Question question, Subject subject, Complexity complexity, TypeQuestion typeQuestion);

    void deleteAnswersForQuestion(Question question);

    void createNewAnswersForQuestion(Question question, String[] additionalAnswers, Boolean[] correctnessAnswers);

    void saveNewQuestion(String questionText, Subject subject, Complexity complexity, TypeQuestion typeQuestion, String[] additionalAnswers, Boolean[] correctnessAnswers);

    Question setFieldsOfQuestion(String questionText, Subject subject, Complexity complexity, TypeQuestion typeQuestion);

    void setComplexityForQuestions(String[] questionsIds, int complexityId);

    void updateQuestionComplexity(int questionId, long complexityId);
}
