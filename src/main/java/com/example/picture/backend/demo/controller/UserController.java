package com.example.picture.backend.demo.controller;

import com.example.picture.backend.demo.common.BaseResponse;
import com.example.picture.backend.demo.common.ResultUtils;
import com.example.picture.backend.demo.exception.ErrorCode;
import com.example.picture.backend.demo.exception.ThrowUtils;
import com.example.picture.backend.demo.model.dto.UserLoginRequest;
import com.example.picture.backend.demo.model.dto.UserRegisterRequest;
import com.example.picture.backend.demo.model.entity.User;
import com.example.picture.backend.demo.model.vo.LoginUserVO;
import com.example.picture.backend.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO result = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(result);
    }

    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVOFromUser(loginUser));
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogin(HttpServletRequest request) {
        boolean res = userService.userLogout(request);
        return ResultUtils.success(res);
    }
}
