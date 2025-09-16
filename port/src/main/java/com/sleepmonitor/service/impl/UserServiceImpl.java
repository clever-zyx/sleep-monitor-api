package com.sleepmonitor.service.impl;

import com.sleepmonitor.common.BusinessException;
import com.sleepmonitor.common.ResultCode;
import com.sleepmonitor.dto.auth.AuthResponse;
import com.sleepmonitor.dto.auth.LoginRequest;
import com.sleepmonitor.dto.auth.RegisterRequest;
import com.sleepmonitor.entity.User;
import com.sleepmonitor.mapper.UserMapper;
import com.sleepmonitor.service.UserService;
import com.sleepmonitor.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 用户服务实现类
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        logger.info("用户注册: {}", registerRequest.getUsername());

        // 验证密码确认
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "两次输入的密码不一致");
        }

        // 检查用户名是否已存在
        if (existsByUsername(registerRequest.getUsername())) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS, "用户名已存在");
        }

        // 检查邮箱是否已存在
        if (existsByEmail(registerRequest.getEmail())) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS, "邮箱已存在");
        }

        // 检查手机号是否已存在（如果提供了手机号）
        if (StringUtils.hasText(registerRequest.getPhone()) && existsByPhone(registerRequest.getPhone())) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS, "手机号已存在");
        }

        // 创建用户
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPhone(registerRequest.getPhone());
        user.setNickname(StringUtils.hasText(registerRequest.getNickname()) ? 
                        registerRequest.getNickname() : registerRequest.getUsername());
        user.setStatus(1); // 默认正常状态

        // 保存用户
        int result = userMapper.insert(user);
        if (result <= 0) {
            throw new BusinessException(ResultCode.DATA_SAVE_FAILED, "用户注册失败");
        }

        logger.info("用户注册成功: {}", user.getUsername());

        // 生成令牌并返回
        return generateAuthResponse(user);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        logger.info("用户登录: {}", loginRequest.getUsername());

        // 查找用户（支持用户名、邮箱、手机号登录）
        User user = findUserByLoginName(loginRequest.getUsername());
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND, "用户不存在");
        }

        // 检查用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException(ResultCode.USER_DISABLED, "用户已被禁用");
        }

        // 验证密码
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR, "密码错误");
        }

        logger.info("用户登录成功: {}", user.getUsername());

        // 生成令牌并返回
        return generateAuthResponse(user);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        logger.info("刷新令牌");

        try {
            // 验证刷新令牌
            if (!jwtUtil.validateTokenFormat(refreshToken)) {
                throw new BusinessException(ResultCode.TOKEN_INVALID, "刷新令牌无效");
            }

            // 从令牌中获取用户信息
            String username = jwtUtil.getUsernameFromToken(refreshToken);
            Long userId = jwtUtil.getUserIdFromToken(refreshToken);

            // 查找用户
            User user = findById(userId);
            if (user == null || !user.getUsername().equals(username)) {
                throw new BusinessException(ResultCode.USER_NOT_FOUND, "用户不存在");
            }

            // 检查用户状态
            if (user.getStatus() == 0) {
                throw new BusinessException(ResultCode.USER_DISABLED, "用户已被禁用");
            }

            logger.info("令牌刷新成功: {}", user.getUsername());

            // 生成新的令牌并返回
            return generateAuthResponse(user);

        } catch (Exception e) {
            logger.error("刷新令牌失败: {}", e.getMessage());
            throw new BusinessException(ResultCode.TOKEN_INVALID, "刷新令牌无效");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userMapper.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPhone(String phone) {
        return userMapper.existsByPhone(phone);
    }

    @Override
    public User updateUser(User user) {
        int result = userMapper.updateById(user);
        if (result <= 0) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED, "用户信息更新失败");
        }
        return userMapper.selectById(user.getId());
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = findById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND, "用户不存在");
        }

        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR, "原密码错误");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        int result = userMapper.updateById(user);
        
        return result > 0;
    }

    /**
     * 根据登录名查找用户（支持用户名、邮箱、手机号）
     */
    private User findUserByLoginName(String loginName) {
        // 先尝试用户名
        User user = userMapper.findByUsername(loginName);
        if (user != null) {
            return user;
        }

        // 再尝试邮箱
        if (loginName.contains("@")) {
            user = userMapper.findByEmail(loginName);
            if (user != null) {
                return user;
            }
        }

        // 最后尝试手机号
        if (loginName.matches("^1[3-9]\\d{9}$")) {
            user = userMapper.findByPhone(loginName);
        }

        return user;
    }

    /**
     * 生成认证响应
     */
    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getUsername());
        Long expiresIn = jwtExpiration / 1000; // 转换为秒

        return new AuthResponse(accessToken, refreshToken, expiresIn, user);
    }
}
