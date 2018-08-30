package com.severstal.sqp.dto;

import com.severstal.sqp.entity.TimeSettings;
import lombok.Data;

import java.util.Date;

@Data
public class TimeSettingsDto {
    private Long id;
    private String name;
    private Date date;

    public TimeSettingsDto(){

    }

    public TimeSettingsDto(TimeSettings timeSettings){
        this.setId(timeSettings.getId());
        this.setName(timeSettings.getName());
        this.setDate(timeSettings.getDate());
    }
}
