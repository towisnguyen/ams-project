package com.r23.ams.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r23.ams.exception.NotFoundException;
import com.r23.ams.models.dto.request.RequestDto;
import com.r23.ams.models.dto.request.RequestSearchDto;
import com.r23.ams.service.request.RequestServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/requests")
public class RequestController {
    @Autowired
    RequestServiceImpl requestServiceImpl;

    @GetMapping("")
    @Operation(summary = "Get all requests", description = "Get all requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list of requests successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestSearchDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Map<String, Object>> getAllRequest(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String orderBy,
            @RequestParam(defaultValue = "asc") String sortBy

    ) {

        try {
            Pageable pageable = null;
            if (sortBy.equals("asc")) {
                Sort sort = Sort.by(orderBy).ascending();
                pageable = PageRequest.of(page, size, sort);
            }
            if (sortBy.equals("desc")) {
                Sort sort = Sort.by(orderBy).descending();
                pageable = PageRequest.of(page, size, sort);
            }

            Page<RequestSearchDto> requestDtoPage;
            requestDtoPage = requestServiceImpl.findAllRequestsPage(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("assetList", requestDtoPage.getContent());
            response.put("currentPage", requestDtoPage.getNumber());
            response.put("totalItems", requestDtoPage.getTotalElements());
            response.put("totalPages", requestDtoPage.getTotalPages());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(description = "Find a request by id", summary = "Find a request by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestSearchDto.class))),
            @ApiResponse(responseCode = "404", description = "User With {id} Is Not Found"),
    })
    ResponseEntity<RequestSearchDto> getOneRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestServiceImpl.findById(id));

    }

