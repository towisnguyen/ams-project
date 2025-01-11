package com.r23.ams.mapper;

import com.r23.ams.models.dto.AppUserDto;
import com.r23.ams.models.dto.RoleDto;
import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.Role;

import java.util.ArrayList;
import java.util.List;

public class AppUserMapper {
    private static AppUserMapper INSTANCE;
    public static AppUserMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppUserMapper();
        }
        return INSTANCE;
    }
    public AppUser toEntity(AppUserDto userDto) {
        AppUser user = new AppUser();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAddress(userDto.getAddress());
        user.setPhone(userDto.getPhone());
        user.setRole(userDto.getRole());
        user.setStatus(userDto.getStatus());
        user.setFullName(userDto.getFullName());
        user.setBirthDate(userDto.getBirthDate());
        user.setGender(userDto.getGender());
        user.setAvatar(userDto.getAvatar());
        return user;
    }
    public AppUserDto toDTO(AppUser user) {
        AppUserDto dto = new AppUserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setAddress(user.getAddress());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setFullName(user.getFullName());
        dto.setBirthDate(user.getBirthDate());
        dto.setGender(user.getGender());
        dto.setAvatar(user.getAvatar());
        return dto;
    }

    public List<AppUserDto> toDTOList (List<AppUser> userList){
        List<AppUserDto> dtoList = new ArrayList<AppUserDto>();
        AppUserDto userDto = new AppUserDto();
        for(AppUser usr: userList){
            userDto = toDTO(usr);
            dtoList.add(userDto);
        }
        return dtoList;
    }
}
