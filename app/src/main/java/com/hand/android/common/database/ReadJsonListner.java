package com.hand.android.common.database;

/**
 * Created by yuer on 2016/10/27.
 */
public interface ReadJsonListner {
    void Success(String string);
    void Error(Throwable throwable);
    void Completed();
}
