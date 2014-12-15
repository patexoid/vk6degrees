package com.patex.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apotekhin on 12/12/2014.
 */
@Component
public class BKCacheManager implements CacheManager, Closeable {
    private final Map<String,BKCache> _cacheMap=new HashMap<>();


    @Override
    public Cache getCache(String name) {
        BKCache cache = _cacheMap.get(name);
        if(cache==null){
            synchronized (_cacheMap){
                cache=_cacheMap.get(name);
                if(cache==null){
                    cache=new BKCache(name);
                    _cacheMap.put(name, cache);
                }
            }
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return _cacheMap.keySet();
    }

    @Override
    public void close() throws IOException {
        for (BKCache cache : _cacheMap.values()) {
            cache.close();
        }
    }
}
