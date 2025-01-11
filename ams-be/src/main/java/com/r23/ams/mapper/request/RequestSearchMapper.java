package com.r23.ams.mapper.request;

import com.r23.ams.models.dto.request.RequestSearchDto;
import com.r23.ams.models.entities.Asset;
import com.r23.ams.models.entities.Request;
import com.r23.ams.repositories.AppUserRepository;
import com.r23.ams.repositories.AssetRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestSearchMapper {
    private static RequestSearchMapper INSTANCE;
    private final AssetRepository assetRepository;
    private final AppUserRepository appUserRepository;

    public RequestSearchMapper(AssetRepository assetRepository, AppUserRepository appUserRepository) {
        this.assetRepository = assetRepository;
        this.appUserRepository = appUserRepository;
    }

    public final RequestSearchMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequestSearchMapper(assetRepository, appUserRepository);
        }
        return INSTANCE;
    }

    public Request toEntity(RequestSearchDto requestSearchDto) {

        Request request = new Request();
        request.setRequestType(requestSearchDto.getRequestType());
        request.setStatus(requestSearchDto.getStatus());
        request.setReason(requestSearchDto.getReason());
        request.setUpdateAt(requestSearchDto.getUpdateAt());

        request.setAssets(assetRepository.findById(requestSearchDto.getAssetId()).get());

        return request;
    }
	
    public List<RequestSearchDto> toSearchDTOList(List<Request> requestList){
        List<RequestSearchDto> dtoList = new ArrayList<RequestSearchDto>();
        RequestSearchDto requestSearchDto = new RequestSearchDto();
        for(Request r: requestList){
            requestSearchDto = toSearchDTO(r);
            dtoList.add(requestSearchDto);
        }
        return dtoList;
    }

    public RequestSearchDto toSearchDTO(Request request){
        //List<Long> assets = request.getAssets().stream().map(Asset::getId).collect(Collectors.toList());

        RequestSearchDto requestSearchDto = new RequestSearchDto();
        requestSearchDto.setRequestType(request.getRequestType());
        requestSearchDto.setStatus(request.getStatus());
        requestSearchDto.setReason(request.getReason());
        requestSearchDto.setUpdateAt(request.getUpdateAt());
        requestSearchDto.setId(request.getId());

        requestSearchDto.setAssetId(request.getAssets().getId());
        requestSearchDto.setAssetName(request.getAssets().getName());
        requestSearchDto.setInventoryAssetNumber(request.getAssets().getInventoryNumber());

        return requestSearchDto;
    }
}
