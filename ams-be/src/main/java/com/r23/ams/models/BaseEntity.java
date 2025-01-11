package com.r23.ams.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @CreationTimestamp
    @Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonIgnore
    private Timestamp created;

    @PrePersist
    public void onInsert() {
        created = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant());
    }
}