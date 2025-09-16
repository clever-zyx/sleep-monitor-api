package com.sleepmonitor.dto.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sleepmonitor.entity.Device;
import com.sleepmonitor.entity.UserDevice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * 设备响应DTO
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@ApiModel(description = "设备信息响应")
public class DeviceResponse {

    @ApiModelProperty(value = "设备ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "设备唯一标识", example = "SM001234567890")
    private String deviceId;

    @ApiModelProperty(value = "设备名称", example = "睡眠监测垫")
    private String deviceName;

    @ApiModelProperty(value = "设备类型", example = "sleep_monitor")
    private String deviceType;

    @ApiModelProperty(value = "设备型号", example = "SM-Pro-2024")
    private String model;

    @ApiModelProperty(value = "固件版本", example = "1.2.3")
    private String firmwareVersion;

    @ApiModelProperty(value = "设备状态: 0-离线, 1-在线, 2-故障", example = "1")
    private Integer status;

    @ApiModelProperty(value = "设备状态描述", example = "在线")
    private String statusText;

    @ApiModelProperty(value = "电池电量(0-100)", example = "85")
    private Integer batteryLevel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后心跳时间")
    private LocalDateTime lastHeartbeat;

    @ApiModelProperty(value = "设备别名", example = "卧室睡眠垫")
    private String deviceAlias;

    @ApiModelProperty(value = "是否为主设备", example = "true")
    private Boolean isPrimary;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "绑定时间")
    private LocalDateTime bindTime;

    public DeviceResponse() {}

    public DeviceResponse(Device device, UserDevice userDevice) {
        this.id = device.getId();
        this.deviceId = device.getDeviceId();
        this.deviceName = device.getDeviceName();
        this.deviceType = device.getDeviceType();
        this.model = device.getModel();
        this.firmwareVersion = device.getFirmwareVersion();
        this.status = device.getStatus();
        this.statusText = getStatusText(device.getStatus());
        this.batteryLevel = device.getBatteryLevel();
        this.lastHeartbeat = device.getLastHeartbeat();
        
        if (userDevice != null) {
            this.deviceAlias = userDevice.getDeviceAlias();
            this.isPrimary = userDevice.getIsPrimary() == 1;
            this.bindTime = userDevice.getBindTime();
        }
    }

    private String getStatusText(Integer status) {
        if (status == null) return "未知";
        switch (status) {
            case 0: return "离线";
            case 1: return "在线";
            case 2: return "故障";
            default: return "未知";
        }
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
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

    public String getDeviceAlias() {
        return deviceAlias;
    }

    public void setDeviceAlias(String deviceAlias) {
        this.deviceAlias = deviceAlias;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public LocalDateTime getBindTime() {
        return bindTime;
    }

    public void setBindTime(LocalDateTime bindTime) {
        this.bindTime = bindTime;
    }
}
