package com.poc.security.jwt;

import com.poc.support.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * see https://www.baeldung.com/spring-security-basic-authentication
 *
 * this class is for catch UN-Authorized request.
 * By default, the BasicAuthenticationEntryPoint provisioned by Spring Security returns a full page for a 401 Unauthorized response back to the client.
 * This HTML representation of the error renders well in a browser,
 * but it not well suited for other scenarios, such as a REST API where a json representation may be preferred.
 */
public class AuthenticationJwtEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationJwtEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        LogUtil.WARN.apply(LOG,
                new StringBuilder("error_AuthenticationJwtEntryPoint_")
                        .append(e.getMessage()).toString() , e);
        response.sendError(response.getStatus(), "Error: Unauthorized");
    }
}
