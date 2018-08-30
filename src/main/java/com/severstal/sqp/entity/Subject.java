package com.severstal.sqp.entity;

/**
 * Subject entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SUBJECT")
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "subject")
    private Set<Question> questions = new HashSet<>();

    @ManyToOne(targetEntity = BusinessUnit.class, optional = false)
    @JoinColumn(name = "BUSINESS_UNIT_ID", referencedColumnName = "ID")
    private BusinessUnit businessUnit;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SUBJECT_TO_SUBDIVPROF",
            joinColumns = {@JoinColumn(name = "SUBJECT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "PROFSUBDIV_ID")})
    private Set<ProfessionToSubdivision> professionToSubdivisions = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    public Set<ProfessionToSubdivision> getProfessionToSubdivisions() {
        return professionToSubdivisions;
    }

    public void setProfessionToSubdivisions(Set<ProfessionToSubdivision> professionToSubdivisions) {
        this.professionToSubdivisions = professionToSubdivisions;
    }
}
