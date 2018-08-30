package com.severstal.sqp.service;

/**
 * Organisation service.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Organisation;

import java.util.List;

public interface OrganisationService {

    List<Organisation> getOrganisationsByName(String name);

    Organisation createNewOrganisationByName(String name, BusinessUnit businessUnit);

//    void populateOrganisationToProfession(int professionId, int organisationId);

    void populateUserToOrganisation(int userId, int professionId);

    Organisation getOrganisationByName(String name);

    List<Organisation> getAllOrganisations();

    Organisation findOrganisationById(int organisationId);

    List<BusinessUnit> getAllBusinessUnits();

    BusinessUnit getBusinessUnitByName(String name);

    BusinessUnit createNewBusinessUnitByName(String name);

    List<Organisation> getAllOrganisationsByBusinessUnitId(String businessUnitId);

    BusinessUnit findBusinessUnitById(Integer id);
}
