package com.poc.db.repository;

import com.poc.db.model.Page;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends CrudRepository<Page, Long> , Serializable {
    @Cacheable(value = "userGrantedPagesCache2", key = "#id", unless="#result == null")
    @Query(value = "SELECT DISTINCT pages FROM " +
            "AdminUser user  " +
            "INNER JOIN user.userAccessProfiles accessProfiles " +
            "INNER JOIN accessProfiles.pages pages where user.id = :id ")
    Optional<List<Page>> findUserAuthorizedPagesByUserId(@Param("id") Long id);

    @Cacheable(value = "userGrantedPagesCache", key = "#id", unless="#result == null")
    @Query(value = "SELECT DISTINCT pages FROM " +
            "AdminUser user  " +
            "JOIN user.userAccessProfiles accessProfiles " +
            "JOIN accessProfiles.pages pages " +
            "LEFT JOIN FETCH pages.module " +
            "LEFT JOIN FETCH pages.rootModule " +
            "WHERE user.id = :id ")
    Optional<List<Page>> findUserAuthorizedPagesByUserIdFetchAll(@Param("id") Long id);

    @Cacheable(value = "userGrantedPagesCache", key = "9223372036854775807L" )
    @Query(value = "SELECT page FROM " +
            "Page page  " +
            "LEFT JOIN FETCH page.module " +
            "LEFT JOIN FETCH page.rootModule " )
    Optional<List<Page>> findAllPagesFetchAll();
}
