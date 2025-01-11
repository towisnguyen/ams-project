package com.r23.ams;

import com.r23.ams.exception.NotFoundException;
import com.r23.ams.mapper.AppUserMapper;
import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.Role;
import com.r23.ams.repositories.AppUserRepository;
import com.r23.ams.repositories.RoleRepository;
import com.r23.ams.service.user.AppUserServiceImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLOutput;

import static org.assertj.core.util.Strings.concat;
import static org.assertj.core.util.Strings.escapePercent;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTests {
    @Autowired
    private AppUserServiceImpl userService;
    @Test
    public void uploadAvatarWithGifFile() throws IOException {
        long id = 5L;
        File file = new File("./home/static/images/pic.gif");
        FileInputStream is = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "image/gif", IOUtils.toByteArray(is));
        AppUser user = AppUserMapper.getInstance().toEntity(userService.getUserById(id));
        userService.uploadAvatar(id, multipartFile);
        Assert.assertEquals(true,userService.getUserById(id).getAvatar().contains("pic.gif"));
    }
    @Test
    public void uploadAvatarWithJpgFile() throws IOException {
        long id = 5L;
        File file = new File("./home/static/images/pic.jpg");
        FileInputStream is = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "image/jpg", IOUtils.toByteArray(is));
        userService.uploadAvatar(id, multipartFile);
        Assert.assertEquals(true,userService.getUserById(id).getAvatar().contains("pic.jpg"));
    }
    @Test
    public void uploadAvatarWithPngFile() throws IOException {
        long id = 5L;
        File file = new File("./home/static/images/pic.png");
        FileInputStream is = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "image/png", IOUtils.toByteArray(is));
        userService.uploadAvatar(id, multipartFile);
        Assert.assertEquals(true,userService.getUserById(id).getAvatar().contains("pic.png"));
    }
    @Test
    public void uploadAvatarWithInvalidFile() throws IOException {
        long id = 5L;
        String error="";
        File file = new File("./home/static/images/pic.txt");
        FileInputStream is = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(is));
        try {
            userService.uploadAvatar(id, multipartFile);
        } catch (Exception e){
            error = e.getMessage();
            System.out.println(error);
        };
        Assert.assertEquals(true, error.contains("Type image is not valid"));
    }
}
