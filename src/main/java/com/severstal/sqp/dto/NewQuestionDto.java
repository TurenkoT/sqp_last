package com.severstal.sqp.dto;

import lombok.Data;

@Data
public class NewQuestionDto {
    private String questionText;
    private int subjectId;
    private int complexityId;
    private int typeQuestionId;
    private String[] additionalAnswers;
    private Boolean[] correctnessAnswers;

    public void setCorrectnessAnswers(String[] correctnessAnswers) {
        Boolean[] result = new Boolean[correctnessAnswers.length];
        for(int i=0; i< correctnessAnswers.length; i++){
            if(correctnessAnswers[i].equals("true")){
                result[i]=true;
            }else if(correctnessAnswers[i].equals("false")){
                result[i]=false;
            }
        }
        this.correctnessAnswers = result;
    }
}
