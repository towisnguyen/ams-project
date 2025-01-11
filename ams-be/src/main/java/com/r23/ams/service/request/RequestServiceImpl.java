package com.r23.ams.service.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r23.ams.exception.NotFoundException;
import com.r23.ams.mapper.request.RequestMapper;
import com.r23.ams.mapper.request.RequestSearchMapper;
import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.dto.request.RequestDto;
import com.r23.ams.models.dto.request.RequestSearchDto;
import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.Asset;
import com.r23.ams.models.entities.Request;
import com.r23.ams.repositories.AppUserRepository;
import com.r23.ams.repositories.AssetRepository;
import com.r23.ams.repositories.RequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final AssetRepository assetRepository;
    private final AppUserRepository appUserRepository;
    private final RequestMapper requestMapper;

    private final RequestSearchMapper requestSearchMapper;

    public RequestServiceImpl(RequestRepository requestRepository, AssetRepository assetRepository,
                              AppUserRepository appUserRepository, RequestMapper requestMapper,
                              RequestSearchMapper requestSearchMapper) {
        this.requestRepository = requestRepository;
        this.assetRepository = assetRepository;
        this.appUserRepository = appUserRepository;
        this.requestMapper = requestMapper;
        this.requestSearchMapper = requestSearchMapper;
    }


    @Override
    public Page<RequestSearchDto> findAllRequestsPage(Pageable pageable) {
        Page<Request> requestPage = requestRepository.findAll(pageable);
        List<Request> requestList = requestPage.getContent();

        List<RequestSearchDto> requestDtoList = requestSearchMapper.toSearchDTOList(requestList);

        return new PageImpl<>(requestDtoList, pageable, requestPage.getTotalElements());
    }

    @Override
    public Page<RequestSearchDto> findAllSearchRequestPage(Pageable pageable) {
        Page<Request> requestPage = requestRepository.findAll(pageable);
        List<Request> requestList = requestPage.getContent();

        List<RequestSearchDto> requestDtoList = requestSearchMapper.toSearchDTOList(requestList);

        return new PageImpl<>(requestDtoList, pageable, requestPage.getTotalElements());
    }

    @Override
	public Page<RequestSearchDto> searchRequestByAssetName(String assetName, Pageable pageable) {
        Page<Request> requestPage = requestRepository.searchRequestByAssetName(assetName, pageable);
        List<Request> requestList = requestPage.getContent();
        List<RequestSearchDto> requestDtoList = requestSearchMapper.toSearchDTOList(requestList);
        return new PageImpl<>(requestDtoList, pageable, requestPage.getTotalElements());
    }

    @Override
    public Page<RequestSearchDto> searchRequestByInventoryNumber(String inventoryNumber, Pageable pageable) {
        Page<Request> requestPage = requestRepository.searchRequestByInventoryNumber(inventoryNumber, pageable);
        List<Request> requestList = requestPage.getContent();
        List<RequestSearchDto> requestDtoList = requestSearchMapper.toSearchDTOList(requestList);
        return new PageImpl<>(requestDtoList, pageable, requestPage.getTotalElements());
    }

    @Override
    public Page<RequestSearchDto> searchRequestByStatus(String status, Pageable pageable) {
        Page<Request> requestPage = requestRepository.searchRequestByStatus(status, pageable);
        List<Request> requestList = requestPage.getContent();
        List<RequestSearchDto> requestDtoList = requestSearchMapper.toSearchDTOList(requestList);
        return new PageImpl<>(requestDtoList, pageable, requestPage.getTotalElements());
    }

    @Override
    public Page<RequestSearchDto> searchRequestByType(String type, Pageable pageable) {
        Page<Request> requestPage = requestRepository.searchRequestByType(type, pageable);
        List<Request> requestList = requestPage.getContent();
        List<RequestSearchDto> requestDtoList = requestSearchMapper.toSearchDTOList(requestList);
        return new PageImpl<>(requestDtoList, pageable, requestPage.getTotalElements());
    }

    @Override		
    public Page<RequestDto> findByCreatedContaining(String name, Pageable pageable) {
        Page<Request> requestPage = requestRepository.findByCreatedContaining(name, pageable);
        List<Request> requestList = requestPage.getContent();
        List<RequestDto> requestDtoList = requestMapper.toDTOList(requestList);
        return new PageImpl<>(requestDtoList, pageable, requestPage.getTotalElements());

    }

    @Override
    public RequestSearchDto findById(Long id) {
        Optional<Request> requestOptional = requestRepository.findById(id);
        if (requestOptional.isEmpty()) {
            throw new NotFoundException("Request ID " + id + " not found!");
        }
        Request request = requestOptional.get();
        RequestSearchDto requestDto = requestSearchMapper.toSearchDTO(request);
        return requestDto;
    }

    @Override
    public RequestDto createNewRequest(RequestDto createdRequestDto) {
        //List<Asset> assets = assetRepository.findByIds(createdRequestDto.getAssetIds());
        List<AppUser> userSend = appUserRepository.findByIds(createdRequestDto.getUserIdSend());

        List<AppUser> userReceive = appUserRepository.findByIds(createdRequestDto.getUserIdReceive());

        List<AppUser> userProcess = appUserRepository.findByIds(createdRequestDto.getUserIdApprove());

        Request request = new Request();
        request.setRequestType(createdRequestDto.getRequestType());
        request.setStatus(createdRequestDto.getStatus());
        request.setReason(createdRequestDto.getReason());

        request.setAssets(assetRepository.findById(createdRequestDto.getAssetIds()).get());

        request.setUserSend(userSend);

        request.setUserReceive(userReceive);

        request.setUserApprove(userProcess);

        requestRepository.save(request);

        return requestMapper.toDTO(request);
    }

    @Override
    public RequestDto updateRequestById(Long id, RequestDto updatedRequestDto) {
        Optional<Request> requestOptional = requestRepository.findById(id);
        if (requestOptional.isEmpty()) {
            throw new NotFoundException("Request ID " + id + " not found!");
        }
        Request request = requestOptional.get();
        if (updatedRequestDto.getReason() != null) {
            request.setReason(updatedRequestDto.getReason());
        }
        if (updatedRequestDto.getRequestType() != null) {
            request.setRequestType(updatedRequestDto.getRequestType());
        }
        if (updatedRequestDto.getStatus() != null) {
            request.setStatus(updatedRequestDto.getStatus());
        }

        //List<Asset> assets = assetRepository.findByIds(updatedRequestDto.getAssetIds());
//        List<AppUser> userSend = appUserRepository.findByIds(updatedRequestDto.getUserIdSend());
//
//        List<AppUser> userReceive = appUserRepository.findByIds(updatedRequestDto.getUserIdReceive());
//
//        List<AppUser> userProcess = appUserRepository.findByIds(updatedRequestDto.getUserIdApprove());
//
//        if (updatedRequestDto.getUserIdSend() != null) {
//            request.setUserSend(userSend);
//        }
//        if (updatedRequestDto.getUserIdReceive() != null) {
//            request.setUserReceive(userReceive);
//        }
//        if (updatedRequestDto.getUserIdApprove() != null) {
//            request.setUserApprove(userProcess);
//        }

        /*request.setReason(updatedRequest.getReason());
        request.setRequestType(updatedRequest.getRequestType());
        request.setStatus(updatedRequest.getStatus());*/

        request.setUpdateAt(new Date());

        requestRepository.save(request);
        RequestDto requestDto = requestMapper.toDTO(request);
        return requestDto;
    }

    @Override
    public ResponseDto deleteRequestById(Long id) throws JsonProcessingException {
        Optional<Request> requestOptional = requestRepository.findById(id);
        if (requestOptional.isEmpty()) {
            throw new NotFoundException("Request ID " + id + " not found!");
        }
        requestRepository.deleteById(id);
        return new ResponseDto("Delete successfully ID: " + id);
    }

    @Override
    public List<RequestDto> getListRequestByAssetId(Long id) {
        return requestRepository.getRequestByAssetId(id).stream().map(request -> requestMapper.toDTO(request)).collect(Collectors.toList());
    }

    @Override
    public Page<RequestDto> searchAllRequestByCertainFields(String assetName, String requestType, String status, String inventoryNumber, Pageable pageable) {
        Page<Request> requestPage = requestRepository.searchAllRequestByCertainFields(assetName, requestType, status, inventoryNumber, pageable);
        List<Request> requestList = requestPage.getContent();
        List<RequestDto> requestDtoList = requestMapper.toDTOList(requestList);
        return new PageImpl<>(requestDtoList, pageable, requestPage.getTotalElements());
    }


    ////For SearchRequestDTO
    @Override
    public Page<RequestSearchDto> searchAllRequestByCertainFieldsForSeachDTO(String assetName, String requestType, String status, String inventoryNumber, Pageable pageable) {
        Page<Request> requestPage = requestRepository.searchAllRequestByCertainFields(assetName, requestType, status, inventoryNumber, pageable);
        List<Request> requestList = requestPage.getContent();
        List<RequestSearchDto> requestDtoList = requestSearchMapper.toSearchDTOList(requestList);
        return new PageImpl<>(requestDtoList, pageable, requestPage.getTotalElements());
    }
}
