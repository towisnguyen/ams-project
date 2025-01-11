package com.r23.ams.repositories;

import com.r23.ams.models.entities.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long> {
    List<AssetCategory> findByName(String categoryName);
}
