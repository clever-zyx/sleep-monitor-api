@echo off
echo 正在启动睡眠监测应用API...
echo.

REM 检查Java环境
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误: 未找到Java环境，请确保已安装Java 8或更高版本
    echo 请设置JAVA_HOME环境变量并将Java添加到PATH中
    pause
    exit /b 1
)

echo Java环境检查通过
echo.

REM 检查是否存在编译后的jar文件
if exist "target\sleep-monitor-api-1.0.0.jar" (
    echo 找到编译后的jar文件，正在启动...
    java -jar target\sleep-monitor-api-1.0.0.jar
) else (
    echo 未找到编译后的jar文件
    echo 请先运行以下命令编译项目:
    echo mvn clean package -DskipTests
    echo.
    echo 或者如果您有Maven环境，可以直接运行:
    echo mvn spring-boot:run
    echo.
    pause
)
