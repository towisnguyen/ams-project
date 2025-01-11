package com.r23.ams.models.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.r23.ams.models.BaseEntity;
import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.Asset;
import com.r23.ams.models.entities.Request;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class RequestDto extends BaseEntity {
    private String requestType;
    private String status;
    private String reason;
    private Date updateAt;
    private long assetIds;
    private List<Long> userIdSend;
    private List<Long> userIdApprove;
    private List<Long> userIdReceive;
    public RequestDto(Request request) {
        this.requestType = request.getRequestType();
        this.status = request.getStatus();
        this.updateAt = request.getUpdateAt();
    }
}
