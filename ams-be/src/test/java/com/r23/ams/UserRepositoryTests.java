package com.r23.ams;

import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.Role;
import com.r23.ams.repositories.AppUserRepository;
import com.r23.ams.repositories.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTests {
    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void addUserTest() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AppUser user = new AppUser();
        user.setEmail("ntdat@tma.com.vn");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole("ADMIN");

        userRepository.save(user);
        Assert.assertEquals(1,userRepository.findAll().size());
    }

    @Test
    public void createUserWithRoleTest() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AppUser user = new AppUser();
        user.setEmail("userAdmin1@tma.com.vn");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole("ADMIN");

        Role role = roleRepository.findByName("ADMIN").get();
        user.setSysRole(role);

        userRepository.save(user);
        Assert.assertEquals(1,userRepository.findByEmail("userAdmin1@tma.com.vn").size());
    }
}
