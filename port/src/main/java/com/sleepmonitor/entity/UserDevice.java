package com.sleepmonitor.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 用户设备关联实体类
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@TableName("user_devices")
@ApiModel(description = "用户设备关联信息")
public class UserDevice {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "关联ID", example = "1")
    private Long id;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;

    @NotNull(message = "设备ID不能为空")
    @ApiModelProperty(value = "设备ID", example = "1")
    private Long deviceId;

    @ApiModelProperty(value = "设备别名", example = "卧室睡眠垫")
    private String deviceAlias;

    @ApiModelProperty(value = "是否为主设备: 0-否, 1-是", example = "1")
    private Integer isPrimary;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "绑定时间")
    private LocalDateTime bindTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "解绑时间")
    private LocalDateTime unbindTime;

    @ApiModelProperty(value = "绑定状态: 0-已解绑, 1-已绑定", example = "1")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    // 构造函数
    public UserDevice() {}

    public UserDevice(Long userId, Long deviceId) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.isPrimary = 0;
        this.status = 1;
        this.bindTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceAlias() {
        return deviceAlias;
    }

    public void setDeviceAlias(String deviceAlias) {
        this.deviceAlias = deviceAlias;
    }

    public Integer getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Integer isPrimary) {
        this.isPrimary = isPrimary;
    }

    public LocalDateTime getBindTime() {
        return bindTime;
    }

    public void setBindTime(LocalDateTime bindTime) {
        this.bindTime = bindTime;
    }

    public LocalDateTime getUnbindTime() {
        return unbindTime;
    }

    public void setUnbindTime(LocalDateTime unbindTime) {
        this.unbindTime = unbindTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
