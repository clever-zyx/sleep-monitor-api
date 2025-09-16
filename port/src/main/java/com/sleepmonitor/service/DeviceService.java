package com.sleepmonitor.service;

import com.sleepmonitor.dto.device.DeviceBindRequest;
import com.sleepmonitor.dto.device.DeviceResponse;
import com.sleepmonitor.entity.Device;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备服务接口
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
public interface DeviceService {

    /**
     * 绑定设备
     * 
     * @param userId 用户ID
     * @param bindRequest 绑定请求
     * @return 设备响应信息
     */
    DeviceResponse bindDevice(Long userId, DeviceBindRequest bindRequest);

    /**
     * 解绑设备
     * 
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @return 解绑成功返回true，否则返回false
     */
    boolean unbindDevice(Long userId, Long deviceId);

    /**
     * 获取用户绑定的设备列表
     * 
     * @param userId 用户ID
     * @return 设备列表
     */
    List<DeviceResponse> getUserDevices(Long userId);

    /**
     * 获取用户的主设备
     * 
     * @param userId 用户ID
     * @return 主设备信息
     */
    DeviceResponse getPrimaryDevice(Long userId);

    /**
     * 设置主设备
     * 
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @return 设置成功返回true，否则返回false
     */
    boolean setPrimaryDevice(Long userId, Long deviceId);

    /**
     * 根据设备唯一标识查询设备
     * 
     * @param deviceId 设备唯一标识
     * @return 设备信息
     */
    Device findByDeviceId(String deviceId);

    /**
     * 根据设备ID查询设备
     * 
     * @param id 设备ID
     * @return 设备信息
     */
    Device findById(Long id);

    /**
     * 更新设备心跳
     * 
     * @param deviceId 设备唯一标识
     * @param heartbeatTime 心跳时间
     * @param batteryLevel 电池电量
     * @return 更新成功返回true，否则返回false
     */
    boolean updateHeartbeat(String deviceId, LocalDateTime heartbeatTime, Integer batteryLevel);

    /**
     * 更新设备状态
     * 
     * @param deviceId 设备唯一标识
     * @param status 设备状态
     * @return 更新成功返回true，否则返回false
     */
    boolean updateDeviceStatus(String deviceId, Integer status);

    /**
     * 检查设备是否存在
     * 
     * @param deviceId 设备唯一标识
     * @return 存在返回true，否则返回false
     */
    boolean existsByDeviceId(String deviceId);

    /**
     * 检查用户是否已绑定设备
     * 
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @return 已绑定返回true，否则返回false
     */
    boolean isDeviceBound(Long userId, Long deviceId);

    /**
     * 获取设备详细信息
     * 
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @return 设备详细信息
     */
    DeviceResponse getDeviceDetail(Long userId, Long deviceId);

    /**
     * 更新设备别名
     * 
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @param deviceAlias 设备别名
     * @return 更新成功返回true，否则返回false
     */
    boolean updateDeviceAlias(Long userId, Long deviceId, String deviceAlias);
}
