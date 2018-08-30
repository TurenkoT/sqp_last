package com.severstal.sqp.entity;

/**
 * Profession to subdivision entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PROFESSION_TO_SUBDIVISION")
public class ProfessionToSubdivision implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Subdivision.class, optional = false)
    @JoinColumn(name = "SUBDIVISION_ID", referencedColumnName = "ID")
    private Subdivision subdivision;

    @ManyToOne(targetEntity = Profession.class, optional = false)
    @JoinColumn(name = "PROFESSION_ID", referencedColumnName = "ID")
    private Profession profession;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "professionToSubdivisions")
    private Set<Subject> subjects = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
