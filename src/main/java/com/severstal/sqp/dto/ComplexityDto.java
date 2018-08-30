package com.severstal.sqp.dto;

import com.severstal.sqp.entity.Complexity;
import lombok.Data;

@Data
public class ComplexityDto {
    private Long id;
    private String name;
    private double points;
    private double penalty;

    public ComplexityDto() {

    }

    public ComplexityDto(Complexity complexity) {
        this.setId(complexity.getId());
        this.setName(complexity.getName());
        this.setPoints(complexity.getPoints());
        this.setPenalty(complexity.getPenalty());
    }
}
