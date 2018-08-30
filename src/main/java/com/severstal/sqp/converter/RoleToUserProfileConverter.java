package com.severstal.sqp.converter;

/**
 * Role to user profile converter.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.converter
 */

import com.severstal.sqp.entity.Role;
import com.severstal.sqp.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleToUserProfileConverter implements Converter<Object, Role> {

    static final Logger logger = LoggerFactory.getLogger(RoleToUserProfileConverter.class);

    @Autowired
    RoleService roleService;


    public Role convert(Object element) {
        Integer id = Integer.parseInt((String) element);
        Role profile = roleService.findById(id);
        logger.info("Profile : {}", profile);
        return profile;
    }
}
