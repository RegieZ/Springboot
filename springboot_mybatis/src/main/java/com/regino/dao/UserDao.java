package com.regino.dao;

import com.regino.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper //声明是一个mapper接口
@Repository //注入到spring容器中
public interface UserDao {
    List<User> findAll();
}
