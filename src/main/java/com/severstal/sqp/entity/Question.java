package com.severstal.sqp.entity;

/**
 * Question entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "QUESTION")
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TEXT", length = 10000)
    private String text;

    @Column(name = "ACTIVE_STATUS")
    private Boolean activeStatus;

    @ManyToOne(targetEntity = Subject.class, optional = false)
    @JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID")
    private Subject subject;

    @OneToMany(mappedBy = "question")
    private Set<Test> tests = new HashSet<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private Set<Answer> answers = new HashSet<Answer>();

    @ManyToOne(targetEntity = TypeQuestion.class, optional = false)
    @JoinColumn(name = "TYPE_QUESTION_ID", referencedColumnName = "ID")
    private TypeQuestion typeQuestion;

    @ManyToOne(targetEntity = Complexity.class, optional = false)
    @JoinColumn(name = "COMPLEXITY_ID", referencedColumnName = "ID")
    private Complexity complexity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public TypeQuestion getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(TypeQuestion typeQuestion) {
        this.typeQuestion = typeQuestion;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }
}