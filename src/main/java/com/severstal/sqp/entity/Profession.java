package com.severstal.sqp.entity;

/**
 * Profession entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "PROFESSION")
public class Profession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "professions")
    private Set<User> associatedUsers;

    @OneToMany(mappedBy = "profession", fetch = FetchType.EAGER)
    private Set<ProfessionToSubdivision> professionToSubdivisions = new HashSet<>();

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

    public Set<User> getAssociatedUsers() {
        return associatedUsers;
    }

    public void setAssociatedUsers(Set<User> associatedUsers) {
        this.associatedUsers = associatedUsers;
    }

    public Set<ProfessionToSubdivision> getProfessionToSubdivisions() {
        return professionToSubdivisions;
    }

    public void setProfessionToSubdivisions(Set<ProfessionToSubdivision> professionToSubdivisions) {
        this.professionToSubdivisions = professionToSubdivisions;
    }
}
