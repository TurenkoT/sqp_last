package com.severstal.sqp.dao.impl;

import com.severstal.sqp.dao.UploadDao;
import com.severstal.sqp.entity.*;
import com.severstal.sqp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Upload dao implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao.impl
 */

@Repository
@Transactional
public class UploadDaoImpl implements UploadDao {

    @Autowired
    QuestionService questionService;
    @Autowired
    EntityManagerFactory entityManagerFactory;
    @Autowired
    @Qualifier("iMySQLJdbc")
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ProfessionService professionService;
    @Autowired
    SubdivisionService subdivisionService;
    @Autowired
    OrganisationService organisationService;
    @Autowired
    private RoleService roleService;

    private static Logger logger = Logger.getLogger(UploadDaoImpl.class.getName());

    @Transactional
    public Map<Integer, List<String>> parseCSV(String filename) {
        String line = "";
        String csvSpliter = ";";
        Map<Integer, List<String>> rows = new HashMap<>();
        int i = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] lines = line.split(csvSpliter);
                List<String> strings = new ArrayList<>();
                for (String row : lines) {
                    if (row.equals("")) {
                        strings.add("null");
                    } else {
                        strings.add(row);
                    }
                }
                rows.put(i, strings);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    @Transactional
    public List<String> saveCSV(Map<Integer, List<String>> map) {
        List<String> messages = new ArrayList<>();
        int count = 0;
        List<Question> questions = questionService.findAllQuestions();
        outer:
        for (Map.Entry entry : map.entrySet()) {
            int subjectId = 0;
            int businessUnitId = 0;
            int questionId = 0;
            Integer key = (Integer) entry.getKey();
            List<String> list = (List<String>) entry.getValue();
            if (key == 0) {
                logger.log(Level.INFO, "Initialisation naming of cells");
            } else {
                int column = 1;
                Question question = null;
                Subject subject;
                TypeQuestion typeQuestion = null;
                BusinessUnit businessUnit = null;
                for (String item : list) {
                    if (column == 1) {
                        if (item.equals("null")) {
                            logger.log(Level.SEVERE, "Business unit is empty so that can't be download. Line:# " + (key + 1));
                            continue outer;
                        } else {
                            businessUnit = organisationService.getBusinessUnitByName(item);
                            if (businessUnit == null) {
                                businessUnit = organisationService.createNewBusinessUnitByName(item);
                                logger.log(Level.INFO, "A new business unit has been created.");
                                businessUnitId = businessUnit.getId();
                            } else {
                                businessUnitId = businessUnit.getId();
                            }
                            column++;
                        }
                    } else if (column == 2) {
                        if (item.equals("null")) {
                            logger.log(Level.SEVERE, "The question doesn't got subject so that can't be download. Line:# " + (key + 1));
                            continue outer;
                        } else {
                            if (!questionService.findSubjectByBusinessUnitId(String.valueOf(businessUnitId)).isEmpty())
                                for (int k = 0; k < questionService.findSubjectByBusinessUnitId(String.valueOf(businessUnitId)).size(); k++) {
                                    if (item.replaceAll(" ", "").toLowerCase().equals(questionService.findSubjectByBusinessUnitId(String.valueOf(businessUnitId)).get(k).getName().replaceAll(" ", "").toLowerCase())) {
                                        subjectId = questionService.findSubjectByBusinessUnitId(String.valueOf(businessUnitId)).get(k).getId();
                                    }
                                }
                            if (subjectId == 0) {
                                subject = questionService.createNewSubjectByNameAndBusinessUnit(item, businessUnit);
                                subjectId = subject.getId();
                                logger.log(Level.INFO, "Subject has been downloaded. Line:# " + (key + 1));
                            } else {
                                logger.log(Level.WARNING, "This subject has already been so that a new one won't be created. Line:# " + (key + 1));
                            }
                            column++;
                        }
                    } else if (column == 3) {
                        if (item.equals("null")) {
                            logger.log(Level.WARNING, "The question don't have type. Line:# " + (key + 1));
                        } else {
                            typeQuestion = questionService.findTypeQuestionByArticle(item);
                        }
                        column++;
                    } else if (column == 4) {
                        if (item.equals("null")) {
                            logger.log(Level.INFO, "Sequence of right answers is not defined. Line:# " + (key + 1));
                        }
                        column++;
                    } else if (column == 5) {
                        if (item.equals("null")) {
                            logger.log(Level.SEVERE, "The empty question can't be downloaded. Line:# " + (key + 1));
                            continue outer;
                        } else if (typeQuestion != null) {
                            if (!questionService.findQuestionsBySubjectId(subjectId).isEmpty())
                                for (int k = 0; k < questionService.findQuestionsBySubjectId(subjectId).size(); k++) {
                                    if (item.toLowerCase().equals(questionService.findQuestionsBySubjectId(subjectId).get(k).getText().toLowerCase())) {
                                        questionId = questions.get(k).getId();
                                    }
                                }
                            if (questionId != 0) {
                                logger.log(Level.SEVERE, "This question is already been. Line:# " + (key + 1));
                                continue outer;
                            } else {
                                question = questionService.createNewQuestionByValue(item, subjectId, typeQuestion.getArticle());
                                questionId = question.getId();
                            }
                        }
                        column++;
                    } else if (column > 5) {
                        if (item.equals("null")) {
                            logger.log(Level.SEVERE, "The empty answer can't be downloaded. Line:# " + (key + 1));
                        } else {
                            Answer answer = questionService.createNewAnswer(item, question);
                            logger.log(Level.INFO, "A new one answer added for question from line:# " + (key + 1));
                        }
                        column++;
                    }
                }
                logger.log(Level.INFO, "All main data is added for a new question. Line:#" + (key + 1));
                questions = questionService.findAllQuestions();
                count++;
                logger.log(Level.INFO, "Count increase. Line:#" + (key + 1));
            }
        }
        logger.log(Level.INFO, count + " questions has been added successful.");
        messages.add(count + " вопроса было добавлено");
        return messages;
    }

