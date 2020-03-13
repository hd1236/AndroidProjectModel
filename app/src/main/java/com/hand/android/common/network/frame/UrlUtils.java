package com.hand.android.common.network.frame;

import android.text.TextUtils;

/**
 * Created by Administrator on 2019/7/5.
 */

public class UrlUtils {

    public static String getContentType(String url) {
        if (TextUtils.isEmpty(url)) {
            return "image/jpeg";
        }
        if (url.contains(".webp")) {
            return "image/webp";
        }
        if (url.contains(".png")) {
            return "image/png";
        }
        if (url.contains(".txt")) {
            return "text/plain";
        }
        return "image/jpeg";
    }
}
