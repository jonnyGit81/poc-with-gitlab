package com.poc.security.service;

import com.poc.db.model.AdminUser;
import com.poc.db.model.Page;
import com.poc.db.repository.AdminUserRepository;
import com.poc.db.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private PageRepository pageRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepository.findByEmail(s).orElse(null);
        List<Page> authPages = null;
        if(null != adminUser) {
            if(AdminUser.AccessLevel.SYSTEM.name().equals(adminUser.getAccessLevel())) {
                authPages = pageRepository.findAllPagesFetchAll().orElse(null);
            }else {
                authPages = pageRepository.findUserAuthorizedPagesByUserIdFetchAll(adminUser.getId()).orElse(null);
            }
        }
        return new UserDetailsImpl(adminUser, authPages);
    }
}
