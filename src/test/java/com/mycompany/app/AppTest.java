package com.mycompany.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 启动 Spring Boot 应用上下文，加载全部 Bean
@SpringBootTest
// 自动配置 MockMvc，用于模拟 HTTP 请求
@AutoConfigureMockMvc
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/")) // 模拟访问 /
                .andExpect(status().isOk()) // 期望返回状态 200
                .andExpect(content().string("Hello World!")); // 期望返回内容是 Hello World!
    }
}
