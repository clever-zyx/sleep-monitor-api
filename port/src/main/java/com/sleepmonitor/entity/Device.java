package com.sleepmonitor.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 设备实体类
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@TableName("devices")
@ApiModel(description = "设备信息")
public class Device {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "设备ID", example = "1")
    private Long id;

    @NotBlank(message = "设备唯一标识不能为空")
    @ApiModelProperty(value = "设备唯一标识", example = "SM001234567890")
    private String deviceId;

    @NotBlank(message = "设备名称不能为空")
    @ApiModelProperty(value = "设备名称", example = "睡眠监测垫")
    private String deviceName;

    @NotBlank(message = "设备类型不能为空")
    @ApiModelProperty(value = "设备类型", example = "sleep_monitor")
    private String deviceType;

    @ApiModelProperty(value = "设备型号", example = "SM-Pro-2024")
    private String model;

    @ApiModelProperty(value = "固件版本", example = "1.2.3")
    private String firmwareVersion;

    @ApiModelProperty(value = "MAC地址", example = "AA:BB:CC:DD:EE:FF")
    private String macAddress;

    @ApiModelProperty(value = "设备状态: 0-离线, 1-在线, 2-故障", example = "1")
    private Integer status;

    @ApiModelProperty(value = "电池电量(0-100)", example = "85")
    private Integer batteryLevel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后心跳时间")
    private LocalDateTime lastHeartbeat;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    // 构造函数
    public Device() {}

    public Device(String deviceId, String deviceName, String deviceType) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.status = 1; // 默认在线状态
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public LocalDateTime getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(LocalDateTime lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
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
