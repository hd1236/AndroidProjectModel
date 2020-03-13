package com.hand.android.constants;


import com.hand.android.utils.MD5Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yuer on 2016/10/27.
 */
public class RequestConstant {

    /**
     * 数据请求时需要提供的request_id  time  sign
     * @param request_id_time
     * @return
     */
    static final String serverName ="hair.cloud.net";
    public static String getRequestId(long request_id_time) {
        Date date = new Date(request_id_time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String formatTime = dateFormat.format(date);
        String numcode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        String request_id = formatTime.substring(2) + numcode;
        return request_id;
    }
    public static String getExUrl(long request_id_time, String request_id) {
        StringBuilder buf = new StringBuilder();
        long time = request_id_time / 1000;
        buf.append("request_id=").append(request_id).append("&time=").append(time);
        String url = buf.toString();
        return url;

    }
    public static String getSign(long request_id_time, String request_id){
        String A = getExUrl(request_id_time,request_id);
        String B = MD5Utils.getMD5(serverName);
        String sign = MD5Utils.getMD5(B+A);
        return sign;
    }
}
