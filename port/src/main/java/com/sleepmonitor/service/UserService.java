package com.sleepmonitor.service;

import com.sleepmonitor.dto.auth.AuthResponse;
import com.sleepmonitor.dto.auth.LoginRequest;
import com.sleepmonitor.dto.auth.RegisterRequest;
import com.sleepmonitor.entity.User;

/**
 * 用户服务接口
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
public interface UserService {

    /**
     * 用户注册
     * 
     * @param registerRequest 注册请求
     * @return 认证响应
     */
    AuthResponse register(RegisterRequest registerRequest);

    /**
     * 用户登录
     * 
     * @param loginRequest 登录请求
     * @return 认证响应
     */
    AuthResponse login(LoginRequest loginRequest);

    /**
     * 刷新令牌
     * 
     * @param refreshToken 刷新令牌
     * @return 认证响应
     */
    AuthResponse refreshToken(String refreshToken);

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 根据用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    User findById(Long userId);

    /**
     * 检查用户名是否存在
     * 
     * @param username 用户名
     * @return 存在返回true，否则返回false
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     * 
     * @param email 邮箱
     * @return 存在返回true，否则返回false
     */
    boolean existsByEmail(String email);

    /**
     * 检查手机号是否存在
     * 
     * @param phone 手机号
     * @return 存在返回true，否则返回false
     */
    boolean existsByPhone(String phone);

    /**
     * 更新用户信息
     * 
     * @param user 用户信息
     * @return 更新后的用户信息
     */
    User updateUser(User user);

    /**
     * 修改密码
     * 
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改成功返回true，否则返回false
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);
}
