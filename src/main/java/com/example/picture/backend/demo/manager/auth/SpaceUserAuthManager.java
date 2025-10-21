package com.example.picture.backend.demo.manager.auth;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.example.picture.backend.demo.manager.auth.model.SpaceUserAuthConfig;
import com.example.picture.backend.demo.manager.auth.model.SpaceUserRole;
import org.jsoup.internal.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//从json权限文档里提取对应的权限配置
@Component
public class SpaceUserAuthManager {

    @Resource
    private SpaceUserAuthConfig spaceUserAuthConfig;


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
}
