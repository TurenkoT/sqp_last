package com.severstal.sqp.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultsOfTestByOrganisationDto {

    private static final String noResults = "нет результатов";
    String organisationName;
    String countJanuary;
    String countFebruary;
    String countMarch;
    String countApril;
    String countMay;
    String countJune;
    String countJuly;
    String countAugust;
    String countSeptember;
    String countOctober;
    String countNovember;
    String countDecember;
    String countRightJanuary;
    String countRightFebruary;
    String countRightMarch;
    String countRightApril;
    String countRightMay;
    String countRightJune;
    String countRightJuly;
    String countRightAugust;
    String countRightSeptember;
    String countRightOctober;
    String countRightNovember;
    String countRightDecember;
    String percentRightJanuary;
    String percentRightFebruary;
    String percentRightMarch;
    String percentRightApril;
    String percentRightMay;
    String percentRightJune;
    String percentRightJuly;
    String percentRightAugust;
    String percentRightSeptember;
    String percentRightOctober;
    String percentRightNovember;
    String percentRightDecember;

    public ResultsOfTestByOrganisationDto(List<Integer> numbersOfMonths) {
        for (Integer number : numbersOfMonths) {
            switch (number) {
                case 1: {
                    countJanuary = noResults;
                    countRightJanuary = noResults;
                    percentRightJanuary = noResults;
                    break;
                }
                case 2: {
                    countFebruary = noResults;
                    countRightFebruary = noResults;
                    percentRightFebruary = noResults;
                    break;
                }
                case 3: {
                    countMarch = noResults;
                    countRightMarch = noResults;
                    percentRightMarch = noResults;
                    break;
                }
                case 4: {
                    countApril = noResults;
                    countRightApril = noResults;
                    percentRightApril = noResults;
                    break;
                }
                case 5: {
                    countMay = noResults;
                    countRightMay = noResults;
                    percentRightMay = noResults;
                    break;
                }
                case 6: {
                    countJune = noResults;
                    countRightJune = noResults;
                    percentRightJune = noResults;
                    break;
                }
                case 7: {
                    countJuly = noResults;
                    countRightJuly = noResults;
                    percentRightJuly = noResults;
                    break;
                }
                case 8: {
                    countAugust = noResults;
                    countRightAugust = noResults;
                    percentRightAugust = noResults;
                    break;
                }
                case 9: {
                    countSeptember = noResults;
                    countRightSeptember = noResults;
                    percentRightSeptember = noResults;
                    break;
                }
                case 10: {
                    countOctober = noResults;
                    countRightOctober = noResults;
                    percentRightOctober = noResults;
                    break;
                }
                case 11: {
                    countNovember = noResults;
                    countRightNovember = noResults;
                    percentRightNovember = noResults;
                    break;
                }
                case 12: {
                    countDecember = noResults;
                    countRightDecember = noResults;
                    percentRightDecember = noResults;
                    break;
                }
            }
        }
    }
}
