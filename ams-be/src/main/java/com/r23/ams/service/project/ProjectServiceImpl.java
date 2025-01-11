package com.r23.ams.service.project;

import com.r23.ams.exception.BadRequestException;
import com.r23.ams.exception.NotFoundException;
import com.r23.ams.mapper.project.ProjectDetailMapper;
import com.r23.ams.mapper.project.ProjectCreateMapper;
import com.r23.ams.models.dto.project.ProjectCreateDto;
import com.r23.ams.models.dto.project.ProjectDetailDto;
import com.r23.ams.models.entities.Project;
import com.r23.ams.repositories.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    private final ProjectDetailMapper projectDetailMapper;

    private final ProjectCreateMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectDetailMapper projectDetailMapper, ProjectCreateMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectDetailMapper = projectDetailMapper;
        this.projectMapper = projectMapper;
    }

    @Override
    public Page<ProjectDetailDto> findAllProjectsPage(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAll(pageable);
        List<Project> projectList = projectPage.getContent();

        List<ProjectDetailDto> projectDtoList = projectDetailMapper.toDTOList(projectList);

        return new PageImpl<>(projectDtoList, pageable, projectPage.getTotalElements());
    }

    @Override
    public List<ProjectDetailDto> findListProjects() {
        List<Project> projectPage = projectRepository.findAll();
        return projectDetailMapper.toDTOList(projectPage);
    }

    @Override
    public Page<ProjectDetailDto> searchByKeyword(String keyword, Pageable pageable) {
        Page<Project> projectPage = projectRepository.searchByKeyword(keyword, pageable);
        List<Project> projectList = projectPage.getContent();
        List<ProjectDetailDto> projectDtoList = projectDetailMapper.toDTOList(projectList);

        return new PageImpl<>(projectDtoList, pageable, projectPage.getTotalElements());
    }

    @Override
    public Page<ProjectDetailDto> searchByStatus(String status, Pageable pageable) {
        Page<Project> projectPage = projectRepository.searchByStatus(status, pageable);
        List<Project> projectList = projectPage.getContent();
        List<ProjectDetailDto> projectDtoList = projectDetailMapper.toDTOList(projectList);

        return new PageImpl<>(projectDtoList, pageable, projectPage.getTotalElements());
    }

    @Override
    public ProjectDetailDto createProject(ProjectCreateDto projectCreateDto) {
        Project project = new Project();
        if (projectRepository.findOneByName(projectCreateDto.getProjectName()).isPresent()) {
            throw new BadRequestException("Project with this name already exists " + projectCreateDto.getProjectName());
        }
        project.setName(projectCreateDto.getProjectName());
        project.setDescription(projectCreateDto.getDescription());
        project.setStatus(projectCreateDto.getStatus());
        projectRepository.save(project);
        return projectDetailMapper.toGetAllDto(project);
    }

    @Override
    public ProjectDetailDto updateProject(Long id, ProjectCreateDto projectCreateDto) {
        if (!projectRepository.existsById(id)){
            throw new NotFoundException("Project with id = " + id + " is not found");
        }
        Project project = projectRepository.findById(id).get();
        project.setName(projectCreateDto.getProjectName());
        project.setDescription(projectCreateDto.getDescription());
        project.setStatus(projectCreateDto.getStatus());
        projectRepository.save(project);
        return projectDetailMapper.toGetAllDto(project);
    }

    @Override
    public void deleteProjectById(long id) {
        if (!projectRepository.existsById(id)){
            throw new NotFoundException("Project with id = " + id + " is not found");
        }
        Project project = projectRepository.findById(id).get();
        projectRepository.delete(project);
    }

    @Override
    public void deleteAll() {
        projectRepository.deleteAll();
    }

    @Override
    public ProjectDetailDto getProjectById(Long id) {
        if (!projectRepository.existsById(id)){
            throw new NotFoundException("Project with id = " + id + " is not found");
        }
        Project project = projectRepository.findById(id).get();
        return projectDetailMapper.toGetAllDto(project);
    }
}
