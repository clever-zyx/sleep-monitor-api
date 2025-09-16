package com.sleepmonitor.dto.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 设备绑定请求DTO
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@ApiModel(description = "设备绑定请求")
public class DeviceBindRequest {

    @NotBlank(message = "设备唯一标识不能为空")
    @ApiModelProperty(value = "设备唯一标识", example = "SM001234567890", required = true)
    private String deviceId;

    @ApiModelProperty(value = "设备别名", example = "卧室睡眠垫")
    private String deviceAlias;

    @ApiModelProperty(value = "是否设为主设备", example = "false")
    private Boolean isPrimary = false;

    public DeviceBindRequest() {}

    public DeviceBindRequest(String deviceId) {
        this.deviceId = deviceId;
    }

    // Getters and Setters
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
}
