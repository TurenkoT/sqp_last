package com.severstal.sqp.service.impl;

/**
 * Test service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.dao.TestDao;
import com.severstal.sqp.dto.*;
import com.severstal.sqp.entity.Test;
import com.severstal.sqp.entity.User;
import com.severstal.sqp.service.QuestionService;
import com.severstal.sqp.service.TestService;
import com.severstal.sqp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestDao testDao;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    public Test findTestById(int id) {
        return testDao.findTestById(id);
    }

    public List<Test> findAllTests() {
        return testDao.findAllTests();
    }

    public Test findCurrentTest() {
        return testDao.findCurrentTest();
    }

    public void updateCountOfTries(int testId, int countRight, int countFalse) {
        testDao.updateCountOfTries(testId, countRight, countFalse);
    }

    public List<Test> findAllTestByUserId(int userId, int subjectId){
        return testDao.findAllTestByUserId(userId, subjectId);
    }

    @Override
    public void createTest(Test test) {
        testDao.createTest(test);
    }

    @Override
    public void updateTest(Test test) {
        testDao.updateTest(test);
    }

    @Override
    public void increaseCountOfWrongAnswers() {
        Test currentTestResult = userService.getCurrentTestResult();
        int countFalse = currentTestResult.getCountFalse();
        currentTestResult.setCountFalse(++countFalse);
        currentTestResult.setPoints(questionService.getCurrentResultPoints());
        if (currentTestResult.getId() == null) {
            createTest(currentTestResult);
        } else {
            updateTest(currentTestResult);
        }
    }

    @Override
    public void increaseCountOfRightAnswers() {
        Test currentTestResult = userService.getCurrentTestResult();
        int countRight = currentTestResult.getCountRight();
        currentTestResult.setCountRight(++countRight);
        currentTestResult.setPoints(questionService.getCurrentResultPoints());
        if (currentTestResult.getId() == null) {
            createTest(currentTestResult);
        } else {
            updateTest(currentTestResult);
        }
    }

    @Override
    public List<Test> findTestsInDatesByUserRole(Date startDate, Date endDate, User user) {
        return testDao.findTestsInDatesByUserRole(startDate, endDate, user);
    }


    @Override
    public List<TestsForReportDTO> getTestsForReport(String startDate, String endDate, User user, String[] subdivs){
     return testDao.getTestsForReport(startDate, endDate, user, subdivs);
    }

    @Override
    public Map<Integer, ResultsForMonthsReportsDto> getResultsForFullBUReport(List<Integer> monthNumbers, User user){
        return testDao.getResultsForFullBUReport(monthNumbers, user);
    }

    @Override
    public Map<Integer, List<ResultsForMonthsReportsByOrganisationDto>> getResultsOfTestByOrganisation(List<Integer> monthNumbers, User user){
        return testDao.getResultsOfTestByOrganisation(monthNumbers, user);
    }

    @Override
    public Map<Integer, List<ResultsForMonthsReportsBySubdivisionDto>> getResultsOfTestBySubdivision(List<Integer> monthNumbers, User user){
        return testDao.getResultsOfTestBySubdivision(monthNumbers, user);
    }

    @Override
    public Map<Integer, List<ResultsForMonthsReportsByProfessionDto>> getResultsOfTestByProfession(List<Integer> monthNumbers, User user){
        return testDao.getResultsOfTestByProfession(monthNumbers, user);
    }

    @Override
    public List<Test> findAllTestByBusinessUnitId(int businessUnitId, String startDate, String endDate) {
        return testDao.findAllTestByBusinessUnitId(businessUnitId, startDate, endDate);
    }

    @Override
    public List<Test> findAllTestBySubdivisionId(int subdivisionId, String startDate, String endDate) {
        return testDao.findAllTestsBySubdivisionId(subdivisionId, startDate, endDate);
    }

}
