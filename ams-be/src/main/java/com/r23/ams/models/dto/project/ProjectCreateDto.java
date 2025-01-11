package com.r23.ams.models.dto.project;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ProjectCreateDto {
    @NotEmpty(message = "Project name cannot be empty!")
    @NotBlank(message = "Project name cannot be blank!")
    private String projectName;
    private String description;
    @NotEmpty(message = "Status cannot be empty!")
    @NotBlank(message = "Status cannot be blank!")
    private String status;
}
