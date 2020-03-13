package com.hand.android.bean;

import java.util.ArrayList;


/**
 * Created by yuer on 2016/10/27.
 */
public class BaseRequestBody<BaseBean> {
    String jsonrpc;
    ArrayList<BaseBean> params;

    public BaseRequestBody(BaseBean baseBean){
        if(baseBean != null) {
            params = new ArrayList<>();
            params.add(baseBean);
        }
    }
    public String getJsonrpc() {
        return jsonrpc = "2.0";
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public ArrayList<BaseBean> getParams() {
        return params;
    }

    public void setParams(ArrayList<BaseBean> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", params=" + params +
                '}';
    }


}

