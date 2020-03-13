package com.hand.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2016/8/30.
 *
 * SharedPreferences工具类
 *
 * 注意：在application中初始化后使用
 */
public class SPutils {


    private static SharedPreferences mSp;
    public static void initSharedPreferences(Context context, String name) {
        if(mSp == null) {
            mSp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
    }
    public static String getString(String key) {
        if(mSp != null) {
            return mSp.getString(key, "");
        }else{
            throw new ExceptionInInitializerError("SPutils not inited, please invoke initSharedPreferences in Application first");
        }
    }
    public static long getLong(String key) {
        if(mSp != null) {
            return mSp.getLong(key, 0);
        }else{
            throw new ExceptionInInitializerError("SPutils not inited, please invoke initSharedPreferences in Application first");
        }
    }


    public static Integer getInt(String key) {
        if(mSp != null) {
            return mSp.getInt(key, 0);
        }else{
            throw new ExceptionInInitializerError("SPutils not inited, please invoke initSharedPreferences in Application first");
        }
    }

    public static boolean getBoolean(Context context, String isSetupCompleted) {
        if(mSp != null) {
            return mSp.getBoolean(isSetupCompleted, false);
        }else{
            throw new ExceptionInInitializerError("SPutils not inited, please invoke initSharedPreferences in Application first");
        }

    }
    public static void put(String key, Object value) {
        if(mSp != null) {
            SharedPreferences.Editor edit = mSp.edit();
            if (value instanceof String) {
                edit.putString(key, (String) value);
            } else if (value instanceof Integer) {
                edit.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                edit.putBoolean(key, (Boolean) value);
            }else if(value instanceof Long){
                edit.putLong(key, (Long) value);
            }
            edit.commit();
        }else{
            throw new ExceptionInInitializerError("SPutils not inited, please invoke initSharedPreferences in Application first");
        }

    }
}
