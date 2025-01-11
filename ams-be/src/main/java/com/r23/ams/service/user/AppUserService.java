package com.r23.ams.service.user;

import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.AppUserDto;
import com.r23.ams.models.dto.appUser.AppUserStatusDto;
import com.r23.ams.models.entities.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AppUserService {
    AppUserDto createNewUser(AppUserDto usr);
    List<AppUserDto> loadUserByUsername(String username);
    Page<AppUserDto> findAllUsersPage(Pageable pageable);
    List<AppUserStatusDto> findAllStatus();
    Page<AppUserDto> searchAppUserByEmail(String email, Pageable pageable);
    Page<AppUserDto> searchAppUserByName(String name, Pageable pageable);
    Page<AppUserDto> filterAppUserByRole(String role, Pageable pageable);

    Page<AppUserDto> filterAppUserByMultipleRoles(String roleFirst, String roleSecond, String roleThird, Pageable pageable);
    Page<AppUserDto> filterAppUserByStatus(String status, Pageable pageable);
    public List<AppUserDto> getAllUsers();
    public AppUserDto getUserById(long id);
    public AppUserDto updateUser(long id, AppUserDto userDto);
    public ResponseDto deleteUser(long id);
    Page<AppUserDto> filterUsersByRoleAndStatus(String keyword, String roleName, String status, Pageable pageable);
    String uploadAvatar(Long id, MultipartFile file);
}
