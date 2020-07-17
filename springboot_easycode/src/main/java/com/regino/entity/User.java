package com.regino.entity;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * (User)实体类
 *
 * @author Regino
 * @since 2020-07-17 10:12:50
 */
public class User implements Serializable {
    private static final long serialVersionUID = 583175427997643158L;

    private Integer id;

    private String uname;

    private String pwd;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}