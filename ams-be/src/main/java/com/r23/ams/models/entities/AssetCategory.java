package com.r23.ams.models.entities;

import com.r23.ams.models.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "asset_categories")
@NoArgsConstructor
public class AssetCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assetCategory")
    private List<Asset> assets;
    public AssetCategory(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
