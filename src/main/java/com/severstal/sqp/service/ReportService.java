package com.severstal.sqp.service;

/**
 * Report interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service
 */

import com.severstal.sqp.dto.*;

import java.util.List;
import java.util.Map;

public interface ReportService {
    Map<Integer, String> getNamesOfChoosenMonths(String start, String end);

    String[] deleteNullCells(String[] nameMappingCount, PercentIndicatorsForReportDto percentIndicatorsForReportDto);

    String[] deleteNullCells(String[] nameMappingCount, MonthsIndicatorsForReportDto monthsIndicatorsForReportDto);

    PercentIndicatorsForReportDto getPercentIndicators(Map<Integer, ResultsForMonthsReportsDto> resultsMap);

    MonthsIndicatorsForReportDto getMonthsIndicators(Map<Integer, ResultsForMonthsReportsDto> resultsMap);

    Map<Integer, ResultsOfTestByOrganisationDto> getFinalResultRows(Map<Integer, List<ResultsForMonthsReportsByOrganisationDto>> result, List<Integer> monthNumbers);

    Map<Integer, ResultsOfTestBySubdivisionDto> getFinalSubdivisinResultRows(Map<Integer, List<ResultsForMonthsReportsBySubdivisionDto>> result, List<Integer> monthNumbers);

    String[] deleteNullCellsFromResultsOfTests(String[] nameMappingResultsOfTestByOrganisation, ResultsOfTestByOrganisationDto resultsOfTestByOrganisationDto);

    String[] deleteNullCellsFromResultsOfTests(String[] nameMappingResultsOfTestByProfession, ResultsOfTestByProfessionDto resultsOfTestByProfessionDto);

    Map<Integer, ResultsOfTestByProfessionDto> getFinalProfessionResultRows(Map<Integer, List<ResultsForMonthsReportsByProfessionDto>> result, List<Integer> monthNumbers);
}
