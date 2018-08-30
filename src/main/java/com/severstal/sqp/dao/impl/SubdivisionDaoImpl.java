package com.severstal.sqp.dao.impl;

/**
 * Statistic service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.dao.AbstractDao;
import com.severstal.sqp.dao.SubdivisionDao;
import com.severstal.sqp.dao.UserDao;
import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.Profession;
import com.severstal.sqp.entity.Subdivision;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class SubdivisionDaoImpl extends AbstractDao<Integer, Subdivision> implements SubdivisionDao {
    @Autowired
    private UserDao userDao;
    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Autowired
    @Qualifier("iMySQLJdbc")
    private JdbcTemplate template;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Subdivision createNewSubdivisionByName(String name, BusinessUnit businessUnit, Organisation organisation) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Subdivision subdivision = new Subdivision();
        subdivision.setName(name);
        subdivision.setBusinessUnit(businessUnit);
        subdivision.setOrganisation(organisation);
        entityManager.getTransaction().begin();
        entityManager.persist(subdivision);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return subdivision;
    }

    @Transactional
    public List<Subdivision> getSubdivisionsByName(String name) {
        return template.query("SELECT S.id, S.name " +
                "FROM Subdivision S WHERE S.name='" + name + "'", new RowMapper<Subdivision>() {
            @Override
            public Subdivision mapRow(ResultSet resultSet, int i) throws SQLException {
                Subdivision subdivision = new Subdivision();
                subdivision.setId(resultSet.getInt(1));
                subdivision.setName(resultSet.getString(2));
                return subdivision;
            }
        });
    }

    @Transactional
    public void populateProfessionToSubdivision(int professionId, int subdivisionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "UPDATE profession SET subdivision_id = ? WHERE id = ?";
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(que)
                .setParameter(1, subdivisionId)
                .setParameter(2, professionId).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public Integer checkSubdivisionToOrganisation(int subdivisionId, int organisationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Profession> list = new ArrayList<Profession>();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT S.id, S.name FROM subdivision S JOIN subdivision_to_organisation SO ON S.id = SO.subdivision_id AND SO.subdivision_id=" + subdivisionId + " AND SO.organisation_id=" + organisationId);
        list.addAll(query.getResultList());
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return list.isEmpty() ? 0 : 1;
    }

    @Transactional
    public Integer getSubdivisionByUserPersonalNumber(String personalNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Integer> subdivisions = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery("SELECT S.id FROM Subdivision S JOIN user U ON U.personal_number=" + personalNumber + " AND S.id=U.subdivision_id");
            subdivisions.addAll(query.getResultList());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return subdivisions.isEmpty() ? null : subdivisions.get(0);
    }

    @Transactional
    public Subdivision getSubdivisionByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Subdivision subdivision = null;
        try {
            entityManager.getTransaction().begin();
            String que = "SELECT S.id FROM Subdivision S where S.name='" + name + "'";
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            subdivision = entityManager.find(Subdivision.class, list.get(0));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return subdivision;
    }

    @Transactional
    public List<Subdivision> getAllSubdivisions() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Subdivision> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Subdivision> query = entityManager.createQuery("FROM Subdivision", Subdivision.class);
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
    public void populateSubdivisionToOrganisation(int subdivisionId, int organisationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "INSERT INTO subdivision_to_organisation(organisation_id, subdivision_id) VALUE (?,?)";
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(que)
                .setParameter(1, organisationId)
                .setParameter(2, subdivisionId).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public void populateUserToSubdivision(int userId, int subdivisionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "INSERT INTO user_to_subdivision(user_id, subdivision_id)   VALUE (?,?)";
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(que)
                .setParameter(1, userId)
                .setParameter(2, subdivisionId).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public Subdivision findSubdivisionById(int subdivisionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Subdivision subdivision = null;
        try {
            entityManager.getTransaction().begin();
            subdivision = entityManager.find(Subdivision.class, subdivisionId);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return subdivision;
    }

    @Transactional
    public List<Subdivision> findAllSubdivisions() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Subdivision> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Subdivision> query = entityManager.createQuery("FROM Subdivision", Subdivision.class);
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

    public Subdivision findByNameSubdivision(String nameSubdivision) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("name", nameSubdivision));
        return (Subdivision) crit.uniqueResult();
    }

    @Transactional
    public void updateUserToSubdivision(int userId, int subdivisionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "UPDATE user_to_subdivision SET subdivision_id=? WHERE user_id=?";

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(que)
                .setParameter(1, subdivisionId)
                .setParameter(2, userId).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public List<Subdivision> findSubdivisionByBusinessUnitId(String businessUnitId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Subdivision> subdivisions = new ArrayList<>();
        try {
            String que = "SELECT S.id FROM Subdivision S where S.businessUnit='" + businessUnitId + "'";
            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++) {
                    subdivisions.add(entityManager.find(Subdivision.class, list.get(i)));
                }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return subdivisions;
    }

    @Transactional
    public List<Subdivision> findSubdivisionByOrganisationId(String organisationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Subdivision> subdivisions = new ArrayList<>();
        try {
            String que = "SELECT S.id FROM Subdivision S where S.organisation='" + organisationId + "'";
            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++) {
                    subdivisions.add(entityManager.find(Subdivision.class, list.get(i)));
                }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return subdivisions;
    }

    @Override
    public List<Subdivision> findSubdivisionsByIds(final List<Integer> ids) {
        final List<Subdivision> subdivisions = em.createQuery("select s from Subdivision s where s.id in :ids", Subdivision.class)
                .setParameter("ids", ids)
                .getResultList();
        return subdivisions;
    }
}
