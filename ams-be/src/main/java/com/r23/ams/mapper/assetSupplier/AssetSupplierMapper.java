package com.r23.ams.mapper.assetSupplier;

import com.r23.ams.models.dto.assetSupplier.AssetSupplierDto;
import com.r23.ams.models.entities.AssetSupplier;
import com.r23.ams.repositories.AppUserRepository;
import com.r23.ams.repositories.AssetRepository;
import org.springframework.stereotype.Component;

@Component
public class AssetSupplierMapper {
    private static AssetSupplierMapper INSTANCE;
    private final AssetRepository assetRepository;
    private final AppUserRepository appUserRepository;

    public AssetSupplierMapper(AssetRepository assetRepository, AppUserRepository appUserRepository) {
        this.assetRepository = assetRepository;
        this.appUserRepository = appUserRepository;
    }

    public final AssetSupplierMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AssetSupplierMapper(assetRepository, appUserRepository);
        }
        return INSTANCE;
    }

    public AssetSupplier toEntity(AssetSupplierDto assetSupplierDto) {

        AssetSupplier assetSupplier = new AssetSupplier();
        assetSupplier.setName(assetSupplierDto.getName());
        return assetSupplier;
    }

    public AssetSupplierDto toDto(AssetSupplier assetSupplier){
        AssetSupplierDto assetSupplierDto = new AssetSupplierDto();
        assetSupplierDto.setName(assetSupplier.getName());
        return assetSupplierDto;
    }
}
