package com.poc.support.dto;

public class UserDetailInfoBuilder {
    private Long id;
    private String name;
    private String email;
    private String status;
    private String accessLevel;

    public UserDetailInfoBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserDetailInfoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserDetailInfoBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserDetailInfoBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public UserDetailInfoBuilder setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
        return this;
    }

    public UserDetailInfo build() {
        return new UserDetailInfo(id, name, email, status, accessLevel);
    }
}
