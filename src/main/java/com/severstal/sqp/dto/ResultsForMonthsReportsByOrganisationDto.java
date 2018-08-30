package com.severstal.sqp.dto;

import lombok.Data;

@Data
public class ResultsForMonthsReportsByOrganisationDto {

    int organisationId;
    int sumRight;
    int sumFalse;
    int totalCountQuestions;
}
