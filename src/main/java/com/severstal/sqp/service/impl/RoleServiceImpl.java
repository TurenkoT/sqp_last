package com.severstal.sqp.service.impl;

/**
 * Role service implementation.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.service.impl
 */

import com.severstal.sqp.dao.RoleDao;
import com.severstal.sqp.entity.Role;
import com.severstal.sqp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao dao;

    public Role findById(int id) {
        return dao.findById(id);
    }

    public Role findByType(String type) {
        return dao.findByType(type);
    }

    public List<Role> findAll() {
        return dao.findAll();
    }

    @Transactional
    public void populateUserToRole(int userId, int userRole) {
        dao.populateUserToRole(userId, userRole);
    }

    @Override
    public void updateUserToRole(final int userId, final int userRoleId)
    {
        dao.updateUserToRole(userId, userRoleId);
    }

    @Override
    public void setRolesForUsers(String[] usersIds, Role role){
        dao.setRolesForUsers(usersIds, role);
    }
}
