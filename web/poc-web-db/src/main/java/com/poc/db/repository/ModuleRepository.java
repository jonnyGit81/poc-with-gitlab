package com.poc.db.repository;

import com.poc.db.model.Module;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends CrudRepository<Module, Long> {

    @Query("Select module from Module module join fetch module.parent parent where parent.id = :id")
    Optional<List<Module>> findByParentId(@Param(("id")) Long id);
}
