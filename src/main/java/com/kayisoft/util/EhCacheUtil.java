package com.kayisoft.util;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Cache工具类
 *
 * @author crp
 */
@SuppressWarnings("all")
public class EhCacheUtil {

    private static final String path = "/ehcache/ehcache.xml";

    private URL url;

    private CacheManager manager;

    private static EhCacheUtil ehCache;

    private EhCacheUtil(String path) {
        url = getClass().getResource(path);
        manager = CacheManager.create(url);
    }

    public static EhCacheUtil getInstance() {
        if (ehCache == null) {
            ehCache = new EhCacheUtil(path);
        }
        return ehCache;
    }

    public void put(String cacheName, String key, Object value) {
        Cache cache = manager.getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }

    public Object get(String cacheName, String key) {
        Cache cache = manager.getCache(cacheName);
        Element element = cache.get(key);
        return element == null ? null : element.getObjectValue();
    }

    public Cache get(String cacheName) {
        return manager.getCache(cacheName);
    }

    public void remove(String cacheName, String key) {
        Cache cache = manager.getCache(cacheName);
        cache.remove(key);
    }

    public List<Object> getAll(String cacheName){
        Cache cache = manager.getCache(cacheName);
        List keys = cache.getKeys();
        List<Object> objects = new ArrayList<>();
        for(Object key: keys){
            Object objectValue = cache.get(key).getObjectValue();
            objects.add(objectValue);
        }
        return objects;
    }
    public void update(String cacheName,String key ,Object value){
        Cache cache = manager.getCache(cacheName);
        cache.replace(new Element(key,value));
    }

}
