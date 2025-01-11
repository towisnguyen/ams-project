package com.r23.ams.models.dto.asset;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssetDetailDto {
    private long id;
    private String name;
    private String inventoryNumber;
    private String serialNumber;
    private String imei;
    private String ownership;
    private String comments;
    private String projectName;
    private String categoryName;
    private String supplierName;
    private String statusName;
    private String useEmail;
}
