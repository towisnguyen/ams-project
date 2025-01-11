package com.r23.ams.service.role;

import com.r23.ams.exception.NotFoundException;
import com.r23.ams.mapper.RoleMapper;
import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.RoleDto;
import com.r23.ams.models.entities.Role;
import com.r23.ams.repositories.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(RoleDto systemRoleCreateDTO) {
        Role systemRole = RoleMapper.getInstance().toEntity(systemRoleCreateDTO);
        if (roleRepository.findByName(systemRole.getName()).isPresent()) {
            throw new NotFoundException("System role with name = " + systemRole.getName() + " already exists");
        }

        Role createdSystemRole = roleRepository.save(systemRole);
        RoleDto systemRoleDetailDTO = RoleMapper.getInstance().toDTO(createdSystemRole);
        return systemRoleDetailDTO;
    }

    @Override
    public RoleDto getRoleById(long id) {
        Optional<Role> systemRoleOptional = roleRepository.findById(id);
        if (systemRoleOptional.isEmpty()) {
            throw new NotFoundException("System role with id = " + id + " is not found");
        }
        Role systemRole = systemRoleOptional.get();
        RoleDto systemRoleDetailDTO = RoleMapper.getInstance().toDTO(systemRole);
        return systemRoleDetailDTO;
    }

    @Override
    @Transactional
    public RoleDto updateRole(long id, RoleDto systemRoleCreateDTO) {
        Optional<Role> systemRoleOptional = roleRepository.findById(id);
        if (systemRoleOptional.isEmpty()) {
            throw new NotFoundException("This system role with id = " + id + " is not found");
        }
        Role systemRole = systemRoleOptional.get();
        if (roleRepository.findByName(systemRoleCreateDTO.getName()).isPresent()) {
            throw new NotFoundException("System role with this name already exists");
        }
        BeanUtils.copyProperties(systemRoleCreateDTO, systemRole);

        Role createdSystemRole = roleRepository.save(systemRole);

        return RoleMapper.getInstance().toDTO(createdSystemRole);
    }

    @Override
    @Transactional
    public ResponseDto deleteRoleById(long id) {
        if (!roleRepository.existsById(id)) {
            throw new NotFoundException("This system role with id = " + id + " is not found");
        }
        roleRepository.deleteById(id);
        return new ResponseDto("Delete system role successfully");
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> RoleMapper.getInstance().toDTO(role))
                .collect(Collectors.toList());
    }

    @Override
    public Page<RoleDto> findByNameContaining(String name, Pageable pageable) {
        Page<Role> rolePage = roleRepository.findByNameContaining(name, pageable);
        List<Role> roleList = rolePage.getContent();

        List<RoleDto> roleDTOList = RoleMapper.getInstance().toDTOList(roleList);
        return new PageImpl<>(roleDTOList,pageable,rolePage.getTotalElements());
    }

    @Override
    public Page<RoleDto> findAllRolesPage(Pageable pageable) {
        Page<Role> rolePage = roleRepository.getAllRolesPage(pageable);
        List<Role> roleList = rolePage.getContent();

        List<RoleDto> roleDTOList = RoleMapper.getInstance().toDTOList(roleList);

        return new PageImpl<>(roleDTOList, pageable, rolePage.getTotalElements());
    }
}
