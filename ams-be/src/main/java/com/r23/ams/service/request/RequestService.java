package com.r23.ams.service.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r23.ams.models.dto.request.RequestDto;
import com.r23.ams.models.dto.request.RequestSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestService {
    //RequestDto findById(Long id);

    RequestSearchDto findById(Long id);

    Page<RequestSearchDto> findAllRequestsPage(Pageable pageable);

    Page<RequestDto> findByCreatedContaining(String name, Pageable pageable);

    RequestDto createNewRequest(RequestDto request);

    RequestDto updateRequestById(Long id, RequestDto request);

    Object deleteRequestById(Long id) throws JsonProcessingException;

    List<RequestDto> getListRequestByAssetId(Long id);

    Page<RequestDto> searchAllRequestByCertainFields(String assetName, String requestType, String status, String inventoryNumber, Pageable pageable);

    Page<RequestSearchDto> searchAllRequestByCertainFieldsForSeachDTO(String assetName, String requestType, String status, String inventoryNumber, Pageable pageable);

    Page<RequestSearchDto> findAllSearchRequestPage(Pageable pageable);
	Page<RequestSearchDto> searchRequestByAssetName(String assetName, Pageable pageable);
    Page<RequestSearchDto> searchRequestByInventoryNumber(String inventoryNumber, Pageable pageable);
    Page<RequestSearchDto> searchRequestByStatus(String status,Pageable pageable);
    Page<RequestSearchDto> searchRequestByType(String type,Pageable pageable);
}
