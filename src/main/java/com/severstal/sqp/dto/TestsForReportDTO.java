package com.severstal.sqp.dto;

/**
 * Dto for creating list of test with not a default fields for reports.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import com.severstal.sqp.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class TestsForReportDTO {

    private String fullName;
    private String passed;
    private String organisationName;
    private String subdivisionName;
    private String personalNumber;
    private String stringDate;
    private User user;
    private int totalCountOfRight;
    private int totalCountOfFalse;
    private int totalCountOfQuestions;
    private Date date;

    public TestsForReportDTO() {

    }

    public TestsForReportDTO(User userDto) {

        this.setTotalCountOfQuestions(totalCountOfQuestions);
        this.setStringDate(stringDate);
        this.setFullName(userDto.getFullName());
        this.setPersonalNumber(userDto.getPersonalNumber());
        this.setOrganisationName(userDto.getOrganisation().getName());
        this.setSubdivisionName(userDto.getSubdivision().getName());
    }

}
