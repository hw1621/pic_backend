package com.example.picture.backend.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.picture.backend.demo.model.dto.UserQueryRequest;
import com.example.picture.backend.demo.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.picture.backend.demo.model.vo.LoginUserVO;
import com.example.picture.backend.demo.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hw1621
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-07-30 23:34:34
 */
public interface UserService extends IService<User> {

    long userRegister(String userAccount, String password, String confirmPassword);

    String getEncryptPassword(String password);

    LoginUserVO getLoginUserVOFromUser(User user);

    UserVO getUserVOFromUser(User user);

    List<UserVO> getUserVOListFromUser(List<User> user);

    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    boolean userLogout(HttpServletRequest request);

    User getLoginUser(HttpServletRequest request);

    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
