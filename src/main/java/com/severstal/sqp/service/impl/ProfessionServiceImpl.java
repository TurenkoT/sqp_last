package com.severstal.sqp.service.impl;

/**
 * Statistic service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.dao.ProfessionDao;
import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.service.ProfessionService;
import com.severstal.sqp.entity.Profession;
import com.severstal.sqp.entity.Subdivision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessionServiceImpl implements ProfessionService
{

    @Autowired
    private ProfessionDao professionDao;

    public List<Integer> getProfessionsByUserPersonalNumber(String personalNumber) {
        return professionDao.getProfessionsByUserPersonalNumber(personalNumber);
    }

//    public List<Integer> getProfessionsBySubdivisionAndUserPersonalNumber(int subdivisionId, String personalNumber){
//        return professionDao.getProfessionsBySubdivisionAndUserPersonalNumber(subdivisionId, personalNumber);
//    }

    public List<Profession> getProfessionsByName(String name){
        return professionDao.getProfessionsByName(name);
    }

    public Profession getProfessionByName(String name){
        return professionDao.getProfessionByName(name);
    }

    public Profession createNewProfessionByName(String name, BusinessUnit businessUnit, Subdivision subdivision){
        return professionDao.createNewProfessionByName(name, businessUnit, subdivision);
    }

    @Transactional
    public List<Profession> getAllProfessions(){
        return professionDao.getAllProfessions();
    }

    public void populateProfessionToMainSubjects(int professionId, int subjectId){
        professionDao.populateProfessionToMainSubjects(professionId, subjectId);
    }

    public Profession findProfessionById(int id){
        return professionDao.findProfessionById(id);
    }


    @Transactional
    public List<Profession> findAllProfessions() {
        return professionDao.findAllProfessions();
    }

    @Override
    public void populateUserToProfession(final int userId, final int professionId)
    {
        professionDao.populateUserToProfession(userId, professionId);
    }

    @Override
    public Profession findByNameProfession(final String profession)
    {
        return professionDao.findByNameProfession(profession);
    }

    @Transactional
    public void updateUserToProfession(final int userId, final int professionId) {
        professionDao.updateUserToProfession(userId, professionId);
    }

    @Transactional
    public Integer checkPopulationProfessionToSubdivision(int professionId, int subdivisionId){
        return professionDao.checkPopulationProfessionToSubdivision(professionId, subdivisionId);
    }

    @Transactional
    public Integer checkPopulationProfessionToOrganisation(int professionId, int organisationId){
        return professionDao.checkPopulationProfessionToOrganisation(professionId, organisationId);
    }

    @Transactional
    public List<Profession> findProfessionsByBusinessUnitId(int businessUnitId) {
        return professionDao.findProfessionsByBusinessUnitId(businessUnitId);
    }

    @Transactional
    public void deleteChosenRelation(String[] strings){
        professionDao.deleteChosenRelation(strings);
    }

    @Transactional
    public void populateProfessionsToSubjects(String[] profs,String[] subjs){
        professionDao.populateProfessionsToSubjects(profs, subjs);
    }

    @Transactional
    public List<Profession> findProfessionsBySubdivision(String profName,int subdivisionId){
        return professionDao.findProfessionsBySubdivision(profName,subdivisionId);
    }

    @Transactional
    public void populateProfessionToSubdivision(int professionId, int subdivisionId){
        professionDao.populateProfessionToSubdivision(professionId, subdivisionId);
    }

    @Override
    public List<Profession> findProfessionsByIds(final List<Integer> ids)
    {
        return professionDao.findProfessionsByIds(ids);
    }
}
