package com.severstal.sqp.dto;

/**
 * Report dto for a csv reports unload.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.config
 */

import lombok.Data;

import java.util.Date;

@Data
public class ReportCsvDTO {

    private int count;
    private String personalNumber;
    private String fullName;
    private String organisation;
    private String subdivision;
    private String profession;
    private Date date;
    private String testStatus;
    private int totalCountOfQuestion;
    private int totalCountOfRightAnswer;
    private int totalCountOfFalseAnswer;

}
