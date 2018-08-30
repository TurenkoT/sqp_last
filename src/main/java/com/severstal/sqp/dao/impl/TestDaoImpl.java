package com.severstal.sqp.dao.impl;

/**
 * Test dao implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao.impl
 */

import com.severstal.sqp.dao.TestDao;
import com.severstal.sqp.dto.*;
import com.severstal.sqp.entity.*;
import com.severstal.sqp.model.impl.UserProfileType;
import com.severstal.sqp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;


@Repository
@Transactional
public class TestDaoImpl implements TestDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    @Qualifier("iMySQLJdbc")
    private JdbcTemplate template;

    @Autowired
    private UserService userService;

    private static Logger logger = Logger.getLogger(TestDaoImpl.class.getName());

    @Override
    public void createTest(Test test) {
        em.persist(test);
    }

    @Override
    public void updateTest(Test test) {
        em.merge(test);
    }

    @Transactional
    public Test findTestById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Test test = null;
        try {
            entityManager.getTransaction().begin();
            test = entityManager.find(Test.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return test;

    }

    @Transactional
    public List<Test> findAllTests() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Test> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Test> query = entityManager.createQuery("FROM Test", Test.class);
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
    public Test findCurrentTest() {

        List<Test> tests = findAllTests();
        if (tests.isEmpty()) {
            return null;
        } else {
            return tests.get(tests.size() - 1);
        }
    }

    @Transactional
    public void updateCountOfTries(int testId, int countRight, int countFalse) {
        String que = "UPDATE test SET count_right=count_right+" + countRight + ",count_false=count_false+"
                + countFalse + " WHERE test.id=" + testId;
        template.update(que);
    }

    @Transactional
    public List<Test> findAllTestByUserId(int userId, int subjectId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Test> list = new ArrayList<>();
        try {
            list.addAll(template.query("SELECT T.id " +
                    "FROM test T JOIN user U ON T.user_id = U.id and U.id=" + userId +
                    " JOIN question Q ON T.question_id = Q.id and Q.subject_id=" + subjectId, new RowMapper<Test>() {
                @Override
                public Test mapRow(ResultSet resultSet, int i) throws SQLException {
                    return entityManager.find(Test.class, resultSet.getInt(1));
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
    public List<Test> findAllTestByBusinessUnitId(int businessUnitId, String startDate, String endDate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Test> list = new ArrayList<>();
        try {
            list.addAll(template.query("SELECT DISTINCT T.id" +
                                               " FROM test T JOIN user U ON T.user_id = U.id" +
                                               " JOIN organisation O ON U.organisation_id = O.id" +
                                               " JOIN business_unit B ON O.business_unit_id = " + businessUnitId +
                                               " WHERE T.date BETWEEN '" + startDate + "' AND '" + endDate + "'", new RowMapper<Test>() {
                @Override
                public Test mapRow(ResultSet resultSet, int i) throws SQLException {
                    return entityManager.find(Test.class, resultSet.getInt(1));
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
    public List<Test> findAllTestsBySubdivisionId(int subdivisionId, String startDate, String endDate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Test> list = new ArrayList<>();
        try {
            list.addAll(template.query("SELECT DISTINCT T.id" +
                                               " FROM test T JOIN user U ON T.user_id = U.id" +
                                               " JOIN subdivision S ON U.subdivision_id = " + subdivisionId +
                                               " WHERE T.date BETWEEN '" + startDate + "' AND '" + endDate + "'", new RowMapper<Test>() {
                @Override
                public Test mapRow(ResultSet resultSet, int i) throws SQLException {
                    return entityManager.find(Test.class, resultSet.getInt(1));
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
    public List<Test> findTestsInDatesByUserRole(Date startDate, Date endDate, User user) {
        for (Role role : user.getRoles()) {
            if (role.getType().equals(UserProfileType.HEADMAN.getUserProfileType())) {
                return findTestInDatesForHeadman(startDate, endDate, user.getSubdivision());
            }

            if (role.getType().equals(UserProfileType.DIRECTOR.getUserProfileType())) {
                return findTestInDatesForDirector(startDate, endDate, user.getSubdivision().getOrganisation());
            }

            if (role.getType().equals(UserProfileType.TOP_MANAGEMENT.getUserProfileType())) {
                return findTestInDatesForTopManagement(startDate, endDate, user.getSubdivision().getOrganisation().getBusinessUnit());
            }
        }
        return null;
    }

    @Transactional
    public List<Test> findTestInDatesForHeadman(Date startDate, Date endDate, Subdivision subdivision) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("FROM Test T " +
                    "WHERE T.user.subdivision.id = :subdivisionId AND T.date BETWEEN :startDate AND :endDate", Test.class)
                    .setParameter("subdivisionId", subdivision.getId())
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    @Transactional
    public List<Test> findTestInDatesForDirector(Date startDate, Date endDate, Organisation organisation) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("FROM Test T " +
                    "WHERE T.user.subdivision.organisation.id = :organisationId AND T.date BETWEEN :startDate AND :endDate", Test.class)
                    .setParameter("organisationId", organisation.getId())
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    @Transactional
    public List<Test> findTestInDatesForTopManagement(Date startDate, Date endDate, BusinessUnit businessUnit) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("FROM Test T " +
                    "WHERE T.user.subdivision.organisation.businessUnit.id = :businessUnitId AND T.date BETWEEN :startDate AND :endDate", Test.class)
                    .setParameter("businessUnitId", businessUnit.getId())
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    @Transactional
    public List<TestsForReportDTO> getTestsForReport(String startDate, String endDate, User user, String[] subdivs) {
        String s = "SELECT T.user_id as USER," +
                "SUM(T.count_right) AS COUNT_RIGHT," +
                "SUM(T.count_false) AS COUNT_FALSE," +
                "COUNT(T.question_id) AS COUNT_OF_QUESTIONS," +
                "T.date AS DATE " +
                "FROM Test T " +
                "JOIN user U ON T.user_id = U.id " +
                "JOIN subdivision S ON U.subdivision_id = S.id AND S.business_unit_id=" + user.getSubdivision().getOrganisation().getBusinessUnit().getId() +
                " WHERE T.date BETWEEN '" + startDate + "' AND '" + endDate + "'" + " AND U.subdivision_id IN (" + Arrays.toString(subdivs).replace("[", "").replace("]", "") + ")" +
                "GROUP BY T.user_id";
        return template.query(s, new RowMapper<TestsForReportDTO>() {
            @Override
            public TestsForReportDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                TestsForReportDTO testsForReportDTO = new TestsForReportDTO();
                testsForReportDTO.setUser(userService.findById(resultSet.getInt(1)));
                testsForReportDTO.setTotalCountOfRight(resultSet.getInt(2));
                testsForReportDTO.setTotalCountOfFalse(resultSet.getInt(3));
                testsForReportDTO.setTotalCountOfQuestions(resultSet.getInt(4));
                testsForReportDTO.setDate(resultSet.getDate(5));
                return testsForReportDTO;
            }
        });
    }

    @Transactional
    public Map<Integer, ResultsForMonthsReportsDto> getResultsForFullBUReport(List<Integer> monthNumbers, User user) {
        final Map<Integer, ResultsForMonthsReportsDto> resultMap = new HashMap<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {

            for (Integer month : monthNumbers) {
                final ResultsForMonthsReportsDto resultsForMonthsReportsDto = new ResultsForMonthsReportsDto();
                List<Object[]> list = entityManager.createNativeQuery("SELECT SUM(T.count_right) AS COUNT_RIGHT, SUM(T.count_false) AS COUNT_FALSE, COUNT(T.ID) AS TOTAL_COUNT " +
                        "FROM Test T JOIN User U ON T.user_id = U.id JOIN Subdivision S ON U.subdivision_id = S.id " +
                        "WHERE MONTH(T.date) in (:month) AND S.business_unit_id=:businessUnitId")
                        .setParameter("month", month)
                        .setParameter("businessUnitId", user.getSubdivision().getOrganisation().getBusinessUnit().getId())
                        .getResultList();
                if (Integer.parseInt(list.get(0)[2].toString()) != 0) {
                    resultsForMonthsReportsDto.setSumRight(Integer.parseInt(list.get(0)[0].toString()));
                    resultsForMonthsReportsDto.setSumFalse(Integer.parseInt(list.get(0)[1].toString()));
                    resultsForMonthsReportsDto.setTotalCountQuestions(Integer.parseInt(list.get(0)[2].toString()));
                }
                resultMap.put(month, resultsForMonthsReportsDto);
            }
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return resultMap;
    }


    @Transactional
    public Map<Integer, List<ResultsForMonthsReportsByOrganisationDto>> getResultsOfTestByOrganisation(List<Integer> monthNumbers, User user) {
        final Map<Integer, List<ResultsForMonthsReportsByOrganisationDto>> resultMap = new HashMap<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {

            for (Integer month : monthNumbers) {

                List<Object[]> list = entityManager.createNativeQuery("SELECT O.ID AS ORGANISATION_ID, SUM(T.count_right) AS COUNT_RIGHT, SUM(T.count_false) AS COUNT_FALSE, COUNT(T.ID) AS TOTAL_COUNT " +
                        "FROM Test T JOIN User U ON T.user_id = U.id JOIN Subdivision S ON U.subdivision_id = S.id JOIN organisation O On S.organisation_id = O.id " +
                        "WHERE MONTH(T.date) in (:month) AND S.business_unit_id=:businessUnitId " +
                        "GROUP BY O.id")
                        .setParameter("month", month)
                        .setParameter("businessUnitId", user.getSubdivision().getOrganisation().getBusinessUnit().getId())
                        .getResultList();
                if (!list.isEmpty()) {
                    List<ResultsForMonthsReportsByOrganisationDto> resultList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (Integer.parseInt(list.get(i)[3].toString()) != 0) {
                            ResultsForMonthsReportsByOrganisationDto resultsForMonthsReportsDto = new ResultsForMonthsReportsByOrganisationDto();
                            resultsForMonthsReportsDto.setOrganisationId(Integer.parseInt(list.get(i)[0].toString()));
                            resultsForMonthsReportsDto.setSumRight(Integer.parseInt(list.get(i)[1].toString()));
                            resultsForMonthsReportsDto.setSumFalse(Integer.parseInt(list.get(i)[2].toString()));
                            resultsForMonthsReportsDto.setTotalCountQuestions(Integer.parseInt(list.get(i)[3].toString()));
                            resultList.add(resultsForMonthsReportsDto);
                        }
                    }
                    resultMap.put(month, resultList);
                }
            }
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return resultMap;
    }

    @Transactional
    public Map<Integer, List<ResultsForMonthsReportsBySubdivisionDto>> getResultsOfTestBySubdivision(List<Integer> monthNumbers, User user) {
        final Map<Integer, List<ResultsForMonthsReportsBySubdivisionDto>> resultMap = new HashMap<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {

            for (Integer month : monthNumbers) {

                List<Object[]> list = entityManager.createNativeQuery("SELECT O.ID AS ORGANISATION_ID, S.id AS SUBDIVISION_ID, SUM(T.count_right) AS COUNT_RIGHT, SUM(T.count_false) AS COUNT_FALSE, COUNT(T.ID) AS TOTAL_COUNT " +
                        "FROM Test T JOIN User U ON T.user_id = U.id JOIN Subdivision S ON U.subdivision_id = S.id JOIN organisation O On S.organisation_id = O.id " +
                        "WHERE MONTH(T.date) in (:month) AND S.business_unit_id=:businessUnitId " +
                        "GROUP BY S.id")
                        .setParameter("month", month)
                        .setParameter("businessUnitId", user.getSubdivision().getOrganisation().getBusinessUnit().getId())
                        .getResultList();
                if (!list.isEmpty()) {
                    List<ResultsForMonthsReportsBySubdivisionDto> resultList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (Integer.parseInt(list.get(i)[4].toString()) != 0) {
                            ResultsForMonthsReportsBySubdivisionDto resultsForMonthsReportsDto = new ResultsForMonthsReportsBySubdivisionDto();
                            resultsForMonthsReportsDto.setOrganisationId(Integer.parseInt(list.get(i)[0].toString()));
                            resultsForMonthsReportsDto.setSubdivisionId(Integer.parseInt(list.get(i)[1].toString()));
                            resultsForMonthsReportsDto.setSumRight(Integer.parseInt(list.get(i)[2].toString()));
                            resultsForMonthsReportsDto.setSumFalse(Integer.parseInt(list.get(i)[3].toString()));
                            resultsForMonthsReportsDto.setTotalCountQuestions(Integer.parseInt(list.get(i)[4].toString()));
                            resultList.add(resultsForMonthsReportsDto);
                        }
                    }
                    resultMap.put(month, resultList);
                }
            }
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return resultMap;
    }

    @Transactional
    public Map<Integer, List<ResultsForMonthsReportsByProfessionDto>> getResultsOfTestByProfession(List<Integer> monthNumbers, User user) {
        final Map<Integer, List<ResultsForMonthsReportsByProfessionDto>> resultMap = new HashMap<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {

            for (Integer month : monthNumbers) {

                List<Object[]> list = entityManager.createNativeQuery("SELECT UP.profession_id AS PROFESSION_ID, SUM(T.count_right) AS COUNT_RIGHT, SUM(T.count_false) AS COUNT_FALSE, COUNT(T.ID) AS TOTAL_COUNT " +
                        "FROM TEST T JOIN User U ON T.user_id = U.id JOIN user_to_profession UP ON U.id = UP.user_id JOIN Subdivision S ON U.subdivision_id = S.id " +
                        "WHERE MONTH(T.date) in (:month) AND S.business_unit_id=:businessUnitId " +
                        "GROUP BY PROFESSION_ID")
                        .setParameter("month", month)
                        .setParameter("businessUnitId", user.getSubdivision().getOrganisation().getBusinessUnit().getId())
                        .getResultList();
                if (!list.isEmpty()) {
                    List<ResultsForMonthsReportsByProfessionDto> resultList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (Integer.parseInt(list.get(i)[3].toString()) != 0) {
                            ResultsForMonthsReportsByProfessionDto resultsForMonthsReportsDto = new ResultsForMonthsReportsByProfessionDto();
                            resultsForMonthsReportsDto.setProfessionId(Integer.parseInt(list.get(i)[0].toString()));
                            resultsForMonthsReportsDto.setSumRight(Integer.parseInt(list.get(i)[1].toString()));
                            resultsForMonthsReportsDto.setSumFalse(Integer.parseInt(list.get(i)[2].toString()));
                            resultsForMonthsReportsDto.setTotalCountQuestions(Integer.parseInt(list.get(i)[3].toString()));
                            resultList.add(resultsForMonthsReportsDto);
                        }
                    }
                    resultMap.put(month, resultList);
                }
            }
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return resultMap;
    }
}
