package com.example.picture.backend.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.picture.backend.demo.model.dto.picture.PictureQueryRequest;
import com.example.picture.backend.demo.model.dto.picture.PictureUploadRequest;
import com.example.picture.backend.demo.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.picture.backend.demo.model.entity.User;
import com.example.picture.backend.demo.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author hw1621
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-08-06 23:36:30
*/
public interface PictureService extends IService<Picture> {

    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);

    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    PictureVO getPictureVOFromPicture(Picture picture, HttpServletRequest request);

    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    void validPicture(Picture picture);
}