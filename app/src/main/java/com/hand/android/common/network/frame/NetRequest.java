package com.hand.android.common.network.frame;

import java.util.Map;

public interface NetRequest {

    void init(String baseUrl);

    void requestData(String var1, String var2, Object var3, Callback var4);

    void requestData(String var1, String var2, Object var3, NetworkOption var4, Callback var5);

    NetworkResponse synRequestData(String var1, String var2, Object var3);

    NetworkResponse synRequestData(String var1, String var2, Object var3, NetworkOption var4);

    Map<String, NetworkResponse> synRequestData(String var1, Map<String, String> var2);

    void cancel(String var1);

    void cancelAll();
}
