package com.severstal.sqp.service.impl;

/**
 * Report service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service
 */

import com.severstal.sqp.dto.*;
import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.Profession;
import com.severstal.sqp.entity.Subdivision;
import com.severstal.sqp.service.ReportService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private static final String JANUARY = "Январь";
    private static final String FEBRUARY = "Февраль";
    private static final String MARCH = "Март";
    private static final String APRIL = "Апрель";
    private static final String MAY = "Май";
    private static final String JUNE = "Июнь";
    private static final String JULY = "Июль";
    private static final String AUGUST = "Август";
    private static final String SEPTEMBER = "Сентябрь";
    private static final String OCTOBER = "Октябрь";
    private static final String NOVEMBER = "Ноябрь";
    private static final String DECEMBER = "Декабрь";
    private static final String nameMappingCountJanuary = "countJanuary";
    private static final String nameMappingCountFebruary = "countFebruary";
    private static final String nameMappingCountMarch = "countMarch";
    private static final String nameMappingCountApril = "countApril";
    private static final String nameMappingCountMay = "countMay";
    private static final String nameMappingCountJune = "countJune";
    private static final String nameMappingCountJuly = "countJuly";
    private static final String nameMappingCountAugust = "countAugust";
    private static final String nameMappingCountSeptember = "countSeptember";
    private static final String nameMappingCountOctober = "countOctober";
    private static final String nameMappingCountNovember = "countNovember";
    private static final String nameMappingCountDecember = "countDecember";
    private static final String nameMappingPercentJanuary = "percentRightJanuary";
    private static final String nameMappingPercentFebruary = "percentRightFebruary";
    private static final String nameMappingPercentMarch = "percentRightMarch";
    private static final String nameMappingPercentApril = "percentRightApril";
    private static final String nameMappingPercentMay = "percentRightMay";
    private static final String nameMappingPercentJune = "percentRightJune";
    private static final String nameMappingPercentJuly = "percentRightJuly";
    private static final String nameMappingPercentAugust = "percentRightAugust";
    private static final String nameMappingPercentSeptember = "percentRightSeptember";
    private static final String nameMappingPercentOctober = "percentRightOctober";
    private static final String nameMappingPercentNovember = "percentRightNovember";
    private static final String nameMappingPercentDecember = "percentRightDecember";
    private static final String TOTAL_COUNT_OF_QUESTIONS = "Общее количество заданных вопросов";
    private static final String PERCENT_OF_RIGHTS = "Процент правильных ответов";

    @Override
    public Map<Integer, String> getNamesOfChoosenMonths(String start, String end) {
        String startMonthNumber = start.substring(5, 7);
        String endMonthNumber = end.substring(5, 7);
        int startMonth = 0;
        int endMonth = 0;
        Map<Integer, String> monthNames = new HashMap<>();
        if (startMonthNumber.startsWith("0")) {
            startMonth = Integer.parseInt(startMonthNumber.replace("0", ""));
        } else {
            startMonth = Integer.parseInt(startMonthNumber);
        }
        if (endMonthNumber.startsWith("0")) {
            endMonth = Integer.parseInt(endMonthNumber.replace("0", ""));
        } else {
            endMonth = Integer.parseInt(endMonthNumber);
        }
        if (startMonth != 0 && endMonth != 0)
            for (int i = startMonth; i <= endMonth; i++) {
                switch (i) {
                    case 1:
                        monthNames.put(1, JANUARY);
                        break;
                    case 2:
                        monthNames.put(2, FEBRUARY);
                        break;
                    case 3:
                        monthNames.put(3, MARCH);
                        break;
                    case 4:
                        monthNames.put(4, APRIL);
                        break;
                    case 5:
                        monthNames.put(5, MAY);
                        break;
                    case 6:
                        monthNames.put(6, JUNE);
                        break;
                    case 7:
                        monthNames.put(7, JULY);
                        break;
                    case 8:
                        monthNames.put(8, AUGUST);
                        break;
                    case 9:
                        monthNames.put(9, SEPTEMBER);
                        break;
                    case 10:
                        monthNames.put(10, OCTOBER);
                        break;
                    case 11:
                        monthNames.put(11, NOVEMBER);
                        break;
                    case 12:
                        monthNames.put(12, DECEMBER);
                        break;
                }
            }
        return monthNames;
    }

    @Override
    public MonthsIndicatorsForReportDto getMonthsIndicators(Map<Integer, ResultsForMonthsReportsDto> resultsMap) {
        int deltaOfCountQuestions = 0;
        final MonthsIndicatorsForReportDto monthsIndicatorsForReportDto = new MonthsIndicatorsForReportDto();
        monthsIndicatorsForReportDto.setRowName(TOTAL_COUNT_OF_QUESTIONS);
        for (Map.Entry<Integer, ResultsForMonthsReportsDto> entry : resultsMap.entrySet()) {
            switch (entry.getKey()) {
                case 1: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountJanuary(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountJanuary(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 2: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountFebruary(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountFebruary(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 3: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountMarch(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountMarch(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 4: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountApril(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountApril(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 5: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountMay(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountMay(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 6: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountJune(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountJune(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 7: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountJuly(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountJuly(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 8: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountAugust(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountAugust(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 9: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountSeptember(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountSeptember(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 10: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountOctober(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountOctober(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 11: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountNovember(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountNovember(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
                case 12: {
                    if (deltaOfCountQuestions > entry.getValue().getTotalCountQuestions()) {
                        monthsIndicatorsForReportDto.setCountDecember(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(-" + (deltaOfCountQuestions - entry.getValue().getTotalCountQuestions()) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    } else {
                        monthsIndicatorsForReportDto.setCountDecember(String.valueOf(entry.getValue().getTotalCountQuestions()) + "(+" + (entry.getValue().getTotalCountQuestions() - deltaOfCountQuestions) + ")");
                        deltaOfCountQuestions = entry.getValue().getTotalCountQuestions();
                    }
                    break;
                }
            }
        }
        return monthsIndicatorsForReportDto;
    }

    @Override
    public PercentIndicatorsForReportDto getPercentIndicators(Map<Integer, ResultsForMonthsReportsDto> resultsMap) {
        double deltaOfPercentRights = 0;
        final PercentIndicatorsForReportDto percentIndicatorsForReportDto = new PercentIndicatorsForReportDto();
        percentIndicatorsForReportDto.setRowName(PERCENT_OF_RIGHTS);
        for (Map.Entry<Integer, ResultsForMonthsReportsDto> entry : resultsMap.entrySet()) {
            double percentRights = 0;
            switch (entry.getKey()) {
                case 1: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightJanuary(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightJanuary(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 2: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightFebruary(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightFebruary(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 3: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightMarch(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightMarch(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 4: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightApril(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightApril(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 5: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightMay(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightMay(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 6: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightJune(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightJune(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 7: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightJuly(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightJuly(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 8: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightAugust(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightAugust(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 9: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightSeptember(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightSeptember(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 10: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightOctober(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightOctober(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 11: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightNovember(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightNovember(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
                case 12: {
                    if (entry.getValue().getTotalCountQuestions() != 0) {
                        percentRights = 100 * entry.getValue().getSumRight() / (entry.getValue().getSumRight() + entry.getValue().getSumFalse());
                    }
                    if (deltaOfPercentRights > percentRights) {
                        percentIndicatorsForReportDto.setPercentRightDecember(percentRights + "%(-" + (deltaOfPercentRights - percentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    } else {
                        percentIndicatorsForReportDto.setPercentRightDecember(percentRights + "%(+" + (percentRights - deltaOfPercentRights) + "%)");
                        deltaOfPercentRights = percentRights;
                    }
                    break;
                }
            }
        }
        return percentIndicatorsForReportDto;
    }

    @Override
    public String[] deleteNullCells(String[] nameMappingCount, MonthsIndicatorsForReportDto monthsIndicatorsForReportDto) {
        for (int i = 0; i < nameMappingCount.length; i++) {
            switch (nameMappingCount[i]) {
                case nameMappingCountJanuary: {
                    if (monthsIndicatorsForReportDto.getCountJanuary() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountFebruary: {
                    if (monthsIndicatorsForReportDto.getCountFebruary() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountMarch: {
                    if (monthsIndicatorsForReportDto.getCountMarch() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountApril: {
                    if (monthsIndicatorsForReportDto.getCountApril() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountMay: {
                    if (monthsIndicatorsForReportDto.getCountMay() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountJune: {
                    if (monthsIndicatorsForReportDto.getCountJune() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountJuly: {
                    if (monthsIndicatorsForReportDto.getCountJuly() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountAugust: {
                    if (monthsIndicatorsForReportDto.getCountAugust() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountSeptember: {
                    if (monthsIndicatorsForReportDto.getCountSeptember() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountOctober: {
                    if (monthsIndicatorsForReportDto.getCountOctober() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountNovember: {
                    if (monthsIndicatorsForReportDto.getCountNovember() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingCountDecember: {
                    if (monthsIndicatorsForReportDto.getCountDecember() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
            }
        }
        return nameMappingCount;
    }

    @Override
    public String[] deleteNullCells(String[] nameMappingCount, PercentIndicatorsForReportDto percentIndicatorsForReportDto) {
        for (int i = 0; i < nameMappingCount.length; i++) {
            switch (nameMappingCount[i]) {
                case nameMappingPercentJanuary: {
                    if (percentIndicatorsForReportDto.getPercentRightJanuary() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentFebruary: {
                    if (percentIndicatorsForReportDto.getPercentRightFebruary() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentMarch: {
                    if (percentIndicatorsForReportDto.getPercentRightMarch() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentApril: {
                    if (percentIndicatorsForReportDto.getPercentRightApril() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentMay: {
                    if (percentIndicatorsForReportDto.getPercentRightMay() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentJune: {
                    if (percentIndicatorsForReportDto.getPercentRightJune() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentJuly: {
                    if (percentIndicatorsForReportDto.getPercentRightJuly() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentAugust: {
                    if (percentIndicatorsForReportDto.getPercentRightAugust() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentSeptember: {
                    if (percentIndicatorsForReportDto.getPercentRightSeptember() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentOctober: {
                    if (percentIndicatorsForReportDto.getPercentRightOctober() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentNovember: {
                    if (percentIndicatorsForReportDto.getPercentRightNovember() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
                case nameMappingPercentDecember: {
                    if (percentIndicatorsForReportDto.getPercentRightDecember() == null) {
                        nameMappingCount = ArrayUtils.remove(nameMappingCount, i);
                        i--;
                    }
                    break;
                }
            }
        }
        return nameMappingCount;
    }

    @Override
    public Map<Integer, ResultsOfTestByOrganisationDto> getFinalResultRows(Map<Integer, List<ResultsForMonthsReportsByOrganisationDto>> result, List<Integer> monthNumbers) {
        final Map<Integer, ResultsOfTestByOrganisationDto> finalResultRows = new HashMap<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //Iterate to each month from map <month - key, results - value>
        for (Map.Entry<Integer, List<ResultsForMonthsReportsByOrganisationDto>> entry : result.entrySet()) {
            switch (entry.getKey()) {
                case 1: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountJanuary(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightJanuary(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightJanuary(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 2: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountFebruary(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightFebruary(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightFebruary(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 3: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountMarch(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightMarch(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightMarch(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 4: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountApril(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightApril(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightApril(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 5: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountMay(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightMay(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightMay(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 6: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountJune(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightJune(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightJune(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 7: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountJuly(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightJuly(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightJuly(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 8: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountAugust(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightAugust(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightAugust(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 9: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountSeptember(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightSeptember(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightSeptember(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 10: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountOctober(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightOctober(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightOctober(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 11: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountNovember(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightNovember(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightNovember(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 12: {

                    //Iterate to each result in month where every row is a different organisation
                    for (ResultsForMonthsReportsByOrganisationDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getOrganisationId()))
                            finalResultRows.put(listElement.getOrganisationId(), new ResultsOfTestByOrganisationDto(monthNumbers));
                        ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto = finalResultRows.get(listElement.getOrganisationId());
                        resultsOfTestByOrganisationDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestByOrganisationDto.setCountDecember(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByOrganisationDto.setCountRightDecember(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByOrganisationDto.setPercentRightDecember(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
            }
        }
        return finalResultRows;
    }

    @Override
    public Map<Integer, ResultsOfTestBySubdivisionDto> getFinalSubdivisinResultRows(Map<Integer, List<ResultsForMonthsReportsBySubdivisionDto>> result, List<Integer> monthNumbers) {
        final Map<Integer, ResultsOfTestBySubdivisionDto> finalResultRows = new HashMap<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //Iterate to each month from map <month - key, results - value>
        for (Map.Entry<Integer, List<ResultsForMonthsReportsBySubdivisionDto>> entry : result.entrySet()) {
            switch (entry.getKey()) {
                case 1: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountJanuary(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightJanuary(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightJanuary(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 2: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountFebruary(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightFebruary(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightFebruary(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 3: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountMarch(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightMarch(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightMarch(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 4: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountApril(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightApril(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightApril(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 5: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountMay(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightMay(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightMay(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 6: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountJune(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightJune(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightJune(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 7: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountJuly(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightJuly(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightJuly(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 8: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountAugust(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightAugust(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightAugust(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 9: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountSeptember(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightSeptember(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightSeptember(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 10: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountOctober(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightOctober(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightOctober(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 11: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountNovember(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightNovember(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightNovember(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 12: {

                    //Iterate to each result in month where every row is a different subdivision
                    for (ResultsForMonthsReportsBySubdivisionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getSubdivisionId()))
                            finalResultRows.put(listElement.getSubdivisionId(), new ResultsOfTestBySubdivisionDto(monthNumbers));
                        ResultsOfTestBySubdivisionDto resultsOfTestBySubdivisionDto = finalResultRows.get(listElement.getSubdivisionId());
                        resultsOfTestBySubdivisionDto.setOrganisationName(entityManager.find(Organisation.class, listElement.getOrganisationId()).getName());
                        resultsOfTestBySubdivisionDto.setSubdivisionName(entityManager.find(Subdivision.class, listElement.getSubdivisionId()).getName());
                        resultsOfTestBySubdivisionDto.setCountDecember(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestBySubdivisionDto.setCountRightDecember(String.valueOf(listElement.getSumRight()));
                        resultsOfTestBySubdivisionDto.setPercentRightDecember(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
            }
        }
        return finalResultRows;
    }

    @Override
    public Map<Integer, ResultsOfTestByProfessionDto> getFinalProfessionResultRows(Map<Integer, List<ResultsForMonthsReportsByProfessionDto>> result, List<Integer> monthNumbers) {
        final Map<Integer, ResultsOfTestByProfessionDto> finalResultRows = new HashMap<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //Iterate to each month from map <month - key, results - value>
        for (Map.Entry<Integer, List<ResultsForMonthsReportsByProfessionDto>> entry : result.entrySet()) {
            switch (entry.getKey()) {
                case 1: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountJanuary(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightJanuary(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightJanuary(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 2: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountFebruary(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightFebruary(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightFebruary(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 3: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountMarch(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightMarch(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightMarch(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 4: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountApril(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightApril(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightApril(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 5: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountMay(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightMay(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightMay(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 6: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountJune(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightJune(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightJune(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 7: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountJuly(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightJuly(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightJuly(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 8: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountAugust(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightAugust(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightAugust(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 9: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountSeptember(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightSeptember(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightSeptember(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 10: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountOctober(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightOctober(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightOctober(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 11: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountNovember(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightNovember(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightNovember(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
                case 12: {

                    //Iterate to each result in month where every row is a different profession
                    for (ResultsForMonthsReportsByProfessionDto listElement : entry.getValue()) {
                        if (!finalResultRows.containsKey(listElement.getProfessionId()))
                            finalResultRows.put(listElement.getProfessionId(), new ResultsOfTestByProfessionDto(monthNumbers));
                        ResultsOfTestByProfessionDto resultsOfTestByProfessionDto = finalResultRows.get(listElement.getProfessionId());
                        resultsOfTestByProfessionDto.setProfessionName(entityManager.find(Profession.class, listElement.getProfessionId()).getName());
                        resultsOfTestByProfessionDto.setCountDecember(String.valueOf(listElement.getTotalCountQuestions()));
                        resultsOfTestByProfessionDto.setCountRightDecember(String.valueOf(listElement.getSumRight()));
                        resultsOfTestByProfessionDto.setPercentRightDecember(String.valueOf((listElement.getSumRight() * 100) / (listElement.getSumRight() + listElement.getSumFalse())) + "%");
                    }
                    break;
                }
            }
        }
        return finalResultRows;
    }

    @Override
    public String[] deleteNullCellsFromResultsOfTests(String[] nameMappingResultsOfTestByOrganisation, ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto) {
        for (int i = 0; i < nameMappingResultsOfTestByOrganisation.length; i++) {
            switch (nameMappingResultsOfTestByOrganisation[i]) {
                case "countJanuary": {
                    if (resultsOfTestByOrganisationDto.getCountJanuary() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countFebruary": {
                    if (resultsOfTestByOrganisationDto.getCountFebruary() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countMarch": {
                    if (resultsOfTestByOrganisationDto.getCountMarch() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countApril": {
                    if (resultsOfTestByOrganisationDto.getCountApril() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countMay": {
                    if (resultsOfTestByOrganisationDto.getCountMay() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countJune": {
                    if (resultsOfTestByOrganisationDto.getCountJune() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countJuly": {
                    if (resultsOfTestByOrganisationDto.getCountJuly() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countAugust": {
                    if (resultsOfTestByOrganisationDto.getCountAugust() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countSeptember": {
                    if (resultsOfTestByOrganisationDto.getCountSeptember() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countOctober": {
                    if (resultsOfTestByOrganisationDto.getCountOctober() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countNovember": {
                    if (resultsOfTestByOrganisationDto.getCountNovember() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countDecember": {
                    if (resultsOfTestByOrganisationDto.getCountDecember() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightJanuary": {
                    if (resultsOfTestByOrganisationDto.getCountRightJanuary() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightFebruary": {
                    if (resultsOfTestByOrganisationDto.getCountRightFebruary() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightMarch": {
                    if (resultsOfTestByOrganisationDto.getCountRightMarch() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightApril": {
                    if (resultsOfTestByOrganisationDto.getCountRightApril() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightMay": {
                    if (resultsOfTestByOrganisationDto.getCountRightMay() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightJune": {
                    if (resultsOfTestByOrganisationDto.getCountRightJune() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightJuly": {
                    if (resultsOfTestByOrganisationDto.getCountRightJuly() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightAugust": {
                    if (resultsOfTestByOrganisationDto.getCountRightAugust() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightSeptember": {
                    if (resultsOfTestByOrganisationDto.getCountRightSeptember() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightOctober": {
                    if (resultsOfTestByOrganisationDto.getCountRightOctober() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightNovember": {
                    if (resultsOfTestByOrganisationDto.getCountRightNovember() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "countRightDecember": {
                    if (resultsOfTestByOrganisationDto.getCountRightDecember() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightJanuary": {
                    if (resultsOfTestByOrganisationDto.getPercentRightJanuary() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightFebruary": {
                    if (resultsOfTestByOrganisationDto.getPercentRightFebruary() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightMarch": {
                    if (resultsOfTestByOrganisationDto.getPercentRightMarch() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightApril": {
                    if (resultsOfTestByOrganisationDto.getPercentRightApril() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightMay": {
                    if (resultsOfTestByOrganisationDto.getPercentRightMay() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightJune": {
                    if (resultsOfTestByOrganisationDto.getPercentRightJune() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightJuly": {
                    if (resultsOfTestByOrganisationDto.getPercentRightJuly() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightAugust": {
                    if (resultsOfTestByOrganisationDto.getPercentRightAugust() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightSeptember": {
                    if (resultsOfTestByOrganisationDto.getPercentRightSeptember() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightOctober": {
                    if (resultsOfTestByOrganisationDto.getPercentRightOctober() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightNovember": {
                    if (resultsOfTestByOrganisationDto.getPercentRightNovember() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }
                case "percentRightDecember": {
                    if (resultsOfTestByOrganisationDto.getPercentRightDecember() == null) {
                        nameMappingResultsOfTestByOrganisation = ArrayUtils.remove(nameMappingResultsOfTestByOrganisation, i);
                        i--;
                    }
                    break;
                }

            }
        }
        return nameMappingResultsOfTestByOrganisation;
    }

    @Override
    public String[] deleteNullCellsFromResultsOfTests(String[] nameMappingResultsOfTestByProfession, ResultsOfTestByProfessionDto resultsOfTestByProfessionDto) {
        for (int i = 0; i < nameMappingResultsOfTestByProfession.length; i++) {
            switch (nameMappingResultsOfTestByProfession[i]) {
                case "countJanuary": {
                    if (resultsOfTestByProfessionDto.getCountJanuary() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countFebruary": {
                    if (resultsOfTestByProfessionDto.getCountFebruary() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countMarch": {
                    if (resultsOfTestByProfessionDto.getCountMarch() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countApril": {
                    if (resultsOfTestByProfessionDto.getCountApril() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countMay": {
                    if (resultsOfTestByProfessionDto.getCountMay() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countJune": {
                    if (resultsOfTestByProfessionDto.getCountJune() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countJuly": {
                    if (resultsOfTestByProfessionDto.getCountJuly() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countAugust": {
                    if (resultsOfTestByProfessionDto.getCountAugust() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countSeptember": {
                    if (resultsOfTestByProfessionDto.getCountSeptember() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countOctober": {
                    if (resultsOfTestByProfessionDto.getCountOctober() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countNovember": {
                    if (resultsOfTestByProfessionDto.getCountNovember() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countDecember": {
                    if (resultsOfTestByProfessionDto.getCountDecember() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightJanuary": {
                    if (resultsOfTestByProfessionDto.getCountRightJanuary() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightFebruary": {
                    if (resultsOfTestByProfessionDto.getCountRightFebruary() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightMarch": {
                    if (resultsOfTestByProfessionDto.getCountRightMarch() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightApril": {
                    if (resultsOfTestByProfessionDto.getCountRightApril() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightMay": {
                    if (resultsOfTestByProfessionDto.getCountRightMay() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightJune": {
                    if (resultsOfTestByProfessionDto.getCountRightJune() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightJuly": {
                    if (resultsOfTestByProfessionDto.getCountRightJuly() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightAugust": {
                    if (resultsOfTestByProfessionDto.getCountRightAugust() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightSeptember": {
                    if (resultsOfTestByProfessionDto.getCountRightSeptember() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightOctober": {
                    if (resultsOfTestByProfessionDto.getCountRightOctober() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightNovember": {
                    if (resultsOfTestByProfessionDto.getCountRightNovember() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "countRightDecember": {
                    if (resultsOfTestByProfessionDto.getCountRightDecember() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightJanuary": {
                    if (resultsOfTestByProfessionDto.getPercentRightJanuary() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightFebruary": {
                    if (resultsOfTestByProfessionDto.getPercentRightFebruary() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightMarch": {
                    if (resultsOfTestByProfessionDto.getPercentRightMarch() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightApril": {
                    if (resultsOfTestByProfessionDto.getPercentRightApril() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightMay": {
                    if (resultsOfTestByProfessionDto.getPercentRightMay() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightJune": {
                    if (resultsOfTestByProfessionDto.getPercentRightJune() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightJuly": {
                    if (resultsOfTestByProfessionDto.getPercentRightJuly() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightAugust": {
                    if (resultsOfTestByProfessionDto.getPercentRightAugust() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightSeptember": {
                    if (resultsOfTestByProfessionDto.getPercentRightSeptember() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightOctober": {
                    if (resultsOfTestByProfessionDto.getPercentRightOctober() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightNovember": {
                    if (resultsOfTestByProfessionDto.getPercentRightNovember() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }
                case "percentRightDecember": {
                    if (resultsOfTestByProfessionDto.getPercentRightDecember() == null) {
                        nameMappingResultsOfTestByProfession = ArrayUtils.remove(nameMappingResultsOfTestByProfession, i);
                        i--;
                    }
                    break;
                }

            }
        }
        return nameMappingResultsOfTestByProfession;
    }
}
