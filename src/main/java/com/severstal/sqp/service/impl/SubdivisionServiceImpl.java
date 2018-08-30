package com.severstal.sqp.service.impl;

/**
 * Subdivision service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.dao.SubdivisionDao;
import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.service.SubdivisionService;
import com.severstal.sqp.entity.Subdivision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubdivisionServiceImpl implements SubdivisionService
{

    @Autowired
    private SubdivisionDao subdivisionDao;

    public Integer getSubdivisionByUserPersonalNumber(String personalNumber) {
        return subdivisionDao.getSubdivisionByUserPersonalNumber(personalNumber);
    }

    public Subdivision createNewSubdivisionByName(String name, BusinessUnit businessUnit, Organisation organisation){
        return subdivisionDao.createNewSubdivisionByName(name, businessUnit, organisation);
    }

    public List<Subdivision> getSubdivisionsByName(String name){
        return subdivisionDao.getSubdivisionsByName(name);
    }

    public void populateProfessionToSubdivision(int professionId, int subdivisionId){
        subdivisionDao.populateProfessionToSubdivision(professionId, subdivisionId);
    }

    public Subdivision getSubdivisionByName(String name){
        return subdivisionDao.getSubdivisionByName(name);
    }

    @Transactional
    public List<Subdivision> getAllSubdivisions(){
        return subdivisionDao.getAllSubdivisions();
    }

    public void populateSubdivisionToOrganisation(int subdivisionId, int organisationId){
        subdivisionDao.populateSubdivisionToOrganisation(subdivisionId, organisationId);
    }

    public void populateUserToSubdivision(int userId, int subdivisionId){
        subdivisionDao.populateUserToSubdivision(userId, subdivisionId);
    }

    @Transactional
    public Subdivision findSubdivisionById(int subdivisionId){
        return subdivisionDao.findSubdivisionById(subdivisionId);
    }

    @Transactional
    public List<Subdivision> findAllSubdivisions() {
        return subdivisionDao.findAllSubdivisions();
    }

    @Override
    public Subdivision findByNameSubdivision(String nameSubdivision)
    {
        return subdivisionDao.findByNameSubdivision(nameSubdivision);
    }

    @Override
    public void updateUserToSubdivision(final int userId, final int subdivisionId)
    {
        subdivisionDao.updateUserToSubdivision(userId, subdivisionId);
    }

    @Transactional
    public Integer checkSubdivisionToOrganisation(int subdivisionId, int organisationId){
        return subdivisionDao.checkSubdivisionToOrganisation(subdivisionId, organisationId);
    }

    @Transactional
    public List<Subdivision> findSubdivisionByBusinessUnitId(String businessUnitId){
        return subdivisionDao.findSubdivisionByBusinessUnitId(businessUnitId);
    }

    @Override
    public List<Subdivision> findSubdivisionsByIds(final List<Integer> ids)
    {
        return subdivisionDao.findSubdivisionsByIds(ids);
    }

    @Override
    public List<Subdivision> findSubdivisionByOrganisationId(String organisationId){
        return subdivisionDao.findSubdivisionByOrganisationId(organisationId);
    }
}
