package com.severstal.sqp.entity;

/**
 * Subdivision entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SUBDIVISION")
public class Subdivision implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "subdivision")
    private Set<User> users = new HashSet<>();

    @ManyToOne(targetEntity = Organisation.class, optional = false)
    @JoinColumn(name = "ORGANISATION_ID", referencedColumnName = "ID")
    private Organisation organisation;

    @ManyToOne(targetEntity = BusinessUnit.class, optional = false)
    @JoinColumn(name = "BUSINESS_UNIT_ID", referencedColumnName = "ID")
    private BusinessUnit businessUnit;

    @OneToMany(mappedBy = "subdivision")
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
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
