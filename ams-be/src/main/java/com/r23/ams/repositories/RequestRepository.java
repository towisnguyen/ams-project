package com.r23.ams.repositories;

import com.r23.ams.models.ResponseDto;
import com.r23.ams.models.entities.Asset;
import com.r23.ams.models.entities.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findById(Long id);

    Page<Request> findByCreatedContaining(String name, Pageable pageable);

    @Query(value = "select * from requests r left join request_asset ra on r.id = ra.asset_id where ra.asset_id = ?1", nativeQuery = true)
    List<Request> getRequestByAssetId(Long id);

    @Query(value = "SELECT r.*, a.name, a.inventory_number FROM (requests r INNER JOIN assets a ON r.asset_id = a.id) \n" +
            "WHERE lower(a.name) LIKE lower(concat('%', ?1,'%')) AND \n" +
            "lower(r.request_type) LIKE lower(concat('%', ?2,'%')) AND \n" +
            "lower(r.status) LIKE lower(concat('%', ?3,'%')) AND \n" +
            "lower(a.inventory_number) LIKE lower(concat('%', ?4,'%'))", nativeQuery = true)
    Page<Request> searchAllRequestByCertainFields(String assetName, String requestType, String status, String inventoryNumber, Pageable pageable);

    @Query(value = "SELECT r.*, a.name, a.inventory_number FROM (requests r INNER JOIN assets a ON r.asset_id = a.id) \n" +
            "WHERE lower(a.name) LIKE lower(concat('%', ?1,'%'))", nativeQuery = true)
    Page<Request> searchRequestByAssetName(String assetName, Pageable pageable);

    @Query(value = "SELECT r.*, a.name, a.inventory_number FROM (requests r INNER JOIN assets a ON r.asset_id = a.id) \n" +
            "WHERE lower(a.inventory_number) LIKE lower(concat('%', ?1,'%'))", nativeQuery = true)
    Page<Request> searchRequestByInventoryNumber(String inventoryNumber, Pageable pageable);

    @Query(value = "SELECT r.*, a.name, a.inventory_number FROM (requests r INNER JOIN assets a ON r.asset_id = a.id) \n" +
            "WHERE lower(r.status) LIKE lower(concat('%', ?1,'%'))", nativeQuery = true)
    Page<Request> searchRequestByStatus(String status, Pageable pageable);

    @Query(value = "SELECT r.*, a.name, a.inventory_number FROM (requests r INNER JOIN assets a ON r.asset_id = a.id) \n" +
            "WHERE lower(r.request_type) LIKE lower(concat('%', ?,'%'))", nativeQuery = true)
    Page<Request> searchRequestByType(String type, Pageable pageable);														  
}
