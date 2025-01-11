package com.r23.ams.models.dto;

import com.r23.ams.models.entities.Project;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ProjectDto {
    private String projectName;
    private String description;
}
