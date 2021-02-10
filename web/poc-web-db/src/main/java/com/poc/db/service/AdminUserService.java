package com.poc.db.service;

import com.poc.db.model.AdminUser;
import com.poc.db.repository.AdminUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserService {
    private AdminUserRepository repository;

    @Autowired
    public AdminUserService(AdminUserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Page<AdminUser> findAdminUserByEmailAndNameContaining(String email, String name, Pageable pageable) {
        Specification<AdminUser> specs = (Specification<AdminUser>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if(StringUtils.isNotBlank(email)) {
                predicates.add ( cb.equal (
                        root.get(AdminUser.FIELD_EMAIL), email)
                );
            }
            if(StringUtils.isNotBlank(name)) {
                predicates.add ( cb.equal (
                        root.get(AdminUser.FIELD_NAME), name.toLowerCase())
                );
            }
            return cb.and(predicates.stream().toArray(Predicate[]::new));
        };

        return repository.findAll(specs, pageable);
    }
}
