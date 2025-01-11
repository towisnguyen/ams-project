package com.r23.ams.mapper;

import com.r23.ams.models.dto.RoleDto;
import com.r23.ams.models.entities.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleMapper {
    private static RoleMapper INSTANCE;
    public static RoleMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleMapper();
        }
        return INSTANCE;
    }
    public Role toEntity(RoleDto roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        return role;
    }
    public RoleDto toDTO(Role role) {
        RoleDto dto = new RoleDto();
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        return dto;
    }
    public List<RoleDto> toDTOList (List<Role> systemRoleList){
        List<RoleDto> dtoList = new ArrayList<RoleDto>();
        RoleDto roleDto = new RoleDto();
        for(Role r: systemRoleList){
            roleDto = toDTO(r);
            dtoList.add(roleDto);
        }
        return dtoList;
    }
}
