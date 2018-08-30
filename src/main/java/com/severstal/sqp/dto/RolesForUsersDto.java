package com.severstal.sqp.dto;

/**
 * Roles for users dto.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.controller
 */

import lombok.Data;

@Data
public class RolesForUsersDto {
    String[] ids;
    String role;
}
