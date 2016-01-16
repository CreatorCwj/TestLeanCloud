package com.imageLoader;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by cwj on 16/1/14.
 * disk缓存文件夹,允许外部获取缓存文件夹
 */
public class FileCache {

    //文件夹名字
    private static final String CACHE_DIR_NAME = "TestLeanCloudCache";

    public static File getCacheDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String path = Environment.getExternalStorageDirectory() + File.separator + CACHE_DIR_NAME;
            File file = new File(path);
            if (!file.exists()) {
                boolean res = file.mkdirs();
                if (!res)
                    return null;
            }
            Log.i("Cache-Direction", file.toString());
            return file;
        }
        return null;
    }
}
