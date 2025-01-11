package com.r23.ams.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.r23.ams.models.dto.assetSupplier.AssetSupplierDetailDto;
import com.r23.ams.models.dto.assetSupplier.AssetSupplierDto;
import com.r23.ams.models.dto.request.RequestDto;
import com.r23.ams.service.assetSupplier.AssetSupplierServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/assetSuppliers")
public class AssetSupplierController {
    private final AssetSupplierServiceImpl assetSupplierImpl;

    public AssetSupplierController(AssetSupplierServiceImpl assetSupplierImpl) {
        this.assetSupplierImpl = assetSupplierImpl;
    }

    @GetMapping("")
    @Operation(summary = "Get all suppliers", description = "Get all suppliers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list of suppliers successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetSupplierDetailDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Map<String, Object>> getAllSuppliers(
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

            Page<AssetSupplierDetailDto> assetSupplierDtoPage = assetSupplierImpl.findAllSuppliersPage(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("assetList", assetSupplierDtoPage.getContent());
            response.put("currentPage", assetSupplierDtoPage.getNumber());
            response.put("totalItems", assetSupplierDtoPage.getTotalElements());
            response.put("totalPages", assetSupplierDtoPage.getTotalPages());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByName")
    @Operation(summary = "Find supplier by name", description = "Find supplier by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find supplier by name successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetSupplierDetailDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ResponseEntity<Map<String, Object>> findSupplierByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "asc") String sortBy

    ) {

        try {
            Pageable pageable = null;
            if (sortBy.equals("asc")) {
                pageable = PageRequest.of(page, size);
            }
            if (sortBy.equals("desc")) {
                pageable = PageRequest.of(page, size);
            }

            Page<AssetSupplierDetailDto> assetSupplierDtoPage = assetSupplierImpl.findSupplierByName(name, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("assetList", assetSupplierDtoPage.getContent());
            response.put("currentPage", assetSupplierDtoPage.getNumber());
            response.put("totalItems", assetSupplierDtoPage.getTotalElements());
            response.put("totalPages", assetSupplierDtoPage.getTotalPages());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(description = "Find an supplier by id", summary = "Find an supplier by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find Successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetSupplierDetailDto.class))),
            @ApiResponse(responseCode = "404", description = "Supplier With {id} Is Not Found"),
    })
    ResponseEntity<AssetSupplierDetailDto> getOneSupplier(@PathVariable Long id) {
        return ResponseEntity.ok(assetSupplierImpl.findById(id));

    }

    @PostMapping("/create")
    @ApiOperation(value = "Create new supplier", notes = "Create new supplier for system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create Successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetSupplierDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<?> createAssetSupplier(@RequestBody AssetSupplierDto assetSupplierDto) {
        return ResponseEntity.ok(assetSupplierImpl.createNewSupplier(assetSupplierDto));
    }

    @PutMapping("/update/{id}")
    @Operation(description = "Update/Insert a supplier", summary = "Update/Insert a supplier for system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetSupplierDetailDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    ResponseEntity<?> updateSupplier(@PathVariable Long id,@RequestBody AssetSupplierDto assetSupplierDto) {
        return ResponseEntity.ok(assetSupplierImpl.updateSupplierById(id, assetSupplierDto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Delete a supplier by id", summary = "Delete a request for system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    ResponseEntity<?> deleteRequest(@PathVariable Long id) throws JsonProcessingException {
        assetSupplierImpl.deleteSupplierById(id);
        return ResponseEntity.ok("Delete successfully!");
    }
}
