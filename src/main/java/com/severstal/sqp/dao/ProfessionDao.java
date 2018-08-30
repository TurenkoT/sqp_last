package com.severstal.sqp.dao;

/**
 * Statistic interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao
 */

import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Profession;
import com.severstal.sqp.entity.Subdivision;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProfessionDao {

    List<Integer> getProfessionsByUserPersonalNumber(String personalNumber);

    List<Profession> findAllProfessions();

    Profession findByNameProfession(String profession);

    void updateUserToProfession(int userId, int professionId);

    List<Profession> getProfessionsByName(String name);

    Profession getProfessionByName(String name);

    Profession createNewProfessionByName(String name, BusinessUnit businessUnit, Subdivision subdivision);

    void populateUserToProfession(int userId, int professionId);

    List<Profession> getAllProfessions();

    void populateProfessionToMainSubjects(int professionId, int subjectId);

    Profession findProfessionById(int id);

    Integer checkPopulationProfessionToSubdivision(int professionId, int subdivisionId);

    Integer checkPopulationProfessionToOrganisation(int professionId, int organisationId);

    List<Profession> findProfessionsByBusinessUnitId(int businessUnitId);

    void deleteChosenRelation(String[] strings);

    void populateProfessionsToSubjects(String[] profs,String[] subjs);

    List<Profession> findProfessionsBySubdivision(String profName,int subdivisionId);

    void populateProfessionToSubdivision(int professionId, int subdivisionId);

    List<Profession> findProfessionsByIds(List<Integer> ids);
}
