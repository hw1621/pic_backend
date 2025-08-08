package com.example.picture.backend.demo.controller;

import com.example.picture.backend.demo.annotation.AuthCheck;
import com.example.picture.backend.demo.common.BaseResponse;
import com.example.picture.backend.demo.common.ResultUtils;
import com.example.picture.backend.demo.constant.UserConstant;
import com.example.picture.backend.demo.model.dto.picture.PictureUploadRequest;
import com.example.picture.backend.demo.model.entity.User;
import com.example.picture.backend.demo.model.vo.PictureVO;
import com.example.picture.backend.demo.service.PictureService;
import com.example.picture.backend.demo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Resource
    private UserService userService;

    @Resource
    private PictureService pictureService;

    @PostMapping("/upload")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PictureVO> uploadPicture(@RequestParam("file") MultipartFile file,
                                                 HttpServletRequest request,
                                                 PictureUploadRequest pictureUploadRequest) {
        User loginUser = userService.getLoginUser(request);
        PictureVO pictureVO = pictureService.uploadPicture(file, pictureUploadRequest, loginUser);
        return ResultUtils.success(pictureVO);
    }
}
