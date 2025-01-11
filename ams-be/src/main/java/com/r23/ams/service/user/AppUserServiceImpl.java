package com.r23.ams.service.user;

import com.r23.ams.exception.BadRequestException;
import com.r23.ams.exception.NotFoundException;
import com.r23.ams.file.FilesStorageService;
import com.r23.ams.mapper.AppUserMapper;
import com.r23.ams.mapper.appUser.AppUserStatusMapper;
import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.AppUserDto;
import com.r23.ams.models.dto.appUser.AppUserStatusDto;
import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.Role;
import com.r23.ams.repositories.AppUserRepository;
import com.r23.ams.repositories.RoleRepository;
import com.r23.ams.utils.ImageFileValidation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FilesStorageService filesStorageService;

    public AppUserServiceImpl(AppUserRepository userRepository, RoleRepository roleRepository, FilesStorageService filesStorageService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.filesStorageService = filesStorageService;
    }

    @Override
    public AppUserDto createNewUser(AppUserDto usr) {
        AppUser appUser = AppUserMapper.getInstance().toEntity(usr);

        if (userRepository.findByPhone(usr.getPhone()).isPresent()) {
            throw new BadRequestException("User with this phone already exists " + usr.getPhone());
        }

        Optional<Role> roleOptional = roleRepository.findByName(usr.getRole());
        if (roleOptional.isEmpty()) {
            throw new NotFoundException("System role with name" + usr.getRole() + " is not found");
        }
        Role sysRole = roleOptional.get();

        appUser.setSysRole(sysRole);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        appUser.setPassword(passwordEncoder.encode(usr.getPassword()));

        AppUser createdAppUser = userRepository.save(appUser);

        AppUserDto appUserDetailDTO = AppUserMapper.getInstance().toDTO(createdAppUser);

        return appUserDetailDTO;
    }
    @Override
    public List<AppUserDto> loadUserByUsername(String username) {
        List<AppUser> userList = userRepository.findByEmail(username);
        List<AppUserDto> userDTOList = AppUserMapper.getInstance().toDTOList(userList);
        return userDTOList;
    }

    @Override
    public Page<AppUserDto> findAllUsersPage(Pageable pageable) {
        Page<AppUser> userPage = userRepository.getAllUsersPage(pageable);
        List<AppUser> userList = userPage.getContent();

        List<AppUserDto> userDTOList = AppUserMapper.getInstance().toDTOList(userList);

        return new PageImpl<>(userDTOList, pageable, userPage.getTotalElements());
    }

    @Override
    public List<AppUserStatusDto> findAllStatus() {
        List<String> listStatus = userRepository.getAllStatus();
        List<AppUserStatusDto> userDTOList = AppUserStatusMapper.getInstance().fromStringListToDTOList(listStatus);
        return userDTOList;
    }

    @Override
    public Page<AppUserDto> searchAppUserByEmail(String email, Pageable pageable) {
        Page<AppUser> userPage = userRepository.searchAppUserByEmail(email, pageable);
        List<AppUser> userList = userPage.getContent();

        List<AppUserDto> userDTOList = AppUserMapper.getInstance().toDTOList(userList);

        return new PageImpl<>(userDTOList, pageable, userPage.getTotalElements());
    }

    @Override
    public Page<AppUserDto> searchAppUserByName(String name, Pageable pageable) {
        Page<AppUser> userPage = userRepository.searchAppUserByName(name, pageable);
        List<AppUser> userList = userPage.getContent();

        List<AppUserDto> userDTOList = AppUserMapper.getInstance().toDTOList(userList);

        return new PageImpl<>(userDTOList, pageable, userPage.getTotalElements());
    }

    @Override
    public Page<AppUserDto> filterAppUserByRole(String role, Pageable pageable) {
        Page<AppUser> userPage = userRepository.filterAppUserByRole(role, pageable);
        List<AppUser> userList = userPage.getContent();

        List<AppUserDto> userDTOList = AppUserMapper.getInstance().toDTOList(userList);

        return new PageImpl<>(userDTOList, pageable, userPage.getTotalElements());
    }

    @Override
    public Page<AppUserDto> filterAppUserByMultipleRoles(String roleFirst, String roleSecond, String roleThird, Pageable pageable) {
        Page<AppUser> userPage = userRepository.filterAppUserByMultipleRoles(roleFirst, roleSecond, roleThird, pageable);
        List<AppUser> userList = userPage.getContent();

        List<AppUserDto> userDTOList = AppUserMapper.getInstance().toDTOList(userList);

        return new PageImpl<>(userDTOList, pageable, userPage.getTotalElements());
    }

    @Override
    public Page<AppUserDto> filterAppUserByStatus(String status, Pageable pageable) {
        Page<AppUser> userPage = userRepository.filterAppUserByStatus(status, pageable);
        List<AppUser> userList = userPage.getContent();

        List<AppUserDto> userDTOList = AppUserMapper.getInstance().toDTOList(userList);

        return new PageImpl<>(userDTOList, pageable, userPage.getTotalElements());
    }

    @Override
    public List<AppUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> AppUserMapper.getInstance().toDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public AppUserDto getUserById(long id) {
        Optional<AppUser> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("System user with id = " + id + " is not found");
        }
        AppUser user = userOptional.get();
        AppUserDto userDto = AppUserMapper.getInstance().toDTO(user);
        return userDto;
    }

    @Override
    @Transactional
    public AppUserDto updateUser(long id, AppUserDto userDto) {
        Optional<AppUser> appUserOptional = userRepository.findById(id);
        if (appUserOptional.isEmpty()) {
            throw new NotFoundException("This user with id = " + id + " is not found");
        }

        Optional<Role> roleOptional = roleRepository.findByName(userDto.getRole());
        if (roleOptional.isEmpty()) {
            throw new NotFoundException("System role with name" + userDto.getRole() + " is not found");
        }
        Role systemRole = roleOptional.get();

        AppUser appUser = appUserOptional.get();
        appUser.setSysRole(systemRole);
        // copy userDto and paste into appUser
        BeanUtils.copyProperties(userDto, appUser);

        AppUser createdAppUser = userRepository.save(appUser);

        AppUserDto appUserDetailDTO = AppUserMapper.getInstance().toDTO(createdAppUser);
        return appUserDetailDTO;
    }

    @Override
    @Transactional
    public ResponseDto deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("This user with id = " + id + " is not found");
        }
        userRepository.deleteById(id);
        return new ResponseDto("Delete user successfully");
    }

    //AMS-31 filterUsersByRoleAndStatus(String keyword, String roleName, String status, Pageable pageable)
    @Override
    public Page<AppUserDto> filterUsersByRoleAndStatus(String keyword, String roleName, String status, Pageable pageable) {
        Page<AppUser> userPage = userRepository.filterUsersByRoleAndStatus(keyword, roleName, status, pageable);
        List<AppUser> userList = userPage.getContent();

        List<AppUserDto> userDTOList = AppUserMapper.getInstance().toDTOList(userList);

        return new PageImpl<>(userDTOList, pageable, userPage.getTotalElements());
    }

    @Transactional
    @Override
    public String uploadAvatar(Long id, MultipartFile file) {
        AppUser appUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found user with id: " + id));
        if(appUser.getAvatar() != null){
            filesStorageService.deleteFile(appUser.getAvatar());//delete old avatar
        }
        ImageFileValidation.validateImage(file);
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS_");
        String currentDateTime = dateFormatter.format(new Date());
        String fileName = currentDateTime + file.getOriginalFilename();
        String relativePath = "/users/" + id + "/avatar/" + fileName;
        filesStorageService.saveAs(file, relativePath);
        appUser.setAvatar(relativePath);//update avatar
        userRepository.save(appUser);
        return fileName;
    }
}
