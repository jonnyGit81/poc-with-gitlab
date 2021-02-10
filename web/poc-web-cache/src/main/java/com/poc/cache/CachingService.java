package com.poc.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CachingService {

    @Autowired
    private CacheManager cacheManager;

    public <K> void evictSingleCacheValue(String cacheName, K cacheKey) {
        cacheManager.getCache(cacheName).evict(cacheKey);
    }

    public void evictAllCacheValues(String cacheName) {
        cacheManager.getCache(cacheName).clear();
    }

    public void evictAllCache() {
        cacheManager.getCacheNames().stream().forEach( c -> cacheManager.getCache(c).clear());
    }

    public <K,V> void putCache(String cacheName, K cacheKey, V value ) {
        cacheManager.getCache(cacheName).put(cacheKey, value);
    }

}
