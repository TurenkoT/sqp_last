package com.severstal.sqp.dto;

import lombok.Data;

@Data
public class EditQuestionDto {
    private int questId;
    private String questionText;
    private Boolean activeStatus;
    private int subjectId;
    private int complexityId;
    private int typeQuestionId;
    private String[] additionalAnswers;
    private Boolean[] correctnessAnswers;

    public void setCorrectnessAnswers(String[] correctnessAnswers) {
        Boolean[] result = new Boolean[correctnessAnswers.length];
        for (int i = 0; i < correctnessAnswers.length; i++) {
            if (correctnessAnswers[i].equals("true")) {
                result[i] = true;
            } else if (correctnessAnswers[i].equals("false")) {
                result[i] = false;
            }
        }
        this.correctnessAnswers = result;
    }

    public void setActiveStatus(String[] activeStatus) {
        boolean result = false;
        if (activeStatus[0].equals("true")) {
            result = true;
        }
        this.activeStatus = result;
    }
}
