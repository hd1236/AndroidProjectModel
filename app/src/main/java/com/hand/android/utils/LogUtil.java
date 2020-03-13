package com.hand.android.utils;

import android.util.Log;

import static com.hand.android.utils.LogUtil.Flags.d;
import static com.hand.android.utils.LogUtil.Flags.e;
import static com.hand.android.utils.LogUtil.Flags.i;
import static com.hand.android.utils.LogUtil.Flags.v;
import static com.hand.android.utils.LogUtil.Flags.w;


/**
 * Created by admin on 2016/11/26.
 */

public class LogUtil {

    private static boolean DEBUG = true;

    enum Flags {
        e,
        i,
        d,
        v,
        w
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            printLog(e,tag,msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            printLog(i,tag,msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            printLog(d,tag,msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            printLog(v,tag,msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            printLog(w,tag,msg);
        }
    }

    private static void printLog(Flags flags, String tag, String msg) {
        if (msg.length() > 4000) {
            log(flags, tag, "log.start : " + msg.length());
            int chunkCount = msg.length() / 4000;
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= msg.length()) {
                    log(flags, tag, "block " + i + "/" + chunkCount + ": " + msg.substring(4000 * i));
                } else {
                    log(flags, tag, "block " + i + "/" + chunkCount + ": " + msg.substring(4000 * i, max));
                }
            }
            log(flags, tag, "log.end");
        } else {
            log(flags,tag, msg);
        }
    }

    private static void log(Flags flags, String tag, String msg) {
        switch (flags) {
            case e:
                Log.e(tag, msg);
                break;
            case i:
                Log.i(tag, msg);
                break;
            case d:
                Log.d(tag, msg);
                break;
            case v:
                Log.v(tag, msg);
                break;
            case w:
                Log.w(tag, msg);
                break;
        }
    }
}
