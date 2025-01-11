package com.r23.ams.mapper;

import com.r23.ams.models.dto.asset.AssetDetailDto;
import com.r23.ams.models.dto.asset.AssetDto;
import com.r23.ams.models.entities.Asset;
import com.r23.ams.repositories.*;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AssetMapper {

    private final AppUserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final AssetCategoryRepository assetCategoryRepository;

    private final AssetSupplierRepository assetSupplierRepository;

    private final AssetStatusRepository assetStatusRepository;
    private static AssetMapper INSTANCE;

    public AssetMapper(AppUserRepository userRepository, ProjectRepository projectRepository, AssetCategoryRepository assetCategoryRepository,
                       AssetSupplierRepository assetSupplierRepository, AssetStatusRepository assetStatusRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.assetCategoryRepository = assetCategoryRepository;
        this.assetSupplierRepository = assetSupplierRepository;
        this.assetStatusRepository = assetStatusRepository;
    }

    public Asset toEntity(AssetDto assetDto) {
        Asset asset = new Asset();
        asset.setName(assetDto.getName());
        asset.setInventoryNumber(assetDto.getInventoryNumber());
        asset.setSerialNumber(assetDto.getSerialNumber());
        asset.setImei(assetDto.getImei());
        asset.setOwnership(assetDto.getOwnership());
        asset.setComments(assetDto.getComments());
        asset.setProject(projectRepository.findById(assetDto.getProject_id()).get());
        asset.setAssetCategory(assetCategoryRepository.findById(assetDto.getCategory_id()).get());
        asset.setAssetSupplier(assetSupplierRepository.findById(assetDto.getSupplier_id()).get());
        asset.setAssetStatus(assetStatusRepository.findById(assetDto.getStatus_id()).get());
        asset.setAppUser(userRepository.findById(assetDto.getUser_id()).get());
        return asset;
    }
    public AssetDto toDTO(Asset asset) {
        AssetDto assetDto = new AssetDto();
        assetDto.setId(asset.getId());
        assetDto.setName(asset.getName());
        assetDto.setInventoryNumber(asset.getInventoryNumber());
        assetDto.setSerialNumber(asset.getSerialNumber());
        assetDto.setImei(asset.getImei());
        assetDto.setOwnership(asset.getOwnership());
        assetDto.setComments(asset.getComments());
        assetDto.setProject_id(asset.getProject().getId());
        assetDto.setCategory_id(asset.getAssetCategory().getId());
        assetDto.setSupplier_id(asset.getAssetSupplier().getId());
        assetDto.setStatus_id(asset.getAssetSupplier().getId());
        assetDto.setUser_id(asset.getAppUser().getId());
        return assetDto;
    }

    public List<AssetDto> fromListAssetToListAssetDto (List<Asset> assetList){
        List<AssetDto> assetDtoList = new ArrayList<AssetDto>();
        AssetDto assetDto = new AssetDto();
        for(Asset asset: assetList){
            assetDto = toDTO(asset);
            assetDtoList.add(assetDto);
        }
        return assetDtoList;
    }

    public AssetDetailDto toDetailDTO(Asset asset) {
        AssetDetailDto assetDetailDto = new AssetDetailDto();
        assetDetailDto.setId(asset.getId());
        assetDetailDto.setName(asset.getName());
        assetDetailDto.setInventoryNumber(asset.getInventoryNumber());
        assetDetailDto.setSerialNumber(asset.getSerialNumber());
        assetDetailDto.setImei(asset.getImei());
        assetDetailDto.setOwnership(asset.getOwnership());
        assetDetailDto.setComments(asset.getComments());
        assetDetailDto.setProjectName(asset.getProject().getName());
        assetDetailDto.setCategoryName(asset.getAssetCategory().getName());
        assetDetailDto.setSupplierName(asset.getAssetSupplier().getName());
        assetDetailDto.setStatusName(asset.getAssetStatus().getName());
        assetDetailDto.setUseEmail(asset.getAppUser().getEmail());
        return assetDetailDto;
    }

    public List<AssetDetailDto> fromListAssetToListAssetDetailDto (List<Asset> assetList){
        List<AssetDetailDto> assetDetailDtoList = new ArrayList<AssetDetailDto>();
        AssetDetailDto assetDetailDto = new AssetDetailDto();
        for(Asset asset: assetList){
            assetDetailDto = toDetailDTO(asset);
            assetDetailDtoList.add(assetDetailDto);
        }
        return assetDetailDtoList;
    }
}
