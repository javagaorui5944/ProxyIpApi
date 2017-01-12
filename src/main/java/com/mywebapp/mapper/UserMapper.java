package com.mywebapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by gaorui on 17/1/12.
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<Map<String,Object>> getUsers();



}