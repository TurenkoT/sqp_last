package com.severstal.sqp.controller;

/**
 * Report controller
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.config
 */

import com.severstal.sqp.dto.*;
import com.severstal.sqp.entity.BusinessUnit;
import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.Profession;
import com.severstal.sqp.entity.User;
import com.severstal.sqp.service.OrganisationService;
import com.severstal.sqp.service.ReportService;
import com.severstal.sqp.service.TestService;
import com.severstal.sqp.service.UserService;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class ReportController {

    @Autowired
    private TestService testService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReportService reportService;

    private static Logger logger = Logger.getLogger(ReportController.class.getName());
    private static final String noResults = "нет результатов";
    private static final String testStatusYes = "да";
    private static final String testStatusNo = "нет";
    private static final String headerCommunityInfo = "Общая информация о тестировании в подразделении";
    private static final String headerNameOfParameter = "Показатель";
    private static final String headerResultsOfTestsByOrganisations = "Результаты тестирования по предприятиям";
    private static final String headerResultsOfTestsBySubdivisions = "Результаты тестирования по цехам/участкам";
    private static final String headerResultsOfTestsByProfessions = "Результаты тестирования по профессиям и должностям (в цехе/на участке)";
    private static final String[] nameMappingCountFullBe = {"RowName", "countJanuary", "countFebruary", "countMarch", "countApril", "countMay", "countJune", "countJuly", "countAugust", "countSeptember", "countOctober", "countNovember", "countDecember"};
    private static final String[] nameMappingPercent = {"RowName", "percentRightJanuary", "percentRightFebruary", "percentRightMarch", "percentRightApril", "percentRightMay", "percentRightJune", "percentRightJuly", "percentRightAugust", "percentRightSeptember", "percentRightOctober", "percentRightNovember", "percentRightDecember"};
    private static final String[] nameMappingResultsOfTestByOrganisation = {"organisationName", "countJanuary", "countFebruary", "countMarch", "countApril", "countMay", "countJune", "countJuly", "countAugust", "countSeptember", "countOctober", "countNovember", "countDecember",
            "countRightJanuary", "countRightFebruary", "countRightMarch", "countRightApril", "countRightMay", "countRightJune", "countRightJuly", "countRightAugust", "countRightSeptember", "countRightOctober", "countRightNovember", "countRightDecember",
            "percentRightJanuary", "percentRightFebruary", "percentRightMarch", "percentRightApril", "percentRightMay", "percentRightJune", "percentRightJuly", "percentRightAugust", "percentRightSeptember", "percentRightOctober", "percentRightNovember", "percentRightDecember"};
    private static final String[] nameMappingResultsOfTestBySubdivision = {"organisationName", "subdivisionName", "countJanuary", "countFebruary", "countMarch", "countApril", "countMay", "countJune", "countJuly", "countAugust", "countSeptember", "countOctober", "countNovember", "countDecember",
            "countRightJanuary", "countRightFebruary", "countRightMarch", "countRightApril", "countRightMay", "countRightJune", "countRightJuly", "countRightAugust", "countRightSeptember", "countRightOctober", "countRightNovember", "countRightDecember",
            "percentRightJanuary", "percentRightFebruary", "percentRightMarch", "percentRightApril", "percentRightMay", "percentRightJune", "percentRightJuly", "percentRightAugust", "percentRightSeptember", "percentRightOctober", "percentRightNovember", "percentRightDecember"};
    private static final String[] nameMappingResultsOfTestByProfession = {"professionName", "countJanuary", "countFebruary", "countMarch", "countApril", "countMay", "countJune", "countJuly", "countAugust", "countSeptember", "countOctober", "countNovember", "countDecember",
            "countRightJanuary", "countRightFebruary", "countRightMarch", "countRightApril", "countRightMay", "countRightJune", "countRightJuly", "countRightAugust", "countRightSeptember", "countRightOctober", "countRightNovember", "countRightDecember",
            "percentRightJanuary", "percentRightFebruary", "percentRightMarch", "percentRightApril", "percentRightMay", "percentRightJune", "percentRightJuly", "percentRightAugust", "percentRightSeptember", "percentRightOctober", "percentRightNovember", "percentRightDecember"};
    private static final String[] header = {"№ п/п", "Предприятие", "Цех/участок", "Профессия/должность", "ФИО", "Табельный №", "Тест пройден(да/нет)", "Всего вопросов", "Правильные ответы", "Неправильные ответы", "Дата тестирования"};
    private static final String[] nameMapping = {"Count", "Organisation", "Subdivision", "Profession", "fullName", "personalNumber", "testStatus", "TotalCountOfQuestion", "TotalCountOfRightAnswer", "TotalCountOfFalseAnswer", "Date"};

    @ModelAttribute
    public void addCurrentUser(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
    }


    @GetMapping("/report")
    public ModelAndView getReportPage() {
        ModelAndView modelAndView = new ModelAndView("reports");
        final BusinessUnit currentBusinessUnit = organisationService.findBusinessUnitById(userService.getCurrentUser().getSubdivision()
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
        return modelAndView;
    }

    @PostMapping("/report/download")
    @ResponseStatus(value = HttpStatus.OK)
    public void getReport(@RequestBody ReportParametersDTO reportParametersDTO, HttpServletResponse response) throws IOException {
        User user = userService.getCurrentUser();

        String startDate = reportParametersDTO.getStartDate();
        String endDate = reportParametersDTO.getEndDate();

        String csvFileName = "report.csv";
        response.setContentType("text/csv;charset=windows-1251");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

        csvWriter.writeHeader(header);

        final List<TestsForReportDTO> testList = testService.getTestsForReport(startDate, endDate, user, reportParametersDTO.getSubdivs());
        int count = 0;
        if (testList.isEmpty()) {
            final ReportCsvDTO emptyReportCsv = createNoDataReportCsvDto();
            csvWriter.write(emptyReportCsv, nameMapping);
            csvWriter.close();
            logger.log(Level.SEVERE, "No test for this parameters");
        } else {
            for (TestsForReportDTO test : testList) {
                final ReportCsvDTO reportCsvDTO = new ReportCsvDTO();
                StringBuilder professionsBuilder = new StringBuilder();

                for (Profession profession : test.getUser().getProfessions())
                    professionsBuilder.append(profession.getName()).append(" ");
                count++;
                reportCsvDTO.setCount(count);
                reportCsvDTO.setOrganisation(test.getUser().getOrganisation().getName());
                reportCsvDTO.setSubdivision(test.getUser().getSubdivision().getName());
                reportCsvDTO.setProfession(professionsBuilder.toString());
                reportCsvDTO.setFullName(test.getUser().getFullName());
                reportCsvDTO.setPersonalNumber(test.getUser().getPersonalNumber());
                if (test.getTotalCountOfRight() > 0) {
                    reportCsvDTO.setTestStatus(testStatusYes);
                } else {
                    reportCsvDTO.setTestStatus(testStatusNo);
                }
                reportCsvDTO.setTotalCountOfQuestion(test.getTotalCountOfQuestions());
                reportCsvDTO.setTotalCountOfRightAnswer(test.getTotalCountOfRight());
                reportCsvDTO.setTotalCountOfFalseAnswer(test.getTotalCountOfFalse());
                reportCsvDTO.setDate(test.getDate());
                csvWriter.write(reportCsvDTO, nameMapping);
            }
            csvWriter.close();
            logger.log(Level.INFO, "Report has been unload for user with personal number#: " + user.getPersonalNumber());
        }
    }

    @PostMapping("/report/download/full_be")
    @ResponseStatus(value = HttpStatus.OK)
    public void getFullBEReport(@RequestBody OptionalReportsDto optionalReportsDto, HttpServletResponse response) throws IOException {
        User user = userService.getCurrentUser();

        String startDate = optionalReportsDto.getStartMonth();
        String endDate = optionalReportsDto.getEndMonth();

        String csvFileName = "report.csv";
        response.setContentType("text/csv;charset=windows-1251");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

        final List<String> header = new ArrayList<>();
        final List<String> countQuestionsHeader = new ArrayList<>();
        final List<String> countRightHeader = new ArrayList<>();
        final List<String> percentRigthHeader = new ArrayList<>();
        final List<String> organisationHeader = new ArrayList<>();
        final List<Integer> monthNumbers = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : reportService.getNamesOfChoosenMonths(startDate, endDate).entrySet()) {
            monthNumbers.add(entry.getKey());
            header.add(entry.getValue());
            countQuestionsHeader.add(entry.getValue() + " Всего вопросов");
            countRightHeader.add(entry.getValue() + " Правильных");
            percentRigthHeader.add(entry.getValue() + " Процент правильных");
        }
        header.add(0, headerNameOfParameter);
        organisationHeader.addAll(countQuestionsHeader);
        organisationHeader.addAll(countRightHeader);
        organisationHeader.addAll(percentRigthHeader);
        organisationHeader.add(0, "Наименование предприятия");
        final String[] finalHeader = new String[header.size()];
        final String[] finalOrganisationHeader = new String[organisationHeader.size()];

        //First table
        csvWriter.writeHeader(headerCommunityInfo);
        csvWriter.writeHeader(header.toArray(finalHeader));
        final Map<Integer, ResultsForMonthsReportsDto> resultsMap = testService.getResultsForFullBUReport(monthNumbers, user);
        final MonthsIndicatorsForReportDto monthsIndicatorsForReportDto = reportService.getMonthsIndicators(resultsMap);
        final PercentIndicatorsForReportDto percentIndicatorsForReportDto = reportService.getPercentIndicators(resultsMap);
        csvWriter.write(monthsIndicatorsForReportDto, reportService.deleteNullCells(nameMappingCountFullBe, monthsIndicatorsForReportDto));
        csvWriter.write(percentIndicatorsForReportDto, reportService.deleteNullCells(nameMappingPercent, percentIndicatorsForReportDto));

        //Second table
        csvWriter.writeHeader(headerResultsOfTestsByOrganisations);
        csvWriter.writeHeader(organisationHeader.toArray(finalOrganisationHeader));
        final Map<Integer, List<ResultsForMonthsReportsByOrganisationDto>> result = testService.getResultsOfTestByOrganisation(monthNumbers, user);
        final Map<Integer, ResultsOfTestByOrganisationDto> finalResultRow = reportService.getFinalResultRows(result, monthNumbers);
        for (Map.Entry<Integer, ResultsOfTestByOrganisationDto> entry : finalResultRow.entrySet()) {
            csvWriter.write(entry.getValue(), reportService.deleteNullCellsFromResultsOfTests(nameMappingResultsOfTestByOrganisation, entry.getValue()));
        }
        csvWriter.close();
        logger.log(Level.INFO, "Report has been unload for user with personal number#: " + user.getPersonalNumber());
    }

    @PostMapping("/report/download/subdivision")
    @ResponseStatus(value = HttpStatus.OK)
    public void getSubdivisionReport(@RequestBody OptionalReportsDto optionalReportsDto, HttpServletResponse response) throws IOException {
        User user = userService.getCurrentUser();

        String startDate = optionalReportsDto.getStartMonth();
        String endDate = optionalReportsDto.getEndMonth();

        String csvFileName = "report.csv";
        response.setContentType("text/csv;charset=windows-1251");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
        final List<String> countQuestionsHeader = new ArrayList<>();
        final List<String> countRightHeader = new ArrayList<>();
        final List<String> percentRigthHeader = new ArrayList<>();
        final List<String> subdivisionHeader = new ArrayList<>();
        final List<Integer> monthNumbers = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : reportService.getNamesOfChoosenMonths(startDate, endDate).entrySet()) {
            monthNumbers.add(entry.getKey());
            countQuestionsHeader.add(entry.getValue() + " Всего вопросов");
            countRightHeader.add(entry.getValue() + " Правильных");
            percentRigthHeader.add(entry.getValue() + " Процент правильных");
        }
        subdivisionHeader.addAll(countQuestionsHeader);
        subdivisionHeader.addAll(countRightHeader);
        subdivisionHeader.addAll(percentRigthHeader);
        subdivisionHeader.add(0, "Наименование цеха/участка");
        subdivisionHeader.add(0, "Наименование предприятия");
        final String[] finalOrganisationHeader = new String[subdivisionHeader.size()];

        csvWriter.writeHeader(headerResultsOfTestsBySubdivisions);
        csvWriter.writeHeader(subdivisionHeader.toArray(finalOrganisationHeader));
        final Map<Integer, List<ResultsForMonthsReportsBySubdivisionDto>> result = testService.getResultsOfTestBySubdivision(monthNumbers, user);
        final Map<Integer, ResultsOfTestBySubdivisionDto> finalResultRow = reportService.getFinalSubdivisinResultRows(result, monthNumbers);
        for (Map.Entry<Integer, ResultsOfTestBySubdivisionDto> entry : finalResultRow.entrySet()) {
            csvWriter.write(entry.getValue(), reportService.deleteNullCellsFromResultsOfTests(nameMappingResultsOfTestBySubdivision, entry.getValue()));
        }
        csvWriter.close();
        logger.log(Level.INFO, "Report has been unload for user with personal number#: " + user.getPersonalNumber());
    }

    @PostMapping("/report/download/profession")
    @ResponseStatus(value = HttpStatus.OK)
    public void getProfessionReport(@RequestBody OptionalReportsDto optionalReportsDto, HttpServletResponse response) throws IOException {
        User user = userService.getCurrentUser();

        String startDate = optionalReportsDto.getStartMonth();
        String endDate = optionalReportsDto.getEndMonth();

        String csvFileName = "report.csv";
        response.setContentType("text/csv;charset=windows-1251");

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
        final List<String> countQuestionsHeader = new ArrayList<>();
        final List<String> countRightHeader = new ArrayList<>();
        final List<String> percentRigthHeader = new ArrayList<>();
        final List<String> subdivisionHeader = new ArrayList<>();
        final List<Integer> monthNumbers = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : reportService.getNamesOfChoosenMonths(startDate, endDate).entrySet()) {
            monthNumbers.add(entry.getKey());
            countQuestionsHeader.add(entry.getValue() + " Всего вопросов");
            countRightHeader.add(entry.getValue() + " Правильных");
            percentRigthHeader.add(entry.getValue() + " Процент правильных");
        }
        subdivisionHeader.addAll(countQuestionsHeader);
        subdivisionHeader.addAll(countRightHeader);
        subdivisionHeader.addAll(percentRigthHeader);
        subdivisionHeader.add(0, "Наименование професии/должности");
        final String[] finalProfessionsHeader = new String[subdivisionHeader.size()];

        csvWriter.writeHeader(headerResultsOfTestsByProfessions);
        csvWriter.writeHeader(subdivisionHeader.toArray(finalProfessionsHeader));
        final Map<Integer, List<ResultsForMonthsReportsByProfessionDto>> result = testService.getResultsOfTestByProfession(monthNumbers, user);
        final Map<Integer, ResultsOfTestByProfessionDto> finalResultRow = reportService.getFinalProfessionResultRows(result, monthNumbers);
        for (Map.Entry<Integer, ResultsOfTestByProfessionDto> entry : finalResultRow.entrySet()) {
            csvWriter.write(entry.getValue(), reportService.deleteNullCellsFromResultsOfTests(nameMappingResultsOfTestByProfession, entry.getValue()));
        }
        csvWriter.close();
        logger.log(Level.INFO, "Report has been unload for user with personal number#: " + user.getPersonalNumber());
    }

