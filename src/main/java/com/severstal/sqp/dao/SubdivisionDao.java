package com.severstal.sqp.dao;

/**
 * Statistic interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao
 */

import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.Subdivision;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SubdivisionDao {

    Integer getSubdivisionByUserPersonalNumber(String personalNumber);

    Subdivision createNewSubdivisionByName(String name, BusinessUnit businessUnit, Organisation organisation);

    List<Subdivision> getSubdivisionsByName(String name);

    void populateProfessionToSubdivision(int professionId, int subdivisionId);

    Subdivision getSubdivisionByName(String name);

    List<Subdivision> getAllSubdivisions();

    void populateSubdivisionToOrganisation(int subdivisionId, int organisationId);

    Subdivision findSubdivisionById(int subdivisionId);

    List<Subdivision> findAllSubdivisions();

    Subdivision findByNameSubdivision(String nameSubdivision);

    void populateUserToSubdivision(int userId, int subdivisionId);

    void updateUserToSubdivision(int userId, int subdivisionId);

    Integer checkSubdivisionToOrganisation(int subdivisionId, int organisationId);

    List<Subdivision> findSubdivisionByBusinessUnitId(String businessUnitId);

    List<Subdivision> findSubdivisionsByIds(List<Integer> subdivisionIds);

    List<Subdivision> findSubdivisionByOrganisationId(String organisationId);
}
