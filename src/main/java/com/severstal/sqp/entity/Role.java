package com.severstal.sqp.entity;

/**
 * User profile entity.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.entity
 */

import com.severstal.sqp.model.impl.UserProfileType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TYPE", length = 15, unique = true, nullable = false)
    private String type = UserProfileType.USER.getUserProfileType();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<User> associatedUsers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<User> getAssociatedUsers() {
        return associatedUsers;
    }

    public void setAssociatedUsers(Set<User> associatedUsers) {
        this.associatedUsers = associatedUsers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Role))
            return false;
        Role other = (Role) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return type;
    }


}
