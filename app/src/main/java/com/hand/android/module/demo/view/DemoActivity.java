package com.hand.android.module.demo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hand.android.R;
import com.hand.android.base.BaseActivity;
import com.hand.android.module.demo.DemoContract;
import com.hand.android.module.demo.presenter.DemoPresenter;
import com.hand.android.nativelib.NativeStringlib;


public class DemoActivity extends BaseActivity implements View.OnClickListener,DemoContract.View {


    private TextView mNative;
    private TextView mContent;
    private Button mBtn1;

    private DemoContract.Presenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mPresenter = new DemoPresenter(this);
        mNative = (TextView) findViewById(R.id.native_word);
        mContent = (TextView) findViewById(R.id.content);
        mBtn1 = (Button) findViewById(R.id.btn_get_user);
        mBtn1.setOnClickListener(this);
        findViewById(R.id.btn_get_repos).setOnClickListener(this);

        mNative.setText(NativeStringlib.stringFromJNI());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_user:
                mPresenter.getUser("hd1236");
                break;
            case R.id.btn_get_repos:
//                mPresenter.getUser("hd1236");
//                mPresenter.getUser("hd1236");
                mPresenter.getRepos("hd1236");
        }
    }

    @Override
    public void showResult(String msg) {
        mContent.setText(msg);
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }
}
