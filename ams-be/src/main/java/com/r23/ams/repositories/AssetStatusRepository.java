package com.r23.ams.repositories;

import com.r23.ams.models.entities.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface AssetStatusRepository extends JpaRepository<AssetStatus, Long> {
    List<AssetStatus> findByName(String statusName);
}
