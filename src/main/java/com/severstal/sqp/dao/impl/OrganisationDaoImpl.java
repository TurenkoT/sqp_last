package com.severstal.sqp.dao.impl;

/**
 * Statistic service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.severstal.sqp.dao.OrganisationDao;
import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.User;

@Repository
@Transactional
public class OrganisationDaoImpl implements OrganisationDao {

    @Autowired
    @Qualifier("iMySQLJdbc")
    JdbcTemplate template;
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    EntityManager em;

    private static Logger logger = Logger.getLogger(OrganisationDaoImpl.class.getName());

    @Transactional
    public List<Organisation> getOrganisationsByName(String name) {
        logger.log(Level.INFO, "Execute query from getOrgnisationsByName()");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Organisation> organisations = new ArrayList<>();
        try {
            String que = "SELECT O.id FROM Organisation O where O.name='" + name + "'";
            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            if(!list.isEmpty())
                for(int i =0; i<list.size(); i++) {
                    organisations.add(entityManager.find(Organisation.class, list.get(i)));
                }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return organisations;
    }

    @Transactional
    public Organisation createNewOrganisationByName(String name, BusinessUnit businessUnit) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Organisation organisation = new Organisation();
        organisation.setName(name);
        organisation.setBusinessUnit(businessUnit);
        entityManager.getTransaction().begin();
        entityManager.persist(organisation);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return organisation;
    }

//    @Transactional
//    public void populateOrganisationToProfession(int professionId, int organisationId) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        String que = "INSERT INTO profession_to_organisation (profession_id, organisation_id)  VALUE (?,?)";
//        entityManager.getTransaction().begin();
//        entityManager.createNativeQuery(que)
//                .setParameter(1, professionId)
//                .setParameter(2, organisationId).executeUpdate();
//        entityManager.flush();
//        entityManager.getTransaction().commit();
//        entityManager.close();
//    }


    @Transactional
    public void populateUserToOrganisation(int userId, int organisationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = entityManager.find(User.class, userId);
        Organisation organisation = entityManager.find(Organisation.class, organisationId);
        entityManager.getTransaction().begin();
        user.setOrganisation(organisation);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public Organisation getOrganisationByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Organisation organisation = null;
        try {
            entityManager.getTransaction().begin();
            String que = "SELECT O.id FROM Organisation O where O.name='" + name + "'";
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            organisation = entityManager.find(Organisation.class, list.get(0));
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return organisation;
    }

    @Transactional
    public List<Organisation> getAllOrganisations() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Organisation> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Organisation> query = entityManager.createQuery("FROM Organisation", Organisation.class);
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
    public Organisation findOrganisationById(int organisationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Organisation organisation = null;
        try {
            entityManager.getTransaction().begin();
            organisation = entityManager.find(Organisation.class, organisationId);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return organisation;
    }

    @Transactional
    public List<BusinessUnit> getAllBusinessUnits(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<BusinessUnit> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<BusinessUnit> query = entityManager.createQuery("FROM BusinessUnit", BusinessUnit.class);
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
    public BusinessUnit getBusinessUnitByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        BusinessUnit businessUnit = null;
        try {
            entityManager.getTransaction().begin();
            String que = "SELECT B.id FROM BusinessUnit B where B.name='" + name + "'";
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            if(!list.isEmpty())
            businessUnit = entityManager.find(BusinessUnit.class, list.get(0));
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return businessUnit;
    }

    @Transactional
    public  BusinessUnit createNewBusinessUnitByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setName(name);
        entityManager.getTransaction().begin();
        entityManager.persist(businessUnit);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return businessUnit;
    }

    @Transactional
    public List<Organisation> getAllOrganisationsByBusinessUnitId(String businessUnitId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Organisation> organisations = new ArrayList<>();
        try {
            String que = "SELECT O.id FROM Organisation O where O.businessUnit='" + businessUnitId + "'";
            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            if(!list.isEmpty())
            for(int i =0; i<list.size(); i++) {
                organisations.add(entityManager.find(Organisation.class, list.get(i)));
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return organisations;
    }

    @Override
    public BusinessUnit findBusinessUnitById(final Integer id)
    {
        return em.find(BusinessUnit.class, id);
    }
}
