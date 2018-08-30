package com.severstal.sqp.entity;

/**
 * Test entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TEST")
public class Test implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "COUNT_RIGHT")
    private int countRight;

    @Column(name = "COUNT_FALSE")
    private int countFalse;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "POINTS")
    private double points;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(targetEntity = Question.class, optional = false)
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID")
    private Question question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCountRight() {
        return countRight;
    }

    public void setCountRight(int countRight) {
        this.countRight = countRight;
    }

    public int getCountFalse() {
        return countFalse;
    }

    public void setCountFalse(int countFalse) {
        this.countFalse = countFalse;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
