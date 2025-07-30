package com.example.picture.backend.demo.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 2694292929552108643L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
