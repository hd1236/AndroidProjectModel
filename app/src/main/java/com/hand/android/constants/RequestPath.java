package com.hand.android.constants;


public class RequestPath {

    public static String getUserInfoUrl(String name){
        return new StringBuilder().append("users/").append(name).toString();
    }

    public static String getUserReposUrl(String name){
        return new StringBuilder().append("users/").append(name).append("/repos").toString();
    }

    public static String temperatureUrl="https://flow-test.cloudream.com/temperature/v1/image";
    public static String temperatureDataUrl="https://flow-test.cloudream.com/temperature/v1/data";
}

