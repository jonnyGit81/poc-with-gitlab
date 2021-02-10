package com.poc.db.model;

import java.time.LocalDateTime;
import java.util.Set;

public class AdminUserBuilder {
    private String name;
    private String email;
    private LocalDateTime lastLoginDateTime;
    private LocalDateTime loginDateTime;
    private AdminUser.Status status;
    private String phone;
    private boolean deleted = false;
    private LocalDateTime deletedTime;
    private String password;
    private Set<AccessProfile> userAccessProfiles;
    private String accessLevel;
    private int loginFailCount = 0;
    private AdminUser.MakerCheckerRole makerCheckerRole;

    public AdminUserBuilder() {

    }
    public AdminUserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AdminUserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public AdminUserBuilder withEmail(String email) {
        this.email  = email;
        return this;
    }

    public AdminUserBuilder withLastLoginDateTime(LocalDateTime lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
        return this;
    }

    public AdminUserBuilder withLoginDateTime(LocalDateTime loginDateTime) {
        this.loginDateTime = loginDateTime;
        return this;
    }

    public AdminUserBuilder withStatus(AdminUser.Status status) {
        this.status = status;
        return this;
    }

    public AdminUserBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public AdminUserBuilder withDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public AdminUserBuilder withDeletedDateTime(LocalDateTime deletedDateTime) {
        this.deletedTime = deletedDateTime ;
        return this;
    }

    public AdminUserBuilder withAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel ;
        return this;
    }

    public AdminUserBuilder withAccessProfiles(Set<AccessProfile> accessProfiles) {
        this.userAccessProfiles = accessProfiles ;
        return this;
    }

    public AdminUserBuilder withMakerCheckerRole(AdminUser.MakerCheckerRole makerCheckerRole) {
        this.makerCheckerRole = makerCheckerRole;
        return this;
    }


    public AdminUserBuilder withLoginFailCount(int counter) {
        this.loginFailCount = counter;
        return this;
    }

    public AdminUser build () {
        AdminUser adminUser = new AdminUser();
        adminUser.setName(this.name);
        adminUser.setEmail(this.email);
        adminUser.setDeleted(this.deleted);
        adminUser.setDeletedTime(this.deletedTime);
        adminUser.setLastLoginDateTime(this.lastLoginDateTime);
        adminUser.setPassword(this.password);
        adminUser.setPhone(this.phone);
        adminUser.setStatus(this.status);
        adminUser.setUserAccessProfiles(userAccessProfiles);
        adminUser.setAccessLevel(this.accessLevel);
        adminUser.setLoginFailCount(this.loginFailCount);
        adminUser.setMakerCheckerRole(this.makerCheckerRole);
        return adminUser;
    }
}
