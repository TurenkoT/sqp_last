package com.severstal.sqp.dto;

/**
 * Row with indicators for report dto controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import lombok.Data;

@Data
public class MonthsIndicatorsForReportDto {
    String rowName;
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
}
