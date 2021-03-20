package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: ChangCheng
 * @time: 2021/1/2 15:56
 */

@Repository
public interface UserMapper {

    User findUserByUserName(@Param("username") String username);
    User findUserById(@Param("id") String id);

}
