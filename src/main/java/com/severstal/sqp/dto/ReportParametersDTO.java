package com.severstal.sqp.dto;

/**
 * Report parameters dto.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.config
 */

import lombok.Data;

import java.util.Map;


@Data
public class ReportParametersDTO {

    String[] subdivs;
    String startDate;
    String endDate;

}
