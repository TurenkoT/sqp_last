package com.severstal.sqp.dao;

/**
 * Test interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao
 */

import com.severstal.sqp.dto.*;
import com.severstal.sqp.entity.Test;
import com.severstal.sqp.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public interface TestDao {

    void createTest(Test test);

    void updateTest(Test test);

    Test findTestById(int id);

    List<Test> findAllTests();

    Test findCurrentTest();

    void updateCountOfTries(int testId, int countRight, int countFalse);

    List<Test> findAllTestByUserId(int userId, int subjectId);

    List<Test> findTestsInDatesByUserRole(Date startDate, Date endDate, User user);

    List<TestsForReportDTO> getTestsForReport(String startDate, String endDate, User user, String[] subdivs);

    Map<Integer, ResultsForMonthsReportsDto> getResultsForFullBUReport(List<Integer> monthNumbers, User user);

    Map<Integer, List<ResultsForMonthsReportsByOrganisationDto>> getResultsOfTestByOrganisation(List<Integer> monthNumbers, User user);

    Map<Integer, List<ResultsForMonthsReportsBySubdivisionDto>> getResultsOfTestBySubdivision(List<Integer> monthNumbers, User user);

    Map<Integer, List<ResultsForMonthsReportsByProfessionDto>> getResultsOfTestByProfession(List<Integer> monthNumbers, User user);

    List<Test> findAllTestByBusinessUnitId(int businessUnitId, String startDate, String endDate);

    List<Test> findAllTestsBySubdivisionId(int subdivisionId, String startDate, String endDate);
}
