package com.sleepmonitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleepmonitor.entity.UserDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户设备关联Mapper接口
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@Mapper
public interface UserDeviceMapper extends BaseMapper<UserDevice> {

    /**
     * 根据用户ID查询绑定的设备列表
     * 
     * @param userId 用户ID
     * @return 用户设备关联列表
     */
    @Select("SELECT * FROM user_devices WHERE user_id = #{userId} AND status = 1 ORDER BY is_primary DESC, bind_time DESC")
    List<UserDevice> findByUserId(@Param("userId") Long userId);

    /**
     * 根据设备ID查询绑定的用户列表
     * 
     * @param deviceId 设备ID
     * @return 用户设备关联列表
     */
    @Select("SELECT * FROM user_devices WHERE device_id = #{deviceId} AND status = 1 ORDER BY bind_time DESC")
    List<UserDevice> findByDeviceId(@Param("deviceId") Long deviceId);

    /**
     * 查询用户设备绑定关系
     * 
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @return 用户设备关联信息
     */
    @Select("SELECT * FROM user_devices WHERE user_id = #{userId} AND device_id = #{deviceId}")
    UserDevice findByUserIdAndDeviceId(@Param("userId") Long userId, @Param("deviceId") Long deviceId);

    /**
     * 查询用户的主设备
     * 
     * @param userId 用户ID
     * @return 主设备关联信息
     */
    @Select("SELECT * FROM user_devices WHERE user_id = #{userId} AND is_primary = 1 AND status = 1")
    UserDevice findPrimaryDevice(@Param("userId") Long userId);

    /**
     * 设置主设备
     * 
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @return 更新行数
     */
    @Update("UPDATE user_devices SET is_primary = CASE WHEN device_id = #{deviceId} THEN 1 ELSE 0 END, updated_at = NOW() WHERE user_id = #{userId} AND status = 1")
    int setPrimaryDevice(@Param("userId") Long userId, @Param("deviceId") Long deviceId);

    /**
     * 解绑设备
     * 
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @return 更新行数
     */
    @Update("UPDATE user_devices SET status = 0, unbind_time = NOW(), updated_at = NOW() WHERE user_id = #{userId} AND device_id = #{deviceId}")
    int unbindDevice(@Param("userId") Long userId, @Param("deviceId") Long deviceId);

    /**
     * 检查用户是否已绑定设备
     * 
     * @param userId 用户ID
     * @param deviceId 设备ID
     * @return 已绑定返回true，否则返回false
     */
    @Select("SELECT COUNT(*) > 0 FROM user_devices WHERE user_id = #{userId} AND device_id = #{deviceId} AND status = 1")
    boolean isDeviceBound(@Param("userId") Long userId, @Param("deviceId") Long deviceId);

    /**
     * 统计用户绑定的设备数量
     * 
     * @param userId 用户ID
     * @return 设备数量
     */
    @Select("SELECT COUNT(*) FROM user_devices WHERE user_id = #{userId} AND status = 1")
    int countByUserId(@Param("userId") Long userId);
}
