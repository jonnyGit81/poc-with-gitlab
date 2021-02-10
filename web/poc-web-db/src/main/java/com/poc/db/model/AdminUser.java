package com.poc.db.model;

import com.poc.db.base.BaseModel;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "admin_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        },
        indexes = {
                @Index(columnList = "name", name = "admin_user_name_idx")
        }
)
public class AdminUser extends BaseModel implements Serializable {

    private static final long serialVersionUID = 3306814417792838545L;
    public static final String FIELD_NAME = "name";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_LAST_LOGIN_DATETIME = "lastLoginDateTime";
    public static final String FIELD_LOGIN_DATETIME = "loginDateTime";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_DELETED = "deleted";
    public static final String FIELD_DELETED_TIME = "deletedTime";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_USER_ACCESS_PROFILES = "userAccessProfiles";
    public static final String FIELD_ACCESS_LEVEL = "accessLevel";
    public static final String FIELD_LOGIN_FAILED_COUNT = "loginFailCount";

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public enum AccessLevel {
        SYSTEM,
        USER
    }

    public enum MakerCheckerRole {
        MAKER ("MAKER - Only Can Create"),
        CHECKER ("CHECKER - Only Can Approve"),
        MAKER_CHECKER ("MAKER_CHECKER - Can Create & Approve [Non Same User]"),
        APPROVER ("APPROVER - Can Create & Approve [Same User]");

        MakerCheckerRole(String description) {
            this.description = description;
        }

        private String description;

        public String getDescription() {
            return description;
        }
    }

    public AdminUser(){

    }

    public AdminUser( Long id, String email, String name, String status, String accessLevel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.accessLevel = accessLevel;
    }

    @NotBlank(message = "name is mandatory")
    @Size(max = 30)
    @Column(name = "name", nullable = false, length = 30)
    private String name;


    @NotBlank(message = "email is mandatory")
    @Size(max = 50)
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginDateTime;

    @Column(name = "login_time")
    private LocalDateTime loginDateTime;


    @NotBlank(message = "status is mandatory")
    @Size(max = 10)
    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_time")
    private LocalDateTime deletedTime;


    @NotBlank(message = "password is mandatory")
    @Size(max = 60)
    @Column(name = "password", length = 60)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "admin_user_access_profile",
            joinColumns = @JoinColumn(name = "admin_user_id"),
            inverseJoinColumns = @JoinColumn(name = "access_profile_id"))
    private Set<AccessProfile> userAccessProfiles;

    @Column(name = "access_level", nullable = false, length = 10)
    private String accessLevel;

    @Column(name = "login_fail_count", nullable = false)
    private int loginFailCount = 0;

    @Column(name = "maker_checker_role", nullable = false, length = 15)
    private String makerCheckerRole;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLastLoginDateTime() {
        return lastLoginDateTime;
    }

    public void setLastLoginDateTime(LocalDateTime lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }

    public LocalDateTime getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(LocalDateTime loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status.name();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(LocalDateTime deletedTime) {
        this.deletedTime = deletedTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AccessProfile> getUserAccessProfiles() {
        return userAccessProfiles;
    }

    public void setUserAccessProfiles(Set<AccessProfile> userAccessProfiles) {
        this.userAccessProfiles = userAccessProfiles;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(int loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public String getMakerCheckerRole() {
        return makerCheckerRole;
    }

    public void setMakerCheckerRole(@NonNull MakerCheckerRole makerCheckerRole) {
        this.makerCheckerRole = makerCheckerRole.name();
    }

    @Transient
    public boolean isAdmin() {
        return AccessLevel.SYSTEM.name().equals(this.accessLevel);
    }

    @Transient
    public boolean isInactive() {
        return Status.INACTIVE.name().equals(this.status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(this.id == null) {
            return  false;
        }
        if(! super.equals(o) ) {
            return false;
        }
        if(o instanceof AdminUser && o.getClass().equals(getClass())) {
            return this.email.equals(((AdminUser) o).getEmail());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(this.name);
        return result;
    }
}
