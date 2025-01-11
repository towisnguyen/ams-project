package com.r23.ams.service.asset;

import com.r23.ams.exception.NotFoundException;
import com.r23.ams.mapper.AssetMapper;
import com.r23.ams.models.dto.asset.AssetCreateDto;
import com.r23.ams.models.dto.asset.AssetDetailDto;
import com.r23.ams.models.dto.asset.AssetDto;
import com.r23.ams.models.entities.*;
import com.r23.ams.repositories.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AssetServiceImpl implements AssetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetServiceImpl.class);
    private final AssetRepository assetRepository;

    private final AppUserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final AssetCategoryRepository assetCategoryRepository;

    private final AssetSupplierRepository assetSupplierRepository;

    private final AssetStatusRepository assetStatusRepository;

    private final AssetMapper assetMapper;

    public AssetServiceImpl(AssetRepository assetRepository, AppUserRepository userRepository, ProjectRepository projectRepository,
                            AssetCategoryRepository assetCategoryRepository, AssetSupplierRepository assetSupplierRepository,
                            AssetStatusRepository assetStatusRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.assetCategoryRepository = assetCategoryRepository;
        this.assetSupplierRepository = assetSupplierRepository;
        this.assetStatusRepository = assetStatusRepository;
        this.assetMapper = assetMapper;
    }

    @Override
    public AssetDto createNewAsset(AssetCreateDto assetCreateDto) throws NotFoundException {
        Asset newAsset = assetRepository.findByName(assetCreateDto.getName()).stream().findFirst()
                .orElseGet(() -> {
                    Asset asset = new Asset();
                    asset.setName(assetCreateDto.getName());
                    asset.setInventoryNumber(assetCreateDto.getInventoryNumber());
                    asset.setSerialNumber(assetCreateDto.getSerialNumber());
                    asset.setImei(assetCreateDto.getImei());
                    asset.setOwnership(assetCreateDto.getOwnership());
                    asset.setComments(assetCreateDto.getComments());
                    asset.setProject(projectRepository.findById(assetCreateDto.getProject_id()).get());
                    asset.setAssetCategory(assetCategoryRepository.findById(assetCreateDto.getCategory_id()).get());
                    asset.setAssetSupplier(assetSupplierRepository.findById(assetCreateDto.getSupplier_id()).get());
                    asset.setAssetStatus(assetStatusRepository.findById(assetCreateDto.getStatus_id()).get());
                    asset.setAppUser(userRepository.findById(assetCreateDto.getUser_id()).get());
                    return assetRepository.save(asset);
                });

        return assetMapper.toDTO(newAsset);
    }

    @Override
    public Page<AssetDetailDto> findAll(Pageable pageable) {
        Page<Asset> assetPage = assetRepository.findAll(pageable);
        List<Asset> assetList =assetPage.getContent();
        List<AssetDetailDto> assetDetailDtoList = assetMapper.fromListAssetToListAssetDetailDto(assetList);
        return new PageImpl<>(assetDetailDtoList, pageable, assetPage.getTotalElements());
    }

    @Override
    public Page<AssetDetailDto> findByNameContaining(String name, Pageable pageable) {
        Page<Asset> assetPage = assetRepository.findByNameContaining(name, pageable);
        List<Asset> assetList =assetPage.getContent();
        List<AssetDetailDto> assetDetailDtoList = assetMapper.fromListAssetToListAssetDetailDto(assetList);
        return new PageImpl<>(assetDetailDtoList, pageable, assetPage.getTotalElements());
    }

    @Override
    public Page<AssetDetailDto> findPageAllAsset(String keyword, Pageable pageable) {
        Page<Asset> assetPage = assetRepository.findByCertainFields(keyword, pageable);
        List<Asset> assetList =assetPage.getContent();
        List<AssetDetailDto> assetDetailDtoList = assetMapper.fromListAssetToListAssetDetailDto(assetList);
        return new PageImpl<>(assetDetailDtoList, pageable, assetPage.getTotalElements());
    }

    @Override
    public AssetDto findAssetById(long id) throws NotFoundException {

        Optional<Asset> findAsset = assetRepository.findById(id);
        if(!findAsset.isPresent()){
            throw new NotFoundException("Not found asset with id: " + id);
        } else {
            return assetMapper.toDTO(findAsset.get());
        }
    }

    @Override
    public AssetDto updateAssetById(long id, AssetCreateDto assetCreateDto) throws NotFoundException {

        Optional<Asset> updateAsset = Optional.of(assetRepository.findById(id).map(asset -> {
            //update
            asset.setName(assetCreateDto.getName());
            asset.setOwnership(assetCreateDto.getOwnership());
            asset.setInventoryNumber(assetCreateDto.getInventoryNumber());
            asset.setSerialNumber(assetCreateDto.getSerialNumber());
            asset.setImei(assetCreateDto.getImei());
            asset.setComments(assetCreateDto.getComments());
            asset.setProject(projectRepository.findById(assetCreateDto.getProject_id()).get());
            asset.setAssetCategory(assetCategoryRepository.findById(assetCreateDto.getCategory_id()).get());
            asset.setAssetSupplier(assetSupplierRepository.findById(assetCreateDto.getSupplier_id()).get());
            asset.setAssetStatus(assetStatusRepository.findById(assetCreateDto.getStatus_id()).get());
            asset.setAppUser(userRepository.findById(assetCreateDto.getUser_id()).get());
            return assetRepository.save(asset);
        }).orElseGet(() -> {
            //insert
            Asset newAsset = new Asset();
            newAsset.setName(assetCreateDto.getName());
            newAsset.setOwnership(assetCreateDto.getOwnership());
            newAsset.setInventoryNumber(assetCreateDto.getInventoryNumber());
            newAsset.setSerialNumber(assetCreateDto.getSerialNumber());
            newAsset.setImei(assetCreateDto.getImei());
            newAsset.setComments(assetCreateDto.getComments());
            newAsset.setProject(projectRepository.findById(assetCreateDto.getProject_id()).get());
            newAsset.setAssetCategory(assetCategoryRepository.findById(assetCreateDto.getCategory_id()).get());
            newAsset.setAssetSupplier(assetSupplierRepository.findById(assetCreateDto.getSupplier_id()).get());
            newAsset.setAssetStatus(assetStatusRepository.findById(assetCreateDto.getStatus_id()).get());
            newAsset.setAppUser(userRepository.findById(assetCreateDto.getUser_id()).get());
            return assetRepository.save(newAsset);
        }));
        return assetMapper.toDTO(updateAsset.get());
    }

    @Override
    public void deleteAssetById(long id) throws NotFoundException {
        if (assetRepository.existsById(id)) {
            assetRepository.deleteById(id);
        } else {
            throw new NotFoundException("Not found asset with id: " + id);
        }
    }

    @Override
    public List<AssetDto> getListAssetByRequestId(Long id) {
        return assetRepository.getAssetsByRequestId(id).stream().map(asset -> assetMapper.toDTO(asset)).collect(Collectors.toList());
    }

    @Override
    public Object getTotalAssetsByProjects() {
        JSONArray ja = new JSONArray();
        JSONObject mainObj = new JSONObject();
        List<Asset> assetList = assetRepository.findAll();

        for(Project project:projectRepository.findAll())
        {
            JSONObject jo = new JSONObject();
            int count = 0;
            for(Asset asset:assetList){
                if (asset.getProject().getId() == project.getId())
                    count++;
            }
            jo.put("id", project.getId());
            jo.put("name", project.getName());
            jo.put("count", count);

            ja.put(jo);
        }
        mainObj.put("projects", ja);
        return mainObj;
    }

    @Override
    public Object getTotalAssetsBySuppliers() {
        JSONArray ja = new JSONArray();
        JSONObject mainObj = new JSONObject();
        List<Asset> assetList = assetRepository.findAll();

        for(AssetSupplier supplier:assetSupplierRepository.findAll())
        {
            JSONObject jo = new JSONObject();
            int count = 0;
            for(Asset asset:assetList){
                if (asset.getAssetSupplier().getId() == supplier.getId())
                    count++;
            }
            jo.put("id", supplier.getId());
            jo.put("name", supplier.getName());
            jo.put("count", count);

            ja.put(jo);
        }
        mainObj.put("suppliers", ja);
        return mainObj;
    }

    @Override
    public Object getTotalAssetsByStatus() {
        JSONArray ja = new JSONArray();
        JSONObject mainObj = new JSONObject();
        List<Asset> assetList = assetRepository.findAll();

        for(AssetStatus status: assetStatusRepository.findAll())
        {
            JSONObject jo = new JSONObject();
            int count = 0;
            for(Asset asset:assetList){
                if (asset.getAssetStatus().getId() == status.getId())
                    count++;
            }
            jo.put("id", status.getId());
            jo.put("name", status.getName());
            jo.put("count", count);

            ja.put(jo);
        }
        mainObj.put("status", ja);
        return mainObj;
    }

    @Override
    public Object getTotalAssetsByCategories() {
        JSONArray ja = new JSONArray();
        JSONObject mainObj = new JSONObject();
        List<Asset> assetList = assetRepository.findAll();

        for(AssetCategory category:assetCategoryRepository.findAll())
        {
            JSONObject jo = new JSONObject();
            int count = 0;
            for(Asset asset:assetList){
                if (asset.getAssetCategory().getId() == category.getId())
                    count++;
            }
            jo.put("id", category.getId());
            jo.put("name", category.getName());
            jo.put("count", count);

            ja.put(jo);
        }
        mainObj.put("categories", ja);
        return mainObj;
    }

    @Override
    public Page<AssetDetailDto> filterAssetsByCertainFields(String keyword, String projectName, String categoryName, String supplierName, String status,
                                                            String currentOwner, String inventoryNumber, String serialNumber, String imei, Pageable pageable) {
        Page<Asset> assetPage = assetRepository.filterAssetsByCertainFields(keyword, projectName, categoryName, supplierName, status,
                currentOwner, inventoryNumber, serialNumber, imei, pageable);
        List<Asset> assetList =assetPage.getContent();
        List<AssetDetailDto> assetDetailDtoList = assetMapper.fromListAssetToListAssetDetailDto(assetList);
        return new PageImpl<>(assetDetailDtoList, pageable, assetPage.getTotalElements());
    }
}
