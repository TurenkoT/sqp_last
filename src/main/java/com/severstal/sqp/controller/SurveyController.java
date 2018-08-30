package com.severstal.sqp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.severstal.sqp.dto.OrganisationDto;
import com.severstal.sqp.dto.ProfessionDto;
import com.severstal.sqp.dto.SubdivisionDto;
import com.severstal.sqp.dto.SurveyDto;
import com.severstal.sqp.dto.SurveyLaunchDto;
import com.severstal.sqp.dto.SurveyQuestionAnswerCsvDto;
import com.severstal.sqp.dto.SurveyQuestionAnswerDto;
import com.severstal.sqp.dto.SurveyQuestionDto;
import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.Profession;
import com.severstal.sqp.entity.Subdivision;
import com.severstal.sqp.entity.Survey;
import com.severstal.sqp.entity.SurveyLaunch;
import com.severstal.sqp.entity.SurveyLaunchResult;
import com.severstal.sqp.entity.SurveyQuestion;
import com.severstal.sqp.entity.SurveyQuestionAnswer;
import com.severstal.sqp.entity.User;
import com.severstal.sqp.service.OrganisationService;
import com.severstal.sqp.service.ProfessionService;
import com.severstal.sqp.service.SubdivisionService;
import com.severstal.sqp.service.SurveyService;
import com.severstal.sqp.service.UserService;

@Controller
public class SurveyController {
    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private ProfessionService professionService;

    @Autowired
    private SubdivisionService subdivisionService;

    @Autowired
    private ModelMapper modelMapper;

    @ModelAttribute
    public void addCurrentUser(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
    }

    private static Logger logger = Logger.getLogger(SurveyController.class.getSimpleName());

    @GetMapping(value = "/adminPanel/surveys")
    public ModelAndView getSurveysPage() {
        final ModelAndView modelAndView = new ModelAndView("survey/surveys");
        Integer businessUnitId = userService.getCurrentUser().getSubdivision().getOrganisation().getBusinessUnit().getId();
        final List<SurveyDto> allSurveyDtoList = surveyService.findSurveysByBusinessUnitId(businessUnitId)
                .stream()
                .map(e -> new SurveyDto(e))
                .collect(Collectors.toList());

        modelAndView.addObject("allSurveys", allSurveyDtoList);

        return modelAndView;
    }

    @GetMapping(value = "/adminPanel/surveys/new")
    public ModelAndView getCreatePage() {
        return new ModelAndView("survey/addSurvey").addObject("user", userService.getCurrentUser());
    }

    @GetMapping(value = "/adminPanel/surveys/edit/{surveyId}")
    public ModelAndView getEditPage(@PathVariable long surveyId) {
        final Survey survey = surveyService.findSurveyById(surveyId);
        final SurveyDto surveyDto = new SurveyDto(survey);

        final ModelAndView modelAndView = new ModelAndView("survey/editSurvey");
        modelAndView.addObject("survey", surveyDto);
        return modelAndView;
    }

    @GetMapping(value = "/adminPanel/surveys/delete/{surveyId}")
    public String deleteSurvey(@PathVariable long surveyId) {
        surveyService.deleteSurvey(surveyId);

        return "redirect:/adminPanel/surveys";
    }

    @PostMapping("/adminPanel/surveys/save")
    public String saveSurvey(@ModelAttribute SurveyDto surveyDto) {
        if (surveyDto.getId() == null) {
            userService.getCurrentUser();
            final Survey survey = modelMapper.map(surveyDto, Survey.class);

            survey.setAuthor(userService.getCurrentUser());
            survey.setCreatedDate(new Date());
            surveyService.createSurvey(survey);
        } else {
            final Survey survey = surveyService.findSurveyById(surveyDto.getId());
            survey.setName(surveyDto.getName());
            surveyService.updateSurvey(survey);
        }

        return "redirect:/adminPanel/surveys";
    }

