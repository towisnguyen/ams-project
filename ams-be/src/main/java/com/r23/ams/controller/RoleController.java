package com.r23.ams.controller;

import com.r23.ams.exception.NotFoundException;
import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.RoleDto;
import com.r23.ams.service.role.RoleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/roles")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping("")
    @Operation(summary = "Get all roles", description = "Get all ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list of roles successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Map<String, Object>> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "created") String orderBy,
            @RequestParam(defaultValue = "asc") String sortBy

    ) {

        try {
            Pageable pageable = null;
            if (sortBy.equals("asc")) {
                Sort sort = Sort.by(orderBy).ascending();
                pageable = PageRequest.of(page, size, sort);
            }
            if (sortBy.equals("desc")) {
                Sort sort = Sort.by(orderBy).descending();
                pageable = PageRequest.of(page, size, sort);
            }

            Page<RoleDto> roleDtoPage = roleService.findAllRolesPage(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("projectList", roleDtoPage.getContent());
            response.put("limit", roleDtoPage.getSize());
            response.put("currentPage", roleDtoPage.getNumber());
            response.put("totalItems", roleDtoPage.getTotalElements());
            response.put("totalPages", roleDtoPage.getTotalPages());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    @Operation(description = "Create new role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create Role Successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RoleDto.class))),
            @ApiResponse(responseCode = "400", description = "This Role Already Exists")

    })
    public ResponseEntity<?> createRole(
            @RequestBody @Valid RoleDto roleCreateDTO) {

        RoleDto systemRoleDetailDTO = roleService.createRole(roleCreateDTO);
        return ResponseEntity.ok(systemRoleDetailDTO);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get a role by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RoleDto.class))),
            @ApiResponse(responseCode = "404", description = "Role With {id} Is Not Found")
    })
    public ResponseEntity<?> getRoleById(@PathVariable long id) throws NotFoundException {

        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PutMapping("/{id}")
    @Operation(description = "Update a role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RoleDto.class))),
            @ApiResponse(responseCode = "404", description = "System Role With {id} Is Not Found")
    })
    public ResponseEntity<?> updateRole(
            @PathVariable("id") long id,
            @RequestBody RoleDto roleCreateDTO)
            throws NotFoundException {

        RoleDto systemRoleDetailDTO = roleService.updateRole(id, roleCreateDTO);
        return ResponseEntity.ok(systemRoleDetailDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Role Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Role With {id} Is Not Found")
    })
    public ResponseEntity<?> deleteRole(
            @PathVariable("id") long id) throws NotFoundException{

        ResponseDto responseDTO = roleService.deleteRoleById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/pagination")
    @Operation(description = "Get system roles with pagination", summary = "Get system roles with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get system roles with pagination Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")

    })
    public ResponseEntity<?> getAllRolesPage(
            @RequestParam(name = "page", defaultValue = "0") int page, //page number
            @RequestParam(name = "limit", defaultValue = "10") int limit, //page size
            @RequestParam(name = "orderBy", defaultValue = "created") String orderBy, //database field
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy)
            throws NotFoundException {
        Pageable pageable = null;

        if (sortBy.equals("asc")) {
            Sort sort = Sort.by(orderBy).ascending();
            pageable = PageRequest.of(page, limit, sort);
        }
        if (sortBy.equals("desc")) {
            Sort sort = Sort.by(orderBy).descending();
            pageable = PageRequest.of(page, limit, sort);
        }

        Page<RoleDto> systemRoleDetailDTOPage = roleService.findAllRolesPage(pageable);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", systemRoleDetailDTOPage.getContent());
        response.put("limit", systemRoleDetailDTOPage.getSize());
        response.put("currentPage", systemRoleDetailDTOPage.getNumber());
        response.put("totalItems", systemRoleDetailDTOPage.getTotalElements());
        response.put("totalPages", systemRoleDetailDTOPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(description = "Search users with pagination", summary = "Search users with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> searchRoleByCertainFields(
            @RequestParam(required = false) String search,
            @RequestParam(name = "page", defaultValue = "0") int page, //page number
            @RequestParam(name = "size", defaultValue = "10") int size, //page size
            @RequestParam(name = "orderBy", defaultValue = "created") String orderBy, //database field
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy)
            throws NotFoundException {
        Pageable pageable = null;

        if (sortBy.equals("asc")) {
            Sort sort = Sort.by(orderBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        }
        if (sortBy.equals("desc")) {
            Sort sort = Sort.by(orderBy).descending();
            pageable = PageRequest.of(page, size, sort);
        }

        Page<RoleDto> roleDTOList = roleService.findByNameContaining(search, pageable);
        if (search == null){
            roleDTOList = roleService.findAllRolesPage(pageable);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", roleDTOList.getContent());
        response.put("size", roleDTOList.getSize());
        response.put("currentPage", roleDTOList.getNumber());
        response.put("totalItems", roleDTOList.getTotalElements());
        response.put("totalPages", roleDTOList.getTotalPages());
        return ResponseEntity.ok(response);
    }
}
