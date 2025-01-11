package com.r23.ams.service.asset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r23.ams.exception.NotFoundException;
import com.r23.ams.models.dto.asset.AssetCreateDto;
import com.r23.ams.models.dto.asset.AssetDetailDto;
import com.r23.ams.models.dto.asset.AssetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssetService {

    Object createNewAsset(AssetCreateDto assetCreateDto) throws JsonProcessingException;

    Page<AssetDetailDto> findPageAllAsset(String keyword, Pageable pageable);
    Page<AssetDetailDto> findAll(Pageable pageable);
    Page<AssetDetailDto> findByNameContaining(String name, Pageable pageable);

    Object findAssetById(long id) throws JsonProcessingException;

    Object updateAssetById(long id, AssetCreateDto assetCreateDto);

    void deleteAssetById(long id) throws NotFoundException;

    List<AssetDto> getListAssetByRequestId(Long id);

    Object getTotalAssetsByProjects();

    Object getTotalAssetsBySuppliers();

    Object getTotalAssetsByStatus();

    Object getTotalAssetsByCategories();

    Page<AssetDetailDto> filterAssetsByCertainFields(String keyword, String projectName, String categoryName, String supplierName, String status,
                                                     String currentOwner, String inventoryNumber, String serialNumber, String imei, Pageable pageable);
}
