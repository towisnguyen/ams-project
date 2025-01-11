package com.r23.ams.mapper.appUser;

import com.r23.ams.mapper.AppUserMapper;
import com.r23.ams.models.dto.AppUserDto;
import com.r23.ams.models.dto.appUser.AppUserStatusDto;
import com.r23.ams.models.entities.AppUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppUserStatusMapper {
    private static AppUserStatusMapper INSTANCE;
    public static AppUserStatusMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppUserStatusMapper();
        }
        return INSTANCE;
    }
    public AppUserStatusDto toDTO(AppUser appUser){
        AppUserStatusDto appUserStatusDto =  new AppUserStatusDto();
        appUserStatusDto.setStatus(appUser.getStatus());
        return appUserStatusDto;
    }

    public AppUserStatusDto toString(String appUser){
        AppUserStatusDto appUserStatusDto =  new AppUserStatusDto();
        appUserStatusDto.setStatus(appUser);
        return appUserStatusDto;
    }

    public List<AppUserStatusDto> toDTOList (List<AppUser> userList){
        List<AppUserStatusDto> dtoList = new ArrayList<AppUserStatusDto>();
        AppUserStatusDto userDto = new AppUserStatusDto();
        for(AppUser usr: userList){
            userDto = toDTO(usr);
            dtoList.add(userDto);
        }
        return dtoList;
    }

    public List<AppUserStatusDto> fromStringListToDTOList (List<String> userList){
        List<AppUserStatusDto> dtoList = new ArrayList<AppUserStatusDto>();
        AppUserStatusDto userDto = new AppUserStatusDto();
        for(String usr: userList){
            userDto = toString(usr);
            dtoList.add(userDto);
        }
        return dtoList;
    }
}
