package com.poc.web.controller;

import com.poc.db.model.AdminUser;
import com.poc.db.repository.AdminUserRepository;
import com.poc.support.dto.ApiResponse;
import com.poc.support.exception.InvalidInputException;
import com.poc.support.exception.NotFoundException;
import com.poc.support.util.LogUtil;
import com.poc.web.form.EditAdminUserForm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("users")
public class EditAdminUserController {
    private static final Logger LOG = LoggerFactory.getLogger(EditAdminUserController.class);

    @Autowired
    private AdminUserRepository repository;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_EDIT_USER')")
    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUsers(@Valid @RequestBody EditAdminUserForm form,
                                         HttpServletRequest request) {

        LogUtil.INFO.apply(LOG,"Incoming request update user => " + form.toString());

        AdminUser adminUser = repository.findById(form.getId()).orElse(null);
        validate(adminUser, form);

        repository.save(adminUser);
        return ResponseEntity.ok(
                ApiResponse.ok(request)
                        .message(new StringBuilder("User ")
                                        .append(adminUser.getName())
                                        .append(" has been updated").toString()));
    }

    private void validate(AdminUser adminUser, EditAdminUserForm form) {
        if( null == adminUser ) {
            throw new NotFoundException("UserID " + form.getId() + " not found");
        }

        if( StringUtils.isNotBlank(form.getAccessLevel()) ) {
            AdminUser.AccessLevel accessLevel = Arrays.stream(AdminUser.AccessLevel.values())
                    .filter(ac -> ac.name().equals(form.getAccessLevel())).findAny().orElse(null);
            if ( null == accessLevel ) {
                throw new InvalidInputException("Invalid Access Level");
            }
            adminUser.setAccessLevel(accessLevel.name());
        }

        if( StringUtils.isNotBlank(form.getStatus()) ) {
            AdminUser.Status status = Arrays.stream(AdminUser.Status.values())
                    .filter(s -> s.name().equals(form.getStatus())).findAny().orElse(null);
            if(null == status) {
                throw new InvalidInputException("Invalid Status");
            }
            adminUser.setStatus(status);
        }
    }
}
