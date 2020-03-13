package com.hand.android.module.demo.presenter;

import android.util.Log;

import com.hand.android.bean.DemoResponseBean;
import com.hand.android.common.network.DataCallback;
import com.hand.android.module.demo.DemoContract;
import com.hand.android.module.demo.model.DemoModel;
import com.hand.android.module.demo.model.IDemoModel;
import com.google.gson.Gson;

import java.lang.reflect.Type;


/**
 * Created by admin on 2016/10/27.
 */
public class DemoPresenter implements DemoContract.Presenter {

    public static final String REPOS_TAG = "REPOS_TAG";
    public static final String USER_TAG = "USER_TAG";
    public static final String IMAGE_TAG = "IMAGE_TAG";
    public static final String DATA_TAG = "DATA_TAG";


    private DemoContract.View mView;

    private IDemoModel mModel;


    public DemoPresenter(DemoContract.View view){
        mView = view;
        mModel = new DemoModel();
    }

    @Override
    public void getUser(String userName) {
        mModel.getUserInfo(userName,mDataCallback);
    }

    @Override
    public void getRepos(String userName) {
        mModel.getUserRepos(userName,mDataCallback);
    }



    /**
     * 数据返回接口
     */
    private DataCallback mDataCallback = new DataCallback() {
        @Override
        public void onStart(String requestTag) {
        }

        @Override
        public void onSuccess(Object data, String requestTag) {
            mView.showResult(data.toString());
            if(REPOS_TAG.equals(requestTag)){
                Log.e("yzmhand","REPOS_TAG:"+requestTag+"--"+data.toString());
                Gson gson = new Gson();
                DemoResponseBean bean = gson.fromJson(data.toString(), DemoResponseBean.class);
                Log.e("yzmhand","ImageUrl:"+bean.getData().getPutUrl());
                mModel.putImage(bean.getData().getPutUrl(),"sdcard/zl.png",mDataCallback);
            }else if(USER_TAG.equals(requestTag)){
                Log.e("yzmhand","USER_TAG:"+requestTag+"--"+data.toString());
            }else if(IMAGE_TAG.equals(requestTag)){
                Log.e("yzmhand","IMAGE_TAG:"+requestTag+"--"+data.toString());
            }else{
                Log.e("yzmhand","DATA_TAG:"+requestTag+"--"+data.toString());
            }
        }

        @Override
        public void onError(Throwable e, String requestTag) {
            Log.e("yzmhand","onError:"+requestTag+"--"+ Log.getStackTraceString(e));
            mView.showResult(e.getMessage());
        }

        @Override
        public void onComplete(String requestTag) {
        }
    };

    @Override
    public void onStop() {
        mModel.stopRequest();//解除请求回调关联，防止View销毁后导致空指针或者持有View引用导致泄漏
    }
}
