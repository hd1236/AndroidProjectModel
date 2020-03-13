package com.hand.android.module.demo;


import com.hand.android.base.BasePresenter;
import com.hand.android.base.BaseView;

/**
 * Created by admin on 2016/8/29.
 * MVP接口
 */
public interface DemoContract {

    interface View extends BaseView {
        void showResult(String msg);
    }

    interface Presenter extends BasePresenter {
        void getUser(String userName);
        void getRepos(String userName);
    }

}
