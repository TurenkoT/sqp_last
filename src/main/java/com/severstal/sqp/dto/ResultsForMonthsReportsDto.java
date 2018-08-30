package com.severstal.sqp.dto;

/**
 * Report's for results per month dto controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import lombok.Data;

@Data
public class ResultsForMonthsReportsDto {
    int sumRight;
    int sumFalse;
    int totalCountQuestions;
}
