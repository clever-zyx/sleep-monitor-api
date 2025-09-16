package com.sleepmonitor.controller;

import com.sleepmonitor.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查控制器
 * 
 * @author Sleep Monitor Team
 * @version 1.0.0
 */
@Tag(name = "健康检查", description = "系统健康检查接口")
@RestController
@RequestMapping("/public")
public class HealthController {

    @Operation(summary = "健康检查", description = "检查系统是否正常运行")
    @GetMapping("/health")
    public Result<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "UP");
        data.put("timestamp", LocalDateTime.now());
        data.put("application", "sleep-monitor-api");
        data.put("version", "1.0.0");
        
        return Result.success("系统运行正常", data);
    }

    @Operation(summary = "API信息", description = "获取API基本信息")
    @GetMapping("/info")
    public Result<Map<String, Object>> info() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "睡眠监测应用API");
        data.put("description", "为睡眠监测应用提供后端API服务");
        data.put("version", "1.0.0");
        data.put("author", "Sleep Monitor Team");
        data.put("documentation", "/swagger-ui.html");
        
        return Result.success("API信息", data);
    }
}
