package com.r23.ams.repositories;

import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.AssetSupplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface AssetSupplierRepository extends JpaRepository<AssetSupplier, Long> {
    List<AssetSupplier> findByName(String supplierName);
    @Query(value = "SELECT * FROM asset_suppliers sup " +
            "WHERE lower(sup.name) LIKE lower(concat('%',?1,'%'))", nativeQuery = true)
    Page<AssetSupplier> findSupplierByName(String name, Pageable pageable);
}
