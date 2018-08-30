package com.severstal.sqp.service;

/**
 * User service interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service
 */

import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.Question;
import com.severstal.sqp.entity.Subdivision;
import com.severstal.sqp.entity.Test;
import com.severstal.sqp.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    void newUser(String login, String password, String name, String personalnumber);

    User findUserByLogin(String login);

    User findById(int id);

    User findByPersonalNumber(String personalNumber);

    User findUserByFullName(String name);

    void addUser(User user);

    void updateEditUser(User user);

    User createNewUser(String personalNumber, String login, String password, String fullName, String DOB, Subdivision subdivision, Organisation organisation);

    List<User> findUsersByBusinessUnitId(String businessUnitId);

    User getCurrentUser();

    User getCurrentEmployee();

    void setCurrentEmployee(User user);

    Question getCurrentTestQuestion();

    void setCurrentTestQuestion(Question question);

    Test getCurrentTestResult();

    void setCurrentTestResult(Test result);

    void resetCurrentSessionObjects();

    void acceptLogin(String personalNumber);

    List<Long> getPersonalStatisticForCurrentMonth();

    List<Long> getPersonalIntervalTimeStatistic();

    Map<Long, Double> getTopTenUsersByPointsInBusinessUnit(User user);
}
