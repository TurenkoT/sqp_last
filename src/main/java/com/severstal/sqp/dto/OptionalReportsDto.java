package com.severstal.sqp.dto;

/**
 * Parameters for the optional report's types dto controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import lombok.Data;

@Data
public class OptionalReportsDto {
    private String startMonth;
    private String endMonth;
}
