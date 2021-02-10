package com.poc.db.repository;

import com.poc.db.model.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminUserRepository extends PagingAndSortingRepository<AdminUser, Long>, JpaSpecificationExecutor<AdminUser> {
    Optional<AdminUser> findByEmail(String email);

    Boolean existsByEmail(String email);


    List<AdminUser> findAll(Specification<AdminUser> querySpec);

    Page<AdminUser> findAll(Specification<AdminUser> querySpec, Pageable pageable);
}
