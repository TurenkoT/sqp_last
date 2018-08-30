package com.severstal.sqp.service;

/**
 * Role service interface.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service
 */

import com.severstal.sqp.entity.Role;

import java.util.List;


public interface RoleService {

    Role findById(int id);

    Role findByType(String type);

    List<Role> findAll();

    void populateUserToRole(int userId, int userRole);

    void updateUserToRole(int userId, int userRoleId);

    void setRolesForUsers(String[] usersIds, Role role);
}
