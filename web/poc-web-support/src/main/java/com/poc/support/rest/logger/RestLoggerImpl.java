package com.poc.support.rest.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.support.util.LogUtil;
import com.poc.support.util.RestLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class RestLoggerImpl implements RestLoggerService {
    private static final Logger LOG = LoggerFactory.getLogger(RestLoggerImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void logRequest(HttpServletRequest request, Object body) {
        try {
            Map<String, Object> map = RestLogger.ofRequest(request);
            LogUtil.INFO.apply(LOG,  objectMapper.writeValueAsString(map));
            RestLogger.log(request);
        } catch ( Exception e ) {
            LogUtil.WARN.apply(LOG, e.getMessage(), e);
        }
    }
    @Override
    public void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
        try {
            Map<String, Object> map = RestLogger.ofResponse(request, response);
            LogUtil.INFO.apply(LOG,  objectMapper.writeValueAsString(map));
            RestLogger.log(response);
        } catch ( Exception e ) {
            LogUtil.WARN.apply(LOG, e.getMessage(), e);
        }
    }
}
