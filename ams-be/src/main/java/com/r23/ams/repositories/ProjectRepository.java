package com.r23.ams.repositories;

import com.r23.ams.models.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByName(String projectName);

    List<Project> findByNameContaining(String projectName);

    @Query(value = "SELECT pj.*, a.id, a.name FROM projects pj LEFT JOIN assets a ON pj.id = a.project_id", nativeQuery = true)
    Page<Project> findAllProjects(Pageable pageable);

    Optional<Project> findOneByName(String name);

    @Query(value = "SELECT * FROM projects pj " +
            "WHERE lower(pj.name) LIKE lower(concat('%', ?1, '%')) OR lower(pj.description) LIKE lower(concat('%', ?1,'%'))", nativeQuery = true)
    Page<Project> searchByKeyword(String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM projects pj " +
            "WHERE lower(pj.status) LIKE lower(?1)", nativeQuery = true)
    Page<Project> searchByStatus(String status, Pageable pageable);
}
