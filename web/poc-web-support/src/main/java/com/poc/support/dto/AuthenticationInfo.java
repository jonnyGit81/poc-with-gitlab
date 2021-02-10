package com.poc.support.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AuthenticationInfo {
    public static final String BEARER = "Bearer";

    private final String token;
    private final String type;
    private final Long id;
    private final String username;
    private final String email;
    private final List<String> grantedAuthorities;
    private final Object modules;
    private final String accessLevel;
    private final String makerCheckerRole;
    private final List<String> grantedUrls;

    private AuthenticationInfo(Builder builder) {
        this.type = builder.type;
        this.token = builder.token;
        this.id = builder.id;
        this.username = builder.username;
        this.email = builder.email;
        this.grantedAuthorities = builder.grantedAuthorities;
        this.modules = builder.modules;
        this.accessLevel = builder.accessLevel;
        this.makerCheckerRole = builder.makerCheckerRole;
        this.grantedUrls = builder.grantedUrls;
    }

    public static Builder of() {
        return new Builder();
    }

    public static class Builder {
        private String type = BEARER;
        private Long id;
        private String username;
        private String email;
        private List<String> grantedAuthorities;
        private Object modules;
        private String token;
        public String accessLevel;
        public String makerCheckerRole;
        private List<String> grantedUrls;

        public Builder() { }

        public Builder(final String tokenType) {
            this.type = tokenType;
        }

        public Builder token(final String token) {
            this.token = token;
            return this;
        }

        public Builder userId(final Long id) {
            this.id = id;
            return this;
        }

        public Builder userName(final String userName) {
            this.username = userName;
            return this;
        }


        public Builder email(final String email) {
            this.email = email;
            return this;
        }


        public Builder grantedAuthorities(final List<String> grantedAuthorities) {
            this.grantedAuthorities = grantedAuthorities;
            return this;
        }

        public Builder grantedModules(final Object grantedModules) {
            this.modules = grantedModules;
            return this;
        }


        public Builder accessLevel(final String accessLevel) {
            this.accessLevel = accessLevel;
            return this;
        }

        public Builder makerCheckerRole(final String makerCheckerRole) {
            this.makerCheckerRole = makerCheckerRole;
            return this;
        }

        public Builder grantedUrls(final List<String> grantedUrls) {
            this.grantedUrls = grantedUrls;
            return this;
        }

        public AuthenticationInfo build() {
            return new AuthenticationInfo(this);
        }
    }
}
