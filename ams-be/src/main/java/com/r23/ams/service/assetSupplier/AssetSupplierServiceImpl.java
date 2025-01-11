package com.r23.ams.service.assetSupplier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r23.ams.exception.NotFoundException;
import com.r23.ams.mapper.AppUserMapper;
import com.r23.ams.mapper.assetSupplier.AssetSupplierDetailMapper;
import com.r23.ams.mapper.assetSupplier.AssetSupplierMapper;
import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.AppUserDto;
import com.r23.ams.models.dto.assetSupplier.AssetSupplierDetailDto;
import com.r23.ams.models.dto.assetSupplier.AssetSupplierDto;
import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.AssetSupplier;
import com.r23.ams.repositories.AssetSupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetSupplierServiceImpl implements AssetSupplierService {
    private final AssetSupplierRepository assetSupplierRepository;
    private final AssetSupplierDetailMapper assetSupplierDetailMapper;
    private final AssetSupplierMapper assetSupplierMapper;

    public AssetSupplierServiceImpl(AssetSupplierRepository assetSupplierRepository, AssetSupplierDetailMapper assetSupplierDetailMapper, AssetSupplierMapper assetSupplierMapper) {
        this.assetSupplierRepository = assetSupplierRepository;
        this.assetSupplierDetailMapper = assetSupplierDetailMapper;
        this.assetSupplierMapper = assetSupplierMapper;
    }

    @Override
    public Page<AssetSupplierDetailDto> findAllSuppliersPage(Pageable pageable) {
        Page<AssetSupplier> assetSupplierPage = assetSupplierRepository.findAll(pageable);
        List<AssetSupplier> assetSupplierList = assetSupplierPage.getContent();

        List<AssetSupplierDetailDto> assetSupplierDetailDtoList = assetSupplierDetailMapper.toDTOList(assetSupplierList);

        return new PageImpl<>(assetSupplierDetailDtoList, pageable, assetSupplierPage.getTotalElements());
    }

    @Override
    public AssetSupplierDetailDto findById(Long id) {
        Optional<AssetSupplier> assetSupplierOptional = assetSupplierRepository.findById(id);
        if (assetSupplierOptional.isEmpty()) {
            throw new NotFoundException("Supplier ID " + id + " not found!");
        }
        AssetSupplier assetSupplier = assetSupplierOptional.get();
        AssetSupplierDetailDto assetSupplierDetailDto = assetSupplierDetailMapper.toDto(assetSupplier);
        return assetSupplierDetailDto;
    }

    @Override
    public Page<AssetSupplierDetailDto> findSupplierByName(String name, Pageable pageable) {
        Page<AssetSupplier> assetSupplierPage = assetSupplierRepository.findSupplierByName(name, pageable);
        List<AssetSupplier> assetSupplierList = assetSupplierPage.getContent();

        List<AssetSupplierDetailDto> userDTOList = assetSupplierDetailMapper.toDTOList(assetSupplierList);

        return new PageImpl<>(userDTOList, pageable, assetSupplierPage.getTotalElements());
    }


    @Override
    public AssetSupplierDto createNewSupplier(AssetSupplierDto assetSupplierDto) throws NotFoundException {
        Optional<AssetSupplier> newAssetSupplier = assetSupplierRepository.findByName(assetSupplierDto.getName()).stream().findFirst();
        if (newAssetSupplier.isPresent()) {
            throw new RuntimeException("Supplier name " +assetSupplierDto.getName() + " already!");
        }
            AssetSupplier assetSupplier = new AssetSupplier();
            assetSupplier.setName(assetSupplierDto.getName());
            assetSupplierRepository.save(assetSupplier);

        return assetSupplierMapper.toDto(assetSupplier);
    }

    @Override
    public AssetSupplierDto updateSupplierById(Long id, AssetSupplierDto assetSupplierDto) {
        Optional<AssetSupplier> assetSupplierOptional = assetSupplierRepository.findById(id);
        if (assetSupplierOptional.isEmpty()){
            throw new NotFoundException("Supplier ID: " + id + " is not found!");
        }
        AssetSupplier assetSupplier = assetSupplierOptional.get();
        if (assetSupplierDto.getName() != null){
            assetSupplier.setName(assetSupplierDto.getName());
        }
        assetSupplierRepository.save(assetSupplier);
        AssetSupplierDto assetSupplierDtoMapper = assetSupplierMapper.toDto(assetSupplier);
        return assetSupplierDtoMapper;
    }

    @Override
    public ResponseDto deleteSupplierById(Long id) throws JsonProcessingException {
        Optional<AssetSupplier> assetSupplier = assetSupplierRepository.findById(id);
        if (assetSupplier.isEmpty()) {
            throw new NotFoundException("Supplier ID " + id + " not found!");
        }
        assetSupplierRepository.deleteById(id);
        return new ResponseDto("Delete successfully ID: " + id);
    }
}
