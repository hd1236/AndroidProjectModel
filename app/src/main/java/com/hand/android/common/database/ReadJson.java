package com.hand.android.common.database;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yuer on 2016/10/27.
 */
public class ReadJson {
    public static Context mContext;
    public ReadJsonListner readJsonListner;

    public static void initReadJson(Context context) {
        mContext = context;
    }


    public void setOnListenr(ReadJsonListner readJsonListner) {
        this.readJsonListner = readJsonListner;
    }

    public void readJson() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    BufferedReader bf = new BufferedReader(new InputStreamReader(
                            mContext.getAssets().open("area.json")));
                    String line;
                    while ((line = bf.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    subscriber.onNext(stringBuilder.toString());
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        readJsonListner.Completed();
                    }

                    @Override
                    public void onError(Throwable e) {
                        readJsonListner.Error(e);
                    }

                    @Override
                    public void onNext(String o) {
                        readJsonListner.Success(o.trim());
                    }
                });
    }

}
