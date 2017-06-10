package com.mywebapp.mapper;

import com.mywebapp.bean.User;
import org.apache.ibatis.annotations.*;
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

    @Select("select    id,u_name,u_email,u_key,u_nickname,u_created_at,u_avatar_url,u_status from p_user where u_name =#{login}")
    User getUserByLogin(@Param("login") String login);

    @Insert("insert into p_user  (u_name,u_email,u_key,u_nickname,u_created_at,u_avatar_url) values(#{u_name},#{u_email},#{u_key},#{u_nickname},#{u_created_at},#{u_avatar_url}) ")
    int insertUser(@Param("u_name") String u_name,@Param("u_email") String u_email,@Param("u_key") String u_key,@Param("u_nickname") String u_nickname,@Param("u_created_at") String u_created_at,@Param("u_avatar_url") String u_avatar_url);

    @Select("select    id,u_name,u_email,u_key,u_nickname,u_created_at,u_avatar_url,u_count from p_user where u_name =#{login} and u_key=#{u_key}")
    User check(@Param("login") String login,@Param("u_key") String u_key);

    @Select("select    id,u_name,u_email,u_key,u_nickname,u_created_at,u_avatar_url,u_status,u_count from p_user")
    List<User> getAllUsers();

    @Update("update p_user  set u_count = u_count-1 where u_name = #{u_name}")
    int userCount(@Param("u_name") String u_name);

    @Update("update p_user  set u_count = u_count+10 where u_name = #{u_name}")
    int addUserCount(@Param("u_name") String u_name);

    @Update("update p_user set u_status = 0 where  u_name = #{u_name}")
    int bannedUser(@Param("u_name") String u_name);

    @Update("update p_user set u_status = 10 where  u_name = #{u_name}")
    int restoreUser(@Param("u_name") String u_name);

    @Select("select u_count from p_user where u_name = #{u_name}")
    int getUserCount(@Param("u_name") String u_name);

    @Select("select u_status from p_user where u_name = #{u_name}")
    int getUserStatus(@Param("u_name") String u_name);

    @Update("update p_user set u_nickname =#{u_nickname} , u_email= #{u_email} where  u_name = #{u_name}")
    int updateInfo(@Param("u_name") String u_name,@Param("u_nickname") String u_nickname,@Param("u_email") String u_email);
}