package com.sleepmonitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleepmonitor.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备Mapper接口
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    /**
     * 根据设备唯一标识查询设备
     * 
     * @param deviceId 设备唯一标识
     * @return 设备信息
     */
    @Select("SELECT * FROM devices WHERE device_id = #{deviceId}")
    Device findByDeviceId(@Param("deviceId") String deviceId);

    /**
     * 根据设备类型查询设备列表
     * 
     * @param deviceType 设备类型
     * @return 设备列表
     */
    @Select("SELECT * FROM devices WHERE device_type = #{deviceType} ORDER BY created_at DESC")
    List<Device> findByDeviceType(@Param("deviceType") String deviceType);

    /**
     * 根据设备状态查询设备列表
     * 
     * @param status 设备状态
     * @return 设备列表
     */
    @Select("SELECT * FROM devices WHERE status = #{status} ORDER BY created_at DESC")
    List<Device> findByStatus(@Param("status") Integer status);

    /**
     * 更新设备心跳时间
     * 
     * @param deviceId 设备唯一标识
     * @param heartbeatTime 心跳时间
     * @return 更新行数
     */
    @Update("UPDATE devices SET last_heartbeat = #{heartbeatTime}, updated_at = NOW() WHERE device_id = #{deviceId}")
    int updateHeartbeat(@Param("deviceId") String deviceId, @Param("heartbeatTime") LocalDateTime heartbeatTime);

    /**
     * 更新设备状态
     * 
     * @param deviceId 设备唯一标识
     * @param status 设备状态
     * @return 更新行数
     */
    @Update("UPDATE devices SET status = #{status}, updated_at = NOW() WHERE device_id = #{deviceId}")
    int updateStatus(@Param("deviceId") String deviceId, @Param("status") Integer status);

    /**
     * 更新设备电池电量
     * 
     * @param deviceId 设备唯一标识
     * @param batteryLevel 电池电量
     * @return 更新行数
     */
    @Update("UPDATE devices SET battery_level = #{batteryLevel}, updated_at = NOW() WHERE device_id = #{deviceId}")
    int updateBatteryLevel(@Param("deviceId") String deviceId, @Param("batteryLevel") Integer batteryLevel);

    /**
     * 检查设备唯一标识是否存在
     * 
     * @param deviceId 设备唯一标识
     * @return 存在返回true，否则返回false
     */
    @Select("SELECT COUNT(*) > 0 FROM devices WHERE device_id = #{deviceId}")
    boolean existsByDeviceId(@Param("deviceId") String deviceId);
}
