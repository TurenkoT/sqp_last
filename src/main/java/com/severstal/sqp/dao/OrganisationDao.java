package com.severstal.sqp.dao;

/**
 * Organisation dao.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.BusinessUnit;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrganisationDao {

    List<Organisation> getOrganisationsByName(String name);

    Organisation createNewOrganisationByName(String name, BusinessUnit businessUnit);

//    void populateOrganisationToProfession(int professionId, int organisationId);

    void populateUserToOrganisation(int userId, int organisationId);

    Organisation getOrganisationByName(String name);

    List<Organisation> getAllOrganisations();

    Organisation findOrganisationById(int organisationId);

    List<BusinessUnit> getAllBusinessUnits();

    BusinessUnit getBusinessUnitByName(String name);

    BusinessUnit createNewBusinessUnitByName(String name);

    List<Organisation> getAllOrganisationsByBusinessUnitId(String businessUnitId);

    BusinessUnit findBusinessUnitById(Integer id);
}
