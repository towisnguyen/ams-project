package com.r23.ams.service.assetSupplier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r23.ams.exception.NotFoundException;
import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.AppUserDto;
import com.r23.ams.models.dto.assetSupplier.AssetSupplierDetailDto;
import com.r23.ams.models.dto.assetSupplier.AssetSupplierDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssetSupplierService {
    Page<AssetSupplierDetailDto> findAllSuppliersPage(Pageable pageable);
    AssetSupplierDetailDto findById(Long id);
    Page<AssetSupplierDetailDto> findSupplierByName(String name, Pageable pageable);
    AssetSupplierDto createNewSupplier(AssetSupplierDto assetSupplierDto) throws NotFoundException;

    AssetSupplierDto updateSupplierById(Long id, AssetSupplierDto assetSupplierDto);
    ResponseDto deleteSupplierById(Long id) throws JsonProcessingException;
}
