package com.sleepmonitor.controller;

import com.sleepmonitor.common.Result;
import com.sleepmonitor.dto.auth.AuthResponse;
import com.sleepmonitor.dto.auth.LoginRequest;
import com.sleepmonitor.dto.auth.RegisterRequest;
import com.sleepmonitor.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 认证控制器
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@Tag(name = "认证管理", description = "用户认证相关接口")
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "用户注册", description = "新用户注册接口")
    @PostMapping("/register")
    public Result<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        logger.info("用户注册请求: {}", registerRequest.getUsername());
        
        AuthResponse authResponse = userService.register(registerRequest);
        
        return Result.success("注册成功", authResponse);
    }

    @Operation(summary = "用户登录", description = "用户登录接口，支持用户名/邮箱/手机号登录")
    @PostMapping("/login")
    public Result<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("用户登录请求: {}", loginRequest.getUsername());
        
        AuthResponse authResponse = userService.login(loginRequest);
        
        return Result.success("登录成功", authResponse);
    }

    @Operation(summary = "刷新令牌", description = "使用刷新令牌获取新的访问令牌")
    @PostMapping("/refresh")
    public Result<AuthResponse> refreshToken(
            @Parameter(description = "刷新令牌", required = true)
            @NotBlank(message = "刷新令牌不能为空")
            @RequestParam String refreshToken) {
        logger.info("刷新令牌请求");
        
        AuthResponse authResponse = userService.refreshToken(refreshToken);
        
        return Result.success("令牌刷新成功", authResponse);
    }

    @Operation(summary = "检查用户名", description = "检查用户名是否可用")
    @GetMapping("/check-username")
    public Result<Boolean> checkUsername(
            @Parameter(description = "用户名", required = true)
            @NotBlank(message = "用户名不能为空")
            @RequestParam String username) {
        logger.info("检查用户名: {}", username);
        
        boolean exists = userService.existsByUsername(username);
        
        return Result.success("检查完成", !exists);
    }

    @Operation(summary = "检查邮箱", description = "检查邮箱是否可用")
    @GetMapping("/check-email")
    public Result<Boolean> checkEmail(
            @Parameter(description = "邮箱", required = true)
            @NotBlank(message = "邮箱不能为空")
            @RequestParam String email) {
        logger.info("检查邮箱: {}", email);
        
        boolean exists = userService.existsByEmail(email);
        
        return Result.success("检查完成", !exists);
    }

    @Operation(summary = "检查手机号", description = "检查手机号是否可用")
    @GetMapping("/check-phone")
    public Result<Boolean> checkPhone(
            @Parameter(description = "手机号", required = true)
            @NotBlank(message = "手机号不能为空")
            @RequestParam String phone) {
        logger.info("检查手机号: {}", phone);
        
        boolean exists = userService.existsByPhone(phone);
        
        return Result.success("检查完成", !exists);
    }

    @Operation(summary = "用户登出", description = "用户登出接口（客户端清除令牌即可）")
    @PostMapping("/logout")
    public Result<Void> logout() {
        logger.info("用户登出请求");
        
        // 在无状态JWT认证中，登出通常由客户端处理（清除本地存储的令牌）
        // 如果需要服务端登出，可以维护一个黑名单来存储已登出的令牌
        
        return Result.success("登出成功");
    }
}
