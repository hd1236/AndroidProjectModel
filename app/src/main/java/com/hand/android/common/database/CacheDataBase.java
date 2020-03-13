package com.hand.android.common.database;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.hand.android.utils.DiskLruCache;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yuer on 2016/10/27.
 */
public class CacheDataBase {
    //    private static final String DATA_DATA_BASE = "himecustomer.db";   //缓存名称
    public static CacheDataBase mCacheDataBase = null;
    private static Context mContext;
    private static DiskLruCache mDiskLruCache;
    private static String flag = "isFristOpenAppFlag";
    private static File cacheDir;
    static Gson gson = new Gson();

    private CacheDataBase() {
    }

    public static CacheDataBase getInstance(Context context) {
       if(mCacheDataBase == null && cacheDir == null) {
            mCacheDataBase = new CacheDataBase();
            mContext = context;
            try {
                createFile();
                mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(mContext), 1, 10 * 1024 * 1024);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mCacheDataBase;
    }

    private static void createFile() throws IOException {
        cacheDir = getDiskCacheDir(mContext, "/himecustomer/cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }
    public static String readCache(String CacheUrl) {
        String json = "";
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(hashKeyForDisk(CacheUrl));
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = -1;
                while ((length = inputStream.read(buffer)) != -1) {
                    arrayOutputStream.write(buffer, 0, length);
                }
                json = arrayOutputStream.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    //将数据缓存起来
    public static void saveCache(String json, String keyForDisk) {
        try {
            DiskLruCache.Editor edit = mDiskLruCache.edit(hashKeyForDisk(keyForDisk));
            OutputStream newOutputStream = edit.newOutputStream(0);
            newOutputStream.write(json.getBytes());
            newOutputStream.close();
            newOutputStream.flush();
            edit.commit();
            mDiskLruCache.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取md5加密的路径
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    //MD5加密
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //获取缓存文件的根路劲
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = /*Environment.getExternalStorageDirectory() +*/ "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    //获取版本号
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
