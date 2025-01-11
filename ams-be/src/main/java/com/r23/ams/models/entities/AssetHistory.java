package com.r23.ams.models.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
@ToString
@Data
@Entity
@Table(name = "asset_history")
public class AssetHistory {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "details")
    private String details;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}