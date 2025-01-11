package com.r23.ams.repositories;

import com.r23.ams.models.entities.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByName(String name);

    Page<Asset> findAll(Pageable pageable);

    //@Query(value="select * from assets where assets.name like %?1%", nativeQuery = true)
    Page<Asset> findByNameContaining(String name, Pageable pageable);

    @Query(value = "SELECT a FROM Asset a " +
            "WHERE (( LOWER(unaccent(a.name)) like LOWER(concat('%', unaccent(?1),'%'))) " +
            "OR ( LOWER (unaccent(a.serialNumber)) like LOWER(concat('%', unaccent(?1),'%'))) " +
            "OR ( LOWER (unaccent(a.imei)) like LOWER(concat('%', unaccent(?1),'%'))) " +
            "OR ( LOWER (unaccent(a.comments)) like LOWER(concat('%', unaccent(?1),'%'))) " +
            ")")
    Page<Asset> findByCertainFields(String search, Pageable pageable);

//    Page<AssetDto> findByKeywordContaining(String keyword, Pageable pageable);

    //    @Query(value="select b FROM Book b where b.name like %?1%")
//    List<Book> findByNameContaining(String name);
    @Query(value = "SELECT * FROM assets a WHERE a.id = ?1", nativeQuery = true)
    List<Asset> getListAssetId(Long id);

    @Query(value = "SELECT * FROM assets a LEFT JOIN request_asset ra ON a.id = ra.asset_id WHERE ra.request_id = :id  ", nativeQuery = true)
    List<Asset> getAssetsByRequestId(Long id);

    @Query(value = "SELECT * FROM assets a INNER JOIN request_asset ra ON a.id = ra.asset_id WHERE ra.request_id = ?1 ", nativeQuery = true)
    List<Asset> findAllByUserId(Long id);

    @Query(value = "SELECT * FROM assets a WHERE a.id IN :ids", nativeQuery = true)
    List<Asset> findByIds(List<Long> ids);

    @Query(value = """
            select * from assets as a 
            inner join projects as p on a.project_id = p.id
            inner join asset_categories as ac on a.category_id = ac.id
            inner join asset_suppliers as asp on a.supplier_id = asp.id
            inner join asset_status as ast on a.status_id = ast.id
            inner join users as u on a.user_id = u.id
            where ((lower(a.name) like lower(concat('%',?1,'%'))) or (lower(a.comments) like lower(concat('%',?1,'%'))) or (lower(a.ownership) like lower(concat('%',?1,'%'))))
            and (lower(p.name) like lower(concat('%',?2,'%')))
            and (lower(ac.name) like lower(concat('%',?3,'%')))
            and (lower(asp.name) like lower(concat('%',?4,'%')))
            and (lower(ast.name) like lower(concat('%',?5,'%')))
            and (lower(concat(u.first_name,' ',u.last_name)) like lower(concat('%',?6,'%')))
            and (lower(a.inventory_number) like lower(concat('%',?7,'%')))
            and (lower(a.serial_number) like lower(concat('%',?8,'%')))
            and (lower(a.imei) like lower(concat('%',?9,'%')))
            """, nativeQuery = true)
    Page<Asset> filterAssetsByCertainFields(String keyword, String projectName, String categoryName, String supplierName, String status,
                                      String currentOwner, String inventoryNumber, String serialNumber, String imei, Pageable pageable);
}
