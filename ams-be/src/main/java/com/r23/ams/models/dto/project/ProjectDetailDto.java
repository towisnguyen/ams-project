package com.r23.ams.models.dto.project;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectDetailDto {
    private long id;
    private String projectName;
    private String description;
    private String status;
}
