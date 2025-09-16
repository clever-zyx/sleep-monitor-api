package com.sleepmonitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleepmonitor.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户Mapper接口
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT * FROM users WHERE username = #{username} AND status = 1")
    User findByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户信息
     */
    @Select("SELECT * FROM users WHERE email = #{email} AND status = 1")
    User findByEmail(@Param("email") String email);

    /**
     * 根据手机号查询用户
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    @Select("SELECT * FROM users WHERE phone = #{phone} AND status = 1")
    User findByPhone(@Param("phone") String phone);

    /**
     * 检查用户名是否存在
     * 
     * @param username 用户名
     * @return 存在返回true，否则返回false
     */
    @Select("SELECT COUNT(*) > 0 FROM users WHERE username = #{username}")
    boolean existsByUsername(@Param("username") String username);

    /**
     * 检查邮箱是否存在
     * 
     * @param email 邮箱
     * @return 存在返回true，否则返回false
     */
    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    boolean existsByEmail(@Param("email") String email);

    /**
     * 检查手机号是否存在
     * 
     * @param phone 手机号
     * @return 存在返回true，否则返回false
     */
    @Select("SELECT COUNT(*) > 0 FROM users WHERE phone = #{phone}")
    boolean existsByPhone(@Param("phone") String phone);
}
