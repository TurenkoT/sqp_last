package com.severstal.sqp.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultsOfTestBySubdivisionDto extends ResultsOfTestByOrganisationDto {

    private String subdivisionName;

    public ResultsOfTestBySubdivisionDto(List<Integer> numbersOfMonths) {
        super(numbersOfMonths);
    }
}
