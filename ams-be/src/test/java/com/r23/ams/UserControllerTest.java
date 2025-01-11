package com.r23.ams;
import com.r23.ams.service.user.AppUserServiceImpl;
import org.apache.commons.io.IOUtils;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.jupiter.api.*;
//import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.data.repository.init.ResourceReader.Type.JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    String documentRoot = "./home/static/images/";
    List<Path> filesToBeDeleted = new ArrayList<>();
    String token = "";
    @Autowired
    private AppUserServiceImpl userService;
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
    void testFindExistingUserById() throws Exception {
        System.out.println("Bearer 2 " + token);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/6").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
    @Test
    void testUploadAvatarWithGifFile() throws Exception {
        System.out.println("Bearer 3" + token);

        File file = new File("./home/static/images/pic.gif");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile sampleFile = new MockMultipartFile("file", file.getName(), "image/gif", IOUtils.toByteArray(input));
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/v1/users/6/upload-avatar");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        mockMvc.perform(builder.file(sampleFile)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        Path docRootPath = Path.of(documentRoot, "pic.gif");
        filesToBeDeleted.add(docRootPath);
        Assert.assertEquals(true,userService.getUserById(6).getAvatar().contains("pic.gif"));
    }
    @Test
    void testUploadAvatarWithJpgFile() throws Exception {
        System.out.println("Bearer 4" + token);

        File file = new File("./home/static/images/pic.jpg");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile sampleFile = new MockMultipartFile("file", file.getName(), "image/jpg", IOUtils.toByteArray(input));
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/v1/users/6/upload-avatar");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder.file(sampleFile).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        Path docRootPath = Path.of(documentRoot, "pic.jpg");
        filesToBeDeleted.add(docRootPath);
        Assert.assertEquals(true,userService.getUserById(6).getAvatar().contains("pic.jpg"));
    }
    @Test
    void testUploadAvatarWithPngFile() throws Exception {
        System.out.println("Bearer 5" + token);

        File file = new File("./home/static/images/pic.png");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile sampleFile = new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(input));
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/v1/users/6/upload-avatar");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder.file(sampleFile).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        Path docRootPath = Path.of(documentRoot, "pic.png");
        filesToBeDeleted.add(docRootPath);
        Assert.assertEquals(true,userService.getUserById(6).getAvatar().contains("pic.png"));

    }
    @Test
    void testUploadAvatarWithInvalidFile() throws Exception {
        System.out.println("Bearer 6" + token);

        File file = new File("./home/static/images/pic.txt");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile sampleFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/v1/users/6/upload-avatar");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder.file(sampleFile).header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }
}