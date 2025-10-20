package com.example.picture.backend.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.picture.backend.demo.model.dto.space.SpaceAddRequest;
import com.example.picture.backend.demo.model.dto.space.SpaceQueryRequest;
import com.example.picture.backend.demo.model.dto.spaceuser.SpaceUserAddRequest;
import com.example.picture.backend.demo.model.dto.spaceuser.SpaceUserQueryRequest;
import com.example.picture.backend.demo.model.entity.Space;
import com.example.picture.backend.demo.model.entity.SpaceUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.picture.backend.demo.model.entity.User;
import com.example.picture.backend.demo.model.vo.SpaceUserVO;
import com.example.picture.backend.demo.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author hw1621
* @description 针对表【space_user(空间用户关联)】的数据库操作Service
* @createDate 2025-09-01 00:43:08
*/
public interface SpaceUserService extends IService<SpaceUser> {

    long addSpaceUser(SpaceUserAddRequest spaceAddRequest);

    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceQueryRequest);

    SpaceUserVO getSpaceUserVOFromSpaceUser(SpaceUser spaceUser, HttpServletRequest request);

    List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList);

    void validSpaceUser(SpaceUser space, boolean add);

}
