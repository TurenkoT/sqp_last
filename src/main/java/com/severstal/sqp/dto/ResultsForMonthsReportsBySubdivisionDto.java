package com.severstal.sqp.dto;

import lombok.Data;

@Data
public class ResultsForMonthsReportsBySubdivisionDto {

    int organisationId;
    int subdivisionId;
    int sumRight;
    int sumFalse;
    int totalCountQuestions;
}
