package com.severstal.sqp.dto;

import lombok.Data;

@Data
public class MassiveEditComplexityForQuestionDto {
    private String[] questionsIds;
    private int complexityId;
}
