package com.r23.ams.models.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class RequestSearchDto {
    private long id;
    private long assetId;
    private String assetName;
    private String inventoryAssetNumber;
    private String requestType;
    private String status;
    private String reason;
    private Date updateAt;
}
