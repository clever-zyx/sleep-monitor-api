package com.sleepmonitor.common;

/**
 * 响应码枚举
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
public enum ResultCode {

    // 通用响应码
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    PARAM_ERROR(400, "参数错误"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    
    // 认证相关
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    TOKEN_INVALID(4001, "Token无效"),
    TOKEN_EXPIRED(4002, "Token已过期"),
    LOGIN_FAILED(4003, "登录失败"),
    PASSWORD_ERROR(4004, "密码错误"),
    USER_NOT_FOUND(4005, "用户不存在"),
    USER_DISABLED(4006, "用户已被禁用"),
    USER_ALREADY_EXISTS(4007, "用户已存在"),
    
    // 设备相关
    DEVICE_NOT_FOUND(5001, "设备不存在"),
    DEVICE_OFFLINE(5002, "设备离线"),
    DEVICE_ALREADY_BOUND(5003, "设备已被绑定"),
    DEVICE_NOT_BOUND(5004, "设备未绑定"),
    DEVICE_BIND_FAILED(5005, "设备绑定失败"),
    
    // 数据相关
    DATA_NOT_FOUND(6001, "数据不存在"),
    DATA_SAVE_FAILED(6002, "数据保存失败"),
    DATA_UPDATE_FAILED(6003, "数据更新失败"),
    DATA_DELETE_FAILED(6004, "数据删除失败"),
    
    // 文件相关
    FILE_UPLOAD_FAILED(7001, "文件上传失败"),
    FILE_TYPE_ERROR(7002, "文件类型错误"),
    FILE_SIZE_EXCEEDED(7003, "文件大小超出限制"),
    
    // 业务相关
    SLEEP_RECORD_NOT_FOUND(8001, "睡眠记录不存在"),
    VITAL_SIGNS_NOT_FOUND(8002, "生命体征数据不存在"),
    MESSAGE_NOT_FOUND(8003, "消息不存在"),
    SETTING_NOT_FOUND(8004, "设置不存在");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