    @PostMapping("/adminPanel/surveys/edit/{surveyId}/questions/save")
    public String saveQuestion(@ModelAttribute SurveyQuestionDto questionDto) {
        final SurveyQuestion question = modelMapper.map(questionDto, SurveyQuestion.class);

        final Survey survey = surveyService.findSurveyById(questionDto.getSurveyId());
        question.setSurvey(survey);

        User currentUser = userService.getCurrentUser();
        question.setAuthor(currentUser);

        surveyService.createSurveyQuestion(question);

        return "redirect:/adminPanel/surveys/edit/" + survey.getId();
    }

    @GetMapping("/adminPanel/surveys/edit/{surveyId}/questions/delete/{questionId}")
    public String deleteQuestion(@PathVariable long surveyId, @PathVariable long questionId) {
        surveyService.deleteSurveyQuestion(questionId);
        return "redirect:/adminPanel/surveys/edit/" + surveyId;
    }

    @GetMapping("/adminPanel/surveys/launch/{surveyId}")
    public ModelAndView getLaunchPage(@PathVariable long surveyId) {
        final User currentUser = userService.getCurrentUser();
        final ModelAndView modelAndView = new ModelAndView("survey/launchSurvey");

        // Survey
        final Survey survey = surveyService.findSurveyById(surveyId);
        modelAndView.addObject("survey", survey);

        final BusinessUnit currentBusinessUnit = organisationService.findBusinessUnitById(currentUser.getSubdivision()
                .getOrganisation()
                .getBusinessUnit()
                .getId());
        // Organisations
        final List<OrganisationDto> organisationDtoList = currentBusinessUnit.getOrganisations()
                .stream()
                .map(e -> modelMapper.map(e, OrganisationDto.class))
                .collect(Collectors.toList());
        modelAndView.addObject("organisations", organisationDtoList);

        // Subdivisions
        Map<Pair<Integer, String>, List<SubdivisionDto>> subdivisionsMap = new HashMap();
        for (Organisation organisation : currentBusinessUnit.getOrganisations()) {
            final List<SubdivisionDto> subdivisionDtoList = organisation.getSubdivisions()
                    .stream()
                    .map(e -> modelMapper.map(e, SubdivisionDto.class))
                    .collect(Collectors.toList());
            subdivisionsMap.put(Pair.of(organisation.getId(), organisation.getName()), subdivisionDtoList);
        }
        modelAndView.addObject("subdivisionsMap", subdivisionsMap);

        // Professions
        final List<ProfessionDto> professionDtoList = professionService.findProfessionsByBusinessUnitId(currentBusinessUnit.getId())
                .stream()
                .map(e -> modelMapper.map(e, ProfessionDto.class))
                .collect(Collectors.toList());
        modelAndView.addObject("professions", professionDtoList);

        return modelAndView;
    }

    @PostMapping("/adminPanel/surveys/launch/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveLaunch(@RequestBody SurveyLaunchDto surveyLaunchDto) {
        final SurveyLaunch surveyLaunch = new SurveyLaunch();
        surveyLaunch.setCreatedDate(new Date());
        surveyLaunch.setAuthor(userService.getCurrentUser());

        final Survey survey = surveyService.findSurveyById(surveyLaunchDto.getSurveyId());
        surveyLaunch.setSurvey(survey);

        final List<Profession> professions = professionService.findProfessionsByIds(surveyLaunchDto.getProfessionIds());
        surveyLaunch.setProfessions(professions);

        final List<Subdivision> subdivisions = subdivisionService.findSubdivisionsByIds(surveyLaunchDto.getSubdivisionIds());
        surveyLaunch.setSubdivisions(subdivisions);

        surveyService.createSurveyLaunch(surveyLaunch);
    }

    @GetMapping("/profile/surveys")
    public ModelAndView getProfileSurveysPage() {
        final User profileUser = userService.getCurrentEmployee();

        final List<SurveyLaunch> activeSurveysLaunches = surveyService.getAllActiveSurveysLaunchesForUser(profileUser);

        final ModelAndView modelAndView = new ModelAndView("survey/profileSurveys");
        modelAndView.addObject("activeSurveysLaunches", activeSurveysLaunches);

        return modelAndView;
    }

