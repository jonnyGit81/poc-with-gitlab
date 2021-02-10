package com.poc.support.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RestLogger {
    private static final Logger LOG = LoggerFactory.getLogger(RestLogger.class);

    public static final String TYPE = "TYPE";
    public static final String TRACE_ID = "traceId";
    public static final String METHOD = "method";
    public static final String HEADERS = "headers";
    public static final String PATH = "path";
    public static final String START_TIME = "startTime";
    public static final String FINISH_TIME = "finishTime";
    public static final String TIME_TAKEN = "timeTaken";
    public static final String REQUEST = "Request";
    public static final String RESPONSE = "Response";
    public static final String COOKIES = "cookies";
    public static final String TIME_SATURATION = "ms";

    public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        forEachRemaining(request.getHeaderNames(),
                ( e -> map.put(e, request.getHeader(e))));
        return map;
    }

    private static <T> void forEachRemaining(Enumeration<T> e, Consumer<? super T> c) {
        while(e.hasMoreElements()) c.accept(e.nextElement());
    }

    public static Map<String, String> getResponseHeaders( HttpServletResponse response) {
        return response.getHeaderNames().stream().distinct()
                .collect(Collectors.toMap(h -> h, response::getHeader));
    }

    public static Map<String, Object> getRequestHeaderCookies(HttpServletRequest request) {
        if( null !=request.getCookies() ) {
            return Arrays.stream(request.getCookies())
                    .collect(Collectors.toMap(cookie -> cookie.getName(), cookie -> cookie.getValue()));
        }
        return null;
    }

    public static Map<String, Object> ofRequest(HttpServletRequest request) {
        long startTime = Instant.now().toEpochMilli();
        String traceId = UUID.randomUUID().toString();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(TYPE, REQUEST);
        map.put(METHOD, request.getMethod());
        map.put(HEADERS, getRequestHeaders(request));
        map.put(COOKIES, getRequestHeaderCookies(request));
        map.put(TRACE_ID, traceId);
        map.put(PATH, request.getRequestURL().toString());
        map.put(START_TIME, startTime);
        return map;
    }

    public static Map<String, Object> ofResponse(HttpServletRequest request, HttpServletResponse response) {
        long startTime = (Long) request.getAttribute(START_TIME);
        Long now = Instant.now().toEpochMilli();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(TYPE, RESPONSE);
        map.put(METHOD, request.getMethod());
        map.put(HEADERS, getResponseHeaders(response));
        map.put(TRACE_ID, (String) request.getAttribute(TRACE_ID));
        map.put(PATH, request.getRequestURL().toString());
        map.put(FINISH_TIME, now.toString());
        map.put(TIME_TAKEN, (now - startTime) + TIME_SATURATION);
        return map;
    }

    public static void log(HttpServletRequest request) {
        LogUtil.INFO.apply(LOG, "\n REQUEST HEADER");
        forEachRemaining(request.getHeaderNames(),
                ( e -> LogUtil.INFO.apply(LOG,
                        new StringBuilder()
                                .append("[ ")
                                .append(e).append(" : ")
                                .append(request.getHeader(e))
                                .append(" ]").toString())));


        if( null != request.getCookies() ) {
            Arrays.stream(request.getCookies()).forEach(e ->
                    LogUtil.INFO.apply(LOG,
                            new StringBuilder()
                                    .append("[ ")
                                    .append( e.getName() )
                                    .append( " : " )
                                    .append( e.getValue() )
                                    .append( " ]").toString()));
        }else {
            LogUtil.INFO.apply(LOG,"Request Cookie was null /n");
        }
    }

    public static void log(HttpServletResponse response) {
        LogUtil.INFO.apply(LOG, "\n RESPONSE HEADER");
        response.getHeaderNames().stream().distinct()
                .forEach(e -> LogUtil.INFO.apply( LOG,
                        new StringBuilder()
                                .append("[ ")
                                .append(e)
                                .append( " : " )
                                .append( response.getHeader(e) )
                                .append(" ]").toString()));
    }
}
