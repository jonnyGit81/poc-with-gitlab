package com.poc.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.db.dto.SortInfo;
import com.poc.db.model.AdminUser;
import com.poc.db.service.AdminUserService;
import com.poc.db.util.SortDirectionUtil;
import com.poc.support.dto.ApiResponse;
import com.poc.support.dto.PageInfo;
import com.poc.support.dto.UserDetailInfo;
import com.poc.support.dto.UserDetailInfoBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class ListAdminUserController {
    private static final Logger LOG = LoggerFactory.getLogger(ListAdminUserController.class);

    private AdminUserService adminUserService;
    private ObjectMapper objectMapper;

    @Autowired
    public ListAdminUserController(AdminUserService adminUserService, ObjectMapper objectMapper) {
        this.adminUserService = adminUserService;
        this.objectMapper = objectMapper;
    }

    private ApiResponse populateResponse(String email,
                                         String name,
                                         int pageNumber,
                                         int size,
                                         SortInfo sort,
                                         HttpServletRequest request) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        if( null == sort ) {
            // default order by name
            orders.add(new Sort.Order(Sort.Direction.ASC, "name"));
        }else {
            orders.add(new Sort.Order(SortDirectionUtil.getSortDirection(sort.getDirection()), sort.getField()));
        }

        Pageable pagingSort = PageRequest.of(pageNumber, size, Sort.by(orders));

        Page<AdminUser> page = adminUserService
                .findAdminUserByEmailAndNameContaining(email, name, pagingSort);

        List<UserDetailInfo> result = Collections.EMPTY_LIST;
        if(ObjectUtils.isNotEmpty(page)) {
            result = page.stream()
                    .map(u ->
                            new UserDetailInfoBuilder()
                                    .setId(u.getId())
                                    .setEmail(u.getEmail())
                                    .setName(u.getName())
                                    .setAccessLevel(u.getAccessLevel())
                                    .setStatus(u.getStatus()).build())
                    .collect(Collectors.toList());
        }

        return ApiResponse.ok(request)
                .data(result)
                .pageInfo(
                        new PageInfo.Builder()
                                .currentPageNumber(page.getNumber())
                                .numbersOfElements(page.getNumberOfElements())
                                .totalElements(page.getTotalElements())
                                .totalPages(page.getTotalPages()).build()
                ).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_LIST_USER')")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers(@RequestParam(required = false) String email,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(name = "page") int pageNumber,
                                      @RequestParam(name = "size") int size,
                                      @RequestParam(name = "sort", required = false) String sort,
                                      HttpServletRequest request) {

        SortInfo sortInfo = new SortInfo("name", SortDirectionUtil.Direction.ASC.name());
        if(StringUtils.isNotBlank(sort)) {
            try {
                String s[] = sort.split("_");
                if(s.length == 2) {
                    sortInfo = new SortInfo(s[0],s[1]);
                }
            }catch (Exception ex) {
                // ignore error
            }
        }
        return ResponseEntity.ok(populateResponse(email, name, pageNumber, size, sortInfo, request));
    }
}
