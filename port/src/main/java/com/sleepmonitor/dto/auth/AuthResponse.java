package com.sleepmonitor.dto.auth;

import com.sleepmonitor.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 认证响应DTO
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@Schema(description = "认证响应")
public class AuthResponse {

    @Schema(description = "访问令牌", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String accessToken;

    @Schema(description = "刷新令牌", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String refreshToken;

    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "过期时间(秒)", example = "86400")
    private Long expiresIn;

    @Schema(description = "用户信息")
    private UserInfo userInfo;

    public AuthResponse() {}

    public AuthResponse(String accessToken, String refreshToken, Long expiresIn, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.userInfo = new UserInfo(user);
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * 用户信息内部类
     */
    @Schema(description = "用户信息")
    public static class UserInfo {
        @Schema(description = "用户ID", example = "1")
        private Long id;

        @Schema(description = "用户名", example = "john_doe")
        private String username;

        @Schema(description = "邮箱", example = "john@example.com")
        private String email;

        @Schema(description = "昵称", example = "小明")
        private String nickname;

        @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
        private String avatarUrl;

        @Schema(description = "性别: 0-未知, 1-男, 2-女", example = "1")
        private Integer gender;

        public UserInfo() {}

        public UserInfo(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.avatarUrl = user.getAvatarUrl();
            this.gender = user.getGender();
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }
    }
}
