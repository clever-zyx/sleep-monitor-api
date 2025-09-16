# 睡眠监测应用后端API

基于Spring Boot开发的睡眠监测应用后端API，支持用户认证、设备管理、睡眠数据分析等功能。

## 技术栈

- Spring Boot 2.7.18 + Java 8
- MySQL 8.0 + MyBatis Plus
- Redis + JWT认证
- Swagger API文档

## 功能模块

- 用户认证（注册/登录/JWT）
- 设备管理（绑定/监控/状态）

- 睡眠数据（记录/分析/报告）
- 生命体征（心率/呼吸/体温/血氧）
- 消息通知（状态/报告推送）

## 快速启动

### 方式一：Docker运行（推荐）
```bash
# 启动所有服务（MySQL + Redis + API）
docker-compose up -d
```

### 方式二：本地运行
1. 安装Java 8+ 和 MySQL 8.0+
2. 创建数据库：`CREATE DATABASE sleep_monitor;`
3. 导入表结构：`mysql -u root -p sleep_monitor < database_schema.sql`
4. 运行项目：`mvn spring-boot:run` 或 `.\start-app.bat`

## 访问地址

启动成功后访问：
- API文档：http://localhost:8080/api/swagger-ui.html
- 健康检查：http://localhost:8080/api/public/health

## 主要接口

- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `GET /api/public/health` - 健康检查
