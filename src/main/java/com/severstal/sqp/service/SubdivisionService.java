package com.severstal.sqp.service;

import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.Subdivision;

import java.util.List;

/**
 * Statistic service interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service
 */

public interface SubdivisionService {

    Integer getSubdivisionByUserPersonalNumber(String personalNumber);

    Subdivision createNewSubdivisionByName(String name, BusinessUnit businessUnit, Organisation organisation);

    List<Subdivision> getSubdivisionsByName(String name);

    void populateProfessionToSubdivision(int professionId, int subdivisionId);

    Subdivision getSubdivisionByName(String name);

    List<Subdivision> getAllSubdivisions();

    void populateSubdivisionToOrganisation(int subdivisionId, int organisationId);

    void populateUserToSubdivision(int userId, int subdivisionId);

    Subdivision findSubdivisionById(int subdivisionId);

    List<Subdivision> findAllSubdivisions();

    Subdivision findByNameSubdivision(String nameSubdivision);

    void updateUserToSubdivision(int userId, int subdivisionId);

    Integer checkSubdivisionToOrganisation(int subdivisionId, int organisationId);

    List<Subdivision> findSubdivisionByBusinessUnitId(String businessUnitId);

    List<Subdivision> findSubdivisionsByIds(List<Integer> subdivisionIds);

    List<Subdivision> findSubdivisionByOrganisationId(String organisationId);
}
