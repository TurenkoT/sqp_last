package com.severstal.sqp.dao.impl;

/**
 * User dao implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao.impl
 */

import com.severstal.sqp.dao.AbstractDao;
import com.severstal.sqp.dao.UserDao;
import com.severstal.sqp.dto.ResultsForMonthsReportsDto;
import com.severstal.sqp.entity.*;

import com.severstal.sqp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Logger;

@Repository
@Transactional
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    @Autowired
    @Qualifier("iMySQLJdbc")
    JdbcTemplate template;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager em;

    private static Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    @Transactional
    public void setUser(String login, String password, String name, String personalnumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String pass = bCryptPasswordEncoder.encode(password);
        User user = new User();
        user.setLogin(login);
        user.setPassword(pass);
        user.setFullName(name);
        user.setPersonalNumber(personalnumber);
        entityManager.persist(user);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public User findById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = null;
        try {
            entityManager.getTransaction().begin();
            user = entityManager.find(User.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return user;
    }

    @Transactional
    public User findUserByFullName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        int userId = 0;
        User user = null;
        try {
            List<User> list = template.query("SELECT U.id, U.full_name " +
                    "FROM user U", new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User user = new User();
                    user.setId(resultSet.getInt(1));
                    user.setFullName(resultSet.getString(2));
                    return user;
                }
            });
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getFullName()
                            .replaceAll(" ", "").toLowerCase()
                            .equals(name.replaceAll(" ", "").toLowerCase())) {
                        userId = list.get(i).getId();
                    }
                }
            if (userId != 0) {
                entityManager.getTransaction().begin();
                user = entityManager.find(User.class, userId);
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return user;
    }

    public User findByLogin(String login) {
        final User user = em.createQuery("select u from User u where u.login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
        return user;
    }

    @Transactional
    public User findByPersonalNumber(String personalNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = null;
        try {
            entityManager.getTransaction().begin();
            String que = "SELECT u.id FROM User u WHERE u.personalNumber='" + personalNumber + "'";
            Query query = entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return entityManager.find(User.class, list.get(0));
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return user;

    }


    @Transactional
    public void updateEditUser(User newUser) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = findById(newUser.getId());
        entityManager.getTransaction().begin();
        user.setLogin(newUser.getLogin());
        user.setPersonalNumber(newUser.getPersonalNumber());
        user.setFullName(newUser.getFullName());
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public User createNewUser(String personalNumber, String login, String password, String fullName, String DOB, Subdivision subdivision, Organisation organisation) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = new User();
        user.setPersonalNumber(personalNumber);
        user.setLogin(login);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setDOB(DOB);
        user.setOrganisation(organisation);
        user.setSubdivision(subdivision);
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return user;
    }

    @Transactional
    public List<User> findUsersByBusinessUnitId(String businessUnitId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<User> users = new ArrayList<>();
        try {
            String que = "SELECT U.id FROM User U JOIN U.organisation O where U.organisation=O.id AND O.businessUnit='" + businessUnitId + "'";
            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++) {
                    users.add(entityManager.find(User.class, list.get(i)));
                }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return users;
    }

    @Transactional
    public List<Long> getPersonalStatisticForCurrentMonth() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final User user = userService.getCurrentEmployee();
        final List<Long> result = new ArrayList<>();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT SUM(T.points) FROM Test T WHERE T.user_id=" + user.getId() + " AND MONTH(T.date) in (" + month + ")");
        result.addAll(query.getResultList());
        entityManager.flush();
        entityManager.getTransaction().commit();
        return result;
    }

    @Transactional
    public List<Long> getPersonalIntervalTimeStatistic() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final User user = userService.getCurrentEmployee();
        final List<Long> result = new ArrayList<>();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT SUM(T.points) FROM Test T JOIN time_settings TS WHERE T.user_id=" + user.getId() + " AND T.date >= TS.date AND TS.business_unit_id=" + user.getSubdivision().getOrganisation().getBusinessUnit().getId());
        result.addAll(query.getResultList());
        entityManager.flush();
        entityManager.getTransaction().commit();
        return result;
    }

    @Transactional
    public Map<Long, Double> getTopTenUsersByPointsInBusinessUnit(User user) {
        final Map<Long, Double> resultMap = new HashMap<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {

            List<Object[]> list = entityManager.createNativeQuery("SELECT DISTINCT U.id, SUM(T.points) AS POINTS " +
                    "FROM test T JOIN time_settings TS JOIN User U On T.user_id = U.id JOIN subdivision S On U.subdivision_id = S.id " +
                    "WHERE T.date >= TS.date AND S.business_unit_id=:businessUnitId AND TS.business_unit_id=:businessUnitId " +
                    "GROUP BY U.id " +
                    "ORDER BY POINTS DESC LIMIT 10")
                    .setParameter("businessUnitId", user.getSubdivision().getOrganisation().getBusinessUnit().getId())
                    .getResultList();
            if(!list.isEmpty())
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i)[1].toString() != null) {
                    resultMap.put((long) Integer.parseInt(list.get(i)[0].toString()),Double.parseDouble(list.get(i)[1].toString()));
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
