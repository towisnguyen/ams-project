package com.r23.ams;

import com.r23.ams.models.entities.*;
import com.r23.ams.repositories.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@ActiveProfiles("Asset_test")
@RunWith(SpringJUnit4ClassRunner.class)
public class AssetRepositoryTests {

//    private final AppUserRepository userRepository;
//
//    private final ProjectRepository projectRepository;
//
//    private final AssetCategoryRepository assetCategoryRepository;
//
//    private final AssetSupplierRepository assetSupplierRepository;
//
//    private final AssetStatusRepository assetStatusRepository;
//
//    private final AssetRepository assetRepository;
//
//    public AssetRepositoryTests(AppUserRepository userRepository, ProjectRepository projectRepository, AssetCategoryRepository assetCategoryRepository, AssetSupplierRepository assetSupplierRepository, AssetStatusRepository assetStatusRepository, AssetRepository assetRepository) {
//            this.userRepository = userRepository;
//            this.projectRepository = projectRepository;
//            this.assetCategoryRepository = assetCategoryRepository;
//            this.assetSupplierRepository = assetSupplierRepository;
//            this.assetStatusRepository = assetStatusRepository;
//            this.assetRepository = assetRepository;
//        }
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    AssetCategoryRepository assetCategoryRepository;
    @Autowired
    AssetSupplierRepository assetSupplierRepository;
    @Autowired
    AssetStatusRepository assetStatusRepository;
    @Autowired
    AssetRepository assetRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Test
    public void addDataTest() {
        AppUser user1 = new AppUser();
        user1.setEmail("tuyen@tma.com.vn");
        user1.setPassword(passwordEncoder.encode("123456"));
        user1.setRole("ADMIN");
        user1.setFirstName("Tuyen");
        user1.setLastName("Le Thanh");
        userRepository.save(user1);

        AppUser user2 = new AppUser();
        user2.setEmail("do@tma.com.vn");
        user2.setPassword(passwordEncoder.encode("123456"));
        user2.setRole("ADMIN");
        user2.setFirstName("Do");
        user2.setLastName("Nguyen Thanh");
        userRepository.save(user2);

        AppUser user3 = new AppUser();
        user3.setEmail("tuan@tma.com.vn");
        user3.setPassword(passwordEncoder.encode("123456"));
        user3.setRole("USER");
        user2.setFirstName("Tuan");
        user2.setLastName("Tran Anh");
        userRepository.save(user3);
        Assert.assertEquals(3,userRepository.findAll().size());

        Project project1 = new Project(1L, "MEGA");projectRepository.save(project1);
        Project project2 = new Project(2L, "ADA");projectRepository.save(project2);
        Project project3 = new Project(3L, "ED");projectRepository.save(project3);
        Project project4 = new Project(4L, "AURA");projectRepository.save(project4);
        Project project5 = new Project(5L, "ASBCE");projectRepository.save(project5);
        Project project6 = new Project(6L, "CS1K");projectRepository.save(project5);
        Assert.assertEquals(5,projectRepository.findAll().size());

        AssetCategory assetCategory1 = new AssetCategory(1L, "SERVER");assetCategoryRepository.save(assetCategory1);
        AssetCategory assetCategory2 = new AssetCategory(2L, "MONITOR");assetCategoryRepository.save(assetCategory2);
        AssetCategory assetCategory3 = new AssetCategory(3L, "PHONE");assetCategoryRepository.save(assetCategory3);
        AssetCategory assetCategory4 = new AssetCategory(4L, "PRINTER");assetCategoryRepository.save(assetCategory4);
        AssetCategory assetCategory5 = new AssetCategory(5L,"COMPUTER");assetCategoryRepository.save(assetCategory5);
        AssetCategory assetCategory6 = new AssetCategory(6L, "UPS");assetCategoryRepository.save(assetCategory6);
        Assert.assertEquals(6,assetCategoryRepository.findAll().size());

        AssetStatus assetStatus1 = new AssetStatus(1L, "AVAILABLE");assetStatusRepository.save(assetStatus1);
        AssetStatus assetStatus2 = new AssetStatus(2L, "BROKEN");assetStatusRepository.save(assetStatus2);
        AssetStatus assetStatus3 = new AssetStatus(3L, "MAINTENANCE");assetStatusRepository.save(assetStatus3);
        AssetStatus assetStatus4 = new AssetStatus(4L, "SPARE");assetStatusRepository.save(assetStatus4);
        AssetStatus assetStatus5 = new AssetStatus(5L, "LEND");assetStatusRepository.save(assetStatus5);
        AssetStatus assetStatus6 = new AssetStatus(6L, "BORROW");assetStatusRepository.save(assetStatus6);
        Assert.assertEquals(6,assetStatusRepository.findAll().size());

        AssetSupplier assetSupplier1 = new AssetSupplier(1L, "HP");assetSupplierRepository.save(assetSupplier1);
        AssetSupplier assetSupplier2 = new AssetSupplier(2L, "LOGITECH");assetSupplierRepository.save(assetSupplier2);
        AssetSupplier assetSupplier3 = new AssetSupplier(3L, "DELL");assetSupplierRepository.save(assetSupplier3);
        AssetSupplier assetSupplier4 = new AssetSupplier(4L, "BROTHER");assetSupplierRepository.save(assetSupplier4);
        AssetSupplier assetSupplier5 = new AssetSupplier(5L, "APPLE");assetSupplierRepository.save(assetSupplier5);
        AssetSupplier assetSupplier6 = new AssetSupplier(6L, "SAMSUNG");assetSupplierRepository.save(assetSupplier6);
        Assert.assertEquals(6,assetSupplierRepository.findAll().size());

        Asset asset1 = new Asset();
        asset1.setName("Monitor");
        asset1.setOwnership("Tuyen");
        asset1.setSerialNumber("123456");
        asset1.setProject(projectRepository.findById(1L).get());
        asset1.setAssetCategory(assetCategoryRepository.findById(1L).get());
        asset1.setAssetSupplier(assetSupplierRepository.findById(1L).get());
        asset1.setAssetStatus(assetStatusRepository.findById(1L).get());
        asset1.setAppUser(userRepository.findById(1L).get());
        assetRepository.save(asset1);

        Asset asset2 = new Asset();
        asset2.setName("Passport");
        asset2.setOwnership("Tuyen");
        asset2.setSerialNumber("123457");
        asset2.setProject(projectRepository.findById(2L).get());
        asset2.setAssetCategory(assetCategoryRepository.findById(2L).get());
        asset2.setAssetSupplier(assetSupplierRepository.findById(3L).get());
        asset2.setAssetStatus(assetStatusRepository.findById(1L).get());
        asset2.setAppUser(userRepository.findById(1L).get());
        assetRepository.save(asset2);

        Asset asset3 = new Asset();
        asset3.setName("Phone1140");
        asset3.setOwnership("Tuan");
        asset3.setSerialNumber("123458");
        asset3.setProject(projectRepository.findById(3L).get());
        asset3.setAssetCategory(assetCategoryRepository.findById(3L).get());
        asset3.setAssetSupplier(assetSupplierRepository.findById(3L).get());
        asset3.setAssetStatus(assetStatusRepository.findById(3L).get());
        asset3.setAppUser(userRepository.findById(2L).get());
        assetRepository.save(asset3);

        Asset asset4 = new Asset();
        asset4.setName("HeadSet");
        asset4.setOwnership("Tuan");
        asset4.setSerialNumber("123459");
        asset4.setProject(projectRepository.findById(3L).get());
        asset4.setAssetCategory(assetCategoryRepository.findById(4L).get());
        asset4.setAssetSupplier(assetSupplierRepository.findById(3L).get());
        asset4.setAssetStatus(assetStatusRepository.findById(5L).get());
        asset4.setAppUser(userRepository.findById(3L).get());
        assetRepository.save(asset4);

        Asset asset5 = new Asset();
        asset5.setName("HPG7");
        asset5.setOwnership("Phuong");
        asset5.setSerialNumber("123460");
        asset5.setProject(projectRepository.findById(3L).get());
        asset5.setAssetCategory(assetCategoryRepository.findById(3L).get());
        asset5.setAssetSupplier(assetSupplierRepository.findById(3L).get());
        asset5.setAssetStatus(assetStatusRepository.findById(1L).get());
        asset5.setAppUser(userRepository.findById(1L).get());
        assetRepository.save(asset5);

        Asset asset6 = new Asset();
        asset6.setName("FAX2840");
        asset6.setOwnership("Do");
        asset6.setSerialNumber("123461");
        asset6.setProject(projectRepository.findById(1L).get());
        asset6.setAssetCategory(assetCategoryRepository.findById(4L).get());
        asset6.setAssetSupplier(assetSupplierRepository.findById(1L).get());
        asset6.setAssetStatus(assetStatusRepository.findById(4L).get());
        asset6.setAppUser(userRepository.findById(1L).get());
        assetRepository.save(asset6);

        Assert.assertEquals(6,assetRepository.findAll().size());
    }
}
