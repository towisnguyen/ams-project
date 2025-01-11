package com.r23.ams.mapper.project;

import com.r23.ams.models.dto.project.ProjectCreateDto;
import com.r23.ams.models.entities.Project;
import com.r23.ams.repositories.AppUserRepository;
import com.r23.ams.repositories.AssetRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProjectCreateMapper {
    private static ProjectCreateMapper INSTANCE;
    private final AssetRepository assetRepository;
    private final AppUserRepository appUserRepository;

    public ProjectCreateMapper(AssetRepository assetRepository, AppUserRepository appUserRepository) {
        this.assetRepository = assetRepository;
        this.appUserRepository = appUserRepository;
    }

    public final ProjectCreateMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProjectCreateMapper(assetRepository, appUserRepository);
        }
        return INSTANCE;
    }

    public Project toEntity(ProjectCreateDto projectDto) {

        Project project = new Project();
        project.setName(projectDto.getProjectName());
        project.setDescription(projectDto.getProjectName());
        project.setStatus(projectDto.getStatus());
        return project;
    }

    public ProjectCreateDto toDto(Project project){
        ProjectCreateDto projectCreatedDto = new ProjectCreateDto();
        projectCreatedDto.setProjectName(project.getName());
        projectCreatedDto.setDescription(project.getDescription());
        projectCreatedDto.setStatus(project.getStatus());
        return  projectCreatedDto;
    }

    public List<ProjectCreateDto> toDTOList(List<Project> projectList){
        List<ProjectCreateDto> dtoList = new ArrayList<ProjectCreateDto>();
        ProjectCreateDto projectCreatedDto = new ProjectCreateDto();
        for(Project p: projectList){
            projectCreatedDto = toDto(p);
            dtoList.add(projectCreatedDto);
        }
        return dtoList;
    }
}
