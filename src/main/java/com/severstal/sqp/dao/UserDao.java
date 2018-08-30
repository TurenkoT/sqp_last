package com.severstal.sqp.dao;

/**
 * User interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao
 */

import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.Subdivision;
import com.severstal.sqp.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public interface UserDao {

    void setUser(String login, String password, String name, String personalnumber);

    User findByLogin(String login);

    User findById(int id);

    User findByPersonalNumber(String personalNumber);

    User findUserByFullName(String name);

    void updateEditUser(User newUser);

    void addUser(User user);

    User createNewUser(String personalNumber, String login, String password, String fullName, String DOB, Subdivision subdivision, Organisation organisation);

    List<User> findUsersByBusinessUnitId(String businessUnitId);

    List<Long> getPersonalStatisticForCurrentMonth();

    List<Long> getPersonalIntervalTimeStatistic();

    Map<Long, Double> getTopTenUsersByPointsInBusinessUnit(User user);
}
