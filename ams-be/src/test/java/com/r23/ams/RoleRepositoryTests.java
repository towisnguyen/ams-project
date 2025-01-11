package com.r23.ams;


import com.r23.ams.models.entities.Role;
import com.r23.ams.repositories.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void addRoleTest() {
        Role role1 = new Role();
        role1.setName("ADMIN");
        role1.setDescription("Admin permission for user");
        roleRepository.save(role1);

        Role role2 = new Role();
        role2.setName("USER");
        role2.setDescription("User permission for user");
        roleRepository.save(role2);


        Assert.assertEquals(2,roleRepository.findAll().size());
    }

}
