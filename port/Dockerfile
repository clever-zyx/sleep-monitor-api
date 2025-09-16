# 使用OpenJDK 8作为基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 安装Maven
RUN apk add --no-cache curl tar bash \
    && curl -fsSL https://archive.apache.org/dist/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz \
    | tar xzf - -C /opt \
    && ln -s /opt/apache-maven-3.8.6 /opt/maven \
    && ln -s /opt/maven/bin/mvn /usr/local/bin/mvn

# 设置Maven环境变量
ENV MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

# 复制项目文件
COPY pom.xml .
COPY src ./src
COPY database_schema.sql .

# 编译项目
RUN mvn clean package -DskipTests

# 暴露端口
EXPOSE 8080

# 启动应用
CMD ["java", "-jar", "target/sleep-monitor-api-1.0.0.jar"]
