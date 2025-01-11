package com.r23.ams.models.entities;

import com.r23.ams.models.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "asset_suppliers")
@NoArgsConstructor
public class AssetSupplier extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", unique = true)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assetSupplier")
    private List<Asset> assets;
    public AssetSupplier(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
