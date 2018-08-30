package com.severstal.sqp.dao.impl;

/**
 * Question dao implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao.impl
 */

import com.severstal.sqp.dao.QuestionDao;
import com.severstal.sqp.dto.ComplexityDto;
import com.severstal.sqp.entity.*;
import com.severstal.sqp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
@Transactional
public class QuestionDaoImpl implements QuestionDao {

    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Autowired
    @Qualifier("iMySQLJdbc")
    private JdbcTemplate template;
    @Autowired
    DataSource dataSource;
    @Autowired
    UserService userService;

    private static Logger logger = Logger.getLogger(QuestionDaoImpl.class.getName());

    @Transactional
    public Question findQuestionByRandomId(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Question question = null;
        try {
            entityManager.getTransaction().begin();
            question = entityManager.find(Question.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return question;
    }

    @Transactional
    public Question findQuestionById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Question question = null;
        try {
            entityManager.getTransaction().begin();
            question = entityManager.find(Question.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return question;
    }

    @Transactional
    public List<Question> findAllQuestions() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Question> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Question> query = entityManager.createQuery("FROM Question", Question.class);
            list.addAll(query.getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return list;
    }

    @Transactional
    public List<Answer> findAllAnswerByQuestionId(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Answer> list = new ArrayList<>();
        try {
            list.addAll(template.query("SELECT A.ID " +
                    "FROM answer A where A.question_id=" + id, new RowMapper<Answer>() {
                @Override
                public Answer mapRow(ResultSet resultSet, int i) throws SQLException {
                    return entityManager.find(Answer.class, resultSet.getInt(1));
                }
            }));
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return list;
    }

    @Transactional
    public List<Answer> findRightAnswer(int questionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Answer> answers = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            answers.addAll(template.query("SELECT A.ID " +
                            "FROM answer A where A.question_id=" + questionId +
                            " AND A.correctness=true",
                    new RowMapper<Answer>() {
                        @Override
                        public Answer mapRow(ResultSet resultSet, int i) throws SQLException {
                            return entityManager.find(Answer.class, resultSet.getInt(1));
                        }
                    }));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return answers;
    }

    @Transactional
    public Subject findRandomSubjectById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Subject subject = null;
        try {
            entityManager.getTransaction().begin();
            subject = entityManager.find(Subject.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return subject;
    }

    @Transactional
    public Subject findSubjectById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Subject subject = null;
        try {
            entityManager.getTransaction().begin();
            subject = entityManager.find(Subject.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return subject;
    }

    @Transactional
    public List<Question> findQuestionsBySubjectId(int subjectId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Question> list = new ArrayList<>();
        try {
            Subject subject = null;
            entityManager.getTransaction().begin();
            subject = entityManager.find(Subject.class, subjectId);
            entityManager.getTransaction().commit();

            Subject finalSubject = subject;
            list.addAll(template.query("SELECT Q.id, Q.text, Q.subject_id, Q.type_question_id " +
                    "FROM question Q " +
                    "WHERE subject_id=" + subjectId, new RowMapper<Question>() {
                @Override
                public Question mapRow(ResultSet resultSet, int i) throws SQLException {
                    Question question = new Question();
                    question.setId(resultSet.getInt(1));
                    question.setText(resultSet.getString(2));
                    question.setSubject(finalSubject);
                    question.setTypeQuestion(entityManager.find(TypeQuestion.class, resultSet.getInt(3)));
                    return question;
                }
            }));
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return list;
    }

    @Transactional
    public List<Answer> findAnswersByQuestionId(int questionId) {
        return template.query("SELECT A.id, A.value, A.correctness " +
                "FROM answer A JOIN answer_to_question AQ where AQ.question_id=" + questionId +
                " AND A.id=AQ.answer_id AND A.correctness=true", new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet resultSet, int i) throws SQLException {
                Answer answer = new Answer();
                answer.setId(resultSet.getInt(1));
                answer.setValue(resultSet.getString(2));
                answer.setCorrectness(resultSet.getBoolean(3));
                return answer;
            }
        });
    }

    @Transactional
    public List<Subject> findAllSubjects() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Subject> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Subject> query = entityManager.createQuery("FROM Subject", Subject.class);
            list.addAll(query.getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return list;
    }

    @Transactional
    public Subject createNewSubjectByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Subject subject = new Subject();
        subject.setName(name);
        subject.setBusinessUnit(userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getOrganisation().getBusinessUnit());
        entityManager.getTransaction().begin();
        entityManager.persist(subject);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return subject;
    }

    /*
        Нужно уточнить, стоить ли удалять со статистикой по этому вопросу, или только вопрос и ответы
     */

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void deleteChosenQuestions(String[] tdValues) {
//        List<Answer> answers = new ArrayList<>();
//        for (String str : tdValues) {
//            answers = findAllAnswerByQuestionId(Integer.parseInt(str));
//            deletePopulationAnswerToQuestion(answers, Integer.parseInt(str));
//            deleteAnswersForQuestion(answers);
//            deletePopulationTestToQuestion(Integer.parseInt(str));
//            deleteQuestionById(Integer.parseInt(str));
//        }
//    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteQuestionById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Question question = entityManager.find(Question.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(question) ? question : entityManager.merge(question));
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void deletePopulationTestToQuestion(int questionId) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        String que = "DELETE FROM test_to_question WHERE question_id=?";
//        entityManager.getTransaction().begin();
//        entityManager.createNativeQuery(que)
//                .setParameter(1, questionId).executeUpdate();
//        entityManager.flush();
//        entityManager.getTransaction().commit();
//
//        entityManager.close();
//    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deletePopulationAnswerToQuestion(List<Answer> answerList, int questionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "DELETE FROM answer_to_question WHERE answer_id=? AND question_id =?";
        for (Answer answer : answerList) {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(que)
                    .setParameter(1, answer.getId())
                    .setParameter(2, questionId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();

        }
        entityManager.close();

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteAnswersForQuestion(List<Answer> answerList) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        for (Answer answer : answerList) {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(answer) ? answer : entityManager.merge(answer));
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
        entityManager.close();
    }


    @Transactional
    public void deleteAnswersForQuestion(Question question) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "DELETE FROM answer WHERE question_id =?";
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(que)
                .setParameter(1, question.getId()).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public Subject findSubjectByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Subject subject = null;
        try {
            entityManager.getTransaction().begin();
            String que = "SELECT S.id FROM Subject S where S.name='" + name + "'";
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            subject = entityManager.find(Subject.class, list.get(0));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return subject;
    }

    @Transactional
    public TypeQuestion findTypeQuestionByArticle(String article) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypeQuestion typeQuestion = null;
        try {
            entityManager.getTransaction().begin();
            String que = "SELECT T.id FROM TypeQuestion T where T.article='" + article + "'";
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            typeQuestion = entityManager.find(TypeQuestion.class, list.get(0));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return typeQuestion;
    }

    @Transactional
    public Question createNewQuestionByValue(String value, int subjectId, String article) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Question question = new Question();
        Subject subject = findSubjectById(subjectId);
        TypeQuestion typeQuestion = findTypeQuestionByArticle(article);
        question.setText(value);
        question.setSubject(subject);
        question.setTypeQuestion(typeQuestion);
        question.setActiveStatus(true);
        question.setComplexity(getAllActiveComplexityForUser(userService.getCurrentUser()).get(0));
        entityManager.getTransaction().begin();
        entityManager.persist(question);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return question;
    }

    @Transactional
    public Question findQuestionByValue(String value) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Question question = null;
        try {
            entityManager.getTransaction().begin();
            String que = "SELECT Q.id FROM Question Q where Q.text='" + value + "'";
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            question = entityManager.find(Question.class, list.get(0));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return question;
    }

    @Transactional
    public Answer createNewAnswer(String value, Question question) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Answer answer = new Answer();
        answer.setQuestion(question);
        if (value.startsWith("@")) {
            answer.setValue(value.substring(1));
            answer.setCorrectness(true);
        } else {
            answer.setValue(value);
            answer.setCorrectness(false);
        }
        entityManager.getTransaction().begin();
        entityManager.persist(answer);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return answer;
    }

//    @Transactional
//    public void populateQuestionToAnswer(int questionid, int answerId) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        String que = "INSERT INTO answer_to_question (answer_id, question_id) VALUE (?,?)";
//        entityManager.getTransaction().begin();
//        entityManager.createNativeQuery(que)
//                .setParameter(1, answerId)
//                .setParameter(2, questionid).executeUpdate();
//        entityManager.flush();
//        entityManager.getTransaction().commit();
//        entityManager.close();
//    }

    @Transactional
    public List<TypeQuestion> findAllTypeQuestion() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<TypeQuestion> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<TypeQuestion> query = entityManager.createQuery("FROM TypeQuestion", TypeQuestion.class);
            list.addAll(query.getResultList());
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return list;
    }

    @Transactional
    public void updateEditQuestion(Subject newSubject, Question newQuestion, String questionValue, TypeQuestion typeQuestion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Question question = findQuestionById(newQuestion.getId());
        entityManager.getTransaction().begin();
        question.setSubject(newSubject);
        question.setText(questionValue);
        question.setTypeQuestion(typeQuestion);
        entityManager.merge(question);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public List<String> updateAnswers(List<String> newAnswers, int questionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Answer> answerList = findAllAnswerByQuestionId(questionId);
        List<String> valueList = new ArrayList<>();
        for (int i = 0; i < newAnswers.size(); i++) {
            if (i < answerList.size()) {
                entityManager.getTransaction().begin();
                if (!answerList.get(i).getValue().replaceAll(" ", "").toLowerCase().equals(newAnswers.get(i).replaceAll(" ", "").toLowerCase())) {
                    if (newAnswers.get(i).startsWith("@")) {
                        answerList.get(i).setValue(newAnswers.get(i).substring(1));
                        answerList.get(i).setCorrectness(true);
                    } else {
                        answerList.get(i).setValue(newAnswers.get(i));
                        answerList.get(i).setCorrectness(false);
                    }
                }
                entityManager.merge(answerList.get(i));
                entityManager.flush();
                entityManager.getTransaction().commit();
            } else {
                valueList.add(newAnswers.get(i));
            }
        }
        entityManager.close();
        return valueList;
    }

    @Transactional
    public List<Subject> getSubjectByProfessionAndSubdivision(Profession profession, Subdivision subdivision) {
        return template.query("SELECT S.id, S.name " +
                "FROM subject S JOIN subject_to_subdivprof SS " +
                "JOIN profession_to_subdivision PS on SS.profsubdiv_id = PS.id where PS.profession_id=" + profession.getId() +
                " AND PS.subdivision_id=" + subdivision.getId() +
                " AND S.id=SS.subject_id", new RowMapper<Subject>() {
            @Override
            public Subject mapRow(ResultSet resultSet, int i) throws SQLException {
                Subject subject = new Subject();
                subject.setId(resultSet.getInt(1));
                subject.setName(resultSet.getString(2));
                return subject;
            }
        });
    }

    @Transactional
    public List<Subject> findSubjectByBusinessUnitId(String businessUnitId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Subject> subjects = new ArrayList<>();
        try {
            String que = "SELECT S.id FROM Subject S where S.businessUnit='" + businessUnitId + "'";
            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++) {
                    subjects.add(entityManager.find(Subject.class, list.get(i)));
                }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return subjects;
    }

    @Transactional
    public Subject createNewSubjectByNameAndBusinessUnit(String item, BusinessUnit businessUnit) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Subject subject = new Subject();
        subject.setName(item);
        subject.setBusinessUnit(businessUnit);
        entityManager.getTransaction().begin();
        entityManager.persist(subject);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return subject;
    }

    @Transactional
    public List<Question> findAllQuestionsByBusinessUnit(String businessUnitId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Question> questions = new ArrayList<>();
        try {
            String que = "SELECT Q.id FROM Question Q JOIN Q.subject S WHERE S.id = Q.subject AND S.businessUnit='" + businessUnitId + "'";
            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++) {
                    questions.add(entityManager.find(Question.class, list.get(i)));
                }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return questions;
    }

    @Transactional
    public List<Complexity> getAllActiveComplexityForUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Complexity> complexityList = entityManager.createQuery("SELECT C FROM Complexity C WHERE C.businessUnit.id = :businessUnitId")
                .setParameter("businessUnitId", user.getSubdivision().getOrganisation().getBusinessUnit().getId())
                .getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return complexityList;
    }

    @Transactional
    public Complexity getComplexityById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Complexity complexity = null;
        try {
            entityManager.getTransaction().begin();
            complexity = entityManager.find(Complexity.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return complexity;
    }


    @Transactional
    public List<Question> findQuestionsBySubjectIdAndComplexity(int subjectId, Complexity complexity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        List<Question> list = new ArrayList<>();
        try {
            Subject subject = null;
            entityManager.getTransaction().begin();
            subject = entityManager.find(Subject.class, subjectId);
            entityManager.getTransaction().commit();

            Subject finalSubject = subject;
            list.addAll(template.query("SELECT Q.id, Q.text,Q.active_status, Q.complexity_id, Q.subject_id, Q.type_question_id " +
                    "FROM question Q " +
                    "WHERE Q.active_status=true AND subject_id=" + subjectId + " AND complexity_id=" + complexity.getId(), new RowMapper<Question>() {
                @Override
                public Question mapRow(ResultSet resultSet, int i) throws SQLException {
                    Question question = new Question();
                    question.setId(resultSet.getInt(1));
                    question.setText(resultSet.getString(2));
                    question.setComplexity(entityManager.find(Complexity.class, resultSet.getLong(3)));
                    question.setSubject(finalSubject);
                    question.setTypeQuestion(entityManager.find(TypeQuestion.class, resultSet.getInt(4)));
                    return question;
                }
            }));
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return list;
    }

    @Transactional
    public void updateComplexity(ComplexityDto complexityDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Complexity complexity = getComplexityById(complexityDto.getId());
        entityManager.getTransaction().begin();
        complexity.setName(complexityDto.getName());
        complexity.setPoints(complexityDto.getPoints());
        complexity.setPenalty(complexityDto.getPenalty());
        entityManager.merge(complexity);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public TypeQuestion findTypeQuestionById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypeQuestion typeQuestion = null;
        try {
            entityManager.getTransaction().begin();
            typeQuestion = entityManager.find(TypeQuestion.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return typeQuestion;
    }

    @Transactional
    public void updateQuestion(String questionText, Boolean activeStatus, Question question, Subject subject, Complexity complexity, TypeQuestion typeQuestion, String[] additionalAnswers, Boolean[] correctnessAnswers) {
        Question newQuestion = setFieldsOfQuestion(questionText, activeStatus, question, subject, complexity, typeQuestion);
        deleteAnswersForQuestion(newQuestion);
        createNewAnswersForQuestion(newQuestion, additionalAnswers, correctnessAnswers);
    }

    @Transactional
    public Question setFieldsOfQuestion(String questionText, Boolean activeStatus, Question question, Subject subject, Complexity complexity, TypeQuestion typeQuestion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Question newQuestion = entityManager.find(Question.class, question.getId());
        entityManager.getTransaction().begin();
        newQuestion.setText(questionText);
        newQuestion.setSubject(subject);
        newQuestion.setComplexity(complexity);
        newQuestion.setTypeQuestion(typeQuestion);
        newQuestion.setActiveStatus(activeStatus);
        entityManager.merge(newQuestion);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return newQuestion;
    }

    @Transactional
    public Question setFieldsOfQuestion(String questionText, Subject subject, Complexity complexity, TypeQuestion typeQuestion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Question question = new Question();
        entityManager.getTransaction().begin();
        question.setText(questionText);
        question.setSubject(subject);
        question.setComplexity(complexity);
        question.setTypeQuestion(typeQuestion);
        question.setActiveStatus(true);
        entityManager.persist(question);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return question;
    }

    @Transactional
    public void createNewAnswersForQuestion(Question question, String[] additionalAnswers, Boolean[] correctnessAnswers) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        for (int i = 0; i < additionalAnswers.length; i++) {
            if (!additionalAnswers[i].equals("")) {
                Answer newAnswer = new Answer();
                newAnswer.setQuestion(question);
                newAnswer.setValue(additionalAnswers[i]);
                newAnswer.setCorrectness(correctnessAnswers[i]);
                entityManager.getTransaction().begin();
                entityManager.persist(newAnswer);
                entityManager.flush();
                entityManager.getTransaction().commit();
            }
        }
        entityManager.close();
    }

    @Transactional
    public void saveNewQuestion(String questionText, Subject subject, Complexity complexity, TypeQuestion typeQuestion, String[] additionalAnswers, Boolean[] correctnessAnswers) {
        Question newQuestion = setFieldsOfQuestion(questionText, subject, complexity, typeQuestion);
        createNewAnswersForQuestion(newQuestion, additionalAnswers, correctnessAnswers);
    }

    @Transactional
    public void setComplexityForQuestions(String[] questionsIds, int complexityId) {
        for (int i = 0; i < questionsIds.length; i++) {
            updateQuestionComplexity(Integer.parseInt(questionsIds[i]), (long) complexityId);
        }
    }

    @Transactional
    public void updateQuestionComplexity(int questionId, long complexityId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Question question = entityManager.find(Question.class, questionId);
        final Complexity complexity = entityManager.find(Complexity.class, complexityId);
        entityManager.getTransaction().begin();
        question.setComplexity(complexity);
        entityManager.merge(question);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
