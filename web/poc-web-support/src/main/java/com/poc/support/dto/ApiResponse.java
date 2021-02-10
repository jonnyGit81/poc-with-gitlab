package com.poc.support.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.poc.support.enums.ApiResponseCode;
import com.poc.support.util.RequestPathUtil;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@Getter
public class ApiResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final ApiResponseCode code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("success")
    private final boolean hasSuccess;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final ZonedDateTime timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object response;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final PageInfo pageInfo;

    private ApiResponse(Builder builder) {
        this.code = builder.code;
        this.hasSuccess = builder.hasSuccess;
        this.timestamp = builder.timestamp;
        this.response = builder.response;
        this.status = builder.status;
        this.pageInfo = builder.pageInfo;
        this.path = builder.path;
        this.message = builder.message;
    }

    public static Builder of(final HttpServletRequest request, final HttpStatus httpStatus) {
        Builder builder = new Builder(request, httpStatus);
        if(HttpStatus.OK == httpStatus) {
            builder.respCode(ApiResponseCode.OK);
        }
        return builder;
    }

    public static Builder ok(final HttpServletRequest request) {
        Builder builder = new Builder(request, HttpStatus.OK);
        builder.respCode(ApiResponseCode.OK);
        return builder;
    }

    public static class Builder {
        private ApiResponse apiResponse;
        private ApiResponseCode code;
        private String message;
        private boolean hasSuccess;
        private ZonedDateTime timestamp;
        private String path;
        private Object response;
        private Integer status;
        private String error;
        private PageInfo pageInfo;

        private Builder(final HttpServletRequest request, final HttpStatus httpStatus) {
            this.timestamp = ZonedDateTime.now();
            this.path = RequestPathUtil.toPathInfo(request);
            this.status = httpStatus.value();
        }

        public Builder data(final Object data) {
            this.response = data;
            return this;
        }

        public Builder respCode(final ApiResponseCode code) {
            this.code = code;
            this.hasSuccess = ObjectUtils.nullSafeEquals(ApiResponseCode.OK, code);
            if(StringUtils.isEmpty(this.message)) {
                this.message = code.getResultCode();
            }
            return this;
        }

        public Builder message(final String message) {
            this.message = message;
            return this;
        }

        public Builder pageInfo(final PageInfo pageInfo) {
            this.pageInfo = pageInfo;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(this);
        }
    }
}
