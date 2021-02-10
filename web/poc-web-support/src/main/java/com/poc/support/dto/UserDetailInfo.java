package com.poc.support.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
public class UserDetailInfo implements Serializable {
    private static final long serialVersionUID = -3132182547308029650L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String accessLevel;

    UserDetailInfo(Long id, String name, String email, String status, String accessLevel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.accessLevel = accessLevel;
    }
}
