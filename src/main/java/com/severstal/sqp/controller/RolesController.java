package com.severstal.sqp.controller;

/**
 * Roles page controller.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import com.severstal.sqp.dto.RolesForUsersDto;
import com.severstal.sqp.service.RoleService;
import com.severstal.sqp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RolesController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/adminPanel/roles")
    public ModelAndView getUsersPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("roles/roles");
        modelAndView.addObject("user",userService.getCurrentUser());
        modelAndView.addObject("employees", userService.findUsersByBusinessUnitId(userService.getCurrentUser().getSubdivision().getOrganisation().getBusinessUnit().getId().toString()));
        modelAndView.addObject("roles", roleService.findAll());
        return modelAndView;
    }

    @PostMapping(value = "/adminPanel/roles/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveRolesForUser(@RequestBody RolesForUsersDto rolesForUsersDto){
        roleService.setRolesForUsers(rolesForUsersDto.getIds(), roleService.findByType(rolesForUsersDto.getRole()));
    }
}
