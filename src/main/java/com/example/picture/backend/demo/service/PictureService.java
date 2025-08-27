package com.example.picture.backend.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.picture.backend.demo.api.aliyunai.model.CreateOutPaintingTaskResponse;
import com.example.picture.backend.demo.model.dto.picture.*;
import com.example.picture.backend.demo.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.picture.backend.demo.model.entity.User;
import com.example.picture.backend.demo.model.vo.PictureVO;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

/**
* @author hw1621
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-08-06 23:36:30
*/
public interface PictureService extends IService<Picture> {

    PictureVO uploadPicture(Object inputSource,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);

    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    PictureVO getPictureVOFromPicture(Picture picture, HttpServletRequest request);

    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    void validPicture(Picture picture);

    void doPictureReview(PictureReviewRequest request, User loginUser);

    void fillReviewParam(Picture picture, User loginUser);

    /**
     * 批量抓取和创建图片
     *
     * @param pictureUploadByBatchRequest
     * @param loginUser
     * @return 成功创建的图片数
     */
    int uploadPictureByBatch(
            PictureUploadByBatchRequest pictureUploadByBatchRequest,
            User loginUser
    );

    @Async
    void clearPictureFile(Picture oldPicture);

    /**
     * 校验空间图片
     * @param loingUser
     * @param picture
     */
    void checkPictureAuth(User loingUser, Picture picture);

    void editPicture(PictureEditRequest pictureEditRequest, User loginUser);

    void deletePicture(long pictureId, User loginUser);

    void editPictureByBatch(PictureEditByBatchRequest request, User loginUser);

    CreateOutPaintingTaskResponse createPictureOutPaintingTask(CreatePictureOutPaintingTaskRequest request, User loginUser);

}