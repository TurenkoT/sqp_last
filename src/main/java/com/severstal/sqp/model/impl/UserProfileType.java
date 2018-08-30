package com.severstal.sqp.model.impl;

/**
 * User role types.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.model.impl
 */

import java.io.Serializable;

public enum UserProfileType implements Serializable {
    USER("USER"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN"),
    HEADMAN("HEADMAN"),
    DIRECTOR("DIRECTOR"),
    TOP_MANAGEMENT("TOP_MANAGEMENT");

    String userProfileType;

    private UserProfileType(String userProfileType) {
        this.userProfileType = userProfileType;
    }

    public String getUserProfileType() {
        return userProfileType;
    }

}