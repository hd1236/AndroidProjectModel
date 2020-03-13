package com.hand.android.module.demo.model;

import com.hand.android.bean.BaseRequestBody;
import com.hand.android.bean.Demo2RequestBean;
import com.hand.android.bean.DemoRequestBean;
import com.hand.android.common.network.DataCallback;
import com.hand.android.common.network.RemoteDataSource;
import com.hand.android.constants.RequestPath;
import com.hand.android.module.demo.presenter.DemoPresenter;

/**
 * Created by admin on 2016/10/25.
 */

public class DemoModel implements IDemoModel {

    private RemoteDataSource mRemoteDataSource;

    public DemoModel() {
        mRemoteDataSource = new RemoteDataSource();
    }

    @Override
    public void getUserInfo(String userName, DataCallback callback) {
        mRemoteDataSource.getRequest(DemoPresenter.USER_TAG, RequestPath.getUserInfoUrl(userName), callback);

    }

    @Override
    public void getUserRepos(String userName, DataCallback callback) {
        //        mRemoteDataSource.getRequest(DemoPresenter.REPOS_TAG, RequestPath.getUserReposUrl(userName), callback);
//        DemoRequestBean params = new DemoRequestBean();
//        params.setSn("001");
//        mRemoteDataSource.postRequest(DemoPresenter.REPOS_TAG,RequestPath.temperatureUrl,params,callback);

        Demo2RequestBean bean = new Demo2RequestBean();
        bean.setFace_image("https://cstorage.oss-cn-shenzhen.aliyuncs.com/temperature%2Fdevelopment%2F5%2Fpreview.png?Expires=1583927298&OSSAccessKeyId=LTAIxIARX95tLOLH&Signature=AQDQp0WXyWe%2FsNCGY4br9e8xx0s%3D");
        bean.setNum(37.5f);
        bean.setSn("001");
        mRemoteDataSource.postRequest(DemoPresenter.DATA_TAG,RequestPath.temperatureDataUrl,bean,callback);
    }

    @Override
    public void putImage(String url,String fileUrl,DataCallback callback) {
//        mRemoteDataSource.putImage(DemoPresenter.IMAGE_TAG,url,fileUrl,callback);
    }

    @Override
    public void stopRequest() {
        mRemoteDataSource.stopAllRequest();
    }
}
