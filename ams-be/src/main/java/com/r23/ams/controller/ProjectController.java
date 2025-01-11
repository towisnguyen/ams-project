package com.r23.ams.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r23.ams.exception.NotFoundException;
import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.project.ProjectCreateDto;
import com.r23.ams.models.dto.project.ProjectDetailDto;
import com.r23.ams.service.project.ProjectServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/project")
public class ProjectController {
    private static final String PROJECTS_LIST = "projectList";
    private static final String PAGE_SIZE = "pageSize";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String TOTAL_ITEMS = "totalItems";

    private static final String TOTAL_PAGES = "totalPages";

    private final ProjectServiceImpl projectServiceImpl;

    public ProjectController(ProjectServiceImpl projectServiceImpl) {
        this.projectServiceImpl = projectServiceImpl;
    }

    @GetMapping("/all")
    @Operation(summary = "Get all projects", description = "Get all projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list of projects successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDetailDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<List<ProjectDetailDto>> getAllProjects() {
        return new ResponseEntity<>(projectServiceImpl.findListProjects(), HttpStatus.OK);
    }

    @GetMapping("")
    @Operation(summary = "Get all projects with paging", description = "Get all projects with paging")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list of projects successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDetailDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Map<String, Object>> getAllProjectsPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created") String orderBy,
            @RequestParam(defaultValue = "asc") String sortBy)
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

        Page<ProjectDetailDto> projectDetailDtoPage = projectServiceImpl.findAllProjectsPage(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put(PROJECTS_LIST, projectDetailDtoPage.getContent());
        response.put(PAGE_SIZE, projectDetailDtoPage.getSize());
        response.put(CURRENT_PAGE, projectDetailDtoPage.getNumber());
        response.put(TOTAL_ITEMS, projectDetailDtoPage.getTotalElements());
        response.put(TOTAL_PAGES, projectDetailDtoPage.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(description = "Search project by keyword", summary = "Search project by keyword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<Map<String, Object>> searchByKeyword(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "asc") String sortBy)
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

        Page<ProjectDetailDto> projectDetailDtoPage;
        if (keyword == null)
            projectDetailDtoPage = projectServiceImpl.findAllProjectsPage(pageable);
        else
            projectDetailDtoPage = projectServiceImpl.searchByKeyword(keyword, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put(PROJECTS_LIST, projectDetailDtoPage.getContent());
        response.put(PAGE_SIZE, projectDetailDtoPage.getSize());
        response.put(CURRENT_PAGE, projectDetailDtoPage.getNumber());
        response.put(TOTAL_ITEMS, projectDetailDtoPage.getTotalElements());
        response.put(TOTAL_PAGES, projectDetailDtoPage.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchByStatus")
    @Operation(description = "Search project by status", summary = "Search project by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<Map<String, Object>> searchByStatus(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "asc") String sortBy)
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

        Page<ProjectDetailDto> projectDetailDtoPage;
        if (status == null)
            projectDetailDtoPage = projectServiceImpl.findAllProjectsPage(pageable);
        else
            projectDetailDtoPage = projectServiceImpl.searchByStatus(status, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put(PROJECTS_LIST, projectDetailDtoPage.getContent());
        response.put(PAGE_SIZE, projectDetailDtoPage.getSize());
        response.put(CURRENT_PAGE, projectDetailDtoPage.getNumber());
        response.put(TOTAL_ITEMS, projectDetailDtoPage.getTotalElements());
        response.put(TOTAL_PAGES, projectDetailDtoPage.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ProjectDetailDto> createProject(@RequestBody @Valid ProjectCreateDto projectCreateDto) throws ValidationException {
        ProjectDetailDto create = projectServiceImpl.createProject(projectCreateDto);
        return ResponseEntity.ok(create);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get a project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectDetailDto.class))),
            @ApiResponse(responseCode = "404", description = "Project with {id} is not found")
    })
    public ResponseEntity<ProjectDetailDto> getProjectById(@PathVariable Long id) throws JsonProcessingException {
            ProjectDetailDto project = projectServiceImpl.getProjectById(id);
            return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDetailDto> updateProject(@PathVariable Long id, @RequestBody @Valid ProjectCreateDto projectCreateDto) {
        ProjectDetailDto update = projectServiceImpl.updateProject(id, projectCreateDto);
            return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteProject(@PathVariable Long id) {
            projectServiceImpl.deleteProjectById(id);
            return new ResponseDto("Delete successfully!");
    }
}
