package com.r23.ams.repositories;

import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    List<AppUser> findByEmail(String Email);
//    @Query(value = "select u.*, r.name from users u left join roles r on r.id = u.role_id " +
//            "where lower(u.email) LIKE lower(concat('%',?1,'%'))", nativeQuery = true)
//    Page<AppUser> findByEmailContaining(String email, Pageable pageable);
    Optional<AppUser> findByPhone(String phone);
    @Query(value = "select u.*, r.name from users u left join roles r on r.id = u.role_id", nativeQuery = true)
    Page<AppUser> getAllUsersPage(Pageable pageable);

    @Query(value = "SELECT DISTINCT u.status FROM users u", nativeQuery = true)
    List<String> getAllStatus();
    @Query(value = "select * from users u left join roles r on r.id = u.role_id" +
            " where lower(u.email) LIKE lower(concat('%',?1,'%'))", nativeQuery = true)
    Page<AppUser> searchAppUserByEmail(String email, Pageable pageable);
    @Query(value = "select * from users u left join roles r on r.id = u.role_id" +
            " where lower(u.first_name) LIKE lower(concat('%',?1,'%')) OR " +
            " lower(u.last_name) LIKE lower(concat('%',?1,'%')) OR " +
            " lower(u.full_name) LIKE lower(concat('%',?1,'%')) OR " +
            " lower(u.username) LIKE lower(concat('%',?1,'%'))", nativeQuery = true)
    Page<AppUser> searchAppUserByName(String name, Pageable pageable);

    @Query(value = "select * from users u left join roles r on r.id = u.role_id" +
            " where lower(r.name) LIKE lower(concat('%',?1,'%'))", nativeQuery = true)
    Page<AppUser> filterAppUserByRole(String role,Pageable pageable);

    @Query(value = "select * from users u left join roles r on r.id = u.role_id" +
            " where lower(u.status) LIKE lower(concat('%',?1,'%'))", nativeQuery = true)
    Page<AppUser> filterAppUserByStatus(String status, Pageable pageable);

    @Query(value = "SELECT * FROM users u INNER JOIN roles r ON r.id = u.role_id " +
            "WHERE lower(r.name) = lower(?1) OR lower(r.name) = lower(?2) OR lower(r.name) = lower(?3)", nativeQuery = true)
    Page<AppUser> filterAppUserByMultipleRoles(String roleFirst, String roleSecond, String roleThird, Pageable pageable);

    @Query(value = "select * from users a where a.id = ?1", nativeQuery = true)
    List<AppUser> getListUserId(Long id);

    @Query(value = "SELECT * FROM users u WHERE u.id IN :ids", nativeQuery = true)
    List<AppUser> findByIds(List<Long> ids);

    @Query(value = """
            select * from users as u 
            inner join roles as r on u.role_id = r.id
            where (lower(u.email) like lower(concat('%',?1,'%')) or (lower(concat(u.first_name,' ',u.last_name)) like lower(concat('%',?1,'%'))))
            and (lower(r.name) like lower(concat(?2,'%')))
            and (lower(u.status) like lower(concat(?3,'%')))
            """, nativeQuery = true)
    Page<AppUser> filterUsersByRoleAndStatus(String keyword, String roleName, String status, Pageable pageable);
}
