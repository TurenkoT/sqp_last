package com.severstal.sqp.dto;

import com.severstal.sqp.entity.*;
import lombok.Data;

import java.util.Set;

@Data
public class QuestionDto {

    private int id;
    private String text;
    private Boolean activeStatus;
    private Subject subject;
    private Set<Answer> answers;
    private TypeQuestion typeQuestion;
    private Complexity complexity;

    public QuestionDto(){

    }

    public QuestionDto(Question question){
        this.setId(question.getId());
        this.setText(question.getText());
        this.setActiveStatus(question.getActiveStatus());
        this.setSubject(question.getSubject());
        this.setAnswers(question.getAnswers());
        this.setTypeQuestion(question.getTypeQuestion());
        this.setComplexity(question.getComplexity());
    }
}
