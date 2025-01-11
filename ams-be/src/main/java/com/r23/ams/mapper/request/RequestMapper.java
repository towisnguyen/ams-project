package com.r23.ams.mapper.request;

import com.r23.ams.models.dto.request.RequestDto;
import com.r23.ams.models.dto.request.RequestSearchDto;
import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.Asset;
import com.r23.ams.models.entities.Request;
import com.r23.ams.repositories.AppUserRepository;
import com.r23.ams.repositories.AssetRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestMapper {
    private static RequestMapper INSTANCE;
    private final AssetRepository assetRepository;
    private final AppUserRepository appUserRepository;

    public RequestMapper(AssetRepository assetRepository, AppUserRepository appUserRepository) {
        this.assetRepository = assetRepository;
        this.appUserRepository = appUserRepository;
    }

    public final RequestMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequestMapper(assetRepository, appUserRepository);
        }
        return INSTANCE;
    }

    public Request toEntity(RequestDto requestDto) {

        //return with array list Long
        List<AppUser> userSend = appUserRepository.findByIds(requestDto.getUserIdSend());

        List<AppUser> userReceive = appUserRepository.findByIds(requestDto.getUserIdReceive());

        List<AppUser> userProcess = appUserRepository.findByIds(requestDto.getUserIdApprove());

        Request request = new Request();
        request.setRequestType(requestDto.getRequestType());
        request.setStatus(requestDto.getStatus());
        request.setReason(requestDto.getReason());
        request.setUpdateAt(requestDto.getUpdateAt());

        request.setAssets(assetRepository.findById(requestDto.getAssetIds()).get());

        request.setUserSend(userSend);
        request.setUserReceive(userReceive);
        request.setUserApprove(userProcess);

        return request;
    }

    public RequestDto toDTO(Request request){

        List<Long> userSend = request.getUserSend().stream().map(AppUser::getId).collect(Collectors.toList());

        List<Long> userReceive = request.getUserReceive().stream().map(AppUser::getId).collect(Collectors.toList());

        List<Long> userProcees = request.getUserApprove().stream().map(AppUser::getId).collect(Collectors.toList());

        RequestDto requestDto = new RequestDto();
        requestDto.setRequestType(request.getRequestType());
        requestDto.setStatus(request.getStatus());
        requestDto.setReason(request.getReason());
        requestDto.setUpdateAt(request.getUpdateAt());

        requestDto.setAssetIds(request.getAssets().getId());

        requestDto.setUserIdSend(userSend);
        requestDto.setUserIdReceive(userReceive);
        requestDto.setUserIdApprove(userProcees);

        return requestDto;
    }

    public List<RequestDto> toDTOList(List<Request> requestList){
        List<RequestDto> dtoList = new ArrayList<RequestDto>();
        RequestDto requestDto = new RequestDto();
        for(Request r: requestList){
            requestDto = toDTO(r);
            dtoList.add(requestDto);
        }
        return dtoList;
    }

}
