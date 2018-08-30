package com.severstal.sqp.service.impl;

/**
 * User service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.dao.UserDao;
import com.severstal.sqp.entity.*;
import com.severstal.sqp.service.QuestionService;
import com.severstal.sqp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Stack;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    UserDao userDao;

    @Autowired
    private Stack<User> currentEmployeeContainer;

    @Autowired
    private Stack<Question> currentTestQuestionContainer;

    @Autowired
    private Stack<Test> currentTestResultContainer;

    public User findById(int id) {
        return userDao.findById(id);
    }

    public void updateEditUser(User user) {
        userDao.updateEditUser(user);
    }

    public User findUserByLogin(String login) {
        User user = userDao.findByLogin(login);
        return user;
    }

    public void newUser(String login, String password, String name, String personalnumber) {
        userDao.setUser(login, password, name, personalnumber);
    }

    public User findUserByFullName(String name) {
        return userDao.findUserByFullName(name);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User findByPersonalNumber(String personalNumber) {
        return userDao.findByPersonalNumber(personalNumber);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.findByLogin(login);
        logger.info("User : {}", user);
        if (user == null) {
            logger.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }

        return user;
    }

    @Transactional
    public User createNewUser(String personalNumber, String login, String password, String fullName, String DOB,
                              Subdivision subdivision, Organisation organisation) {
        return userDao.createNewUser(personalNumber, login, password, fullName, DOB, subdivision, organisation);
    }

    @Transactional
    public List<User> findUsersByBusinessUnitId(String businessUnitId) {
        return userDao.findUsersByBusinessUnitId(businessUnitId);
    }

    @Override
    public User getCurrentUser() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }

        return null;
    }

    @Override
    public User getCurrentEmployee() {
        if (!currentEmployeeContainer.empty()) {
            return currentEmployeeContainer.peek();
        }

        return null;
    }

    @Override
    public void setCurrentEmployee(User user) {
        if (!currentEmployeeContainer.empty()) {
            currentEmployeeContainer.pop();
        }
        currentEmployeeContainer.push(user);
    }

    @Override
    public Question getCurrentTestQuestion() {
        if (!currentTestQuestionContainer.empty())
            return currentTestQuestionContainer.peek();

        return null;
    }

    @Override
    public void setCurrentTestQuestion(final Question question) {
        if (!currentTestQuestionContainer.empty())
            currentTestQuestionContainer.pop();

        currentTestQuestionContainer.push(question);
    }

    @Override
    public Test getCurrentTestResult() {
        if (!currentTestResultContainer.empty()) {
            return currentTestResultContainer.peek();
        }

        return null;
    }

    @Override
    public void setCurrentTestResult(Test result) {
        if (!currentTestResultContainer.empty()) {
            currentTestResultContainer.pop();
        }

        currentTestResultContainer.push(result);
    }

    @Override
    public void resetCurrentSessionObjects() {
        setCurrentTestResult(null);
        setCurrentTestQuestion(null);
        setCurrentEmployee(null);

    }

    @Override
    public void acceptLogin(String personalNumber) {
        User user = findByPersonalNumber(personalNumber);
        setCurrentEmployee(user);

    }

    @Override
    public List<Long> getPersonalStatisticForCurrentMonth() {
        return userDao.getPersonalStatisticForCurrentMonth();
    }

    @Override
    public List<Long> getPersonalIntervalTimeStatistic() {
        return userDao.getPersonalIntervalTimeStatistic();
    }

    @Override
    public Map<Long, Double> getTopTenUsersByPointsInBusinessUnit(User user){
        return userDao.getTopTenUsersByPointsInBusinessUnit(user);
    }
}
