package com.hand.android.module.demo.model;

import com.hand.android.base.BaseModel;
import com.hand.android.common.network.DataCallback;

/**
 * Created by admin on 2016/10/25.
 */

public interface IDemoModel extends BaseModel {

        void getUserInfo(String userName, DataCallback callBack);
        void getUserRepos(String userName,DataCallback callBack);
        void putImage(String url,String fileUrl,DataCallback callback);
        void stopRequest();
}