    @GetMapping("/profile/surveys/pass/{surveyLaunchId}")
    public ModelAndView getProfilePassSurveyPage(@PathVariable long surveyLaunchId) {
        final User profileUser = userService.getCurrentEmployee();
        final SurveyLaunch surveyLaunch = surveyService.findLaunchById(surveyLaunchId);

        final ModelAndView modelAndView = new ModelAndView("survey/passSurvey");
        modelAndView.addObject("questions", surveyLaunch.getSurvey().getQuestions());
        modelAndView.addObject("surveyLaunchId", surveyLaunch.getId());

        return modelAndView;
    }

    @PostMapping("/profile/surveys/pass/{surveyLaunchId}/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveAnswers(@RequestBody List<SurveyQuestionAnswerDto> answerDtoList, @PathVariable long surveyLaunchId) {
        final User profileUser = userService.getCurrentEmployee();

        final SurveyLaunchResult surveyLaunchResult = new SurveyLaunchResult();

        final SurveyLaunch surveyLaunch = surveyService.findLaunchById(surveyLaunchId);
        surveyLaunchResult.setSurveyLaunch(surveyLaunch);
        surveyLaunchResult.setUser(profileUser);
        surveyLaunchResult.setCreatedDate(new Date());

        final List<SurveyQuestionAnswer> answerList = new ArrayList<>();
        for (SurveyQuestionAnswerDto answerDto : answerDtoList) {
            final SurveyQuestionAnswer answer = new SurveyQuestionAnswer();
            final SurveyQuestion question = surveyService.findSurveyQuestionById(answerDto.getSurveyQuestionId());
            answer.setSurveyQuestion(question);
            answer.setSurveyLaunchResult(surveyLaunchResult);
            answer.setText(answerDto.getText());

            answerList.add(answer);
        }

        surveyLaunchResult.setSurveyQuestionAnswers(answerList);

        surveyService.createSurveyLaunchResult(surveyLaunchResult);

        logger.info("Employee with personal number: " + userService.getCurrentEmployee().getPersonalNumber() + " pass the survey");
    }

    @GetMapping("/adminPanel/surveys/{surveyId}/launches")
    public ModelAndView getSurveyLaunchesPage(@PathVariable long surveyId) {
        final List<SurveyLaunchDto> launchDtoList = surveyService.findLaunchesBySurveyId(surveyId)
                .stream()
                .map(e -> new SurveyLaunchDto(e))
                .collect(Collectors.toList());
        final String surveyName = surveyService.findSurveyById(surveyId).getName();

        ModelAndView modelAndView = new ModelAndView("survey/surveyLaunches");
        modelAndView.addObject("launches", launchDtoList);
        modelAndView.addObject("surveyName", surveyName);
        return modelAndView;
    }


    @GetMapping("/adminPanel/surveys/launches/{launchId}/downloadResults")
    public void downloadResultsCSV(HttpServletResponse response, @PathVariable long launchId) throws IOException {
        final SurveyLaunch launch = surveyService.findLaunchById(launchId);
        String csvFileName = "survey_results.csv";
        response.setContentType("text/csv; charset=Windows-1251");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
        response.setHeader(headerKey, headerValue);

        // uses the Super CSV API to generate CSV data from the model data
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

        String[] header = {
                "UserName", "SubdivisionName", "ProfessionNames", "AnswerDate", "QuestionText", "AnswerText", "OrganisationName"
        };

        for (SurveyLaunchResult result : launch.getSurveyLaunchResults()) {
            for (SurveyQuestionAnswer answer : result.getSurveyQuestionAnswers()) {
                final SurveyQuestionAnswerCsvDto surveyQuestionAnswerCsvDto = new SurveyQuestionAnswerCsvDto(answer);
                csvWriter.write(surveyQuestionAnswerCsvDto, header);
            }
        }

        csvWriter.close();
    }
}
