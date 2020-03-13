package com.hand.android.common.network.frame;

import java.util.UUID;

/**
 * Created by liaoyj on 2019/1/23 0023 10:23
 */
public class TagUtils {
    public static String generateTag(String tag) {
        return tag + "&&&" + UUID.randomUUID().toString();
    }

    public static String originalTag(String tag) {
        if (tag.contains("&&&")) {
            return tag.substring(0, tag.indexOf("&&&"));
        }
        return tag;
    }
}
