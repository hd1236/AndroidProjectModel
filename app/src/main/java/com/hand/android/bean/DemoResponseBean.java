package com.hand.android.bean;

public class DemoResponseBean {


    /**
     * code : 0
     * requestId : fb10cdf6-6385-11ea-af5a-0242c0a8100a
     * msg : success
     * data : {"id":3,"putUrl":"https://cstorage.oss-cn-shenzhen.aliyuncs.com/temperature%2Fdevelopment%2F3%2Fpreview.png?Expires=1583926768&OSSAccessKeyId=LTAIxIARX95tLOLH&Signature=A4w1G5sRSG0tKUwKbP%2BHS7RaRVA%3D"}
     */

    private int code;
    private String requestId;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * putUrl : https://cstorage.oss-cn-shenzhen.aliyuncs.com/temperature%2Fdevelopment%2F3%2Fpreview.png?Expires=1583926768&OSSAccessKeyId=LTAIxIARX95tLOLH&Signature=A4w1G5sRSG0tKUwKbP%2BHS7RaRVA%3D
         */

        private int id;
        private String putUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPutUrl() {
            return putUrl;
        }

        public void setPutUrl(String putUrl) {
            this.putUrl = putUrl;
        }
    }
}
