package com.severstal.sqp.dto;

import com.severstal.sqp.entity.Organisation;
import com.severstal.sqp.entity.Test;
import com.severstal.sqp.entity.User;
import lombok.Data;

@Data
public class UserDto {

    private String passed;
    private String fullName;
    private String personalNumber;
    private String organisationName;
    private String subdivisionName;

    public UserDto() {}

    public UserDto(User entity) {

        this.setPassed("");
        this.setFullName(entity.getFullName());
        this.setPersonalNumber(entity.getPersonalNumber());
        this.setOrganisationName(entity.getOrganisation().getName());
        this.setSubdivisionName(entity.getSubdivision().getName());
    }

}
