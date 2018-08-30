package com.severstal.sqp.service.impl;

/**
 * Organisation service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.dao.OrganisationDao;
import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.service.OrganisationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrganisationServiceImpl implements OrganisationService {

    @Autowired
    OrganisationDao organisationDao;

    public List<Organisation> getOrganisationsByName(String name) {
        return organisationDao.getOrganisationsByName(name);
    }

    public Organisation createNewOrganisationByName(String name, BusinessUnit businessUnit) {
        return organisationDao.createNewOrganisationByName(name, businessUnit);
    }

    public void populateUserToOrganisation(int userId, int professionId) {
        organisationDao.populateUserToOrganisation(userId, professionId);
    }

    public Organisation getOrganisationByName(String name){
        return organisationDao.getOrganisationByName(name);
    }

    public List<Organisation> getAllOrganisations(){
        return organisationDao.getAllOrganisations();
    }

    public Organisation findOrganisationById(int organisationId){
        return organisationDao.findOrganisationById(organisationId);
    }

    public List<BusinessUnit> getAllBusinessUnits(){
        return organisationDao.getAllBusinessUnits();
    }

    public BusinessUnit getBusinessUnitByName(String name){
        return organisationDao.getBusinessUnitByName(name);
    }

    public BusinessUnit createNewBusinessUnitByName(String name) {
        return organisationDao.createNewBusinessUnitByName(name);
    }

    public List<Organisation> getAllOrganisationsByBusinessUnitId(String businessUnitId){
        return organisationDao.getAllOrganisationsByBusinessUnitId(businessUnitId);
    }

    @Override
    public BusinessUnit findBusinessUnitById(final Integer id)
    {
        return organisationDao.findBusinessUnitById(id);
    }
}
