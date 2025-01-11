package com.r23.ams.models.dto.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AssetCreateDto {
    @NotBlank(message = "Name is required")
    @Schema(example = "Laptop")
    private String name;
    @Schema(example = "IN123123")
    private String inventoryNumber;
    @Schema(example = "SN456654")
    private String serialNumber;
    @Schema(example = "IM147852")
    private String imei;
    private String ownership;
    private String comments;
    @NotNull(message = "Project Id is required")
    @Schema(example = "1")
    private long project_id;
    @NotNull(message = "Category Id is required")
    @Schema(example = "1")
    private long category_id;
    @NotNull(message = "Supplier Id is required")
    @Schema(example = "1")
    private long supplier_id;
    @NotNull(message = "Status Id is required")
    @Schema(example = "1")
    private long status_id;
    @NotNull(message = "User Id is required")
    @Schema(example = "7")
    private long user_id;
}