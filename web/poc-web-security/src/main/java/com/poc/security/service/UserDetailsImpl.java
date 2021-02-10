package com.poc.security.service;

import com.google.common.base.CaseFormat;
import com.poc.db.dto.ModuleDto;
import com.poc.db.dto.PageDto;
import com.poc.db.model.AdminUser;
import com.poc.db.model.Page;
import com.poc.support.dto.MsgInfo;
import com.poc.support.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 5414346732768124539L;
    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsImpl.class);

    private final AdminUser adminUser;
    private Collection<GrantedAuthority> authorities;
    private Map<Long, ModuleDto> modules;
    private List<ModuleDto> grantedModules;
    private List<String> grantedUrls;

    public UserDetailsImpl(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    private ModuleDto createParentFromPage(Page page) {
        ModuleDto parent = ModuleDto.toDto(page.getRootModule());
        modules.put(parent.getId(), parent);
        parent.setSubModules(new ArrayList<>());
        return parent;
    }

    /**
     * create a map containing by format
     * {@code <K,V> <ParentModuleId,ParentModue> which
     *  ParentModule containing list of submodules and each list of submodules containing pages }
     * @param page
     */
    private void populateGrantedModules(Page page) {
        try {
            if (null != page.getModule() && null != page.getRootModule()) {
                ModuleDto parent;
                ModuleDto child;

                boolean isNewParentModule = modules.isEmpty() || ( ! modules.containsKey(page.getRootModule().getId()));
                if ( isNewParentModule ) {
                    parent = createParentFromPage(page);
                } else {
                    parent = modules.get(page.getRootModule().getId());
                }

                child = ModuleDto.toDto(page.getModule());
                if (!modules.get(parent.getId()).getSubModules().contains(child)) {
                    child.setPages(new ArrayList<>());
                    parent.getSubModules().add(child);
                } else {
                    Optional<ModuleDto> findChild = parent.getSubModules().stream()
                            .filter(c -> (c.getName().equals(page.getModule().getName()) &&
                                    c.getId().equals(page.getModule().getId())))
                            .findFirst();
                    child = findChild.orElse(child);
                    if (CollectionUtils.isEmpty(child.getPages())) {
                        child.setPages(new ArrayList<>());
                    }
                }

                PageDto pageDto = PageDto.toDto(page);
                child.getPages().add(pageDto);

                this.grantedUrls.add(
                        new StringBuilder()
                                .append(parent.getUrl())
                                //.append(child.getUrl())
                                .append(page.getUrl())
                                .toString()
                );
            }
        }catch (Exception e) {
            LogUtil.WARN.apply(LOG, "error_populating_module " + e.getMessage(), e);
        }
    }

    public UserDetailsImpl(AdminUser adminUser, List<Page> authPages) {
        this.adminUser = adminUser;
        this.authorities = new HashSet<>();
        this.modules = new HashMap<>();
        this.grantedUrls = new ArrayList<>();

        if(! CollectionUtils.isEmpty(authPages))  {
            authPages.stream().forEach(page -> {
                this.authorities.add(
                        // addAccessProfile = ROLE_ADD_ACCESS_PROFILE
                        new SimpleGrantedAuthority(
                                "ROLE_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, page.getName())
                        ));

                populateGrantedModules(page);
            });

            if(! CollectionUtils.isEmpty(modules)) {
                // sort the map by display order of parent module then sort the module by using display order then sort the pages in the module
                grantedModules = modules.entrySet()
                        .stream()
                        .map(entry -> entry.getValue())
                        .collect(Collectors.toList());
                grantedModules = grantedModules
                        .stream()
                        .sorted(Comparator.comparingInt(ModuleDto::getDisplayOrder))
                        .collect(Collectors.toList());
                grantedModules.stream()
                        .forEach(m -> m.getSubModules()
                                .stream()
                                .sorted(Comparator.comparingInt(ModuleDto::getDisplayOrder))
                                .forEach(p ->
                                        p.getPages()
                                                .stream()
                                                .sorted(Comparator.comparingInt(PageDto::getDisplayOrder))));
            }
        }

        if(adminUser != null && adminUser.isAdmin()) {
            LogUtil.INFO.apply(LOG,
                    MsgInfo.of("user ROLE_ADMIN {} ",adminUser.isAdmin()));
            this.authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        if(null != this.adminUser) {
            return adminUser.getPassword();
        }
        return null;
    }

    @Override
    public String getUsername() {
        // using email as the unique userName
        if(null != this.adminUser) {
            return adminUser.getEmail();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        // todo if want to implement account expiration
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if ( null == adminUser ) {
            return false;
        } else if (adminUser.isAdmin()) {
            return true;
        } else if ( adminUser.getLoginFailCount() > 3 ) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // todo if want to implement password have expiration
        return true;
    }

    @Override
    public boolean isEnabled() {
        if ( null == adminUser ) {
            return false;
        } else if ( adminUser.isAdmin() ) {
            return true;
        } else if ( adminUser.getLoginFailCount() > 3 || adminUser.isInactive() ) {
            return false;
        } else {
            return true;
        }
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public List<ModuleDto> getGrantedModules() {
        return grantedModules;
    }

    public List<String> getGrantedUrls() {
        return grantedUrls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)  {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(this.adminUser, ((UserDetailsImpl) o).adminUser);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((adminUser == null) ? 0 : adminUser.hashCode());
        return result;
    }
}
