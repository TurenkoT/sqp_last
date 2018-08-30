package com.severstal.sqp.entity;

/**
 * Organisation entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "ORGANISATION")
public class Organisation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(targetEntity = BusinessUnit.class, optional = false)
    @JoinColumn(name = "BUSINESS_UNIT_ID", referencedColumnName = "ID")
    private BusinessUnit businessUnit;

    @OneToMany(mappedBy = "organisation")
    private Set<Subdivision> subdivisions;

    @OneToMany(mappedBy = "organisation")
    private Set<User> users;


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

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    public Set<Subdivision> getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(Set<Subdivision> subdivisions) {
        this.subdivisions = subdivisions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
