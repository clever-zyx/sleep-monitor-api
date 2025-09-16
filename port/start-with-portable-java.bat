@echo off
chcp 65001 >nul
echo ================================
echo 睡眠监测应用API启动脚本
echo ================================
echo.

REM 设置便携版Java路径
set JAVA_HOME=%~dp0jdk8
set PATH=%JAVA_HOME%\bin;%PATH%

echo 正在检查Java环境...
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo.
    echo ❌ 未找到便携版Java环境
    echo.
    echo 请按照以下步骤配置：
    echo 1. 下载JDK 8便携版：https://github.com/adoptium/temurin8-binaries/releases
    echo 2. 选择 OpenJDK8U-jdk_x64_windows_hotspot_xxx.zip
    echo 3. 解压到项目根目录，重命名文件夹为 "jdk8"
    echo 4. 确保路径为：%~dp0jdk8\bin\java.exe
    echo.
    echo 或者安装系统Java环境，然后运行 start-app.bat
    echo.
    pause
    exit /b 1
)

echo ✅ 找到便携版Java环境
%JAVA_HOME%\bin\java -version
echo.

echo 正在检查项目文件...
if not exist "src\main\java\com\sleepmonitor\SleepMonitorApplication.java" (
    echo ❌ 未找到项目源码文件
    echo 请确保在正确的项目目录中运行此脚本
    pause
    exit /b 1
)

echo ✅ 项目文件检查通过
echo.

echo 正在检查数据库配置...
if not exist "database_schema.sql" (
    echo ⚠️  未找到数据库脚本文件
    echo 请确保 database_schema.sql 文件存在
)

echo.
echo 📋 启动前准备清单：
echo 1. ✅ Java环境已配置
echo 2. ✅ 项目文件完整
echo 3. ⚠️  请确保MySQL已安装并运行
echo 4. ⚠️  请确保已创建数据库：sleep_monitor
echo 5. ⚠️  请确保已导入数据库表结构
echo.

echo 如果数据库未配置，请：
echo 1. 安装MySQL
echo 2. 创建数据库：CREATE DATABASE sleep_monitor;
echo 3. 导入表结构：mysql -u root -p sleep_monitor ^< database_schema.sql
echo.

set /p continue="是否继续启动应用？(y/n): "
if /i not "%continue%"=="y" (
    echo 启动已取消
    pause
    exit /b 0
)

echo.
echo 🚀 正在启动应用...
echo.

REM 检查是否有编译好的jar文件
if exist "target\sleep-monitor-api-1.0.0.jar" (
    echo 使用已编译的jar文件启动...
    %JAVA_HOME%\bin\java -jar target\sleep-monitor-api-1.0.0.jar
) else (
    echo 未找到编译好的jar文件
    echo.
    echo 请选择启动方式：
    echo 1. 使用Maven编译并运行（需要Maven环境）
    echo 2. 手动编译运行（需要下载依赖）
    echo 3. 退出
    echo.
    set /p choice="请选择 (1-3): "
    
    if "%choice%"=="1" (
        echo 正在使用Maven编译...
        mvn clean package -DskipTests
        if exist "target\sleep-monitor-api-1.0.0.jar" (
            echo 编译成功，正在启动...
            %JAVA_HOME%\bin\java -jar target\sleep-monitor-api-1.0.0.jar
        ) else (
            echo 编译失败，请检查Maven环境
            pause
        )
    ) else if "%choice%"=="2" (
        echo 手动编译模式需要先下载所有依赖jar包
        echo 这比较复杂，建议安装Maven环境
        echo 或者使用在线IDE如Gitpod运行项目
        pause
    ) else (
        echo 启动已取消
        pause
    )
)

echo.
echo 如果启动成功，请访问：
echo 🌐 健康检查：http://localhost:8080/api/public/health
echo 📚 API文档：http://localhost:8080/api/swagger-ui.html
echo.
pause
