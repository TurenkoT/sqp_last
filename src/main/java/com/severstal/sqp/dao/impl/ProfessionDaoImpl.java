package com.severstal.sqp.dao.impl;

/**
 * Statistic interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.severstal.sqp.dao.AbstractDao;
import com.severstal.sqp.dao.ProfessionDao;
import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Profession;
import com.severstal.sqp.entity.Subdivision;

@Repository
@Transactional
public class ProfessionDaoImpl extends AbstractDao<Integer, Profession> implements ProfessionDao {

    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Autowired
    @Qualifier("iMySQLJdbc")
    private JdbcTemplate template;

    private static Logger logger = Logger.getLogger(ProfessionDaoImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Integer> getProfessionsByUserPersonalNumber(String personalNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Integer> professions = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery("SELECT DISTINCT P.id FROM Profession P JOIN User U ON U.personal_number=" + personalNumber + " JOIN user_to_profession UP ON UP.user_id=U.id AND P.id=UP.profession_id");
            professions.addAll(query.getResultList());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return professions.isEmpty() ? null : professions;
    }

    @Transactional
    public List<Profession> getProfessionsByName(String name) {
        return template.query("SELECT P.id, P.name " +
                        "FROM profession P WHERE P.name='" + name + "'",
                new RowMapper<Profession>() {
                    @Override
                    public Profession mapRow(ResultSet resultSet, int i) throws SQLException {
                        Profession profession = new Profession();
                        profession.setId(resultSet.getInt(1));
                        profession.setName(resultSet.getString(2));
                        return profession;
                    }
                });
    }

    @Transactional
    public Profession getProfessionByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Profession profession = null;
        try {
            String que = "SELECT P.id FROM Profession P where P.name='" + name + "'";
            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createQuery(que, Integer.class);
            List list = query.getResultList();
            profession = entityManager.find(Profession.class, list.get(0));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return profession;
    }

    @Transactional
    public Profession createNewProfessionByName(String name, BusinessUnit businessUnit, Subdivision subdivision) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Profession profession = new Profession();
        profession.setName(name);
        entityManager.getTransaction().begin();
        entityManager.persist(profession);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return profession;
    }


    @Transactional
    public Integer checkPopulationProfessionToSubdivision(int professionId, int subdivisionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Profession> list = new ArrayList<Profession>();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT PS.id FROM profession_to_subdivision PS WHERE PS.profession_id=" + professionId + " AND PS.subdivision_id=" + subdivisionId);
        list.addAll(query.getResultList());
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return list.isEmpty() ? 0 : 1;
    }

    @Transactional
    public Integer checkPopulationProfessionToOrganisation(int professionId, int organisationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Profession> list = new ArrayList<Profession>();
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("SELECT P.id, P.name FROM profession P JOIN profession_to_organisation PO ON P.id = PO.profession_id AND PO.profession_id=" + professionId + " AND PO.organisation_id=" + organisationId);
        list.addAll(query.getResultList());
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return list.isEmpty() ? 0 : 1;
    }

    @Transactional
    public void populateUserToProfession(int userId, int professionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "INSERT INTO user_to_profession (user_id, profession_id) VALUE (?,?)";
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(que)
                .setParameter(1, userId)
                .setParameter(2, professionId).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public List<Profession> getAllProfessions() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Profession> list = new ArrayList<Profession>();
        entityManager.getTransaction().begin();
        TypedQuery<Profession> query = entityManager.createQuery("FROM Profession", Profession.class);
        list.addAll(query.getResultList());
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return list;
    }

    @Transactional
    public void populateProfessionToMainSubjects(int professionId, int subjectId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "INSERT INTO profession_to_subject (profession_id, subject_id)  VALUE (?,?)";
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(que)
                .setParameter(1, professionId)
                .setParameter(2, subjectId).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public Profession findProfessionById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Profession profession = null;
        try {
            entityManager.getTransaction().begin();
            profession = entityManager.find(Profession.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return profession;
    }

    public List<Profession> findAllProfessions() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Profession> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Profession> query = entityManager.createQuery("FROM Profession", Profession.class);
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
    public Profession findByNameProfession(String profession) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Profession> professions = new ArrayList<>();
        try {
            String que = "SELECT P.id FROM Profession P WHERE P.name='"+profession+"'";
            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createNativeQuery(que);
            List list = query.getResultList();
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++) {
                    professions.add(entityManager.find(Profession.class, list.get(i)));
                }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return professions.isEmpty() ? null : professions.get(0);
    }

    @Transactional
    public void updateUserToProfession(int userId, int professionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "UPDATE user_to_profession SET profession_id=? WHERE user_id=?";

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(que)
                .setParameter(1, professionId)
                .setParameter(2, userId).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Transactional
    public List<Profession> findProfessionsByBusinessUnitId(int businessUnitId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Profession> professions = new ArrayList<>();
        try {
            String queryStr =
                    "SELECT DISTINCT P.id FROM Profession P JOIN profession_to_subdivision PS  JOIN subdivision S WHERE P.id=PS" +
                            ".profession_id AND S.id=PS.subdivision_id AND S.business_unit_id=" +
                            businessUnitId;

            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createNativeQuery(queryStr);
            List list = query.getResultList();
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++) {
                    professions.add(entityManager.find(Profession.class, list.get(i)));
                }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return professions;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteChosenRelation(String[] strings) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "DELETE SS FROM subject_to_subdivprof SS JOIN profession_to_subdivision PS ON SS.profsubdiv_id = PS.id WHERE PS.profession_id=? AND SS.subject_id=?";
        String spliter = ":";
        String[] lines;
        for (String str : strings) {
            lines = str.split(spliter);
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(que)
                    .setParameter(1, lines[0])
                    .setParameter(2, lines[1]).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
        entityManager.close();
    }

    @Transactional
    public void populateProfessionsToSubjects(String[] profs, String[] subjs) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "INSERT INTO subject_to_subdivprof (subject_id, profsubdiv_id)" +
                "  VALUES (?, (SELECT PS.id FROM profession_to_subdivision PS WHERE PS.profession_id=? AND PS.subdivision_id=?))";
        String spliter = ":";
        String[] profIds;
        String[] subdivIds;
        for (String str : profs) {
            profIds = str.split(spliter);
            for (int i = 0; i < subjs.length; i++) {
                entityManager.getTransaction().begin();
                entityManager.createNativeQuery(que)
                        .setParameter(1, subjs[i])
                        .setParameter(2, profIds[0])
                        .setParameter(3, profIds[1]).executeUpdate();
                entityManager.flush();
                entityManager.getTransaction().commit();
            }
        }
        entityManager.close();
    }

    @Transactional
    public List<Profession> findProfessionsBySubdivision(String profName, int subdivisionId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Profession> professions = new ArrayList<>();
        try {
            String que = "SELECT P.id FROM Profession P JOIN profession_to_subdivision PS  JOIN subdivision S WHERE P.id=PS.profession_id AND S.id="+subdivisionId+" AND P.name='"+profName+"'";
            entityManager.getTransaction().begin();
            Query query =
                    entityManager.createNativeQuery(que);
            List list = query.getResultList();
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++) {
                    professions.add(entityManager.find(Profession.class, list.get(i)));
                }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.close();
        } finally {
            entityManager.close();
        }
        return professions;
    }

    @Transactional
    public void populateProfessionToSubdivision(int professionId, int subdivisionId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String que = "INSERT INTO profession_to_subdivision (profession_id, subdivision_id) VALUE (?,?)";
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(que)
                .setParameter(1, professionId)
                .setParameter(2, subdivisionId).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Profession> findProfessionsByIds(final List<Integer> ids)
    {
        final List<Profession> professions = em.createQuery("select p from Profession p where p.id in :ids", Profession.class)
                            .setParameter("ids", ids)
                            .getResultList();
        return professions;
    }

}
