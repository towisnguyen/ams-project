package com.r23.ams.repositories;

import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
    @Query(value = "select * from roles r where lower(r.name) LIKE lower(concat('%',?1,'%'))", nativeQuery = true)
    Page<Role> findByNameContaining(String name, Pageable pageable);
    @Query(value = "select * from roles", nativeQuery = true)
    Page<Role> getAllRolesPage(Pageable pageable);
}
