package com.poc.cache;

import com.poc.support.dto.MsgInfo;
import com.poc.support.util.LogUtil;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheLogger implements CacheEventListener<Object, Object> {
    private final Logger LOG = LoggerFactory.getLogger(CacheLogger.class);

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        LogUtil.INFO.apply(LOG,
                MsgInfo.of("Key: {} | EventType: {} | Old value: {} | New value: {}",
                        cacheEvent.getKey(),
                        cacheEvent.getType(),
                        cacheEvent.getOldValue(),
                        cacheEvent.getNewValue()));
    }
}
