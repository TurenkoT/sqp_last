package com.severstal.sqp.dao;

/**
 * Role dao interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.dao
 */

import com.severstal.sqp.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RoleDao {

    List<Role> findAll();

    Role findByType(String type);

    Role findById(int id);

    void populateUserToRole(int userId, int userRole);

    void updateUserToRole(int userId, int userRoleId);

    void setRolesForUsers(String[] usersIds, Role role);
}