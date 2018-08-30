package com.severstal.sqp.dto;

import lombok.Data;

@Data
public class ResultsForMonthsReportsByProfessionDto {

    int professionId;
    int sumRight;
    int sumFalse;
    int totalCountQuestions;
}
