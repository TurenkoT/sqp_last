package com.severstal.sqp.entity;

/**
 * Answer entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ANSWER")
public class Answer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "VALUE", length = 10000)
    private String value;

    @Column(name = "CORRECTNESS")
    private boolean correctness;

    @ManyToOne(targetEntity = Question.class, optional = false)
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID")
    private Question question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isCorrectness() {
        return correctness;
    }

    public void setCorrectness(boolean correctness) {
        this.correctness = correctness;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
