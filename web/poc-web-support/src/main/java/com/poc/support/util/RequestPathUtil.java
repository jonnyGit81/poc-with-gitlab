package com.poc.support.util;

import javax.servlet.http.HttpServletRequest;

public final class RequestPathUtil {
    private RequestPathUtil() {
        throw new AssertionError();
    }

    public static String toPathInfo(HttpServletRequest request) {
        String path = request.getPathInfo();
        if( null == path && null != request) {
            return  null != request.getRequestURI() ?
                    request.getRequestURI().substring(request.getContextPath().length()) : "";
        }
        return null;
    }
}
