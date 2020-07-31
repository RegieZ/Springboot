package com.regino.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int uid;
    private String username;
    private String sex;
    private Date birthday;
    private String address;
}
