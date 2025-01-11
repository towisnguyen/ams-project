package com.r23.ams.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.r23.ams.exception.NotFoundException;
import com.r23.ams.file.export.AssetExcelService;
import com.r23.ams.models.dto.asset.AssetCreateDto;
import com.r23.ams.models.dto.asset.AssetDetailDto;
import com.r23.ams.models.dto.asset.AssetDto;
import com.r23.ams.repositories.AssetRepository;
import com.r23.ams.service.asset.AssetServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {
    private final AssetServiceImpl assetServiceImpl;

    private final AssetExcelService assetExcelService;

    public AssetController(AssetServiceImpl assetServiceImpl, AssetExcelService assetExcelService) {
        this.assetServiceImpl = assetServiceImpl;
        this.assetExcelService = assetExcelService;
    }

    @Transactional
    @PostMapping("/create")
    @ApiOperation(value = "Create new asset", notes = "Create new asset for system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<?> createAsset(@RequestBody AssetCreateDto assetCreateDto) {
        return ResponseEntity.ok(assetServiceImpl.createNewAsset(assetCreateDto));
    }

    @GetMapping("")
    @Operation(summary = "Get paging assets by name", description = "Get paging assets by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list of assets successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    public ResponseEntity<Map<String, Object>> getAllAssets(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "name") String orderBy,
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

            Page<AssetDetailDto> assetDetailDTOPage;
            assetDetailDTOPage = assetServiceImpl.findAll(pageable);
            if (name == null)
                assetDetailDTOPage = assetServiceImpl.findAll(pageable);
            else
                assetDetailDTOPage = assetServiceImpl.findByNameContaining(name, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("assetList", assetDetailDTOPage.getContent());
            response.put("currentPage", assetDetailDTOPage.getNumber());
            response.put("totalItems", assetDetailDTOPage.getTotalElements());
            response.put("totalPages", assetDetailDTOPage.getTotalPages());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find an asset by id", notes = "Find an asset by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetDto.class))),
            @ApiResponse(responseCode = "404", description = "User With {id} Is Not Found"),
    })
    public ResponseEntity<?> findAssetById(@PathVariable long id) {
        return ResponseEntity.ok(assetServiceImpl.findAssetById(id));
    }

    @Transactional
    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update/Insert an asset", notes = "Update/Insert an asset for system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    public ResponseEntity<?> updateAssetById(@PathVariable long id, @RequestBody AssetCreateDto assetCreateDto) throws JsonProcessingException {
        return ResponseEntity.ok(assetServiceImpl.updateAssetById(id, assetCreateDto));
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete an asset by id", notes = "Delete an asset for system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<?> deleteAssetById(@PathVariable long id) {
        assetServiceImpl.deleteAssetById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getTotalAssetsByProjects")
    @ApiOperation(value = "Search assets by project", notes = "Search assets by project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<?> getTotalAssetsByProjects() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(assetServiceImpl.getTotalAssetsByProjects().toString());
        return ResponseEntity.ok(json);
    }

    @GetMapping("/getTotalAssetsBySuppliers")
    @ApiOperation(value = "Search assets by supplier", notes = "Search assets by supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<?> getTotalAssetsBySuppliers() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(assetServiceImpl.getTotalAssetsBySuppliers().toString());
        return ResponseEntity.ok(json);
    }

    @GetMapping("/getTotalAssetsByStatus")
    @ApiOperation(value = "Search assets by status", notes = "Search assets by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<?> getTotalAssetsByStatus() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(assetServiceImpl.getTotalAssetsByStatus().toString());
        return ResponseEntity.ok(json);
    }

    @GetMapping("/getTotalAssetsByCategories")
    @ApiOperation(value = "Search assets by category", notes = "Search assets by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<?> getTotalAssetsByCategories() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(assetServiceImpl.getTotalAssetsByCategories().toString());
        return ResponseEntity.ok(json);
    }

    @GetMapping("/filterAssetsByCertainFields")
    @Operation(description = "Search assets with pagination", summary = "Search assets with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search Successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    public ResponseEntity<?> filterByCertainFields(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "projectName", defaultValue = "") String projectName,
            @RequestParam(name = "categoryName", defaultValue = "") String categoryName,
            @RequestParam(name = "supplierName", defaultValue = "") String supplierName,
            @RequestParam(name = "status", defaultValue = "") String status,
            @RequestParam(name = "currentOwner", defaultValue = "") String currentOwner,
            @RequestParam(name = "inventoryNumber", defaultValue = "") String inventoryNumber,
            @RequestParam(name = "serialNumber", defaultValue = "") String serialNumber,
            @RequestParam(name = "imei", defaultValue = "") String imei,
            @RequestParam(name = "page", defaultValue = "0") int page, //page number
            @RequestParam(name = "limit", defaultValue = "3") int limit, //page size
            @RequestParam(name = "orderBy", defaultValue = "name") String orderBy, //database field
            @RequestParam(name = "sortBy", defaultValue = "desc") String sortBy)
            throws NotFoundException {
        Pageable pageable = null;

        if (sortBy.equals("asc")) {
            Sort sort = Sort.by(orderBy).ascending();
            pageable = PageRequest.of(page, limit, sort);
        }
        if (sortBy.equals("desc")) {
            Sort sort = Sort.by(orderBy).descending();
            pageable = PageRequest.of(page, limit, sort);
        }
        Page<AssetDetailDto> assetDetailDTOPage = assetServiceImpl.filterAssetsByCertainFields(keyword, projectName, categoryName, supplierName, status, currentOwner, inventoryNumber, serialNumber, imei, pageable);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("data", assetDetailDTOPage.getContent());
        response.put("limit", assetDetailDTOPage.getSize());
        response.put("currentPage", assetDetailDTOPage.getNumber());
        response.put("totalItems", assetDetailDTOPage.getTotalElements());
        response.put("totalPages", assetDetailDTOPage.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/export/excel")
    @Operation(description = "Export to excel", summary = "Export to excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=asset_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        assetExcelService.exportAllAsset(response);
    }

}