    @Transactional
    public List<String> saveUsersCSV(Map<Integer, List<String>> map) {

        List<String> messages = new ArrayList<>();
        int userRoleId = roleService.findByType("USER").getId();
        int count = 0;
        outer:
        for (Map.Entry entry : map.entrySet()) {
            int businessUnitId = 0;
            int professionId = 0;
            int subdivisionId = 0;
            int organisationId = 0;
            int proftosubdiv = 0;
            String personalNumber = "";
            String login = "";
            String password = "";
            String DOB = "";
            Integer key = (Integer) entry.getKey();
            List<String> list = (List<String>) entry.getValue();
            if (key == 0) {
                logger.log(Level.INFO, "Initialisation naming of cells");
            } else {
                int column = 1;
                BusinessUnit businessUnit = null;
                Profession profession = null;
                Subdivision subdivision = null;
                Organisation organisation = null;
                StringBuilder stringBuilderFullName = new StringBuilder();
                User user = null;
                User createdUser = null;
                StringBuilder stringBuilderProfessionName = new StringBuilder();
                StringBuilder stringBuilderSubdivisionName = new StringBuilder();
                for (String item : list) {
                    if (column == 1) {
                        if (item.equals("null")) {
                            logger.log(Level.SEVERE, "Business unit is empty so that can't be download. Line:# " + (key + 1));
                            continue outer;
                        } else {
                            businessUnit = organisationService.getBusinessUnitByName(item);
                            if (businessUnit == null) {
                                businessUnit = organisationService.createNewBusinessUnitByName(item);
                                logger.log(Level.INFO, "A new business unit has been created.");
                                businessUnitId = businessUnit.getId();
                            } else {
                                businessUnitId = businessUnit.getId();
                            }
                            column++;
                        }
                    } else if (column == 2) {
                        if (item.equals("null")) {
                            logger.log(Level.SEVERE, "The user doesn't got personal number so that can't be download. Line:# " + (key + 1));
                            continue outer;
                        } else {
                            user = userService.findByPersonalNumber(item);
                            if (user == null) {
                                logger.log(Level.INFO, "User has been created. Line:# " + (key + 1));
                                personalNumber = item;
                                login = item;
                                password = bCryptPasswordEncoder.encode(item);
                                logger.log(Level.INFO, "Personal number has been set to a new user. Line:# " + (key + 1));
                            } else {
                                logger.log(Level.WARNING, "User with this personal number has already been so that a new one won't be created. Line:# " + (key + 1));
                                continue outer;
                            }
                            column++;
                        }
                    } else if (column == 3) {
                        if (item.equals("null")) {
                            logger.log(Level.WARNING, "The user  don't have name. Line:# " + (key + 1));
                        } else {
                            stringBuilderFullName.append(item).append(" ");
                        }
                        column++;
                    } else if (column == 4) {
                        if (item.equals("null")) {
                            logger.log(Level.WARNING, "The user  don't have second name. Line:# " + (key + 1));
                        } else {
                            stringBuilderFullName.append(item).append(" ");
                        }
                        column++;
                    } else if (column == 5) {
                        if (item.equals("null")) {
                            logger.log(Level.WARNING, "The user  don't have patronymic. Line:# " + (key + 1));
                        } else {
                            stringBuilderFullName.append(item);
                            logger.log(Level.INFO, "Full name for user is set. Line:# " + (key + 1));
                        }
                        column++;
                    } else if (column == 6) {
                        if (item.equals("null")) {
                            logger.log(Level.SEVERE, "Profession for the new user is not defined. So user would not be added. Line:# " + (key + 1));
                            continue outer;
                        } else {
                            stringBuilderProfessionName.append(item);
                            logger.log(Level.INFO, "Name for a new profession has been created. Line:# " + (key + 1));
                        }
                        column++;
                    } else if (column == 7) {
                        if (item.equals("null")) {
                            logger.log(Level.SEVERE, "New user can't be created without subdivision. Line:# " + (key + 1));
                            continue outer;
                        } else {
                            stringBuilderSubdivisionName.append(item);
                            logger.log(Level.INFO, "New subdivision name has been save. Line:# " + (key + 1));
                        }
                        column++;
                    } else if (column == 8) {
                        if (item.equals("null")) {
                            logger.log(Level.SEVERE, "User's DoB is empty. Line:# " + (key + 1));
                        } else {
                            DOB = item;
                            logger.log(Level.INFO, "User's dob has been set. Line:# " + (key + 1));
                        }
                        column++;
                    } else if (column == 9) {
                        if (item.equals("null")) {
                            logger.log(Level.SEVERE, "User's organisation is empty. Creating canceled. Line:# " + (key + 1));
                            continue outer;
                        } else {
                            if (!organisationService.getAllOrganisationsByBusinessUnitId(String.valueOf(businessUnitId)).isEmpty())
                                for (Organisation org : organisationService.getAllOrganisationsByBusinessUnitId(String.valueOf(businessUnitId))) {
                                    if (item.replaceAll(" ", "").toLowerCase().equals(org.getName().replaceAll(" ", "").toLowerCase())) {
                                        organisationId = org.getId();
                                    }
                                }
                            if (organisationId == 0) {
                                organisation = organisationService.createNewOrganisationByName(item, businessUnit);
                                organisationId = organisation.getId();
                                logger.log(Level.INFO, "Organisation id has been defined. Line:# " + (key + 1));
                                logger.log(Level.INFO, "Organisation has been created. Line:# " + (key + 1));
                            } else {
                                organisation = organisationService.findOrganisationById(organisationId);
                                logger.log(Level.INFO, "Organisation with this name is already defined. Line:# " + (key + 1));
                            }
                            if (!subdivisionService.findSubdivisionByOrganisationId(String.valueOf(organisationId)).isEmpty())
                                for (Subdivision sub : subdivisionService.findSubdivisionByOrganisationId(String.valueOf(organisationId))) {
                                    if (stringBuilderSubdivisionName.toString().replaceAll(" ", "").toLowerCase().equals(sub.getName().replaceAll(" ", "").toLowerCase())) {
                                        subdivisionId = sub.getId();
                                    }
                                }
                            if (subdivisionId == 0) {
                                subdivision = subdivisionService.createNewSubdivisionByName(stringBuilderSubdivisionName.toString(), businessUnit, organisation);
                                logger.log(Level.INFO, "New subdivision has been created. Line:# " + (key + 1));
                                subdivisionId = subdivision.getId();
                                logger.log(Level.INFO, "Subdivision id has been defined. Line:# " + (key + 1));
                            } else {
                                subdivision = subdivisionService.findSubdivisionById(subdivisionId);
                                logger.log(Level.WARNING, "Subdivision with this name is already defined. Line:# " + (key + 1));
                            }
                            if (professionService.findByNameProfession(stringBuilderProfessionName.toString())==null) {
                                profession = professionService.createNewProfessionByName(stringBuilderProfessionName.toString(), businessUnit, subdivision);
                                professionId = profession.getId();
                            } else {
                                profession = (professionService.findProfessionsBySubdivision(stringBuilderProfessionName.toString(), subdivisionId).get(0));
                                professionId = profession.getId();
                            }
                        }
                        column++;
                    }
                }
                if (column == 10) {
                    try {
                        createdUser = userService.createNewUser(personalNumber, login, password, stringBuilderFullName.toString(), DOB, subdivision, organisation);
                        logger.log(Level.INFO, "User has been persisted. Line:# " + (key + 1));
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "User hasn't been persisted. Line:# " + (key + 1));
                    }
                    proftosubdiv = professionService.checkPopulationProfessionToSubdivision(professionId, subdivision.getId());
                    roleService.populateUserToRole(createdUser.getId(), userRoleId);
                    logger.log(Level.INFO, "Population for user and role has been defined. Line:# " + (key + 1));
                    professionService.populateUserToProfession(createdUser.getId(), professionId);
                    logger.log(Level.INFO, "Population for user and profession has been defined. Line:# " + (key + 1));
                    if(proftosubdiv == 0){
                        professionService.populateProfessionToSubdivision(professionId, subdivision.getId());
                    }

                }
                logger.log(Level.INFO, "All main data is added for a new user. Line:#" + (key + 1));
                count++;
            }
        }
        logger.log(Level.INFO, count + " users has been added successful.");
        messages.add(count + " пользователей было добавлено");

        return messages;
    }
}
