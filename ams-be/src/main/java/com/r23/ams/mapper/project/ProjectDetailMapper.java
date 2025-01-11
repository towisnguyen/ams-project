package com.r23.ams.mapper.project;

import com.r23.ams.models.dto.project.ProjectDetailDto;
import com.r23.ams.models.entities.Project;
import com.r23.ams.repositories.AppUserRepository;
import com.r23.ams.repositories.AssetRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectDetailMapper {
    private static ProjectDetailMapper INSTANCE;
    private final AssetRepository assetRepository;
    private final AppUserRepository appUserRepository;

    public ProjectDetailMapper(AssetRepository assetRepository, AppUserRepository appUserRepository) {
        this.assetRepository = assetRepository;
        this.appUserRepository = appUserRepository;
    }

    public final ProjectDetailMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProjectDetailMapper(assetRepository, appUserRepository);
        }
        return INSTANCE;
    }

    public Project toEntity(ProjectDetailDto projectGetAllDto) {

        Project project = new Project();
        project.setId(projectGetAllDto.getId());
        project.setName(projectGetAllDto.getProjectName());
        project.setDescription(projectGetAllDto.getProjectName());
        project.setStatus(projectGetAllDto.getStatus());
        return project;
    }

    public ProjectDetailDto toGetAllDto(Project project){
        ProjectDetailDto projectGetAllDto = new ProjectDetailDto();
        projectGetAllDto.setId(project.getId());
        projectGetAllDto.setProjectName(project.getName());
        projectGetAllDto.setDescription(project.getDescription());
        projectGetAllDto.setStatus(project.getStatus());
        return  projectGetAllDto;
    }

    public List<ProjectDetailDto> toDTOList(List<Project> projectList){
        List<ProjectDetailDto> dtoList = new ArrayList<ProjectDetailDto>();
        ProjectDetailDto projectGetAllDto = new ProjectDetailDto();
        for(Project p: projectList){
            projectGetAllDto = toGetAllDto(p);
            dtoList.add(projectGetAllDto);
        }
        return dtoList;
    }
}
