-- 睡眠监测应用数据库表结构设计
-- 创建数据库
CREATE DATABASE IF NOT EXISTS sleep_monitor DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sleep_monitor;

-- 用户表
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
    password VARCHAR(255) NOT NULL COMMENT '密码(加密)',
    phone VARCHAR(20) COMMENT '手机号',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar_url VARCHAR(255) COMMENT '头像URL',
    gender TINYINT DEFAULT 0 COMMENT '性别: 0-未知, 1-男, 2-女',
    birth_date DATE COMMENT '出生日期',
    height DECIMAL(5,2) COMMENT '身高(cm)',
    weight DECIMAL(5,2) COMMENT '体重(kg)',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_phone (phone)
) COMMENT '用户表';

-- 设备表
CREATE TABLE devices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    device_id VARCHAR(100) NOT NULL UNIQUE COMMENT '设备唯一标识',
    device_name VARCHAR(100) NOT NULL COMMENT '设备名称',
    device_type VARCHAR(50) NOT NULL COMMENT '设备类型',
    model VARCHAR(100) COMMENT '设备型号',
    firmware_version VARCHAR(50) COMMENT '固件版本',
    mac_address VARCHAR(17) COMMENT 'MAC地址',
    status TINYINT DEFAULT 1 COMMENT '设备状态: 0-离线, 1-在线, 2-故障',
    battery_level TINYINT COMMENT '电池电量(0-100)',
    last_heartbeat TIMESTAMP COMMENT '最后心跳时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_device_id (device_id),
    INDEX idx_status (status),
    INDEX idx_last_heartbeat (last_heartbeat)
) COMMENT '设备表';

-- 用户设备关联表
CREATE TABLE user_devices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    device_id BIGINT NOT NULL,
    device_alias VARCHAR(100) COMMENT '设备别名',
    is_primary TINYINT DEFAULT 0 COMMENT '是否为主设备: 0-否, 1-是',
    bind_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '绑定时间',
    unbind_time TIMESTAMP NULL COMMENT '解绑时间',
    status TINYINT DEFAULT 1 COMMENT '绑定状态: 0-已解绑, 1-已绑定',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_device (user_id, device_id),
    INDEX idx_user_id (user_id),
    INDEX idx_device_id (device_id)
) COMMENT '用户设备关联表';

-- 睡眠记录表
CREATE TABLE sleep_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    device_id BIGINT NOT NULL,
    sleep_date DATE NOT NULL COMMENT '睡眠日期',
    bedtime TIMESTAMP COMMENT '上床时间',
    sleep_time TIMESTAMP COMMENT '入睡时间',
    wake_time TIMESTAMP COMMENT '醒来时间',
    get_up_time TIMESTAMP COMMENT '起床时间',
    total_sleep_duration INT COMMENT '总睡眠时长(分钟)',
    deep_sleep_duration INT COMMENT '深睡时长(分钟)',
    light_sleep_duration INT COMMENT '浅睡时长(分钟)',
    rem_sleep_duration INT COMMENT 'REM睡眠时长(分钟)',
    awake_duration INT COMMENT '清醒时长(分钟)',
    sleep_efficiency DECIMAL(5,2) COMMENT '睡眠效率(%)',
    sleep_quality_score TINYINT COMMENT '睡眠质量评分(0-100)',
    turn_over_count INT DEFAULT 0 COMMENT '翻身次数',
    snore_duration INT DEFAULT 0 COMMENT '打鼾时长(分钟)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_date (user_id, sleep_date),
    INDEX idx_user_id (user_id),
    INDEX idx_sleep_date (sleep_date),
    INDEX idx_device_id (device_id)
) COMMENT '睡眠记录表';

-- 睡眠阶段详细数据表
CREATE TABLE sleep_stage_details (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sleep_record_id BIGINT NOT NULL,
    stage_time TIMESTAMP NOT NULL COMMENT '阶段时间',
    sleep_stage TINYINT NOT NULL COMMENT '睡眠阶段: 0-清醒, 1-浅睡, 2-深睡, 3-REM',
    duration INT NOT NULL COMMENT '持续时长(秒)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sleep_record_id) REFERENCES sleep_records(id) ON DELETE CASCADE,
    INDEX idx_sleep_record_id (sleep_record_id),
    INDEX idx_stage_time (stage_time)
) COMMENT '睡眠阶段详细数据表';

-- 生命体征记录表
CREATE TABLE vital_signs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    device_id BIGINT NOT NULL,
    record_time TIMESTAMP NOT NULL COMMENT '记录时间',
    heart_rate TINYINT COMMENT '心率(bpm)',
    respiratory_rate TINYINT COMMENT '呼吸率(次/分)',
    body_temperature DECIMAL(4,2) COMMENT '体温(°C)',
    blood_oxygen TINYINT COMMENT '血氧饱和度(%)',
    blood_pressure_systolic SMALLINT COMMENT '收缩压(mmHg)',
    blood_pressure_diastolic SMALLINT COMMENT '舒张压(mmHg)',
    stress_level TINYINT COMMENT '压力水平(0-100)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_device_id (device_id),
    INDEX idx_record_time (record_time)
) COMMENT '生命体征记录表';

-- 设备消息表
CREATE TABLE device_messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    device_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    message_type TINYINT NOT NULL COMMENT '消息类型: 1-设备状态, 2-电量提醒, 3-异常告警, 4-系统通知',
    title VARCHAR(100) NOT NULL COMMENT '消息标题',
    content TEXT COMMENT '消息内容',
    level TINYINT DEFAULT 1 COMMENT '消息级别: 1-普通, 2-重要, 3-紧急',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读: 0-未读, 1-已读',
    read_time TIMESTAMP NULL COMMENT '阅读时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (device_id) REFERENCES devices(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_device_id (device_id),
    INDEX idx_user_id (user_id),
    INDEX idx_message_type (message_type),
    INDEX idx_is_read (is_read),
    INDEX idx_created_at (created_at)
) COMMENT '设备消息表';

-- 系统设置表
CREATE TABLE system_settings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    setting_key VARCHAR(100) NOT NULL COMMENT '设置键',
    setting_value TEXT COMMENT '设置值',
    setting_type VARCHAR(50) DEFAULT 'string' COMMENT '设置类型: string, number, boolean, json',
    description VARCHAR(255) COMMENT '设置描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_setting (user_id, setting_key),
    INDEX idx_user_id (user_id),
    INDEX idx_setting_key (setting_key)
) COMMENT '系统设置表';

-- 用户反馈表
CREATE TABLE user_feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    feedback_type TINYINT NOT NULL COMMENT '反馈类型: 1-功能建议, 2-问题反馈, 3-使用体验',
    title VARCHAR(200) NOT NULL COMMENT '反馈标题',
    content TEXT NOT NULL COMMENT '反馈内容',
    contact_info VARCHAR(100) COMMENT '联系方式',
    status TINYINT DEFAULT 0 COMMENT '处理状态: 0-待处理, 1-处理中, 2-已解决, 3-已关闭',
    reply TEXT COMMENT '回复内容',
    reply_time TIMESTAMP NULL COMMENT '回复时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_feedback_type (feedback_type),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) COMMENT '用户反馈表';
