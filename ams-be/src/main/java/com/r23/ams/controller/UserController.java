package com.r23.ams.controller;

import com.r23.ams.exception.NotFoundException;
import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.AppUserDto;
import com.r23.ams.models.dto.appUser.AppUserStatusDto;
import com.r23.ams.service.role.RoleServiceImpl;
import com.r23.ams.service.user.AppUserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
    @Autowired
    private AppUserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;


    @PostMapping("")
    @Operation(description = "Create new user", summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create User Successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppUserDto.class))),
            @ApiResponse(responseCode = "400", description = "This User Already Exists")

    })
    public ResponseEntity<?> createNewUser(@RequestBody @Valid AppUserDto user) throws NotFoundException {
        AppUserDto userDTO = userService.createNewUser(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/getAllUsers")
    @Operation(description = "Get users with pagination", summary = "Get users with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get users with pagination Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")

    })
    public ResponseEntity<?> getAllUsersPage(
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

        Page<AppUserDto> userDTOList = userService.findAllUsersPage(pageable);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", userDTOList.getContent());
        response.put("limit", userDTOList.getSize());
        response.put("currentPage", userDTOList.getNumber());
        response.put("totalItems", userDTOList.getTotalElements());
        response.put("totalPages", userDTOList.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllStatus")
    @Operation(description = "Get status with pagination", summary = "Get status with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get users with pagination Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")

    })
    public ResponseEntity<?> getAllStatus(){

        List<AppUserStatusDto> userDTOList = userService.findAllStatus();
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/searchUsersByEmail")
    @Operation(description = "Search user by email", summary = "Search user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")

    })
    public ResponseEntity<?> searchAppUserByEmail(
            @RequestParam(required = false) String email,
            @RequestParam(name = "page", defaultValue = "0") int page, //page number
            @RequestParam(name = "limit", defaultValue = "10") int limit, //page size
            @RequestParam(name = "orderBy", defaultValue = "created") String orderBy, //database field
            @RequestParam(name = "sortBy", defaultValue = "email") String sortBy)
            throws NotFoundException {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortBy).descending());

        Page<AppUserDto> userDTOList = userService.searchAppUserByEmail(email, pageable);
        if (email == null){
            userDTOList = userService.findAllUsersPage(pageable);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", userDTOList.getContent());
        response.put("limit", userDTOList.getSize());
        response.put("currentPage", userDTOList.getNumber());
        response.put("totalItems", userDTOList.getTotalElements());
        response.put("totalPages", userDTOList.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchUsersByName")
    @Operation(description = "Search users by name", summary = "Search users by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> searchAppUserByName(
            @RequestParam(required = false) String name,
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
        Page<AppUserDto> userDTOList = userService.searchAppUserByName(name, pageable);
        if (name == null){
            userDTOList = userService.findAllUsersPage(pageable);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", userDTOList.getContent());
        response.put("limit", userDTOList.getSize());
        response.put("currentPage", userDTOList.getNumber());
        response.put("totalItems", userDTOList.getTotalElements());
        response.put("totalPages", userDTOList.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filterUsersByRole")
    @Operation(description = "Filter users by role", summary = "Filter users by role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filter Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> filterAppUserByRole(
            @RequestParam(required = false) String role,
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
        Page<AppUserDto> userDTOList = userService.filterAppUserByRole(role, pageable);
        if (role == null){
            userDTOList = userService.findAllUsersPage(pageable);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", userDTOList.getContent());
        response.put("limit", userDTOList.getSize());
        response.put("currentPage", userDTOList.getNumber());
        response.put("totalItems", userDTOList.getTotalElements());
        response.put("totalPages", userDTOList.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filterUsersByStatus")
    @Operation(description = "Filter users by status", summary = "Filter users by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filter Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> filterAppUserByStatus(
            @RequestParam(required = false) String status,
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
        Page<AppUserDto> userDTOList = userService.filterAppUserByStatus(status, pageable);
        if (status == null){
            userDTOList = userService.findAllUsersPage(pageable);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", userDTOList.getContent());
        response.put("limit", userDTOList.getSize());
        response.put("currentPage", userDTOList.getNumber());
        response.put("totalItems", userDTOList.getTotalElements());
        response.put("totalPages", userDTOList.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filterUsersByMultipleRoles")
    @Operation(description = "Filter users by multiple roles", summary = "Filter users by multiple roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filter Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> filterAppUserByMultipleRoles(
            @RequestParam(required = false) String roleFirst,
            @RequestParam(required = false) String roleSecond,
            @RequestParam(required = false) String roleThird,
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
        Page<AppUserDto> userDTOList = userService.filterAppUserByMultipleRoles(roleFirst, roleSecond, roleThird, pageable);
        if (roleFirst == null && roleSecond == null && roleThird == null){
            userDTOList = userService.findAllUsersPage(pageable);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", userDTOList.getContent());
        response.put("limit", userDTOList.getSize());
        response.put("currentPage", userDTOList.getNumber());
        response.put("totalItems", userDTOList.getTotalElements());
        response.put("totalPages", userDTOList.getTotalPages());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    @Operation(description = "Get a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppUserDto.class))),
            @ApiResponse(responseCode = "404", description = "User With {id} Is Not Found")
    })
    public ResponseEntity<?> getUserById(@PathVariable long id) throws NotFoundException {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Operation(description = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update Successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppUserDto.class))),
            @ApiResponse(responseCode = "404", description = "User With {id} Is Not Found")
    })
    public ResponseEntity<?> updateUser(
            @PathVariable("id") long id,
            @RequestBody @Valid AppUserDto appUserCreateDTO)
            throws NotFoundException {

        AppUserDto appUserDetailDTO = userService.updateUser(id, appUserCreateDTO);
        return ResponseEntity.ok(appUserDetailDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete an user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete User Successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))),

            @ApiResponse(responseCode = "404", description = "User With {id} Is Not Found")
    })
    public ResponseEntity<?> deleteUser(
            @PathVariable("id") long id) throws NotFoundException{
        ResponseDto responseDTO = userService.deleteUser(id);

        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/require_role_USER")
    public ResponseEntity<?> require_role_USER() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree("{\"Greeting\": \"You are USER\"}");
        return ResponseEntity.ok(json);
    }

    @GetMapping("/require_role_ADMIN")
    public ResponseEntity<?> require_role_ADMIN() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree("{\"Greeting\": \"You are ADMIN\"}");
        return ResponseEntity.ok(json);
    }

    //AMS-31
    @GetMapping("/filterUsersByRoleAndStatus")
    @Operation(description = "Search users by keyword, role and status", summary = "Search users by keyword, role and status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> filterUsersByRoleAndStatus(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "roleName", defaultValue = "") String roleName,
            @RequestParam(name = "status", defaultValue = "") String status,
            @RequestParam(name = "page", defaultValue = "0") int page, //page number
            @RequestParam(name = "size", defaultValue = "3") int size, //page size
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

        Page<AppUserDto> userDTOList = userService.filterUsersByRoleAndStatus(keyword, roleName, status, pageable);
        if (keyword == null){
            userDTOList = userService.findAllUsersPage(pageable);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", userDTOList.getContent());
        response.put("size", userDTOList.getSize());
        response.put("currentPage", userDTOList.getNumber());
        response.put("totalItems", userDTOList.getTotalElements());
        response.put("totalPages", userDTOList.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}/upload-avatar", consumes = "multipart/form-data")
    @Operation(summary = "Upload avatar", description = "Upload avatar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload Avatar Successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppUserDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid File Format"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Permission Denied"),
            @ApiResponse(responseCode = "404", description = "User With {id} Is Not Found")
    })
    public ResponseEntity<JsonNode> uploadAvatar(
            @PathVariable Long id,
            @RequestPart MultipartFile file) throws Exception {
        userService.uploadAvatar( id, file);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree("{\"Message\":\"Upload Avatar Successfully\"}");
        return ResponseEntity.ok(json);
    }
}
