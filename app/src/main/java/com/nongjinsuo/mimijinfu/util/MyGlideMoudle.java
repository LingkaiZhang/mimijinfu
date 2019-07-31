package com.nongjinsuo.mimijinfu.util;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * @author czz
 * @time用一句话描述
 * @Description: ()
 */
public class MyGlideMoudle implements GlideModule {
    private int yourSizeInBytes = 0;
    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, yourSizeInBytes));
        builder .setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                // Careful: the external cache directory doesn't enforce permissions
                File cacheLocation = new File(context.getExternalCacheDir(), "cache_dir_name");
                cacheLocation.mkdirs();
                return DiskLruCacheWrapper.get(cacheLocation, yourSizeInBytes);
            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        Glide.get(context).setMemoryCategory(MemoryCategory.HIGH);
    }
}