package com.severstal.sqp.entity;

/**
 * Business entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "BUSINESS_UNIT")
public class BusinessUnit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @OneToMany(mappedBy = "businessUnit")
    private Set<Organisation> organisations = new HashSet<>();

    @OneToMany(mappedBy = "businessUnit")
    private Set<Subdivision> subdivisions = new HashSet<>();

    @OneToMany(mappedBy = "businessUnit")
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "businessUnit")
    private Set<Complexity> complexities = new HashSet<>();

    @OneToMany(mappedBy = "businessUnit")
    private Set<TimeSettings> timeSettings = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(Set<Organisation> organisations) {
        this.organisations = organisations;
    }

    public Set<Subdivision> getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(Set<Subdivision> subdivisions) {
        this.subdivisions = subdivisions;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<Complexity> getComplexities() {
        return complexities;
    }

    public void setComplexities(Set<Complexity> complexities) {
        this.complexities = complexities;
    }

    public Set<TimeSettings> getTimeSettings() {
        return timeSettings;
    }

    public void setTimeSettings(Set<TimeSettings> timeSettings) {
        this.timeSettings = timeSettings;
    }
}
