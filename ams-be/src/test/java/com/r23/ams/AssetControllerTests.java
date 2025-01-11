package com.r23.ams;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.*;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AssetControllerTests {
    @Autowired
    MockMvc mockMvc;
    String token = "";
    @BeforeAll
    void setup() throws Exception {
        System.out.println("******* Starting setup....");
        String username = "admin@tma.com.vn";
        String password = "123456";

        String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        response = response.replace("{\"token\":\"", "");
        token = response.replace("\"}", "");

        System.out.println("Bearer 1 " + token);
    }
    @Test
    void testExportExcelFile() throws Exception {
        System.out.println("Bearer 2 " + token);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/assets/export/excel").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
