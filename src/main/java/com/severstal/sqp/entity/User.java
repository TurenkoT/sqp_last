package com.severstal.sqp.entity;

/**
 * User entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "USER")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "DOB")
    private String DOB;

    @Column(name = "PERSONAL_NUMBER")
    private String personalNumber;

    @ManyToOne(targetEntity = Organisation.class, optional = false)
    @JoinColumn(name = "ORGANISATION_ID", referencedColumnName = "ID")
    private Organisation organisation;

    @ManyToOne(targetEntity = Subdivision.class, optional = false)
    @JoinColumn(name = "SUBDIVISION_ID", referencedColumnName = "ID")
    private Subdivision subdivision;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_TO_PROFESSION", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "PROFESSION_ID")
    })
    private Set<Profession> professions = new HashSet<Profession>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_TO_ROLE", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "USER_ROLE_ID")
    })
    private Set<Role> roles = new HashSet<Role>();

    @OneToMany(mappedBy = "user")
    private Set<Test> tests = new HashSet<Test>();

    @OneToMany(mappedBy = "user")
    private Set<Competence> competences = new HashSet<Competence>();

    @Transient
    List<GrantedAuthority> authorities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    public Set<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(Set<Profession> professions) {
        this.professions = professions;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }

    public Set<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences = competences;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }

        authorities = new ArrayList<GrantedAuthority>();
        for (Role role : this.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getType()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User [id " + id + ", login=" + login + ", password=" + password + ", name=" + fullName + ", PersonalNumber=" +
                personalNumber + "]";
    }

    public String getStringOfProfessions() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Profession> iterator = getProfessions().iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next().getName());
            if (iterator.hasNext()) {
                stringBuilder.append("; ");
            }
        }
        return stringBuilder.toString();
    }

}
