package com.example.picture.backend.demo.manager.auth;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.example.picture.backend.demo.manager.auth.model.SpaceUserAuthConfig;
import com.example.picture.backend.demo.manager.auth.model.SpaceUserRole;
import com.example.picture.backend.demo.model.entity.Space;
import com.example.picture.backend.demo.model.entity.SpaceUser;
import com.example.picture.backend.demo.model.entity.User;
import com.example.picture.backend.demo.model.enums.SpaceRoleEnum;
import com.example.picture.backend.demo.model.enums.SpaceTypeEnum;
import com.example.picture.backend.demo.service.SpaceUserService;
import com.example.picture.backend.demo.service.UserService;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//从json权限文档里提取对应的权限配置
@Component
public class SpaceUserAuthManager {

    @Resource
    private UserService userService;

    @Resource
    private SpaceUserService spaceUserService;


    public static final SpaceUserAuthConfig SPACE_USER_AUTH_CONFIG;

    static {
        String json = ResourceUtil.readUtf8Str("biz/spaceUserAuthConfig.json");
        SPACE_USER_AUTH_CONFIG = JSONUtil.toBean(json, SpaceUserAuthConfig.class);
    }

    public List<String> getPermissionsByRole(String spaceUserRole) {
        if (StringUtil.isBlank(spaceUserRole)) {
            return new ArrayList<>();
        }
        SpaceUserRole role = SPACE_USER_AUTH_CONFIG.getRoles().stream()
                .filter(r -> spaceUserRole.equals(r.getKey()))
                .findFirst()
                .orElse(null);
        if (role == null) {
            return new ArrayList<>();
        }
        return role.getPermissions();
    }

    public List<String> getPermissionList(Space space, User loginUser) {
        if (loginUser == null) {
            return new ArrayList<>();
        }
        // 管理员权限
        List<String> ADMIN_PERMISSIONS = getPermissionsByRole(SpaceRoleEnum.ADMIN.getValue());
        // 公共图库
        if (space == null) {
            if (userService.isAdmin(loginUser)) {
                return ADMIN_PERMISSIONS;
            }
            return new ArrayList<>();
        }
        SpaceTypeEnum spaceTypeEnum = SpaceTypeEnum.getEnumByValue(space.getSpaceType());
        if (spaceTypeEnum == null) {
            return new ArrayList<>();
        }
        // 根据空间获取对应的权限
        switch (spaceTypeEnum) {
            case PRIVATE:
                // 私有空间，仅本人或管理员有所有权限
                if (space.getUserId().equals(loginUser.getId()) || userService.isAdmin(loginUser)) {
                    return ADMIN_PERMISSIONS;
                } else {
                    return new ArrayList<>();
                }
            case TEAM:
                // 团队空间，查询 SpaceUser 并获取角色和权限
                SpaceUser spaceUser = spaceUserService.lambdaQuery()
                        .eq(SpaceUser::getSpaceId, space.getId())
                        .eq(SpaceUser::getUserId, loginUser.getId())
                        .one();
                if (spaceUser == null) {
                    return new ArrayList<>();
                } else {
                    return getPermissionsByRole(spaceUser.getSpaceRole());
                }
        }
        return new ArrayList<>();
    }

}
