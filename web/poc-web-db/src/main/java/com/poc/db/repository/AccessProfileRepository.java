package com.poc.db.repository;

import com.poc.db.model.AccessProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessProfileRepository extends CrudRepository<AccessProfile, Long> {

}
