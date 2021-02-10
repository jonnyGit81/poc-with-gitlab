package com.poc.web.controller;

import com.poc.db.model.AdminUser;
import com.poc.support.dto.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class AdminUserCommonController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/commons/user-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdminUserStatusList(HttpServletRequest request) {
        Map<String,String> status = Arrays.stream(AdminUser.Status.values())
                .collect(Collectors.toMap(AdminUser.Status::name, AdminUser.Status::name));
        return ResponseEntity.ok(
                ApiResponse.ok(request).data(status).build()
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/commons/access-level", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdminUserAccessLevel(HttpServletRequest request) {
        Map<String,String> acessLevel = Arrays.stream(AdminUser.AccessLevel.values())
                .collect(Collectors.toMap(AdminUser.AccessLevel::name, AdminUser.AccessLevel::name));
        return ResponseEntity.ok(
                ApiResponse.ok(request).data(acessLevel).build()
        );
    }
}
