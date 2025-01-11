package com.r23.ams.service.role;

import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.AppUserDto;
import com.r23.ams.models.dto.RoleDto;
import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto systemRoleCreateDTO);
    RoleDto getRoleById(long id);
    RoleDto updateRole(long id, RoleDto systemRoleCreateDTO);
    ResponseDto deleteRoleById(long id);
    public List<RoleDto> getAllRoles();
    Page<RoleDto> findByNameContaining(String name, Pageable pageable);
    Page<RoleDto> findAllRolesPage(Pageable pageable);
}
