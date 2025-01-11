package com.r23.ams.models.dto.asset;

import com.r23.ams.models.entities.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AssetDto {
    private long id;
    private String name;
    private String inventoryNumber;
    private String serialNumber;
    private String imei;
    private String ownership;
    private String comments;
    private long project_id;
    private long category_id;
    private long supplier_id;
    private long status_id;
    private long user_id;

    public AssetDto(Asset asset){
        this.id = asset.getId();
        this.name = asset.getName();
    }
}
