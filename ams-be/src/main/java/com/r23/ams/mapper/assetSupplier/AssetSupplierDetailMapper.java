package com.r23.ams.mapper.assetSupplier;

import com.r23.ams.models.dto.assetSupplier.AssetSupplierDetailDto;
import com.r23.ams.models.entities.AssetSupplier;
import com.r23.ams.repositories.AppUserRepository;
import com.r23.ams.repositories.AssetRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AssetSupplierDetailMapper {
    private static AssetSupplierDetailMapper INSTANCE;
    private final AssetRepository assetRepository;
    private final AppUserRepository appUserRepository;

    public AssetSupplierDetailMapper(AssetRepository assetRepository, AppUserRepository appUserRepository) {
        this.assetRepository = assetRepository;
        this.appUserRepository = appUserRepository;
    }

    public final AssetSupplierDetailMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AssetSupplierDetailMapper(assetRepository, appUserRepository);
        }
        return INSTANCE;
    }

    public AssetSupplier toEntity(AssetSupplierDetailDto assetSupplierDetailDto) {

        AssetSupplier assetSupplier = new AssetSupplier();
        assetSupplier.setId(assetSupplierDetailDto.getId());
        assetSupplier.setName(assetSupplierDetailDto.getName());
        return assetSupplier;
    }

    public AssetSupplierDetailDto toDto(AssetSupplier assetSupplier){
        AssetSupplierDetailDto assetSupplierDetailDto = new AssetSupplierDetailDto();
        assetSupplierDetailDto.setId(assetSupplier.getId());
        assetSupplierDetailDto.setName(assetSupplier.getName());
        return assetSupplierDetailDto;
    }

    public List<AssetSupplierDetailDto> toDTOList(List<AssetSupplier> assetSupplierList){
        List<AssetSupplierDetailDto> dtoList = new ArrayList<AssetSupplierDetailDto>();
        AssetSupplierDetailDto assetSupplierDetailDto = new AssetSupplierDetailDto();
        for(AssetSupplier a: assetSupplierList){
            assetSupplierDetailDto = toDto(a);
            dtoList.add(assetSupplierDetailDto);
        }
        return dtoList;
    }
}
