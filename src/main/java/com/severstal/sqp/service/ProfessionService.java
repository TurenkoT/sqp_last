package com.severstal.sqp.service;

/**
 * Statistic service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Profession;
import com.severstal.sqp.entity.Subdivision;

import java.util.List;

public interface ProfessionService {

    List<Integer> getProfessionsByUserPersonalNumber(String personalNumber);

    List<Profession> getProfessionsByName(String name);

    Profession getProfessionByName(String name);

    Profession createNewProfessionByName(String name, BusinessUnit businessUnit, Subdivision subdivision);

    void populateUserToProfession(int userId, int professionId);

    List<Profession> getAllProfessions();

    void populateProfessionToMainSubjects(int professionId, int subjectId);

    Profession findProfessionById(int id);

    List<Profession> findAllProfessions();

    Profession findByNameProfession(String profession);

    void updateUserToProfession(int userId, int professionId);

    Integer checkPopulationProfessionToSubdivision(int professionId, int subdivisionId);

    Integer checkPopulationProfessionToOrganisation(int professionId, int organisationId);

    List<Profession> findProfessionsByBusinessUnitId(int businessUnitId);

    void deleteChosenRelation(String[] strings);

    void populateProfessionsToSubjects(String[] profs,String[] subjs);

    List<Profession> findProfessionsBySubdivision(String profName,int subdivisionId);

    void populateProfessionToSubdivision(int professionId, int subdivisionId);

    List<Profession> findProfessionsByIds(final List<Integer> ids);
}
