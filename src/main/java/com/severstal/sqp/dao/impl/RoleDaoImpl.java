package com.severstal.sqp.dao.impl;

/**
 * Role implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao.impl
 */

import com.severstal.sqp.dao.AbstractDao;
import com.severstal.sqp.dao.RoleDao;
import com.severstal.sqp.entity.Role;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements RoleDao {

    @Autowired
    @Qualifier("iMySQLJdbc")
    JdbcTemplate template;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public Role findById(int id) {
        return getByKey(id);
    }

    @Transactional
    public void populateUserToRole(int userId, int userRoleId) {
        String que = "INSERT INTO user_to_role(user_id, user_role_id) VALUE (" + userId + "," + userRoleId + ")";
        template.update(que);
    }

    @Transactional
    public void updateUserToRole(int userId, int userRoleId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "UPDATE user_to_role SET user_role_id=? WHERE user_id=?";

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(que)
                .setParameter(1, userRoleId)
                .setParameter(2, userId).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public Role findByType(String type) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("type", type));
        return (Role) crit.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Role> findAll() {
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("type"));
        return (List<Role>) crit.list();
    }


    @Transactional
    public void setRolesForUsers(String[] usersIds, Role role) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "UPDATE user_to_role SET user_role_id=? WHERE user_id=?";
        for (String id : usersIds) {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(que)
                    .setParameter(1, role.getId())
                    .setParameter(2, id).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
        entityManager.close();
    }

}
