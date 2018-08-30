package com.severstal.sqp.dao.impl;

/**
 * Time settings dao implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import com.severstal.sqp.dao.TimeSettingsDao;
import com.severstal.sqp.dto.TimeSettingsDto;
import com.severstal.sqp.entity.TimeSettings;
import com.severstal.sqp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


@Repository
@Transactional
public class TimeSettingsDaoImpl implements TimeSettingsDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Transactional
    public List<TimeSettings> getTimeSettingsForUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("FROM TimeSettings T " +
                    "WHERE T.businessUnit.id = :businessUnitId", TimeSettings.class)
                    .setParameter("businessUnitId", user.getSubdivision().getOrganisation().getBusinessUnit().getId())
                    .getResultList();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    @Transactional
    public TimeSettings getTimeSettingsById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TimeSettings timeSettings = null;
        try {
            entityManager.getTransaction().begin();
            timeSettings = entityManager.find(TimeSettings.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.close();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return timeSettings;
    }

    @Transactional
    public void updateTimeSetting(TimeSettingsDto timeSettingsDto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TimeSettings timeSettings = getTimeSettingsById(timeSettingsDto.getId());
        entityManager.getTransaction().begin();
        timeSettings.setName(timeSettingsDto.getName());
        timeSettings.setDate(timeSettingsDto.getDate());
        entityManager.merge(timeSettings);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
