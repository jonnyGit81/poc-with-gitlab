package com.poc.support.controller.advice;

import com.poc.support.rest.logger.RestLoggerService;
import com.poc.support.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

// is logging for post only
@ControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(CustomRequestBodyAdviceAdapter.class);

    @Autowired
    RestLoggerService loggingService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        try {
            loggingService.logRequest(httpServletRequest, body);
        }catch (Exception e) {
            LogUtil.WARN.apply(LOG, "error_CustomRequestBodyAdviceAdapter_afterRead ", e );
        }
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    /*@Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
                                           MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            loggingService.logRequest(httpServletRequest, inputMessage.getBody());
        }catch (Exception e) {
            LogUtil.WARN.apply(LOG, "error_CustomRequestBodyAdviceAdapter_afterRead ", e );
        }

        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }*/
}
