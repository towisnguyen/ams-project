package com.r23.ams.service.project;

import com.r23.ams.models.dto.project.ProjectCreateDto;
import com.r23.ams.models.dto.project.ProjectDetailDto;
import com.r23.ams.models.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    Page<ProjectDetailDto> findAllProjectsPage(Pageable pageable);

    List<ProjectDetailDto> findListProjects();

    Page<ProjectDetailDto> searchByKeyword(String keyword, Pageable pageable);

    Page<ProjectDetailDto> searchByStatus(String status, Pageable pageable);

    ProjectDetailDto createProject(ProjectCreateDto projectCreatedDto);

    ProjectDetailDto updateProject(Long id, ProjectCreateDto projectCreatedDto);

    void deleteProjectById(long id);

    void deleteAll();

    ProjectDetailDto getProjectById(Long id);
}
