package com.example.picture.backend.demo.constant;

/**
 * User-related constant definition interface
 * This interface centrally manages user-related constants in the system,
 * including session state keys and role definitions. It provides unified
 * constant references to avoid maintenance issues caused by hardcoding.
 **/
 public interface UserConstant {

    /**
     * User login session key
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * Default role
     */
    String DEFAULT_ROLE = "user";

    /**
     * Admin role
     */
    String ADMIN_ROLE = "admin";

    // endregion
}

