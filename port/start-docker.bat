@echo off
chcp 65001 >nul
echo ================================
echo 睡眠监测应用Docker启动脚本
echo ================================
echo.

echo 正在检查Docker环境...
docker --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ 未找到Docker环境
    echo.
    echo 请先安装Docker Desktop：
    echo 1. 访问：https://www.docker.com/products/docker-desktop
    echo 2. 下载并安装Docker Desktop
    echo 3. 启动Docker Desktop
    echo 4. 重新运行此脚本
    echo.
    pause
    exit /b 1
)

echo ✅ Docker环境检查通过
docker --version
echo.

echo 正在检查Docker Compose...
docker-compose --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ 未找到Docker Compose
    echo Docker Compose通常随Docker Desktop一起安装
    echo 请确保Docker Desktop已正确安装
    pause
    exit /b 1
)

echo ✅ Docker Compose检查通过
docker-compose --version
echo.

echo 📋 即将启动的服务：
echo 1. 🗄️  MySQL 8.0 数据库 (端口: 3306)
echo 2. 🔴 Redis 6.2 缓存 (端口: 6379)
echo 3. ☕ Spring Boot API (端口: 8080)
echo.

echo 🔧 配置信息：
echo - 数据库名：sleep_monitor
echo - 数据库用户：sleepmonitor
echo - 数据库密码：123456
echo - API地址：http://localhost:8080/api
echo.

set /p continue="是否继续启动？(y/n): "
if /i not "%continue%"=="y" (
    echo 启动已取消
    pause
    exit /b 0
)

echo.
echo 🚀 正在启动Docker容器...
echo 这可能需要几分钟时间，请耐心等待...
echo.

REM 停止并删除现有容器
echo 清理现有容器...
docker-compose down

REM 构建并启动服务
echo 构建并启动服务...
docker-compose up --build -d

if %errorlevel% neq 0 (
    echo ❌ 启动失败
    echo 请检查Docker日志：docker-compose logs
    pause
    exit /b 1
)

echo.
echo ✅ 服务启动成功！
echo.

echo 等待服务完全启动...
timeout /t 30 /nobreak >nul

echo 🌐 访问地址：
echo - 健康检查：http://localhost:8080/api/public/health
echo - API文档：http://localhost:8080/api/swagger-ui.html
echo - API JSON：http://localhost:8080/api/v3/api-docs
echo.

echo 📊 查看服务状态：
docker-compose ps

echo.
echo 📝 常用命令：
echo - 查看日志：docker-compose logs -f
echo - 停止服务：docker-compose down
echo - 重启服务：docker-compose restart
echo.

echo 正在打开API文档...
start http://localhost:8080/api/swagger-ui.html

echo.
echo 按任意键退出...
pause >nul
