package com.example.picture.backend.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.picture.backend.demo.model.dto.space.SpaceAddRequest;
import com.example.picture.backend.demo.model.dto.space.SpaceQueryRequest;
import com.example.picture.backend.demo.model.entity.Space;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.picture.backend.demo.model.entity.User;
import com.example.picture.backend.demo.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author hw1621
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2025-08-16 14:59:16
*/
public interface SpaceService extends IService<Space> {

    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    SpaceVO getSpaceVOFromSpace(Space space, HttpServletRequest request);

    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    void validSpace(Space space, boolean add);

    void fillSpaceBySpaceLevel(Space space);
}
