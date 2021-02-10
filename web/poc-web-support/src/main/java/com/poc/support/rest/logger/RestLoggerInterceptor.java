package com.poc.support.rest.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.support.util.LogUtil;
import com.poc.support.util.RestLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RestLoggerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(RestLoggerInterceptor.class);

    private ObjectMapper objectMapper;
    public RestLoggerInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map<String, Object> requestLogInfo = RestLogger.ofRequest(request);
        try{
            LogUtil.INFO.apply(LOG, new StringBuilder("\n INCOMING REQUEST \n ")
                    .append(objectMapper.writeValueAsString(requestLogInfo))
                    .toString());
        } catch (Exception je) {
            LogUtil.WARN.apply(LOG, "error_inteceptor_preHandle", je);
        }
        request.setAttribute( RestLogger.START_TIME, requestLogInfo.get(RestLogger.START_TIME));
        request.setAttribute(RestLogger.TRACE_ID, requestLogInfo.get(RestLogger.TRACE_ID));

        RestLogger.log(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Map<String, Object> responseLogInfo = RestLogger.ofResponse(request, response);
        try{
            LogUtil.INFO.apply(LOG,
                    new StringBuilder("\n REQUEST FINISH \n ")
                            .append(objectMapper.writeValueAsString(responseLogInfo))
                            .toString());
        } catch (Exception je) {
            LogUtil.WARN.apply(LOG, "error_inteceptor_afterCompleteion", je);
        }
    }
}
