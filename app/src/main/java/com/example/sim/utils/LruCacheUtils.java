package com.example.sim.utils;

import android.util.LruCache;

public class LruCacheUtils extends LruCache {
    public static LruCacheUtils lruCacheUtils;
    public LruCacheUtils(int maxSize) {
        super(maxSize);
    }
    public static LruCacheUtils getInstance(){
        if (lruCacheUtils == null){
            lruCacheUtils = new LruCacheUtils(1000);
        }
        return lruCacheUtils;
    }

    @Override
    protected int sizeOf(Object key, Object value) {
        return super.sizeOf(key, value);
    }

}