//TODO: commented controllers which would be released for another types of reports
//    @PostMapping("/report/download/employee")
//    @ResponseStatus(value = HttpStatus.OK)
//    public void getEmployeeReport(@RequestBody OptionalReportsDto optionalReportsDto, HttpServletResponse response) throws IOException {
//        User user = userService.getCurrentUser();
//
//        String startDate = optionalReportsDto.getStartMonth();
//        String endDate = optionalReportsDto.getEndMonth();
//
//        String csvFileName = "report.csv";
//        response.setContentType("text/csv;charset=windows-1251");
//
//        String headerKey = "Content-Disposition";
//        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
//        response.setHeader(headerKey, headerValue);
//
//        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
//    }
//
//    @PostMapping("/report/download/subject")
//    @ResponseStatus(value = HttpStatus.OK)
//    public void getSubjectReport(@RequestBody OptionalReportsDto optionalReportsDto, HttpServletResponse response) throws IOException {
//        User user = userService.getCurrentUser();
//
//        String startDate = optionalReportsDto.getStartMonth();
//        String endDate = optionalReportsDto.getEndMonth();
//
//        String csvFileName = "report.csv";
//        response.setContentType("text/csv;charset=windows-1251");
//
//        String headerKey = "Content-Disposition";
//        String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
//        response.setHeader(headerKey, headerValue);
//
//        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
//    }


    private ReportCsvDTO createNoDataReportCsvDto() {
        ReportCsvDTO emptyReportCsv = new ReportCsvDTO();
        emptyReportCsv.setCount(1);
        emptyReportCsv.setOrganisation(noResults);
        emptyReportCsv.setSubdivision(noResults);
        emptyReportCsv.setProfession(noResults);
        emptyReportCsv.setFullName(noResults);
        emptyReportCsv.setPersonalNumber(noResults);
        emptyReportCsv.setTestStatus(noResults);
        emptyReportCsv.setTotalCountOfQuestion(0);
        emptyReportCsv.setTotalCountOfRightAnswer(0);
        emptyReportCsv.setTotalCountOfFalseAnswer(0);
        emptyReportCsv.setDate(new Date());
        return emptyReportCsv;
    }


}