    @GetMapping("/search")
    @Operation(description = "Search request by assetName, status, type, inventoryNumber", summary = "Search request by assetName, status, type, inventoryNumber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> searchRequestByCertainFields(
            @RequestParam(required = false) String assetName,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) String inventoryNumber,
            @RequestParam(name = "page", defaultValue = "0") int page, //page number
            @RequestParam(name = "size", defaultValue = "10") int size, //page size
            @RequestParam(name = "orderBy", defaultValue = "created") String orderBy, //database field
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy)
            throws NotFoundException {
        Pageable pageable = null;

        if (sortBy.equals("asc")) {
            Sort sort = Sort.by(orderBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        }
        if (sortBy.equals("desc")) {
            Sort sort = Sort.by(orderBy).descending();
            pageable = PageRequest.of(page, size, sort);
        }

        Page<RequestSearchDto> requestSearchDto = requestServiceImpl.searchAllRequestByCertainFieldsForSeachDTO(assetName, requestType, status, inventoryNumber, pageable);
        if (assetName == null && status == null && requestType == null && inventoryNumber == null){
            requestSearchDto = requestServiceImpl.findAllSearchRequestPage(pageable);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", requestSearchDto.getContent());
        response.put("size", requestSearchDto.getSize());
        response.put("currentPage", requestSearchDto.getNumber());
        response.put("totalItems", requestSearchDto.getTotalElements());
        response.put("totalPages", requestSearchDto.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchByAssetName")
    @Operation(description = "Search request by Asset name", summary = "Search request by Asset name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> searchRequestByAssetName(
            @RequestParam(required = false) String assetName,
            @RequestParam(name = "page", defaultValue = "0") int page, //page number
            @RequestParam(name = "size", defaultValue = "10") int size, //page size
            @RequestParam(name = "orderBy", defaultValue = "created") String orderBy, //database field
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy)
            throws NotFoundException {
        Pageable pageable = null;

        if (sortBy.equals("asc")) {
            Sort sort = Sort.by(orderBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        }
        if (sortBy.equals("desc")) {
            Sort sort = Sort.by(orderBy).descending();
            pageable = PageRequest.of(page, size, sort);
        }

        Page<RequestSearchDto> requestSearchDto = requestServiceImpl.searchRequestByAssetName(assetName, pageable);
        if (assetName == null){
            requestSearchDto = requestServiceImpl.findAllSearchRequestPage(pageable);
        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", requestSearchDto.getContent());
        response.put("size", requestSearchDto.getSize());
        response.put("currentPage", requestSearchDto.getNumber());
        response.put("totalItems", requestSearchDto.getTotalElements());
        response.put("totalPages", requestSearchDto.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchByInventoryNumber")
    @Operation(description = "Search request by Inventory Number", summary = "Search request by Inventory Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> searchRequestByInventoryNumber(
            @RequestParam(required = false) String inventoryNumber,
            @RequestParam(name = "page", defaultValue = "0") int page, //page number
            @RequestParam(name = "size", defaultValue = "10") int size, //page size
            @RequestParam(name = "orderBy", defaultValue = "created") String orderBy, //database field
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy)
            throws NotFoundException {
        Pageable pageable = null;

        if (sortBy.equals("asc")) {
            Sort sort = Sort.by(orderBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        }
        if (sortBy.equals("desc")) {
            Sort sort = Sort.by(orderBy).descending();
            pageable = PageRequest.of(page, size, sort);
        }

        Page<RequestSearchDto> requestSearchDto = requestServiceImpl.searchRequestByInventoryNumber(inventoryNumber, pageable);
        if (inventoryNumber == null){
            requestSearchDto = requestServiceImpl.findAllSearchRequestPage(pageable);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", requestSearchDto.getContent());
        response.put("size", requestSearchDto.getSize());
        response.put("currentPage", requestSearchDto.getNumber());
        response.put("totalItems", requestSearchDto.getTotalElements());
        response.put("totalPages", requestSearchDto.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchByStatus")
    @Operation(description = "Search request by Status", summary = "Search request by Status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> searchRequestByStatus(
            @RequestParam(required = false) String status,
            @RequestParam(name = "page", defaultValue = "0") int page, //page number
            @RequestParam(name = "size", defaultValue = "10") int size, //page size
            @RequestParam(name = "orderBy", defaultValue = "created") String orderBy, //database field
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy)
            throws NotFoundException {
        Pageable pageable = null;

        if (sortBy.equals("asc")) {
            Sort sort = Sort.by(orderBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        }
        if (sortBy.equals("desc")) {
            Sort sort = Sort.by(orderBy).descending();
            pageable = PageRequest.of(page, size, sort);
        }

        Page<RequestSearchDto> requestSearchDto = requestServiceImpl.searchRequestByStatus(status, pageable);
        if (status == null){
            requestSearchDto = requestServiceImpl.findAllSearchRequestPage(pageable);
        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", requestSearchDto.getContent());
        response.put("size", requestSearchDto.getSize());
        response.put("currentPage", requestSearchDto.getNumber());
        response.put("totalItems", requestSearchDto.getTotalElements());
        response.put("totalPages", requestSearchDto.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchByType")
    @Operation(description = "Search request by Type", summary = "Search request by Type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> searchRequestByType(
            @RequestParam(required = false) String requestType,
            @RequestParam(name = "page", defaultValue = "0") int page, //page number
            @RequestParam(name = "size", defaultValue = "10") int size, //page size
            @RequestParam(name = "orderBy", defaultValue = "created") String orderBy, //database field
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy)
            throws NotFoundException {
        Pageable pageable = null;

        if (sortBy.equals("asc")) {
            Sort sort = Sort.by(orderBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        }
        if (sortBy.equals("desc")) {
            Sort sort = Sort.by(orderBy).descending();
            pageable = PageRequest.of(page, size, sort);
        }

        Page<RequestSearchDto> requestSearchDto = requestServiceImpl.searchRequestByType(requestType, pageable);
        if (requestType == null){
            requestSearchDto = requestServiceImpl.findAllSearchRequestPage(pageable);
        }

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", requestSearchDto.getContent());
        response.put("size", requestSearchDto.getSize());
        response.put("currentPage", requestSearchDto.getNumber());
        response.put("totalItems", requestSearchDto.getTotalElements());
        response.put("totalPages", requestSearchDto.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    @Operation(description = "Create new request", summary = "Create new request for system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    ResponseEntity<?> createNewRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(requestServiceImpl.createNewRequest(requestDto));
    }

    @Transactional
    @PutMapping("/update/{id}")
    @Operation(description = "Update/Insert a request", summary = "Update/Insert a request for system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    ResponseEntity<?> updateRequest(@PathVariable Long id,@RequestBody RequestDto request) {
        return ResponseEntity.ok(requestServiceImpl.updateRequestById(id, request));
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    @Operation(description = "Delete a request by id", summary = "Delete a request for system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    ResponseEntity<?> deleteRequest(@PathVariable Long id) throws JsonProcessingException {
        requestServiceImpl.deleteRequestById(id);
        return ResponseEntity.ok("Delete successfully!");
    }
}
